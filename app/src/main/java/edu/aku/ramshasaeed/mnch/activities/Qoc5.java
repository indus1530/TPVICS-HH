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
import edu.aku.ramshasaeed.mnch.databinding.ActivityQoc5Binding;
import edu.aku.ramshasaeed.mnch.validation.validatorClass;

import static edu.aku.ramshasaeed.mnch.activities.LoginActivity.db;
import static edu.aku.ramshasaeed.mnch.activities.RSDInfoActivity.fc;

public class Qoc5 extends AppCompatActivity implements RadioGroup.OnCheckedChangeListener {

    ActivityQoc5Binding bi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        bi = DataBindingUtil.setContentView(this, R.layout.activity_qoc5);
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
                MainApp.endActivity(this, this, Qoc6.class, true, RSDInfoActivity.fc);

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

        return validatorClass.EmptyCheckingContainer(this, bi.llqoc5);
    }

    private void SaveDraft() throws JSONException {

        JSONObject qoc5 = new JSONObject();

        qoc5.put("qe0501a", bi.qe0501aa.isChecked() ? "1" : bi.qe0501ab.isChecked() ? "2" : bi.qe0501a97.isChecked() ? "NA" : "0");
        qoc5.put("qe0501b", bi.qe0501b.getText().toString().trim().length() > 0 ? bi.qe0501b.getText().toString() : "0");
//        qoc5.put("qe0501c", bi.qe0501c.getText().toString().trim().length() > 0 ? bi.qe0501c.getText().toString() : "0");

        qoc5.put("qe0502a", bi.qe0502aa.isChecked() ? "1" : bi.qe0502ab.isChecked() ? "2" : bi.qe0502a97.isChecked() ? "NA" : "0");
        qoc5.put("qe0502b", bi.qe0502b.getText().toString().trim().length() > 0 ? bi.qe0502b.getText().toString() : "0");
//        qoc5.put("qe0502c", bi.qe0502c.getText().toString().trim().length() > 0 ? bi.qe0502c.getText().toString() : "0");

        qoc5.put("qe0503a", bi.qe0503aa.isChecked() ? "1" : bi.qe0503ab.isChecked() ? "2" : bi.qe0503a97.isChecked() ? "NA" : "0");
        qoc5.put("qe0503b", bi.qe0503b.getText().toString().trim().length() > 0 ? bi.qe0503b.getText().toString() : "0");
//        qoc5.put("qe0503c", bi.qe0503c.getText().toString().trim().length() > 0 ? bi.qe0503c.getText().toString() : "0");

        qoc5.put("qe05Ap", bi.qe05Ap.getText().toString().trim().length() > 0 ? bi.qe05Ap.getText().toString() : "0");

        fc.setSE(String.valueOf(qoc5));

    }

    @Override
    public void onBackPressed() {
        Toast.makeText(this, "You can't go back", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onCheckedChanged(RadioGroup radioGroup, int i) {

        if (!bi.qe0501ab.isChecked()){
            bi.qe0501b.setEnabled(false);
        } else {
            bi.qe0501b.setEnabled(true);
        }

        if (!bi.qe0502ab.isChecked()){
            bi.qe0502b.setEnabled(false);
        } else {
            bi.qe0502b.setEnabled(true);
        }

        if (!bi.qe0503ab.isChecked()){
            bi.qe0503b.setEnabled(false);
        } else {
            bi.qe0503b.setEnabled(true);
        }

    }


    void events_call() {

        bi.qe0501a.setOnCheckedChangeListener(this);
        bi.qe0502a.setOnCheckedChangeListener(this);
        bi.qe0503a.setOnCheckedChangeListener(this);

    }
}
