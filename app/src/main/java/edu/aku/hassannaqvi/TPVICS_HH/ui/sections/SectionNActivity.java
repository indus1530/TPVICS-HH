package edu.aku.hassannaqvi.TPVICS_HH.ui.sections;

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

import edu.aku.hassannaqvi.TPVICS_HH.R;
import edu.aku.hassannaqvi.TPVICS_HH.contracts.FormsContract;
import edu.aku.hassannaqvi.TPVICS_HH.core.DatabaseHelper;
import edu.aku.hassannaqvi.TPVICS_HH.core.MainApp;
import edu.aku.hassannaqvi.TPVICS_HH.databinding.ActivitySectionNBinding;
import edu.aku.hassannaqvi.TPVICS_HH.utils.Util;

public class SectionNActivity extends AppCompatActivity {

    ActivitySectionNBinding bi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        bi = DataBindingUtil.setContentView(this, R.layout.activity_section_n);
        bi.setCallback(this);

        setUIComponent();
    }

    private void setUIComponent() {

        bi.n103.setOnCheckedChangeListener(((radioGroup, i) -> {
            if (i == bi.n103c.getId()) {
                bi.fldGrpCVn104.setVisibility(View.VISIBLE);
                bi.fldGrpCVn105.setVisibility(View.VISIBLE);
            } else {
                bi.fldGrpCVn104.setVisibility(View.GONE);
                bi.fldGrpCVn105.setVisibility(View.GONE);
                Clear.clearAllFields(bi.fldGrpCVn104);
                Clear.clearAllFields(bi.fldGrpCVn105);
            }

        }));

        bi.n108.setOnCheckedChangeListener(((radioGroup, i) -> {
            if (i != bi.n108a.getId()) {
                Clear.clearAllFields(bi.fldGrpCVn109);
            }
        }));

        bi.n111.setOnCheckedChangeListener(((radioGroup, i) -> {
            if (i != bi.n111a.getId()) {
                Clear.clearAllFields(bi.fldGrp1213);
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
                startActivity(new Intent(this, SectionOActivity.class));
            } else {
                Toast.makeText(this, "Failed to Update Database!", Toast.LENGTH_SHORT).show();
            }

        }
    }

    private boolean UpdateDB() {

        DatabaseHelper db = new DatabaseHelper(this);
        int updcount = db.updatesFormColumn(FormsContract.FormsTable.COLUMN_SN, MainApp.fc.getsN());
        if (updcount == 1) {
            return true;
        } else {
            Toast.makeText(this, "Updating Database... ERROR!", Toast.LENGTH_SHORT).show();
            return false;
        }
    }

    private void SaveDraft() throws JSONException {

        JSONObject json = new JSONObject();

        json.put("n101",
                bi.n101a.isChecked() ? "1" :
                        bi.n101b.isChecked() ? "2" :
                                bi.n101c.isChecked() ? "3" :
                                        bi.n101d.isChecked() ? "4" :
                                                bi.n101e.isChecked() ? "5" :
                                                        bi.n101f.isChecked() ? "6" :
                                                                bi.n101g.isChecked() ? "7" :
                                                                        bi.n101h.isChecked() ? "8" :
                                                                                bi.n101i.isChecked() ? "9" :
                                                                                        bi.n101j.isChecked() ? "10" :
                                                                                                bi.n101k.isChecked() ? "11" :
                                                                                                        bi.n101l.isChecked() ? "12" :
                                                                                                                bi.n101m.isChecked() ? "13" :
                                                                                                                        bi.n101n.isChecked() ? "14" :
                                                                                                                                "0");
        json.put("n102",
                bi.n102a.isChecked() ? "1" :
                        bi.n102b.isChecked() ? "2" :
                                bi.n102c.isChecked() ? "3" :
                                        bi.n102d.isChecked() ? "4" :
                                                bi.n102e.isChecked() ? "5" :
                                                        bi.n102f.isChecked() ? "6" :
                                                                bi.n102g.isChecked() ? "7" :
                                                                        bi.n102h.isChecked() ? "8" :
                                                                                bi.n102i.isChecked() ? "9" :
                                                                                        bi.n102j.isChecked() ? "10" :
                                                                                                bi.n102k.isChecked() ? "11" :
                                                                                                        bi.n102l.isChecked() ? "12" :
                                                                                                                bi.n102m.isChecked() ? "13" :
                                                                                                                        bi.n102n.isChecked() ? "14" :
                                                                                                                                "0");
        json.put("n103",
                bi.n103a.isChecked() ? "1" :
                        bi.n103b.isChecked() ? "2" :
                                bi.n103c.isChecked() ? "3" :
                                        "0");

        json.put("n104",
                bi.n10498.isChecked() ? "98" :
                        bi.n104.getText().toString());

        json.put("n105",
                bi.n105a.isChecked() ? "1" :
                        bi.n105b.isChecked() ? "2" :
                                bi.n105c.isChecked() ? "3" :
                                        bi.n105d.isChecked() ? "4" :
                                                bi.n105e.isChecked() ? "5" :
                                                        bi.n105f.isChecked() ? "98" :
                                                                "0");

        json.put("n106",
                bi.n106a.isChecked() ? "1" :
                        bi.n106b.isChecked() ? "2" :
                                "0");

        json.put("n107",
                bi.n107a.isChecked() ? "1" :
                        bi.n107b.isChecked() ? "2" :
                                bi.n10798.isChecked() ? "98" :
                                        "0");

        json.put("n108",
                bi.n108a.isChecked() ? "1" :
                        bi.n108b.isChecked() ? "2" :
                                bi.n10898.isChecked() ? "98" :
                                        "0");

        json.put("n109a", bi.n109a.isChecked() ? "1" : "0");
        json.put("n109b", bi.n109b.isChecked() ? "2" : "0");
        json.put("n109c", bi.n109c.isChecked() ? "3" : "0");
        json.put("n109d", bi.n109d.isChecked() ? "4" : "0");
        json.put("n109e", bi.n109e.isChecked() ? "5" : "0");
        json.put("n109f", bi.n109f.isChecked() ? "6" : "0");
        json.put("n109g", bi.n109g.isChecked() ? "7" : "0");

        json.put("n110",
                bi.n110a.isChecked() ? "1" :
                        bi.n110b.isChecked() ? "2" :
                                bi.n110c.isChecked() ? "3" :
                                        bi.n110d.isChecked() ? "4" :
                                                bi.n110e.isChecked() ? "5" :
                                                        bi.n110f.isChecked() ? "6" :
                                                                bi.n110g.isChecked() ? "7" :
                                                                        bi.n110h.isChecked() ? "8" :
                                                                                bi.n110i.isChecked() ? "9" :
                                                                                        bi.n110j.isChecked() ? "10" :
                                                                                                bi.n110k.isChecked() ? "11" :
                                                                                                        "0");
        json.put("n111",
                bi.n111a.isChecked() ? "1" :
                        bi.n111b.isChecked() ? "2" :
                                bi.n11198.isChecked() ? "98" :
                                        "0");

        json.put("n112",
                bi.n112a.isChecked() ? "1" :
                        bi.n112b.isChecked() ? "2" :
                                "0");

        json.put("n113",
                bi.n113a.isChecked() ? "1" :
                        bi.n113b.isChecked() ? "2" :
                                bi.n11398.isChecked() ? "98" :
                                        "0");

        MainApp.fc.setsN(String.valueOf(json));

    }

    private boolean formValidation() {
        return Validator.emptyCheckingContainer(this, bi.fldGrpSectionN);

    }

    public void BtnEnd() {
        Util.openEndActivity(this);
    }

    @Override
    public void onBackPressed() {
        Toast.makeText(this, "You can't go back", Toast.LENGTH_SHORT).show();
    }
}
