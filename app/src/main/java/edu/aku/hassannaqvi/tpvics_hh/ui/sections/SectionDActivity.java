package edu.aku.hassannaqvi.tpvics_hh.ui.sections;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.validatorcrawler.aliazaz.Clear;
import com.validatorcrawler.aliazaz.Validator;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import edu.aku.hassannaqvi.tpvics_hh.R;
import edu.aku.hassannaqvi.tpvics_hh.contracts.FamilyMembersContract;
import edu.aku.hassannaqvi.tpvics_hh.core.DatabaseHelper;
import edu.aku.hassannaqvi.tpvics_hh.core.MainApp;
import edu.aku.hassannaqvi.tpvics_hh.databinding.ActivitySectionDBinding;
import edu.aku.hassannaqvi.tpvics_hh.datecollection.AgeModel;
import edu.aku.hassannaqvi.tpvics_hh.datecollection.DateRepository;
import edu.aku.hassannaqvi.tpvics_hh.ui.list_activity.FamilyMembersListActivity;
import edu.aku.hassannaqvi.tpvics_hh.viewmodel.MainVModel;
import kotlin.Pair;

import static edu.aku.hassannaqvi.tpvics_hh.CONSTANTS.SERIAL_EXTRA;

public class SectionDActivity extends AppCompatActivity {

    ActivitySectionDBinding bi;
    private MainVModel mainVModel;
    private FamilyMembersContract fmc;
    private boolean fmcFLAG = false;
    private int serial = 0;
    private Pair<List<Integer>, List<String>> menSLst;
    private Pair<List<Integer>, List<String>> womenSLst;
    private FamilyMembersContract motherFMC, fatheFMC;
    private String motherSerial, fatherSerial;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        bi = DataBindingUtil.setContentView(this, R.layout.activity_section_d);
        bi.setCallback(this);

