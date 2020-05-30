package edu.aku.hassannaqvi.tpvics_hh.ui.sections;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.validatorcrawler.aliazaz.Clear;
import com.validatorcrawler.aliazaz.Validator;

import org.json.JSONException;
import org.json.JSONObject;

import edu.aku.hassannaqvi.tpvics_hh.R;
import edu.aku.hassannaqvi.tpvics_hh.contracts.ChildContract;
import edu.aku.hassannaqvi.tpvics_hh.core.DatabaseHelper;
import edu.aku.hassannaqvi.tpvics_hh.core.MainApp;
import edu.aku.hassannaqvi.tpvics_hh.databinding.ActivitySectionChABinding;
import edu.aku.hassannaqvi.tpvics_hh.ui.other.ChildEndingActivity;
import edu.aku.hassannaqvi.tpvics_hh.utils.EndSectionActivity;

import static edu.aku.hassannaqvi.tpvics_hh.CONSTANTS.CHILD_SERIAL;
import static edu.aku.hassannaqvi.tpvics_hh.core.MainApp.child;
import static edu.aku.hassannaqvi.tpvics_hh.utils.UtilKt.contextEndActivity;

public class SectionCHAActivity extends AppCompatActivity implements EndSectionActivity {

    ActivitySectionChABinding bi;
//    FamilyMembersContract selMWRA;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        bi = DataBindingUtil.setContentView(this, R.layout.activity_section_ch_a);
        bi.setCallback(this);

        setupListeners();
    }

    private void setupListeners() {

        bi.ec19.setOnCheckedChangeListener(((radioGroup, i) -> {
            if (i == bi.ec19a.getId()) {
                Clear.clearAllFields(bi.fldGrpCVec21, false);
            } else {
                Clear.clearAllFields(bi.fldGrpCVec21, true);
            }
        }));

        bi.ec13.setText(String.valueOf(getIntent().getIntExtra(CHILD_SERIAL, 0)));
    }

    private boolean UpdateDB() {
        DatabaseHelper db = MainApp.appInfo.getDbHelper();
        long updcount = db.addChild(child);
        child.set_ID(String.valueOf(updcount));
        if (updcount > 0) {
            child.setUID(MainApp.deviceId + child.get_ID());
            db.updatesChildColumn(ChildContract.ChildTable.COLUMN_UID, child.getUID());
            return true;
        } else {
            Toast.makeText(this, "Updating Database... ERROR!", Toast.LENGTH_SHORT).show();
            return false;
        }
    }

    private void SaveDraft() throws JSONException {

        child = new ChildContract();
        child.set_UUID(MainApp.fc.get_UID());
        child.setDeviceId(MainApp.appInfo.getDeviceID());
        child.setDevicetagID(MainApp.appInfo.getTagName());
        child.sethhno(MainApp.fc.getHhno());
        child.setcluster(MainApp.fc.getClusterCode());
        child.setFormDate(MainApp.fc.getFormDate());
        child.setUser(MainApp.userName);
        child.setChildSerial(bi.ec13.getText().toString());
        child.setChildName(bi.ec14.getText().toString());
        child.setgender(bi.ec151.isChecked() ? "1" : bi.ec152.isChecked() ? "2" : "0");

        JSONObject f1 = new JSONObject();
        f1.put("_luid", MainApp.fc.getLuid());
        f1.put("ec01", bi.ec01.getText().toString());
        f1.put("ec02", bi.ec02.getText().toString());
        f1.put("ec13", bi.ec13.getText().toString());
        f1.put("ec14", bi.ec14.getText().toString());
        f1.put("ec15", bi.ec151.isChecked() ? "1" : bi.ec152.isChecked() ? "2" : "0");
        f1.put("ec16", bi.ec16.getText().toString());
        f1.put("ec17", bi.ec17.getText().toString());

        f1.put("ec18",
                bi.ec181.isChecked() ? "1" :
                        bi.ec182.isChecked() ? "2" :
                                bi.ec183.isChecked() ? "3" :
                                        bi.ec184.isChecked() ? "4" :
                                                bi.ec1898.isChecked() ? "98" :
                                                        "0");
        f1.put("ec1898x", bi.ec1898x.getText().toString());

        f1.put("ec19",
                bi.ec19a.isChecked() ? "1" :
                        bi.ec19b.isChecked() ? "2" :
                                "0");

        f1.put("ec21",
                bi.ec21a.isChecked() ? "1" :
                        bi.ec20b.isChecked() ? "2" :
                                "0");

        child.setsCA(String.valueOf(f1));

        // Deleting item in list
/*        MainApp.selectedChildren.getFirst().remove(position - 1);
        MainApp.selectedChildren.getSecond().remove(position - 1);*/

    }

    private boolean formValidation() {
        return Validator.emptyCheckingContainer(this, bi.fldGrpSectionCHA);
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
                startActivity(new Intent(this, SectionCHBActivity.class));
            } else {
                Toast.makeText(this, "Failed to Update Database!", Toast.LENGTH_SHORT).show();
            }
        }

    }

    public void BtnEnd() {
        if (!formValidation()) return;
        contextEndActivity(this);
    }

    @Override
    public void endSecActivity(boolean flag) {
        try {
            SaveDraft();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        if (UpdateDB()) {
            finish();
            startActivity(new Intent(this, ChildEndingActivity.class).putExtra("complete", flag));
        } else {
            Toast.makeText(this, "Failed to Update Database!", Toast.LENGTH_SHORT).show();
        }
    }
}
