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
import edu.aku.hassannaqvi.tpvics_hh.contracts.ChildContract;
import edu.aku.hassannaqvi.tpvics_hh.core.DatabaseHelper;
import edu.aku.hassannaqvi.tpvics_hh.core.MainApp;
import edu.aku.hassannaqvi.tpvics_hh.databinding.ActivitySectionChCBinding;

import static edu.aku.hassannaqvi.tpvics_hh.CONSTANTS.IM02FLAG;
import static edu.aku.hassannaqvi.tpvics_hh.core.MainApp.child;
import static edu.aku.hassannaqvi.tpvics_hh.utils.UtilKt.openChildEndActivity;

public class SectionCHCActivity extends AppCompatActivity {

    ActivitySectionChCBinding bi;
    boolean im02Flag = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        bi = DataBindingUtil.setContentView(this, R.layout.activity_section_ch_c);
        bi.setCallback(this);

        setupListeners();

    }

    private void setupListeners() {


        bi.im01.setOnCheckedChangeListener(((radioGroup, i) -> {
            if (i == bi.im011.getId()) {
                Clear.clearAllFields(bi.fldGrpCVim02, false);
                Clear.clearAllFields(bi.fldGrpCVim03, false);
                Clear.clearAllFields(bi.fldGrpCVim04, true);
            } else if (i == bi.im012.getId()) {
                Clear.clearAllFields(bi.fldGrpCVim02, false);
                Clear.clearAllFields(bi.fldGrpCVim03, true);
                Clear.clearAllFields(bi.fldGrpCVim04, false);
            } else {
                Clear.clearAllFields(bi.fldGrpCVim02, true);
                Clear.clearAllFields(bi.fldGrpCVim03, true);
                Clear.clearAllFields(bi.fldGrpCVim04, false);
            }

        }));

        bi.im02.setOnCheckedChangeListener((radioGroup, i) -> {
/*            if (i == bi.im021.getId()) {
                Clear.clearAllFields(bi.fldGrpCVim03, false);
//                Clear.clearAllFields(bi.fldGrpSecChc2, false);
            } else {
                Clear.clearAllFields(bi.fldGrpCVim03, true);
//                Clear.clearAllFields(bi.fldGrpSecChc2, true);
            }*/

            Clear.clearAllFields(bi.fldGrpCVim03, i == bi.im022.getId());
            im02Flag = i == bi.im021.getId();

        });

    }

    private boolean UpdateDB() {
        DatabaseHelper db = MainApp.appInfo.getDbHelper();
        int updcount = db.updatesChildColumn(ChildContract.SingleChild.COLUMN_SCC, child.getsCC());
        if (updcount == 1) {
            return true;
        } else {
            Toast.makeText(this, "Updating Database... ERROR!", Toast.LENGTH_SHORT).show();
            return false;
        }
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
        f1.put("im03",
                bi.im031.isChecked() ? "1" :
                        bi.im032.isChecked() ? "2" :
                                bi.im033.isChecked() ? "3" :
                                        bi.im034.isChecked() ? "4" :
                                                bi.im035.isChecked() ? "5" :
                                                        bi.im036.isChecked() ? "6" :
                                                                bi.im0396.isChecked() ? "96" :
                                                                        "0");

        f1.put("im04dd", bi.im04dd1.isChecked() ? "98" : bi.im04dd.getText().toString());
        f1.put("im04mm", bi.im04mm.getText().toString());
        f1.put("im04yy", bi.im04yy.getText().toString());

        child.setsCC(String.valueOf(f1));
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
                startActivity(new Intent(this, SectionCHDActivity.class).putExtra(IM02FLAG, !im02Flag));

            } else {
                Toast.makeText(this, "Failed to Update Database!", Toast.LENGTH_SHORT).show();
            }
        }

    }

    public void BtnEnd() {

        openChildEndActivity(this);
    }

    @Override
    public void onBackPressed() {
        Toast.makeText(this, "Back Press Not Allowed", Toast.LENGTH_SHORT).show();
    }

}
