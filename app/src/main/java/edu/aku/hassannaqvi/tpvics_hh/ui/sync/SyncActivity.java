package edu.aku.hassannaqvi.tpvics_hh.ui.sync;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import edu.aku.hassannaqvi.tpvics_hh.CONSTANTS;
import edu.aku.hassannaqvi.tpvics_hh.R;
import edu.aku.hassannaqvi.tpvics_hh.adapter.SyncListAdapter;
import edu.aku.hassannaqvi.tpvics_hh.adapter.UploadListAdapter;
import edu.aku.hassannaqvi.tpvics_hh.contracts.ChildContract;
import edu.aku.hassannaqvi.tpvics_hh.contracts.FormsContract;
import edu.aku.hassannaqvi.tpvics_hh.core.DatabaseHelper;
import edu.aku.hassannaqvi.tpvics_hh.core.MainApp;
import edu.aku.hassannaqvi.tpvics_hh.databinding.ActivitySyncBinding;
import edu.aku.hassannaqvi.tpvics_hh.get.GetAllData;
import edu.aku.hassannaqvi.tpvics_hh.otherClasses.SyncModel;
import edu.aku.hassannaqvi.tpvics_hh.sync.SyncAllData;
import edu.aku.hassannaqvi.tpvics_hh.sync.SyncAllPhotos;
import edu.aku.hassannaqvi.tpvics_hh.sync.SyncDevice;
import edu.aku.hassannaqvi.tpvics_hh.utils.AndroidUtilityKt;
import edu.aku.hassannaqvi.tpvics_hh.utils.shared.SharedStorage;

import static edu.aku.hassannaqvi.tpvics_hh.utils.CreateTable.PROJECT_NAME;
import static edu.aku.hassannaqvi.tpvics_hh.utils.UtilKt.dbBackup;

public class SyncActivity extends AppCompatActivity implements SyncDevice.SyncDeviceInterface {

