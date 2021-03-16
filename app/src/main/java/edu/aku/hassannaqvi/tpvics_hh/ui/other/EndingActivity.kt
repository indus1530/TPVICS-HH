package edu.aku.hassannaqvi.tpvics_hh.ui.other

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.validatorcrawler.aliazaz.Validator
import edu.aku.hassannaqvi.tpvics_hh.CONSTANTS
import edu.aku.hassannaqvi.tpvics_hh.R
import edu.aku.hassannaqvi.tpvics_hh.core.MainApp
import edu.aku.hassannaqvi.tpvics_hh.databinding.ActivityEndingBinding
import edu.aku.hassannaqvi.tpvics_hh.ui.sections.SectionSubInfoActivity
import edu.aku.hassannaqvi.tpvics_hh.utils.JSONUtils
import org.json.JSONException
import org.json.JSONObject
import java.text.SimpleDateFormat
import java.util.*

class EndingActivity : AppCompatActivity() {
    lateinit var bi: ActivityEndingBinding
    private val subInfoEndActivityFlag by lazy {
        intent.getBooleanExtra(CONSTANTS.SUB_INFO_END_FLAG, false)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bi = DataBindingUtil.setContentView(this, R.layout.activity_ending)
        bi.callback = this

        val check = intent.getBooleanExtra("complete", false)
        val fStatus = intent.getStringExtra(CONSTANTS.FSTATUS_END_FLAG)
        val fStatusEndActivityFlag = fStatus != null
        if (check) {
            bi.istatusa.isEnabled = true
            bi.istatusb.isEnabled = fStatusEndActivityFlag
            bi.istatusc.isEnabled = fStatusEndActivityFlag
            bi.istatusd.isEnabled = fStatusEndActivityFlag
            bi.istatuse.isEnabled = fStatusEndActivityFlag
            bi.istatusf.isEnabled = fStatusEndActivityFlag
            bi.istatusg.isEnabled = fStatusEndActivityFlag
            bi.istatus96.isEnabled = fStatusEndActivityFlag
        } else {
            bi.istatusa.isEnabled = false
            if (fStatus == null) {
                bi.istatusb.isEnabled = true
                bi.istatusc.isEnabled = true
                bi.istatusd.isEnabled = true
                bi.istatuse.isEnabled = true
                bi.istatusf.isEnabled = true
                bi.istatusg.isEnabled = true
                bi.istatus96.isEnabled = true
            } else {
                bi.istatusb.isEnabled = false
                bi.istatusc.isEnabled = false
                bi.istatusd.isEnabled = false
                bi.istatuse.isEnabled = false
                bi.istatusf.isEnabled = false
                bi.istatusg.isEnabled = false
                bi.istatus96.isEnabled = false
                when (fStatus.toInt()) {
                    1 -> {
                        bi.istatusb.isEnabled = true
                        bi.istatusc.isEnabled = true
                        bi.istatusd.isEnabled = true
                        bi.istatuse.isEnabled = true
                        bi.istatusf.isEnabled = true
                        bi.istatusg.isEnabled = true
                        bi.istatus96.isEnabled = true
                    }
                    2 -> bi.istatusb.isEnabled = true
                    3 -> bi.istatusc.isEnabled = true
                    4 -> bi.istatusd.isEnabled = true
                    5 -> bi.istatuse.isEnabled = true
                    6 -> bi.istatusf.isEnabled = true
                    7 -> bi.istatusg.isEnabled = true
                    96 -> bi.istatus96.isEnabled = true
                }
            }
        }
    }

    fun BtnEnd() {
        if (formValidation()) {
            saveDraft()
            if (updateDB()) {
                finish()
                if (subInfoEndActivityFlag) startActivity(Intent(this, MainActivity::class.java))
            } else {
                Toast.makeText(this, "Error in updating db!!", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun saveDraft() {
        val statusValue = when {
            bi.istatusa.isChecked -> "1"
            bi.istatusb.isChecked -> "2"
            bi.istatusc.isChecked -> "3"
            bi.istatusd.isChecked -> "4"
            bi.istatuse.isChecked -> "5"
            bi.istatusf.isChecked -> "6"
            bi.istatusg.isChecked -> "7"
            bi.istatus96.isChecked -> "96"
            else -> "0"
        }
        if (subInfoEndActivityFlag) {
            MainApp.fc.istatus = statusValue
            MainApp.fc.istatus88x = bi.istatus96x.text.toString()

            val json = JSONObject()
            json.put("ttChild", SectionSubInfoActivity.mainVModel.childU5.value?.size ?: 0)
            try {
                val json_merge = JSONUtils.mergeJSONObjects(JSONObject(MainApp.fc.getsInfo()), json)
                MainApp.fc.setsInfo(json_merge.toString())
            } catch (e: JSONException) {
                e.printStackTrace()
            }

        } else {
            MainApp.fc.endingdatetime = SimpleDateFormat("dd-MM-yy HH:mm", Locale.getDefault()).format(Date().time)
            MainApp.fc.setfStatus(statusValue)
            MainApp.fc.fstatus88x = bi.istatus96x.text.toString()
        }
    }

    private fun updateDB(): Boolean {
        val db = MainApp.appInfo.dbHelper
        val updcount = db.updateEnding(subInfoEndActivityFlag)
        return if (updcount == 1) {
            true
        } else {
            Toast.makeText(this, "Updating Database... ERROR!", Toast.LENGTH_SHORT).show()
            false
        }
    }

    private fun formValidation(): Boolean {
        return Validator.emptyCheckingContainer(this, bi.fldGrpEnd)
    }

    override fun onBackPressed() {
        Toast.makeText(applicationContext, "You Can't go back", Toast.LENGTH_LONG).show()
    }
}