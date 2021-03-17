package edu.aku.hassannaqvi.tpvics_hh.ui.sections;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.validatorcrawler.aliazaz.Validator;

import org.jetbrains.annotations.NotNull;
import org.json.JSONException;
import org.json.JSONObject;
import org.threeten.bp.Instant;
import org.threeten.bp.LocalDateTime;
import org.threeten.bp.ZoneId;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Locale;

import edu.aku.hassannaqvi.tpvics_hh.R;
import edu.aku.hassannaqvi.tpvics_hh.core.MainApp;
import edu.aku.hassannaqvi.tpvics_hh.database.DatabaseHelper;
import edu.aku.hassannaqvi.tpvics_hh.databinding.ActivitySectionChBBinding;
import edu.aku.hassannaqvi.tpvics_hh.models.ChildContract;
import edu.aku.hassannaqvi.tpvics_hh.utils.EndSectionActivity;
import edu.aku.hassannaqvi.tpvics_hh.utils.datecollection.AgeModel;
import edu.aku.hassannaqvi.tpvics_hh.utils.datecollection.DateRepository;

import static edu.aku.hassannaqvi.tpvics_hh.core.MainApp.child;
import static edu.aku.hassannaqvi.tpvics_hh.utils.AppUtilsKt.openChildEndActivity;
import static edu.aku.hassannaqvi.tpvics_hh.utils.AppUtilsKt.openWarningActivity;

public class SectionCHBActivity extends AppCompatActivity implements EndSectionActivity {

    ActivitySectionChBBinding bi;
    boolean dtFlag = false, edGrade = true, edGrade02 = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        bi = DataBindingUtil.setContentView(this, R.layout.activity_section_ch_b);
        bi.setCallback(this);

        setupSkips();

