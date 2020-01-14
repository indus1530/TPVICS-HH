package edu.aku.hassannaqvi.tpvics_hh.ui;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.validatorcrawler.aliazaz.Validator;

import org.json.JSONException;
import org.json.JSONObject;

import edu.aku.hassannaqvi.tpvics_hh.R;
import edu.aku.hassannaqvi.tpvics_hh.core.MainApp;
import edu.aku.hassannaqvi.tpvics_hh.databinding.ActivitySectionCBinding;
import edu.aku.hassannaqvi.tpvics_hh.validation.ClearClass;
import edu.aku.hassannaqvi.tpvics_hh.validation.ValidatorClass;

public class SectionCActivity extends AppCompatActivity {

    private ActivitySectionCBinding bi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        bi = DataBindingUtil.setContentView(this, R.layout.activity_section_c);
        bi.setCallback(this);

        setListeners();
    }

    private void setListeners() {

        bi.hs04.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {

                if (bi.hs04b.isChecked()) {
                    ClearClass.ClearAllFields(bi.fldGrpCVhs05, null);
                }
            }
        });

        bi.hs07.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {

                if (!bi.hs07h.isChecked() || !bi.hs07h.isChecked()) {
                    ClearClass.ClearAllFields(bi.fldGrpCVhs08, null);
                }
            }
        });

        bi.hs09.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {

                if (bi.hs09b.isChecked()) {
                    ClearClass.ClearAllFields(bi.fldGrpCVhs0x, null);
                }
            }
        });

        bi.hs11.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {

                if (bi.hs11b.isChecked()) {
                    ClearClass.ClearAllFields(bi.fldGrpCVhs12, null);
                }
            }
        });
    }

    private boolean formValidation() {
        return Validator.emptyCheckingContainer(this, bi.fldGrpSecC);
    }

    public void BtnContinue() {
        if (formValidation()) {
            try {
                SaveDraft();
            } catch (Exception e) {
                e.printStackTrace();
            }
            if (UpdateDB()) {
//                if (bi.td01b.isChecked()) {
//                    finish();
//                    startActivity(new Intent(this, ChildListActivity.class));
//                } else {
//                    finish();
//                    startActivity(new Intent(this, SectionDAActivity.class));
//                }

            } else {
                Toast.makeText(this, "Failed to Update Database!", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private boolean UpdateDB() {

        return true;
    }

    private void SaveDraft() throws JSONException {
        JSONObject f3 = new JSONObject();

        //hs01
        f3.put("hs01", bi.hs01a.isChecked() ? "1"
                : bi.hs01b.isChecked() ? "2"
                : bi.hs01c.isChecked() ? "3"
                : bi.hs01d.isChecked() ? "4"
                : bi.hs01e.isChecked() ? "5"
                : bi.hs01f.isChecked() ? "6"
                : bi.hs01g.isChecked() ? "7"
                : bi.hs0196.isChecked() ? "96"
                : "0");
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
                        try {
                            SaveDraft();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        if (!UpdateDB()) {
                            Toast.makeText(SectionCActivity.this, "Error in updating db!!", Toast.LENGTH_SHORT).show();
                            return;
                        }

                        MainApp.endActivitySetRouting(SectionCActivity.this, SectionCActivity.this, EndingActivity.class, false,null);
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
