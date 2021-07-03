package edu.aku.hassannaqvi.tpvics_hh.database

import edu.aku.hassannaqvi.tpvics_hh.models.*
import edu.aku.hassannaqvi.tpvics_hh.models.BLRandom.BLRandomHHTable
import edu.aku.hassannaqvi.tpvics_hh.models.ChildContract.ChildTable
import edu.aku.hassannaqvi.tpvics_hh.utils.shared.Keys.apiKey

object CreateTable {
    const val PROJECT_NAME = "TPVICS_HH"
    const val DATABASE_NAME = "asSa%s|n'\$ crEed"
    const val DATABASE_PASSWORD = "$PROJECT_NAME.db"
    const val DATABASE_COPY = "${PROJECT_NAME}_copy.db"
    const val DATABASE_VERSION = 1

    const val SQL_CREATE_FORMS = ("CREATE TABLE "
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
            + FormsContract.FormsTable.COLUMN_SYSDATE + " TEXT,"
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
            + " );")

    const val SQL_CREATE_USERS = ("CREATE TABLE " + Users.UserTable.TABLE_NAME + "("
            + Users.UserTable.COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + Users.UserTable.COLUMN_USERNAME + " TEXT,"
            + Users.UserTable.COLUMN_PASSWORD + " TEXT,"
            + Users.UserTable.COLUMN_DIST_ID + " TEXT"
            + " );")

    const val SQL_CREATE_VERSIONAPP = "CREATE TABLE " + VersionApp.VersionAppTable.TABLE_NAME + " (" +
            VersionApp.VersionAppTable.COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
            VersionApp.VersionAppTable.COLUMN_VERSION_CODE + " TEXT, " +
            VersionApp.VersionAppTable.COLUMN_VERSION_NAME + " TEXT, " +
            VersionApp.VersionAppTable.COLUMN_PATH_NAME + " TEXT " +
            ");"

    const val SQL_CREATE_BL_RANDOM = ("CREATE TABLE " + BLRandomHHTable.TABLE_NAME + "("
            + BLRandomHHTable.COLUMN_ID + " TEXT,"
            + BLRandomHHTable.COLUMN_P_CODE + " TEXT,"
            + BLRandomHHTable.COLUMN_EB_CODE + " TEXT,"
            + BLRandomHHTable.COLUMN_LUID + " TEXT,"
            + BLRandomHHTable.COLUMN_HH + " TEXT,"
            + BLRandomHHTable.COLUMN_STRUCTURE_NO + " TEXT,"
            + BLRandomHHTable.COLUMN_FAMILY_EXT_CODE + " TEXT,"
            + BLRandomHHTable.COLUMN_HH_HEAD + " TEXT,"
            + BLRandomHHTable.COLUMN_CONTACT + " TEXT,"
            + BLRandomHHTable.COLUMN_HH_SELECTED_STRUCT + " TEXT,"
            + BLRandomHHTable.COLUMN_RANDOMDT + " TEXT,"
            + BLRandomHHTable.COLUMN_SNO_HH + " TEXT );")

    const val SQL_CREATE_PSU_TABLE = "CREATE TABLE " + Clusters.ClusterTable.TABLE_NAME + " (" +
            Clusters.ClusterTable._ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
            Clusters.ClusterTable.COLUMN_DIST_CODE + " TEXT, " +
            Clusters.ClusterTable.COLUMN_ENUM_BLOCK_CODE + " TEXT, " +
            Clusters.ClusterTable.COLUMN_GEO_AREA + " TEXT, " +
            Clusters.ClusterTable.COLUMN_CLUSTER_AREA + " TEXT " +
            ");"

    const val SQL_CREATE_CHILD_TABLE = ("CREATE TABLE " + ChildTable.TABLE_NAME + "("
            + ChildTable._ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + ChildTable.COLUMN_DEVICEID + " TEXT,"
            + ChildTable.COLUMN_DEVICETAGID + " TEXT,"
            + ChildTable.COLUMN_USER + " TEXT,"
            + ChildTable.COLUMN_UID + " TEXT,"
            + ChildTable.COLUMN_UUID + " TEXT,"
            + ChildTable.COLUMN_FORMDATE + " TEXT,"
            + ChildTable.COLUMN_SYSDATE + " TEXT,"
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
            + ChildTable.COLUMN_CSTATUS88x + " TEXT );")

    const val SQL_CREATE_DISTRICTS = ("CREATE TABLE " + Districts.DistrictTable.TABLE_NAME + "("
            + Districts.DistrictTable.COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + Districts.DistrictTable.COLUMN_DIST_ID + " TEXT,"
            + Districts.DistrictTable.COLUMN_DIST_NAME + " TEXT,"
            + Districts.DistrictTable.COLUMN_PROVINCE_NAME + " TEXT );")
}