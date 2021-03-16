package edu.aku.hassannaqvi.tpvics_hh.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.MatrixCursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import edu.aku.hassannaqvi.tpvics_hh.core.MainApp;
import edu.aku.hassannaqvi.tpvics_hh.models.BLRandom;
import edu.aku.hassannaqvi.tpvics_hh.models.BLRandom.BLRandomHHTable;
import edu.aku.hassannaqvi.tpvics_hh.models.ChildContract;
import edu.aku.hassannaqvi.tpvics_hh.models.ChildContract.ChildTable;
import edu.aku.hassannaqvi.tpvics_hh.models.Clusters;
import edu.aku.hassannaqvi.tpvics_hh.models.Clusters.ClusterTable;
import edu.aku.hassannaqvi.tpvics_hh.models.Districts;
import edu.aku.hassannaqvi.tpvics_hh.models.FormsContract;
import edu.aku.hassannaqvi.tpvics_hh.models.FormsContract.FormsTable;
import edu.aku.hassannaqvi.tpvics_hh.models.Users;
import edu.aku.hassannaqvi.tpvics_hh.models.VersionApp;

import static edu.aku.hassannaqvi.tpvics_hh.database.CreateTable.DATABASE_NAME;
import static edu.aku.hassannaqvi.tpvics_hh.database.CreateTable.DATABASE_VERSION;
import static edu.aku.hassannaqvi.tpvics_hh.database.CreateTable.SQL_CREATE_BL_RANDOM;
import static edu.aku.hassannaqvi.tpvics_hh.database.CreateTable.SQL_CREATE_CHILD_TABLE;
import static edu.aku.hassannaqvi.tpvics_hh.database.CreateTable.SQL_CREATE_DISTRICTS;
import static edu.aku.hassannaqvi.tpvics_hh.database.CreateTable.SQL_CREATE_FORMS;
import static edu.aku.hassannaqvi.tpvics_hh.database.CreateTable.SQL_CREATE_PSU_TABLE;
import static edu.aku.hassannaqvi.tpvics_hh.database.CreateTable.SQL_CREATE_USERS;
import static edu.aku.hassannaqvi.tpvics_hh.database.CreateTable.SQL_CREATE_VERSIONAPP;