        setUIComponent();
        setListeners();
    }

    private void setUIComponent() {
        mainVModel = FamilyMembersListActivity.Companion.getMainVModel();
        serial = getIntent().getIntExtra(SERIAL_EXTRA, 0);
        bi.d101.setText(String.valueOf(serial));
        fmc = mainVModel.getMemberInfo(serial);
        fmcFLAG = fmc == null;

        if (fmcFLAG) {
            bi.fldGrpSectionD01.setVisibility(View.VISIBLE);
            bi.fldGrpSectionD02.setVisibility(View.GONE);
            fmc = new FamilyMembersContract();
        } else {
            /*bi.d102Name.setText(new StringBuilder(fmc.getName().toUpperCase()).append("\n")
                    .append(getResources().getString(R.string.d101name))
                    .append(":")
                    .append(fmc.getSerialno()));*/
            bi.d102Name.setText(new StringBuilder(fmc.getName()));
            bi.d102Num.setText(new StringBuilder(fmc.getSerialno()));
            bi.fldGrpSectionD01.setVisibility(View.GONE);
            bi.fldGrpSectionD02.setVisibility(View.VISIBLE);

            menSLst = mainVModel.getAllMenWomenName(1, Integer.valueOf(fmc.getSerialno()));
            womenSLst = mainVModel.getAllMenWomenName(2, Integer.valueOf(fmc.getSerialno()));

            List<String> menLst = new ArrayList<String>() {
                {
                    add("....");
                    add("NA");
                    if (menSLst != null) addAll(menSLst.getSecond());
                }
            };
            List<String> womenLst = new ArrayList<String>() {
                {
                    add("....");
                    add("NA");
                    if (womenSLst != null) addAll(womenSLst.getSecond());
                }
            };

            bi.d106.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_dropdown_item_1line, menLst));
            bi.d107.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_dropdown_item_1line, womenLst));
        }

        if (serial == 1) {
            Clear.clearAllFields(bi.d103, false);
            bi.d103a.setChecked(true);
            bi.ch06.setMinvalue(15);
        }

    }

    public void BtnContinue() {
        if (!formValidation()) return;
        try {
            SaveDraft();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        if (fmcFLAG) {
            setResult(RESULT_OK, new Intent().putExtra(SERIAL_EXTRA, serial));
            finish();
        } else {
            if (UpdateDB()) {
                setResult(RESULT_OK);
                finish();
            } else {
                Toast.makeText(this, "Failed to Update Database!", Toast.LENGTH_SHORT).show();
            }
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

        if (fmcFLAG) {
            fmc.setUuid(MainApp.fc.get_UID());
            fmc.setClusterno(MainApp.fc.getClusterCode());
            fmc.setHhno(MainApp.fc.getHhno());
            fmc.setSerialno(bi.d101.getText().toString());
            fmc.setName(bi.d102.getText().toString());
            fmc.setFormdate(MainApp.fc.getFormDate());

            fmc.setRelHH(bi.d103a.isChecked() ? "1"
                    : bi.d103b.isChecked() ? "2"
                    : bi.d103c.isChecked() ? "3"
                    : bi.d103d.isChecked() ? "4"
                    : bi.d103e.isChecked() ? "5"
                    : bi.d103f.isChecked() ? "6"
                    : bi.d103g.isChecked() ? "7"
                    : bi.d103h.isChecked() ? "8"
                    : bi.d103i.isChecked() ? "9"
                    : bi.d103j.isChecked() ? "10"
                    : bi.d103k.isChecked() ? "11"
                    : bi.d103l.isChecked() ? "12"
                    : bi.d103m.isChecked() ? "13"
                    : bi.d103n.isChecked() ? "14"
                    : bi.d103o.isChecked() ? "15"
                    : "0");

            fmc.setGender(bi.d104a.isChecked() ? "1"
                    : bi.d104b.isChecked() ? "2"
                    : "0");

            fmc.setAge("-1");

            // Update in ViewModel
            mainVModel.setFamilyMembers(fmc);
            serial++;
            return;
        }

        fmc.setMarital(bi.d105a.isChecked() ? "1"
                : bi.d105b.isChecked() ? "2"
                : bi.d105c.isChecked() ? "3"
                : bi.d105d.isChecked() ? "4"
                : "0");

        JSONObject sd = new JSONObject();

//        sd.put("formdate", MainApp.fc.getFormDate());
        sd.put("username", MainApp.fc.getUser());
        sd.put("deviceid", MainApp.appInfo.getDeviceID());
        sd.put("tagid", MainApp.fc.getDevicetagID());
        sd.put("appversion", MainApp.appInfo.getAppVersion());
        sd.put("_luid", MainApp.fc.getLuid());

        sd.put("d106", fatherSerial);
        fmc.setfName(bi.d106.getSelectedItem().toString());

        fmc.setMother_name(bi.d107.getSelectedItem().toString());
        sd.put("d107", motherSerial);
        fmc.setMother_serial(motherSerial);

        sd.put("ch05a", bi.ch05a.getText().toString());
        sd.put("ch05b", bi.ch05b.getText().toString());
        sd.put("ch05c", bi.ch05c.getText().toString());
        sd.put("ch06", bi.ch06.getText().toString());
        fmc.setAge(bi.ch06.getText().toString());
        fmc.setMonthfm(bi.ch06a.getText().toString().trim().isEmpty() ? "0" : bi.ch06a.getText().toString());

        sd.put("d110", bi.d110a.isChecked() ? "0"
                : bi.d110b.isChecked() ? "1"
                : bi.d110c.isChecked() ? "2"
                : bi.d110d.isChecked() ? "3"
                : bi.d110e.isChecked() ? "4"
                : bi.d110f.isChecked() ? "5"
                : bi.d110g.isChecked() ? "6"
                : bi.d110h.isChecked() ? "7"
                : bi.d110i.isChecked() ? "8"
                : bi.d110j.isChecked() ? "9"
                : bi.d110k.isChecked() ? "10"
                : bi.d110l.isChecked() ? "98"
                : bi.d110m.isChecked() ? "99"
                : "0");

        sd.put("d111", bi.d111a.isChecked() ? "1"
                : bi.d111b.isChecked() ? "2"
                : bi.d111c.isChecked() ? "3"
                : bi.d111d.isChecked() ? "4"
                : bi.d111e.isChecked() ? "5"
                : bi.d111f.isChecked() ? "6"
                : bi.d111g.isChecked() ? "6"
                : bi.d111h.isChecked() ? "8"
                : bi.d111i.isChecked() ? "9"
                : bi.d111j.isChecked() ? "99"
                : "0");

        sd.put("d115", bi.d115a.isChecked() ? "1"
                : bi.d115b.isChecked() ? "2"
                : "0");

        fmc.setAvailable(bi.d115a.isChecked() ? "1"
                : bi.d115b.isChecked() ? "2"
                : "0");

        fmc.setsD(String.valueOf(sd));

        // Update in ViewModel
        mainVModel.updateFamilyMembers(fmc);

        if (Integer.valueOf(fmc.getAge()) >= 15 && Integer.valueOf(fmc.getAge()) < 49 && fmc.getGender().equals("2") && !bi.d105b.isChecked())
            mainVModel.setMWRA(fmc);
        else if (Integer.valueOf(fmc.getAge()) < 5) {
            mainVModel.setChildU5(fmc);
            if (motherFMC == null) return;
            if (Integer.valueOf(motherFMC.getAge()) >= 15 && Integer.valueOf(motherFMC.getAge()) < 49 && motherFMC.getAvailable().equals("1"))
                mainVModel.setMwraChildU5(motherFMC);
        }

    }

    private boolean formValidation() {
        if (fmcFLAG) return Validator.emptyCheckingContainer(this, bi.fldGrpSectionD);
        else {
            if (!Validator.emptyCheckingContainer(this, bi.fldGrpSectionD))
                return false;
            if (!Validator.emptyEditTextPicker(this, bi.ch06))
                return false;
            if (!checkingParentsAge()) {
                String msg = "Requires difference of 10Years from parent age!!";
                Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
                bi.ch06.setError("Less then Parent Age");
                return false;
            }
            return true;
        }
    }

    private boolean checkingParentsAge() {

        if (menSLst.getFirst().size() != 0 && bi.d106.getSelectedItemPosition() != 1) {
            fatheFMC = mainVModel.getMemberInfo(menSLst.getFirst().get(bi.d106.getSelectedItemPosition() - 2));
            fatherSerial = fatheFMC.getSerialno();
        } else {
            fatheFMC = null;
            fatherSerial = "97";
        }

        if (womenSLst.getFirst().size() != 0 && bi.d107.getSelectedItemPosition() != 1) {
            motherFMC = mainVModel.getMemberInfo(womenSLst.getFirst().get(bi.d107.getSelectedItemPosition() - 2));
            motherSerial = motherFMC.getSerialno();
        } else {
            motherFMC = null;
            motherSerial = "97";
        }

        int fAge = fatheFMC != null ? Integer.valueOf(fatheFMC.getAge()) : 0;
        int mAge = motherFMC != null ? Integer.valueOf(motherFMC.getAge()) : 0;

        if (fAge == 0 && mAge == 0) return true;
        int maxAge = fAge > mAge ? mAge != 0 ? mAge : fAge : fAge != 0 ? fAge : mAge;

        return Integer.valueOf(Objects.requireNonNull(bi.ch06.getText()).toString().trim()) <= maxAge - 10;
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

        bi.d105.setOnCheckedChangeListener((group, checkedId) -> {
            if (fmc.getGender().equals("2") && !bi.d105b.isChecked())
                bi.d111a.setEnabled(true);
            else {
                bi.d111a.setEnabled(false);
                bi.d111a.setChecked(false);
            }
        });

        bi.ch06.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (bi.ch06.getText().toString().isEmpty()) return;
                int calAge = Integer.parseInt(bi.ch06.getText().toString());
                if (Integer.signum(calAge) == -1) return;
                personInfoFunctionality(calAge);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

    }

    private void personInfoFunctionality(int calAge) {

        if (calAge > 0) bi.fldGrpSectionD03.setVisibility(View.VISIBLE);
        else {
            Clear.clearAllFields(bi.fldGrpSectionD03);
            bi.fldGrpSectionD03.setVisibility(View.GONE);
        }

        if (calAge >= 10)
            bi.fldGrpCVd105.setVisibility(View.VISIBLE);
        else {
            bi.d105.clearCheck();
            bi.fldGrpCVd105.setVisibility(View.GONE);
        }

        Clear.clearAllFields(bi.d110, false);
        Clear.clearAllFields(bi.d111, false);

        if (calAge > 0 && calAge <= 2) {
            bi.d110a.setEnabled(true);
            bi.d110b.setEnabled(true);
            bi.d110m.setEnabled(true);
            bi.d111j.setEnabled(true);
            bi.d111g.setEnabled(true);
        }

        if (calAge > 2 && calAge <= 5) {
            bi.d110a.setEnabled(true);
            bi.d110b.setEnabled(true);
            bi.d110c.setEnabled(true);
            bi.d110m.setEnabled(true);
            bi.d111g.setEnabled(true);
            bi.d111j.setEnabled(true);
        }

        if (calAge > 5 && calAge <= 10) {
            bi.d110a.setEnabled(true);
            bi.d110d.setEnabled(true);
            bi.d110e.setEnabled(true);
            bi.d110l.setEnabled(true);
            bi.d110m.setEnabled(true);
            bi.d111g.setEnabled(true);
            bi.d111j.setEnabled(true);

            bi.d110b.setEnabled(true);
            bi.d110c.setEnabled(true);
        }

        if (calAge > 10 && calAge <= 20) {
            bi.d110a.setEnabled(true);
            bi.d110e.setEnabled(true);
            bi.d110f.setEnabled(true);
            bi.d110g.setEnabled(true);
            bi.d110j.setEnabled(true);
            bi.d110k.setEnabled(true);
            bi.d110l.setEnabled(true);
            bi.d110m.setEnabled(true);
            bi.d111a.setEnabled(true);
            bi.d111b.setEnabled(true);
            bi.d111c.setEnabled(true);
            bi.d111d.setEnabled(true);
            bi.d111e.setEnabled(true);
            bi.d111g.setEnabled(true);
            bi.d111h.setEnabled(true);
            bi.d111j.setEnabled(true);

            bi.d110d.setEnabled(true);
            bi.d110b.setEnabled(true);
            bi.d110c.setEnabled(true);
        }

        if (calAge > 20) {
            Clear.clearAllFields(bi.d110, true);
            Clear.clearAllFields(bi.d111, true);

        }

    }

    @Override
    public boolean onSupportNavigateUp() {
        /*if (fmcFLAG) {
            serial--;
            setResult(RESULT_CANCELED, new Intent().putExtra(SERIAL_EXTRA, serial));
            finish();
            return true;
        } else {
            Toast.makeText(this, "You can't go back!!", Toast.LENGTH_SHORT).show();
            return false;
        }*/

        setResult(RESULT_CANCELED);
        finish();

        return true;

    }

    @Override
    public void onBackPressed() {
        Toast.makeText(this, "Press top back button.", Toast.LENGTH_SHORT).show();
    }
}
