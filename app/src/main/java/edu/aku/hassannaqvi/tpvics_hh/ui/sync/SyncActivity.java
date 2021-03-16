package edu.aku.hassannaqvi.tpvics_hh.ui.sync;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.work.Constraints;
import androidx.work.Data;
import androidx.work.NetworkType;
import androidx.work.OneTimeWorkRequest;
import androidx.work.WorkContinuation;
import androidx.work.WorkInfo;
import androidx.work.WorkManager;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

import edu.aku.hassannaqvi.tpvics_hh.CONSTANTS;
import edu.aku.hassannaqvi.tpvics_hh.R;
import edu.aku.hassannaqvi.tpvics_hh.adapters.SyncListAdapter;
import edu.aku.hassannaqvi.tpvics_hh.core.MainApp;
import edu.aku.hassannaqvi.tpvics_hh.database.DatabaseHelper;
import edu.aku.hassannaqvi.tpvics_hh.databinding.ActivitySyncBinding;
import edu.aku.hassannaqvi.tpvics_hh.models.BLRandom;
import edu.aku.hassannaqvi.tpvics_hh.models.ChildContract;
import edu.aku.hassannaqvi.tpvics_hh.models.Clusters;
import edu.aku.hassannaqvi.tpvics_hh.models.Districts;
import edu.aku.hassannaqvi.tpvics_hh.models.FormsContract;
import edu.aku.hassannaqvi.tpvics_hh.models.SyncModel;
import edu.aku.hassannaqvi.tpvics_hh.models.Users;
import edu.aku.hassannaqvi.tpvics_hh.models.VersionApp;
import edu.aku.hassannaqvi.tpvics_hh.workers.DataDownWorkerALL;
import edu.aku.hassannaqvi.tpvics_hh.workers.DataUpWorkerALL;
import edu.aku.hassannaqvi.tpvics_hh.workers.PhotoUploadWorkerAll;

import static edu.aku.hassannaqvi.tpvics_hh.core.MainApp.sdDir;
import static edu.aku.hassannaqvi.tpvics_hh.database.CreateTable.PROJECT_NAME;
import static edu.aku.hassannaqvi.tpvics_hh.utils.AndroidUtilityKt.isNetworkConnected;
import static edu.aku.hassannaqvi.tpvics_hh.utils.AppUtilsKt.dbBackup;


public class SyncActivity extends AppCompatActivity {
    private static final String TAG = "SyncActivity";
    private DatabaseHelper db;
    private SyncListAdapter syncListAdapter;
    private ActivitySyncBinding bi;
    private List<SyncModel> uploadTables;
    private List<SyncModel> downloadTables;
    private String distCode;
    private int totalFiles;
    private long tStart;
    private String progress;
    final Handler handler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        bi = DataBindingUtil.setContentView(this, R.layout.activity_sync);
        bi.setCallback(this);
        db = MainApp.appInfo.getDbHelper();
        uploadTables = new ArrayList<>();
        downloadTables = new ArrayList<>();
        MainApp.uploadData = new ArrayList<>();
        sdDir = new File(this.getExternalFilesDir(
                Environment.DIRECTORY_PICTURES), PROJECT_NAME);

        bi.noDataItem.setVisibility(View.VISIBLE);