        if (child.getLocalDate() != null) {
            int maxYears = child.getLocalDate().getYear();
            int minYears = child.getLocalDate().minusYears(5).getYear();
            bi.cb03yy.setMinvalue(minYears);
            bi.cb03yy.setMaxvalue(maxYears);
        }

    }

    private void setupSkips() {

        bi.cb01a.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                bi.cb01b.setText(null);
                if (TextUtils.isEmpty(bi.cb01a.getText())) return;
                if (bi.cb01a.getText().toString().trim().length() > 0 && Integer.parseInt(bi.cb01a.getText().toString()) == 77) {
                    bi.cb01b.setEnabled(true);
                    edGrade = false;
                } else {
                    bi.cb01b.setEnabled(false);
                    edGrade = true;
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        bi.cb01b.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (TextUtils.isEmpty(bi.cb01b.getText())) return;
                edGrade = bi.cb01b.getText().toString().equals("88") || bi.cb01b.getText().toString().equals("77");
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (!edGrade) bi.cb01b.setError("Invalid grade");
                else bi.cb01b.setError(null);
            }
        });


        bi.cb02a.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                bi.cb02b.setText(null);
                if (TextUtils.isEmpty(bi.cb02a.getText())) return;
                if (bi.cb02a.getText().toString().trim().length() > 0 && Integer.parseInt(bi.cb02a.getText().toString()) == 77) {
                    bi.cb02b.setEnabled(true);
                    edGrade02 = false;
                } else {
                    bi.cb02b.setEnabled(false);
                    edGrade02 = true;
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        bi.cb02b.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (TextUtils.isEmpty(bi.cb02b.getText())) return;
                edGrade02 = bi.cb02b.getText().toString().equals("88") || bi.cb02b.getText().toString().equals("77");
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (!edGrade02) bi.cb02b.setError("Invalid grade");
                else bi.cb02b.setError(null);
            }
        });

        EditText[] txtListener = new EditText[]{bi.cb03dd, bi.cb03mm};
        for (EditText txtItem : txtListener) {

            txtItem.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    bi.cb04mm.setText(null);
                    bi.cb04yy.setText(null);
                    bi.cb03yy.setText(null);
                }

                @Override
                public void afterTextChanged(Editable s) {

                }
            });

        }

    }

    private boolean UpdateDB() {
        DatabaseHelper db = MainApp.appInfo.getDbHelper();
        int updcount = db.updatesChildColumn(ChildContract.ChildTable.COLUMN_SCB, child.getsCB());
        if (updcount == 1) {
            return true;
        } else {
            Toast.makeText(this, "Updating Database... ERROR!", Toast.LENGTH_SHORT).show();
            return false;
        }
    }

    private void SaveDraft() throws JSONException {

        JSONObject f1 = new JSONObject();

        f1.put("cb01a", bi.cb01a.getText().toString());
        f1.put("cb01b", bi.cb01b.getText().toString());
        //f1.put("cb01dk", bi.cb01dk.isChecked() ? "98" : "0");

        f1.put("cb02a", bi.cb02a.getText().toString());
        f1.put("cb02b", bi.cb02b.getText().toString());
        // f1.put("cb02dk", bi.cb02dk.isChecked() ? "98" : "0");

        f1.put("cb03dd", bi.cb03dd.getText().toString());
        f1.put("cb03mm", bi.cb03mm.getText().toString());
        f1.put("cb03yy", bi.cb03yy.getText().toString());

        f1.put("cb04mm", bi.cb04mm.getText().toString());
        f1.put("cb04yy", bi.cb04yy.getText().toString());

        child.setagem(bi.cb04mm.getText().toString());
        child.setagey(bi.cb04yy.getText().toString());

        child.setsCB(String.valueOf(f1));

        if (child.getCalculatedDOB() == null)
            child.setCalculatedDOB(DateRepository.Companion.getDOBFromAge(Integer.parseInt(bi.cb04yy.getText().toString()), Integer.parseInt(bi.cb04mm.getText().toString()), 15));
    }

    private boolean formValidation() {
        if (!Validator.emptyCheckingContainer(this, bi.fldGrpSectionCHB))
            return false;
        if (!edGrade || !edGrade02) {
            Toast.makeText(this, "Invalid Religious Grade!", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (!dtFlag) {
            Toast.makeText(this, "Invalid date!", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    public void BtnContinue() {

        if (formValidation()) {
            //Calculate months
            int totalMonths = Integer.parseInt(bi.cb04mm.getText().toString()) + Integer.parseInt(bi.cb04yy.getText().toString()) * 12;
            boolean monthFlag = totalMonths >= 12 && totalMonths < 24;
            if (monthFlag) {
                try {
                    SaveDraft();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                if (UpdateDB()) {
                    finish();
                    startActivity(new Intent(this, SectionCHCActivity.class));
                } else {
                    Toast.makeText(this, "Failed to Update Database!", Toast.LENGTH_SHORT).show();
                }
            } else
                openWarningActivity(this, "Current Child age leads to End this form.\nDo you want to Continue?");
        }
    }

    public void BtnEnd() {
        openChildEndActivity(this);
    }

    @Override
    public void onBackPressed() {
        Toast.makeText(this, "Back Press Not Allowed", Toast.LENGTH_SHORT).show();
    }

    public void cb03yyOnTextChanged(CharSequence s, int start, int before, int count) {
        bi.cb04mm.setEnabled(false);
        bi.cb04mm.setText(null);
        bi.cb04yy.setEnabled(false);
        bi.cb04yy.setText(null);
        child.setCalculatedDOB(null);
        if (TextUtils.isEmpty(bi.cb03dd.getText()) || TextUtils.isEmpty(bi.cb03mm.getText()) || TextUtils.isEmpty(bi.cb03yy.getText()))
            return;
        if (!bi.cb03dd.isRangeTextValidate() || !bi.cb03mm.isRangeTextValidate() || !bi.cb03yy.isRangeTextValidate())
            return;
        if (bi.cb03dd.getText().toString().equals("98") && bi.cb03mm.getText().toString().equals("98") && bi.cb03yy.getText().toString().equals("9998")) {
            bi.cb04mm.setEnabled(true);
            bi.cb04yy.setEnabled(true);
            dtFlag = true;
            return;
        }
        int day = bi.cb03dd.getText().toString().equals("98") ? 15 : Integer.parseInt(bi.cb03dd.getText().toString());
        int month = Integer.parseInt(bi.cb03mm.getText().toString());
        int year = Integer.parseInt(bi.cb03yy.getText().toString());

        AgeModel age;
        if (child.getLocalDate() != null)
            age = DateRepository.Companion.getCalculatedAge(child.getLocalDate(), year, month, day);
        else
            age = DateRepository.Companion.getCalculatedAge(year, month, day);
        if (age == null) {
            bi.cb03yy.setError("Invalid date!!");
            dtFlag = false;
            return;
        }
        dtFlag = true;
        bi.cb04mm.setText(String.valueOf(age.getMonth()));
        bi.cb04yy.setText(String.valueOf(age.getYear()));

        //Setting Date
        try {
            Instant instant = Instant.parse(new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH).format(new SimpleDateFormat("dd-MM-yyyy", Locale.ENGLISH).parse(
                    day + "-" + month + "-" + year
            )) + "T06:24:01Z");
            child.setCalculatedDOB(LocalDateTime.ofInstant(instant, ZoneId.systemDefault()).toLocalDate());
        } catch (ParseException e) {
            e.printStackTrace();
        }

    }

    public void showTooltip(@NotNull View view) {
        if (view.getId() != View.NO_ID) {
            String package_name = getApplicationContext().getPackageName();

            // Question Number Textview ID must be prefixed with q_ e.g.: 'q_aa12a'
            String infoid = view.getResources().getResourceName(view.getId()).replace(package_name + ":id/q_", "");

            // Question info text must be suffixed with _info e.g.: aa12a_info
            int stringRes = this.getResources().getIdentifier(infoid + "_info", "string", getApplicationContext().getPackageName());

            // Fetch info text from strings.xml
            //String infoText = (String) getResources().getText(stringRes);

            // Check if string resource exists to avoid crash on missing info string
            if (stringRes != 0) {

                // Fetch info text from strings.xml
                String infoText = (String) getResources().getText(stringRes);

                new AlertDialog.Builder(this)
                        .setTitle("Info: " + infoid.toUpperCase())
                        .setMessage(infoText)
                        .setIcon(android.R.drawable.ic_dialog_info)
                        .show();
            } else {
                Toast.makeText(this, "No information available on this question.", Toast.LENGTH_SHORT).show();
            }

        } else {
            Toast.makeText(this, "No ID Associated with this question.", Toast.LENGTH_SHORT).show();

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
            startActivity(new Intent(this, SectionCHCActivity.class));
        } else {
            Toast.makeText(this, "Failed to Update Database!", Toast.LENGTH_SHORT).show();
        }

    }
}
