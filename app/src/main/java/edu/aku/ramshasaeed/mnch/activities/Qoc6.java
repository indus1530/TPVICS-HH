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
import edu.aku.ramshasaeed.mnch.databinding.ActivityQoc6Binding;
import edu.aku.ramshasaeed.mnch.validation.validatorClass;

import static edu.aku.ramshasaeed.mnch.activities.LoginActivity.db;
import static edu.aku.ramshasaeed.mnch.activities.RSDInfoActivity.fc;

public class Qoc6 extends AppCompatActivity implements RadioGroup.OnCheckedChangeListener {

    ActivityQoc6Binding bi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        bi = DataBindingUtil.setContentView(this, R.layout.activity_qoc6);
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
                MainApp.endActivity(this, this, Qoc7.class, true, RSDInfoActivity.fc);

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

        return validatorClass.EmptyCheckingContainer(this, bi.llqoc6);
    }

    private void SaveDraft() throws JSONException {

        JSONObject qoc6 = new JSONObject();

        qoc6.put("qf0601a", bi.qf0601aa.isChecked() ? "1" : bi.qf0601ab.isChecked() ? "2" : bi.qf0601a97.isChecked() ? "NA" : "0");
        qoc6.put("qf0601b", bi.qf0601b.getText().toString().trim().length() > 0 ? bi.qf0601b.getText().toString() : "0");
//        qoc6.put("qf0601c", bi.qf0601c.getText().toString().trim().length() > 0 ? bi.qf0601c.getText().toString() : "0");

        qoc6.put("qf0602a", bi.qf0602aa.isChecked() ? "1" : bi.qf0602ab.isChecked() ? "2" : bi.qf0602a97.isChecked() ? "NA" : "0");
        qoc6.put("qf0602b", bi.qf0602b.getText().toString().trim().length() > 0 ? bi.qf0602b.getText().toString() : "0");
//        qoc6.put("qf0602c", bi.qf0602c.getText().toString().trim().length() > 0 ? bi.qf0602c.getText().toString() : "0");

        qoc6.put("qf0603a", bi.qf0603aa.isChecked() ? "1" : bi.qf0603ab.isChecked() ? "2" : bi.qf0603a97.isChecked() ? "NA" : "0");
        qoc6.put("qf0603b", bi.qf0603b.getText().toString().trim().length() > 0 ? bi.qf0603b.getText().toString() : "0");
//        qoc6.put("qf0603c", bi.qf0603c.getText().toString().trim().length() > 0 ? bi.qf0603c.getText().toString() : "0");

        qoc6.put("qf0604a", bi.qf0604aa.isChecked() ? "1" : bi.qf0604ab.isChecked() ? "2" : bi.qf0604a97.isChecked() ? "NA" : "0");
        qoc6.put("qf0604b", bi.qf0604b.getText().toString().trim().length() > 0 ? bi.qf0604b.getText().toString() : "0");
//        qoc6.put("qf0604c", bi.qf0604c.getText().toString().trim().length() > 0 ? bi.qf0604c.getText().toString() : "0");

        qoc6.put("qf06Ap", bi.qf06Ap.getText().toString().trim().length() > 0 ? bi.qf06Ap.getText().toString() : "0");

        fc.setSF(String.valueOf(qoc6));

    }

    @Override
    public void onBackPressed() {
        Toast.makeText(this, "You can't go back", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onCheckedChanged(RadioGroup radioGroup, int i) {

        if (!bi.qf0601ab.isChecked()){
            bi.qf0601b.setEnabled(false);
        } else {
            bi.qf0601b.setEnabled(true);
        }

        if (!bi.qf0602ab.isChecked()){
            bi.qf0602b.setEnabled(false);
        } else {
            bi.qf0602b.setEnabled(true);
        }

        if (!bi.qf0603ab.isChecked()){
            bi.qf0603b.setEnabled(false);
        } else {
            bi.qf0603b.setEnabled(true);
        }

        if (!bi.qf0604ab.isChecked()){
            bi.qf0604b.setEnabled(false);
        } else {
            bi.qf0604b.setEnabled(true);
        }

    }


    void events_call() {

        bi.qf0601a.setOnCheckedChangeListener(this);
        bi.qf0602a.setOnCheckedChangeListener(this);
        bi.qf0603a.setOnCheckedChangeListener(this);
        bi.qf0604a.setOnCheckedChangeListener(this);

    }
}
