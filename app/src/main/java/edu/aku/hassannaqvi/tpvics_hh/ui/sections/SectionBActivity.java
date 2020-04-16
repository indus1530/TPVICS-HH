package edu.aku.hassannaqvi.tpvics_hh.ui.sections;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.validatorcrawler.aliazaz.Validator;

import org.json.JSONException;
import org.json.JSONObject;

import edu.aku.hassannaqvi.tpvics_hh.R;
import edu.aku.hassannaqvi.tpvics_hh.contracts.FamilyMembersContract;
import edu.aku.hassannaqvi.tpvics_hh.core.DatabaseHelper;
import edu.aku.hassannaqvi.tpvics_hh.core.MainApp;
import edu.aku.hassannaqvi.tpvics_hh.databinding.ActivitySectionBBinding;
import edu.aku.hassannaqvi.tpvics_hh.datecollection.AgeModel;
import edu.aku.hassannaqvi.tpvics_hh.datecollection.DateRepository;
import edu.aku.hassannaqvi.tpvics_hh.ui.list_activity.FamilyMembersListActivity;
import edu.aku.hassannaqvi.tpvics_hh.viewmodel.MainVModel;

import static edu.aku.hassannaqvi.tpvics_hh.CONSTANTS.SERIAL_EXTRA;

public class SectionBActivity extends AppCompatActivity {

    ActivitySectionBBinding bi;
    private MainVModel mainVModel;
    private FamilyMembersContract fmc;
    private int serial = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        bi = DataBindingUtil.setContentView(this, R.layout.activity_section_b);
        bi.setCallback(this);

        setUIComponent();
        setListeners();
    }

    private void setUIComponent() {
        mainVModel = FamilyMembersListActivity.Companion.getMainVModel();
        serial = getIntent().getIntExtra(SERIAL_EXTRA, 0);
        bi.ch01.setText(String.valueOf(serial));

    }

    public void BtnContinue() {
        if (!formValidation()) return;
        try {
            SaveDraft();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        if (UpdateDB()) {
            setResult(RESULT_OK, new Intent().putExtra(SERIAL_EXTRA, serial));
            finish();
        } else {
            Toast.makeText(this, "Failed to Update Database!", Toast.LENGTH_SHORT).show();
        }

    }

    private boolean UpdateDB() {
        DatabaseHelper db = MainApp.appInfo.getDbHelper();
        long updcount = db.addFamilyMember(fmc);
        fmc.set_id(String.valueOf(updcount));
        if (updcount > 0) {
            fmc.setUid(MainApp.deviceId + fmc.get_id());
            db.updatesFamilyMemberColumn(FamilyMembersContract.SingleMember.COLUMN_UID, fmc.getUid(), fmc.get_id());
            return true;
        } else {
            Toast.makeText(this, "Updating Database... ERROR!", Toast.LENGTH_SHORT).show();
        }
        return false;
    }

    private void SaveDraft() throws JSONException {

        fmc = new FamilyMembersContract();
        fmc.setUuid(MainApp.fc.get_UID());
        fmc.setFormdate(MainApp.fc.getFormDate());
        fmc.setClusterno(MainApp.fc.getClusterCode());
        fmc.setHhno(MainApp.fc.getHhno());
        fmc.setSerialno(bi.ch01.getText().toString());
        fmc.setName(bi.ch02.getText().toString());
        fmc.setFather_name(bi.ch03.getText().toString());
        fmc.setMother_name(bi.ch03b.getText().toString());

        JSONObject sd = new JSONObject();
        sd.put("username", MainApp.fc.getUser());
        sd.put("deviceid", MainApp.appInfo.getDeviceID());
        sd.put("tagid", MainApp.fc.getDevicetagID());
        sd.put("appversion", MainApp.appInfo.getAppVersion());
        sd.put("_luid", MainApp.fc.getLuid());

        fmc.setGender(bi.ch04a.isChecked() ? "1"
                : bi.ch04b.isChecked() ? "2"
                : "0");

        sd.put("ch05a", bi.ch05a.getText().toString());
        sd.put("ch05b", bi.ch05b.getText().toString());
        sd.put("ch05c", bi.ch05c.getText().toString());
        sd.put("ch06", bi.ch06.getText().toString());
        fmc.setAge(bi.ch06.getText().toString());
        fmc.setMonthfm(bi.ch06a.getText().toString().trim().isEmpty() ? "0" : bi.ch06a.getText().toString());

        sd.put("ch07", bi.ch07a.isChecked() ? "1"
                : bi.ch07b.isChecked() ? "2"
                : bi.ch07c.isChecked() ? "3"
                : bi.ch0796.isChecked() ? "96"
                : "0");

        sd.put("ch08", bi.ch08a.isChecked() ? "1"
                : bi.ch08b.isChecked() ? "2"
                : "0");

        fmc.setsD(String.valueOf(sd));

        // Update in ViewModel
        mainVModel.updateFamilyMembers(fmc);

        if (Integer.parseInt(fmc.getAge()) < 5) {
            mainVModel.setChildU5(fmc);
            int cumMonths = Integer.parseInt(fmc.getAge()) * 12 + Integer.parseInt(fmc.getMonthfm());
            if (cumMonths >= 12 && cumMonths < 24)
                mainVModel.setChildU12(fmc);
        }

        // Update in ViewModel
        mainVModel.setFamilyMembers(fmc);
        serial++;

    }

    private boolean formValidation() {
        return Validator.emptyCheckingContainer(this, bi.fldGrpSectionD);
    }

    public void BtnEnd() {
        finish();
    }

    private void setListeners() {

        EditText[] txtListener = new EditText[]{bi.ch05a, bi.ch05b};
        for (EditText txtItem : txtListener) {

            txtItem.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    bi.ch06.setText(null);
                    bi.ch06a.setText(null);
                }

                @Override
                public void afterTextChanged(Editable s) {

                }
            });

        }

        bi.ch05c.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                bi.ch06.setEnabled(false);
                bi.ch06.setText(null);
                bi.ch06a.setEnabled(false);
                bi.ch06a.setText(null);
                if (!bi.ch05a.isRangeTextValidate() || !bi.ch05b.isRangeTextValidate() || !bi.ch05c.isRangeTextValidate())
                    return;
                if (bi.ch05a.getText().toString().equals("00") && bi.ch05b.getText().toString().equals("00") && bi.ch05c.getText().toString().equals("00")) {
                    bi.ch06.setEnabled(true);
                    bi.ch06a.setEnabled(true);
                    return;
                }
                int day = bi.ch05a.getText().toString().equals("00") ? 0 : Integer.parseInt(bi.ch05a.getText().toString());
                int month = Integer.parseInt(bi.ch05b.getText().toString());
                int year = Integer.parseInt(bi.ch05c.getText().toString());

                AgeModel age = DateRepository.Companion.getCalculatedAge(year, month, day);
                if (age == null) return;
                bi.ch06.setText(String.valueOf(age.getYear()));
                bi.ch06a.setText(String.valueOf(age.getMonth()));
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

    }

    @Override
    public boolean onSupportNavigateUp() {
        setResult(RESULT_CANCELED);
        finish();
        return true;
    }

    @Override
    public void onBackPressed() {
        Toast.makeText(this, "Press top back button.", Toast.LENGTH_SHORT).show();
    }
}
