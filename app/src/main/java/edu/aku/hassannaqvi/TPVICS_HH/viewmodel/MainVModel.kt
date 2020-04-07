package edu.aku.hassannaqvi.TPVICS_HH.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import edu.aku.hassannaqvi.TPVICS_HH.contracts.FamilyMembersContract

class MainVModel : ViewModel() {

    var familyMemLst = MutableLiveData<MutableList<FamilyMembersContract>>()
        private set

    var mwraLst = MutableLiveData<MutableList<FamilyMembersContract>>()
        private set

    var childLstU5 = MutableLiveData<MutableList<FamilyMembersContract>>()
        private set

    var mwraChildU5Lst = MutableLiveData<MutableList<FamilyMembersContract>>()
        private set

    var checkedItems = MutableLiveData<MutableList<Int>>()
        private set

    fun setFamilyMembers(item: FamilyMembersContract) {
        var lst = familyMemLst.value
        if (lst.isNullOrEmpty())
            lst = mutableListOf()
        lst.add(item)
        familyMemLst.value = lst
    }

    fun updateFamilyMembers(item: FamilyMembersContract) {
        val lst = familyMemLst.value
        lst?.map { if (it.serialno.toInt() == item.serialno.toInt()) item else it }
        familyMemLst.value = lst
    }

    fun setChildU5(item: FamilyMembersContract) {
        var lst = childLstU5.value
        if (lst.isNullOrEmpty())
            lst = mutableListOf()
        lst.add(item)
        childLstU5.value = lst
    }

    fun setMwraChildU5(item: FamilyMembersContract) {
        var lst = mwraChildU5Lst.value
        if (lst.isNullOrEmpty()) {
            lst = mutableListOf()
            lst.add(item)
        } else {
            val fmc = mwraChildU5Lst.value?.find { it.serialno.toInt() == item.serialno.toInt() }
            fmc?.let { lst.map { if (it.serialno.toInt() == fmc.serialno.toInt()) item else it } }
                    ?: lst.add(item)
        }
        mwraChildU5Lst.value = lst
    }

    fun setCheckedItemValues(index: Int) {
        var lst = checkedItems.value
        if (lst.isNullOrEmpty()) {
            lst = mutableListOf()
            lst.add(index)
        } else lst.add(index)

        checkedItems.value = lst
    }

    fun setMWRA(item: FamilyMembersContract) {
        var lst = mwraLst.value
        if (lst.isNullOrEmpty())
            lst = mutableListOf()
        lst.add(item)
        mwraLst.value = lst
    }

    fun getMemberInfo(index: Int): FamilyMembersContract? {
        return familyMemLst.value?.find { it.serialno.toInt() == index }
    }

    fun getAllMenWomenName(gender: Int, currentPersonSerial: Int): Pair<List<Int>?, List<String>?> {
        val family = familyMemLst.value?.filter { it -> it.age.toInt() >= 15 && it.marital.toInt() != 2 && it.gender.toInt() == gender && it.serialno.toInt() != currentPersonSerial }
        return Pair(family?.map { it.serialno.toInt() }, family?.map { it.name })
    }

    /* fun getAllWomenName(): Pair<List<Int>?, List<String>?> {
         val family = familyMemLst.value?.filter { it -> (it.age.toInt() in 15..50) && it.marital.toInt() != 2 && it.gender.toInt() == 2 }
         return Pair(family?.map { it.serialno.toInt() }, family?.map { it.name })
     }*/

    fun getAllWomenName(): Pair<List<Int>?, List<String>?> {
        val family = mwraLst.value
        return Pair(family?.map { it.serialno.toInt() }, family?.map { it.name })
    }

    fun getAllUnder5(): Pair<List<Int>?, List<String>?> {
        val family = familyMemLst.value?.filter { it -> (it.age.toInt() < 5) }
        return Pair(family?.map { it.serialno.toInt() }, family?.map { it.name })
    }

    fun getAllRespondent(): Pair<List<Int>?, List<String>?> {
        val family = familyMemLst.value?.filter { it -> (it.age.toInt() >= 15) }
        return Pair(family?.map { it.serialno.toInt() }, family?.map { it.name })
    }

    fun getCheckedItemValues(fmItem: Int): Boolean {
        val flag = checkedItems.value?.find { it == fmItem }
        flag?.let { return true } ?: return false
    }

    fun getAllChildrenOfSelMWRA(mwraSerial: Int): List<FamilyMembersContract>? {
        return childLstU5.value?.filter { it -> it.mother_serial.toInt() == mwraSerial }
    }


}