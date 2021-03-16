package edu.aku.hassannaqvi.tpvics_hh.utils.shared

import android.content.Context
import android.content.Context.MODE_PRIVATE

/*
* @author Mustufa.Ansari
* @update Ali.Azaz
* */
open class SharedStorageBase {

    fun put(context: Context, key: String?, objectValue: Any?) {
        val mSharedPreferences = context.getSharedPreferences(context.applicationContext.packageName, MODE_PRIVATE)
        val editor = mSharedPreferences.edit()
        when (objectValue) {
            is String -> editor.putString(key, objectValue)
            is Int -> editor.putInt(key, objectValue)
            is Long -> editor.putLong(key, objectValue)
            is Boolean -> editor.putBoolean(key, objectValue)
            is Float -> editor.putFloat(key, objectValue)
            else -> editor.putString(key, objectValue.toString())
        }
        editor.apply()
    }

    operator fun get(context: Context, key: String?, defaultObject: Any?): Any? {
        val mSharedPreferences = context.getSharedPreferences(context.applicationContext.packageName, MODE_PRIVATE)
        return when (defaultObject) {
            is String -> mSharedPreferences.getString(key, defaultObject)
            is Int -> mSharedPreferences.getInt(key, defaultObject)
            is Long -> mSharedPreferences.getLong(key, defaultObject)
            is Boolean -> mSharedPreferences.getBoolean(key, defaultObject)
            is Float -> mSharedPreferences.getFloat(key, defaultObject)
            else -> null
        }
    }

}