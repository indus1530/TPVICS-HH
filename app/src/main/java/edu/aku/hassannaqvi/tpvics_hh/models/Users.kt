package edu.aku.hassannaqvi.tpvics_hh.models

import android.database.Cursor
import org.apache.commons.lang3.StringUtils
import org.json.JSONException
import org.json.JSONObject

/**
 * Created by hassan.naqvi.
 */
class Users {
    var userID: Long = 0
    var userName: String = StringUtils.EMPTY
    var password: String = StringUtils.EMPTY
    var distID: String = StringUtils.EMPTY

    @Throws(JSONException::class)
    fun sync(jsonObject: JSONObject): Users {
        userName = jsonObject.getString(UserTable.COLUMN_USERNAME)
        password = jsonObject.getString(UserTable.COLUMN_PASSWORD)
        distID = jsonObject.getString(UserTable.COLUMN_DIST_ID)
        return this
    }

    fun hydrate(cursor: Cursor): Users {
        userID = cursor.getLong(cursor.getColumnIndex(UserTable.COLUMN_ID))
        userName = cursor.getString(cursor.getColumnIndex(UserTable.COLUMN_USERNAME))
        password = cursor.getString(cursor.getColumnIndex(UserTable.COLUMN_PASSWORD))
        distID = cursor.getString(cursor.getColumnIndex(UserTable.COLUMN_DIST_ID))
        return this
    }

    object UserTable {
        const val TABLE_NAME = "users"
        const val COLUMN_ID = "id"
        const val COLUMN_USERNAME = "username"
        const val COLUMN_PASSWORD = "password"
        const val COLUMN_DIST_ID = "dist_id"
    }
}