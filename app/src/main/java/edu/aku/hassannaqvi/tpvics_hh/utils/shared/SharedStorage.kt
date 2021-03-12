package edu.aku.hassannaqvi.tpvics_hh.utils.shared

import android.content.Context
import edu.aku.hassannaqvi.tpvics_hh.CONSTANTS.Companion.BACKUP_DT
import edu.aku.hassannaqvi.tpvics_hh.CONSTANTS.Companion.DOWNLOAD_FILE_REFID
import edu.aku.hassannaqvi.tpvics_hh.CONSTANTS.Companion.LAST_DATA_DOWNLOAD
import edu.aku.hassannaqvi.tpvics_hh.CONSTANTS.Companion.LAST_DATA_UPLOAD
import edu.aku.hassannaqvi.tpvics_hh.CONSTANTS.Companion.LAST_PHOTO_UPLOAD
import edu.aku.hassannaqvi.tpvics_hh.CONSTANTS.Companion.TAG_NAME
import org.apache.commons.lang3.StringUtils

/*
* @author Ali.Azaz
* */
object SharedStorage : SharedStorageBase() {

    fun setLastPhotoUpload(context: Context, dt: String) {
        put(context, LAST_PHOTO_UPLOAD, dt)
    }

    fun getLastPhotoUpload(context: Context): String {
        return get(context, LAST_PHOTO_UPLOAD, "Never Uploaded") as String
    }

    fun setLastDataUpload(context: Context, dt: String) {
        put(context, LAST_DATA_UPLOAD, dt)
    }

    fun getLastDataUpload(context: Context): String {
        return get(context, LAST_DATA_UPLOAD, "Never Uploaded") as String
    }

    fun setLastDataDownload(context: Context, dt: String) {
        put(context, LAST_DATA_DOWNLOAD, dt)
    }

    fun getLastDataDownload(context: Context): String {
        return get(context, LAST_DATA_DOWNLOAD, "Never Downloaded") as String
    }

    fun setTagName(context: Context, tag: String) {
        put(context, TAG_NAME, tag)
    }

    fun getTagName(context: Context): String {
        return get(context, TAG_NAME, StringUtils.EMPTY) as String
    }

    fun setBackUpDTFolder(context: Context, tag: String) {
        put(context, BACKUP_DT, tag)
    }

    fun getBackUpDTFolder(context: Context): String {
        return get(context, BACKUP_DT, StringUtils.EMPTY) as String
    }

    fun setDownloadFileRefID(context: Context, refID: Long) {
        put(context, DOWNLOAD_FILE_REFID, refID)
    }

    fun getDownloadFileRefID(context: Context): Long {
        return get(context, DOWNLOAD_FILE_REFID, 0) as Long
    }

}