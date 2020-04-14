package edu.aku.hassannaqvi.tpvics_hh.utils;

import edu.aku.hassannaqvi.tpvics_hh.contracts.BLRandomContract.SingleRandomHH;
import edu.aku.hassannaqvi.tpvics_hh.contracts.ChildContract.SingleChild;
import edu.aku.hassannaqvi.tpvics_hh.contracts.EnumBlockContract;
import edu.aku.hassannaqvi.tpvics_hh.contracts.FamilyMembersContract;
import edu.aku.hassannaqvi.tpvics_hh.contracts.FormsContract;
import edu.aku.hassannaqvi.tpvics_hh.contracts.KishMWRAContract.SingleKishMWRA;
import edu.aku.hassannaqvi.tpvics_hh.contracts.MWRAContract.MWRATable;
import edu.aku.hassannaqvi.tpvics_hh.contracts.MWRA_PREContract.SingleMWRAPRE;
import edu.aku.hassannaqvi.tpvics_hh.contracts.MortalityContract.SingleMortality;
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
            + FormsContract.FormsTable.COLUMN_UID + " TEXT,"
            + FormsContract.FormsTable.COLUMN_FORMDATE + " TEXT,"
            + FormsContract.FormsTable.COLUMN_APPVERSION + " TEXT,"
            + FormsContract.FormsTable.COLUMN_CLUSTERCODE + " TEXT,"
            + FormsContract.FormsTable.COLUMN_HHNO + " TEXT,"
            + FormsContract.FormsTable.COLUMN_FORMTYPE + " TEXT,"
            + FormsContract.FormsTable.COLUMN_LUID + " TEXT,"
            + FormsContract.FormsTable.COLUMN_USER + " TEXT,"
            + FormsContract.FormsTable.COLUMN_SINFO + " TEXT,"
            + FormsContract.FormsTable.COLUMN_SE + " TEXT,"
            + FormsContract.FormsTable.COLUMN_SM + " TEXT,"
            + FormsContract.FormsTable.COLUMN_SN + " TEXT,"
            + FormsContract.FormsTable.COLUMN_SO + " TEXT,"
            + FormsContract.FormsTable.COLUMN_ISTATUS + " TEXT,"
            + FormsContract.FormsTable.COLUMN_ISTATUS88x + " TEXT,"
            + FormsContract.FormsTable.COLUMN_ENDINGDATETIME + " TEXT,"
            + FormsContract.FormsTable.COLUMN_GPSLAT + " TEXT,"
            + FormsContract.FormsTable.COLUMN_GPSLNG + " TEXT,"
            + FormsContract.FormsTable.COLUMN_GPSDATE + " TEXT,"
            + FormsContract.FormsTable.COLUMN_GPSACC + " TEXT,"
            + FormsContract.FormsTable.COLUMN_DEVICEID + " TEXT,"
            + FormsContract.FormsTable.COLUMN_DEVICETAGID + " TEXT,"
            + FormsContract.FormsTable.COLUMN_SYNCED + " TEXT,"
            + FormsContract.FormsTable.COLUMN_SYNCED_DATE + " TEXT"
            + " );";

    public static final String SQL_CREATE_USERS = "CREATE TABLE " + UsersContract.singleUser.TABLE_NAME + "("
            + UsersContract.singleUser._ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + UsersContract.singleUser.ROW_USERNAME + " TEXT,"
            + UsersContract.singleUser.ROW_PASSWORD + " TEXT,"
            + UsersContract.singleUser.DIST_ID + " TEXT"
            + " );";

    public static final String SQL_CREATE_VERSIONAPP = "CREATE TABLE " + VersionAppContract.VersionAppTable.TABLE_NAME + " (" +
            VersionAppContract.VersionAppTable._ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
            VersionAppContract.VersionAppTable.COLUMN_VERSION_CODE + " TEXT, " +
            VersionAppContract.VersionAppTable.COLUMN_VERSION_NAME + " TEXT, " +
            VersionAppContract.VersionAppTable.COLUMN_PATH_NAME + " TEXT " +
            ");";
