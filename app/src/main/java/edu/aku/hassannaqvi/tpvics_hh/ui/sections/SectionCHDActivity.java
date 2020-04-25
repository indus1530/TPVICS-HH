package edu.aku.hassannaqvi.tpvics_hh.ui.sections;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.edittextpicker.aliazaz.EditTextPicker;
import com.validatorcrawler.aliazaz.Clear;
import com.validatorcrawler.aliazaz.Validator;

import org.json.JSONException;
import org.json.JSONObject;

import edu.aku.hassannaqvi.tpvics_hh.R;
import edu.aku.hassannaqvi.tpvics_hh.contracts.ChildContract;
import edu.aku.hassannaqvi.tpvics_hh.core.DatabaseHelper;
import edu.aku.hassannaqvi.tpvics_hh.core.MainApp;
import edu.aku.hassannaqvi.tpvics_hh.databinding.ActivitySectionChDBinding;
import edu.aku.hassannaqvi.tpvics_hh.datecollection.AgeModel;
import edu.aku.hassannaqvi.tpvics_hh.datecollection.DateRepository;
import edu.aku.hassannaqvi.tpvics_hh.ui.other.EndingActivity;
import edu.aku.hassannaqvi.tpvics_hh.utils.JSONUtils;

import static edu.aku.hassannaqvi.tpvics_hh.CONSTANTS.IM02FLAG;
import static edu.aku.hassannaqvi.tpvics_hh.core.MainApp.child;
import static edu.aku.hassannaqvi.tpvics_hh.utils.UtilKt.openChildEndActivity;

public class SectionCHDActivity extends AppCompatActivity {

