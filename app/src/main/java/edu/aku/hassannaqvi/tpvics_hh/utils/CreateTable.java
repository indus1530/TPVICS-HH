package edu.aku.hassannaqvi.tpvics_hh.utils;

import edu.aku.hassannaqvi.tpvics_hh.contracts.BLRandomContract.SingleRandomHH;
import edu.aku.hassannaqvi.tpvics_hh.contracts.ChildContract.ChildTable;
import edu.aku.hassannaqvi.tpvics_hh.contracts.EnumBlockContract;
import edu.aku.hassannaqvi.tpvics_hh.contracts.FamilyMembersContract;
import edu.aku.hassannaqvi.tpvics_hh.contracts.FormsContract;
import edu.aku.hassannaqvi.tpvics_hh.contracts.UsersContract;
import edu.aku.hassannaqvi.tpvics_hh.contracts.VersionAppContract;

public final class CreateTable {

    public static final String DATABASE_NAME = "tpvics_hh.db";
    public static final String DB_NAME = "tpvics_hh_copy.db";
    public static final String PROJECT_NAME = "TPVICS_HH";
    public static final int DATABASE_VERSION = 1;

    public static final String SQL_CREATE_FORMS = "CREATE TABLE "
            + FormsContract.FormsTable.TABLE_NAME + "("
            + FormsContract.FormsTable.COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + FormsContract.FormsTable.COLUMN_PROJECT_NAME + " TEXT,"
            + FormsContract.FormsTable.COLUMN_DEVICEID + " TEXT,"
            + FormsContract.FormsTable.COLUMN_DEVICETAGID + " TEXT,"
            + FormsContract.FormsTable.COLUMN_USER + " TEXT,"
            + FormsContract.FormsTable.COLUMN_UID + " TEXT,"
            + FormsContract.FormsTable.COLUMN_LUID + " TEXT,"
            + FormsContract.FormsTable.COLUMN_GPSLAT + " TEXT,"
            + FormsContract.FormsTable.COLUMN_GPSLNG + " TEXT,"
            + FormsContract.FormsTable.COLUMN_GPSDATE + " TEXT,"
            + FormsContract.FormsTable.COLUMN_GPSACC + " TEXT,"
            + FormsContract.FormsTable.COLUMN_FORMDATE + " TEXT,"
            + FormsContract.FormsTable.COLUMN_APPVERSION + " TEXT,"
            + FormsContract.FormsTable.COLUMN_CLUSTERCODE + " TEXT,"
            + FormsContract.FormsTable.COLUMN_HHNO + " TEXT,"
            + FormsContract.FormsTable.COLUMN_FORMTYPE + " TEXT,"
            + FormsContract.FormsTable.COLUMN_SINFO + " TEXT,"
            + FormsContract.FormsTable.COLUMN_SE + " TEXT,"
            + FormsContract.FormsTable.COLUMN_SM + " TEXT,"
            + FormsContract.FormsTable.COLUMN_SN + " TEXT,"
            + FormsContract.FormsTable.COLUMN_SO + " TEXT,"
            + FormsContract.FormsTable.COLUMN_FSTATUS + " TEXT,"
            + FormsContract.FormsTable.COLUMN_FSTATUS88x + " TEXT,"
            + FormsContract.FormsTable.COLUMN_ENDINGDATETIME + " TEXT,"
            + FormsContract.FormsTable.COLUMN_ISTATUS + " TEXT,"
            + FormsContract.FormsTable.COLUMN_ISTATUS88x + " TEXT,"
            + FormsContract.FormsTable.COLUMN_SYNCED + " TEXT,"
            + FormsContract.FormsTable.COLUMN_SYNCED_DATE + " TEXT"
            + " );";

    public static final String SQL_CREATE_USERS = "CREATE TABLE " + UsersContract.SingleUser.TABLE_NAME + "("
            + UsersContract.SingleUser._ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + UsersContract.SingleUser.ROW_USERNAME + " TEXT,"
            + UsersContract.SingleUser.ROW_PASSWORD + " TEXT,"
            + UsersContract.SingleUser.DIST_ID + " TEXT"
            + " );";

    public static final String SQL_CREATE_VERSIONAPP = "CREATE TABLE " + VersionAppContract.VersionAppTable.TABLE_NAME + " (" +
            VersionAppContract.VersionAppTable._ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
            VersionAppContract.VersionAppTable.COLUMN_VERSION_CODE + " TEXT, " +
            VersionAppContract.VersionAppTable.COLUMN_VERSION_NAME + " TEXT, " +
            VersionAppContract.VersionAppTable.COLUMN_PATH_NAME + " TEXT " +
            ");";

