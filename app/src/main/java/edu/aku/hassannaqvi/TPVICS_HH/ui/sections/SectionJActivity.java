package edu.aku.hassannaqvi.TPVICS_HH.ui.sections;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.validatorcrawler.aliazaz.Clear;
import com.validatorcrawler.aliazaz.Validator;

import org.json.JSONException;
import org.json.JSONObject;

import edu.aku.hassannaqvi.TPVICS_HH.CONSTANTS;
import edu.aku.hassannaqvi.TPVICS_HH.R;
import edu.aku.hassannaqvi.TPVICS_HH.contracts.ChildContract;
import edu.aku.hassannaqvi.TPVICS_HH.core.DatabaseHelper;
import edu.aku.hassannaqvi.TPVICS_HH.core.MainApp;
import edu.aku.hassannaqvi.TPVICS_HH.databinding.ActivitySectionJBinding;
import edu.aku.hassannaqvi.TPVICS_HH.utils.Util;

import static edu.aku.hassannaqvi.TPVICS_HH.CONSTANTS.SEC_J_FLAG;

public class SectionJActivity extends AppCompatActivity {

    ActivitySectionJBinding bi;
    int totalMonth = 0;
//    private FamilyMembersContract fmc_child, res_child;
//    private Pair<List<Integer>, List<String>> childLst, resList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        bi = DataBindingUtil.setContentView(this, R.layout.activity_section_j);
        bi.setCallback(this);