        db = new DatabaseHelper(this);
        dbBackup(this);

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        setResult(RESULT_OK);
        finish();
    }


    @SuppressLint("NonConstantResourceId")
    public void ProcessStart(View view) {

        if (!isNetworkConnected(this))
            return;

        switch (view.getId()) {

            case R.id.btnUpload:
                bi.dataLayout.setVisibility(View.VISIBLE);
                bi.photoLayout.setVisibility(View.GONE);
                bi.mTextViewS.setVisibility(View.GONE);
                bi.pBar.setVisibility(View.GONE);
                uploadTables.clear();
                MainApp.uploadData.clear();

                // Forms
                uploadTables.add(new SyncModel(FormsContract.FormsTable.TABLE_NAME.toLowerCase()));
                MainApp.uploadData.add(db.getUnsyncedForm());

                // Child
                uploadTables.add(new SyncModel(ChildContract.ChildTable.TABLE_NAME.toLowerCase()));
                MainApp.uploadData.add(db.getUnsyncedChildForms());

                setAdapter(uploadTables);
                uploadData();
                break;
            case R.id.btnSync:
                MainApp.downloadData = new String[0];
                bi.dataLayout.setVisibility(View.VISIBLE);
                bi.photoLayout.setVisibility(View.GONE);
                bi.mTextViewS.setVisibility(View.GONE);
                bi.pBar.setVisibility(View.GONE);
                downloadTables.clear();
                boolean sync_flag = getIntent().getBooleanExtra(CONSTANTS.SYNC_LOGIN, false);
                if (sync_flag) {
                    distCode = getIntent().getStringExtra(CONSTANTS.SYNC_DISTRICTID_LOGIN);
                    downloadTables.add(new SyncModel(BLRandom.BLRandomHHTable.TABLE_NAME.toLowerCase()));
                    downloadTables.add(new SyncModel(Clusters.ClusterTable.TABLE_NAME.toLowerCase()));
                } else {
                    // Set tables to DOWNLOAD
                    downloadTables.add(new SyncModel(Users.UserTable.TABLE_NAME.toLowerCase()));
                    downloadTables.add(new SyncModel(VersionApp.VersionAppTable.TABLE_NAME.toLowerCase()));
                    downloadTables.add(new SyncModel(Districts.DistrictTable.TABLE_NAME.toLowerCase()));
                }
                MainApp.downloadData = new String[downloadTables.size()];
                setAdapter(downloadTables);
                BeginDownload();
                break;

            default:
                throw new IllegalStateException("Unexpected value: " + view.getId());
        }
    }

    public void UploadPhotos(View view) {
        bi.dataLayout.setVisibility(View.GONE);
        bi.photoLayout.setVisibility(View.VISIBLE);
        bi.mTextViewS.setVisibility(View.VISIBLE);
        bi.pBar.setVisibility(View.VISIBLE);
        bi.photoLayout.removeAllViews();
        Log.d("Directory", "uploadPhotos: " + sdDir);
        if (sdDir.exists()) {
            File[] files = sdDir.listFiles(file -> (file.getPath().endsWith(".jpg") || file.getPath().endsWith(".jpeg")));
            sortBySize(files);
            bi.pBar.setProgress(0);
            Log.d("Files", "Count: " + files.length);
            if (files.length > 0) {

                int fcount = Math.min(files.length, 300);
                for (int i = 0; i < fcount; i++) {
                    TextView textView = new TextView(SyncActivity.this);
                    textView.setText("PROCESSING: " + files[i].getName());
                    textView.setId(i);
                    bi.photoLayout.addView(textView);
                    bi.mTextViewS.setText(i + " Photos found (processing...)");

                    int finalI = i;
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            totalFiles = files.length;
                            File fileZero = files[finalI];
                            tStart = System.currentTimeMillis();
                            Constraints constraints = new Constraints.Builder()
                                    .setRequiredNetworkType(NetworkType.CONNECTED)
                                    .build();
                            Data data = new Data.Builder()
                                    .putString("filename", fileZero.getName())
                                    .putInt("fCount", finalI)
                                    .build();

                            //This is the subclass of our WorkRequest
                            OneTimeWorkRequest photoUpload = new OneTimeWorkRequest.Builder(PhotoUploadWorkerAll.class).setInputData(data).setConstraints(constraints).build();

                            WorkManager.getInstance().enqueue(photoUpload);
                            //Listening to the work status
                            final TextView[] mTextView1 = new TextView[1];

                            WorkManager.getInstance().getWorkInfoByIdLiveData(photoUpload.getId())
                                    .observe(SyncActivity.this, new Observer<WorkInfo>() {

                                        @Override
                                        public void onChanged(@Nullable WorkInfo workInfo) {
                                            mTextView1[0] = findViewById(finalI);
                                            String message = workInfo.getState().name() + ": " + files[finalI].getName();
                                            mTextView1[0].setText(message);
                                            if (workInfo.getState() == WorkInfo.State.SUCCEEDED) {
                                                message = workInfo.getState().name() + ": " + workInfo.getOutputData().getString("message");
                                                mTextView1[0].setText(message);
                                                mTextView1[0].setTextColor(Color.parseColor("#007f00"));
                                                mTextView1[0].animate()
                                                        .translationY(-mTextView1[0].getHeight() * finalI)
                                                        .alpha(0.0f)
                                                        .setDuration(3500)
                                                        .setListener(new AnimatorListenerAdapter() {
                                                            @Override
                                                            public void onAnimationEnd(Animator animation) {
                                                                super.onAnimationEnd(animation);
                                                                mTextView1[0].setVisibility(View.GONE);
                                                            }
                                                        });
                                                upDatePhotoCount();
                                            }

                                            if (workInfo.getState() == WorkInfo.State.FAILED) {
                                                Toast.makeText(SyncActivity.this, "Failed", Toast.LENGTH_SHORT).show();
                                                String error = workInfo.getState().name() + ": " + workInfo.getOutputData().getString("error");
                                                mTextView1[0].setText(error);
                                                mTextView1[0].setTextColor(Color.RED);

                                                upDatePhotoCount();
                                            }

                                            if (workInfo.getState() == WorkInfo.State.CANCELLED) {
                                                mTextView1[0].setText("CANCELLED: " + fileZero.getName());
                                                mTextView1[0].setTextColor(Color.RED);

                                                upDatePhotoCount();
                                            }
                                        }
                                    });

                            if (finalI % 25 == 0) {
                                try {
                                    //3000 ms delay to process upload of next file.
                                    Thread.sleep(14000);
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }
                        }
                    }, 300);
                }

                bi.mTextViewS.setText(totalFiles + " Photos found (waiting for server...)");

            } else {
                bi.mTextViewS.setText(files.length + " Photos remaining");

                Toast.makeText(this, "No photos to upload.", Toast.LENGTH_SHORT).show();
            }
        } else {
            bi.mTextViewS.setText("No photo's were taken.");

            Toast.makeText(this, "No photos were taken", Toast.LENGTH_SHORT).show();
        }
    }


    private void BeginDownload() {
        List<OneTimeWorkRequest> workRequests = new ArrayList<>();
        for (int i = 0; i < downloadTables.size(); i++) {
            Data.Builder data = new Data.Builder()
                    .putString("table", downloadTables.get(i).gettableName())
                    .putInt("position", i)
                    //.putString("columns", "_id, sysdate")
                    // .putString("where", where)
                    ;
            if (downloadTables.get(i).gettableName().equals(BLRandom.BLRandomHHTable.TABLE_NAME)) {
                data.putString("where", BLRandom.BLRandomHHTable.COLUMN_DIST_CODE + "='" + distCode + "'");
            } else if (downloadTables.get(i).gettableName().equals(Clusters.ClusterTable.TABLE_NAME)) {
                data.putString("where", Clusters.ClusterTable.COLUMN_DIST_CODE + "='" + distCode + "'");
            }
            workRequests.add(new OneTimeWorkRequest.Builder(DataDownWorkerALL.class)
                    .addTag(String.valueOf(i))
                    .setInputData(data.build()).build());
        }

        // FOR SIMULTANEOUS WORKREQUESTS (ALL TABLES DOWNLOAD AT THE SAME TIME)
        WorkManager wm = WorkManager.getInstance();
        WorkContinuation wc = wm.beginWith(workRequests);
        wc.enqueue();

        wc.getWorkInfosLiveData().observe(this, workInfos -> {
            Log.d(TAG, "workInfos: " + workInfos.size());
            for (WorkInfo workInfo : workInfos) {
                Log.d(TAG, "workInfo: getState " + workInfo.getState());
                Log.d(TAG, "workInfo: data " + workInfo.getOutputData().getString("data"));
                Log.d(TAG, "workInfo: error " + workInfo.getOutputData().getString("error"));
                Log.d(TAG, "workInfo: position " + workInfo.getOutputData().getInt("position", 0));
            }
            for (WorkInfo workInfo : workInfos) {
                int position = workInfo.getOutputData().getInt("position", 0);
                String tableName = downloadTables.get(position).gettableName();

                if (workInfo.getState() == WorkInfo.State.SUCCEEDED) {

                    String result = MainApp.downloadData[position];
                    //Do something with the JSON string
                    if (result != null) {
                        if (result.length() > 0) {
                            Log.d(TAG, "onChanged: result " + result);
                            System.out.println("SYSTEM onChanged: result" + result);
                            DatabaseHelper db = new DatabaseHelper(SyncActivity.this);
                            try {
                                JSONArray jsonArray = new JSONArray();
                                int insertCount = 0;
                                switch (tableName) {
                                    case Users.UserTable.TABLE_NAME:
                                        jsonArray = new JSONArray(result);
                                        insertCount = db.syncUser(jsonArray);
                                        break;
                                    case VersionApp.VersionAppTable.TABLE_NAME:
                                        insertCount = db.syncVersionApp(new JSONObject(result));
                                        if (insertCount == 1) jsonArray.put("1");
                                        break;
                                    case BLRandom.BLRandomHHTable.TABLE_NAME:
                                        jsonArray = new JSONArray(result);
                                        insertCount = db.syncBLRandom(jsonArray);
                                        break;
                                    case Clusters.ClusterTable.TABLE_NAME:
                                        jsonArray = new JSONArray(result);
                                        insertCount = db.syncEnumBlocks(jsonArray);
                                        break;
                                    case Districts.DistrictTable.TABLE_NAME:
                                        jsonArray = new JSONArray(result);
                                        insertCount = db.syncDistrict(jsonArray);
                                        break;
                                }

                                downloadTables.get(position).setmessage("Received: " + jsonArray.length() + ", Saved: " + insertCount);
                                downloadTables.get(position).setstatus(insertCount == 0 ? "Unsuccessful" : "Successful");
                                downloadTables.get(position).setstatusID(insertCount == 0 ? 1 : 3);
                                syncListAdapter.updatesyncList(downloadTables);
                            } catch (JSONException e) {
                                e.printStackTrace();
                                downloadTables.get(position).setstatus("Process Failed");
                                downloadTables.get(position).setstatusID(1);
                                downloadTables.get(position).setmessage(result);
                                syncListAdapter.updatesyncList(downloadTables);
                            }
                        } else {
                            downloadTables.get(position).setmessage("Received: " + result.length() + "");
                            downloadTables.get(position).setstatus("Successful");
                            downloadTables.get(position).setstatusID(3);
                            syncListAdapter.updatesyncList(downloadTables);
                        }
                    } else {
                        downloadTables.get(position).setstatus("Process Failed");
                        downloadTables.get(position).setstatusID(1);
                        downloadTables.get(position).setmessage("Server not found!");
                        syncListAdapter.updatesyncList(downloadTables);
                    }
                }
                if (workInfo.getState() == WorkInfo.State.FAILED) {
                    String message = workInfo.getOutputData().getString("error");
                    downloadTables.get(position).setstatus("Process Failed");
                    downloadTables.get(position).setstatusID(1);
                    downloadTables.get(position).setmessage(message);
                    syncListAdapter.updatesyncList(downloadTables);

                }
            }
        });
    }

    private void setAdapter(List<SyncModel> tables) {
        syncListAdapter = new SyncListAdapter(tables);
        RecyclerView.LayoutManager mLayoutManager2 = new LinearLayoutManager(getApplicationContext());
        bi.rvUploadList.setLayoutManager(mLayoutManager2);
        bi.rvUploadList.setItemAnimator(new DefaultItemAnimator());
        bi.rvUploadList.setAdapter(syncListAdapter);
        syncListAdapter.notifyDataSetChanged();
        if (syncListAdapter.getItemCount() > 0) {
            bi.noDataItem.setVisibility(View.GONE);
        } else {
            bi.noDataItem.setVisibility(View.VISIBLE);
        }
    }

    private void uploadData() {
        List<OneTimeWorkRequest> workRequests = new ArrayList<>();
        for (int i = 0; i < uploadTables.size(); i++) {
            Data data = new Data.Builder()
                    .putString("table", uploadTables.get(i).gettableName())
                    .putInt("position", i)
                    //    .putString("data", uploadData.get(i).toString())
                    //.putString("columns", "_id, sysdate")
                    // .putString("where", where)
                    .build();
            OneTimeWorkRequest workRequest = new OneTimeWorkRequest.Builder(DataUpWorkerALL.class)
                    .addTag(String.valueOf(i))
                    .setInputData(data).build();
            workRequests.add(workRequest);

        }

        // FOR SIMULTANEOUS WORKREQUESTS (ALL TABLES DOWNLOAD AT THE SAME TIME)
        WorkManager wm = WorkManager.getInstance();
        WorkContinuation wc = wm.beginWith(workRequests);
        wc.enqueue();

        // FOR WORKREQUESTS CHAIN (ONE TABLE DOWNLOADS AT A TIME)
        wc.getWorkInfosLiveData().observe(this, workInfos -> {
            Log.d(TAG, "workInfos: " + workInfos.size());
            for (WorkInfo workInfo : workInfos) {
                Log.d(TAG, "workInfo: getState " + workInfo.getState());
                Log.d(TAG, "workInfo: data " + workInfo.getTags());
                Log.d(TAG, "workInfo: data " + workInfo.getOutputData().getString("message"));
                Log.d(TAG, "workInfo: error " + workInfo.getOutputData().getString("error"));
                Log.d(TAG, "workInfo: position " + workInfo.getOutputData().getInt("position", 0));
            }
            for (WorkInfo workInfo : workInfos) {
                int position = workInfo.getOutputData().getInt("position", 0);
                String tableName = uploadTables.get(position).gettableName();

                if (workInfo.getState() == WorkInfo.State.SUCCEEDED) {

                    String result = workInfo.getOutputData().getString("message");

                    int sSynced = 0;
                    int sDuplicate = 0;
                    StringBuilder sSyncedError = new StringBuilder();
                    JSONArray json;

                    if (result != null) {
                        if (result.length() > 0) {
                            try {
                                Log.d(TAG, "onPostExecute: " + result);
                                json = new JSONArray(result);

                                Method method = null;
                                for (Method method1 : db.getClass().getDeclaredMethods()) {
                                    Log.d(TAG, "onChanged Methods: " + method1.getName());
                                    Log.d(TAG, "onChanged Names: updateSynced" + tableName);
                                    Log.d(TAG, "onChanged Compare: " + method1.getName().equals("updateSynced" + tableName));
                                    if (method1.getName().equals("updateSynced" + tableName)) {
                                        method = method1;
                                        Toast.makeText(SyncActivity.this, "updateSynced not found: updateSynced" + tableName, Toast.LENGTH_SHORT).show();
                                        break;
                                    }
                                }
                                if (method != null) {
                                    for (int i = 0; i < json.length(); i++) {
                                        JSONObject jsonObject = new JSONObject(json.getString(i));
                                        Log.d(TAG, "onChanged: " + json.getString(i));
                                        if (jsonObject.getString("status").equals("1") && jsonObject.getString("error").equals("0")) {
                                            method.invoke(db, jsonObject.getString("id"));
                                            sSynced++;
                                        } else if (jsonObject.getString("status").equals("2") && jsonObject.getString("error").equals("0")) {
                                            method.invoke(db, jsonObject.getString("id"));
                                            sDuplicate++;
                                        } else {
                                            sSyncedError.append("\nError: ").append(jsonObject.getString("message"));
                                        }
                                    }
                                    Toast.makeText(SyncActivity.this, tableName + " synced: " + sSynced + "\r\n\r\n Errors: " + sSyncedError, Toast.LENGTH_SHORT).show();

                                    if (sSyncedError.toString().equals("")) {
                                        uploadTables.get(position).setmessage(tableName + " synced: " + sSynced + "\r\n\r\n Duplicates: " + sDuplicate + "\r\n\r\n Errors: " + sSyncedError);
                                        uploadTables.get(position).setstatus("Completed");
                                        uploadTables.get(position).setstatusID(3);
                                        syncListAdapter.updatesyncList(uploadTables);
                                    } else {
                                        uploadTables.get(position).setmessage(tableName + " synced: " + sSynced + "\r\n\r\n Duplicates: " + sDuplicate + "\r\n\r\n Errors: " + sSyncedError);
                                        uploadTables.get(position).setstatus("Process Failed");
                                        uploadTables.get(position).setstatusID(1);
                                        syncListAdapter.updatesyncList(uploadTables);
                                    }
                                } else {
                                    uploadTables.get(position).setmessage("Method not found: updateSynced" + tableName);
                                    uploadTables.get(position).setstatus("Process Failed");
                                    uploadTables.get(position).setstatusID(1);
                                    syncListAdapter.updatesyncList(uploadTables);
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                                Toast.makeText(SyncActivity.this, "Sync Result:  " + result, Toast.LENGTH_SHORT).show();

                                if (result.equals("No new records to sync.")) {
                                    uploadTables.get(position).setmessage(result /*+ " Open Forms" + String.format("%02d", unclosedForms.size())*/);
                                    uploadTables.get(position).setstatus("Not processed");
                                    uploadTables.get(position).setstatusID(4);
                                    syncListAdapter.updatesyncList(uploadTables);
                                } else {
                                    uploadTables.get(position).setmessage(result);
                                    uploadTables.get(position).setstatus("Process Failed");
                                    uploadTables.get(position).setstatusID(1);
                                    syncListAdapter.updatesyncList(uploadTables);
                                }
                            } catch (IllegalAccessException | InvocationTargetException e) {
                                e.printStackTrace();
                                uploadTables.get(position).setstatus("Process Failed");
                                uploadTables.get(position).setstatusID(1);
                                uploadTables.get(position).setmessage(e.getMessage());
                                syncListAdapter.updatesyncList(uploadTables);
                            }
                        } else {
                            uploadTables.get(position).setmessage("Received: " + result.length() + "");
                            uploadTables.get(position).setstatus("Successful");
                            uploadTables.get(position).setstatusID(3);
                            syncListAdapter.updatesyncList(uploadTables);
                        }
                    } else {
                        uploadTables.get(position).setstatus("Process Failed");
                        uploadTables.get(position).setstatusID(1);
                        uploadTables.get(position).setmessage("Server not found!");
                        syncListAdapter.updatesyncList(uploadTables);
                    }
                }
                //mTextView1.append("\n" + workInfo.getState().name());
                if (workInfo.getState() == WorkInfo.State.FAILED) {
                    String message = workInfo.getOutputData().getString("error");
                    uploadTables.get(position).setstatus("Process Failed");
                    uploadTables.get(position).setstatusID(1);
                    uploadTables.get(position).setmessage(message);
                    syncListAdapter.updatesyncList(uploadTables);

                }
            }
        });

    }

    private void upDatePhotoCount() {
        if (sdDir.exists()) {
            Log.d("DIR", "onCreate: " + sdDir.getName());
            File[] files = sdDir.listFiles(file -> (file.getPath().endsWith(".jpg") || file.getPath().endsWith(".jpeg")));

            if (files.length < totalFiles) {

                int filesProcessed = totalFiles - files.length;
                long tEnd = System.currentTimeMillis();
                long tDelta = tEnd - tStart;
                long tPerFile = tDelta / filesProcessed;
                long timeRemaining = files.length * tPerFile;
                long absTimeRemaining = ((timeRemaining / 25) * 14) + timeRemaining;
                String tRemain = String.format(Locale.ENGLISH, "%dh:%dm %ds",
                        TimeUnit.MILLISECONDS.toHours(absTimeRemaining),
                        TimeUnit.MILLISECONDS.toMinutes(absTimeRemaining),
                        TimeUnit.MILLISECONDS.toSeconds(absTimeRemaining) -
                                TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(absTimeRemaining))
                );

                // WORK MANAGER
                progress = files.length + "/" + totalFiles + " Photos remaining \r\nTime remaining: " + tRemain;
                if (absTimeRemaining > 0 && files.length > 0) {
                    if (TimeUnit.MILLISECONDS.toMinutes(absTimeRemaining / files.length) > 1) {
                        progress += "\r\n (!) - slow internet detected";
                    }
                }
                bi.mTextViewS.setText(progress);
                int fProgress = (int) Math.ceil(((totalFiles - files.length) * 100f) / totalFiles);
                bi.pBar.setProgress(fProgress);
            } else {
                bi.mTextViewS.setText(progress + "\r\n DONE!");
            }
        } else {
            bi.mTextViewS.setText("No photos found");
        }
    }

    private void sortBySize(File[] files) {
        Arrays.sort(files, (t, t1) -> (int) (t.length() - t1.length()));
    }
}
