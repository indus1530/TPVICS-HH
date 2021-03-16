package edu.aku.hassannaqvi.tpvics_hh.ui.sections;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.validatorcrawler.aliazaz.Clear;
import com.validatorcrawler.aliazaz.Validator;

import org.json.JSONException;
import org.json.JSONObject;
import org.threeten.bp.Instant;
import org.threeten.bp.LocalDateTime;
import org.threeten.bp.ZoneId;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import edu.aku.hassannaqvi.tpvics_hh.R;
import edu.aku.hassannaqvi.tpvics_hh.core.MainApp;
import edu.aku.hassannaqvi.tpvics_hh.database.DatabaseHelper;
import edu.aku.hassannaqvi.tpvics_hh.databinding.ActivitySectionChCBinding;
import edu.aku.hassannaqvi.tpvics_hh.models.ChildContract;
import edu.aku.hassannaqvi.tpvics_hh.ui.other.ChildEndingActivity;
import edu.aku.hassannaqvi.tpvics_hh.ui.other.TakePhoto;
import edu.aku.hassannaqvi.tpvics_hh.utils.DateUtils;
import edu.aku.hassannaqvi.tpvics_hh.utils.EndSectionActivity;
import edu.aku.hassannaqvi.tpvics_hh.utils.datecollection.AgeModel;
import edu.aku.hassannaqvi.tpvics_hh.utils.datecollection.DateRepository;
import kotlin.Pair;

import static edu.aku.hassannaqvi.tpvics_hh.CONSTANTS.CHILD_ENDING_AGE_ISSUE;
import static edu.aku.hassannaqvi.tpvics_hh.CONSTANTS.IM01FLAG;
import static edu.aku.hassannaqvi.tpvics_hh.CONSTANTS.IM02CARDSEEN;
import static edu.aku.hassannaqvi.tpvics_hh.core.MainApp.child;
import static edu.aku.hassannaqvi.tpvics_hh.utils.AppUtilsKt.openChildEndActivity;
import static edu.aku.hassannaqvi.tpvics_hh.utils.AppUtilsKt.openWarningActivity;

public class SectionCHCActivity extends AppCompatActivity implements EndSectionActivity {

    ActivitySectionChCBinding bi;
    boolean im01Flag = false, imFlag = true;
    Instant dtInstant = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        bi = DataBindingUtil.setContentView(this, R.layout.activity_section_ch_c);
        bi.setCallback(this);

        setupListeners();

