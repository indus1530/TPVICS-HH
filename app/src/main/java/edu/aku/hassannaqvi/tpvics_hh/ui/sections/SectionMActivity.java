package edu.aku.hassannaqvi.tpvics_hh.ui.sections;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.validatorcrawler.aliazaz.Clear;
import com.validatorcrawler.aliazaz.Validator;

import org.json.JSONException;
import org.json.JSONObject;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import edu.aku.hassannaqvi.tpvics_hh.R;
import edu.aku.hassannaqvi.tpvics_hh.contracts.FormsContract;
import edu.aku.hassannaqvi.tpvics_hh.core.DatabaseHelper;
import edu.aku.hassannaqvi.tpvics_hh.core.MainApp;
import edu.aku.hassannaqvi.tpvics_hh.databinding.ActivitySectionMBinding;
import edu.aku.hassannaqvi.tpvics_hh.utils.Util;

public class SectionMActivity extends AppCompatActivity {

    ActivitySectionMBinding bi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        bi = DataBindingUtil.setContentView(this, R.layout.activity_section_m);
        bi.setCallback(this);

        setUIComponents();
    }

    private void setUIComponents() {

        bi.m109.setOnCheckedChangeListener(((radioGroup, i) -> {

            if (i == bi.m109b.getId()) {
                Clear.clearAllFields(bi.fldGrpCVm110);
            }
        }));

        bi.m111.setOnCheckedChangeListener(((radioGroup, i) -> {
            if (i == bi.m111b.getId()) {
                Clear.clearAllFields(bi.fldGrpCVm112);
            }
        }));
    }

    public void BtnContinue() {
        if (formValidation()) {
            try {
                SaveDraft();
            } catch (Exception e) {
                e.printStackTrace();
            }
            if (UpdateDB()) {
                finish();
                startActivity(new Intent(this, SectionNActivity.class));
            } else {
                Toast.makeText(this, "Failed to Update Database!", Toast.LENGTH_SHORT).show();
            }

        }
    }

    private boolean UpdateDB() {

        DatabaseHelper db = MainApp.appInfo.getDbHelper();
        int updcount = db.updatesFormColumn(FormsContract.FormsTable.COLUMN_SM, MainApp.fc.getsM());
        if (updcount == 1) {
            return true;
        } else {
            Toast.makeText(this, "Updating Database... ERROR!", Toast.LENGTH_SHORT).show();
            return false;
        }

    }

    private void SaveDraft() throws JSONException {

        JSONObject json = new JSONObject();

        json.put("ss01", bi.ss01a.isChecked() ? "1"
                : bi.ss01b.isChecked() ? "2"
                : bi.ss01c.isChecked() ? "3"
                : bi.ss01d.isChecked() ? "4"
                : bi.ss01e.isChecked() ? "5"
                : bi.ss01f.isChecked() ? "6"
                : bi.ss01g.isChecked() ? "7"
                : bi.ss0196.isChecked() ? "96"
                : "0");
        json.put("ss0196x", bi.ss0196x.getText().toString());

        json.put("ss02", bi.ss02a.isChecked() ? "1"
                : bi.ss02b.isChecked() ? "2"
                : bi.ss0296.isChecked() ? "3"
                : "0");
        json.put("ss0296x", bi.ss0296x.getText().toString());


        json.put("ss03a", bi.ss03a.isChecked() ? "1" : "0");
        json.put("ss03b", bi.ss03b.isChecked() ? "2" : "0");
        json.put("ss03c", bi.ss03c.isChecked() ? "3" : "0");
        json.put("ss03d", bi.ss03d.isChecked() ? "4" : "0");
        json.put("ss03e", bi.ss03e.isChecked() ? "5" : "0");
        json.put("ss03f", bi.ss03f.isChecked() ? "6" : "0");
        json.put("ss03g", bi.ss03g.isChecked() ? "7" : "0");
        json.put("ss03h", bi.ss03h.isChecked() ? "8" : "0");
        json.put("ss03i", bi.ss03i.isChecked() ? "9" : "0");
        json.put("ss03j", bi.ss03j.isChecked() ? "10" : "0");
        json.put("ss03k", bi.ss03k.isChecked() ? "11" : "0");
        json.put("ss03l", bi.ss03l.isChecked() ? "12" : "0");
        json.put("ss03m", bi.ss03m.isChecked() ? "13" : "0");
        json.put("ss03n", bi.ss03n.isChecked() ? "14" : "0");
        json.put("ss0396", bi.ss0396.isChecked() ? "96" : "0");

        json.put("ss04", bi.ss04a.isChecked() ? "1"
                : bi.ss04b.isChecked() ? "2"
                : "0");

        json.put("ss05", bi.ss05a.isChecked() ? "1"
                : bi.ss05b.isChecked() ? "2"
                : bi.ss05c.isChecked() ? "3"
                : bi.ss05d.isChecked() ? "4"
                : bi.ss05e.isChecked() ? "5"
                : bi.ss05f.isChecked() ? "6"
                : bi.ss0596.isChecked() ? "96"
                : "0");
        json.put("ss0596x", bi.ss0596x.getText().toString());


        json.put("ss06a", bi.ss06a.isChecked() ? "1" : "0");
        json.put("ss06b", bi.ss06b.isChecked() ? "2" : "0");
        json.put("ss06c", bi.ss06c.isChecked() ? "3" : "0");
        json.put("ss06d", bi.ss06d.isChecked() ? "4" : "0");
        json.put("ss06e", bi.ss06e.isChecked() ? "5" : "0");
        json.put("ss06f", bi.ss06f.isChecked() ? "6" : "0");
        json.put("ss06g", bi.ss06g.isChecked() ? "7" : "0");
        json.put("ss06h", bi.ss06h.isChecked() ? "8" : "0");
        json.put("ss06i", bi.ss06i.isChecked() ? "9" : "0");
        json.put("ss06j", bi.ss06j.isChecked() ? "10" : "0");
        json.put("ss06k", bi.ss06k.isChecked() ? "11" : "0");
        json.put("ss06l", bi.ss06l.isChecked() ? "12" : "0");
        json.put("ss06m", bi.ss06m.isChecked() ? "13" : "0");
        json.put("ss06n", bi.ss06n.isChecked() ? "14" : "0");
        json.put("ss0696", bi.ss0696.isChecked() ? "96" : "0");
        

        MainApp.fc.setsM(String.valueOf(json));

    }

    private boolean formValidation() {

        return Validator.emptyCheckingContainer(this, bi.fldGrpSectionM);

    }

    public void BtnEnd() {

        Util.openEndActivity(this);
    }

    @Override
    public void onBackPressed() {
        Toast.makeText(this, "You can't go back", Toast.LENGTH_SHORT).show();
    }

}
