package edu.aku.hassannaqvi.tpvics_hh.ui.sections;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.validatorcrawler.aliazaz.Clear;
import com.validatorcrawler.aliazaz.Validator;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import edu.aku.hassannaqvi.tpvics_hh.R;
import edu.aku.hassannaqvi.tpvics_hh.contracts.ChildContract;
import edu.aku.hassannaqvi.tpvics_hh.contracts.FamilyMembersContract;
import edu.aku.hassannaqvi.tpvics_hh.core.DatabaseHelper;
import edu.aku.hassannaqvi.tpvics_hh.core.MainApp;
import edu.aku.hassannaqvi.tpvics_hh.databinding.ActivitySectionChABinding;

import static edu.aku.hassannaqvi.tpvics_hh.core.MainApp.child;
import static edu.aku.hassannaqvi.tpvics_hh.ui.list_activity.FamilyMembersListActivity.mainVModel;
import static edu.aku.hassannaqvi.tpvics_hh.utils.UtilKt.openChildEndActivity;

public class SectionCHAActivity extends AppCompatActivity {

    ActivitySectionChABinding bi;
    int position;
    FamilyMembersContract selMWRA;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        bi = DataBindingUtil.setContentView(this, R.layout.activity_section_ch_a);
        bi.setCallback(this);

        setTitle(R.string.ch_title);
        setupListeners();
    }

    private void setupListeners() {

        bi.uf14.setOnCheckedChangeListener(((radioGroup, i) -> {
            if (i == bi.uf14a.getId()) {
                Clear.clearAllFields(bi.fldGrpCVuf15, false);
            } else {
                Clear.clearAllFields(bi.fldGrpCVuf15, true);
            }
        }));

        List<String> childrenLst = new ArrayList<String>() {
            {
                add("....");
                addAll(MainApp.selectedChildren.getSecond());
            }
        };

        bi.uf09.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, childrenLst));

        bi.uf09.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                position = i;
                if (i == 0) return;
                selMWRA = mainVModel.getMemberInfo(MainApp.selectedChildren.getFirst().get(bi.uf09.getSelectedItemPosition() - 1));
                bi.uf09a.setText(selMWRA.getMotherName());
                int totalAge = Integer.parseInt(selMWRA.getAge()) * 12 + Integer.parseInt(selMWRA.getMonthfm());
                bi.uf9a.setText(new StringBuilder("Mother name:").append(totalAge));
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });

    }

    private boolean UpdateDB() {
        DatabaseHelper db = MainApp.appInfo.getDbHelper();
        long updcount = db.addChild(child);
        child.set_ID(String.valueOf(updcount));
        if (updcount > 0) {
            child.setUID(MainApp.deviceId + child.get_ID());
            db.updatesChildColumn(ChildContract.SingleChild.COLUMN_UID, child.getUID());
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
        child.setFormDate(MainApp.fc.getFormDate());
        child.setUser(MainApp.userName);

        JSONObject f1 = new JSONObject();

        f1.put("hhno", MainApp.fc.getHhno());
        f1.put("cluster_no", MainApp.fc.getClusterCode());
        f1.put("_luid", MainApp.fc.getLuid());
        f1.put("fm_uid", selMWRA.getUid());
        f1.put("fm_serial", selMWRA.getSerialno());
        f1.put("mm_name", selMWRA.getMotherName());
        f1.put("appversion", MainApp.appInfo.getAppVersion());

        f1.put("uf09", selMWRA.getName());
        f1.put("uf09a", selMWRA.getMotherName());

        f1.put("uf9a", bi.uf9a.getText().toString());

        f1.put("uf9b",
                bi.uf9b1.isChecked() ? "1" :
                        bi.uf9b2.isChecked() ? "2" :
                                bi.uf9b98.isChecked() ? "98" :
                                        "0");

        f1.put("uf14",
                bi.uf14a.isChecked() ? "1" :
                        bi.uf14b.isChecked() ? "2" :
                                "0");

        f1.put("uf15",
                bi.uf15a.isChecked() ? "1" :
                        bi.uf15b.isChecked() ? "2" :
                                "0");

        child.setsCA(String.valueOf(f1));

        // Deleting item in list
        MainApp.selectedChildren.getFirst().remove(position - 1);
        MainApp.selectedChildren.getSecond().remove(position - 1);

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
        openChildEndActivity(this);
    }

    @Override
    public void onBackPressed() {
        Toast.makeText(this, "Back Press Not Allowed", Toast.LENGTH_SHORT).show();
    }

}
