package edu.aku.hassannaqvi.tpvics_hh.ui.sections;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.validatorcrawler.aliazaz.Validator;

import org.json.JSONException;
import org.json.JSONObject;

import edu.aku.hassannaqvi.tpvics_hh.R;
import edu.aku.hassannaqvi.tpvics_hh.databinding.ActivitySectionChEBinding;
import edu.aku.hassannaqvi.tpvics_hh.ui.other.EndingActivity;

import static edu.aku.hassannaqvi.tpvics_hh.utils.UtilKt.openEndActivity;

public class SectionCHEActivity extends AppCompatActivity {

    ActivitySectionChEBinding bi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        bi = DataBindingUtil.setContentView(this, R.layout.activity_section_ch_e);
        bi.setCallback(this);
        setTitle(R.string.chsec);
        setupListeners();
    }

    private void setupListeners() {

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


    }

    private boolean formValidation() {
        return Validator.emptyCheckingContainer(this, bi.fldGrpSectionCHE);
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

        openEndActivity(this);
    }

    @Override
    public void onBackPressed() {
        Toast.makeText(this, "Press top back button.", Toast.LENGTH_SHORT).show();
    }

}