        setUIComponent();
        setListener();

    }

    private void setUIComponent() {

        /*childLst = mainVModel.getAllUnder5();

        List<String> childLst = new ArrayList<String>() {
            {
                add("....");
                addAll(SectionJActivity.this.childLst.getSecond());
            }
        };

        bi.j100.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_dropdown_item_1line, childLst));*/

        bi.txtHeadLbl.setText(new StringBuilder(MainApp.indexKishMWRAChild.getName().toUpperCase()).append("\n")
                .append(MainApp.selectedKishMWRA.getName().toUpperCase()));

        bi.j103c.setMaxvalue(CONSTANTS.MAXYEAR);
        bi.j103c.setMinvalue(CONSTANTS.MINYEAR_IM);

        bi.j10401y.setMaxvalue(CONSTANTS.MAXYEAR);
        bi.j10401y.setMinvalue(CONSTANTS.MINYEAR_IM);

        bi.j10402y.setMaxvalue(CONSTANTS.MAXYEAR);
        bi.j10402y.setMinvalue(CONSTANTS.MINYEAR_IM);

        bi.j10403y.setMaxvalue(CONSTANTS.MAXYEAR);
        bi.j10403y.setMinvalue(CONSTANTS.MINYEAR_IM);

        bi.j10404y.setMaxvalue(CONSTANTS.MAXYEAR);
        bi.j10404y.setMinvalue(CONSTANTS.MINYEAR_IM);

        bi.j10405y.setMaxvalue(CONSTANTS.MAXYEAR);
        bi.j10405y.setMinvalue(CONSTANTS.MINYEAR_IM);

        bi.j10406y.setMaxvalue(CONSTANTS.MAXYEAR);
        bi.j10406y.setMinvalue(CONSTANTS.MINYEAR_IM);

        bi.j10407y.setMaxvalue(CONSTANTS.MAXYEAR);
        bi.j10407y.setMinvalue(CONSTANTS.MINYEAR_IM);

        bi.j10408y.setMaxvalue(CONSTANTS.MAXYEAR);
        bi.j10408y.setMinvalue(CONSTANTS.MINYEAR_IM);

        //Immunization visibility
        totalMonth = (Integer.parseInt(MainApp.indexKishMWRAChild.getAge()) * 12) + Integer.parseInt(MainApp.indexKishMWRAChild.getMonthfm());


        if (totalMonth > 1) {
            bi.fldGrpCVj10403.setVisibility(View.VISIBLE);
            bi.fldGrpCVj10404.setVisibility(View.VISIBLE);
            bi.fldGrpCVj10405.setVisibility(View.VISIBLE);
            bi.fldGrpCVj10406.setVisibility(View.VISIBLE);
        }

        if (totalMonth > 2) {
            bi.fldGrpCVj10407.setVisibility(View.VISIBLE);
            bi.fldGrpCVj10408.setVisibility(View.VISIBLE);
        }

    }

    private void populateRespondentSpinner() {
        /*resList = mainVModel.getAllRespondent();
        List<String> reponList = new ArrayList<String>() {
            {
                add("....");
                addAll(SectionJActivity.this.resList.getSecond());
            }
        };

        bi.j100res.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_dropdown_item_1line, reponList));*/
    }

    private void setListener() {

        /*bi.j100.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position == 0) return;
                fmc_child = mainVModel.getMemberInfo(childLst.getFirst().get(bi.j100.getSelectedItemPosition() - 1));
                if (fmc_child.getMother_serial().equals("97")) {
                    bi.respondentSpinner.setVisibility(View.VISIBLE);
                    populateRespondentSpinner();
                } else {
                    bi.respondentSpinner.setVisibility(View.GONE);
                    res_child = mainVModel.getMemberInfo(Integer.valueOf(fmc_child.getSerialno()));
                }


            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        bi.j100res.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position == 0) return;
                res_child = mainVModel.getMemberInfo(resList.getFirst().get(bi.j100res.getSelectedItemPosition() - 1));
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });*/


        bi.j101.setOnCheckedChangeListener(((radioGroup, i) -> {

            if (i != bi.j101c.getId()) {
                Clear.clearAllFields(bi.fldGrpCVj102);
            }

        }));

        bi.j102.setOnCheckedChangeListener(((radioGroup, i) -> {

            if (i == bi.j102b.getId()) {
                Clear.clearAllFields(bi.fldGrpSectionJ011);
            }

        }));
    }

    public void BtnContinue() {
        if (formValidation()) {
            try {
                SaveDraft();
            } catch (Exception e) {
                e.printStackTrace();
            }
            if (UpdateDB()) {
                finish();
                startActivity(new Intent(this, !bi.j102b.isChecked() ? (totalMonth > 2 ? SectionJ02Activity.class : SectionJ03Activity.class) : SectionJ03Activity.class)
                        .putExtra(SEC_J_FLAG, bi.j102b.isChecked())
                );
            } else {
                Toast.makeText(this, "Failed to Update Database!", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private boolean UpdateDB() {
        DatabaseHelper db = MainApp.appInfo.getDbHelper();
        int updcount = db.updatesChildColumn(ChildContract.SingleChild.COLUMN_SJ, MainApp.child.getsJ());
        if (updcount == 1) {
            return true;
        } else {
            Toast.makeText(this, "Updating Database... ERROR!", Toast.LENGTH_SHORT).show();
            return false;
        }
    }

    private void SaveDraft() throws JSONException {

        JSONObject json = new JSONObject();
        json.put("j_fm_uid", MainApp.indexKishMWRAChild.getUid());
        json.put("j_fm_serial", MainApp.indexKishMWRAChild.getSerialno());
        /*json.put("j_res_fm_uid", res_child.getUid());
        json.put("j_res_fm_serial", res_child.getSerialno());*/

        json.put("j101", bi.j101a.isChecked() ? "1" :
                bi.j101b.isChecked() ? "2" :
                        bi.j101c.isChecked() ? "3" : "0");

        json.put("j102", bi.j102a.isChecked() ? "1" :
                bi.j102b.isChecked() ? "2" : "0");

        json.put("j103d", bi.j103d.isChecked() ? "98" : "0");
        json.put("j103a", bi.j103a.getText().toString());
        json.put("j103b", bi.j103b.getText().toString());
        json.put("j103c", bi.j103c.getText().toString());

        json.put("j10401d", bi.j10401d.getText().toString());
        json.put("j10401m", bi.j10401m.getText().toString());
        json.put("j10401y", bi.j10401y.getText().toString());

        json.put("j10402d", bi.j10402d.getText().toString());
        json.put("j10402m", bi.j10402m.getText().toString());
        json.put("j10402y", bi.j10402y.getText().toString());

        json.put("j10403d", bi.j10403d.getText().toString());
        json.put("j10403m", bi.j10403m.getText().toString());
        json.put("j10403y", bi.j10403y.getText().toString());

        json.put("j10404d", bi.j10404d.getText().toString());
        json.put("j10404m", bi.j10404m.getText().toString());
        json.put("j10404y", bi.j10404y.getText().toString());

        json.put("j10405d", bi.j10405d.getText().toString());
        json.put("j10405m", bi.j10405m.getText().toString());
        json.put("j10405y", bi.j10405y.getText().toString());

        json.put("j10406d", bi.j10406d.getText().toString());
        json.put("j10406m", bi.j10406m.getText().toString());
        json.put("j10406y", bi.j10406y.getText().toString());

        json.put("j10407d", bi.j10407d.getText().toString());
        json.put("j10407m", bi.j10407m.getText().toString());
        json.put("j10407y", bi.j10407y.getText().toString());

        json.put("j10408d", bi.j10408d.getText().toString());
        json.put("j10408m", bi.j10408m.getText().toString());
        json.put("j10408y", bi.j10408y.getText().toString());

        MainApp.child.setsJ(String.valueOf(json));

    }

    private boolean formValidation() {

        return Validator.emptyCheckingContainer(this, bi.fldGrpSectionJ01);
    }

    @Override
    public void onBackPressed() {
        Toast.makeText(this, "You can't go back", Toast.LENGTH_SHORT).show();
    }

    public void BtnEnd() {

        Util.openEndActivity(this);
    }
}
