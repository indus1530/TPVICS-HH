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
import edu.aku.ramshasaeed.mnch.databinding.ActivityQoc4Binding;
import edu.aku.ramshasaeed.mnch.validation.validatorClass;

import static edu.aku.ramshasaeed.mnch.activities.LoginActivity.db;
import static edu.aku.ramshasaeed.mnch.activities.RSDInfoActivity.fc;

public class Qoc4 extends AppCompatActivity implements RadioGroup.OnCheckedChangeListener {

    ActivityQoc4Binding bi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        bi = DataBindingUtil.setContentView(this, R.layout.activity_qoc4);
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
                MainApp.endActivity(this, this, Qoc5.class, true, RSDInfoActivity.fc);

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

        return validatorClass.EmptyCheckingContainer(this, bi.llqoc4);
    }

    private void SaveDraft() throws JSONException {

        JSONObject qoc4 = new JSONObject();

        qoc4.put("qd0401a", bi.qd0401aa.isChecked() ? "1" : bi.qd0401ab.isChecked() ? "2" : bi.qd0401a97.isChecked() ? "NA" : "0");
        qoc4.put("qd0401b", bi.qd0401b.getText().toString().trim().length() > 0 ? bi.qd0401b.getText().toString() : "0");
//        qoc4.put("qd0401c", bi.qd0401c.getText().toString().trim().length() > 0 ? bi.qd0401c.getText().toString() : "0");

        qoc4.put("qd0402a", bi.qd0402aa.isChecked() ? "1" : bi.qd0402ab.isChecked() ? "2" : bi.qd0402a97.isChecked() ? "NA" : "0");
        qoc4.put("qd0402b", bi.qd0402b.getText().toString().trim().length() > 0 ? bi.qd0402b.getText().toString() : "0");
//        qoc4.put("qd0402c", bi.qd0402c.getText().toString().trim().length() > 0 ? bi.qd0402c.getText().toString() : "0");

        qoc4.put("qd0403a", bi.qd0403aa.isChecked() ? "1" : bi.qd0403ab.isChecked() ? "2" : bi.qd0403a97.isChecked() ? "NA" : "0");
        qoc4.put("qd0403b", bi.qd0403b.getText().toString().trim().length() > 0 ? bi.qd0403b.getText().toString() : "0");
//        qoc4.put("qd0403c", bi.qd0403c.getText().toString().trim().length() > 0 ? bi.qd0403c.getText().toString() : "0");

        qoc4.put("qd0404a", bi.qd0404aa.isChecked() ? "1" : bi.qd0404ab.isChecked() ? "2" : bi.qd0404a97.isChecked() ? "NA" : "0");
        qoc4.put("qd0404b", bi.qd0404b.getText().toString().trim().length() > 0 ? bi.qd0404b.getText().toString() : "0");
//        qoc4.put("qd0404c", bi.qd0404c.getText().toString().trim().length() > 0 ? bi.qd0404c.getText().toString() : "0");

        qoc4.put("qd04Ap", bi.qd04Ap.getText().toString().trim().length() > 0 ? bi.qd04Ap.getText().toString() : "0");


        fc.setSD(String.valueOf(qoc4));

    }

    @Override
    public void onBackPressed() {
        Toast.makeText(this, "You can't go back", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onCheckedChanged(RadioGroup radioGroup, int i) {

        if (!bi.qd0401ab.isChecked()){
            bi.qd0401b.setEnabled(false);
        } else {
            bi.qd0401b.setEnabled(true);
        }

        if (!bi.qd0402ab.isChecked()){
            bi.qd0402b.setEnabled(false);
        } else {
            bi.qd0402b.setEnabled(true);
        }

        if (!bi.qd0403ab.isChecked()){
            bi.qd0403b.setEnabled(false);
        } else {
            bi.qd0403b.setEnabled(true);
        }

        if (!bi.qd0404ab.isChecked()){
            bi.qd0404b.setEnabled(false);
        } else {
            bi.qd0404b.setEnabled(true);
        }

    }


    void events_call() {

        bi.qd0401a.setOnCheckedChangeListener(this);
        bi.qd0402a.setOnCheckedChangeListener(this);
        bi.qd0403a.setOnCheckedChangeListener(this);
        bi.qd0404a.setOnCheckedChangeListener(this);

    }
}
