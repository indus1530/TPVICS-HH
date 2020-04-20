package edu.aku.hassannaqvi.tpvics_hh.ui.sections;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.validatorcrawler.aliazaz.Clear;
import com.validatorcrawler.aliazaz.Validator;

import org.json.JSONException;
import org.json.JSONObject;

import edu.aku.hassannaqvi.tpvics_hh.R;
import edu.aku.hassannaqvi.tpvics_hh.contracts.FormsContract;
import edu.aku.hassannaqvi.tpvics_hh.core.DatabaseHelper;
import edu.aku.hassannaqvi.tpvics_hh.core.MainApp;
import edu.aku.hassannaqvi.tpvics_hh.databinding.ActivitySectionC1Binding;
import edu.aku.hassannaqvi.tpvics_hh.utils.Util;

public class SectionC1Activity extends AppCompatActivity {

    ActivitySectionC1Binding bi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        bi = DataBindingUtil.setContentView(this, R.layout.activity_section_c1);
        bi.setCallback(this);
        setTitle(R.string.sssec);
        setupSkips();


    }


    private void setupSkips() {

        bi.ss04.setOnCheckedChangeListener(((radioGroup, i) -> {
            if (i == bi.ss04b.getId()) {
                Clear.clearAllFields(bi.ss05cv);
                bi.ss05cv.setVisibility(View.GONE);
            } else {
                bi.ss05cv.setVisibility(View.VISIBLE);
            }
        }));


        bi.ss07.setOnCheckedChangeListener(((radioGroup, i) -> {
            if (i == bi.ss07h.getId() || i == bi.ss07i.getId()) {
                Clear.clearAllFields(bi.ss08cv);
                Clear.clearAllFields(bi.ss09cv);
                Clear.clearAllFields(bi.ss10cv);
                Clear.clearAllFields(bi.ss11cv);
                Clear.clearAllFields(bi.ss12cv);
                bi.ss08cv.setVisibility(View.GONE);
                bi.ss09cv.setVisibility(View.GONE);
                bi.ss10cv.setVisibility(View.GONE);
                bi.ss11cv.setVisibility(View.GONE);
                bi.ss12cv.setVisibility(View.GONE);
            } else {
                bi.ss08cv.setVisibility(View.VISIBLE);
                bi.ss09cv.setVisibility(View.VISIBLE);
                bi.ss10cv.setVisibility(View.VISIBLE);
                bi.ss11cv.setVisibility(View.VISIBLE);
                bi.ss12cv.setVisibility(View.VISIBLE);
            }
        }));


        bi.ss09.setOnCheckedChangeListener(((radioGroup, i) -> {
            if (i == bi.ss09b.getId()) {
                Clear.clearAllFields(bi.ss10cv);
                Clear.clearAllFields(bi.ss11cv);
                Clear.clearAllFields(bi.ss12cv);
                bi.ss10cv.setVisibility(View.GONE);
                bi.ss11cv.setVisibility(View.GONE);
                bi.ss12cv.setVisibility(View.GONE);
            } else {
                bi.ss10cv.setVisibility(View.VISIBLE);
                bi.ss11cv.setVisibility(View.VISIBLE);
                bi.ss12cv.setVisibility(View.VISIBLE);
            }
        }));


        bi.ss11.setOnCheckedChangeListener(((radioGroup, i) -> {
            if (i == bi.ss11b.getId()) {
                Clear.clearAllFields(bi.ss12cv);
                Clear.clearAllFields(bi.ss13cv);
                bi.ss12cv.setVisibility(View.GONE);
                bi.ss13cv.setVisibility(View.GONE);
            } else {
                bi.ss12cv.setVisibility(View.VISIBLE);
                bi.ss13cv.setVisibility(View.VISIBLE);
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
                startActivity(new Intent(this, SectionC2Activity.class));
            } else {
                Toast.makeText(this, "Failed to Update Database!", Toast.LENGTH_SHORT).show();
            }

        }
    }


    private boolean UpdateDB() {

        DatabaseHelper db = MainApp.appInfo.getDbHelper();
        int updcount = db.updatesFormColumn(FormsContract.FormsTable.COLUMN_SE, MainApp.fc.getsE());
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

        json.put("ss07", bi.ss07a.isChecked() ? "1"
                : bi.ss07b.isChecked() ? "2"
                : bi.ss07c.isChecked() ? "3"
                : bi.ss07d.isChecked() ? "4"
                : bi.ss07e.isChecked() ? "5"
                : bi.ss07f.isChecked() ? "6"
                : bi.ss07g.isChecked() ? "7"
                : bi.ss07h.isChecked() ? "8"
                : bi.ss07i.isChecked() ? "9"
                : bi.ss0796.isChecked() ? "96"
                : "0");
        json.put("ss0796x", bi.ss0796x.getText().toString());

        json.put("ss08", bi.ss08a.isChecked() ? "1"
                : bi.ss08b.isChecked() ? "2"
                : bi.ss08c.isChecked() ? "3"
                : "0");

        json.put("ss09", bi.ss09a.isChecked() ? "1"
                : bi.ss09b.isChecked() ? "2"
                : "0");

        json.put("ss10", bi.ss10.getText().toString());

        json.put("ss11", bi.ss11a.isChecked() ? "1"
                : bi.ss11b.isChecked() ? "2"
                : "0");

        json.put("ss12", bi.ss12a.isChecked() ? "1"
                : bi.ss12b.isChecked() ? "2"
                : bi.ss1298.isChecked() ? "98"
                : "0");

        json.put("ss13", bi.ss13a.isChecked() ? "1"
                : bi.ss13b.isChecked() ? "2"
                : bi.ss13c.isChecked() ? "3"
                : bi.ss13d.isChecked() ? "4"
                : bi.ss13e.isChecked() ? "5"
                : bi.ss13f.isChecked() ? "6"
                : bi.ss13g.isChecked() ? "7"
                : bi.ss1396.isChecked() ? "96"
                : "0");
        json.put("ss1396x", bi.ss1396x.getText().toString());


        json.put("ss14a", bi.ss14aa.isChecked() ? "1"
                : bi.ss14ab.isChecked() ? "2"
                : "0");

        json.put("ss14b", bi.ss14ba.isChecked() ? "1"
                : bi.ss14bb.isChecked() ? "2"
                : "0");

        json.put("ss14c", bi.ss14ca.isChecked() ? "1"
                : bi.ss14cb.isChecked() ? "2"
                : "0");

        json.put("ss14d", bi.ss14da.isChecked() ? "1"
                : bi.ss14db.isChecked() ? "2"
                : "0");

        json.put("ss14e", bi.ss14ea.isChecked() ? "1"
                : bi.ss14eb.isChecked() ? "2"
                : "0");

        json.put("ss14f", bi.ss14fa.isChecked() ? "1"
                : bi.ss14fb.isChecked() ? "2"
                : "0");

        json.put("ss14g", bi.ss14ga.isChecked() ? "1"
                : bi.ss14gb.isChecked() ? "2"
                : "0");

        json.put("ss14h", bi.ss14ha.isChecked() ? "1"
                : bi.ss14hb.isChecked() ? "2"
                : "0");

        json.put("ss14i", bi.ss14ia.isChecked() ? "1"
                : bi.ss14ib.isChecked() ? "2"
                : "0");

        json.put("ss14j", bi.ss14ja.isChecked() ? "1"
                : bi.ss14jb.isChecked() ? "2"
                : "0");

        json.put("ss14k", bi.ss14ka.isChecked() ? "1"
                : bi.ss14kb.isChecked() ? "2"
                : "0");

        json.put("ss14l", bi.ss14la.isChecked() ? "1"
                : bi.ss14lb.isChecked() ? "2"
                : "0");

        json.put("ss14m", bi.ss14ma.isChecked() ? "1"
                : bi.ss14mb.isChecked() ? "2"
                : "0");

        json.put("ss14n", bi.ss14na.isChecked() ? "1"
                : bi.ss14nb.isChecked() ? "2"
                : "0");

        json.put("ss14o", bi.ss14oa.isChecked() ? "1"
                : bi.ss14ob.isChecked() ? "2"
                : "0");

        json.put("ss14p", bi.ss14pa.isChecked() ? "1"
                : bi.ss14pb.isChecked() ? "2"
                : "0");

        json.put("ss14q", bi.ss14qa.isChecked() ? "1"
                : bi.ss14qb.isChecked() ? "2"
                : "0");

        json.put("ss14r", bi.ss14ra.isChecked() ? "1"
                : bi.ss14rb.isChecked() ? "2"
                : "0");

        json.put("ss14s", bi.ss14sa.isChecked() ? "1"
                : bi.ss14sb.isChecked() ? "2"
                : "0");

        MainApp.fc.setsE(String.valueOf(json));

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
