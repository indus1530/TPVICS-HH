package edu.aku.hassannaqvi.tpvics_hh.ui.sections;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.validatorcrawler.aliazaz.Validator;

import org.json.JSONException;
import org.json.JSONObject;

import edu.aku.hassannaqvi.tpvics_hh.R;
import edu.aku.hassannaqvi.tpvics_hh.databinding.ActivitySectionChBBinding;
import edu.aku.hassannaqvi.tpvics_hh.ui.other.EndingActivity;
import edu.aku.hassannaqvi.tpvics_hh.utils.Util;

public class SectionCHBActivity extends AppCompatActivity {

    ActivitySectionChBBinding bi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        bi = DataBindingUtil.setContentView(this, R.layout.activity_section_ch_b);
        bi.setCallback(this);
        setTitle(R.string.cb0_title);
        setupSkips();

    }

    private void setupSkips() {

        bi.cb01a.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

                if ((bi.cb01a.getText()).hashCode() == s.hashCode()) {
                    if (Integer.parseInt(bi.cb01a.getText().toString()) == 77) {
                        bi.cb01b.setEnabled(true);
                    } else {
                        bi.cb01b.setEnabled(false);
                        bi.cb01b.setText("");
                    }
                }

            }
        });


        bi.cb02a.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {

                if ((bi.cb02a.getText()).hashCode() == s.hashCode()) {
                    if (Integer.parseInt(bi.cb02a.getText().toString()) == 77) {
                        bi.cb02b.setEnabled(true);
                    } else {
                        bi.cb02b.setEnabled(false);
                        bi.cb02b.setText("");
                    }
                }

            }
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

        f1.put("cb01a", bi.cb01a.getText().toString());
        f1.put("cb01b", bi.cb01b.getText().toString());
        f1.put("cb01dk", bi.cb01dk.isChecked() ? "98" : "0");

        f1.put("cb02a", bi.cb02a.getText().toString());
        f1.put("cb02b", bi.cb02b.getText().toString());
        f1.put("cb02dk", bi.cb02dk.isChecked() ? "98" : "0");

        f1.put("cb03dd", bi.cb03dd.getText().toString());
        f1.put("cb03mm", bi.cb03mm.getText().toString());
        f1.put("cb03yy", bi.cb03yy.getText().toString());

        f1.put("cb04mm", bi.cb04mm.getText().toString());
        f1.put("cb04yy", bi.cb04yy.getText().toString());

        f1.put("cb05", bi.cb051.isChecked() ? "1" :
                bi.cb052.isChecked() ? "2" : "0");

    }

    private boolean formValidation() {
        return Validator.emptyCheckingContainer(this, bi.fldGrpSectionCHB);
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