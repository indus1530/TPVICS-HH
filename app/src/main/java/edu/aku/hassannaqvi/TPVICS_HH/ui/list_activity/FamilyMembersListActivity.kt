package edu.aku.hassannaqvi.TPVICS_HH.ui.list_activity

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import edu.aku.hassannaqvi.TPVICS_HH.CONSTANTS
import edu.aku.hassannaqvi.TPVICS_HH.CONSTANTS.Companion.SERIAL_EXTRA
import edu.aku.hassannaqvi.TPVICS_HH.R
import edu.aku.hassannaqvi.TPVICS_HH.adapter.FamilyMemberListAdapter
import edu.aku.hassannaqvi.TPVICS_HH.contracts.FamilyMembersContract
import edu.aku.hassannaqvi.TPVICS_HH.core.MainApp
import edu.aku.hassannaqvi.TPVICS_HH.core.MainApp.openDialog
import edu.aku.hassannaqvi.TPVICS_HH.databinding.ActivityFamilyMembersListBinding
import edu.aku.hassannaqvi.TPVICS_HH.databinding.ItemMemListBinding
import edu.aku.hassannaqvi.TPVICS_HH.otherClasses.KishGrid
import edu.aku.hassannaqvi.TPVICS_HH.ui.sections.SectionDActivity
import edu.aku.hassannaqvi.TPVICS_HH.ui.sections.SectionE1Activity
import edu.aku.hassannaqvi.TPVICS_HH.ui.sections.SectionE3Activity
import edu.aku.hassannaqvi.TPVICS_HH.utils.Util
import edu.aku.hassannaqvi.TPVICS_HH.viewmodel.MainVModel
import kotlinx.android.synthetic.main.activity_family_members_list.*
import ru.whalemare.sheetmenu.ActionItem
import ru.whalemare.sheetmenu.SheetMenu
import ru.whalemare.sheetmenu.layout.GridLayoutProvider

class FamilyMembersListActivity : AppCompatActivity() {

    private var serial = 1
    private var memSelectedCounter = 0
    private lateinit var adapter: FamilyMemberListAdapter
    private lateinit var bi: ActivityFamilyMembersListBinding
    private var viewHolder: ItemMemListBinding? = null
    private var currentFM: FamilyMembersContract? = null
    private lateinit var clickLst: MutableList<FamilyMembersContract>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_family_members_list)
        setSupportActionBar(toolbar)

        bi = DataBindingUtil.setContentView(this, R.layout.activity_family_members_list)
        bi.callback = this

        settingValue()
        settingMenu()

        clickLst = mutableListOf()
    }

    private fun settingMenu() {

        fabMenu.setOnClickListener {
            SheetMenu(
                    "Select your Action",
                    listOf(
                            ActionItem(
                                    0,
                                    "Add Member",
                                    getDrawable(R.drawable.ic_account_circle_black_24dp)
                            )
                            ,
                            ActionItem(
                                    1,
                                    "Next Section",
                                    getDrawable(R.drawable.ic_keyboard_arrow_right_black_24dp)
                            )
                            ,
                            ActionItem(
                                    2,
                                    "End Activity",
                                    getDrawable(R.drawable.ic_closed_caption_black_24dp)
                            )
                    ),
                    onClick = { item: ActionItem ->
                        run {
                            when (item.id) {
                                0 -> {
                                    startActivityForResult(Intent(this, SectionDActivity::class.java).putExtra(SERIAL_EXTRA, serial), CONSTANTS.MEMBER_ITEM)
                                }
                                1 -> {
                                    if (memSelectedCounter == 0) return@run

                                    if (memSelectedCounter != serial - 1) return@run

                                    MainApp.pragnantWoman = mainVModel.getAllWomenName()

                                    MainApp.selectedKishMWRA = mainVModel.mwraChildU5Lst.value?.get(kishSelectedMWRA(intent.getIntExtra("sno", 0), mainVModel.mwraChildU5Lst.value!!.size) - 1)

                                    if (MainApp.selectedKishMWRA != null) {
                                        val childLst = mainVModel.getAllChildrenOfSelMWRA(MainApp.selectedKishMWRA.serialno.toInt())
                                        MainApp.indexKishMWRAChild = childLst?.let {
                                            childLst[kishSelectedMWRA(intent.getIntExtra("sno", 0),
                                                    childLst.size) - 1]
                                        }
                                    }

                                    finish()
                                    startActivity(Intent(this, if (bi.contentScroll.mwra.text.toString().toInt() > 0) SectionE1Activity::class.java else SectionE3Activity::class.java))
                                }
                                else -> Util.openEndActivity(this)
                            }
                        }

                    }, layoutProvider = GridLayoutProvider()
            ).show(this)

        }
        toolbar_layout.title = "Household FamilyMembers"
        toolbar_layout.setExpandedTitleTextAppearance(R.style.expandCollapse)
    }

    private fun settingValue() {
        mainVModel = this.run {
            ViewModelProviders.of(this)[MainVModel::class.java]
        }
        mainVModel.childLstU5.observe(this, Observer { item -> bi.contentScroll.under5.text = String.format("%02d", item.size) })
        mainVModel.mwraLst.observe(this, Observer { item -> bi.contentScroll.mwra.text = String.format("%02d", item.size) })
        mainVModel.familyMemLst.observe(this, Observer { item ->
            bi.contentScroll.total.text = String.format("%02d", item.size)
            adapter.setMList(item)
        })
        setupRecyclerView(mutableListOf())
    }

    private fun setupRecyclerView(membersLst: MutableList<FamilyMembersContract>) {
        adapter = FamilyMemberListAdapter(this, membersLst, mainVModel)
        bi.contentScroll.recyclerView.layoutManager = LinearLayoutManager(this)
        bi.contentScroll.recyclerView.adapter = adapter
        adapter.setItemClicked { item, position ->
            openDialog(this, item)
            MainApp.setItemClick {

                currentFM = item

                startActivityForResult(Intent(this, SectionDActivity::class.java)
                        .putExtra(SERIAL_EXTRA, item.serialno.toInt()), CONSTANTS.MEMBER_ITEM)

            }

        }

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == CONSTANTS.MEMBER_ITEM) {
            when (resultCode) {
                Activity.RESULT_OK -> {
                    data?.let { serial = data.getIntExtra(SERIAL_EXTRA, 0) } ?: handlingHolder()
                }
                Activity.RESULT_CANCELED -> {
                }
            }
        }
    }

    private fun handlingHolder() {
        memSelectedCounter++
        currentFM?.let {
            mainVModel.setCheckedItemValues(currentFM!!.serialno.toInt())
        }

    }

    private fun kishSelectedMWRA(sno: Int, size: Int): Int {
        return KishGrid.kishGridProcess(sno, size)
    }

    companion object {
        lateinit var mainVModel: MainVModel
    }

    override fun onBackPressed() {
        Toast.makeText(this, "Press top back button.", Toast.LENGTH_SHORT).show()
    }
}
