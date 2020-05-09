package edu.aku.hassannaqvi.tpvics_hh.repository

import android.content.Context
import edu.aku.hassannaqvi.tpvics_hh.contracts.ChildContract
import edu.aku.hassannaqvi.tpvics_hh.contracts.FormsContract
import edu.aku.hassannaqvi.tpvics_hh.core.DatabaseHelper
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.withContext

suspend fun getAllHHChildFromDB(context: Context, form: FormsContract): MutableList<ChildContract> = withContext(Dispatchers.IO) {
    val db = DatabaseHelper(context)
    return@withContext db.getFilledChildForms(form.clusterCode, form.hhno, form._UID)
}

suspend fun populatingChild(context: Context, form: FormsContract) {
    coroutineScope {
        val def = getAllHHChildFromDB(context, form)
    }
}