    ActivitySectionChDBinding bi;
    boolean imFlag = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        bi = DataBindingUtil.setContentView(this, R.layout.activity_section_ch_d);
        bi.setCallback(this);
        setTitle(R.string.chsec);
        setupListeners();
        setupTextWatchers();

    }

    private void setupTextWatchers() {
        editTextImplementation(new EditTextPicker[]{bi.im41bcgdd, bi.im41bcgmm, bi.im41bcgyy});
        editTextImplementation(new EditTextPicker[]{bi.im42opv0dd, bi.im42opv0mm, bi.im42opv0yy});
        editTextImplementation(new EditTextPicker[]{bi.im43opv1dd, bi.im43opv1mm, bi.im43opv1yy});
        editTextImplementation(new EditTextPicker[]{bi.im44penta1dd, bi.im44penta1mm, bi.im44penta1yy});
        editTextImplementation(new EditTextPicker[]{bi.im45pcv1dd, bi.im45pcv1mm, bi.im45pcv1yy});
        editTextImplementation(new EditTextPicker[]{bi.im46rv1dd, bi.im46rv1mm, bi.im46rv1yy});
        editTextImplementation(new EditTextPicker[]{bi.im47opv2dd, bi.im47opv2mm, bi.im47opv2yy});
        editTextImplementation(new EditTextPicker[]{bi.im48penta2dd, bi.im48penta2mm, bi.im48penta2yy});
        editTextImplementation(new EditTextPicker[]{bi.im49pcv2dd, bi.im49pcv2mm, bi.im49pcv2yy});
        editTextImplementation(new EditTextPicker[]{bi.im410rv2dd, bi.im410rv2mm, bi.im410rv2yy});
        editTextImplementation(new EditTextPicker[]{bi.im411opv3dd, bi.im411opv3mm, bi.im411opv3yy});
        editTextImplementation(new EditTextPicker[]{bi.im412penta3dd, bi.im412penta3mm, bi.im412penta3yy});
        editTextImplementation(new EditTextPicker[]{bi.im413pcv3dd, bi.im413pcv3mm, bi.im413pcv3yy});
        editTextImplementation(new EditTextPicker[]{bi.im414ipvdd, bi.im414ipvmm, bi.im414ipvyy});
        editTextImplementation(new EditTextPicker[]{bi.im415measles1dd, bi.im415measles1mm, bi.im415measles1yy});
        editTextImplementation(new EditTextPicker[]{bi.im416measles2dd, bi.im416measles2mm, bi.im416measles2yy});
    }

    public void editTextImplementation(EditTextPicker[] editTextsArray) {
        if (editTextsArray.length != 3) return;
        EditTextPicker editTextPicker01 = editTextsArray[0];
        EditTextPicker editTextPicker02 = editTextsArray[1];
        EditTextPicker editTextPicker03 = editTextsArray[2];

        for (EditTextPicker item : new EditTextPicker[]{editTextPicker01, editTextPicker02}) {
            item.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    editTextPicker03.setText(null);
                    editTextPicker03.setError(null);
                }

                @Override
                public void afterTextChanged(Editable s) {

                }
            });
        }

        editTextPicker03.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String txt01, txt02, txt03;
                editTextPicker01.setEnabled(true);
                editTextPicker02.setEnabled(true);
                if (!TextUtils.isEmpty(editTextPicker01.getText()) && !TextUtils.isEmpty(editTextPicker02.getText()) && !TextUtils.isEmpty(editTextPicker03.getText())) {
                    txt01 = editTextPicker01.getText().toString();
                    txt02 = editTextPicker02.getText().toString();
                    txt03 = editTextPicker03.getText().toString();
                } else return;
                if ((!editTextPicker01.isRangeTextValidate() || txt01.trim().equals("44") || txt01.trim().equals("97")) ||
                        (!editTextPicker02.isRangeTextValidate() || txt02.trim().equals("44") || txt02.trim().equals("97")) ||
                        (!editTextPicker03.isRangeTextValidate() || txt03.trim().equals("44") || txt03.trim().equals("97")))
                    return;
                int day = Integer.parseInt(txt01);
                int month = Integer.parseInt(txt02);
                int year = Integer.parseInt(txt03);
                AgeModel age = DateRepository.Companion.getCalculatedAge(year, month, day);
                if (age == null) {
                    editTextPicker03.setError("Invalid date!!");
                    imFlag = false;
                } else {
                    imFlag = true;
                    editTextPicker01.setEnabled(false);
                    editTextPicker02.setEnabled(false);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });


    }

    private void setupListeners() {

        Clear.clearAllFields(bi.fldGrpSecChc2, getIntent().getBooleanExtra(IM02FLAG, true));

        bi.im05.setOnCheckedChangeListener((radioGroup, i) -> {
            if (i != bi.im051.getId()) {
                /*bi.qIm06.setEnabled(false);
                Clear.clearAllFields(bi.qIm08, false);*/
                Clear.clearAllFields(bi.fldGrpCVim06, false);
                Clear.clearAllFields(bi.fldGrpSecChc3, false);
                Clear.clearAllFields(bi.fldGrpCVim08, false);
                Clear.clearAllFields(bi.fldGrpCVim23, true);
                Clear.clearAllFields(bi.fldGrpCVim23a, true);
            } else {
                Clear.clearAllFields(bi.fldGrpCVim06, true);
                Clear.clearAllFields(bi.fldGrpSecChc3, true);
                Clear.clearAllFields(bi.fldGrpCVim08, true);
                Clear.clearAllFields(bi.fldGrpCVim23, false);
                Clear.clearAllFields(bi.fldGrpCVim23a, false);
            }
        });

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

        JSONObject json = new JSONObject();
        json.put("im41bcgdd", bi.im41bcgdd.getText().toString());
        json.put("im41bcgmm", bi.im41bcgmm.getText().toString());
        json.put("im41bcgyy", bi.im41bcgyy.getText().toString());
        json.put("im42opv0dd", bi.im42opv0dd.getText().toString());
        json.put("im42opv0mm", bi.im42opv0mm.getText().toString());
        json.put("im42opv0yy", bi.im42opv0yy.getText().toString());
        json.put("im43opv1dd", bi.im43opv1dd.getText().toString());
        json.put("im43opv1mm", bi.im43opv1mm.getText().toString());
        json.put("im43opv1yy", bi.im43opv1yy.getText().toString());
        json.put("im44penta1dd", bi.im44penta1dd.getText().toString());
        json.put("im44penta1mm", bi.im44penta1mm.getText().toString());
        json.put("im44penta1yy", bi.im44penta1yy.getText().toString());
        json.put("im45pcv1dd", bi.im45pcv1dd.getText().toString());
        json.put("im45pcv1mm", bi.im45pcv1mm.getText().toString());
        json.put("im45pcv1yy", bi.im45pcv1yy.getText().toString());
        json.put("im46rv1dd", bi.im46rv1dd.getText().toString());
        json.put("im46rv1mm", bi.im46rv1mm.getText().toString());
        json.put("im46rv1yy", bi.im46rv1yy.getText().toString());
        json.put("im47opv2dd", bi.im47opv2dd.getText().toString());
        json.put("im47opv2mm", bi.im47opv2mm.getText().toString());
        json.put("im47opv2yy", bi.im47opv2yy.getText().toString());
        json.put("im48penta2dd", bi.im48penta2dd.getText().toString());
        json.put("im48penta2mm", bi.im48penta2mm.getText().toString());
        json.put("im48penta2yy", bi.im48penta2yy.getText().toString());
        json.put("im49pcv2dd", bi.im49pcv2dd.getText().toString());
        json.put("im49pcv2mm", bi.im49pcv2mm.getText().toString());
        json.put("im49pcv2yy", bi.im49pcv2yy.getText().toString());
        json.put("im410rv2dd", bi.im410rv2dd.getText().toString());
        json.put("im410rv2mm", bi.im410rv2mm.getText().toString());
        json.put("im410rv2yy", bi.im410rv2yy.getText().toString());
        json.put("im411opv3dd", bi.im411opv3dd.getText().toString());
        json.put("im411opv3mm", bi.im411opv3mm.getText().toString());
        json.put("im411opv3yy", bi.im411opv3yy.getText().toString());
        json.put("im412penta3dd", bi.im412penta3dd.getText().toString());
        json.put("im412penta3mm", bi.im412penta3mm.getText().toString());
        json.put("im412penta3yy", bi.im412penta3yy.getText().toString());
        json.put("im413pcv3dd", bi.im413pcv3dd.getText().toString());
        json.put("im413pcv3mm", bi.im413pcv3mm.getText().toString());
        json.put("im413pcv3yy", bi.im413pcv3yy.getText().toString());
        json.put("im414ipvdd", bi.im414ipvdd.getText().toString());
        json.put("im414ipvmm", bi.im414ipvmm.getText().toString());
        json.put("im414ipvyy", bi.im414ipvyy.getText().toString());
        json.put("im415measles1dd", bi.im415measles1dd.getText().toString());
        json.put("im415measles1mm", bi.im415measles1mm.getText().toString());
        json.put("im415measles1yy", bi.im415measles1yy.getText().toString());
        json.put("im416measles2dd", bi.im416measles2dd.getText().toString());
        json.put("im416measles2mm", bi.im416measles2mm.getText().toString());
        json.put("im416measles2yy", bi.im416measles2yy.getText().toString());
        json.put("im05",
                bi.im051.isChecked() ? "1" :
                        bi.im052.isChecked() ? "2" :
                                bi.im053.isChecked() ? "98" :
                                        "0");
        json.put("im06",
                bi.im061.isChecked() ? "1" :
                        bi.im062.isChecked() ? "2" :
                                "0");
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


        try {
            JSONObject json_merge = JSONUtils.mergeJSONObjects(new JSONObject(child.getsCC()), json);

            child.setsCC(String.valueOf(json_merge));

        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    private boolean formValidation() {
        if (!imFlag) {
            Toast.makeText(this, "Invalid date!!", Toast.LENGTH_SHORT).show();
            return false;
        }
        return Validator.emptyCheckingContainer(this, bi.fldGrpSectionCHD);
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
        openChildEndActivity(this);
    }

    @Override
    public void onBackPressed() {
        Toast.makeText(this, "Back Press Not Allowed", Toast.LENGTH_SHORT).show();
    }

}
