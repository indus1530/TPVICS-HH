package edu.aku.ramshasaeed.mnch.activities;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.ExecutionException;

import edu.aku.ramshasaeed.mnch.R;
import edu.aku.ramshasaeed.mnch.RMOperations.crudOperations;
import edu.aku.ramshasaeed.mnch.data.DAO.FormsDAO;
import edu.aku.ramshasaeed.mnch.databinding.ActivityRsd05Binding;
import edu.aku.ramshasaeed.mnch.validation.validatorClass;

import static edu.aku.ramshasaeed.mnch.activities.LoginActivity.db;
import static edu.aku.ramshasaeed.mnch.activities.RSDInfoActivity.fc;

public class Rsd05 extends AppCompatActivity {
    ActivityRsd05Binding bi;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        bi = DataBindingUtil.setContentView(this, R.layout.activity_rsd05);
        bi.setCallback(this);
        this.setTitle(getString(R.string.routineone) + "(" + fc.getReportingMonth() + ")");


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
                startActivity(new Intent(this, RsdMain.class));

            } else {
                Toast.makeText(this, "Failed to Update Database!", Toast.LENGTH_SHORT).show();
            }
        }
    }

    public void BtnEnd() {
        finish();
        startActivity(new Intent(this, EndingActivity.class).putExtra("complete", false));

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


        if (!bi.rs3499.isChecked()) {

            if (!validatorClass.EmptyTextBox(this, bi.rs34, getString(R.string.rs34))) {
                return false;
            }
        }
        if (!bi.rs3199.isChecked()) {


            if (!validatorClass.EmptyTextBox(this, bi.rs31, getString(R.string.rs31))) {
                return false;
            }
        }
        if (!bi.rs2799.isChecked()) {

            if (!validatorClass.EmptyTextBox(this, bi.rs27, getString(R.string.rs27))) {
                return false;
            }
        }
        if (!bi.rs2899.isChecked()) {


            if (!validatorClass.EmptyTextBox(this, bi.rs28, getString(R.string.rs28))) {
                return false;
            }
        }
        if (!bi.rs3599.isChecked()) {

            if (!validatorClass.EmptyTextBox(this, bi.rs35, getString(R.string.rs35))) {
                return false;
            }
        }
        if (!bi.rs3299.isChecked()) {

            return validatorClass.EmptyTextBox(this, bi.rs32, getString(R.string.rs32));
        }

        return true;
    }


    private void SaveDraft() throws JSONException {

        JSONObject f01 = new JSONObject();

        f01.put("rs05_formdate", new SimpleDateFormat("dd-MM-yyyy HH:mm").format(new Date().getTime()));
        f01.put("rs34", bi.rs3499.isChecked() ? "Mi" : bi.rs34.getText().toString());
        f01.put("rs31", bi.rs3199.isChecked() ? "Mi" : bi.rs31.getText().toString());
        f01.put("rs27", bi.rs2799.isChecked() ? "Mi" : bi.rs27.getText().toString());
        f01.put("rs28", bi.rs2899.isChecked() ? "Mi" : bi.rs28.getText().toString());
        f01.put("rs35", bi.rs3599.isChecked() ? "Mi" : bi.rs35.getText().toString());
        f01.put("rs32", bi.rs3299.isChecked() ? "Mi" : bi.rs32.getText().toString());

        fc.setSE(String.valueOf(f01));

    }

}
