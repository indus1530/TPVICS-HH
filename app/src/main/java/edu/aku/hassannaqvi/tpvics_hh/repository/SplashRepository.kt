package edu.aku.hassannaqvi.tpvics_hh.repository

import android.content.Context
import edu.aku.hassannaqvi.tpvics_hh.core.DatabaseHelper
import edu.aku.hassannaqvi.tpvics_hh.ui.other.SplashscreenActivity.Companion.districtsMap
import edu.aku.hassannaqvi.tpvics_hh.ui.other.SplashscreenActivity.Companion.provinces
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

private suspend fun getEnumGeoArea(context: Context) = withContext(Dispatchers.IO) {
    val db = DatabaseHelper(context)
    return@withContext db.enumBlock.map { it.geoarea.split("|").subList(0, 2) }
}

suspend fun getEnumData(context: Context): MutableMap<String, String> {
    val enumLst = getEnumGeoArea(context)
    val splitLst: MutableMap<String, String> = mutableMapOf()
    enumLst.forEach { item ->
        splitLst[item[1]] = item[0]
    }
    return splitLst
}

private suspend fun getEnumContract(context: Context, province: String, district: String) = withContext(Dispatchers.IO) {
    val db = DatabaseHelper(context)
    return@withContext db.enumBlock.find { it.geoarea.split("|").subList(0, 2)[1] == district }
}

suspend fun setProvinceDistricts(context: Context, def: MutableMap<String, String>) {
    def.entries.forEach { item ->
        if (!provinces.contains(item.value)) provinces.add(item.value)
        getEnumContract(context, item.value, item.key)?.let { districtsMap[item.key] = Pair(item.value, it) }
    }
}

suspend fun populatingSpinners(context: Context) {
    GlobalScope.launch {
        val def = withContext(Dispatchers.Main) { getEnumData(context) }
        if (def.isNotEmpty())
            withContext(Dispatchers.Main) { setProvinceDistricts(context, def) }
    }
}