    public static final String SQL_CREATE_BL_RANDOM = "CREATE TABLE " + SingleRandomHH.TABLE_NAME + "("
            + SingleRandomHH.COLUMN_ID + " TEXT,"
            + SingleRandomHH.COLUMN_P_CODE + " TEXT,"
            + SingleRandomHH.COLUMN_EB_CODE + " TEXT,"
            + SingleRandomHH.COLUMN_LUID + " TEXT,"
            + SingleRandomHH.COLUMN_HH + " TEXT,"
            + SingleRandomHH.COLUMN_STRUCTURE_NO + " TEXT,"
            + SingleRandomHH.COLUMN_FAMILY_EXT_CODE + " TEXT,"
            + SingleRandomHH.COLUMN_HH_HEAD + " TEXT,"
            + SingleRandomHH.COLUMN_CONTACT + " TEXT,"
            + SingleRandomHH.COLUMN_HH_SELECTED_STRUCT + " TEXT,"
            + SingleRandomHH.COLUMN_RANDOMDT + " TEXT,"
            + SingleRandomHH.COLUMN_SNO_HH + " TEXT );";

    public static final String SQL_CREATE_PSU_TABLE = "CREATE TABLE " + EnumBlockContract.EnumBlockTable.TABLE_NAME + " (" +
            EnumBlockContract.EnumBlockTable._ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
            EnumBlockContract.EnumBlockTable.COLUMN_DIST_ID + " TEXT, " +
            EnumBlockContract.EnumBlockTable.COLUMN_ENUM_BLOCK_CODE + " TEXT, " +
            EnumBlockContract.EnumBlockTable.COLUMN_GEO_AREA + " TEXT, " +
            EnumBlockContract.EnumBlockTable.COLUMN_CLUSTER_AREA + " TEXT " +
            ");";

    public static final String SQL_CREATE_CHILD_TABLE = "CREATE TABLE " + ChildTable.TABLE_NAME + "("
            + ChildTable._ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + ChildTable.COLUMN_DEVICEID + " TEXT,"
            + ChildTable.COLUMN_DEVICETAGID + " TEXT,"
            + ChildTable.COLUMN_USER + " TEXT,"
            + ChildTable.COLUMN_UID + " TEXT,"
            + ChildTable.COLUMN_UUID + " TEXT,"
            + ChildTable.COLUMN_FORMDATE + " TEXT,"
            + ChildTable.COLUMN_SCA + " TEXT,"
            + ChildTable.COLUMN_SCB + " TEXT,"
            + ChildTable.COLUMN_SCC + " TEXT,"
            + ChildTable.COLUMN_SYNCED + " TEXT,"
            + ChildTable.COLUMN_SYNCED_DATE + " TEXT,"

            + ChildTable.COLUMN_CHILDNAME + " TEXT,"
            + ChildTable.COLUMN_CHILDSERIAL + " TEXT,"
            + ChildTable.COLUMN_GENDER + " TEXT,"
            + ChildTable.COLUMN_AGEY + " TEXT,"
            + ChildTable.COLUMN_AGEM + " TEXT,"
            + ChildTable.COLUMN_CLUSTERCODE + " TEXT,"
            + ChildTable.COLUMN_HHNO + " TEXT,"
            + ChildTable.COLUMN_CSTATUS + " TEXT,"
            + ChildTable.COLUMN_CSTATUS88x + " TEXT );";

    public static final String SQL_CREATE_FAMILY_MEMBERS = "CREATE TABLE " + FamilyMembersContract.SingleMember.TABLE_NAME + "("
            + FamilyMembersContract.SingleMember.COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
            FamilyMembersContract.SingleMember.COLUMN_UID + " TEXT," +
            FamilyMembersContract.SingleMember.COLUMN_UUID + " TEXT," +
            FamilyMembersContract.SingleMember.COLUMN_FORMDATE + " TEXT," +
            FamilyMembersContract.SingleMember.COLUMN_CLUSTERNO + " TEXT," +
            FamilyMembersContract.SingleMember.COLUMN_HHNO + " TEXT," +
            FamilyMembersContract.SingleMember.COLUMN_SERIAL_NO + " TEXT," +
            FamilyMembersContract.SingleMember.COLUMN_NAME + " TEXT," +
            FamilyMembersContract.SingleMember.COLUMN_FATHER_NAME + " TEXT," +
            FamilyMembersContract.SingleMember.COLUMN_AGE + " TEXT," +
            FamilyMembersContract.SingleMember.COLUMN_MONTH_FM + " TEXT," +
            FamilyMembersContract.SingleMember.COLUMN_MOTHER_NAME + " TEXT," +
            FamilyMembersContract.SingleMember.COLUMN_GENDER + " TEXT," +
            FamilyMembersContract.SingleMember.COLUMN_SD + " TEXT," +
            FamilyMembersContract.SingleMember.COLUMN_SYNCED + " TEXT," +
            FamilyMembersContract.SingleMember.COLUMN_SYNCED_DATE + " TEXT"
            + ");";

}
