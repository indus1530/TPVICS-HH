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
import edu.aku.ramshasaeed.mnch.databinding.ActivityQoc1Binding;
import edu.aku.ramshasaeed.mnch.validation.validatorClass;

import static edu.aku.ramshasaeed.mnch.activities.LoginActivity.db;
import static edu.aku.ramshasaeed.mnch.activities.RSDInfoActivity.fc;


public class Qoc1 extends AppCompatActivity implements RadioGroup.OnCheckedChangeListener {

    ActivityQoc1Binding bi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        bi = DataBindingUtil.setContentView(this, R.layout.activity_qoc1);
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
                MainApp.endActivity(this, this, Qoc2.class, true, RSDInfoActivity.fc);

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

        return validatorClass.EmptyCheckingContainer(this, bi.llqoc1);
    }

    private void SaveDraft() throws JSONException {

        JSONObject qoc1 = new JSONObject();

        qoc1.put("qa0101a", bi.qa0101aa.isChecked() ? "1" : bi.qa0101ab.isChecked() ? "2" : bi.qa0101a97.isChecked() ? "NA" : "0");
        qoc1.put("qa0101b", bi.qa0101b.getText().toString().trim().length() > 0 ? bi.qa0101b.getText().toString() : "0");
//        qoc1.put("qa0101c", bi.qa0101c.getText().toString().trim().length() > 0 ? bi.qa0101c.getText().toString() : "0");

        qoc1.put("qa0102a", bi.qa0102aa.isChecked() ? "1" : bi.qa0102ab.isChecked() ? "2" : bi.qa0102a97.isChecked() ? "NA" : "0");
        qoc1.put("qa0102b", bi.qa0102b.getText().toString().trim().length() > 0 ? bi.qa0102b.getText().toString() : "0");
//        qoc1.put("qa0102c", bi.qa0102c.getText().toString().trim().length() > 0 ? bi.qa0102c.getText().toString() : "0");

        qoc1.put("qa0103a", bi.qa0103aa.isChecked() ? "1" : bi.qa0103ab.isChecked() ? "2" : bi.qa0103a97.isChecked() ? "NA" : "0");
        qoc1.put("qa0103b", bi.qa0103b.getText().toString().trim().length() > 0 ? bi.qa0103b.getText().toString() : "0");
//        qoc1.put("qa0103c", bi.qa0103c.getText().toString().trim().length() > 0 ? bi.qa0103c.getText().toString() : "0");

        qoc1.put("qa0104a", bi.qa0104aa.isChecked() ? "1" : bi.qa0104ab.isChecked() ? "2" : bi.qa0104a97.isChecked() ? "NA" : "0");
        qoc1.put("qa0104b", bi.qa0104b.getText().toString().trim().length() > 0 ? bi.qa0104b.getText().toString() : "0");
//        qoc1.put("qa0104c", bi.qa0104c.getText().toString().trim().length() > 0 ? bi.qa0104c.getText().toString() : "0");

        qoc1.put("qa0105a", bi.qa0105aa.isChecked() ? "1" : bi.qa0105ab.isChecked() ? "2" : bi.qa0105a97.isChecked() ? "NA" : "0");
        qoc1.put("qa0105b", bi.qa0105b.getText().toString().trim().length() > 0 ? bi.qa0105b.getText().toString() : "0");
//        qoc1.put("qa0105c", bi.qa0105c.getText().toString().trim().length() > 0 ? bi.qa0105c.getText().toString() : "0");

        qoc1.put("qa0106a", bi.qa0106aa.isChecked() ? "1" : bi.qa0106ab.isChecked() ? "2" : bi.qa0106a97.isChecked() ? "NA" : "0");
        qoc1.put("qa0106b", bi.qa0106b.getText().toString().trim().length() > 0 ? bi.qa0106b.getText().toString() : "0");
//        qoc1.put("qa0106c", bi.qa0106c.getText().toString().trim().length() > 0 ? bi.qa0106c.getText().toString() : "0");

        qoc1.put("qa0107a", bi.qa0107aa.isChecked() ? "1" : bi.qa0107ab.isChecked() ? "2" : bi.qa0107a97.isChecked() ? "NA" : "0");
        qoc1.put("qa0107b", bi.qa0107b.getText().toString().trim().length() > 0 ? bi.qa0107b.getText().toString() : "0");
//        qoc1.put("qa0107c", bi.qa0107c.getText().toString().trim().length() > 0 ? bi.qa0107c.getText().toString() : "0");

        qoc1.put("qa0108a", bi.qa0108aa.isChecked() ? "1" : bi.qa0108ab.isChecked() ? "2" : bi.qa0108a97.isChecked() ? "NA" : "0");
        qoc1.put("qa0108b", bi.qa0108b.getText().toString().trim().length() > 0 ? bi.qa0108b.getText().toString() : "0");
//        qoc1.put("qa0108c", bi.qa0108c.getText().toString().trim().length() > 0 ? bi.qa0108c.getText().toString() : "0");

        qoc1.put("qa0109a", bi.qa0109aa.isChecked() ? "1" : bi.qa0109ab.isChecked() ? "2" : bi.qa0109a97.isChecked() ? "NA" : "0");
        qoc1.put("qa0109b", bi.qa0109b.getText().toString().trim().length() > 0 ? bi.qa0109b.getText().toString() : "0");
//        qoc1.put("qa0109c", bi.qa0109c.getText().toString().trim().length() > 0 ? bi.qa0109c.getText().toString() : "0");

        qoc1.put("qa0110a", bi.qa0110aa.isChecked() ? "1" : bi.qa0110ab.isChecked() ? "2" : bi.qa0110a97.isChecked() ? "NA" : "0");
        qoc1.put("qa0110b", bi.qa0110b.getText().toString().trim().length() > 0 ? bi.qa0110b.getText().toString() : "0");
//        qoc1.put("qa0110c", bi.qa0110c.getText().toString().trim().length() > 0 ? bi.qa0110c.getText().toString() : "0");

        qoc1.put("qa0111a", bi.qa0111aa.isChecked() ? "1" : bi.qa0111ab.isChecked() ? "2" : bi.qa0111a97.isChecked() ? "NA" : "0");
        qoc1.put("qa0111b", bi.qa0111b.getText().toString().trim().length() > 0 ? bi.qa0111b.getText().toString() : "0");
//        qoc1.put("qa0111c", bi.qa0111c.getText().toString().trim().length() > 0 ? bi.qa0111c.getText().toString() : "0");

        qoc1.put("qa0112a", bi.qa0112aa.isChecked() ? "1" : bi.qa0112ab.isChecked() ? "2" : bi.qa0112a97.isChecked() ? "NA" : "0");
        qoc1.put("qa0112b", bi.qa0112b.getText().toString().trim().length() > 0 ? bi.qa0112b.getText().toString() : "0");
//        qoc1.put("qa0112c", bi.qa0112c.getText().toString().trim().length() > 0 ? bi.qa0112c.getText().toString() : "0");

        qoc1.put("qa0113a", bi.qa0113aa.isChecked() ? "1" : bi.qa0113ab.isChecked() ? "2" : bi.qa0113a97.isChecked() ? "NA" : "0");
        qoc1.put("qa0113b", bi.qa0113b.getText().toString().trim().length() > 0 ? bi.qa0113b.getText().toString() : "0");
//        qoc1.put("qa0113c", bi.qa0113c.getText().toString().trim().length() > 0 ? bi.qa0113c.getText().toString() : "0");

        qoc1.put("qa0114a", bi.qa0114aa.isChecked() ? "1" : bi.qa0114ab.isChecked() ? "2" : bi.qa0114a97.isChecked() ? "NA" : "0");
        qoc1.put("qa0114b", bi.qa0114b.getText().toString().trim().length() > 0 ? bi.qa0114b.getText().toString() : "0");
//        qoc1.put("qa0114c", bi.qa0114c.getText().toString().trim().length() > 0 ? bi.qa0114c.getText().toString() : "0");

        qoc1.put("qa0115a", bi.qa0115aa.isChecked() ? "1" : bi.qa0115ab.isChecked() ? "2" : bi.qa0115a97.isChecked() ? "NA" : "0");
        qoc1.put("qa0115b", bi.qa0115b.getText().toString().trim().length() > 0 ? bi.qa0115b.getText().toString() : "0");
//        qoc1.put("qa0115c", bi.qa0115c.getText().toString().trim().length() > 0 ? bi.qa0115c.getText().toString() : "0");

        qoc1.put("qa0116a", bi.qa0116aa.isChecked() ? "1" : bi.qa0116ab.isChecked() ? "2" : bi.qa0116a97.isChecked() ? "NA" : "0");
        qoc1.put("qa0116b", bi.qa0116b.getText().toString().trim().length() > 0 ? bi.qa0116b.getText().toString() : "0");
//        qoc1.put("qa0116c", bi.qa0116c.getText().toString().trim().length() > 0 ? bi.qa0116c.getText().toString() : "0");

        qoc1.put("qa01Ap", bi.qa01AP.getText().toString().trim().length() > 0 ? bi.qa01AP.getText().toString() : "0");

        fc.setSA(String.valueOf(qoc1));

    }

    @Override
    public void onCheckedChanged(RadioGroup radioGroup, int i) {

        if (!bi.qa0101ab.isChecked()) {
            bi.qa0101b.setEnabled(false);
        } else {
            bi.qa0101b.setEnabled(true);
        }

        if (!bi.qa0102ab.isChecked()) {
            bi.qa0102b.setEnabled(false);
        } else {
            bi.qa0102b.setEnabled(true);
        }

        if (!bi.qa0103ab.isChecked()) {
            bi.qa0103b.setEnabled(false);
        } else {
            bi.qa0103b.setEnabled(true);
        }

        if (!bi.qa0104ab.isChecked()) {
            bi.qa0104b.setEnabled(false);
        } else {
            bi.qa0104b.setEnabled(true);
        }

        if (!bi.qa0105ab.isChecked()) {
            bi.qa0105b.setEnabled(false);
        } else {
            bi.qa0105b.setEnabled(true);
        }

        if (!bi.qa0106ab.isChecked()) {
            bi.qa0106b.setEnabled(false);
        } else {
            bi.qa0106b.setEnabled(true);
        }

        if (!bi.qa0107ab.isChecked()) {
            bi.qa0107b.setEnabled(false);
        } else {
            bi.qa0107b.setEnabled(true);
        }

        if (!bi.qa0108ab.isChecked()) {
            bi.qa0108b.setEnabled(false);
        } else {
            bi.qa0108b.setEnabled(true);
        }

        if (!bi.qa0109ab.isChecked()) {
            bi.qa0109b.setEnabled(false);
        } else {
            bi.qa0109b.setEnabled(true);
        }

        if (!bi.qa0110ab.isChecked()) {
            bi.qa0110b.setEnabled(false);
        } else {
            bi.qa0110b.setEnabled(true);
        }

        if (!bi.qa0111ab.isChecked()) {
            bi.qa0111b.setEnabled(false);
        } else {
            bi.qa0111b.setEnabled(true);
        }

        if (!bi.qa0112ab.isChecked()) {
            bi.qa0112b.setEnabled(false);
        } else {
            bi.qa0112b.setEnabled(true);
        }

        if (!bi.qa0113ab.isChecked()) {
            bi.qa0113b.setEnabled(false);
        } else {
            bi.qa0113b.setEnabled(true);
        }

        if (!bi.qa0114ab.isChecked()) {
            bi.qa0114b.setEnabled(false);
        } else {
            bi.qa0114b.setEnabled(true);
        }

        if (!bi.qa0115ab.isChecked()) {
            bi.qa0115b.setEnabled(false);
        } else {
            bi.qa0115b.setEnabled(true);
        }

        if (!bi.qa0116ab.isChecked()) {
            bi.qa0116b.setEnabled(false);
        } else {
            bi.qa0116b.setEnabled(true);
        }
    }

    void events_call() {

        bi.qa0101a.setOnCheckedChangeListener(this);
        bi.qa0102a.setOnCheckedChangeListener(this);
        bi.qa0103a.setOnCheckedChangeListener(this);
        bi.qa0104a.setOnCheckedChangeListener(this);
        bi.qa0105a.setOnCheckedChangeListener(this);
        bi.qa0106a.setOnCheckedChangeListener(this);
        bi.qa0107a.setOnCheckedChangeListener(this);
        bi.qa0108a.setOnCheckedChangeListener(this);
        bi.qa0109a.setOnCheckedChangeListener(this);
        bi.qa0110a.setOnCheckedChangeListener(this);
        bi.qa0111a.setOnCheckedChangeListener(this);
        bi.qa0112a.setOnCheckedChangeListener(this);
        bi.qa0113a.setOnCheckedChangeListener(this);
        bi.qa0114a.setOnCheckedChangeListener(this);
        bi.qa0115a.setOnCheckedChangeListener(this);
        bi.qa0116a.setOnCheckedChangeListener(this);

    }

}
