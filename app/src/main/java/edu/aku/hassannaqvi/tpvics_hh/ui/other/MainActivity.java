package edu.aku.hassannaqvi.tpvics_hh.ui.other;

import android.annotation.SuppressLint;
import android.app.ActivityManager;
import android.app.DownloadManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

import edu.aku.hassannaqvi.tpvics_hh.CONSTANTS;
import edu.aku.hassannaqvi.tpvics_hh.R;
import edu.aku.hassannaqvi.tpvics_hh.core.AndroidDatabaseManager;
import edu.aku.hassannaqvi.tpvics_hh.core.MainApp;
import edu.aku.hassannaqvi.tpvics_hh.database.CreateTable;
import edu.aku.hassannaqvi.tpvics_hh.databinding.ActivityMainBinding;
import edu.aku.hassannaqvi.tpvics_hh.models.FormsContract;
import edu.aku.hassannaqvi.tpvics_hh.models.VersionApp;
import edu.aku.hassannaqvi.tpvics_hh.ui.list_activity.FormsReportCluster;
import edu.aku.hassannaqvi.tpvics_hh.ui.list_activity.FormsReportDate;
import edu.aku.hassannaqvi.tpvics_hh.ui.list_activity.PendingFormsActivity;
import edu.aku.hassannaqvi.tpvics_hh.ui.sections.SectionInfoActivity;
import edu.aku.hassannaqvi.tpvics_hh.ui.sync.SyncActivity;
import edu.aku.hassannaqvi.tpvics_hh.utils.AppUtilsKt;
import edu.aku.hassannaqvi.tpvics_hh.utils.WarningActivityInterface;
import edu.aku.hassannaqvi.tpvics_hh.utils.shared.SharedStorage;

import static edu.aku.hassannaqvi.tpvics_hh.core.MainApp.appInfo;

public class MainActivity extends AppCompatActivity implements WarningActivityInterface {

    static File file;
    private final String TAG = MainActivity.class.getName();
    private ActivityMainBinding bi;
    private final String dtToday = new SimpleDateFormat("dd-MMM-yyyy", Locale.ENGLISH).format(new Date());
    private final String sysdateToday = new SimpleDateFormat("dd-MM-yy", Locale.ENGLISH).format(new Date());
    private String preVer = "", newVer = "";
    private DownloadManager downloadManager;
    private Boolean exit = false;

    BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (DownloadManager.ACTION_DOWNLOAD_COMPLETE.equals(intent.getAction())) {

                DownloadManager.Query query = new DownloadManager.Query();
                query.setFilterById(SharedStorage.INSTANCE.getDownloadFileRefID(MainActivity.this));

                downloadManager = (DownloadManager) getSystemService(Context.DOWNLOAD_SERVICE);
                assert downloadManager != null;
                Cursor cursor = downloadManager.query(query);
                if (cursor.moveToFirst()) {
                    int colIndex = cursor.getColumnIndex(DownloadManager.COLUMN_STATUS);
                    if (DownloadManager.STATUS_SUCCESSFUL == cursor.getInt(colIndex)) {

                        Toast.makeText(context, "New App downloaded!!", Toast.LENGTH_SHORT).show();
                        bi.lblAppVersion.setText(new StringBuilder(getString(R.string.app_name) + " App New Version ").append(newVer).append("  Downloaded"));

                        ActivityManager am = (ActivityManager) getSystemService(ACTIVITY_SERVICE);
                        List<ActivityManager.RunningTaskInfo> taskInfo = am.getRunningTasks(1);

                        if (Objects.requireNonNull(taskInfo.get(0).topActivity).getClassName().equals(MainActivity.class.getName())) {
                            showDialog(newVer, preVer);
                        }
                    }
                }
            }
        }
    };

    private void showDialog(String newVer, String preVer) {
        AppUtilsKt.openWarningActivity(
                this,
                getString(R.string.app_name) + " APP is available!",
                getString(R.string.app_name) + " App Ver." + newVer + " is now available. Your are currently using older Ver." + preVer + ".\nInstall new version to use this app.",
                "Install",
                "Cancel"
        );
    }

    @SuppressLint("NonConstantResourceId")
    public void openForm(View v) {
        Intent oF = null;
        switch (v.getId()) {
            case R.id.formA:
                oF = new Intent(this, SectionInfoActivity.class);
                break;
            case R.id.databaseBtn:
                oF = new Intent(this, AndroidDatabaseManager.class);
                break;
        }
        startActivity(oF);
    }

    public void toggleSummary(View view) {
        if (bi.recordSummary.getVisibility() == View.VISIBLE)
            bi.recordSummary.setVisibility(View.GONE);
        else
            bi.recordSummary.setVisibility(View.VISIBLE);
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Intent intent = null;
        switch (item.getItemId()) {
            case R.id.onSync:
                intent = new Intent(MainActivity.this, SyncActivity.class)
                        .putExtra(CONSTANTS.SYNC_LOGIN, true)
                        .putExtra(CONSTANTS.SYNC_DISTRICTID_LOGIN, MainApp.DIST_ID);
                break;
            case R.id.checkOpenForms:
                intent = new Intent(MainActivity.this, PendingFormsActivity.class);
                break;
            case R.id.formsReportDate:
                intent = new Intent(MainActivity.this, FormsReportDate.class);
                break;
            case R.id.formsReportCluster:
                intent = new Intent(MainActivity.this, FormsReportCluster.class);
                break;
        }
        startActivity(intent);
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.item_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        bi = DataBindingUtil.setContentView(this, R.layout.activity_main);
        bi.setCallback(this);

        bi.txtinstalldate.setText(appInfo.getAppInfo());
        Collection<FormsContract> todaysForms = appInfo.getDbHelper().getTodayForms(sysdateToday);
        Collection<FormsContract> unsyncedForms = appInfo.getDbHelper().getUnsyncedForms();
        Collection<FormsContract> unclosedForms = appInfo.getDbHelper().getUnclosedForms();

        StringBuilder rSumText = new StringBuilder()
                .append("TODAY'S RECORDS SUMMARY\r\n")
                .append("=======================\r\n")
                .append("\r\n")
                .append("Total Forms Today" + "(").append(dtToday).append("): ").append(todaysForms.size()).append("\r\n");
        if (todaysForms.size() > 0) {
            String iStatus;
            rSumText.append("---------------------------------------------------------\r\n")
                    .append("[Cluster][Household][Children][Form Status][Sync Status]\r\n")
                    .append("---------------------------------------------------------\r\n");

            for (FormsContract fc : todaysForms) {
                Log.d(TAG, "onCreate: '" + fc.getIstatus() + "'");
                switch (fc.getIstatus()) {
                    case "1":
                        iStatus = "Complete";
                        break;
                    case "2":
                        iStatus = "No Resp";
                        break;
                    case "3":
                        iStatus = "Empty";
                        break;
                    case "4":
                        iStatus = "Refused";
                        break;
                    case "5":
                        iStatus = "Non Res.";
                        break;
                    case "6":
                        iStatus = "Not Found";
                        break;
                    case "96":
                        iStatus = "Other";
                        break;
                    case "":
                        iStatus = "Open";
                        break;
                    default:
                        iStatus = "\t\tN/A" + fc.getIstatus();
                }


                int childCount = appInfo.getDbHelper().getChildrenByUUID(fc.get_UID());
                rSumText.append(fc.getClusterCode())
                        .append(fc.getHhno())
                        .append("  \t\t")
                        .append(childCount)
                        .append("\t\t\t\t")
                        .append(iStatus)
                        .append("\t\t\t\t")
                        .append(fc.getSynced() == null ? "Not Synced" : "Synced")
                        .append("\r\n")
                        .append("---------------------------------------------------------\r\n");
            }
        }
        SharedPreferences syncPref = getSharedPreferences("src", Context.MODE_PRIVATE);
        rSumText.append("\r\nDEVICE INFORMATION\r\n")
                .append("  ========================================================\r\n")
                .append("\t|| Open Forms: \t\t\t\t\t\t").append(String.format(Locale.ENGLISH, "%02d", unclosedForms.size()))
                .append("\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t||\r\n")
                .append("\t|| Unsynced Forms: \t\t\t\t").append(String.format(Locale.ENGLISH, "%02d", unsyncedForms.size()))
                .append("\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t||\r\n")
                .append("\t|| Last Data Download: \t\t").append(SharedStorage.INSTANCE.getLastDataDownload(this))
                .append("\t\t\t\t\t\t||\r\n")
                .append("\t|| Last Data Upload: \t\t\t").append(SharedStorage.INSTANCE.getLastDataUpload(this))
                .append("\t\t\t\t\t\t||\r\n")
                .append("\t|| Last Photo Upload: \t\t").append(SharedStorage.INSTANCE.getLastPhotoUpload(this))
                .append("\t\t\t\t\t\t||\r\n")
                .append("\t========================================================\r\n");
        bi.recordSummary.setText(rSumText);

        Log.d(TAG, "onCreate: " + rSumText);
        bi.databaseBtn.setVisibility(MainApp.admin ? View.VISIBLE : View.GONE);

        // Auto download app
        VersionApp versionApp = appInfo.getDbHelper().getVersionApp();

        if (versionApp != null) {
            versionApp.getVersioncode();
            preVer = appInfo.getVersionName() + "." + appInfo.getVersionCode();
            newVer = versionApp.getVersionname() + "." + versionApp.getVersioncode();
            if (appInfo.getVersionCode() < Integer.parseInt(versionApp.getVersioncode())) {
                bi.lblAppVersion.setVisibility(View.VISIBLE);

                String fileName = CreateTable.DATABASE_NAME.replace(".db", "-New-Apps");
                file = new File(Environment.getExternalStorageDirectory() + File.separator + fileName, versionApp.getPathname());

                if (file.exists()) {
                    bi.lblAppVersion.setText(new StringBuilder(getString(R.string.app_name) + " New Version ").append(newVer).append("  Downloaded"));
                    showDialog(newVer, preVer);
                } else {
                    NetworkInfo networkInfo = ((ConnectivityManager) Objects.requireNonNull(getSystemService(Context.CONNECTIVITY_SERVICE))).getActiveNetworkInfo();
                    if (networkInfo != null && networkInfo.isConnected()) {
                        bi.lblAppVersion.setText(new StringBuilder(getString(R.string.app_name) + " App New Version ").append(newVer).append("  Downloading.."));
                        downloadManager = (DownloadManager) getSystemService(Context.DOWNLOAD_SERVICE);
                        Uri uri = Uri.parse(MainApp._UPDATE_URL + versionApp.getPathname());
                        DownloadManager.Request request = new DownloadManager.Request(uri);
                        request.setDestinationInExternalPublicDir(fileName, versionApp.getPathname())
                                .setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED)
                                .setTitle("Downloading " + getString(R.string.app_name) + " App new App ver." + newVer);

                        long refID = downloadManager.enqueue(request);
                        SharedStorage.INSTANCE.setDownloadFileRefID(this, refID);

                    } else {
                        bi.lblAppVersion.setText(new StringBuilder(getString(R.string.app_name) + " App New Version ").append(newVer).append("  Available..\n(Can't download.. Internet connectivity issue!!)"));
                    }
                }

            } else {
                bi.lblAppVersion.setVisibility(View.GONE);
                bi.lblAppVersion.setText(null);
            }
            registerReceiver(broadcastReceiver, new IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE));
        }

//        Testing visibility
        if (Integer.parseInt(appInfo.getVersionName().split("\\.")[0]) > 0) {
            bi.testing.setVisibility(View.GONE);
        } else {
            bi.testing.setVisibility(View.VISIBLE);
        }

    }

    @Override
    public void callWarningActivity() {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setDataAndType(Uri.fromFile(file), "application/vnd.android.package-archive");
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

    @Override
    public void onBackPressed() {
        if (exit) {
            finish(); // finish activity
            startActivity(new Intent(this, LoginActivity.class));
        } else {
            Toast.makeText(this, "Press Back again to Exit.", Toast.LENGTH_SHORT).show();
            exit = true;
            new Handler().postDelayed(() -> exit = false, 3 * 1000);

        }
    }
}