        /*if (child.getCalculatedDOB() != null) {
            int maxYears = child.getCalculatedDOB().getYear();
            int minYears = child.getCalculatedDOB().minusYears(2).getYear();
            setYearOfBirth(minYears, maxYears);
        } else */
        if (child.getLocalDate() != null) {
            int maxYears = child.getLocalDate().getYear();
            int minYears = child.getLocalDate().minusYears(2).getYear();
            setYearOfBirth(minYears, maxYears);
        }

    }

    private void setYearOfBirth(int minYears, int maxYears) {
        bi.im04yy.setMinvalue(minYears);
        bi.im04yy.setMaxvalue(maxYears);
    }

    private void setupListeners() {


        bi.im02.setOnCheckedChangeListener(((radioGroup, i) -> {
            if (i == bi.im021.getId()) {
                Clear.clearAllFields(bi.fldGrpCVim03, false);
                Clear.clearAllFields(bi.fldGrpCVim04, true);
                Clear.clearAllFields(bi.fldGrpSecChc1, true);
                bi.frontPhoto.setEnabled(true);
                bi.backPhoto.setEnabled(true);
                bi.frontPhoto.setBackground(getResources().getDrawable(R.drawable.outline_btn));
                bi.backPhoto.setBackground(getResources().getDrawable(R.drawable.outline_btn));
                bi.frontFileName.setText(null);
                bi.backFileName.setText(null);
                im01Flag = false;
            } else {
                Clear.clearAllFields(bi.fldGrpCVim03, false);
                Clear.clearAllFields(bi.fldGrpCVim04, false);
                Clear.clearAllFields(bi.fldGrpSecChc1, false);
                bi.frontPhoto.setEnabled(false);
                bi.backPhoto.setEnabled(false);
                bi.frontPhoto.setBackground(null);
                bi.backPhoto.setBackground(null);
                bi.frontFileName.setText(null);
                bi.backFileName.setText(null);
                im01Flag = true;
            }

        }));

        bi.im01.setOnCheckedChangeListener((radioGroup, i) -> {
            Clear.clearAllFields(bi.fldGrpCVim02, i == bi.im011.getId());
            Clear.clearAllFields(bi.fldGrpSecChc1, i == bi.im011.getId());
            Clear.clearAllFields(bi.fldGrpCVim03, i == bi.im012.getId());

            bi.frontPhoto.setEnabled(i == bi.im011.getId());
            bi.backPhoto.setEnabled(i == bi.im011.getId());
            bi.frontPhoto.setBackground(getResources().getDrawable(R.drawable.outline_btn));
            bi.backPhoto.setBackground(getResources().getDrawable(R.drawable.outline_btn));
            bi.frontFileName.setText(null);
            bi.backFileName.setText(null);

            im01Flag = i == bi.im012.getId();
        });

        bi.im04yy.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                dtInstant = null;
                if (!bi.im021.isChecked() || bi.im0497.isChecked()) return;
                String txt01, txt02, txt03;
                bi.im04dd.setEnabled(true);
                bi.im04mm.setEnabled(true);
                if (!TextUtils.isEmpty(bi.im04dd.getText()) && !TextUtils.isEmpty(bi.im04mm.getText()) && !TextUtils.isEmpty(bi.im04yy.getText())) {
                    txt01 = bi.im04dd.getText().toString();
                    txt02 = bi.im04mm.getText().toString();
                    txt03 = bi.im04yy.getText().toString();
                } else return;
                if ((!bi.im04dd.isRangeTextValidate()) ||
                        (!bi.im04mm.isRangeTextValidate()) ||
                        (!bi.im04yy.isRangeTextValidate()))
                    return;
                int day = bi.im04dd.getText().toString().equals("98") ? 15 : Integer.parseInt(txt01);
                int month = Integer.parseInt(txt02);
                int year = Integer.parseInt(txt03);

                AgeModel age;
                /*if (child.getCalculatedDOB() != null)
                    age = DateRepository.Companion.getCalculatedAge(child.getCalculatedDOB(), year, month, day);
                else */
                if (child.getLocalDate() != null)
                    age = DateRepository.Companion.getCalculatedAge(child.getLocalDate(), year, month, day);
                else
                    age = DateRepository.Companion.getCalculatedAge(year, month, day);
                if (age == null) {
                    bi.im04yy.setError("Invalid date!!");
                    imFlag = false;
                } else {
                    imFlag = true;
                    bi.im04dd.setEnabled(false);
                    bi.im04mm.setEnabled(false);

//                    if (child.getCalculatedDOB() == null) {
                    //Setting Date
                    try {
                        dtInstant = Instant.parse(new SimpleDateFormat("yyyy-MM-dd").format(new SimpleDateFormat("dd-MM-yyyy").parse(
                                day + "-" + month + "-" + year
                        )) + "T06:24:01Z");

                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
//                    }

                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        bi.im0497.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                Clear.clearAllFields(bi.fldGrpim04DT, false);
                imFlag = true;
            } else
                Clear.clearAllFields(bi.fldGrpim04DT, true);
        });

    }

    private boolean UpdateDB() {
        DatabaseHelper db = MainApp.appInfo.getDbHelper();
        int updcount = db.updatesChildColumn(ChildContract.ChildTable.COLUMN_SCC, child.getsCC());
        if (updcount == 1) {
            return true;
        } else {
            Toast.makeText(this, "Updating Database... ERROR!", Toast.LENGTH_SHORT).show();
            return false;
        }
    }

    private void SaveDraft() throws JSONException {

        JSONObject f1 = new JSONObject();

        f1.put("im02",
                bi.im021.isChecked() ? "1" :
                        bi.im022.isChecked() ? "2" :
                                bi.im023.isChecked() ? "3" :
                                        "0");
        f1.put("im01",
                bi.im011.isChecked() ? "1" :
                        bi.im012.isChecked() ? "2" :
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
        f1.put("im0396x", bi.im0396x.getText().toString());

        f1.put("im04dd", bi.im04dd.getText().toString());
        f1.put("im04mm", bi.im04mm.getText().toString());
        f1.put("im04yy", bi.im04yy.getText().toString());
        f1.put("im0497", bi.im0497.isChecked() ? "97" : "0");
        f1.put("frontFileName", bi.frontFileName.getText().toString());
        f1.put("backFileName", bi.backFileName.getText().toString());

        child.setsCC(String.valueOf(f1));

        if (dtInstant != null)
            child.setCalculatedDOB(LocalDateTime.ofInstant(dtInstant, ZoneId.systemDefault()).toLocalDate());
    }

    private boolean formValidation() {
        if (!imFlag) {
            Toast.makeText(this, "Invalid date!", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (bi.im021.isChecked() && (TextUtils.isEmpty(bi.frontFileName.getText()) || TextUtils.isEmpty(bi.backFileName.getText()))) {
            Toast.makeText(this, "No Photos attached", Toast.LENGTH_SHORT).show();
            return false;
        }
        return Validator.emptyCheckingContainer(this, bi.fldGrpSectionCHC);
    }

    public void BtnContinue() {

        if (formValidation()) {
            //Calculate months
            boolean monthFlag = true;
            if (child.getCalculatedDOB() != null || dtInstant != null) {
                Pair<String, String> month_year;
                if (bi.im021.isChecked() && dtInstant != null && !bi.im0497.isChecked())
                    month_year = getMonthAndYearFromDate(LocalDateTime.ofInstant(dtInstant, ZoneId.systemDefault()).toLocalDate().toString());
                else month_year = getMonthAndYearFromDate(child.getCalculatedDOB().toString());
                int totalMonths = Integer.parseInt(month_year.getFirst()) + Integer.parseInt(month_year.getSecond()) * 12;
                monthFlag = totalMonths >= 12 && totalMonths < 24;
            }
            if (monthFlag) {
                try {
                    SaveDraft();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                if (UpdateDB()) {
                    finish();
                    startActivity(new Intent(this, SectionCHDActivity.class).putExtra(IM01FLAG, !im01Flag).putExtra(IM02CARDSEEN, bi.im021.isChecked()));
                } else {
                    Toast.makeText(this, "Failed to Update Database!", Toast.LENGTH_SHORT).show();
                }
            } else
                openWarningActivity(this, "Current Child age leads to End this form.\nDo you want to Continue?");

        }

    }

    private Pair<String, String> getMonthAndYearFromDate(String date) {
        Calendar cal = DateUtils.getCalendarDate(date.replace("-", "/"));
        int curdate = child.getLocalDate().getDayOfMonth();
        int curmonth = child.getLocalDate().getMonthValue();
        int curyear = child.getLocalDate().getYear();
        int day = cal.get(Calendar.DAY_OF_MONTH);
        int month = cal.get(Calendar.MONTH) + 1;
        int year = cal.get(Calendar.YEAR);

        if (day > curdate) {
            curmonth -= 1;
        }
        if (month > curmonth) {
            curyear -= 1;
            curmonth += 12;
        }
        return new Pair<>(String.valueOf(curmonth - month), String.valueOf(curyear - year));
    }

    public void BtnEnd() {
        openChildEndActivity(this);
    }

    @Override
    public void onBackPressed() {
        Toast.makeText(this, "Back Press Not Allowed", Toast.LENGTH_SHORT).show();
    }


    public void takePhoto(int id) {

        Intent intent = new Intent(this, TakePhoto.class);

        intent.putExtra("picID", MainApp.fc.getClusterCode() + "_" + MainApp.fc.getHhno() + "_" + MainApp.child.getChildSerial() + "_");
        intent.putExtra("childName", MainApp.child.getChildName());

/*
        intent.putExtra("picID", "901001" + "_" + "A-0001-001" + "_" + "1" + "_");
        intent.putExtra("childName", "Hassan");
*/

        if (id == 1) {
            intent.putExtra("picView", "front".toUpperCase());
            startActivityForResult(intent, 1); // Activity is started with requestCode 1 = Front
        } else {
            intent.putExtra("picView", "back".toUpperCase());
            startActivityForResult(intent, 2); // Activity is started with requestCode 2 = Back
        }
    }

    // Call Back method  to get the Message form other Activity
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode != RESULT_CANCELED) {
            Toast.makeText(this, requestCode + "_" + resultCode, Toast.LENGTH_SHORT).show();

            String fileName = data.getStringExtra("FileName");

            // Check if the requestCode 1 = Front : 2 = Back -- resultCode 1 = Success : 2 = Failure
            // Results received with requestCode 1 = Front

            if (requestCode == 1 && resultCode == 1) {
                Toast.makeText(this, "Photo Taken", Toast.LENGTH_SHORT).show();

                bi.frontFileName.setText(fileName);
                bi.frontPhoto.setEnabled(false);


            } else if (requestCode == 1 && resultCode != 1) {
                Toast.makeText(this, "Photo Cancelled", Toast.LENGTH_SHORT).show();

                //TODO: Implement functionality below when photo was not taken
                // ...
                bi.frontFileName.setText("Photo not taken.");

            }

            // Results received with requestCode 2 = Back
            if (requestCode == 2 && resultCode == 1) {
                Toast.makeText(this, "Photo Taken", Toast.LENGTH_SHORT).show();

                bi.backFileName.setText(fileName);
                bi.backPhoto.setEnabled(false);
            } else if (requestCode == 2 && resultCode != 1) {

                Toast.makeText(this, "Photo Cancelled", Toast.LENGTH_SHORT).show();

                //TODO: Implement functionality below when photo was not taken
                // ...
                bi.backFileName.setText("Photo not taken.");

            }
        }
    }

    @Override
    public void endSecActivity(boolean flag) {
        try {
            SaveDraft();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        if (UpdateDB()) {
            finish();
            startActivity(new Intent(this, ChildEndingActivity.class)
                    .putExtra(CHILD_ENDING_AGE_ISSUE, true));
        } else {
            Toast.makeText(this, "Failed to Update Database!", Toast.LENGTH_SHORT).show();
        }
    }
}
