package edu.aku.hassannaqvi.tpvics_hh.repository

import android.content.Context
import edu.aku.hassannaqvi.tpvics_hh.contracts.ChildContract
import edu.aku.hassannaqvi.tpvics_hh.contracts.FormsContract
import edu.aku.hassannaqvi.tpvics_hh.core.DatabaseHelper
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

suspend fun getAllHHChildFromDB(context: Context, form: FormsContract): MutableList<ChildContract> = withContext(Dispatchers.IO) {
    val db = DatabaseHelper(context)
    return@withContext db.getFilledChildForms(form.clusterCode, form.hhno, form._UID)
}

fun populatingChild(context: Context, form: FormsContract) {
    GlobalScope.launch {
        val def = getAllHHChildFromDB(context, form)
    }
}