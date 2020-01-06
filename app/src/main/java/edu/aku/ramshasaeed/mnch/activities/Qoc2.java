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
import edu.aku.ramshasaeed.mnch.databinding.ActivityQoc2Binding;
import edu.aku.ramshasaeed.mnch.validation.validatorClass;

import static edu.aku.ramshasaeed.mnch.activities.LoginActivity.db;
import static edu.aku.ramshasaeed.mnch.activities.RSDInfoActivity.fc;

public class Qoc2 extends AppCompatActivity implements RadioGroup.OnCheckedChangeListener {

    ActivityQoc2Binding bi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        bi = DataBindingUtil.setContentView(this, R.layout.activity_qoc2);
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
                MainApp.endActivity(this, this, Qoc3.class, true, RSDInfoActivity.fc);

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

        return validatorClass.EmptyCheckingContainer(this, bi.llqoc2);
    }

    private void SaveDraft() throws JSONException {

        JSONObject qoc2 = new JSONObject();

        qoc2.put("qb0201a", bi.qb0201aa.isChecked() ? "1" : bi.qb0201ab.isChecked() ? "2" : bi.qb0201a97.isChecked() ? "NA" : "0");
        qoc2.put("qb0201b", bi.qb0201b.getText().toString().trim().length() > 0 ? bi.qb0201b.getText().toString() : "0");
//        qoc2.put("qb0201c", bi.qb0201c.getText().toString().trim().length() > 0 ? bi.qb0201c.getText().toString() : "0");

        qoc2.put("qb0202a", bi.qb0202aa.isChecked() ? "1" : bi.qb0202ab.isChecked() ? "2" : bi.qb0202a97.isChecked() ? "NA" : "0");
        qoc2.put("qb0202b", bi.qb0202b.getText().toString().trim().length() > 0 ? bi.qb0202b.getText().toString() : "0");
//        qoc2.put("qb0202c", bi.qb0202c.getText().toString().trim().length() > 0 ? bi.qb0202c.getText().toString() : "0");

        qoc2.put("qb0203a", bi.qb0203aa.isChecked() ? "1" : bi.qb0203ab.isChecked() ? "2" : bi.qb0203a97.isChecked() ? "NA" : "0");
        qoc2.put("qb0203b", bi.qb0203b.getText().toString().trim().length() > 0 ? bi.qb0203b.getText().toString() : "0");
//        qoc2.put("qb0203c", bi.qb0203c.getText().toString().trim().length() > 0 ? bi.qb0203c.getText().toString() : "0");

        qoc2.put("qb02Ap", bi.qb02Ap.getText().toString().trim().length() > 0 ? bi.qb02Ap.getText().toString() : "0");

        fc.setSB(String.valueOf(qoc2));

    }



    @Override
    public void onCheckedChanged(RadioGroup radioGroup, int i) {

        if (!bi.qb0201ab.isChecked()){
            bi.qb0201b.setEnabled(false);
        } else {
            bi.qb0201b.setEnabled(true);
        }

        if (!bi.qb0202ab.isChecked()){
            bi.qb0202b.setEnabled(false);
        } else {
            bi.qb0202b.setEnabled(true);
        }

        if (!bi.qb0203ab.isChecked()){
            bi.qb0203b.setEnabled(false);
        } else {
            bi.qb0203b.setEnabled(true);
        }
        
    }


    void events_call() {

        bi.qb0201a.setOnCheckedChangeListener(this);
        bi.qb0202a.setOnCheckedChangeListener(this);
        bi.qb0203a.setOnCheckedChangeListener(this);
    }


    @Override
    public void onBackPressed() {
        Toast.makeText(this, "You can't go back", Toast.LENGTH_SHORT).show();
    }


}
