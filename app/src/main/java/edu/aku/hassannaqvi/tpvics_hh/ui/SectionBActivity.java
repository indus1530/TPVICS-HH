package edu.aku.hassannaqvi.tpvics_hh.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.validatorcrawler.aliazaz.Validator;

import org.json.JSONException;
import org.json.JSONObject;

import edu.aku.hassannaqvi.tpvics_hh.R;
import edu.aku.hassannaqvi.tpvics_hh.core.MainApp;
import edu.aku.hassannaqvi.tpvics_hh.databinding.ActivitySectionBBinding;
import edu.aku.hassannaqvi.tpvics_hh.validation.ValidatorClass;

public class SectionBActivity extends AppCompatActivity {


    ActivitySectionBBinding bi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        bi = DataBindingUtil.setContentView(this, R.layout.activity_section_b);
        bi.setCallback(this);
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

    private void SaveDraft() throws JSONException {
        JSONObject f2 = new JSONObject();

        //f2.put("hl01", bi.hl01.getText().toString());

        f2.put("hl02", bi.hl02.getText().toString());

        f2.put("hl03", bi.hl03a.isChecked() ? "1" :
                bi.hl03b.isChecked() ? "2" :
                        bi.hl03c.isChecked() ? "3" :
                                bi.hl03d.isChecked() ? "4" :
                                        bi.hl03e.isChecked() ? "5" :
                                                bi.hl03f.isChecked() ? "6" :
                                                        bi.hl03g.isChecked() ? "7" :
                                                                bi.hl03h.isChecked() ? "8" :
                                                                        bi.hl03i.isChecked() ? "9" :
                                                                                bi.hl03j.isChecked() ? "10" :
                                                                                        bi.hl03k.isChecked() ? "11" :
                                                                                                bi.hl03l.isChecked() ? "12" :
                                                                                                        bi.hl03m.isChecked() ? "13" :
                                                                                                                bi.hl03n.isChecked() ? "14" :
                                                                                                                        bi.hl03o.isChecked() ? "15" :
                                                                                                                                bi.hl03p.isChecked() ? "96" :
                                                                                                                                        bi.hl03q.isChecked() ? "98" : "0");

        f2.put("hl04", bi.hl04a.isChecked() ? "1" :
                bi.hl04b.isChecked() ? "2" :
                        bi.hl04c.isChecked() ? "3" : "0");

        f2.put("hl05", bi.hl05.getText().toString());
        //f2.put("hl05dd", bi.hl05dd.getText().toString());
        //f2.put("hl05mm", bi.hl05mm.getText().toString());
        //f2.put("hl05yy", bi.hl05yy.getText().toString());

        f2.put("hl06", bi.hl06.getText().toString());
        f2.put("hl0698", bi.hl0698.isChecked() ? "98" : "0");

        //f2.put("hl06a", bi.hl06a.getText().toString());
        //f2.put("hl06dd", bi.hl06dd.getText().toString());
        //f2.put("hl06mm", bi.hl06mm.getText().toString());
        //f2.put("hl06yy", bi.hl06yy.getText().toString());

        f2.put("hl07", bi.hl07a.isChecked() ? "1" :
                bi.hl07b.isChecked() ? "2" :
                        bi.hl07c.isChecked() ? "3" :
                                bi.hl07d.isChecked() ? "4" :
                                        bi.hl07e.isChecked() ? "5" : "0");

        f2.put("hl08", bi.hl08a.isChecked() ? "1" :
                bi.hl08b.isChecked() ? "2" :
                        bi.hl08c.isChecked() ? "3" :
                                bi.hl08d.isChecked() ? "4" :
                                        bi.hl08e.isChecked() ? "5" :
                                                bi.hl08f.isChecked() ? "6" :
                                                        bi.hl08g.isChecked() ? "7" :
                                                                bi.hl08h.isChecked() ? "8" :
                                                                        bi.hl08i.isChecked() ? "9" :
                                                                                bi.hl08j.isChecked() ? "98" : "0");

        f2.put("hl09", bi.hl09a.isChecked() ? "1" :
                bi.hl09b.isChecked() ? "2" :
                        bi.hl09c.isChecked() ? "3" :
                                bi.hl09d.isChecked() ? "4" :
                                        bi.hl09e.isChecked() ? "5" :
                                                bi.hl09f.isChecked() ? "6" :
                                                        bi.hl09g.isChecked() ? "7" :
                                                                bi.hl09h.isChecked() ? "8" :
                                                                        bi.hl09i.isChecked() ? "9" :
                                                                                bi.hl09j.isChecked() ? "10" :
                                                                                        bi.hl0996.isChecked() ? "96" : "0");
        f2.put("hl0996x", bi.hl0996x.getText().toString());

        f2.put("hl10", bi.hl10a.isChecked() ? "1" :
                bi.hl10b.isChecked() ? "2" : "0");

        f2.put("hl11", bi.hl11.getText().toString());

        f2.put("hl12", bi.hl12.getText().toString());

        f2.put("hl13", bi.hl13.getText().toString());

        f2.put("hl14", bi.hl14.getText().toString());

        /*f2.put("hl15", bi.hl15a.isChecked() ? "1" :
                        bi.hl15b.isChecked() ? "2" :
                                "0");*/

        f2.put("hl15a", bi.hl15a.getText().toString());
        f2.put("hl15b", bi.hl15b.getText().toString());

        /*f2.put("hl16", bi.hl16a.isChecked() ? "1" :
                        bi.hl16b.isChecked() ? "2" :
                                "0");*/

        f2.put("hl16a", bi.hl16a.getText().toString());
        f2.put("hl16b", bi.hl16b.getText().toString());
    }

    private boolean UpdateDB() {

        return true;
    }

    private boolean formValidation() {
        return Validator.emptyCheckingContainer(this, bi.fldGrpSecB);
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
                            Toast.makeText(SectionBActivity.this, "Error in updating db!!", Toast.LENGTH_SHORT).show();
                            return;
                        }

                        MainApp.endActivitySetRouting(SectionBActivity.this, SectionBActivity.this, EndingActivity.class, false,null);
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