    DatabaseHelper db;
    SyncListAdapter syncListAdapter;
    UploadListAdapter uploadListAdapter;
    ActivitySyncBinding bi;
    SyncModel model;
    SyncModel uploadmodel;
    List<SyncModel> list;
    List<SyncModel> uploadlist;
    Boolean listActivityCreated;
    Boolean uploadlistActivityCreated;
    private boolean sync_flag;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        bi = DataBindingUtil.setContentView(this, R.layout.activity_sync);
        bi.setCallback(this);
        list = new ArrayList<>();
        uploadlist = new ArrayList<>();
        bi.noItem.setVisibility(View.VISIBLE);
        bi.noDataItem.setVisibility(View.VISIBLE);
        listActivityCreated = true;
        uploadlistActivityCreated = true;
        db = MainApp.appInfo.dbHelper;
        dbBackup(this);
        sync_flag = getIntent().getBooleanExtra(CONSTANTS.SYNC_LOGIN, false);
        setAdapter();
        setUploadAdapter();
    }

    public void btnOnDownloadData(View v) {
        if (AndroidUtilityKt.isNetworkConnected(this)) {
            bi.actionLbl.setText("Downloading Data...");
            if (sync_flag) new SyncData(SyncActivity.this, MainApp.DIST_ID).execute(true);
            else new SyncDevice(SyncActivity.this, true).execute();
        } else {
            Toast.makeText(this, "No network connection available.", Toast.LENGTH_SHORT).show();
        }
    }

    public void btnOnUploadData(View v) {
        if (AndroidUtilityKt.isNetworkConnected(this)) {
            bi.actionLbl.setText("Uploading Data...");
            DatabaseHelper db = new DatabaseHelper(this);

            new SyncDevice(this, false).execute();
//  *******************************************************Forms*********************************
            Toast.makeText(getApplicationContext(), "Syncing Forms", Toast.LENGTH_SHORT).show();
            if (uploadlistActivityCreated) {
                uploadmodel = new SyncModel();
                uploadmodel.setstatusID(0);
                uploadlist.add(uploadmodel);
            }
            new SyncAllData(
                    this,
                    "Forms",
                    "updateSyncedForms",
                    FormsContract.class,
                    MainApp._HOST_URL + MainApp._SERVER_URL,
                    FormsContract.FormsTable.TABLE_NAME,
                    db.getUnsyncedForms(), 0, uploadListAdapter, uploadlist
            ).execute();


            if (uploadlistActivityCreated) {
                uploadmodel = new SyncModel();
                uploadmodel.setstatusID(0);
                uploadlist.add(uploadmodel);
            }
            new SyncAllData(
                    this,
                    "Child",
                    "updateSyncedChildForms",
                    ChildContract.class,
                    MainApp._HOST_URL + MainApp._SERVER_URL,
                    ChildContract.ChildTable.TABLE_NAME,
                    db.getUnsyncedChildForms(), 1, uploadListAdapter, uploadlist
            ).execute();

            bi.noDataItem.setVisibility(View.GONE);

            uploadlistActivityCreated = false;

            SharedStorage.INSTANCE.setLastDataUpload(this, new SimpleDateFormat("dd-MM-yyyy HH:mm:ss", Locale.ENGLISH).format(new Date()));

        } else {
            Toast.makeText(this, "No network connection available.", Toast.LENGTH_SHORT).show();
        }
    }

    public void btnUploadPhotos(View view) {

        File directory = new File(this.getExternalFilesDir(Environment.DIRECTORY_PICTURES), PROJECT_NAME);
        Log.d("Directory", "uploadPhotos: " + directory);
        if (directory.exists()) {
            File[] files = directory.listFiles(file -> (file.getPath().endsWith(".jpg") || file.getPath().endsWith(".jpeg")));
            Log.d("Files", "Count: " + files.length);
            if (files.length > 0) {
                for (File file : files) {
                    Log.d("Files", "FileName:" + file.getName());
                    SyncAllPhotos syncAllPhotos = new SyncAllPhotos(file.getName(), this);
                    syncAllPhotos.execute();
                    try {
                        //3000 ms delay to process upload of next file.
                        Thread.sleep(3000);

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                SharedStorage.INSTANCE.setLastPhotoUpload(this, new SimpleDateFormat("dd-MM-yyyy HH:mm:ss", Locale.ENGLISH).format(new Date()));
            } else {
                Toast.makeText(this, "No photos to upload.", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(this, "No photos were taken", Toast.LENGTH_SHORT).show();
        }
    }

    void setAdapter() {
        syncListAdapter = new SyncListAdapter(list);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        bi.rvSyncList.setLayoutManager(mLayoutManager);
        bi.rvSyncList.setItemAnimator(new DefaultItemAnimator());
        bi.rvSyncList.setAdapter(syncListAdapter);
        syncListAdapter.notifyDataSetChanged();
        if (syncListAdapter.getItemCount() > 0) {
            bi.noItem.setVisibility(View.GONE);
        } else {
            bi.noItem.setVisibility(View.VISIBLE);
        }
    }

    void setUploadAdapter() {
        uploadListAdapter = new UploadListAdapter(uploadlist);
        RecyclerView.LayoutManager mLayoutManager2 = new LinearLayoutManager(getApplicationContext());
        bi.rvUploadList.setLayoutManager(mLayoutManager2);
        bi.rvUploadList.setItemAnimator(new DefaultItemAnimator());
        bi.rvUploadList.setAdapter(uploadListAdapter);
        uploadListAdapter.notifyDataSetChanged();
        if (uploadListAdapter.getItemCount() > 0) {
            bi.noDataItem.setVisibility(View.GONE);
        } else {
            bi.noDataItem.setVisibility(View.VISIBLE);
        }
    }

    private class SyncData extends AsyncTask<Boolean, String, String> {

        private final Context mContext;
        private final String distID;

        private SyncData(Context mContext, String districtId) {
            this.mContext = mContext;
            this.distID = districtId;
        }

        @Override
        protected String doInBackground(Boolean... booleans) {
            runOnUiThread(() -> {

                if (booleans[0]) {
//                  getting Users!!
                    if (listActivityCreated) {
                        model = new SyncModel();
                        model.setstatusID(0);
                        list.add(model);
                    }
                    new GetAllData(mContext, "User", syncListAdapter, list).execute();

//                    Getting App Version
                    if (listActivityCreated) {
                        model = new SyncModel();
                        model.setstatusID(0);
                        list.add(model);
                    }
                    new GetAllData(mContext, "VersionApp", syncListAdapter, list).execute();

//                  getting Districts!!
                    if (listActivityCreated) {
                        model = new SyncModel();
                        model.setstatusID(0);
                        list.add(model);
                    }
                    new GetAllData(mContext, "District", syncListAdapter, list).execute();
                    bi.noItem.setVisibility(View.GONE);

                } else {

//                   getting BL Random
                    if (listActivityCreated) {
                        model = new SyncModel();
                        model.setstatusID(0);
                        list.add(model);
                    }
                    new GetAllData(mContext, "BLRandom", syncListAdapter, list).execute(distID);

//                    Getting Enumblocks
                    if (listActivityCreated) {
                        model = new SyncModel();
                        model.setstatusID(0);
                        list.add(model);
                    }
                    new GetAllData(mContext, "EnumBlock", syncListAdapter, list).execute(distID);
                    bi.noItem.setVisibility(View.GONE);

                }

                listActivityCreated = false;
            });

            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            new Handler().postDelayed(() -> SharedStorage.INSTANCE.setLastDataDownload(mContext, new SimpleDateFormat("dd-MM-yyyy HH:mm:ss", Locale.ENGLISH).format(new Date())), 1200);
        }
    }

    @Override
    public void processFinish(boolean flag) {
        if (flag) {
            MainApp.appInfo.updateTagName(SyncActivity.this);
            new SyncData(SyncActivity.this, MainApp.DIST_ID).execute(sync_flag);
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        setResult(RESULT_OK);
        finish();
    }
}