/**
 * Created by hassan.naqvi.
 */

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String TAG = "DatabaseHelper";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL(SQL_CREATE_USERS);
        db.execSQL(SQL_CREATE_FORMS);
        db.execSQL(SQL_CREATE_PSU_TABLE);
        db.execSQL(SQL_CREATE_BL_RANDOM);
        db.execSQL(SQL_CREATE_VERSIONAPP);
        db.execSQL(SQL_CREATE_DISTRICTS);
        db.execSQL(SQL_CREATE_CHILD_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }


    public int syncEnumBlocks(JSONArray enumList) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(ClusterTable.TABLE_NAME, null, null);
        int insertCount = 0;

        for (int i = 0; i < enumList.length(); i++) {
            JSONObject jsonObjectCC = null;
            try {
                jsonObjectCC = enumList.getJSONObject(i);
            } catch (JSONException e) {
                e.printStackTrace();
            }

            Clusters Vc = new Clusters();
            try {
                Vc.Sync(jsonObjectCC);
            } catch (JSONException e) {
                e.printStackTrace();
            }

            ContentValues values = new ContentValues();

            values.put(ClusterTable.COLUMN_DIST_CODE, Vc.getDist_code());
            values.put(ClusterTable.COLUMN_ENUM_BLOCK_CODE, Vc.getEbcode());
            values.put(ClusterTable.COLUMN_GEO_AREA, Vc.getGeoarea());
            values.put(ClusterTable.COLUMN_CLUSTER_AREA, Vc.getCluster());

            db.insert(ClusterTable.TABLE_NAME, null, values);
            long rowID = db.insert(ClusterTable.TABLE_NAME, null, values);
            if (rowID != -1) insertCount++;
        }
        return insertCount;
    }

    public int syncBLRandom(JSONArray blList) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(BLRandomHHTable.TABLE_NAME, null, null);

        JSONArray jsonArray = blList;
        int insertCount = 0;
        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject jsonObjectCC = null;
            try {
                jsonObjectCC = jsonArray.getJSONObject(i);
            } catch (JSONException e) {
                e.printStackTrace();
            }

            BLRandom Vc = new BLRandom();
            try {
                Vc.Sync(jsonObjectCC);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            Log.d(TAG, "syncBLRandom: " + Vc.get_ID());
            ContentValues values = new ContentValues();

            values.put(BLRandomHHTable.COLUMN_ID, Vc.get_ID());
            values.put(BLRandomHHTable.COLUMN_LUID, Vc.getLUID());
            values.put(BLRandomHHTable.COLUMN_STRUCTURE_NO, Vc.getStructure());
            values.put(BLRandomHHTable.COLUMN_FAMILY_EXT_CODE, Vc.getExtension());
            values.put(BLRandomHHTable.COLUMN_HH, Vc.getHh());
            values.put(BLRandomHHTable.COLUMN_EB_CODE, Vc.getEbcode());
            values.put(BLRandomHHTable.COLUMN_P_CODE, Vc.getpCode());
            values.put(BLRandomHHTable.COLUMN_RANDOMDT, Vc.getRandomDT());
            values.put(BLRandomHHTable.COLUMN_HH_HEAD, Vc.getHhhead());
            values.put(BLRandomHHTable.COLUMN_CONTACT, Vc.getContact());
            values.put(BLRandomHHTable.COLUMN_HH_SELECTED_STRUCT, Vc.getSelStructure());
            values.put(BLRandomHHTable.COLUMN_SNO_HH, Vc.getSno());

            long row = db.insert(BLRandomHHTable.TABLE_NAME, null, values);
            if (row != -1) insertCount++;
        }
        return insertCount;
    }

    public Integer syncVersionApp(JSONObject VersionList) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(VersionApp.VersionAppTable.TABLE_NAME, null, null);
        long count = 0;
        try {
            JSONObject jsonObjectCC = ((JSONArray) VersionList.get(VersionApp.VersionAppTable.COLUMN_VERSION_PATH)).getJSONObject(0);
            VersionApp Vc = new VersionApp();
            Vc.sync(jsonObjectCC);

            ContentValues values = new ContentValues();

            values.put(VersionApp.VersionAppTable.COLUMN_PATH_NAME, Vc.getPathname());
            values.put(VersionApp.VersionAppTable.COLUMN_VERSION_CODE, Vc.getVersioncode());
            values.put(VersionApp.VersionAppTable.COLUMN_VERSION_NAME, Vc.getVersionname());

            count = db.insert(VersionApp.VersionAppTable.TABLE_NAME, null, values);
            if (count > 0) count = 1;

        } catch (Exception ignored) {
        } finally {
            db.close();
        }

        return (int) count;
    }

    public int syncUser(JSONArray userList) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(Users.UserTable.TABLE_NAME, null, null);
        int insertCount = 0;
        try {
            for (int i = 0; i < userList.length(); i++) {

                JSONObject jsonObjectUser = userList.getJSONObject(i);

                Users user = new Users();
                user.sync(jsonObjectUser);
                ContentValues values = new ContentValues();

                values.put(Users.UserTable.COLUMN_USERNAME, user.getUserName());
                values.put(Users.UserTable.COLUMN_PASSWORD, user.getPassword());
                values.put(Users.UserTable.COLUMN_DIST_ID, user.getDistID());
                long rowID = db.insert(Users.UserTable.TABLE_NAME, null, values);
                if (rowID != -1) insertCount++;
            }

        } catch (Exception e) {
            Log.d(TAG, "syncUser(e): " + e);
            db.close();
        } finally {
            db.close();
        }
        return insertCount;
    }

    public int syncDistrict(JSONArray distList) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(Districts.DistrictTable.TABLE_NAME, null, null);
        int insertCount = 0;
        try {
            for (int i = 0; i < distList.length(); i++) {

                JSONObject jsonObjectUser = distList.getJSONObject(i);

                Districts dist = new Districts();
                dist.Sync(jsonObjectUser);
                ContentValues values = new ContentValues();

                values.put(Districts.DistrictTable.COLUMN_DIST_ID, dist.getDist_id());
                values.put(Districts.DistrictTable.COLUMN_DIST_NAME, dist.getDistrict());
                values.put(Districts.DistrictTable.COLUMN_PROVINCE_NAME, dist.getProvince());
                long rowID = db.insert(Districts.DistrictTable.TABLE_NAME, null, values);
                if (rowID != -1) insertCount++;
            }

        } catch (Exception e) {
            Log.d(TAG, "syncDist(e): " + e);
            db.close();
        } finally {
            db.close();
        }
        return insertCount;
    }


    public Long addForm(FormsContract fc) {

        // Gets the data repository in write mode
        SQLiteDatabase db = this.getWritableDatabase();

// Create a new map of values, where column names are the keys
        ContentValues values = new ContentValues();
        values.put(FormsTable.COLUMN_PROJECT_NAME, fc.getProjectName());
        values.put(FormsTable.COLUMN_UID, fc.get_UID());
        values.put(FormsTable.COLUMN_FORMDATE, fc.getFormDate());
        values.put(FormsTable.COLUMN_SYSDATE, fc.getSysDate());
        values.put(FormsTable.COLUMN_LUID, fc.getLuid());
        values.put(FormsTable.COLUMN_USER, fc.getUser());
        values.put(FormsTable.COLUMN_ISTATUS, fc.getIstatus());
        values.put(FormsTable.COLUMN_ISTATUS88x, fc.getIstatus88x());
        values.put(FormsTable.COLUMN_FSTATUS, fc.getfStatus());
        values.put(FormsTable.COLUMN_FSTATUS88x, fc.getFstatus88x());
        values.put(FormsTable.COLUMN_ENDINGDATETIME, fc.getEndingdatetime());
        values.put(FormsTable.COLUMN_SINFO, fc.getsInfo());
        values.put(FormsTable.COLUMN_SE, fc.getsE());
        values.put(FormsTable.COLUMN_SM, fc.getsM());
        values.put(FormsTable.COLUMN_SN, fc.getsN());
        values.put(FormsTable.COLUMN_SO, fc.getsO());
        values.put(FormsTable.COLUMN_GPSLAT, fc.getGpsLat());
        values.put(FormsTable.COLUMN_GPSLNG, fc.getGpsLng());
        values.put(FormsTable.COLUMN_GPSDATE, fc.getGpsDT());
        values.put(FormsTable.COLUMN_GPSACC, fc.getGpsAcc());
        values.put(FormsTable.COLUMN_DEVICETAGID, fc.getDevicetagID());
        values.put(FormsTable.COLUMN_DEVICEID, fc.getDeviceID());
        values.put(FormsTable.COLUMN_APPVERSION, fc.getAppversion());
        values.put(FormsTable.COLUMN_CLUSTERCODE, fc.getClusterCode());
        values.put(FormsTable.COLUMN_HHNO, fc.getHhno());
        values.put(FormsTable.COLUMN_FORMTYPE, fc.getFormType());

        // Insert the new row, returning the primary key value of the new row
        long newRowId;
        newRowId = db.insert(
                FormsTable.TABLE_NAME,
                FormsTable.COLUMN_NAME_NULLABLE,
                values);
        return newRowId;
    }

    public Long addChild(ChildContract childContract) {

        // Gets the data repository in write mode
        SQLiteDatabase db = this.getWritableDatabase();

        // Create a new map of values, where column names are the keys
        ContentValues values = new ContentValues();
//        values.put(MWRATable._ID, mwra.get_ID());
        values.put(ChildTable.COLUMN_UUID, childContract.get_UUID());
        values.put(ChildTable.COLUMN_DEVICEID, childContract.getDeviceId());
        values.put(ChildTable.COLUMN_FORMDATE, childContract.getFormDate());
        values.put(ChildTable.COLUMN_SYSDATE, childContract.getSysDate());
        values.put(ChildTable.COLUMN_USER, childContract.getUser());
        values.put(ChildTable.COLUMN_SCA, childContract.getsCA());
        values.put(ChildTable.COLUMN_SCB, childContract.getsCB());
        values.put(ChildTable.COLUMN_SCC, childContract.getsCC());
        values.put(ChildTable.COLUMN_DEVICETAGID, childContract.getDevicetagID());

        values.put(ChildTable.COLUMN_CHILDNAME, childContract.getChildName());
        values.put(ChildTable.COLUMN_CHILDSERIAL, childContract.getChildSerial());
        values.put(ChildTable.COLUMN_GENDER, childContract.getgender());
        values.put(ChildTable.COLUMN_AGEY, childContract.getagey());
        values.put(ChildTable.COLUMN_AGEM, childContract.getagem());
        values.put(ChildTable.COLUMN_CLUSTERCODE, childContract.getcluster());
        values.put(ChildTable.COLUMN_HHNO, childContract.gethhno());
        values.put(ChildTable.COLUMN_CSTATUS, childContract.getCstatus());
        values.put(ChildTable.COLUMN_CSTATUS88x, childContract.getCstatus88x());


        // Insert the new row, returning the primary key value of the new row
        long newRowId;
        newRowId = db.insert(
                ChildTable.TABLE_NAME,
                null,
                values);
        return newRowId;
    }

    public int getChildrenByUUID(String UUID) {
        String countQuery = "SELECT  * FROM " + ChildTable.TABLE_NAME + " WHERE " + ChildTable.COLUMN_UUID + " = '" + UUID + "' AND " + ChildTable.COLUMN_CSTATUS + " = '1'";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        int count = cursor.getCount();
        cursor.close();
        return count;
    }

    public int getChildrenPhotoCheck(String UID) {
        String countQuery = "SELECT  * FROM " + ChildTable.TABLE_NAME +
                " WHERE " + ChildTable.COLUMN_UUID + " = '" + UID +
                "' AND " + ChildTable.COLUMN_CSTATUS + " = '1' " +
                " AND (" + ChildTable.COLUMN_SCC + " NOT LIKE '%\"frontFileName\":\"\"%' " +
                " OR " + ChildTable.COLUMN_SCC + " NOT LIKE '%\"backFileName\":\"\"%') ";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        int count = cursor.getCount();
        cursor.close();
        return count;
    }

    public int getChildrenCardCheck(String UID) {
        String countQuery = "SELECT  * FROM " + ChildTable.TABLE_NAME +
                " WHERE " + ChildTable.COLUMN_UUID + " = '" + UID +
                "' AND " + ChildTable.COLUMN_CSTATUS + " = '1' " +
                " AND " + ChildTable.COLUMN_SCC + " LIKE '%\"im01\":\"1\"%' ";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        int count = cursor.getCount();
        cursor.close();
        return count;
    }


    public Collection<FormsContract> getUnsyncedForms() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = null;
        String[] columns = null;

        String whereClause = FormsTable.COLUMN_SYNCED + " is null AND " + FormsTable.COLUMN_ISTATUS + " != '' ";

        String[] whereArgs = null;

        String groupBy = null;
        String having = null;

        String orderBy = FormsTable.COLUMN_ID + " ASC";

        Collection<FormsContract> allFC = new ArrayList<>();
        try {
            c = db.query(
                    FormsTable.TABLE_NAME,  // The table to query
                    columns,                   // The columns to return
                    whereClause,               // The columns for the WHERE clause
                    whereArgs,                 // The values for the WHERE clause
                    groupBy,                   // don't group the rows
                    having,                    // don't filter by row groups
                    orderBy                    // The sort order
            );
            while (c.moveToNext()) {
                FormsContract fc = new FormsContract();
                allFC.add(fc.Hydrate(c));
            }
        } finally {
            if (c != null) {
                c.close();
            }
            if (db != null) {
                db.close();
            }
        }
        return allFC;
    }

    public JSONArray getUnsyncedForm() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = null;
        String[] columns = null;

        String whereClause = FormsTable.COLUMN_SYNCED + " is null AND " + FormsTable.COLUMN_ISTATUS + " != '' ";

        String[] whereArgs = null;

        String groupBy = null;
        String having = null;

        String orderBy = FormsTable.COLUMN_ID + " ASC";

        JSONArray allFC = new JSONArray();
        try {
            c = db.query(
                    FormsTable.TABLE_NAME,  // The table to query
                    columns,                   // The columns to return
                    whereClause,               // The columns for the WHERE clause
                    whereArgs,                 // The values for the WHERE clause
                    groupBy,                   // don't group the rows
                    having,                    // don't filter by row groups
                    orderBy                    // The sort order
            );
            while (c.moveToNext()) {
                FormsContract fc = new FormsContract();
                allFC.put(fc.Hydrate(c).toJSONObject());
            }
        } catch (JSONException e) {
            e.printStackTrace();
        } finally {
            if (c != null) {
                c.close();
            }
            if (db != null) {
                db.close();
            }
        }
        return allFC;
    }

    public JSONArray getUnsyncedChildForms() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = null;
        String[] columns = null;

        String whereClause = ChildTable.COLUMN_SYNCED + " is null";

        String[] whereArgs = null;
        String groupBy = null;
        String having = null;

        String orderBy = ChildTable._ID + " ASC";

        JSONArray allFC = new JSONArray();
        try {
            c = db.query(
                    ChildTable.TABLE_NAME,  // The table to query
                    columns,                   // The columns to return
                    whereClause,               // The columns for the WHERE clause
                    whereArgs,                 // The values for the WHERE clause
                    groupBy,                   // don't group the rows
                    having,                    // don't filter by row groups
                    orderBy                    // The sort order
            );
            while (c.moveToNext()) {
                allFC.put(new ChildContract().hydrate(c).toJSONObject());
            }
        } catch (JSONException e) {
            e.printStackTrace();
        } finally {
            if (c != null) {
                c.close();
            }
            if (db != null) {
                db.close();
            }
        }
        return allFC;
    }


    public Users getLoginUser(String username, String password) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = null;
        String[] columns = null;
        String whereClause = Users.UserTable.COLUMN_USERNAME + "=? AND " + Users.UserTable.COLUMN_PASSWORD + "=?";
        String[] whereArgs = {username, password};
        String groupBy = null;
        String having = null;
        String orderBy = Users.UserTable.COLUMN_ID + " ASC";

        Users allForms = null;
        try {
            c = db.query(
                    Users.UserTable.TABLE_NAME,  // The table to query
                    columns,                   // The columns to return
                    whereClause,               // The columns for the WHERE clause
                    whereArgs,                 // The values for the WHERE clause
                    groupBy,                   // don't group the rows
                    having,                    // don't filter by row groups
                    orderBy                    // The sort order
            );
            while (c.moveToNext()) {
                allForms = new Users().hydrate(c);
            }
        } finally {
            if (c != null) {
                c.close();
            }
            if (db != null) {
                db.close();
            }
        }
        return allForms;
    }

    public VersionApp getVersionApp() {

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = null;
        String[] columns = {
                VersionApp.VersionAppTable.COLUMN_ID,
                VersionApp.VersionAppTable.COLUMN_VERSION_CODE,
                VersionApp.VersionAppTable.COLUMN_VERSION_NAME,
                VersionApp.VersionAppTable.COLUMN_PATH_NAME
        };

        String whereClause = null;
        String[] whereArgs = null;
        String groupBy = null;
        String having = null;

        String orderBy = null;

        VersionApp allVC = new VersionApp();
        try {
            c = db.query(
                    VersionApp.VersionAppTable.TABLE_NAME,  // The table to query
                    columns,                   // The columns to return
                    whereClause,               // The columns for the WHERE clause
                    whereArgs,                 // The values for the WHERE clause
                    groupBy,                   // don't group the rows
                    having,                    // don't filter by row groups
                    orderBy                    // The sort order
            );
            while (c.moveToNext()) {
                allVC.hydrate(c);
            }
        } finally {
            if (c != null) {
                c.close();
            }
            if (db != null) {
                db.close();
            }
        }
        return allVC;
    }

    public Collection<FormsContract> getTodayForms(String sysdate) {

        // String sysdate =  spDateT.substring(0, 8).trim()
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = null;
        String[] columns = {
                FormsTable._ID,
                FormsTable.COLUMN_UID,
                FormsTable.COLUMN_FORMDATE,
                FormsTable.COLUMN_SYSDATE,
                FormsTable.COLUMN_CLUSTERCODE,
                FormsTable.COLUMN_HHNO,
                FormsTable.COLUMN_ISTATUS,
                FormsTable.COLUMN_FSTATUS,
                FormsTable.COLUMN_SYNCED,

        };
        String whereClause = FormsTable.COLUMN_SYSDATE + " Like ? ";
        String[] whereArgs = new String[]{"%" + sysdate + " %"};
//        String[] whereArgs = new String[]{"%" + spDateT.substring(0, 8).trim() + "%"};
        String groupBy = null;
        String having = null;

        String orderBy = FormsTable.COLUMN_ID + " DESC";

        Collection<FormsContract> allFC = new ArrayList<>();
        try {
            c = db.query(
                    FormsTable.TABLE_NAME,  // The table to query
                    columns,                   // The columns to return
                    whereClause,               // The columns for the WHERE clause
                    whereArgs,                 // The values for the WHERE clause
                    groupBy,                   // don't group the rows
                    having,                    // don't filter by row groups
                    orderBy                    // The sort order
            );
            while (c.moveToNext()) {
                FormsContract fc = new FormsContract();
                fc.set_ID(c.getString(c.getColumnIndex(FormsTable.COLUMN_ID)));
                fc.set_UID(c.getString(c.getColumnIndex(FormsTable.COLUMN_UID)));
                fc.setFormDate(c.getString(c.getColumnIndex(FormsTable.COLUMN_FORMDATE)));
                fc.setSysDate(c.getString(c.getColumnIndex(FormsTable.COLUMN_SYSDATE)));
                fc.setClusterCode(c.getString(c.getColumnIndex(FormsTable.COLUMN_CLUSTERCODE)));
                fc.setHhno(c.getString(c.getColumnIndex(FormsTable.COLUMN_HHNO)));
                fc.setIstatus(c.getString(c.getColumnIndex(FormsTable.COLUMN_ISTATUS)));
                fc.setfStatus(c.getString(c.getColumnIndex(FormsTable.COLUMN_FSTATUS)));
                fc.setSynced(c.getString(c.getColumnIndex(FormsTable.COLUMN_SYNCED)));
                allFC.add(fc);
            }
        } finally {
            if (c != null) {
                c.close();
            }
            if (db != null) {
                db.close();
            }
        }
        return allFC;
    }

    public Collection<FormsContract> getFormsByCluster(String cluster) {

        // String sysdate =  spDateT.substring(0, 8).trim()
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = null;
        String[] columns = {
                FormsTable._ID,
                FormsTable.COLUMN_UID,
                FormsTable.COLUMN_FORMDATE,
                FormsTable.COLUMN_SYSDATE,
                FormsTable.COLUMN_CLUSTERCODE,
                FormsTable.COLUMN_HHNO,
                FormsTable.COLUMN_ISTATUS,
                FormsTable.COLUMN_FSTATUS,
                FormsTable.COLUMN_SYNCED,

        };
        String whereClause = FormsTable.COLUMN_CLUSTERCODE + " = ? ";
        String[] whereArgs = new String[]{cluster};
//        String[] whereArgs = new String[]{"%" + spDateT.substring(0, 8).trim() + "%"};
        String groupBy = null;
        String having = null;

        String orderBy =
                FormsTable.COLUMN_ID + " ASC";

        Collection<FormsContract> allFC = new ArrayList<>();
        try {
            c = db.query(
                    FormsTable.TABLE_NAME,  // The table to query
                    columns,                   // The columns to return
                    whereClause,               // The columns for the WHERE clause
                    whereArgs,                 // The values for the WHERE clause
                    groupBy,                   // don't group the rows
                    having,                    // don't filter by row groups
                    orderBy                    // The sort order
            );
            while (c.moveToNext()) {
                FormsContract fc = new FormsContract();
                fc.set_ID(c.getString(c.getColumnIndex(FormsTable.COLUMN_ID)));
                fc.set_UID(c.getString(c.getColumnIndex(FormsTable.COLUMN_UID)));
                fc.setFormDate(c.getString(c.getColumnIndex(FormsTable.COLUMN_FORMDATE)));
                fc.setSysDate(c.getString(c.getColumnIndex(FormsTable.COLUMN_SYSDATE)));
                fc.setClusterCode(c.getString(c.getColumnIndex(FormsTable.COLUMN_CLUSTERCODE)));
                fc.setHhno(c.getString(c.getColumnIndex(FormsTable.COLUMN_HHNO)));
                fc.setIstatus(c.getString(c.getColumnIndex(FormsTable.COLUMN_ISTATUS)));
                fc.setfStatus(c.getString(c.getColumnIndex(FormsTable.COLUMN_FSTATUS)));
                fc.setSynced(c.getString(c.getColumnIndex(FormsTable.COLUMN_SYNCED)));
                allFC.add(fc);
            }
        } finally {
            if (c != null) {
                c.close();
            }
            if (db != null) {
                db.close();
            }
        }
        return allFC;
    }

    public ArrayList<FormsContract> getUnclosedForms() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = null;
        String[] columns = {
                FormsTable._ID,
                FormsTable.COLUMN_UID,
                FormsTable.COLUMN_FORMDATE,
                FormsTable.COLUMN_SYSDATE,
                FormsTable.COLUMN_CLUSTERCODE,
                FormsTable.COLUMN_HHNO,
                FormsTable.COLUMN_ISTATUS,
                FormsTable.COLUMN_FSTATUS,
                FormsTable.COLUMN_SYNCED,
        };
        String whereClause = FormsTable.COLUMN_ISTATUS + " = ''";
        String[] whereArgs = null;
