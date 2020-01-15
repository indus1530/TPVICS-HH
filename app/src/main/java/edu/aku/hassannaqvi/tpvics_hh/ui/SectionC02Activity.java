package edu.aku.hassannaqvi.tpvics_hh.ui;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.content.DialogInterface;
import android.os.Bundle;
import android.widget.RadioGroup;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

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
            try {
                SaveDraft();
            } catch (JSONException e) {
                e.printStackTrace();
            }
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

    private void SaveDraft() throws JSONException {
        JSONObject f4 = new JSONObject();


        //checkbox hs16
        f4.put("hs16a", bi.hs16a.isChecked() ? "1" : "0");
        f4.put("hs16b", bi.hs16b.isChecked() ? "2" : "0");
        f4.put("hs16c", bi.hs16c.isChecked() ? "3" : "0");
        f4.put("hs16d", bi.hs16d.isChecked() ? "4" : "0");
        f4.put("hs16e", bi.hs16e.isChecked() ? "5" : "0");
        f4.put("hs16f", bi.hs16f.isChecked() ? "6" : "0");
        f4.put("hs1696", bi.hs1696.isChecked() ? "96" : "0");
        f4.put("hs1696x", bi.hs1696x.getText().toString());

        //hs17
        f4.put("hs17", bi.hs17a.isChecked() ?"1"
                : bi.hs17b.isChecked() ?"2"
                : bi.hs17c.isChecked() ?"3"
                : bi.hs17d.isChecked() ?"4"
                : bi.hs17e.isChecked() ?"5"
                : bi.hs17f.isChecked() ?"6"
                : bi.hs17g.isChecked() ?"7"
                : bi.hs17h.isChecked() ?"8"
                : bi.hs17i.isChecked() ?"9"
                : bi.hs17j.isChecked() ?"10"
                : bi.hs17k.isChecked() ?"11"
                : bi.hs1796.isChecked() ?"96"
                : "0");
        f4.put("hs1796x", bi.hs1796x.getText().toString());

        //hs18
        f4.put("hs18", bi.hs18a.isChecked() ?"11"
                : bi.hs18b.isChecked() ?"12"
                : bi.hs18c.isChecked() ?"21"
                : bi.hs18d.isChecked() ?"22"
                : bi.hs18e.isChecked() ?"31"
                : bi.hs18f.isChecked() ?"32"
                : bi.hs18g.isChecked() ?"33"
                : bi.hs18h.isChecked() ?"34"
                : bi.hs18i.isChecked() ?"35"
                : bi.hs18j.isChecked() ?"36"
                : bi.hs18k.isChecked() ?"37"
                : bi.hs1896.isChecked() ?"96"
                : "0");
        f4.put("hs1896x", bi.hs1896x.getText().toString());

        //hs19
        f4.put("hs19", bi.hs19a.isChecked() ?"11"
                : bi.hs19b.isChecked() ?"12"
                : bi.hs19c.isChecked() ?"13"
                : bi.hs19d.isChecked() ?"21"
                : bi.hs19e.isChecked() ?"22"
                : bi.hs19f.isChecked() ?"23"
                : bi.hs19g.isChecked() ?"24"
                : bi.hs19h.isChecked() ?"31"
                : bi.hs19i.isChecked() ?"32"
                : bi.hs19j.isChecked() ?"33"
                : bi.hs19k.isChecked() ?"34"
                : bi.hs19l.isChecked() ?"35"
                : bi.hs19m.isChecked() ?"36"
                : bi.hs19n.isChecked() ?"37"
                : bi.hs1996.isChecked() ?"96"
                : "0");
        f4.put("hs1996x", bi.hs1996x.getText().toString());

        //hs20
        f4.put("hs20", bi.hs20a.isChecked() ?"11"
                : bi.hs20b.isChecked() ?"12"
                : bi.hs20c.isChecked() ?"13"
                : bi.hs20d.isChecked() ?"21"
                : bi.hs20e.isChecked() ?"22"
                : bi.hs20f.isChecked() ?"23"
                : bi.hs20g.isChecked() ?"24"
                : bi.hs20h.isChecked() ?"25"
                : bi.hs20i.isChecked() ?"26"
                : bi.hs20j.isChecked() ?"27"
                : bi.hs20k.isChecked() ?"31"
                : bi.hs20l.isChecked() ?"32"
                : bi.hs20m.isChecked() ?"33"
                : bi.hs20n.isChecked() ?"34"
                : bi.hs20o.isChecked() ?"35"
                : bi.hs20p.isChecked() ?"36"
                : bi.hs2096.isChecked() ?"96"
                : "0");
        f4.put("hs2096x", bi.hs2096x.getText().toString());

        //hs21
        f4.put("hs21", bi.hs21.getText().toString());

        //hs22
        f4.put("hs22", bi.hs22a.isChecked() ?"1"
                : bi.hs22b.isChecked() ?"2"
                : "0");

        //hs23
        f4.put("hs23a", bi.hs23a.getText().toString());
        f4.put("hs23b", bi.hs23b.getText().toString());
        f4.put("hs23", bi.hs23c.isChecked() ?"98" : "0");

        //hs24
        f4.put("hs24", bi.hs24a.isChecked() ?"1"
                : bi.hs24b.isChecked() ?"2"
                : "0");

        //hs25
        f4.put("hs25a", bi.hs25a.getText().toString());
        f4.put("hs25b", bi.hs25b.getText().toString());
        f4.put("hs25c", bi.hs25c.getText().toString());
        f4.put("hs25d", bi.hs25d.getText().toString());
        f4.put("hs25e", bi.hs25e.getText().toString());
        f4.put("hs25f", bi.hs25f.getText().toString());
        f4.put("hs25g", bi.hs25g.getText().toString());

        //hs26
        f4.put("hs26", bi.hs26a.isChecked() ?"1"
                : bi.hs26b.isChecked() ?"2"
                : "0");

        //hs27
        f4.put("hs27", bi.hs27.getText().toString());


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
