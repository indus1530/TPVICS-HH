package edu.aku.hassannaqvi.tpvics_hh.ui.sections;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Toast;

import com.validatorcrawler.aliazaz.Clear;
import com.validatorcrawler.aliazaz.Validator;

import org.jetbrains.annotations.NotNull;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import edu.aku.hassannaqvi.tpvics_hh.R;
import edu.aku.hassannaqvi.tpvics_hh.contracts.BLRandomContract;
import edu.aku.hassannaqvi.tpvics_hh.contracts.EnumBlockContract;
import edu.aku.hassannaqvi.tpvics_hh.contracts.FormsContract;
import edu.aku.hassannaqvi.tpvics_hh.core.DatabaseHelper;
import edu.aku.hassannaqvi.tpvics_hh.core.MainApp;
import edu.aku.hassannaqvi.tpvics_hh.databinding.ActivitySectionABinding;
import edu.aku.hassannaqvi.tpvics_hh.ui.list_activity.FamilyMembersListActivity;
import edu.aku.hassannaqvi.tpvics_hh.ui.other.EndingActivity;
import edu.aku.hassannaqvi.tpvics_hh.utils.EndSecAActivity;

import static edu.aku.hassannaqvi.tpvics_hh.utils.UtilKt.contextEndActivity;

public class SectionAActivity extends AppCompatActivity implements EndSecAActivity {

    ActivitySectionABinding bi;
    private DatabaseHelper db;
    private BLRandomContract bl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        bi = DataBindingUtil.setContentView(this, R.layout.activity_section_a);
        bi.setCallback(this);
        db = MainApp.appInfo.getDbHelper();
        setUIComponent();
        setTitle(R.string.hhsec);
    }

    public void hh07TextChanged(CharSequence s, int start, int before, int count) {
        bi.fldGrpSectionA01.setVisibility(View.GONE);
        bi.fldGrpSectionA02.setVisibility(View.GONE);
        Clear.clearAllFields(bi.fldGrpSectionA01);
    }

    private void setUIComponent() {

        bi.hh08.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                //      Toast.makeText(SectionAActivity.this, charSequence+" i="+i+" i1="+i1+" i2="+i2, Toast.LENGTH_LONG).show();

                if (i == 1 && i1 == 0 && i2 == 1) {
                    bi.hh08.setText(bi.hh08.getText().toString() + "-");
                }
                if (i == 6 && i1 == 0 && i2 == 1) {
                    bi.hh08.setText(bi.hh08.getText().toString() + "-");
                }
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                if (i == 0 && i1 == 0 && i2 == 1) {
                    bi.hh08.setText(bi.hh08.getText().toString() + "-");
                }
                if (i == 2 && i1 == 1 && i2 == 0) {
                    bi.hh08.setText(bi.hh08.getText().toString().substring(0, 1));
                }
                if (i == 1 && i1 == 4 && i2 == 5) {
                    bi.hh08.setText(bi.hh08.getText().toString() + "-");
                }
                if (i == 7 && i1 == 1 && i2 == 0) {
                    bi.hh08.setText(bi.hh08.getText().toString().substring(0, 6));
                }

            }

            @Override
            public void afterTextChanged(Editable editable) {
                bi.hh08.setSelection(bi.hh08.getText().toString().length());
            }
        });


        bi.hh18.setOnCheckedChangeListener(((radioGroup, i) -> {
            Clear.clearAllFields(bi.fldGrpAHH17, i == bi.hh18a.getId());
        }));

        bi.hh20.setOnCheckedChangeListener((group, checkedId) -> {
            if (checkedId == bi.hh20a.getId()) {
                bi.btnEnd.setVisibility(View.GONE);
                bi.btnNext.setVisibility(View.VISIBLE);
            } else {
                bi.btnEnd.setVisibility(View.VISIBLE);
                bi.btnNext.setVisibility(View.GONE);
            }
        });


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
                startActivity(new Intent(SectionAActivity.this, FamilyMembersListActivity.class).putExtra("sno", Integer.valueOf("5")));
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

        MainApp.fc = new FormsContract();
        MainApp.fc.setFormDate(new SimpleDateFormat("dd-MM-yy HH:mm").format(new Date().getTime()));
        MainApp.fc.setUser(MainApp.userName);
        MainApp.fc.setDeviceID(MainApp.appInfo.getDeviceID());
        MainApp.fc.setDevicetagID(MainApp.appInfo.getTagName());
        MainApp.fc.setAppversion(MainApp.appInfo.getAppVersion());
        MainApp.fc.setClusterCode(bi.hh07.getText().toString());
        MainApp.fc.setHhno(bi.hh08.getText().toString());
        MainApp.fc.setLuid(bl.getLUID());
        MainApp.setGPS(this); // Set GPS

        JSONObject json = new JSONObject();

        json.put("imei", MainApp.IMEI);
        /*json.put("rndid", bl.get_ID());
        json.put("luid", bl.getLUID());
        json.put("randDT", bl.getRandomDT());
        json.put("hh03", bl.getStructure());
        json.put("hh07", bl.getExtension());
        json.put("hhhead", bl.getHhhead());
        json.put("hh09", bl.getContact());
        json.put("hhss", bl.getSelStructure());*/
