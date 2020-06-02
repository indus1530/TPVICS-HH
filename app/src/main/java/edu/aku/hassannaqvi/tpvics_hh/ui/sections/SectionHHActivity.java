package edu.aku.hassannaqvi.tpvics_hh.ui.sections;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.validatorcrawler.aliazaz.Clear;
import com.validatorcrawler.aliazaz.Validator;

import org.jetbrains.annotations.NotNull;
import org.json.JSONException;
import org.json.JSONObject;

import edu.aku.hassannaqvi.tpvics_hh.R;
import edu.aku.hassannaqvi.tpvics_hh.contracts.FormsContract;
import edu.aku.hassannaqvi.tpvics_hh.core.DatabaseHelper;
import edu.aku.hassannaqvi.tpvics_hh.core.MainApp;
import edu.aku.hassannaqvi.tpvics_hh.databinding.ActivitySectionHhBinding;
import edu.aku.hassannaqvi.tpvics_hh.ui.other.EndingActivity;
import edu.aku.hassannaqvi.tpvics_hh.utils.EndSectionActivity;

import static edu.aku.hassannaqvi.tpvics_hh.utils.UtilKt.contextEndActivity;

public class SectionHHActivity extends AppCompatActivity implements EndSectionActivity {

    ActivitySectionHhBinding bi;
    private DatabaseHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        bi = DataBindingUtil.setContentView(this, R.layout.activity_section_hh);
        bi.setCallback(this);
        db = MainApp.appInfo.getDbHelper();
        setUIComponent();

    }

    private void setUIComponent() {
    }


    public void onCheckedHH20(RadioGroup group, int checkedId) {
        if (checkedId == bi.hh20a.getId()) {
            bi.btnEnd.setVisibility(View.GONE);
            bi.btnNext.setVisibility(View.VISIBLE);
        } else {
            bi.btnEnd.setVisibility(View.VISIBLE);
            bi.btnNext.setVisibility(View.GONE);
            bi.hh20aa.setText(null);
        }
        bi.hh20aa.setEnabled(checkedId == bi.hh20a.getId());
    }

    public void onCheckedHH18(RadioGroup group, int checkedId) {
        if (checkedId == bi.hh18a.getId()) {
            bi.btnEnd.setVisibility(View.GONE);
            bi.btnNext.setVisibility(View.VISIBLE);
        } else {
            bi.btnEnd.setVisibility(View.VISIBLE);
            bi.btnNext.setVisibility(View.GONE);
        }
        Clear.clearAllFields(bi.fldGrpAHH19, checkedId == bi.hh18a.getId());
    }

    public void onCheckedHH15(RadioGroup group, int checkedId) {
        Clear.clearAllFields(bi.fldGrpAHH16, checkedId == bi.hh15b.getId());
    }

    public void BtnContinue() {
        if (formValidation(true)) {
            try {
                SaveDraft();
            } catch (Exception e) {
                e.printStackTrace();
            }
            if (UpdateDB()) {
                finish();
                startActivity(new Intent(SectionHHActivity.this, SectionSS1Activity.class));
            }
        }
    }

    private boolean UpdateDB() {
        long updcount = db.addForm(MainApp.fc);
        MainApp.fc.set_ID(String.valueOf(updcount));
        if (updcount > 0) {
            MainApp.fc.set_UID(MainApp.fc.getDeviceID() + MainApp.fc.get_ID());
            db.updatesFormColumn(FormsContract.FormsTable.COLUMN_UID, MainApp.fc.get_UID());
            return true;
        } else {
            Toast.makeText(this, "Updating Database... ERROR!", Toast.LENGTH_SHORT).show();
            return false;
        }

    }

    private void SaveDraft() throws JSONException {

        JSONObject json = new JSONObject();

        MainApp.fc.setFormDate(bi.hh01.getText().toString());
//        json.put("hh01", bi.hh01.getText().toString());
        json.put("hh02", bi.hh02.getText().toString());
        json.put("hh10", bi.hh10.getText().toString());
        json.put("hh13", bi.hh13.getText().toString());
        json.put("hh13a", bi.hh13a.getText().toString());

        json.put("hh14", bi.hh14.getText().toString());

        json.put("hh15", bi.hh15a.isChecked() ? "1"
                : bi.hh15b.isChecked() ? "2"
                : "0");

        json.put("hh16a", bi.hh16a.getText().toString());
        json.put("hh16b", bi.hh16b.getText().toString());

       /* json.put("hh17a", bi.hh17a.getText().toString());
        json.put("hh17b", bi.hh17b.getText().toString());*/

        json.put("hh18", bi.hh18a.isChecked() ? "1"
                : bi.hh18b.isChecked() ? "2"
                : "0");

        json.put("hh19", bi.hh19.getText().toString());

        json.put("hh20", bi.hh20a.isChecked() ? "1"
                : bi.hh20b.isChecked() ? "2"
                : "0");

        json.put("hh20a", bi.hh20aa.getText().toString());

        MainApp.fc.setsInfo(String.valueOf(json));

    }

    private boolean formValidation(boolean flag) {
        if (flag) {
            if (!Validator.emptyCheckingContainer(this, bi.fldGrpSectionA01))
                return false;
            return Validator.emptyCheckingContainer(this, bi.fldGrpSectionA02);
        } else return Validator.emptyCheckingContainer(this, bi.fldGrpSectionA01);
    }

    public void BtnEnd() {
        if (formValidation(false)) {
            contextEndActivity(this);
        }
    }

    @Override
    public void endSecActivity(boolean flag) {
        try {
            SaveDraft();
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (UpdateDB()) {
            finish();
            startActivity(new Intent(this, EndingActivity.class).putExtra("complete", false));
        }
    }

    public void showTooltip(@NotNull View view) {
        if (view.getId() != View.NO_ID) {
            String package_name = getApplicationContext().getPackageName();

            // Question Number Textview ID must be prefixed with q_ e.g.: 'q_aa12a'
            String infoid = view.getResources().getResourceName(view.getId()).replace(package_name + ":id/q_", "");

            // Question info text must be suffixed with _info e.g.: aa12a_info
            int stringRes = this.getResources().getIdentifier(infoid + "_info", "string", getApplicationContext().getPackageName());

            // Fetch info text from strings.xml
            //String infoText = (String) getResources().getText(stringRes);

            // Check if string resource exists to avoid crash on missing info string
            if (stringRes != 0) {

                // Fetch info text from strings.xml
                String infoText = (String) getResources().getText(stringRes);

                new AlertDialog.Builder(this)
                        .setTitle("Info: " + infoid.toUpperCase())
                        .setMessage(infoText)
                        .setIcon(android.R.drawable.ic_dialog_info)
                        .show();
            } else {
                Toast.makeText(this, "No information available on this question.", Toast.LENGTH_SHORT).show();
            }

        } else {
            Toast.makeText(this, "No ID Associated with this question.", Toast.LENGTH_SHORT).show();

        }
    }

}
