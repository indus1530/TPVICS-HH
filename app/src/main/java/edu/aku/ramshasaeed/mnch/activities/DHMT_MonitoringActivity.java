package edu.aku.ramshasaeed.mnch.activities;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.concurrent.ExecutionException;

import edu.aku.ramshasaeed.mnch.R;
import edu.aku.ramshasaeed.mnch.RMOperations.crudOperations;
import edu.aku.ramshasaeed.mnch.core.MainApp;
import edu.aku.ramshasaeed.mnch.data.DAO.FormsDAO;
import edu.aku.ramshasaeed.mnch.databinding.ActivityDhmtMonitoringBinding;
import edu.aku.ramshasaeed.mnch.validation.validatorClass;

import static edu.aku.ramshasaeed.mnch.activities.LoginActivity.db;
import static edu.aku.ramshasaeed.mnch.activities.RSDInfoActivity.fc;

public class DHMT_MonitoringActivity extends AppCompatActivity {
ActivityDhmtMonitoringBinding bi;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        bi = DataBindingUtil.setContentView(this,R.layout.activity_dhmt_monitoring);
        bi.setCallback(this);
        this.setTitle(getString(R.string.routinethree) + "(" + fc.getReportingMonth() + ")");

    }


    public void BtnContinue() {
        if (formValidation()) {
            try {
                SaveDraft();
            } catch (JSONException e) {
                e.printStackTrace();
            }
            if (UpdateDB()) {
                MainApp.endActivity(this, this, EndingActivity.class, true, RSDInfoActivity.fc);

            } else {
                Toast.makeText(this, "Failed to Update Database!", Toast.LENGTH_SHORT).show();
            }
        }
    }

    public void BtnEnd() {
        MainApp.endActivity(this, this, EndingActivity.class, false, RSDInfoActivity.fc);

    }

    private boolean UpdateDB() {

        try {

            Long longID = new crudOperations(db, RSDInfoActivity.fc).execute(FormsDAO.class.getName(), "formsDao", "updateForm").get();
            return longID == 1;

        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        return false;

    }

    public boolean formValidation() {

            /*if (!validatorClass.EmptyRadioButton(this, bi.dh01,bi.dh01a, getString(R.string.dh01))) {
                return false;
            }
            if (!validatorClass.EmptyRadioButton(this, bi.dh02,bi.dh02a, getString(R.string.dh02))) {
                return false;
            }
            if (!validatorClass.EmptyRadioButton(this, bi.dh03,bi.dh03a, getString(R.string.dh03))) {
                return false;
            }
            if (!validatorClass.EmptyRadioButton(this, bi.dh04,bi.dh04a, getString(R.string.dh04))) {
                return false;
            }
            if (!validatorClass.EmptyRadioButton(this, bi.dh05,bi.dh05a, getString(R.string.dh05))) {
                return false;
            }
            if (!validatorClass.EmptyRadioButton(this, bi.dh06,bi.dh06a, getString(R.string.dh06))) {
                return false;
            }

        return true;*/


        return validatorClass.EmptyCheckingContainer(this, bi.lldhmt);

    }


    private void SaveDraft() throws JSONException {

        JSONObject dhmt = new JSONObject();

        dhmt.put("dh01", bi.dh01a.isChecked() ? "1" : bi.dh01b.isChecked() ? "2" : "0");
        dhmt.put("dh01Ap", bi.dh01Ap.getText().toString().trim().length() > 0 ? bi.dh01Ap.getText().toString() : "0");

        dhmt.put("dh02", bi.dh02a.isChecked() ? "1" : bi.dh02b.isChecked() ? "2" : "0");
        dhmt.put("dh02Ap", bi.dh02Ap.getText().toString().trim().length() > 0 ? bi.dh02Ap.getText().toString() : "0");

        dhmt.put("dh03", bi.dh03a.isChecked() ? "1" : bi.dh03b.isChecked() ? "2" : "0");
        dhmt.put("dh03Ap", bi.dh03Ap.getText().toString().trim().length() > 0 ? bi.dh03Ap.getText().toString() : "0");

        dhmt.put("dh04", bi.dh04a.isChecked() ? "1" : bi.dh04b.isChecked() ? "2" : "0");
        dhmt.put("dh04Ap", bi.dh04Ap.getText().toString().trim().length() > 0 ? bi.dh04Ap.getText().toString() : "0");

        dhmt.put("dh05", bi.dh05a.isChecked() ? "1" : bi.dh05b.isChecked() ? "2" : "0");
        dhmt.put("dh05Ap", bi.dh05Ap.getText().toString().trim().length() > 0 ? bi.dh05Ap.getText().toString() : "0");

        dhmt.put("dh06", bi.dh06a.isChecked() ? "1" : bi.dh06b.isChecked() ? "2" : "0");
        dhmt.put("dh06Ap", bi.dh06Ap.getText().toString().trim().length() > 0 ? bi.dh06Ap.getText().toString() : "0");

        dhmt.put("dh07", bi.dh07a.isChecked() ? "1" : bi.dh07b.isChecked() ? "2" : "0");
        dhmt.put("dh07Ap", bi.dh07Ap.getText().toString().trim().length() > 0 ? bi.dh07Ap.getText().toString() : "0");

        dhmt.put("dhmtSum", bi.dhmtSum.getText().toString().trim().length() > 0 ? bi.dhmtSum.getText().toString() : "0");

        fc.setSA(String.valueOf(dhmt));

    }

}
