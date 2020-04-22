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
import edu.aku.hassannaqvi.tpvics_hh.databinding.ActivitySectionChc2Binding;
import edu.aku.hassannaqvi.tpvics_hh.ui.other.EndingActivity;

import static edu.aku.hassannaqvi.tpvics_hh.utils.UtilKt.openEndActivity;

public class SectionCHC2Activity extends AppCompatActivity {

    ActivitySectionChc2Binding bi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        bi = DataBindingUtil.setContentView(this, R.layout.activity_section_chc2);
        bi.setCallback(this);
        setTitle(R.string.chsec);
        setupSkips();
    }

    private void setupSkips() {

        bi.im08.setOnCheckedChangeListener(((radioGroup, i) -> {

            if (i == bi.im081.getId()) {
                Clear.clearAllFields(bi.fldGrpCVim09, true);
                Clear.clearAllFields(bi.fldGrpCVim10, true);
                Clear.clearAllFields(bi.fldGrpSecChc5, true);
                Clear.clearAllFields(bi.fldGrpSecChc6, true);
                Clear.clearAllFields(bi.fldGrpCVim23, true);
                Clear.clearAllFields(bi.fldGrpCVim23a, true);
            } else {
                Clear.clearAllFields(bi.fldGrpCVim09, false);
                Clear.clearAllFields(bi.fldGrpCVim10, false);
                Clear.clearAllFields(bi.fldGrpSecChc5, false);
                Clear.clearAllFields(bi.fldGrpSecChc6, false);
                Clear.clearAllFields(bi.fldGrpCVim23, false);
                Clear.clearAllFields(bi.fldGrpCVim23a, false);
            }

        }));

        bi.im10.setOnCheckedChangeListener((radioGroup, i) -> {
            if (i == bi.im101.getId()) {
                Clear.clearAllFields(bi.fldGrpSecChc5, true);
            } else {
                Clear.clearAllFields(bi.fldGrpSecChc5, false);
            }

        });

        bi.im14.setOnCheckedChangeListener((radioGroup, i) -> {
            if (i == bi.im141.getId()) {
                Clear.clearAllFields(bi.fldGrpCVim15, true);
            } else {
                Clear.clearAllFields(bi.fldGrpCVim15, false);
            }
        });

        bi.im16.setOnCheckedChangeListener((radioGroup, i) -> {
            if (i == bi.im161.getId()) {
                Clear.clearAllFields(bi.fldGrpCVim17, true);
            } else {
                Clear.clearAllFields(bi.fldGrpCVim17, false);
            }
        });

        bi.im18.setOnCheckedChangeListener((radioGroup, i) -> {
            if (i == bi.im181.getId()) {
                Clear.clearAllFields(bi.fldGrpCVim19, true);
            } else {
                Clear.clearAllFields(bi.fldGrpCVim19, false);
            }
        });

        bi.im21.setOnCheckedChangeListener((radioGroup, i) -> {
            if (i == bi.im211.getId()) {
                Clear.clearAllFields(bi.fldGrpCVim22, true);
            } else {
                Clear.clearAllFields(bi.fldGrpCVim22, false);
            }
        });

        bi.im23.setOnCheckedChangeListener((radioGroup, i) -> {
            if (i == bi.im231.getId() || i == bi.im232.getId() || i == bi.im233.getId()) {
                Clear.clearAllFields(bi.fldGrpSecChc4, false);
            } else if (i == bi.im234.getId()) {
                Clear.clearAllFields(bi.fldGrpCVim23a, false);
                Clear.clearAllFields(bi.fldGrpSecChc4, true);
            } else {
                Clear.clearAllFields(bi.fldGrpCVim23a, true);
                Clear.clearAllFields(bi.fldGrpSecChc4, true);
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

        JSONObject json = new JSONObject();

        json.put("im08",
                bi.im081.isChecked() ? "1" :
                        bi.im082.isChecked() ? "2" :
                                bi.im083.isChecked() ? "98" :
                                        "0");
        json.put("im09",
                bi.im091.isChecked() ? "1" :
                        bi.im092.isChecked() ? "2" :
                                bi.im093.isChecked() ? "98" :
                                        "0");
        json.put("im10",
                bi.im101.isChecked() ? "1" :
                        bi.im102.isChecked() ? "2" :
                                bi.im103.isChecked() ? "98" :
                                        "0");
        json.put("im11",
                bi.im111.isChecked() ? "1" :
                        bi.im112.isChecked() ? "2" :
                                bi.im113.isChecked() ? "98" :
                                        "0");
        json.put("im12", bi.im12.getText().toString());
        json.put("im13",
                bi.im131.isChecked() ? "1" :
                        bi.im132.isChecked() ? "2" :
                                bi.im133.isChecked() ? "98" :
                                        "0");
        json.put("im14",
                bi.im141.isChecked() ? "1" :
                        bi.im142.isChecked() ? "2" :
                                bi.im143.isChecked() ? "98" :
                                        "0");
        json.put("im15", bi.im15.getText().toString());
        json.put("im16",
                bi.im161.isChecked() ? "1" :
                        bi.im162.isChecked() ? "2" :
                                bi.im163.isChecked() ? "98" :
                                        "0");
        json.put("im17", bi.im17.getText().toString());
        json.put("im18",
                bi.im181.isChecked() ? "1" :
                        bi.im182.isChecked() ? "2" :
                                bi.im183.isChecked() ? "98" :
                                        "0");
        json.put("im19",
                "0");
        json.put("im20",
                bi.im201.isChecked() ? "1" :
                        bi.im202.isChecked() ? "2" :
                                bi.im203.isChecked() ? "98" :
                                        "0");
        json.put("im21",
                bi.im211.isChecked() ? "1" :
                        bi.im212.isChecked() ? "2" :
                                bi.im213.isChecked() ? "98" :
                                        "0");
        json.put("im22", bi.im22.getText().toString());
        json.put("im23",
                bi.im231.isChecked() ? "1" :
                        bi.im232.isChecked() ? "2" :
                                bi.im233.isChecked() ? "3" :
                                        bi.im234.isChecked() ? "4" :
                                                bi.im236.isChecked() ? "6" :
                                                        "0");
        json.put("im236x", bi.im236x.getText().toString());
        json.put("im23a",
                bi.im23a1.isChecked() ? "1" :
                        bi.im23a2.isChecked() ? "2" :
                                bi.im23a3.isChecked() ? "3" :
                                        bi.im23a96.isChecked() ? "96" :
                                                "0");
        json.put("im24",
                bi.im241.isChecked() ? "1" :
                        bi.im242.isChecked() ? "2" :
                                bi.im243.isChecked() ? "3" :
                                        bi.im244.isChecked() ? "4" :
                                                bi.im245.isChecked() ? "5" :
                                                        bi.im246.isChecked() ? "6" :
                                                                bi.im247.isChecked() ? "7" :
                                                                        bi.im248.isChecked() ? "8" :
                                                                                bi.im249.isChecked() ? "9" :
                                                                                        bi.im2410.isChecked() ? "10" :
                                                                                                bi.im2411.isChecked() ? "11" :
                                                                                                        bi.im2412.isChecked() ? "12" :
                                                                                                                bi.im2413.isChecked() ? "13" :
                                                                                                                        bi.im2414.isChecked() ? "14" :
                                                                                                                                bi.im2415.isChecked() ? "15" :
                                                                                                                                        bi.im2416.isChecked() ? "16" :
                                                                                                                                                bi.im2417.isChecked() ? "98" :
                                                                                                                                                        bi.im2499.isChecked() ? "98" :
                                                                                                                                                                "0");
        json.put("im2417x", bi.im2417x.getText().toString());

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

   /* @Override
    public void onBackPressed() {
        Toast.makeText(this, "Press top back button.", Toast.LENGTH_SHORT).show();
    }*/

}
