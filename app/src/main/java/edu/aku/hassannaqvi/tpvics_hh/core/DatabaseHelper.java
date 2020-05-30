package edu.aku.hassannaqvi.tpvics_hh.core;

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

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import edu.aku.hassannaqvi.tpvics_hh.contracts.AreasContract.singleAreas;
import edu.aku.hassannaqvi.tpvics_hh.contracts.BLRandomContract;
import edu.aku.hassannaqvi.tpvics_hh.contracts.BLRandomContract.SingleRandomHH;
import edu.aku.hassannaqvi.tpvics_hh.contracts.ChildContract;
import edu.aku.hassannaqvi.tpvics_hh.contracts.ChildContract.ChildTable;
import edu.aku.hassannaqvi.tpvics_hh.contracts.EnumBlockContract;
import edu.aku.hassannaqvi.tpvics_hh.contracts.EnumBlockContract.EnumBlockTable;
import edu.aku.hassannaqvi.tpvics_hh.contracts.FamilyMembersContract;
import edu.aku.hassannaqvi.tpvics_hh.contracts.FamilyMembersContract.SingleMember;
import edu.aku.hassannaqvi.tpvics_hh.contracts.FormsContract;
import edu.aku.hassannaqvi.tpvics_hh.contracts.FormsContract.FormsTable;
import edu.aku.hassannaqvi.tpvics_hh.contracts.TalukasContract;
import edu.aku.hassannaqvi.tpvics_hh.contracts.UCsContract;
import edu.aku.hassannaqvi.tpvics_hh.contracts.UsersContract;
import edu.aku.hassannaqvi.tpvics_hh.contracts.VersionAppContract;
import edu.aku.hassannaqvi.tpvics_hh.contracts.VillagesContract.SingleVillage;

import static edu.aku.hassannaqvi.tpvics_hh.utils.CreateTable.DATABASE_NAME;
import static edu.aku.hassannaqvi.tpvics_hh.utils.CreateTable.DATABASE_VERSION;
import static edu.aku.hassannaqvi.tpvics_hh.utils.CreateTable.SQL_CREATE_BL_RANDOM;
import static edu.aku.hassannaqvi.tpvics_hh.utils.CreateTable.SQL_CREATE_CHILD_TABLE;
import static edu.aku.hassannaqvi.tpvics_hh.utils.CreateTable.SQL_CREATE_FAMILY_MEMBERS;
import static edu.aku.hassannaqvi.tpvics_hh.utils.CreateTable.SQL_CREATE_FORMS;
import static edu.aku.hassannaqvi.tpvics_hh.utils.CreateTable.SQL_CREATE_PSU_TABLE;
import static edu.aku.hassannaqvi.tpvics_hh.utils.CreateTable.SQL_CREATE_USERS;
import static edu.aku.hassannaqvi.tpvics_hh.utils.CreateTable.SQL_CREATE_VERSIONAPP;


