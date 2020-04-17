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
import edu.aku.hassannaqvi.tpvics_hh.databinding.ActivitySectionChABinding;
import edu.aku.hassannaqvi.tpvics_hh.ui.other.EndingActivity;
import edu.aku.hassannaqvi.tpvics_hh.utils.Util;

public class SectionCHAActivity extends AppCompatActivity {

    ActivitySectionChABinding bi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        bi = DataBindingUtil.setContentView(this, R.layout.activity_section_ch_a);
        bi.setCallback(this);

        setupListeners();
    }

    private void setupListeners() {

        bi.uf14.setOnCheckedChangeListener((radioGroup, i) -> {
            if (i == bi.uf14a.getId())
                Clear.clearAllFields(bi.fldGrpCVuf15);
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

        f1.put("uf09", bi.uf09.getText().toString());
        f1.put("uf9a", bi.uf9a.getText().toString());
        f1.put("uf9b",
                bi.uf9b1.isChecked() ? "1" :
                        bi.uf9b2.isChecked() ? "2" :
                                bi.uf9b98.isChecked() ? "98" :
                                        "0");

        f1.put("uf14",
                bi.uf14a.isChecked() ? "1" :
                        bi.uf14b.isChecked() ? "2" :
                                "0");

        f1.put("uf15",
                bi.uf15a.isChecked() ? "1" :
                        bi.uf15b.isChecked() ? "2" :
                                "0");

    }

    private boolean formValidation() {
        return Validator.emptyCheckingContainer(this, bi.fldGrpSectionCHA);
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

        Util.openEndActivity(this);
    }

    @Override
    public void onBackPressed() {
        Toast.makeText(this, "Press top back button.", Toast.LENGTH_SHORT).show();
    }

}
