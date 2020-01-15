package edu.aku.hassannaqvi.tpvics_hh.ui;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
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
                    startActivity(new Intent(this, SectionC02Activity.class));
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
        f3.put("hs0196x", bi.hs0196x.getText().toString());

        //hs02
        f3.put("hs02", bi.hs02a.isChecked() ? "1"
                : bi.hs02b.isChecked() ? "2"
                : bi.hs0296.isChecked() ? "96"
                : "0");
        f3.put("hs0296x", bi.hs0296x.getText().toString());

        //hs03
        f3.put("hs03", bi.hs03a.isChecked() ?"11"
                : bi.hs03b.isChecked() ?"12"
                : bi.hs03c.isChecked() ?"13"
                : bi.hs03d.isChecked() ?"14"
                : bi.hs03e.isChecked() ?"21"
                : bi.hs03f.isChecked() ?"31"
                : bi.hs03g.isChecked() ?"32"
                : bi.hs03h.isChecked() ?"41"
                : bi.hs03i.isChecked() ?"42"
                : bi.hs03j.isChecked() ?"51"
                : bi.hs03k.isChecked() ?"61"
                : bi.hs03l.isChecked() ?"71"
                : bi.hs03m.isChecked() ?"81"
                : bi.hs03n.isChecked() ?"91"
                : bi.hs0396.isChecked() ?"96"
                : "0");
        f3.put("hs0396x", bi.hs0396x.getText().toString());

        //hs04
        f3.put("hs04", bi.hs04a.isChecked() ?"1"
                : bi.hs04b.isChecked() ?"2"
                : "0");

        //hs05
        f3.put("hs05", bi.hs05a.isChecked() ?"1"
                : bi.hs05b.isChecked() ?"2"
                : bi.hs05c.isChecked() ?"3"
                : bi.hs05d.isChecked() ?"4"
                : bi.hs05e.isChecked() ?"5"
                : bi.hs05f.isChecked() ?"6"
                : bi.hs0596.isChecked() ?"96"
                : "0");
        f3.put("hs0596x", bi.hs0596x.getText().toString());

        //hs06
        f3.put("hs06", bi.hs06a.isChecked() ?"11"
                : bi.hs06b.isChecked() ?"12"
                : bi.hs06c.isChecked() ?"13"
                : bi.hs06d.isChecked() ?"14"
                : bi.hs06e.isChecked() ?"21"
                : bi.hs06f.isChecked() ?"31"
                : bi.hs06g.isChecked() ?"32"
                : bi.hs06h.isChecked() ?"41"
                : bi.hs06i.isChecked() ?"42"
                : bi.hs06j.isChecked() ?"51"
                : bi.hs06k.isChecked() ?"61"
                : bi.hs06l.isChecked() ?"71"
                : bi.hs06m.isChecked() ?"81"
                : bi.hs06n.isChecked() ?"91"
                : bi.hs0696.isChecked() ?"96"
                : "0");
        f3.put("hs0696x", bi.hs0696x.getText().toString());

        //hs07
        f3.put("hs07", bi.hs07a.isChecked() ?"1"
                : bi.hs07b.isChecked() ?"2"
                : bi.hs07c.isChecked() ?"3"
                : bi.hs07d.isChecked() ?"4"
                : bi.hs07e.isChecked() ?"5"
                : bi.hs07f.isChecked() ?"6"
                : bi.hs07g.isChecked() ?"7"
                : bi.hs07h.isChecked() ?"8"
                : bi.hs07i.isChecked() ?"9"
                : bi.hs0796.isChecked() ?"96"
                : "0");
        f3.put("hs0796x", bi.hs0796x.getText().toString());

        //hs08
        f3.put("hs08", bi.hs08a.isChecked() ?"1"
                : bi.hs08b.isChecked() ?"2"
                : bi.hs08c.isChecked() ?"3"
                : "0");

        //hs09
        f3.put("hs09", bi.hs09a.isChecked() ?"1"
                : bi.hs09b.isChecked() ?"2"
                : "0");

        //hs10
        f3.put("hs10", bi.hs10.getText().toString());

        //hs11
        f3.put("hs11", bi.hs11a.isChecked() ?"1"
                : bi.hs11b.isChecked() ?"2"
                : "0");

        //hs12
        f3.put("hs12", bi.hs12a.isChecked() ?"0"
                : bi.hs12b.isChecked() ?"10"
                : bi.hs1298.isChecked() ?"98"
                : "0");

        //hs13
        f3.put("hs13", bi.hs13a.isChecked() ?"1"
                : bi.hs13b.isChecked() ?"2"
                : bi.hs13c.isChecked() ?"3"
                : bi.hs13d.isChecked() ?"4"
                : bi.hs13e.isChecked() ?"5"
                : bi.hs13f.isChecked() ?"6"
                : bi.hs13g.isChecked() ?"7"
                : bi.hs1396.isChecked() ?"96"
                : "0");
        f3.put("hs1396x", bi.hs1396x.getText().toString());

        //hs14a
        f3.put("hs14a", bi.hs14aa.isChecked() ?"1"
                : bi.hs14ab.isChecked() ?"2"
                : "0");

        //hs14b
        f3.put("hs14b", bi.hs14ba.isChecked() ?"1"
                : bi.hs14bb.isChecked() ?"2"
                : "0");

        //hs14c
        f3.put("hs14c", bi.hs14ca.isChecked() ?"1"
                : bi.hs14cb.isChecked() ?"2"
                : "0");

        //hs14d
        f3.put("hs14d", bi.hs14da.isChecked() ?"1"
                : bi.hs14db.isChecked() ?"2"
                : "0");

        //hs14e
        f3.put("hs14e", bi.hs14ea.isChecked() ?"1"
                : bi.hs14eb.isChecked() ?"2"
                : "0");

        //hs14f
        f3.put("hs14f", bi.hs14fa.isChecked() ?"1"
                : bi.hs14fb.isChecked() ?"2"
                : "0");

        //hs14g
        f3.put("hs14g", bi.hs14ga.isChecked() ?"1"
                : bi.hs14gb.isChecked() ?"2"
                : "0");

        //hs14h
        f3.put("hs14h", bi.hs14ha.isChecked() ?"1"
                : bi.hs14hb.isChecked() ?"2"
                : "0");

        //hs14i
        f3.put("hs14i", bi.hs14ia.isChecked() ?"1"
                : bi.hs14ib.isChecked() ?"2"
                : "0");

        //hs14j
        f3.put("hs14j", bi.hs14ja.isChecked() ?"1"
                : bi.hs14jb.isChecked() ?"2"
                : "0");

        //hs14k
        f3.put("hs14k", bi.hs14ka.isChecked() ?"1"
                : bi.hs14kb.isChecked() ?"2"
                : "0");

        //hs14l
        f3.put("hs14l", bi.hs14la.isChecked() ?"1"
                : bi.hs14lb.isChecked() ?"2"
                : "0");

        //hs14m
        f3.put("hs14m", bi.hs14ma.isChecked() ?"1"
                : bi.hs14mb.isChecked() ?"2"
                : "0");

        //hs14n
        f3.put("hs14n", bi.hs14na.isChecked() ?"1"
                : bi.hs14nb.isChecked() ?"2"
                : "0");

        //hs14o
        f3.put("hs14o", bi.hs14oa.isChecked() ?"1"
                : bi.hs14ob.isChecked() ?"2"
                : "0");

        //hs14p
        f3.put("hs14p", bi.hs14pa.isChecked() ?"1"
                : bi.hs14pb.isChecked() ?"2"
                : "0");

        //hs14q
        f3.put("hs14q", bi.hs14qa.isChecked() ?"1"
                : bi.hs14qb.isChecked() ?"2"
                : "0");

        //hs14r
        f3.put("hs14r", bi.hs14ra.isChecked() ?"1"
                : bi.hs14rb.isChecked() ?"2"
                : "0");

        //hs14s
        f3.put("hs14s", bi.hs14sa.isChecked() ?"1"
                : bi.hs14sb.isChecked() ?"2"
                : "0");

        //hs15a
        f3.put("hs15a", bi.hs15aa.isChecked() ?"1"
                : bi.hs15ab.isChecked() ?"2"
                : "0");

        //hs15b
        f3.put("hs15b", bi.hs15ba.isChecked() ?"1"
                : bi.hs15bb.isChecked() ?"2"
                : "0");

        //hs15c
        f3.put("hs15c", bi.hs15ca.isChecked() ?"1"
                : bi.hs15cb.isChecked() ?"2"
                : "0");

        //hs15d
        f3.put("hs15d", bi.hs15da.isChecked() ?"1"
                : bi.hs15db.isChecked() ?"2"
                : "0");

        //hs15e
        f3.put("hs15e", bi.hs15ea.isChecked() ?"1"
                : bi.hs15eb.isChecked() ?"2"
                : "0");

        //hs15f
        f3.put("hs15f", bi.hs15fa.isChecked() ?"1"
                : bi.hs15fb.isChecked() ?"2"
                : "0");

        //hs15g
        f3.put("hs15g", bi.hs15ga.isChecked() ?"1"
                : bi.hs15gb.isChecked() ?"2"
                : "0");

        //hs15h
        f3.put("hs15h", bi.hs15ha.isChecked() ?"1"
                : bi.hs15hb.isChecked() ?"2"
                : "0");

        //hs15i
        f3.put("hs15i", bi.hs15ia.isChecked() ?"1"
                : bi.hs15ib.isChecked() ?"2"
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
