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
import edu.aku.hassannaqvi.tpvics_hh.databinding.ActivitySectionChCBinding;
import edu.aku.hassannaqvi.tpvics_hh.ui.other.EndingActivity;
import edu.aku.hassannaqvi.tpvics_hh.utils.Util;

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

        bi.im01.setOnCheckedChangeListener((radioGroup, i) -> {
            if (i == bi.im011.getId())
                Clear.clearAllFields(bi.fldGrpSecChc1);
        });

        bi.im01.setOnCheckedChangeListener((radioGroup, i) -> {
            if (i == bi.im012.getId())
                Clear.clearAllFields(bi.fldGrpCVim02);
        });


        bi.im02.setOnCheckedChangeListener((radioGroup, i) -> {
            if (i == bi.im021.getId())
                Clear.clearAllFields(bi.fldGrpCVim02a);
        });

        bi.im02.setOnCheckedChangeListener((radioGroup, i) -> {
            if (i == bi.im021.getId())
                Clear.clearAllFields(bi.fldGrpSecChc2);
        });

        bi.im05.setOnCheckedChangeListener((radioGroup, i) -> {
            if (i != bi.im051.getId())
                Clear.clearAllFields(bi.fldGrpCVim06);
        });

        bi.im05.setOnCheckedChangeListener((radioGroup, i) -> {
            if (i != bi.im051.getId())
                Clear.clearAllFields(bi.fldGrpSecChc3);
        });

        bi.im23.setOnCheckedChangeListener((radioGroup, i) -> {
            if (i == bi.im231.getId() || i == bi.im232.getId() || i == bi.im233.getId())
                Clear.clearAllFields(bi.fldGrpSecChc4);
        });

        bi.im23.setOnCheckedChangeListener((radioGroup, i) -> {
            if (i == bi.im234.getId())
                Clear.clearAllFields(bi.fldGrpCVim23a);
        });

        bi.im23.setOnCheckedChangeListener((radioGroup, i) -> {
            if (i == bi.im234.getId())
                Clear.clearAllFields(bi.fldGrpCVim23a);
        });


        bi.im08.setOnCheckedChangeListener(((radioGroup, i) -> {

            if (i == bi.im081.getId()) {
                bi.fldGrpSecChc3.setVisibility(View.VISIBLE);
                bi.fldGrpCVim23.setVisibility(View.VISIBLE);
                bi.fldGrpCVim23a.setVisibility(View.VISIBLE);
            } else {
                Clear.clearAllFields(bi.fldGrpSecChc3);
                Clear.clearAllFields(bi.fldGrpCVim23);
                Clear.clearAllFields(bi.fldGrpCVim23a);
                bi.fldGrpSecChc3.setVisibility(View.GONE);
                bi.fldGrpCVim23.setVisibility(View.GONE);
                bi.fldGrpCVim23a.setVisibility(View.GONE);
            }

        }));


        bi.im10.setOnCheckedChangeListener((radioGroup, i) -> {
            if (i != bi.im101.getId())
                Clear.clearAllFields(bi.fldGrpSecChc5);
        });

        bi.im14.setOnCheckedChangeListener((radioGroup, i) -> {
            if (i != bi.im141.getId())
                Clear.clearAllFields(bi.fldGrpCVim15);
        });

        bi.im16.setOnCheckedChangeListener((radioGroup, i) -> {
            if (i != bi.im161.getId())
                Clear.clearAllFields(bi.fldGrpCVim17);
        });

        bi.im18.setOnCheckedChangeListener((radioGroup, i) -> {
            if (i != bi.im181.getId())
                Clear.clearAllFields(bi.fldGrpCVim19);
        });

        bi.im21.setOnCheckedChangeListener((radioGroup, i) -> {
            if (i != bi.im211.getId())
                Clear.clearAllFields(bi.fldGrpCVim22);
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
        f1.put("im03dd", bi.im03dd.getText().toString());
        f1.put("im03dd1", bi.im03dd1.getText().toString());
        f1.put("im03mm", bi.im03mm.getText().toString());
        f1.put("im03yy", bi.im03yy.getText().toString());
        f1.put("im41bcgdd", bi.im41bcgdd.getText().toString());
        f1.put("im41bcgmm", bi.im41bcgmm.getText().toString());
        f1.put("im41bcgyy", bi.im41bcgyy.getText().toString());
        f1.put("im42opv0dd", bi.im42opv0dd.getText().toString());
        f1.put("im42opv0mm", bi.im42opv0mm.getText().toString());
        f1.put("im42opv0yy", bi.im42opv0yy.getText().toString());
        f1.put("im43opv1dd", bi.im43opv1dd.getText().toString());
        f1.put("im43opv1mm", bi.im43opv1mm.getText().toString());
        f1.put("im43opv1yy", bi.im43opv1yy.getText().toString());
        f1.put("im44penta1dd", bi.im44penta1dd.getText().toString());
        f1.put("im44penta1mm", bi.im44penta1mm.getText().toString());
        f1.put("im44penta1yy", bi.im44penta1yy.getText().toString());
        f1.put("im45pcv1dd", bi.im45pcv1dd.getText().toString());
        f1.put("im45pcv1mm", bi.im45pcv1mm.getText().toString());
        f1.put("im45pcv1yy", bi.im45pcv1yy.getText().toString());
        f1.put("im46rv1dd", bi.im46rv1dd.getText().toString());
        f1.put("im46rv1mm", bi.im46rv1mm.getText().toString());
        f1.put("im46rv1yy", bi.im46rv1yy.getText().toString());
        f1.put("im47opv2dd", bi.im47opv2dd.getText().toString());
        f1.put("im47opv2mm", bi.im47opv2mm.getText().toString());
        f1.put("im47opv2yy", bi.im47opv2yy.getText().toString());
        f1.put("im48penta2dd", bi.im48penta2dd.getText().toString());
        f1.put("im48penta2mm", bi.im48penta2mm.getText().toString());
        f1.put("im48penta2yy", bi.im48penta2yy.getText().toString());
        f1.put("im49pcv2dd", bi.im49pcv2dd.getText().toString());
        f1.put("im49pcv2mm", bi.im49pcv2mm.getText().toString());
        f1.put("im49pcv2yy", bi.im49pcv2yy.getText().toString());
        f1.put("im410rv2dd", bi.im410rv2dd.getText().toString());
        f1.put("im410rv2mm", bi.im410rv2mm.getText().toString());
        f1.put("im410rv2yy", bi.im410rv2yy.getText().toString());
        f1.put("im411opv3dd", bi.im411opv3dd.getText().toString());
        f1.put("im411opv3mm", bi.im411opv3mm.getText().toString());
        f1.put("im411opv3yy", bi.im411opv3yy.getText().toString());
        f1.put("im412penta3dd", bi.im412penta3dd.getText().toString());
        f1.put("im412penta3mm", bi.im412penta3mm.getText().toString());
        f1.put("im412penta3yy", bi.im412penta3yy.getText().toString());
        f1.put("im413pcv3dd", bi.im413pcv3dd.getText().toString());
        f1.put("im413pcv3mm", bi.im413pcv3mm.getText().toString());
        f1.put("im413pcv3yy", bi.im413pcv3yy.getText().toString());
        f1.put("im414ipvdd", bi.im414ipvdd.getText().toString());
        f1.put("im414ipvmm", bi.im414ipvmm.getText().toString());
        f1.put("im414ipvyy", bi.im414ipvyy.getText().toString());
        f1.put("im415measles1dd", bi.im415measles1dd.getText().toString());
        f1.put("im415measles1mm", bi.im415measles1mm.getText().toString());
        f1.put("im415measles1yy", bi.im415measles1yy.getText().toString());
        f1.put("im416measles2dd", bi.im416measles2dd.getText().toString());
        f1.put("im416measles2mm", bi.im416measles2mm.getText().toString());
        f1.put("im416measles2yy", bi.im416measles2yy.getText().toString());
        f1.put("im05",
                bi.im051.isChecked() ? "1" :
                        bi.im052.isChecked() ? "2" :
                                bi.im053.isChecked() ? "98" :
                                        "0");
        f1.put("im06",
                bi.im061.isChecked() ? "1" :
                        bi.im062.isChecked() ? "2" :
                                "0");
        f1.put("im08",
                bi.im081.isChecked() ? "1" :
                        bi.im082.isChecked() ? "2" :
                                bi.im083.isChecked() ? "98" :
                                        "0");
        f1.put("im09",
                bi.im091.isChecked() ? "1" :
                        bi.im092.isChecked() ? "2" :
                                bi.im093.isChecked() ? "98" :
                                        "0");
        f1.put("im10",
                bi.im101.isChecked() ? "1" :
                        bi.im102.isChecked() ? "2" :
                                bi.im103.isChecked() ? "98" :
                                        "0");
        f1.put("im11",
                bi.im111.isChecked() ? "1" :
                        bi.im112.isChecked() ? "2" :
                                bi.im113.isChecked() ? "98" :
                                        "0");
        f1.put("im12", bi.im12.getText().toString());
        f1.put("im13",
                bi.im131.isChecked() ? "1" :
                        bi.im132.isChecked() ? "2" :
                                bi.im133.isChecked() ? "98" :
                                        "0");
        f1.put("im14",
                bi.im141.isChecked() ? "1" :
                        bi.im142.isChecked() ? "2" :
                                bi.im143.isChecked() ? "98" :
                                        "0");
        f1.put("im15", bi.im15.getText().toString());
        f1.put("im16",
                bi.im161.isChecked() ? "1" :
                        bi.im162.isChecked() ? "2" :
                                bi.im163.isChecked() ? "98" :
                                        "0");
        f1.put("im17", bi.im17.getText().toString());
        f1.put("im18",
                bi.im181.isChecked() ? "1" :
                        bi.im182.isChecked() ? "2" :
                                bi.im183.isChecked() ? "98" :
                                        "0");
        f1.put("im19",
                "0");
        f1.put("im20",
                bi.im201.isChecked() ? "1" :
                        bi.im202.isChecked() ? "2" :
                                bi.im203.isChecked() ? "98" :
                                        "0");
        f1.put("im21",
                bi.im211.isChecked() ? "1" :
                        bi.im212.isChecked() ? "2" :
                                bi.im213.isChecked() ? "98" :
                                        "0");
        f1.put("im22", bi.im22.getText().toString());
        f1.put("im23",
                bi.im231.isChecked() ? "1" :
                        bi.im232.isChecked() ? "2" :
                                bi.im233.isChecked() ? "3" :
                                        bi.im234.isChecked() ? "4" :
                                                bi.im236.isChecked() ? "6" :
                                                        "0");
        f1.put("im236x", bi.im236x.getText().toString());
        f1.put("im23a",
                bi.im23a1.isChecked() ? "1" :
                        bi.im23a2.isChecked() ? "2" :
                                bi.im23a3.isChecked() ? "3" :
                                        bi.im23a96.isChecked() ? "96" :
                                                "0");
        f1.put("im24",
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
        f1.put("im2417x", bi.im2417x.getText().toString());

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

        Util.openEndActivity(this);
    }

    @Override
    public void onBackPressed() {
        Toast.makeText(this, "Press top back button.", Toast.LENGTH_SHORT).show();
    }

}