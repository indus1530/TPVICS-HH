package edu.aku.hassannaqvi.tpvics_hh.ui.sections

import android.content.Intent
import android.os.Bundle
import android.view.View
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
    private lateinit var mainVModel: MainVModel
    private var serial = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bi = DataBindingUtil.setContentView(this, R.layout.activity_section_sub_info)
        bi.callback = this
        mainVModel = this.run {
            ViewModelProvider(this).get(MainVModel::class.java)
        }
        mainVModel.populateChildListU5(this, MainApp.fc)
        mainVModel.childU5.observe(this, Observer {
            serial = it.size + 1
        })

    }

    override fun onResume() {
        super.onResume()
        setUI()
    }

    fun onHHViewClick() {
        startActivity(Intent(this, SectionHHActivity::class.java))
    }

    fun onChildViewClick() {
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
                bi.btnHHView.isEnabled = false
                bi.btnChildView.isEnabled = true
                flagNewForm = false

                bi.hhInstruct.visibility = View.GONE
                bi.childInstruct.visibility = View.VISIBLE
            }
            "" -> {
                bi.btnHHView.isEnabled = true
                bi.btnChildView.isEnabled = false
                flagNewForm = true

                bi.hhInstruct.visibility = View.VISIBLE
                bi.childInstruct.visibility = View.GONE
            }
            else -> {
                bi.btnHHView.isEnabled = false
                bi.btnChildView.isEnabled = false
                flagNewForm = false
                flagInCompleteForm = true

                bi.hhInstruct.visibility = View.GONE
                bi.childInstruct.visibility = View.GONE
            }
        }
    }

    override fun endSecActivity(flag: Boolean) {
        finish()
        startActivity(Intent(this, EndingActivity::class.java).putExtra("complete", flag).putExtra(SUB_INFO_END_FLAG, true)
                .setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK))
    }
}
