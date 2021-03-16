package edu.aku.hassannaqvi.tpvics_hh.viewmodel

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import edu.aku.hassannaqvi.tpvics_hh.models.ChildContract
import edu.aku.hassannaqvi.tpvics_hh.models.FormsContract
import edu.aku.hassannaqvi.tpvics_hh.repository.getAllHHChildFromDB
import kotlinx.coroutines.launch

class MainVModel : ViewModel() {

    private var _childU5: MutableLiveData<MutableList<ChildContract>> = MutableLiveData()

    val childU5: MutableLiveData<MutableList<ChildContract>>
        get() = _childU5

    // Functions for child viewmodel
    fun setChildListU5(item: ChildContract) {
        var lst = _childU5.value
        if (lst.isNullOrEmpty())
            lst = mutableListOf()
        lst.add(item)
        _childU5.value = lst
    }

    fun populateChildListU5(context: Context, form: FormsContract) {
        viewModelScope.launch {
            var lst = getAllHHChildFromDB(context, form)
            if (lst.isNullOrEmpty()) lst = mutableListOf()
            _childU5.value = lst
        }
    }

}