/**
 * Created by hassan.naqvi on 11/30/2016.
 */

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String SQL_DELETE_VILLAGES = "DROP TABLE IF EXISTS " + SingleVillage.TABLE_NAME;
    private static final String SQL_DELETE_TALUKAS = "DROP TABLE IF EXISTS " + TalukasContract.singleTalukas.TABLE_NAME;
    private static final String SQL_DELETE_UCS = "DROP TABLE IF EXISTS " + UCsContract.singleUCs.TABLE_NAME;
    private static final String SQL_DELETE_AREAS = "DROP TABLE IF EXISTS " + singleAreas.TABLE_NAME;
    private final String TAG = "DatabaseHelper";
    private String spDateT = new SimpleDateFormat("dd-MM-yy").format(new Date().getTime());

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
        db.execSQL(SQL_CREATE_FAMILY_MEMBERS);
        db.execSQL(SQL_CREATE_CHILD_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
    }

    public int syncEnumBlocks(JSONArray enumList) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(EnumBlockContract.EnumBlockTable.TABLE_NAME, null, null);
        int insertCount = 0;

        JSONArray jsonArray = enumList;
        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject jsonObjectCC = null;
            try {
                jsonObjectCC = jsonArray.getJSONObject(i);
            } catch (JSONException e) {
                e.printStackTrace();
            }

            EnumBlockContract Vc = new EnumBlockContract();
            try {
                Vc.Sync(jsonObjectCC);
            } catch (JSONException e) {
                e.printStackTrace();
            }

            ContentValues values = new ContentValues();

            values.put(EnumBlockContract.EnumBlockTable.COLUMN_DIST_ID, Vc.getDist_code());
            values.put(EnumBlockTable.COLUMN_ENUM_BLOCK_CODE, Vc.getEbcode());
            values.put(EnumBlockContract.EnumBlockTable.COLUMN_GEO_AREA, Vc.getGeoarea());
            values.put(EnumBlockContract.EnumBlockTable.COLUMN_CLUSTER_AREA, Vc.getCluster());

            db.insert(EnumBlockContract.EnumBlockTable.TABLE_NAME, null, values);
            long rowID = db.insert(EnumBlockContract.EnumBlockTable.TABLE_NAME, null, values);
            if (rowID != -1) insertCount++;
        }
        return insertCount;
    }

    public int syncBLRandom(JSONArray blList) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(SingleRandomHH.TABLE_NAME, null, null);

        JSONArray jsonArray = blList;
        int insertCount = 0;
        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject jsonObjectCC = null;
            try {
                jsonObjectCC = jsonArray.getJSONObject(i);
            } catch (JSONException e) {
                e.printStackTrace();
            }

            BLRandomContract Vc = new BLRandomContract();
            try {
                Vc.Sync(jsonObjectCC);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            Log.d(TAG, "syncBLRandom: " + Vc.get_ID());
            ContentValues values = new ContentValues();

            values.put(SingleRandomHH.COLUMN_ID, Vc.get_ID());
            values.put(SingleRandomHH.COLUMN_LUID, Vc.getLUID());
            values.put(SingleRandomHH.COLUMN_STRUCTURE_NO, Vc.getStructure());
            values.put(SingleRandomHH.COLUMN_FAMILY_EXT_CODE, Vc.getExtension());
            values.put(SingleRandomHH.COLUMN_HH, Vc.getHh());
            values.put(SingleRandomHH.COLUMN_EB_CODE, Vc.getEbcode());
            values.put(SingleRandomHH.COLUMN_P_CODE, Vc.getpCode());
            values.put(SingleRandomHH.COLUMN_RANDOMDT, Vc.getRandomDT());
            values.put(SingleRandomHH.COLUMN_HH_HEAD, Vc.getHhhead());
            values.put(SingleRandomHH.COLUMN_CONTACT, Vc.getContact());
            values.put(SingleRandomHH.COLUMN_HH_SELECTED_STRUCT, Vc.getSelStructure());
            values.put(SingleRandomHH.COLUMN_SNO_HH, Vc.getSno());

            long row = db.insert(SingleRandomHH.TABLE_NAME, null, values);
            if (row != -1) insertCount++;
        }
        return insertCount;
    }

    public void syncVersionApp(JSONArray VersionList) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(VersionAppContract.VersionAppTable.TABLE_NAME, null, null);
        try {
            JSONArray jsonArray = VersionList;
            JSONObject jsonObjectCC = jsonArray.getJSONObject(0);

            VersionAppContract Vc = new VersionAppContract();
            Vc.Sync(jsonObjectCC);

            ContentValues values = new ContentValues();

            values.put(VersionAppContract.VersionAppTable.COLUMN_PATH_NAME, Vc.getPathname());
            values.put(VersionAppContract.VersionAppTable.COLUMN_VERSION_CODE, Vc.getVersioncode());
            values.put(VersionAppContract.VersionAppTable.COLUMN_VERSION_NAME, Vc.getVersionname());

            db.insert(VersionAppContract.VersionAppTable.TABLE_NAME, null, values);
        } catch (Exception e) {
        } finally {
            db.close();
        }
    }

    public VersionAppContract getVersionApp() {

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = null;
        String[] columns = {
                VersionAppContract.VersionAppTable._ID,
                VersionAppContract.VersionAppTable.COLUMN_VERSION_CODE,
                VersionAppContract.VersionAppTable.COLUMN_VERSION_NAME,
                VersionAppContract.VersionAppTable.COLUMN_PATH_NAME
        };

        String whereClause = null;
        String[] whereArgs = null;
        String groupBy = null;
        String having = null;

        String orderBy = null;

        VersionAppContract allVC = new VersionAppContract();
        try {
            c = db.query(
                    VersionAppContract.VersionAppTable.TABLE_NAME,  // The table to query
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

    public int syncUser(JSONArray userList) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(UsersContract.singleUser.TABLE_NAME, null, null);
        int insertCount = 0;
        try {
            JSONArray jsonArray = userList;
            for (int i = 0; i < jsonArray.length(); i++) {

                JSONObject jsonObjectUser = jsonArray.getJSONObject(i);

                UsersContract user = new UsersContract();
                user.Sync(jsonObjectUser);
                ContentValues values = new ContentValues();

                values.put(UsersContract.singleUser.ROW_USERNAME, user.getUserName());
                values.put(UsersContract.singleUser.ROW_PASSWORD, user.getPassword());
                values.put(UsersContract.singleUser.DIST_ID, user.getDIST_ID());
                long rowID = db.insert(UsersContract.singleUser.TABLE_NAME, null, values);
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

    public boolean Login(String username, String password) throws SQLException {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor mCursor = db.rawQuery("SELECT * FROM " + UsersContract.singleUser.TABLE_NAME + " WHERE " + UsersContract.singleUser.ROW_USERNAME + "=? AND " + UsersContract.singleUser.ROW_PASSWORD + "=?", new String[]{username, password});
        if (mCursor != null) {

            /*if (mCursor.moveToFirst()) {
                    MainApp.DIST_ID = mCursor.getString(mCursor.getColumnIndex(UsersContract.singleUser.DIST_ID));
                }*/
            return mCursor.getCount() > 0;
        }
        return false;
    }

    public Long addForm(FormsContract fc) {

        // Gets the data repository in write mode
        SQLiteDatabase db = this.getWritableDatabase();

// Create a new map of values, where column names are the keys
        ContentValues values = new ContentValues();
        values.put(FormsTable.COLUMN_PROJECT_NAME, fc.getProjectName());
        values.put(FormsTable.COLUMN_UID, fc.get_UID());
        values.put(FormsTable.COLUMN_FORMDATE, fc.getFormDate());
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

    public Long addFamilyMember(FamilyMembersContract fmc) {

        // Gets the data repository in write mode
        SQLiteDatabase db = this.getWritableDatabase();

// Create a new map of values, where column names are the keys
        ContentValues values = new ContentValues();
        values.put(SingleMember.COLUMN_ID, fmc.get_id());
        values.put(SingleMember.COLUMN_UID, fmc.getUid());
        values.put(SingleMember.COLUMN_UUID, fmc.getUuid());
        values.put(SingleMember.COLUMN_FORMDATE, fmc.getFormdate());
        values.put(SingleMember.COLUMN_CLUSTERNO, fmc.getClusterno());
        values.put(SingleMember.COLUMN_HHNO, fmc.getHhno());
        values.put(SingleMember.COLUMN_SERIAL_NO, fmc.getSerialno());
        values.put(SingleMember.COLUMN_NAME, fmc.getName());
        values.put(SingleMember.COLUMN_FATHER_NAME, fmc.getFatherName());
        values.put(SingleMember.COLUMN_AGE, fmc.getAge());
        values.put(SingleMember.COLUMN_MONTH_FM, fmc.getMonthfm());
        values.put(SingleMember.COLUMN_MOTHER_NAME, fmc.getMotherName());
        values.put(SingleMember.COLUMN_GENDER, fmc.getGender());
        values.put(SingleMember.COLUMN_SD, fmc.getsD());

        // Insert the new row, returning the primary key value of the new row
        long newRowId;
        newRowId = db.insert(
                SingleMember.TABLE_NAME,
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

    public FormsContract isDataExists(String studyId) {
        SQLiteDatabase db = getReadableDatabase();
        Cursor c = null;

// New value for one column
        String[] columns = {
                FormsTable.COLUMN_LUID,
                FormsTable.COLUMN_ISTATUS,

        };

// Which row to update, based on the ID
        String selection = FormsTable.COLUMN_LUID + " = ? AND "
                + FormsTable.COLUMN_ISTATUS + " = ?";
        String[] selectionArgs = new String[]{studyId, "1"};

        FormsContract allFC = new FormsContract();
        try {
            c = db.query(FormsTable.TABLE_NAME, //Table to query
                    columns,                    //columns to return
                    selection,                  //columns for the WHERE clause
                    selectionArgs,              //The values for the WHERE clause
                    null,                       //group the rows
                    null,                       //filter by row groups
                    null);                   // The sort order

            while (c.moveToNext()) {
                allFC.setLuid(c.getString(c.getColumnIndex(FormsTable.COLUMN_LUID)));
                allFC.setIstatus(c.getString(c.getColumnIndex(FormsTable.COLUMN_ISTATUS)));
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

    public int updateFormID() {
        SQLiteDatabase db = this.getReadableDatabase();

// New value for one column
        ContentValues values = new ContentValues();
        values.put(FormsTable.COLUMN_UID, MainApp.fc.get_UID());

// Which row to update, based on the ID
        String selection = FormsTable._ID + " = ?";
        String[] selectionArgs = {String.valueOf(MainApp.fc.get_ID())};

        int count = db.update(FormsTable.TABLE_NAME,
                values,
                selection,
                selectionArgs);
        return count;
    }

    public Collection<FormsContract> getAllForms() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = null;
        String[] columns = {
                FormsTable._ID,
                FormsTable.COLUMN_UID,
                FormsTable.COLUMN_FORMDATE,
                FormsTable.COLUMN_USER,
                FormsTable.COLUMN_ISTATUS,
                FormsTable.COLUMN_SINFO,
                FormsTable.COLUMN_FSTATUS,
                FormsTable.COLUMN_GPSLAT,
                FormsTable.COLUMN_GPSLNG,
                FormsTable.COLUMN_GPSDATE,
                FormsTable.COLUMN_GPSACC,
                FormsTable.COLUMN_DEVICETAGID,
                FormsTable.COLUMN_DEVICEID,
                FormsTable.COLUMN_APPVERSION,
                FormsTable.COLUMN_CLUSTERCODE,
                FormsTable.COLUMN_HHNO,
                FormsTable.COLUMN_FORMTYPE,

        };
        String whereClause = null;
        String[] whereArgs = null;
        String groupBy = null;
        String having = null;

        String orderBy =
                FormsTable.COLUMN_ID + " ASC";

        Collection<FormsContract> allFC = new ArrayList<FormsContract>();
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

    public Collection<FamilyMembersContract> getAllFamilyMembersForms() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = null;
        String[] columns = {
                SingleMember.COLUMN_ID,
                SingleMember.COLUMN_UID,
                SingleMember.COLUMN_UUID,
                SingleMember.COLUMN_FORMDATE,
                SingleMember.COLUMN_CLUSTERNO,
                SingleMember.COLUMN_HHNO,
                SingleMember.COLUMN_SERIAL_NO,
                SingleMember.COLUMN_NAME,
                SingleMember.COLUMN_FATHER_NAME,
                SingleMember.COLUMN_AGE,
                SingleMember.COLUMN_MONTH_FM,
                SingleMember.COLUMN_MOTHER_NAME,
                SingleMember.COLUMN_GENDER,
                SingleMember.COLUMN_SD,
        };
        String whereClause = SingleMember.COLUMN_SYNCED + " is null";
        String[] whereArgs = null;
        String groupBy = null;
        String having = null;

        String orderBy =
                FormsTable.COLUMN_ID + " ASC";

        Collection<FamilyMembersContract> allFC = new ArrayList<>();
        try {
            c = db.query(
                    SingleMember.TABLE_NAME,  // The table to query
                    columns,                   // The columns to return
                    whereClause,               // The columns for the WHERE clause
                    whereArgs,                 // The values for the WHERE clause
                    groupBy,                   // don't group the rows
                    having,                    // don't filter by row groups
                    orderBy                    // The sort order
            );
            while (c.moveToNext()) {
                FamilyMembersContract fc = new FamilyMembersContract();
                allFC.add(fc.hydrate(c));
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

    public Collection<FormsContract> checkFormExist() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = null;
        String[] columns = {
                FormsTable._ID,
                FormsTable.COLUMN_UID,
                FormsTable.COLUMN_FORMDATE,
                FormsTable.COLUMN_USER,
                FormsTable.COLUMN_ISTATUS,
                FormsTable.COLUMN_SINFO,
                FormsTable.COLUMN_FSTATUS,
                FormsTable.COLUMN_GPSLAT,
                FormsTable.COLUMN_GPSLNG,
                FormsTable.COLUMN_GPSDATE,
                FormsTable.COLUMN_GPSACC,
                FormsTable.COLUMN_DEVICETAGID,
                FormsTable.COLUMN_DEVICEID,
                FormsTable.COLUMN_APPVERSION,
                FormsTable.COLUMN_CLUSTERCODE,
                FormsTable.COLUMN_HHNO,
                FormsTable.COLUMN_FORMTYPE,

        };
        String whereClause = null;
        String[] whereArgs = null;
        String groupBy = null;
        String having = null;

        String orderBy =
                FormsTable.COLUMN_ID + " ASC";

        Collection<FormsContract> allFC = new ArrayList<FormsContract>();
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

    public Collection<FormsContract> getUnsyncedForms() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = null;
        String[] columns = {
                FormsTable._ID,
                FormsTable.COLUMN_UID,
                FormsTable.COLUMN_FORMDATE,
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


        String whereClause = FormsTable.COLUMN_SYNCED + " is null";

        String[] whereArgs = null;

        String groupBy = null;
        String having = null;

        String orderBy =
                FormsTable.COLUMN_ID + " ASC";

        Collection<FormsContract> allFC = new ArrayList<FormsContract>();
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

    public Collection<ChildContract> getUnsyncedChildForms() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = null;
        String[] columns = {
                ChildTable._ID,
                ChildTable.COLUMN_UID,
                ChildTable.COLUMN_UUID,
                ChildTable.COLUMN_DEVICEID,
                ChildTable.COLUMN_FORMDATE,
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


        String whereClause = ChildTable.COLUMN_SYNCED + " is null";

        String[] whereArgs = null;

        String groupBy = null;
        String having = null;

        String orderBy =
                ChildTable._ID + " ASC";

        Collection<ChildContract> allFC = new ArrayList<ChildContract>();
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
                ChildContract fc = new ChildContract();
                allFC.add(fc.hydrate(c));
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

    public Collection<FormsContract> getTodayForms() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = null;
        String[] columns = {
                FormsTable._ID,
                FormsTable.COLUMN_UID,
                FormsTable.COLUMN_FORMDATE,
                FormsTable.COLUMN_CLUSTERCODE,
                FormsTable.COLUMN_HHNO,
                FormsTable.COLUMN_ISTATUS,
                FormsTable.COLUMN_FSTATUS,
                FormsTable.COLUMN_SYNCED,

        };
        String whereClause = FormsTable.COLUMN_FORMDATE + " Like ? ";
        String[] whereArgs = new String[]{"%" + spDateT.substring(0, 8).trim() + "%"};
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

    public int getChildrenByUID(String UID) {
        String countQuery = "SELECT  * FROM " + ChildTable.TABLE_NAME + " WHERE " + ChildTable.COLUMN_UUID + " = '" + UID + "' AND " + ChildTable.COLUMN_CSTATUS + " = '1'";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        int count = cursor.getCount();
        cursor.close();
        return count;
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

    //Get BLRandom data
    public BLRandomContract getHHFromBLRandom(String subAreaCode, String hh) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = null;

        String[] columns = {
                SingleRandomHH.COLUMN_ID,
                SingleRandomHH.COLUMN_LUID,
                SingleRandomHH.COLUMN_STRUCTURE_NO,
                SingleRandomHH.COLUMN_FAMILY_EXT_CODE,
                SingleRandomHH.COLUMN_HH,
                SingleRandomHH.COLUMN_P_CODE,
                SingleRandomHH.COLUMN_EB_CODE,
                SingleRandomHH.COLUMN_RANDOMDT,
                SingleRandomHH.COLUMN_HH_SELECTED_STRUCT,
                SingleRandomHH.COLUMN_CONTACT,
                SingleRandomHH.COLUMN_HH_HEAD,
                SingleRandomHH.COLUMN_SNO_HH
        };

        String whereClause = SingleRandomHH.COLUMN_P_CODE + "=? AND " + SingleRandomHH.COLUMN_HH + "=?";
        String[] whereArgs = new String[]{subAreaCode, hh};
        String groupBy = null;
        String having = null;

        String orderBy =
                SingleRandomHH.COLUMN_ID + " ASC";

        BLRandomContract allBL = null;
        try {
            c = db.query(
                    SingleRandomHH.TABLE_NAME,  // The table to query
                    columns,                   // The columns to return
                    whereClause,               // The columns for the WHERE clause
                    whereArgs,                 // The values for the WHERE clause
                    groupBy,                   // don't group the rows
                    having,                    // don't filter by row groups
                    orderBy                    // The sort order
            );
            while (c.moveToNext()) {
                allBL = new BLRandomContract().hydrate(c);
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

    //Get EnumBlock
    public EnumBlockContract getEnumBlock(String cluster) {

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = null;
        String[] columns = {
                EnumBlockTable._ID,
                EnumBlockTable.COLUMN_DIST_ID,
                EnumBlockTable.COLUMN_ENUM_BLOCK_CODE,
                EnumBlockTable.COLUMN_GEO_AREA,
                EnumBlockTable.COLUMN_CLUSTER_AREA
        };

        String whereClause = EnumBlockTable.COLUMN_CLUSTER_AREA + " =?";
        String[] whereArgs = {cluster};

        String groupBy = null;
        String having = null;

        String orderBy = EnumBlockTable._ID + " ASC";
        EnumBlockContract allEB = null;
        try {
            c = db.query(
                    EnumBlockTable.TABLE_NAME,  // The table to query
                    columns,                   // The columns to return
                    whereClause,               // The columns for the WHERE clause
                    whereArgs,                 // The values for the WHERE clause
                    groupBy,                   // don't group the rows
                    having,                    // don't filter by row groups
                    orderBy                    // The sort order
            );
            while (c.moveToNext()) {
                allEB = new EnumBlockContract().HydrateEnum(c);
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

    //Get All EnumBlock
    public List<EnumBlockContract> getEnumBlock() {

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = null;
        String[] columns = {
                EnumBlockTable._ID,
                EnumBlockTable.COLUMN_DIST_ID,
                EnumBlockTable.COLUMN_ENUM_BLOCK_CODE,
                EnumBlockTable.COLUMN_GEO_AREA,
                EnumBlockTable.COLUMN_CLUSTER_AREA
        };

        String whereClause = null;
        String[] whereArgs = null;
        String groupBy = null;
        String having = null;

        String orderBy = EnumBlockTable._ID + " ASC";
        List<EnumBlockContract> allEB = new ArrayList<>();
        try {
            c = db.query(
                    EnumBlockTable.TABLE_NAME,  // The table to query
                    columns,                   // The columns to return
                    whereClause,               // The columns for the WHERE clause
                    whereArgs,                 // The values for the WHERE clause
                    groupBy,                   // don't group the rows
                    having,                    // don't filter by row groups
                    orderBy                    // The sort order
            );
            while (c.moveToNext()) {
                allEB.add(new EnumBlockContract().HydrateEnum(c));
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

    //Get All Child of HH
    public List<ChildContract> getFilledChildForms(String clusterCode, String hhNo, String uuid) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = null;
        String[] columns = {
                ChildTable._ID,
                ChildTable.COLUMN_UID,
                ChildTable.COLUMN_UUID,
                ChildTable.COLUMN_DEVICEID,
                ChildTable.COLUMN_FORMDATE,
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

    //Get Form already exist
    public FormsContract getFilledForm(String clusterCode, String hhNo) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = null;
        String[] columns = {
                FormsTable._ID,
                FormsTable.COLUMN_UID,
                FormsTable.COLUMN_FORMDATE,
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
        String orderBy = FormsTable._ID + " DESC";
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

    //Generic update FormColumn
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

    //Generic update FamilyMemberColumn
    public int updatesFamilyMemberColumn(String column, String value, String valueID) {
        SQLiteDatabase db = this.getReadableDatabase();

        ContentValues values = new ContentValues();
        values.put(column, value);

        String selection = SingleMember._ID + " =? ";
        String[] selectionArgs = {String.valueOf(valueID)};

        return db.update(SingleMember.TABLE_NAME,
                values,
                selection,
                selectionArgs);
    }

    //Generic update ChildColumn
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

    // ANDROID DATABASE MANAGER
    public ArrayList<Cursor> getData(String Query) {
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

    //Generic Un-Synced Children
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

    //Generic Un-Synced Forms
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

    public void updateSyncedFamilyMemForms(String id) {
        SQLiteDatabase db = this.getReadableDatabase();

// New value for one column
        ContentValues values = new ContentValues();
        values.put(SingleMember.COLUMN_SYNCED, true);
        values.put(SingleMember.COLUMN_SYNCED_DATE, new Date().toString());

// Which row to update, based on the title
        String where = SingleMember._ID + " = ?";
        String[] whereArgs = {id};

        int count = db.update(
                SingleMember.TABLE_NAME,
                values,
                where,
                whereArgs);
    }
}