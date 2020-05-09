package edu.aku.hassannaqvi.tpvics_hh.ui.sections

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import edu.aku.hassannaqvi.tpvics_hh.CONSTANTS.Companion.CHILD_SERIAL
import edu.aku.hassannaqvi.tpvics_hh.CONSTANTS.Companion.SUB_INFO_END_FLAG
import edu.aku.hassannaqvi.tpvics_hh.R
import edu.aku.hassannaqvi.tpvics_hh.core.MainApp
import edu.aku.hassannaqvi.tpvics_hh.databinding.ActivitySectionSubInfoBinding
import edu.aku.hassannaqvi.tpvics_hh.ui.other.EndingActivity
import edu.aku.hassannaqvi.tpvics_hh.utils.EndSectionActivity
import edu.aku.hassannaqvi.tpvics_hh.utils.contextEndActivity
import edu.aku.hassannaqvi.tpvics_hh.viewmodel.MainVModel
import ru.whalemare.sheetmenu.ActionItem
import ru.whalemare.sheetmenu.SheetMenu
import ru.whalemare.sheetmenu.layout.GridLayoutProvider

class SectionSubInfoActivity : AppCompatActivity(), EndSectionActivity {

    private lateinit var bi: ActivitySectionSubInfoBinding
    private var flagNewForm = false
    private var flagInCompleteForm = false
    private var serial = 0
    private var hhFlag = false
    private var childFlag = false
    private lateinit var mainVModel: MainVModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bi = DataBindingUtil.setContentView(this, R.layout.activity_section_sub_info)
        bi.callback = this
        mainVModel = this.run {
            ViewModelProvider(this).get(MainVModel::class.java)
        }

        /*runBlocking {
            launch(Dispatchers.Main) {
                bi.txtCluster.text = MainApp.fc.clusterCode
                bi.txtHHNo.text = MainApp.fc.hhno
            }
        }*/

    }

    override fun onResume() {
        super.onResume()
        setUI()

        mainVModel.populateChildListU5(this, MainApp.fc)
        mainVModel.childU5.observe(this, Observer {
            serial = it.size + 1
        })
    }

    fun onHHViewClick() {
        if (!hhFlag) return
        startActivity(Intent(this, SectionHHActivity::class.java))
    }

    fun onChildViewClick() {
        if (!childFlag) return
        startActivity(Intent(this, SectionCHAActivity::class.java).putExtra(CHILD_SERIAL, serial))
    }

    fun onFabBtnMenuClick() {
        SheetMenu(
                "Select your Action",
                listOf(
                        ActionItem(
                                0,
                                "Force Stop",
                                getDrawable(R.drawable.ic_warning_black_24dp)
                        )
                        ,
                        ActionItem(
                                1,
                                "Partial Complete",
                                getDrawable(R.drawable.ic_battery_black_24dp)
                        )
                        ,
                        ActionItem(
                                2,
                                "End Household",
                                getDrawable(R.drawable.ic_closed_caption_black_24dp)
                        )
                ),
                onClick = { item: ActionItem ->
                    run {
                        when (item.id) {
                            0 -> {
                                if (flagNewForm || flagInCompleteForm) return@run
                                contextEndActivity(this, false)
                            }
                            1 -> {
                                if (flagNewForm) return@run
                                onBackPressed()
                            }
                            else -> {
                                if (flagNewForm) return@run
                                contextEndActivity(this)
                            }
                        }
                    }

                }, layoutProvider = GridLayoutProvider()
        ).show(this)

    }

    private fun setUI() {
        when (MainApp.fc.getfStatus()) {
            "1" -> {
                hhFlag = false
                childFlag = true
                flagNewForm = false
                bi.instruction.text = getString(R.string.childforminfo)
            }
            "" -> {
                hhFlag = true
                childFlag = false
                flagNewForm = true
                bi.instruction.text = getString(R.string.hhformInfo)
            }
            else -> {
                hhFlag = false
                childFlag = false
                flagNewForm = false
                flagInCompleteForm = true
                bi.instruction.text = getString(R.string.end_interview)
            }
        }
    }

    override fun endSecActivity(flag: Boolean) {
        finish()
        startActivity(Intent(this, EndingActivity::class.java).putExtra("complete", flag).putExtra(SUB_INFO_END_FLAG, true)
                .setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK))
    }
}
