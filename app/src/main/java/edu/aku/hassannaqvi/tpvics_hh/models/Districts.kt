package edu.aku.hassannaqvi.tpvics_hh.models

import android.database.Cursor
import org.apache.commons.lang3.StringUtils
import org.json.JSONException
import org.json.JSONObject

class Districts {

    var dist_id: String = StringUtils.EMPTY
    var district: String = StringUtils.EMPTY
    var province: String = StringUtils.EMPTY

    @Throws(JSONException::class)
    fun sync(jsonObject: JSONObject): Districts {
        dist_id = jsonObject.getString(DistrictTable.COLUMN_DIST_ID)
        district = jsonObject.getString(DistrictTable.COLUMN_DIST_NAME)
        province = jsonObject.getString(DistrictTable.COLUMN_PROVINCE_NAME)
        return this
    }

    fun hydrate(cursor: Cursor): Districts {
        dist_id = cursor.getString(cursor.getColumnIndex(DistrictTable.COLUMN_DIST_ID))
        district = cursor.getString(cursor.getColumnIndex(DistrictTable.COLUMN_DIST_NAME))
        province = cursor.getString(cursor.getColumnIndex(DistrictTable.COLUMN_PROVINCE_NAME))
        return this
    }

    object DistrictTable {
        const val TABLE_NAME = "districts"
        const val COLUMN_ID = "_id"
        const val COLUMN_DIST_ID = "dist_id"
        const val COLUMN_DIST_NAME = "district"
        const val COLUMN_PROVINCE_NAME = "province"
    }

}