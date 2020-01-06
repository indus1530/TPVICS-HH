package edu.aku.ramshasaeed.mnch.activities;

import android.app.ActivityManager;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DownloadManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.databinding.DataBindingUtil;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.support.design.widget.NavigationView;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.text.InputType;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.gson.Gson;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ExecutionException;

import edu.aku.ramshasaeed.mnch.R;
import edu.aku.ramshasaeed.mnch.appVersion.VersionAppContract;
import edu.aku.ramshasaeed.mnch.core.CONSTANTS;
import edu.aku.ramshasaeed.mnch.core.MainApp;
import edu.aku.ramshasaeed.mnch.data.AppDatabase;
import edu.aku.ramshasaeed.mnch.data.DAO.GetFncDAO;
import edu.aku.ramshasaeed.mnch.data.entities.Forms;
import edu.aku.ramshasaeed.mnch.databinding.ActivityMainBinding;
import edu.aku.ramshasaeed.mnch.get.db.GetAllDBData;
import edu.aku.ramshasaeed.mnch.get.server.GetAllData;
import edu.aku.ramshasaeed.mnch.sync.SyncAllData;
import im.dino.dbinspector.activities.DbInspectorActivity;

import static edu.aku.ramshasaeed.mnch.activities.LoginActivity.db;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    ActivityMainBinding bi;
    SharedPreferences.Editor editor;
    SharedPreferences sharedPref;
    AlertDialog.Builder builder;
    private String rSumText = "";
    String m_Text = "", preVer = "", newVer = "";
    String DirectoryName;
    private boolean updata = false;
    private Boolean exit = false;
    String dtToday = new SimpleDateFormat("dd-MM-yyyy HH:mm").format(new Date().getTime());
    String _dtToday = new SimpleDateFormat("dd-MM-yyyy").format(new Date().getTime());

    static File file;
    //working on verison control
    SharedPreferences sharedPrefDownload;
    SharedPreferences.Editor editorDownload;
    VersionAppContract versionAppContract;
    DownloadManager downloadManager;
    Long refID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        bi = DataBindingUtil.setContentView(this, R.layout.activity_main);
        bi.setCallback(this);
        setSupportActionBar(bi.appbarmain.toolbar);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, bi.drawerLayout, bi.appbarmain.toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        bi.drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        dbBackup();
        displayFormsStatus();
        loadTagDialog();

        bi.navView.setNavigationItemSelectedListener(this);

    }

    private void displayFormsStatus() {
        Collection<Forms> todaysForms = null;

        try {
            todaysForms = (Collection<Forms>) new GetAllDBData(db, GetFncDAO.class.getName(), "getFncDao", "getTodaysForms").execute("%" + _dtToday + "%").get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        String formID = "", completestatus = "", syncedStatus = "", formType = "";
        if (todaysForms.size() > 0) {

            String iStatus;

            for (Forms fc : todaysForms) {
                if (fc.getIstatus() != null) {
                    switch (fc.getIstatus()) {
                        case "1":
                            iStatus = "\tComplete";
                            break;
                        case "2":
                            iStatus = "\tIncomplete";
                            break;
                        default:
                            iStatus = "\tN/A";
                    }
                } else {
                    iStatus = "\tN/A";
                }
                formID = formID + "\n" + fc.getId();
                formType = formType + "\n" + fc.getFormType();
                completestatus = completestatus + "\n" + iStatus;
                syncedStatus = syncedStatus + "\n" + (fc.getSynced() == null || fc.getSynced().equals("") ? "Not Synced" : "Synced");

            }
        }
//        Setting Text in  UI
        bi.appbarmain.contentmain.formId.setText(formID);
        bi.appbarmain.contentmain.formType.setText(formType);
        bi.appbarmain.contentmain.completeStatus.setText(completestatus);
        bi.appbarmain.contentmain.syncStatus.setText(syncedStatus);

        settingVersion();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        /*if (id == R.id.action_settings) {
            return true;
        }*/

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        /*if (id == R.id.nav_hfa) {
            Toast.makeText(this, "This Form is Under Construction!", Toast.LENGTH_SHORT).show();

        } else*/
        if (id == R.id.nav_rsd) {
            startingActivities(1);
        } else if (id == R.id.navQOC) {
            startingActivities(2);
        } else if (id == R.id.nav_dhmt) {
            startingActivities(3);
        } else if (id == R.id.nav_upload) {
            uploadData();
        } else if (id == R.id.nav_download) {
            // Require permissions INTERNET & ACCESS_NETWORK_STATE
            ConnectivityManager connMgr = (ConnectivityManager)
                    getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
            if (networkInfo != null && networkInfo.isConnected()) {
                new syncData(this).execute();
            } else {
                Toast.makeText(this, "No network connection available.", Toast.LENGTH_SHORT).show();
            }
        } else if (id == R.id.nav_opendb) {
            if (MainApp.admin) {
                Intent dbmanager = new Intent(getApplicationContext(), DbInspectorActivity.class);
                startActivity(dbmanager);
            } else {
                Toast.makeText(this, "You are not allowed to avail this feature!!", Toast.LENGTH_SHORT).show();
            }

        }


        bi.drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

    private void startingActivities(int flag) {
        MainApp.FORM_TYPE = null;
        if (loadTagDialog()) return;
        if (settingVersion()) return;

        Intent i = new Intent(MainActivity.this, RSDInfoActivity.class);
        if (flag == 1)
            i.putExtra(MainApp.FORM_TYPE, MainApp.RSD);
        else if (flag == 2)
            i.putExtra(MainApp.FORM_TYPE, MainApp.QOC);
        else if (flag == 3)
            i.putExtra(MainApp.FORM_TYPE, MainApp.DHMT);

        startActivity(i);
    }

    @Override
    public void onBackPressed() {
        if (bi.drawerLayout.isDrawerOpen(GravityCompat.START)) {
            bi.drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            if (exit) {
                finish(); // finish activity
                startActivity(new Intent(this, LoginActivity.class).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK));
            } else {
                Toast.makeText(this, "Press Back again to Exit.",
                        Toast.LENGTH_SHORT).show();
                exit = true;
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        exit = false;
                    }
                }, 3 * 1000);

            }
        }
    }

    public void dbBackup() {

        sharedPref = getSharedPreferences("qoc_uen", MODE_PRIVATE);
        editor = sharedPref.edit();

        if (sharedPref.getBoolean("flag", true)) {

            String dt = sharedPref.getString("dt", new SimpleDateFormat("dd-MM-yyyy").format(new Date()));

            if (dt != new SimpleDateFormat("dd-MM-yyyy").format(new Date())) {
                editor.putString("dt", new SimpleDateFormat("dd-MM-yyyy").format(new Date()));

                editor.commit();
            }

            File folder = new File(Environment.getExternalStorageDirectory() + File.separator + "DMU-QOC-UEN");
            boolean success = true;
            if (!folder.exists()) {
                success = folder.mkdirs();
            }
            if (success) {

                DirectoryName = folder.getPath() + File.separator + sharedPref.getString("dt", "");
                folder = new File(DirectoryName);
                if (!folder.exists()) {
                    success = folder.mkdirs();
                }
                if (success) {

                    try {

                        String dbFileName = this.getDatabasePath(AppDatabase.Sub_DBConnection.DATABASE_NAME).getAbsolutePath();
                        String outFileName = DirectoryName + File.separator + AppDatabase.Sub_DBConnection.DATABASE_NAME + ".db";

                        File currentDB = new File(dbFileName);
                        File backupDB = new File(outFileName);

                        FileChannel src = new FileInputStream(currentDB).getChannel();
                        FileChannel dst = new FileOutputStream(backupDB).getChannel();
                        dst.transferFrom(src, 0, src.size());
                        src.close();
                        dst.close();


                    } catch (IOException e) {
                        Log.e("dbBackup:", e.getMessage());
                    }

                }

            } else {
                Toast.makeText(this, "Not create folder", Toast.LENGTH_SHORT).show();
            }
        }

    }

    public void uploadData() {

        if (!updata) {
            updata = true;

            // Require permissions INTERNET & ACCESS_NETWORK_STATE
            ConnectivityManager connMgr = (ConnectivityManager)
                    getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
            if (networkInfo != null && networkInfo.isConnected()) {

//            new SyncDevice(this).execute();
                Toast.makeText(getApplicationContext(), "Syncing Forms", Toast.LENGTH_SHORT).show();

//                Upload Form
                /*RSD Forms Upload*/
                Collection rsdcollection = null;
                try {
                    rsdcollection = new GetAllDBData(db, GetFncDAO.class.getName(), "getFncDao", "getUnSyncedForms").execute(MainApp.RSD).get();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                new SyncAllData(
                        this,
                        "RSDForms",
                        "updateSyncedForms",
                        Forms.class,
                        MainApp._HOST_URL + CONSTANTS.URL_RSD, rsdcollection
                ).execute();

                /*QOC Forms Upload*/
                Collection qoccollection = null;
                try {
                    qoccollection = new GetAllDBData(db, GetFncDAO.class.getName(), "getFncDao", "getUnSyncedForms").execute(MainApp.QOC).get();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                new SyncAllData(
                        this,
                        "QOCForms",
                        "updateSyncedForms",
                        Forms.class,
                        MainApp._HOST_URL + CONSTANTS.URL_QOC, qoccollection
                ).execute();

                /*DHMT Forms Upload*/
                Collection dhmtcollection = null;
                try {
                    dhmtcollection = new GetAllDBData(db, GetFncDAO.class.getName(), "getFncDao", "getUnSyncedForms").execute(MainApp.DHMT).get();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                new SyncAllData(
                        this,
                        "DHMTForms",
                        "updateSyncedForms",
                        Forms.class,
                        MainApp._HOST_URL + CONSTANTS.URL_DHMT, dhmtcollection
                ).execute();

                SharedPreferences syncPref = getSharedPreferences("SyncInfo", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = syncPref.edit();

                editor.putString("LastUpSyncServer", dtToday);

                editor.apply();

            } else {
                Toast.makeText(this, "No network connection available.", Toast.LENGTH_SHORT).show();
            }
            updata = false;
        }

    }

    public class syncData extends AsyncTask<String, String, String> {

        private Context mContext;

        public syncData(Context mContext) {
            this.mContext = mContext;
        }

        @Override
        protected String doInBackground(String... strings) {
            runOnUiThread(new Runnable() {

                @Override
                public void run() {

                    // RAMSHA SAEED
                    /*Toast.makeText(MainActivity.this, "Sync Users", Toast.LENGTH_LONG).show();
                    new GetAllData(mContext, "User", MainApp._HOST_URL + CONSTANTS.URL_USERS).execute();
                    Toast.makeText(MainActivity.this, "Sync District", Toast.LENGTH_LONG).show();
                    new GetAllData(mContext, "District", MainApp._HOST_URL + CONSTANTS.URL_DISTRICT).execute();
                    Toast.makeText(MainActivity.this, "Sync Facility Provider", Toast.LENGTH_LONG).show();
                    new GetAllData(mContext, "FacilityProvider", MainApp._HOST_URL + CONSTANTS.URL_HEALTH_FACILITY).execute();
                    *//*Toast.makeText(MainActivity.this, "Sync Tehsil", Toast.LENGTH_LONG).show();
                    new GetAllData(mContext, "Tehsil", MainApp._HOST_URL + CONSTANTS.URL_TEHSIL).execute();
                    Toast.makeText(MainActivity.this, "Sync UCs", Toast.LENGTH_LONG).show();
                    new GetAllData(mContext, "UCs", MainApp._HOST_URL + CONSTANTS.URL_UCS).execute();*/

                    //MUSTAFA ANSARI BY SAJID
                    Toast.makeText(MainActivity.this, "Sync Users", Toast.LENGTH_SHORT).show();
                    new GetAllData(mContext, "User", MainApp._HOST_URL + CONSTANTS.URL_USERS).execute();
                    Toast.makeText(MainActivity.this, "Sync District", Toast.LENGTH_SHORT).show();
                    new GetAllData(mContext, "District", MainApp._HOST_URL + CONSTANTS.URL_DISTRICT).execute();
                    Toast.makeText(MainActivity.this, "Sync Facility Provider", Toast.LENGTH_SHORT).show();
                    new GetAllData(mContext, "FacilityProvider", MainApp._HOST_URL + CONSTANTS.URL_HEALTH_FACILITY).execute();
                    Toast.makeText(MainActivity.this, "Sync AppVersion", Toast.LENGTH_SHORT).show();
                    new GetAllData(mContext, "appversion", MainApp._UPDATE_URL + CONSTANTS.URL_UPDATE_APP).execute();
                    Toast.makeText(MainActivity.this, "Sync Tehsil", Toast.LENGTH_LONG).show();
                    new GetAllData(mContext, "Tehsils", MainApp._HOST_URL + CONSTANTS.URL_TEHSILS).execute();
                    /*Toast.makeText(MainActivity.this, "Sync UCs", Toast.LENGTH_LONG).show();
                    new GetAllData(mContext, "UCs", MainApp._HOST_URL + CONSTANTS.URL_UCS).execute();*/
                }
            });

            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            new Handler().postDelayed(new Runnable() {

                @Override
                public void run() {

                    editor.putBoolean("flag", true);
                    editor.commit();

                    dbBackup();

                }
            }, 1200);
        }
    }

    private boolean loadTagDialog() {

        sharedPref = getSharedPreferences("tagName", MODE_PRIVATE);
        editor = sharedPref.edit();
        if (!sharedPref.contains("tagName") && sharedPref.getString("tagName", null) == null) {

            builder = new AlertDialog.Builder(MainActivity.this);
            builder.setTitle("ENTER DEVICE TAG-ID");
            /*ImageView img = new ImageView(getApplicationContext());
            img.setPadding(0, 15, 0, 15);
            builder.setCustomTitle(img);*/

            final EditText input = new EditText(MainActivity.this);
            input.setInputType(InputType.TYPE_CLASS_TEXT);
            builder.setView(input);

            builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    m_Text = input.getText().toString();
                    if (!m_Text.equals("")) {
                        editor.putString("tagName", m_Text);
                        editor.commit();
                        dialog.dismiss();

                    }
                }
            });
            builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.cancel();
                }
            });

            builder.show();

            return true;
        }
        return false;
    }

    boolean settingVersion() {
        sharedPrefDownload = getSharedPreferences("appDownload", MODE_PRIVATE);
        editorDownload = sharedPrefDownload.edit();

        BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                if (DownloadManager.ACTION_DOWNLOAD_COMPLETE.equals(intent.getAction())) {

                    DownloadManager.Query query = new DownloadManager.Query();
                    query.setFilterById(sharedPrefDownload.getLong("refID", 0));

                    downloadManager = (DownloadManager) getSystemService(Context.DOWNLOAD_SERVICE);
                    Cursor cursor = downloadManager.query(query);
                    if (cursor.moveToFirst()) {
                        int colIndex = cursor.getColumnIndex(DownloadManager.COLUMN_STATUS);
                        if (DownloadManager.STATUS_SUCCESSFUL == cursor.getInt(colIndex)) {

                            editorDownload.putBoolean("flag", true);
                            editorDownload.commit();

                            Toast.makeText(context, "New App downloaded!!", Toast.LENGTH_SHORT).show();
                            bi.appbarmain.contentmain.lblAppVersion.setText("MNCH App New Version " + newVer + "  Downloaded.");

                            ActivityManager am = (ActivityManager) getSystemService(ACTIVITY_SERVICE);
                            List<ActivityManager.RunningTaskInfo> taskInfo = am.getRunningTasks(1);
                            if (taskInfo.get(0).topActivity.getClassName().equals(MainActivity.class.getName())) {
//                                InstallNewApp(newVer, preVer);
                                showDialog(newVer, preVer);
                            }
                        }
                    }
                }
            }
        };

        versionAppContract = new Gson().fromJson(getSharedPreferences("main", Context.MODE_PRIVATE).getString("appVersion", ""), VersionAppContract.class);
        if (versionAppContract != null) {

            if (versionAppContract.getVersioncode() != null) {
                preVer = MainApp.versionName + "." + MainApp.versionCode;
                newVer = versionAppContract.getVersionname() + "." + versionAppContract.getVersioncode();

                if (MainApp.versionCode < Integer.valueOf(versionAppContract.getVersioncode())) {
                    bi.appbarmain.contentmain.lblAppVersion.setVisibility(View.VISIBLE);

                    String fileName = AppDatabase.Sub_DBConnection.DATABASE_NAME.replace(".db", "-New-Apps");
                    file = new File(Environment.getExternalStorageDirectory() + File.separator + fileName, versionAppContract.getPathname());

                    if (file.exists()) {
                        bi.appbarmain.contentmain.lblAppVersion.setText("MNCH New Version " + newVer + "  Downloaded.");
                        showDialog(newVer, preVer);
                    } else {
                        NetworkInfo networkInfo = ((ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE)).getActiveNetworkInfo();
                        if (networkInfo != null && networkInfo.isConnected()) {

                            bi.appbarmain.contentmain.lblAppVersion.setText("MNCH App New Version " + newVer + " Downloading..");
                            downloadManager = (DownloadManager) getSystemService(Context.DOWNLOAD_SERVICE);
                            Uri uri = Uri.parse(MainApp._UPDATE_URL + versionAppContract.getPathname());
                            DownloadManager.Request request = new DownloadManager.Request(uri);
                            request.setDestinationInExternalPublicDir(fileName, versionAppContract.getPathname())
                                    .setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED)
                                    .setTitle("Downloading MNCH App new App ver." + newVer);
                            refID = downloadManager.enqueue(request);

                            editorDownload.putLong("refID", refID);
                            editorDownload.putBoolean("flag", false);
                            editorDownload.commit();

                        } else {
                            bi.appbarmain.contentmain.lblAppVersion.setText("MNCH App New Version " + newVer + "  Available..\n(Can't download.. Internet connectivity issue!!)");
                        }
                    }

                    return true;

                } else {
                    bi.appbarmain.contentmain.lblAppVersion.setVisibility(View.GONE);
                    bi.appbarmain.contentmain.lblAppVersion.setText(null);
                }
            }
        }

        registerReceiver(broadcastReceiver, new IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE));

        return false;
    }


    void showDialog(String newVer, String preVer) {
        FragmentManager ft = getSupportFragmentManager();
        FragmentTransaction transaction = ft.beginTransaction();
        Fragment prev = ft.findFragmentByTag("dialog");
        if (prev != null) {
            transaction.remove(prev);
        }
        transaction.addToBackStack(null);
        DialogFragment newFragment = MyDialogFragment.newInstance(newVer, preVer);
        newFragment.show(ft, "dialog");
    }

    public static class MyDialogFragment extends DialogFragment {

        String newVer, preVer;

        static MyDialogFragment newInstance(String newVer, String preVer) {
            MyDialogFragment f = new MyDialogFragment();

            Bundle args = new Bundle();
            args.putString("newVer", newVer);
            args.putString("preVer", preVer);
            f.setArguments(args);

            return f;
        }

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            newVer = getArguments().getString("newVer");
            preVer = getArguments().getString("preVer");

            return new AlertDialog.Builder(getActivity())
                    .setIcon(android.R.drawable.ic_dialog_info)
                    .setTitle("MNCH App is available!")
                    .setMessage("MNCH App " + newVer + " is now available. Your are currently using older version " + preVer + ".\nInstall new version to use this app.")
                    .setPositiveButton("INSTALL!!",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int whichButton) {
                                    Intent intent = new Intent(Intent.ACTION_VIEW);
                                    intent.setDataAndType(Uri.fromFile(file), "application/vnd.android.package-archive");
                                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                    startActivity(intent);
                                }
                            }
                    )
                    .create();
        }

    }
}