/*
    public static final String SQL_CREATE_TALUKAS = "CREATE TABLE " + TalukasContract.singleTalukas.TABLE_NAME + "("
            + TalukasContract.singleTalukas._ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + TalukasContract.singleTalukas.COLUMN_TALUKA_CODE + " TEXT,"
            + TalukasContract.singleTalukas.COLUMN_TALUKA + " TEXT );";


    public static final String SQL_CREATE_UCS = "CREATE TABLE " + UCsContract.singleUCs.TABLE_NAME + "("
            + UCsContract.singleUCs._ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + UCsContract.singleUCs.COLUMN_UCCODE + " TEXT,"
            + UCsContract.singleUCs.COLUMN_TALUKA_CODE + " TEXT,"
            + UCsContract.singleUCs.COLUMN_UCS + " TEXT );";


    public static final String SQL_CREATE_AREAS = "CREATE TABLE " + AreasContract.singleAreas.TABLE_NAME + "("
            + AreasContract.singleAreas._ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + AreasContract.singleAreas.COLUMN_AREACODE + " TEXT,"
            + AreasContract.singleAreas.COLUMN_UC_CODE + " TEXT,"
            + AreasContract.singleAreas.COLUMN_AREA + " TEXT );";*/

    public static final String SQL_CREATE_BL_RANDOM = "CREATE TABLE " + SingleRandomHH.TABLE_NAME + "("
            + SingleRandomHH.COLUMN_ID + " TEXT,"
            + SingleRandomHH.COLUMN_ENUM_BLOCK_CODE + " TEXT,"
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
            EnumBlockContract.EnumBlockTable.COLUMN_GEO_AREA + " TEXT, " +
            EnumBlockContract.EnumBlockTable.COLUMN_CLUSTER_AREA + " TEXT " +
            ");";

    public static final String SQL_CREATE_KISH_TABLE = "CREATE TABLE " + SingleKishMWRA.TABLE_NAME + "("
            + SingleKishMWRA._ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + SingleKishMWRA.COLUMN_UID + " TEXT,"
            + SingleKishMWRA.COLUMN__UUID + " TEXT,"
            + SingleKishMWRA.COLUMN_DEVICEID + " TEXT,"
            + SingleKishMWRA.COLUMN_FORMDATE + " TEXT,"
            + SingleKishMWRA.COLUMN_USER + " TEXT,"
            + SingleKishMWRA.COLUMN_SF + " TEXT,"
            + SingleKishMWRA.COLUMN_SG + " TEXT,"
            + SingleKishMWRA.COLUMN_SH1 + " TEXT,"
            + SingleKishMWRA.COLUMN_SH2 + " TEXT,"
            + SingleKishMWRA.COLUMN_SK + " TEXT,"
            + SingleKishMWRA.COLUMN_SL + " TEXT,"
            + SingleKishMWRA.COLUMN_DEVICETAGID + " TEXT,"
            + SingleKishMWRA.COLUMN_SYNCED + " TEXT,"
            + SingleKishMWRA.COLUMN_SYNCED_DATE + " TEXT );";

    public static final String SQL_CREATE_MWRA_TABLE = "CREATE TABLE " + MWRATable.TABLE_NAME + "("
            + MWRATable._ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + MWRATable.COLUMN_UID + " TEXT,"
            + MWRATable.COLUMN_UUID + " TEXT,"
            + MWRATable.COLUMN_FORMDATE + " TEXT,"
            + MWRATable.COLUMN_DEVICEID + " TEXT,"
            + MWRATable.COLUMN_USER + " TEXT,"
            + MWRATable.COLUMN_SE1 + " TEXT,"
            + MWRATable.COLUMN_DEVICETAGID + " TEXT,"
            + MWRATable.COLUMN_SYNCED + " TEXT,"
            + MWRATable.COLUMN_SYNCED_DATE + " TEXT );";

    public static final String SQL_CREATE_MWRAPRE_TABLE = "CREATE TABLE " + SingleMWRAPRE.TABLE_NAME + "("
            + SingleMWRAPRE._ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + SingleMWRAPRE.COLUMN_UID + " TEXT,"
            + SingleMWRAPRE.COLUMN__UUID + " TEXT,"
            + SingleMWRAPRE.COLUMN_FORMDATE + " TEXT,"
            + SingleMWRAPRE.COLUMN_DEVICEID + " TEXT,"
            + SingleMWRAPRE.COLUMN_USER + " TEXT,"
            + SingleMWRAPRE.COLUMN_SE2 + " TEXT,"
            + SingleMWRAPRE.COLUMN_DEVICETAGID + " TEXT,"
            + SingleMWRAPRE.COLUMN_SYNCED + " TEXT,"
            + SingleMWRAPRE.COLUMN_SYNCED_DATE + " TEXT );";

    public static final String SQL_CREATE_CHILD_TABLE = "CREATE TABLE " + SingleChild.TABLE_NAME + "("
            + SingleChild._ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + SingleChild.COLUMN_UID + " TEXT,"
            + SingleChild.COLUMN__UUID + " TEXT,"
            + SingleChild.COLUMN_DEVICEID + " TEXT,"
            + SingleChild.COLUMN_FORMDATE + " TEXT,"
            + SingleChild.COLUMN_USER + " TEXT,"
            + SingleChild.COLUMN_SI1 + " TEXT,"
            + SingleChild.COLUMN_SI2 + " TEXT,"
            + SingleChild.COLUMN_SJ + " TEXT,"
            + SingleChild.COLUMN_DEVICETAGID + " TEXT,"
            + SingleChild.COLUMN_SYNCED + " TEXT,"
            + SingleChild.COLUMN_SYNCED_DATE + " TEXT );";


    public static final String SQL_CREATE_MORTALITY = "CREATE TABLE " + SingleMortality.TABLE_NAME + "("
            + SingleMortality._ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + SingleMortality.COLUMN_UID + " TEXT,"
            + SingleMortality.COLUMN__UUID + " TEXT,"
            + SingleMortality.COLUMN_DEVICEID + " TEXT,"
            + SingleMortality.COLUMN_FORMDATE + " TEXT,"
            + SingleMortality.COLUMN_USER + " TEXT,"
            + SingleMortality.COLUMN_SE3 + " TEXT,"
            + SingleMortality.COLUMN_DEVICETAGID + " TEXT,"
            + SingleMortality.COLUMN_SYNCED + " TEXT,"
            + SingleMortality.COLUMN_SYNCED_DATE + " TEXT );";


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