//        String[] whereArgs = new String[]{"%" + spDateT.substring(0, 8).trim() + "%"};
        String groupBy = null;
        String having = null;
        String orderBy = FormsTable.COLUMN_ID + " ASC";
        ArrayList<FormsContract> allFC = new ArrayList<>();
        try {
            c = db.query(
                    FormsTable.TABLE_NAME,  // The table to query
                    columns,                   // The columns to return
                    whereClause,               // The columns for the WHERE clause
                    whereArgs,                 // The values for the WHERE clause
                    groupBy,                   // don't group the rows
                    having,                    // don't filter by row groups
                    orderBy                    // The sort order
            );
            while (c.moveToNext()) {
                FormsContract fc = new FormsContract();
                fc.set_ID(c.getString(c.getColumnIndex(FormsTable.COLUMN_ID)));
                fc.set_UID(c.getString(c.getColumnIndex(FormsTable.COLUMN_UID)));
                fc.setFormDate(c.getString(c.getColumnIndex(FormsTable.COLUMN_FORMDATE)));
                fc.setSysDate(c.getString(c.getColumnIndex(FormsTable.COLUMN_SYSDATE)));
                fc.setClusterCode(c.getString(c.getColumnIndex(FormsTable.COLUMN_CLUSTERCODE)));
                fc.setHhno(c.getString(c.getColumnIndex(FormsTable.COLUMN_HHNO)));
                fc.setIstatus(c.getString(c.getColumnIndex(FormsTable.COLUMN_ISTATUS)));
                fc.setfStatus(c.getString(c.getColumnIndex(FormsTable.COLUMN_FSTATUS)));
                fc.setSynced(c.getString(c.getColumnIndex(FormsTable.COLUMN_SYNCED)));
                allFC.add(fc);
            }
        } finally {
            if (c != null) {
                c.close();
            }
            if (db != null) {
                db.close();
            }
        }
        return allFC;
    }

    public BLRandom getHHFromBLRandom(String subAreaCode, String hh) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = null;

        String[] columns = {
                BLRandomHHTable.COLUMN_ID,
                BLRandomHHTable.COLUMN_LUID,
                BLRandomHHTable.COLUMN_STRUCTURE_NO,
                BLRandomHHTable.COLUMN_FAMILY_EXT_CODE,
                BLRandomHHTable.COLUMN_HH,
                BLRandomHHTable.COLUMN_P_CODE,
                BLRandomHHTable.COLUMN_EB_CODE,
                BLRandomHHTable.COLUMN_RANDOMDT,
                BLRandomHHTable.COLUMN_HH_SELECTED_STRUCT,
                BLRandomHHTable.COLUMN_CONTACT,
                BLRandomHHTable.COLUMN_HH_HEAD,
                BLRandomHHTable.COLUMN_SNO_HH
        };

        String whereClause = BLRandomHHTable.COLUMN_P_CODE + "=? AND " + BLRandomHHTable.COLUMN_HH + "=?";
        String[] whereArgs = new String[]{subAreaCode, hh};
        String groupBy = null;
        String having = null;

        String orderBy =
                BLRandomHHTable.COLUMN_ID + " ASC";

        BLRandom allBL = null;
        try {
            c = db.query(
                    BLRandomHHTable.TABLE_NAME,  // The table to query
                    columns,                   // The columns to return
                    whereClause,               // The columns for the WHERE clause
                    whereArgs,                 // The values for the WHERE clause
                    groupBy,                   // don't group the rows
                    having,                    // don't filter by row groups
                    orderBy                    // The sort order
            );
            while (c.moveToNext()) {
                allBL = new BLRandom().hydrate(c);
            }
        } finally {
            if (c != null) {
                c.close();
            }
            if (db != null) {
                db.close();
            }
        }
        return allBL;
    }

    public Clusters getEnumBlock(String cluster) {

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = null;
        String[] columns = {
                ClusterTable._ID,
                ClusterTable.COLUMN_DIST_CODE,
                ClusterTable.COLUMN_ENUM_BLOCK_CODE,
                ClusterTable.COLUMN_GEO_AREA,
                ClusterTable.COLUMN_CLUSTER_AREA
        };

        String whereClause = ClusterTable.COLUMN_CLUSTER_AREA + " =?";
        String[] whereArgs = {cluster};

        String groupBy = null;
        String having = null;

        String orderBy = ClusterTable._ID + " ASC";
        Clusters allEB = null;
        try {
            c = db.query(
                    ClusterTable.TABLE_NAME,  // The table to query
                    columns,                   // The columns to return
                    whereClause,               // The columns for the WHERE clause
                    whereArgs,                 // The values for the WHERE clause
                    groupBy,                   // don't group the rows
                    having,                    // don't filter by row groups
                    orderBy                    // The sort order
            );
            while (c.moveToNext()) {
                allEB = new Clusters().HydrateEnum(c);
            }
        } finally {
            if (c != null) {
                c.close();
            }
            if (db != null) {
                db.close();
            }
        }
        return allEB;
    }

    public List<ChildContract> getFilledChildForms(String clusterCode, String hhNo, String uuid) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = null;
        String[] columns = {
                ChildTable._ID,
                ChildTable.COLUMN_UID,
                ChildTable.COLUMN_UUID,
                ChildTable.COLUMN_DEVICEID,
                ChildTable.COLUMN_FORMDATE,
                ChildTable.COLUMN_SYSDATE,
                ChildTable.COLUMN_USER,
                ChildTable.COLUMN_SCA,
                ChildTable.COLUMN_SCB,
                ChildTable.COLUMN_SCC,
                ChildTable.COLUMN_DEVICETAGID,
                ChildTable.COLUMN_CHILDNAME,
                ChildTable.COLUMN_CHILDSERIAL,
                ChildTable.COLUMN_GENDER,
                ChildTable.COLUMN_AGEY,
                ChildTable.COLUMN_AGEM,
                ChildTable.COLUMN_CLUSTERCODE,
                ChildTable.COLUMN_HHNO,
                ChildTable.COLUMN_CSTATUS,
                ChildTable.COLUMN_CSTATUS88x,
        };

        String whereClause = ChildTable.COLUMN_CLUSTERCODE + "=? AND " + ChildTable.COLUMN_HHNO + "=? AND " + ChildTable.COLUMN_UUID + "=? AND (" + ChildTable.COLUMN_CSTATUS + " is not null OR " + ChildTable.COLUMN_CSTATUS + " !='')";
        String[] whereArgs = {clusterCode, hhNo, uuid};
        String groupBy = null;
        String having = null;
        String orderBy = ChildTable.COLUMN_CHILDSERIAL + " ASC";
        List<ChildContract> allFC = new ArrayList<>();
        try {
            c = db.query(
                    ChildTable.TABLE_NAME,  // The table to query
                    columns,                   // The columns to return
                    whereClause,               // The columns for the WHERE clause
                    whereArgs,                 // The values for the WHERE clause
                    groupBy,                   // don't group the rows
                    having,                    // don't filter by row groups
                    orderBy                    // The sort order
            );
            while (c.moveToNext()) {
                allFC.add(new ChildContract().hydrate(c));
            }
        } finally {
            if (c != null) {
                c.close();
            }
            if (db != null) {
                db.close();
            }
        }
        return allFC;
    }

    public FormsContract getFilledForm(String clusterCode, String hhNo) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = null;
        String[] columns = {
                FormsTable._ID,
                FormsTable.COLUMN_UID,
                FormsTable.COLUMN_FORMDATE,
                FormsTable.COLUMN_SYSDATE,
                FormsTable.COLUMN_USER,
                FormsTable.COLUMN_ISTATUS,
                FormsTable.COLUMN_ISTATUS88x,
                FormsTable.COLUMN_FSTATUS,
                FormsTable.COLUMN_FSTATUS88x,
                FormsTable.COLUMN_LUID,
                FormsTable.COLUMN_ENDINGDATETIME,
                FormsTable.COLUMN_SINFO,
                FormsTable.COLUMN_SE,
                FormsTable.COLUMN_SM,
                FormsTable.COLUMN_SN,
                FormsTable.COLUMN_SO,
                FormsTable.COLUMN_GPSLAT,
                FormsTable.COLUMN_GPSLNG,
                FormsTable.COLUMN_GPSDATE,
                FormsTable.COLUMN_GPSACC,
                FormsTable.COLUMN_DEVICETAGID,
                FormsTable.COLUMN_DEVICEID,
                FormsTable.COLUMN_APPVERSION,
                FormsTable.COLUMN_CLUSTERCODE,
                FormsTable.COLUMN_HHNO,
                FormsTable.COLUMN_FORMTYPE
        };

