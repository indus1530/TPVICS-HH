package edu.aku.hassannaqvi.tpvics_hh.ui.sections;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.validatorcrawler.aliazaz.Clear;
import com.validatorcrawler.aliazaz.Validator;

import org.json.JSONException;
import org.json.JSONObject;

import edu.aku.hassannaqvi.tpvics_hh.R;
import edu.aku.hassannaqvi.tpvics_hh.databinding.ActivitySectionChCBinding;
import edu.aku.hassannaqvi.tpvics_hh.ui.other.EndingActivity;

import static edu.aku.hassannaqvi.tpvics_hh.utils.UtilKt.openEndActivity;

public class SectionCHCActivity extends AppCompatActivity {

    ActivitySectionChCBinding bi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        bi = DataBindingUtil.setContentView(this, R.layout.activity_section_ch_c);
        bi.setCallback(this);
        setTitle(R.string.chsec);
        setupListeners();
    }

    private void setupListeners() {


        bi.im01.setOnCheckedChangeListener(((radioGroup, i) -> {
            if (i == bi.im011.getId()) {
                Clear.clearAllFields(bi.fldGrpCVim02, false);
                Clear.clearAllFields(bi.fldGrpCVim02a, false);
            } else if (i == bi.im012.getId()) {
                Clear.clearAllFields(bi.fldGrpCVim02, false);
                Clear.clearAllFields(bi.fldGrpCVim02a, true);
            } else {
                Clear.clearAllFields(bi.fldGrpCVim02, true);
                Clear.clearAllFields(bi.fldGrpCVim02a, true);
            }

        }));

        bi.im02.setOnCheckedChangeListener((radioGroup, i) -> {
            if (i == bi.im021.getId()) {
                Clear.clearAllFields(bi.fldGrpCVim02a, false);
//                Clear.clearAllFields(bi.fldGrpSecChc2, false);
            } else {
                Clear.clearAllFields(bi.fldGrpCVim02a, true);
//                Clear.clearAllFields(bi.fldGrpSecChc2, true);
            }

        });

    }

    private boolean UpdateDB() {
        /*DatabaseHelper db = MainApp.appInfo.getDbHelper();
        long updcount = db.addFamilyMember(fmc);
        fmc.set_id(String.valueOf(updcount));
        if (updcount > 0) {
            fmc.setUid(MainApp.deviceId + fmc.get_id());
            db.updatesFamilyMemberColumn(FamilyMembersContract.SingleMember.COLUMN_UID, fmc.getUid(), fmc.get_id());
            return true;
        } else {
            Toast.makeText(this, "Updating Database... ERROR!", Toast.LENGTH_SHORT).show();
        }*/
        return false;
    }

    private void SaveDraft() throws JSONException {

        JSONObject f1 = new JSONObject();

        f1.put("im01",
                bi.im011.isChecked() ? "1" :
                        bi.im012.isChecked() ? "2" :
                                bi.im013.isChecked() ? "3" :
                                        "0");
        f1.put("im02",
                bi.im021.isChecked() ? "1" :
                        bi.im022.isChecked() ? "2" :
                                "0");
        f1.put("im02a",
                bi.im02a1.isChecked() ? "1" :
                        bi.im02a2.isChecked() ? "2" :
                                bi.im02a3.isChecked() ? "3" :
                                        bi.im02a4.isChecked() ? "4" :
                                                bi.im02a5.isChecked() ? "5" :
                                                        bi.im02a6.isChecked() ? "6" :
                                                                bi.im02a96.isChecked() ? "96" :
                                                                        "0");

    }

    private boolean formValidation() {
        return Validator.emptyCheckingContainer(this, bi.fldGrpSectionCHC);
    }

    public void BtnContinue() {

        if (formValidation()) {
            try {
                SaveDraft();
            } catch (JSONException e) {
                e.printStackTrace();
            }
            if (UpdateDB()) {
                finish();
                startActivity(new Intent(this, EndingActivity.class).putExtra("complete", true));

            } else {
                Toast.makeText(this, "Failed to Update Database!", Toast.LENGTH_SHORT).show();
            }
        }

    }

    public void BtnEnd() {

        openEndActivity(this);
    }

    @Override
    public void onBackPressed() {
        Toast.makeText(this, "Press top back button.", Toast.LENGTH_SHORT).show();
    }

}
