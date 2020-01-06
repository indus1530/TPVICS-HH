package edu.aku.ramshasaeed.mnch.activities;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.RadioGroup;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.concurrent.ExecutionException;

import edu.aku.ramshasaeed.mnch.R;
import edu.aku.ramshasaeed.mnch.RMOperations.crudOperations;
import edu.aku.ramshasaeed.mnch.core.MainApp;
import edu.aku.ramshasaeed.mnch.data.DAO.FormsDAO;
import edu.aku.ramshasaeed.mnch.databinding.ActivityQoc3Binding;
import edu.aku.ramshasaeed.mnch.validation.validatorClass;

import static edu.aku.ramshasaeed.mnch.activities.LoginActivity.db;
import static edu.aku.ramshasaeed.mnch.activities.RSDInfoActivity.fc;

public class Qoc3 extends AppCompatActivity implements RadioGroup.OnCheckedChangeListener {

    ActivityQoc3Binding bi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        bi = DataBindingUtil.setContentView(this, R.layout.activity_qoc3);
        bi.setCallback(this);
        this.setTitle(getString(R.string.routinetwo) + "(" + fc.getReportingMonth() + ")");
        events_call();
    }

    public void BtnContinue() {
        if (formValidation()) {
            try {
                SaveDraft();
            } catch (JSONException e) {
                e.printStackTrace();
            }
            if (UpdateDB()) {
                MainApp.endActivity(this, this, Qoc4.class, true, RSDInfoActivity.fc);

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

        return validatorClass.EmptyCheckingContainer(this, bi.llqoc3);
    }

    private void SaveDraft() throws JSONException {

        JSONObject qoc3 = new JSONObject();

        qoc3.put("qc0301a", bi.qc0301aa.isChecked() ? "1" : bi.qc0301ab.isChecked() ? "2" : bi.qc0301a97.isChecked() ? "NA" : "0");
        qoc3.put("qc0301b", bi.qc0301b.getText().toString().trim().length() > 0 ? bi.qc0301b.getText().toString() : "0");
//        qoc3.put("qc0301c", bi.qc0301c.getText().toString().trim().length() > 0 ? bi.qc0301c.getText().toString() : "0");

        qoc3.put("qc0302a", bi.qc0302aa.isChecked() ? "1" : bi.qc0302ab.isChecked() ? "2" : bi.qc0302a97.isChecked() ? "NA" : "0");
        qoc3.put("qc0302b", bi.qc0302b.getText().toString().trim().length() > 0 ? bi.qc0302b.getText().toString() : "0");
//        qoc3.put("qc0302c", bi.qc0302c.getText().toString().trim().length() > 0 ? bi.qc0302c.getText().toString() : "0");

        qoc3.put("qc0303a", bi.qc0303aa.isChecked() ? "1" : bi.qc0303ab.isChecked() ? "2" : bi.qc0303a97.isChecked() ? "NA" : "0");
        qoc3.put("qc0303b", bi.qc0303b.getText().toString().trim().length() > 0 ? bi.qc0303b.getText().toString() : "0");
//        qoc3.put("qc0303c", bi.qc0303c.getText().toString().trim().length() > 0 ? bi.qc0303c.getText().toString() : "0");

        qoc3.put("qc0304a", bi.qc0304aa.isChecked() ? "1" : bi.qc0304ab.isChecked() ? "2" : bi.qc0304a97.isChecked() ? "NA" : "0");
        qoc3.put("qc0304b", bi.qc0304b.getText().toString().trim().length() > 0 ? bi.qc0304b.getText().toString() : "0");
//        qoc3.put("qc0304c", bi.qc0304c.getText().toString().trim().length() > 0 ? bi.qc0304c.getText().toString() : "0");

        qoc3.put("qc0305a", bi.qc0305aa.isChecked() ? "1" : bi.qc0305ab.isChecked() ? "2" : bi.qc0305a97.isChecked() ? "NA" : "0");
        qoc3.put("qc0305b", bi.qc0305b.getText().toString().trim().length() > 0 ? bi.qc0305b.getText().toString() : "0");
//        qoc3.put("qc0305c", bi.qc0305c.getText().toString().trim().length() > 0 ? bi.qc0305c.getText().toString() : "0");

        qoc3.put("qc03Ap", bi.qc03Ap.getText().toString().trim().length() > 0 ? bi.qc03Ap.getText().toString() : "0");

        fc.setSC(String.valueOf(qoc3));

    }



    @Override
    public void onCheckedChanged(RadioGroup radioGroup, int i) {

        if (!bi.qc0301ab.isChecked()){
            bi.qc0301b.setEnabled(false);
        } else {
            bi.qc0301b.setEnabled(true);
        }

        if (!bi.qc0302ab.isChecked()){
            bi.qc0302b.setEnabled(false);
        } else {
            bi.qc0302b.setEnabled(true);
        }

        if (!bi.qc0303ab.isChecked()){
            bi.qc0303b.setEnabled(false);
        } else {
            bi.qc0303b.setEnabled(true);
        }

        if (!bi.qc0304ab.isChecked()){
            bi.qc0304b.setEnabled(false);
        } else {
            bi.qc0304b.setEnabled(true);
        }

        if (!bi.qc0305ab.isChecked()){
            bi.qc0305b.setEnabled(false);
        } else {
            bi.qc0305b.setEnabled(true);
        }

    }


    void events_call() {

        bi.qc0301a.setOnCheckedChangeListener(this);
        bi.qc0302a.setOnCheckedChangeListener(this);
        bi.qc0303a.setOnCheckedChangeListener(this);
        bi.qc0304a.setOnCheckedChangeListener(this);
        bi.qc0305a.setOnCheckedChangeListener(this);

    }


    @Override
    public void onBackPressed() {
        Toast.makeText(this, "You can't go back", Toast.LENGTH_SHORT).show();
    }

}
