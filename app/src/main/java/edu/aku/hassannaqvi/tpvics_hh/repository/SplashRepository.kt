package edu.aku.hassannaqvi.tpvics_hh.repository

import android.content.Context
import edu.aku.hassannaqvi.tpvics_hh.core.DatabaseHelper
import edu.aku.hassannaqvi.tpvics_hh.ui.other.SplashscreenActivity.Companion.districts
import edu.aku.hassannaqvi.tpvics_hh.ui.other.SplashscreenActivity.Companion.provinces
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

private suspend fun getEnumList(context: Context) = withContext(Dispatchers.IO) {
    val db = DatabaseHelper(context)
    return@withContext db.enumBlock.map { it.geoarea.split("|").subList(0, 2) }
}

suspend fun getEnumData(context: Context): MutableMap<String, String> {
    val enumLst = getEnumList(context)
    val splitLst: MutableMap<String, String> = mutableMapOf()
    enumLst.forEach { item ->
        splitLst[item[1]] = item[0]
    }
    return splitLst
}

suspend fun setProvinceDistricts(def: MutableMap<String, String>) {
    def.entries.forEach { item ->
        if (provinces.any { it != item.value }) provinces.toMutableList().add(item.value)
    }
    districts = def
}