/*        json.put("hhheadpresent", bi.checkHHHeadpresent.isChecked() ? "1" : "2");
        json.put("hhheadpresentnew", bi.newHHheadname.getText().toString());*/


        json.put("hh07", bi.hh07.getText().toString());
        json.put("geoarea", bi.hh06txt.getText().toString() + ", " + bi.geoarea.getText().toString());
  /*      json.put("hh04", bi.hh04.getText().toString());
        json.put("hh05", bi.hh05.getText().toString());
        json.put("hh06", bi.hh06.getText().toString());*/
        json.put("hh07", bi.hh07.getText().toString());
        json.put("hh08", bi.hh08.getText().toString());
        json.put("hh09", MainApp.userName);
        //json.put("hh10", bi.hh10.getText().toString());
        json.put("hh11", new SimpleDateFormat("dd-MM-yy").format(new Date().getTime()));
        json.put("hh12", new SimpleDateFormat("HH:mm").format(new Date().getTime()));
        json.put("hh13a", bi.hh13a.getText().toString());
        json.put("hh13b", bi.hh13b.getText().toString());
        json.put("hh14", bi.hh14.getText().toString());

        json.put("hh15", bi.hh15a.isChecked() ? "1"
                : bi.hh15b.isChecked() ? "2"
                : "0");

        json.put("hh16a", bi.hh16a.getText().toString());
        json.put("hh16b", bi.hh16b.getText().toString());

        json.put("hh17a", bi.hh17a.getText().toString());
        json.put("hh17b", bi.hh17b.getText().toString());

        json.put("hh18", bi.hh18a.isChecked() ? "1"
                : bi.hh18b.isChecked() ? "2"
                : "0");

        json.put("hh18", bi.hh18a.isChecked() ? "1"
                : bi.hh18b.isChecked() ? "2"
                : "0");

        json.put("hh19", bi.hh19a.getText().toString());

        json.put("hh20", bi.hh20a.isChecked() ? "1"
                : bi.hh20b.isChecked() ? "2"
                : "0");

        json.put("hh21", bi.hh21.getText().toString());


        MainApp.fc.setsInfo(String.valueOf(json));

    }

    private boolean formValidation() {
        return Validator.emptyCheckingContainer(this, bi.fldGrpSectionA);
    }

    public void BtnEnd() {
        if (formValidation()) {
            contextEndActivity(this);
        }
    }

    public void BtnCheckCluster() {

        if (!Validator.emptyTextBox(this, bi.hh07)) return;
        boolean loginFlag;
        if (bi.hh07.getText().toString().length() != 6) {
            Toast.makeText(this, "Invalid Cluster length!!", Toast.LENGTH_SHORT).show();
            return;
        }
        /*int cluster = Integer.parseInt(bi.hh07.getText().toString().substring(3, 6));
        if (cluster < 500) {
            loginFlag = !(MainApp.userName.equals("test1234") || MainApp.userName.equals("dmu@aku") || MainApp.userName.substring(0, 4).equals("user"));
        } else {
            loginFlag = MainApp.userName.equals("test1234") || MainApp.userName.equals("dmu@aku") || MainApp.userName.substring(0, 4).equals("user");
        }
        if (!loginFlag) {
            Toast.makeText(this, "Can't proceed test cluster for current user!!", Toast.LENGTH_SHORT).show();
            return;
        }*/

        EnumBlockContract enumBlockContract = db.getEnumBlock(bi.hh07.getText().toString());
        if (enumBlockContract != null) {
            String selected = enumBlockContract.getGeoarea();
            if (!selected.equals("")) {

                String[] selSplit = selected.split("\\|");

                bi.fldGrpSectionA01.setVisibility(View.VISIBLE);
         /*       bi.hh03.setText(selSplit[0]);
                bi.hh04.setText(selSplit[1].equals("") ? "----" : selSplit[1]);
                bi.hh05.setText(selSplit[2].equals("") ? "----" : selSplit[2]);
                bi.hh06.setText(selSplit[3]);*/
                bi.hh06txt.setText(selSplit[3]);
                bi.geoarea.setText(selSplit[2] + ", " + selSplit[1] + ", " + selSplit[0]);
            }
        } else {
            Toast.makeText(this, "Sorry cluster not found!!", Toast.LENGTH_SHORT).show();
        }

    }

    public void BtnCheckHH() {
        if (!Validator.emptyTextBox(this, bi.hh07)) return;

        bl = MainApp.appInfo.getDbHelper().getHHFromBLRandom(bi.hh07.getText().toString(), bi.hh08.getText().toString().toUpperCase());

        if (bl != null) {
            Toast.makeText(this, "Household found!", Toast.LENGTH_SHORT).show();
            bi.hh08msg.setText("Household Found!");
            bi.hh08name.setText(bl.getHhhead().toUpperCase());
        } else {
            bi.fldGrpSectionA02.setVisibility(View.GONE);
            Toast.makeText(this, "No Household found!", Toast.LENGTH_SHORT).show();
        }


        bi.fldGrpSectionA02.setVisibility(View.VISIBLE);

    }

    @Override
    public void endSecAActivity(boolean flag) {
        if (!flag) return;

        try {
            SaveDraft();
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (UpdateDB()) {
            finish();
            startActivity(new Intent(this, EndingActivity.class).putExtra("complete", true)
                    .setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
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
