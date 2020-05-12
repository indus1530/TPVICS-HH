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
import edu.aku.hassannaqvi.tpvics_hh.utils.JSONUtils;

import static edu.aku.hassannaqvi.tpvics_hh.CONSTANTS.IM02FLAG;
import static edu.aku.hassannaqvi.tpvics_hh.core.MainApp.child;
import static edu.aku.hassannaqvi.tpvics_hh.utils.UtilKt.openChildEndActivity;

public class SectionCHDActivity extends AppCompatActivity {

    ActivitySectionChDBinding bi;
    boolean imFlag = false, daysFlag = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        bi = DataBindingUtil.setContentView(this, R.layout.activity_section_ch_d);
        bi.setCallback(this);
        setTitle(R.string.im05title1);
        setupListeners();
        setupTextWatchers();

    }

    private void setupTextWatchers() {
        editTextImplementation(new EditTextPicker[]{bi.im0501dd, bi.im0501mm, bi.im0501yy});
        editTextImplementation(new EditTextPicker[]{bi.im0502dd, bi.im0502mm, bi.im0502yy});
        editTextImplementation(new EditTextPicker[]{bi.im0503dd, bi.im0503mm, bi.im0503yy});
        editTextImplementation(new EditTextPicker[]{bi.im0504dd, bi.im0504mm, bi.im0504yy});
        editTextImplementation(new EditTextPicker[]{bi.im0505dd, bi.im0505mm, bi.im0505yy});
        editTextImplementation(new EditTextPicker[]{bi.im0506dd, bi.im0506mm, bi.im0506yy});
        editTextImplementation(new EditTextPicker[]{bi.im0507dd, bi.im0507mm, bi.im0507yy});
        editTextImplementation(new EditTextPicker[]{bi.im0508dd, bi.im0508mm, bi.im0508yy});
        editTextImplementation(new EditTextPicker[]{bi.im0509dd, bi.im0509mm, bi.im0509yy});
        editTextImplementation(new EditTextPicker[]{bi.im0510dd, bi.im0510mm, bi.im0510yy});
        editTextImplementation(new EditTextPicker[]{bi.im0511dd, bi.im0511mm, bi.im0511yy});
        editTextImplementation(new EditTextPicker[]{bi.im0512dd, bi.im0512mm, bi.im0512yy});
        editTextImplementation(new EditTextPicker[]{bi.im0513dd, bi.im0513mm, bi.im0513yy});
        editTextImplementation(new EditTextPicker[]{bi.im0514dd, bi.im0514mm, bi.im0514yy});
        editTextImplementation(new EditTextPicker[]{bi.im0515dd, bi.im0515mm, bi.im0515yy});
        editTextImplementation(new EditTextPicker[]{bi.im0516dd, bi.im0516mm, bi.im0516yy});
    }

    public void editTextImplementation(EditTextPicker[] editTextsArray) {
        if (editTextsArray.length != 3) return;
        EditTextPicker editTextPicker01 = editTextsArray[0];
        EditTextPicker editTextPicker02 = editTextsArray[1];
        EditTextPicker editTextPicker03 = editTextsArray[2];

        editTextPicker01.addTextChangedListener(new TextWatcher() {
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

        editTextPicker01.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                editTextPicker03.setText(null);
                editTextPicker03.setError(null);

                editTextPicker02.setEnabled(true);
                editTextPicker03.setEnabled(true);

                daysFlag = true;
                imFlag = true;

                String txt01;
                if (!TextUtils.isEmpty(editTextPicker01.getText())) {
                    txt01 = editTextPicker01.getText().toString();

                    if (txt01.trim().equals("44") || txt01.trim().equals("97") || txt01.trim().equals("66") || txt01.trim().equals("86")) {
                        editTextPicker02.setText(null);
                        editTextPicker03.setText(null);
                        editTextPicker02.setEnabled(false);
                        editTextPicker03.setEnabled(false);
                        editTextPicker01.setRangedefaultvalue(Float.parseFloat(txt01));

                        daysFlag = false;
                        imFlag = true;
                    }
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        editTextPicker03.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String txt01, txt02, txt03;
                if (!daysFlag) return;
                editTextPicker01.setEnabled(true);
                editTextPicker02.setEnabled(true);
                if (!TextUtils.isEmpty(editTextPicker01.getText()) && !TextUtils.isEmpty(editTextPicker02.getText()) && !TextUtils.isEmpty(editTextPicker03.getText())) {
                    txt01 = editTextPicker01.getText().toString();
                    txt02 = editTextPicker02.getText().toString();
                    txt03 = editTextPicker03.getText().toString();
                } else return;
                if ((!editTextPicker01.isRangeTextValidate() || txt01.trim().equals("44") || txt01.trim().equals("97") || txt01.trim().equals("66") || txt01.trim().equals("86")) ||
                        (!editTextPicker02.isRangeTextValidate()) ||
                        (!editTextPicker03.isRangeTextValidate()))
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

        boolean flag = getIntent().getBooleanExtra(IM02FLAG, true);
        if (!flag) imFlag = true;
        Clear.clearAllFields(bi.fldGrpSecChc2, flag);

       /* bi.im06.setOnCheckedChangeListener((radioGroup, i) -> {
            if (i != bi.im061.getId()) {
                *//*bi.qim07.setEnabled(false);
                Clear.clearAllFields(bi.qIm08, false);*//*
                Clear.clearAllFields(bi.fldGrpCVim07, false);
                Clear.clearAllFields(bi.fldGrpSecChc3, false);
                Clear.clearAllFields(bi.fldGrpCVim08, false);
                Clear.clearAllFields(bi.fldGrpCVim23, true);
                Clear.clearAllFields(bi.fldGrpCVim23a, true);
            } else {
                Clear.clearAllFields(bi.fldGrpCVim07, true);
                Clear.clearAllFields(bi.fldGrpSecChc3, true);
                Clear.clearAllFields(bi.fldGrpCVim08, true);
                Clear.clearAllFields(bi.fldGrpCVim23, false);
                Clear.clearAllFields(bi.fldGrpCVim23a, false);
            }
        });*/

        bi.im07.setOnCheckedChangeListener((radioGroup, i) -> {
            if (i == bi.im071.getId()) {
                Clear.clearAllFields(bi.fldGrpCVim08, false);
                Clear.clearAllFields(bi.fldGrpSecChc3, false);
                Clear.clearAllFields(bi.fldGrpCVim23, true);
                Clear.clearAllFields(bi.fldGrpCVim23a, true);
            } else {
                Clear.clearAllFields(bi.fldGrpCVim08, true);
                Clear.clearAllFields(bi.fldGrpSecChc3, true);
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
            if (i == bi.im234.getId()) {
                Clear.clearAllFields(bi.fldGrpCVim23a, false);
            } else {
                Clear.clearAllFields(bi.fldGrpCVim23a, true);
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

        json.put("im0501dd", bi.im0501dd.getText().toString());
        json.put("im0501mm", bi.im0501mm.getText().toString());
        json.put("im0501yy", bi.im0501yy.getText().toString());
        json.put("im0502dd", bi.im0502dd.getText().toString());
        json.put("im0502mm", bi.im0502mm.getText().toString());
        json.put("im0502yy", bi.im0502yy.getText().toString());
        json.put("im0503dd", bi.im0503dd.getText().toString());
        json.put("im0503mm", bi.im0503mm.getText().toString());
        json.put("im0503yy", bi.im0503yy.getText().toString());
        json.put("im0504dd", bi.im0504dd.getText().toString());
        json.put("im0504mm", bi.im0504mm.getText().toString());
        json.put("im0504yy", bi.im0504yy.getText().toString());
        json.put("im0505dd", bi.im0505dd.getText().toString());
        json.put("im0505mm", bi.im0505mm.getText().toString());
        json.put("im0505yy", bi.im0505yy.getText().toString());
        json.put("im0506dd", bi.im0506dd.getText().toString());
        json.put("im0506mm", bi.im0506mm.getText().toString());
        json.put("im0506yy", bi.im0506yy.getText().toString());
        json.put("im0507dd", bi.im0507dd.getText().toString());
        json.put("im0507mm", bi.im0507mm.getText().toString());
        json.put("im0507yy", bi.im0507yy.getText().toString());
        json.put("im0508dd", bi.im0508dd.getText().toString());
        json.put("im0508mm", bi.im0508mm.getText().toString());
        json.put("im0508yy", bi.im0508yy.getText().toString());
        json.put("im0509dd", bi.im0509dd.getText().toString());
        json.put("im0509mm", bi.im0509mm.getText().toString());
        json.put("im0509yy", bi.im0509yy.getText().toString());
        json.put("im0510dd", bi.im0510dd.getText().toString());
        json.put("im0510mm", bi.im0510mm.getText().toString());
        json.put("im0510yy", bi.im0510yy.getText().toString());
        json.put("im0511dd", bi.im0511dd.getText().toString());
        json.put("im0511mm", bi.im0511mm.getText().toString());
        json.put("im0511yy", bi.im0511yy.getText().toString());
        json.put("im0512dd", bi.im0512dd.getText().toString());
        json.put("im0512mm", bi.im0512mm.getText().toString());
        json.put("im0512yy", bi.im0512yy.getText().toString());
        json.put("im0513dd", bi.im0513dd.getText().toString());
        json.put("im0513mm", bi.im0513mm.getText().toString());
        json.put("im0513yy", bi.im0513yy.getText().toString());
        json.put("im0514dd", bi.im0514dd.getText().toString());
        json.put("im0514mm", bi.im0514mm.getText().toString());
        json.put("im0514yy", bi.im0514yy.getText().toString());
        json.put("im0515dd", bi.im0515dd.getText().toString());
        json.put("im0515mm", bi.im0515mm.getText().toString());
        json.put("im0515yy", bi.im0515yy.getText().toString());
        json.put("im0516dd", bi.im0516dd.getText().toString());
        json.put("im0516mm", bi.im0516mm.getText().toString());
        json.put("im0516yy", bi.im0516yy.getText().toString());
       /* json.put("im06",
                bi.im061.isChecked() ? "1" :
                        bi.im062.isChecked() ? "2" :
                                bi.im063.isChecked() ? "98" :
                                        "0");*/
        json.put("im07",
                bi.im071.isChecked() ? "1" :
                        bi.im072.isChecked() ? "2" :
                                bi.im073.isChecked() ? "3" :
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
        json.put("im19", bi.im19.getText().toString());
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
        json.put("im23a96x", bi.im23a96x.getText().toString());
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
            Toast.makeText(this, "Invalid date!", Toast.LENGTH_SHORT).show();
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
                startActivity(new Intent(this, SectionCHEActivity.class));
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
