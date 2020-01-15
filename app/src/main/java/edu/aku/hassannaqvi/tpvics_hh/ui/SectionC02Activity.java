package edu.aku.hassannaqvi.tpvics_hh.ui;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.content.DialogInterface;
import android.os.Bundle;
import android.widget.RadioGroup;
import android.widget.Toast;

import org.json.JSONException;

import edu.aku.hassannaqvi.tpvics_hh.R;
import edu.aku.hassannaqvi.tpvics_hh.core.MainApp;
import edu.aku.hassannaqvi.tpvics_hh.databinding.ActivitySectionC02Binding;
import edu.aku.hassannaqvi.tpvics_hh.validation.ClearClass;
import edu.aku.hassannaqvi.tpvics_hh.validation.ValidatorClass;

public class SectionC02Activity extends AppCompatActivity {

    private ActivitySectionC02Binding bi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        bi = DataBindingUtil.setContentView(this, R.layout.activity_section_c02);
        bi.setCallback(this);

        setListeners();
    }


    private void setListeners() {

        bi.hs22.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {

                if (bi.hs22b.isChecked()) {
                    ClearClass.ClearAllFields(bi.fldGrpCVhs23, null);
                }
            }
        });

        bi.hs24.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {

                if (bi.hs24b.isChecked()) {
                    ClearClass.ClearAllFields(bi.fldGrpCVhs25, null);
                }
            }
        });
    }

    private boolean formValidation() {
        return ValidatorClass.EmptyCheckingContainer(this, bi.fldGrpSecC02);
    }

    public void BtnContinue() {
        if (formValidation()) {
            SaveDraft();
            if (UpdateDB()) {
                setResult(RESULT_OK);
                finish();
            } else {
                Toast.makeText(this, "Complete", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private boolean UpdateDB() {

        return true;
    }

    private void SaveDraft() {
    }


    public void BtnEnd() {

        new AlertDialog.Builder(this)
                .setTitle("END INTERVIEW")
                .setIcon(R.drawable.ic_power_settings_new_black_24dp)
                .setCancelable(false)
                .setCancelable(false)
                .setMessage("Do you want to End Interview??")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        SaveDraft();
                        if (!UpdateDB()) {
                            Toast.makeText(SectionC02Activity.this, "Error in updating db!!", Toast.LENGTH_SHORT).show();
                            return;
                        }

                        MainApp.endActivitySetRouting(SectionC02Activity.this, SectionC02Activity.this, EndingActivity.class, false, null);
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                })
                .show();
    }
}