//        String whereClause = "(" + FormsTable.COLUMN_ISTATUS + " is null OR " + FormsTable.COLUMN_ISTATUS + "='') AND " + FormsTable.COLUMN_CLUSTERCODE + "=? AND " + FormsTable.COLUMN_HHNO + "=?";
        String whereClause = FormsTable.COLUMN_CLUSTERCODE + "=? AND " + FormsTable.COLUMN_HHNO + "=?";
        String[] whereArgs = {clusterCode, hhNo};
        String groupBy = null;
        String having = null;
        String orderBy = FormsTable._ID + " ASC";
        FormsContract allFC = null;
        try {
            c = db.query(
                    FormsTable.TABLE_NAME,  // The table to query
                    columns,                   // The columns to return
                    whereClause,               // The columns for the WHERE clause
                    whereArgs,                 // The values for the WHERE clause
                    groupBy,                   // don't group the rows
                    having,                    // don't filter by row groups
                    orderBy                    // The sort order
            );
            while (c.moveToNext()) {
                allFC = new FormsContract().Hydrate(c);
            }
        } finally {
            if (c != null) {
                c.close();
            }
            if (db != null) {
                db.close();
            }
        }
        return allFC;
    }

    public List<Districts> getDistrictProv() {

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = null;
        String[] columns = {
                Districts.DistrictTable._ID,
                Districts.DistrictTable.COLUMN_DIST_ID,
                Districts.DistrictTable.COLUMN_DIST_NAME,
                Districts.DistrictTable.COLUMN_PROVINCE_NAME
        };

        String whereClause = null;
        String[] whereArgs = null;
        String groupBy = null;
        String having = null;

        String orderBy = Districts.DistrictTable._ID + " ASC";
        List<Districts> allEB = new ArrayList<>();
        try {
            c = db.query(
                    Districts.DistrictTable.TABLE_NAME,  // The table to query
                    columns,                   // The columns to return
                    whereClause,               // The columns for the WHERE clause
                    whereArgs,                 // The values for the WHERE clause
                    groupBy,                   // don't group the rows
                    having,                    // don't filter by row groups
                    orderBy                    // The sort order
            );
            while (c.moveToNext()) {
                allEB.add(new Districts().Hydrate(c));
            }
        } finally {
            if (c != null) {
                c.close();
            }
            if (db != null) {
                db.close();
            }
        }
        return allEB;
    }


    public int updatesFormColumn(String column, String value) {
        SQLiteDatabase db = this.getReadableDatabase();

        ContentValues values = new ContentValues();
        values.put(column, value);

        String selection = FormsTable._ID + " =? ";
        String[] selectionArgs = {String.valueOf(MainApp.fc.get_ID())};

        return db.update(FormsTable.TABLE_NAME,
                values,
                selection,
                selectionArgs);
    }

    public int updatesChildColumn(String column, String value) {
        SQLiteDatabase db = this.getReadableDatabase();

        ContentValues values = new ContentValues();
        values.put(column, value);

        String selection = ChildTable._ID + " =? ";
        String[] selectionArgs = {String.valueOf(MainApp.child.get_ID())};

        return db.update(ChildTable.TABLE_NAME,
                values,
                selection,
                selectionArgs);
    }

    public int updateEnding(boolean flag) {
        SQLiteDatabase db = this.getReadableDatabase();

        // New value for one column
        ContentValues values = new ContentValues();
        if (flag) {
            values.put(FormsTable.COLUMN_ISTATUS, MainApp.fc.getIstatus());
            values.put(FormsTable.COLUMN_ISTATUS88x, MainApp.fc.getIstatus88x());
            values.put(FormsTable.COLUMN_SINFO, MainApp.fc.getsInfo());
        } else {
            values.put(FormsTable.COLUMN_FSTATUS, MainApp.fc.getfStatus());
            values.put(FormsTable.COLUMN_FSTATUS88x, MainApp.fc.getFstatus88x());
            values.put(FormsTable.COLUMN_ENDINGDATETIME, MainApp.fc.getEndingdatetime());
        }

        // Which row to update, based on the ID
        String selection = FormsTable.COLUMN_ID + " =? ";
        String[] selectionArgs = {String.valueOf(MainApp.fc.get_ID())};

        int count = db.update(FormsTable.TABLE_NAME,
                values,
                selection,
                selectionArgs);
        return count;
    }

    public int updateChildEnding() {
        SQLiteDatabase db = this.getReadableDatabase();

        // New value for one column
        ContentValues values = new ContentValues();
        values.put(ChildTable.COLUMN_CSTATUS, MainApp.child.getCstatus());
        values.put(ChildTable.COLUMN_CSTATUS88x, MainApp.child.getCstatus88x());

        // Which row to update, based on the ID
        String selection = ChildTable.COLUMN_ID + " =? ";
        String[] selectionArgs = {String.valueOf(MainApp.child.get_ID())};

        int count = db.update(ChildTable.TABLE_NAME,
                values,
                selection,
                selectionArgs);
        return count;
    }

    public ArrayList<Cursor> getDataForDatabaseManager(String Query) {
        //get writable database
        SQLiteDatabase sqlDB = this.getWritableDatabase();
        String[] columns = new String[]{"message"};
        //an array list of cursor to save two cursors one has results from the query
        //other cursor stores error message if any errors are triggered
        ArrayList<Cursor> alc = new ArrayList<Cursor>(2);
        MatrixCursor Cursor2 = new MatrixCursor(columns);
        alc.add(null);
        alc.add(null);

        try {
            String maxQuery = Query;
            //execute the query results will be save in Cursor c
            Cursor c = sqlDB.rawQuery(maxQuery, null);

            //add value to cursor2
            Cursor2.addRow(new Object[]{"Success"});

            alc.set(1, Cursor2);
            if (null != c && c.getCount() > 0) {

                alc.set(0, c);
                c.moveToFirst();

                return alc;
            }
            return alc;
        } catch (SQLException sqlEx) {
            Log.d("printing exception", sqlEx.getMessage());
            //if any exceptions are triggered save the error message to cursor an return the arraylist
            Cursor2.addRow(new Object[]{"" + sqlEx.getMessage()});
            alc.set(1, Cursor2);
            return alc;
        } catch (Exception ex) {

            Log.d("printing exception", ex.getMessage());

            //if any exceptions are triggered save the error message to cursor an return the arraylist
            Cursor2.addRow(new Object[]{"" + ex.getMessage()});
            alc.set(1, Cursor2);
            return alc;
        }
    }


    public void updateSyncedForms(String id) {
        SQLiteDatabase db = this.getReadableDatabase();

// New value for one column
        ContentValues values = new ContentValues();
        values.put(FormsTable.COLUMN_SYNCED, true);
        values.put(FormsTable.COLUMN_SYNCED_DATE, new Date().toString());

// Which row to update, based on the title
        String where = FormsTable.COLUMN_ID + " = ?";
        String[] whereArgs = {id};

        int count = db.update(
                FormsTable.TABLE_NAME,
                values,
                where,
                whereArgs);
    }

    public void updateSyncedChildForms(String id) {
        SQLiteDatabase db = this.getReadableDatabase();

// New value for one column
        ContentValues values = new ContentValues();
        values.put(ChildTable.COLUMN_SYNCED, true);
        values.put(ChildTable.COLUMN_SYNCED_DATE, new Date().toString());

// Which row to update, based on the title
        String where = ChildTable._ID + " = ?";
        String[] whereArgs = {id};

        int count = db.update(
                ChildTable.TABLE_NAME,
                values,
                where,
                whereArgs);
    }
}