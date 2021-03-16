package edu.aku.hassannaqvi.tpvics_hh.ui.sections;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
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
import edu.aku.hassannaqvi.tpvics_hh.core.MainApp;
import edu.aku.hassannaqvi.tpvics_hh.database.DatabaseHelper;
import edu.aku.hassannaqvi.tpvics_hh.databinding.ActivitySectionSs2Binding;
import edu.aku.hassannaqvi.tpvics_hh.models.FormsContract;
import edu.aku.hassannaqvi.tpvics_hh.ui.other.EndingActivity;

import static edu.aku.hassannaqvi.tpvics_hh.utils.AppUtilsKt.openEndActivity;

public class SectionSS2Activity extends AppCompatActivity {

    ActivitySectionSs2Binding bi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        bi = DataBindingUtil.setContentView(this, R.layout.activity_section_ss2);
        bi.setCallback(this);
        setupSkips();

    }


    private void setupSkips() {

        bi.ss22.setOnCheckedChangeListener(((radioGroup, i) -> {
            Clear.clearAllFields(bi.fldGrpCVss23, i != bi.ss22b.getId());
        }));


        bi.ss24.setOnCheckedChangeListener(((radioGroup, i) -> {
            Clear.clearAllFields(bi.ss25cvall, i != bi.ss24b.getId());
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

                /*selectedChildren = FamilyMembersListActivity.mainVModel.getAllUnder12();
                Class<?> T = EndingActivity.class;
                if (selectedChildren.getFirst().size() > 0) T = SectionCHAActivity.class;
                startActivity(new Intent(this, T).putExtra("complete", true));*/
                startActivity(new Intent(this, EndingActivity.class).putExtra("complete", true));
            } else {
                Toast.makeText(this, "Failed to Update Database!", Toast.LENGTH_SHORT).show();
            }

        }
    }


    private boolean UpdateDB() {

        DatabaseHelper db = MainApp.appInfo.getDbHelper();
        int updcount = db.updatesFormColumn(FormsContract.FormsTable.COLUMN_SM, MainApp.fc.getsM());
        if (updcount == 1) {
            return true;
        } else {
            Toast.makeText(this, "Updating Database... ERROR!", Toast.LENGTH_SHORT).show();
            return false;
        }

    }


    private void SaveDraft() throws JSONException {

        JSONObject json = new JSONObject();

        json.put("ss15a", bi.ss15aa.isChecked() ? "1"
                : bi.ss15ab.isChecked() ? "2"
                : "0");

        json.put("ss15b", bi.ss15ba.isChecked() ? "1"
                : bi.ss15bb.isChecked() ? "2"
                : "0");

        json.put("ss15c", bi.ss15ca.isChecked() ? "1"
                : bi.ss15cb.isChecked() ? "2"
                : "0");

        json.put("ss15d", bi.ss15da.isChecked() ? "1"
                : bi.ss15db.isChecked() ? "2"
                : "0");

        json.put("ss15e", bi.ss15ea.isChecked() ? "1"
                : bi.ss15eb.isChecked() ? "2"
                : "0");

        json.put("ss15f", bi.ss15fa.isChecked() ? "1"
                : bi.ss15fb.isChecked() ? "2"
                : "0");

        json.put("ss15g", bi.ss15ga.isChecked() ? "1"
                : bi.ss15gb.isChecked() ? "2"
                : "0");

        json.put("ss15h", bi.ss15ha.isChecked() ? "1"
                : bi.ss15hb.isChecked() ? "2"
                : "0");

        json.put("ss15i", bi.ss15ia.isChecked() ? "1"
                : bi.ss15ib.isChecked() ? "2"
                : "0");


/*        json.put("ss16a", bi.ss16a.isChecked() ? "1" : "0");
        json.put("ss16b", bi.ss16b.isChecked() ? "2" : "0");
        json.put("ss16c", bi.ss16c.isChecked() ? "3" : "0");
        json.put("ss16d", bi.ss16d.isChecked() ? "4" : "0");
        json.put("ss16e", bi.ss16e.isChecked() ? "5" : "0");
        json.put("ss16f", bi.ss16f.isChecked() ? "6" : "0");
        json.put("ss1696", bi.ss1696.isChecked() ? "96" : "0");*/

        json.put("ss17", bi.ss17a.isChecked() ? "1"
                : bi.ss17b.isChecked() ? "2"
                : bi.ss17c.isChecked() ? "3"
                : bi.ss17d.isChecked() ? "4"
                : bi.ss17e.isChecked() ? "5"
                : bi.ss17f.isChecked() ? "6"
                : bi.ss17g.isChecked() ? "7"
                : bi.ss17h.isChecked() ? "8"
                : bi.ss17i.isChecked() ? "9"
                : bi.ss17j.isChecked() ? "10"
                : bi.ss17k.isChecked() ? "11"
                : bi.ss1796.isChecked() ? "96"
                : "0");
        json.put("ss1796x", bi.ss1796x.getText().toString());

        json.put("ss18", bi.ss18a.isChecked() ? "11"
                : bi.ss18b.isChecked() ? "12"
                : bi.ss18c.isChecked() ? "21"
                : bi.ss18d.isChecked() ? "22"
                : bi.ss18e.isChecked() ? "31"
                : bi.ss18f.isChecked() ? "32"
                : bi.ss18g.isChecked() ? "33"
                : bi.ss18h.isChecked() ? "34"
                : bi.ss18i.isChecked() ? "35"
                : bi.ss18j.isChecked() ? "36"
                : bi.ss18k.isChecked() ? "37"
                : bi.ss1896.isChecked() ? "96"
                : "0");
        json.put("ss1896x", bi.ss1896x.getText().toString());

        json.put("ss19", bi.ss19a.isChecked() ? "11"
                : bi.ss19b.isChecked() ? "12"
                : bi.ss19c.isChecked() ? "13"
                : bi.ss19d.isChecked() ? "21"
                : bi.ss19e.isChecked() ? "22"
                : bi.ss19f.isChecked() ? "23"
                : bi.ss19g.isChecked() ? "24"
                : bi.ss19h.isChecked() ? "31"
                : bi.ss19i.isChecked() ? "12"
                : bi.ss19j.isChecked() ? "33"
                : bi.ss19k.isChecked() ? "34"
                : bi.ss19l.isChecked() ? "35"
                : bi.ss19m.isChecked() ? "36"
                : bi.ss19n.isChecked() ? "37"
                : bi.ss1996.isChecked() ? "96"
                : "0");
        json.put("ss1996x", bi.ss1996x.getText().toString());

        json.put("ss20", bi.ss20a.isChecked() ? "11"
                : bi.ss20b.isChecked() ? "12"
                : bi.ss20c.isChecked() ? "31"
                : bi.ss20d.isChecked() ? "21"
                : bi.ss20e.isChecked() ? "22"
                : bi.ss20f.isChecked() ? "23"
                : bi.ss20g.isChecked() ? "24"
                : bi.ss20h.isChecked() ? "25"
                : bi.ss20i.isChecked() ? "25"
                : bi.ss20j.isChecked() ? "27"
                : bi.ss20k.isChecked() ? "31"
                : bi.ss20l.isChecked() ? "32"
                : bi.ss20m.isChecked() ? "33"
                : bi.ss20n.isChecked() ? "34"
                : bi.ss20o.isChecked() ? "35"
                : bi.ss20p.isChecked() ? "36"
                : bi.ss2096.isChecked() ? "96"
                : "0");
        json.put("ss2096x", bi.ss2096x.getText().toString());


        json.put("ss21", bi.ss21.getText().toString());


        json.put("ss22", bi.ss22a.isChecked() ? "1"
                : bi.ss22b.isChecked() ? "2"
                : "0");


        /*json.put("ss23", bi.ss23a.isChecked() ? "1"
                : bi.ss23b.isChecked() ? "2"
                : bi.ss2398.isChecked() ? "98"
                : "0");
        json.put("ss23ax", bi.ss23ax.getText().toString());
        json.put("ss23bx", bi.ss23bx.getText().toString());*/


        json.put("ss23", bi.ss23a.isChecked() ? "1"
                : bi.ss23b.isChecked() ? "2"
                : bi.ss23c.isChecked() ? "98"
                : "0");
        json.put("ss23landx", bi.ss23landx.getText().toString());


        json.put("ss24", bi.ss24a.isChecked() ? "1"
                : bi.ss24b.isChecked() ? "2"
                : "0");


        json.put("ss25a", bi.ss25a.getText().toString());
        json.put("ss25b", bi.ss25b.getText().toString());
        json.put("ss25c", bi.ss25c.getText().toString());
        json.put("ss25d", bi.ss25d.getText().toString());
        json.put("ss25e", bi.ss25e.getText().toString());
        json.put("ss25f", bi.ss25f.getText().toString());
        json.put("ss25g", bi.ss25g.getText().toString());


        json.put("ss26", bi.ss26a.isChecked() ? "1"
                : bi.ss26b.isChecked() ? "2"
                : bi.ss2698.isChecked() ? "98"
                : "0");

        json.put("ss28", bi.ss28.getText().toString());


        MainApp.fc.setsM(String.valueOf(json));

    }


    private boolean formValidation() {

        if (!Validator.emptyCheckingContainer(this, bi.fldGrpSectionM)) return false;
        bi.ss25a.setFocusable(false);
        if (bi.ss24a.isChecked()) {
            int total = Integer.parseInt(bi.ss25a.getText().toString()) + Integer.parseInt(bi.ss25b.getText().toString()) + Integer.parseInt(bi.ss25c.getText().toString()) +
                    Integer.parseInt(bi.ss25d.getText().toString()) + Integer.parseInt(bi.ss25e.getText().toString()) + Integer.parseInt(bi.ss25f.getText().toString()) +
                    Integer.parseInt(bi.ss25g.getText().toString());
            if (total == 0) {
                bi.ss25a.setError("Invalid value!!");
                bi.ss25a.setFocusable(true);
                return false;
            }
        }
        return true;
    }


    public void BtnEnd() {
        openEndActivity(this);
    }


    @Override
    public void onBackPressed() {
        Toast.makeText(this, "Back Press Not Allowed", Toast.LENGTH_SHORT).show();
    }


    public void showTooltip(@NotNull View view) {
        if (view.getId() != View.NO_ID) {
            String package_name = getApplicationContext().getPackageName();

            // Question Number Textview ID must be prefixed with q_ e.g.: 'q_aa12a'
            String infoid = view.getResources().getResourceName(view.getId()).replace(package_name + ":id/q_", "");

            // Question info text must be suffixed with _info e.g.: aa12a_info
            int stringRes = this.getResources().getIdentifier(infoid + "_info", "string", getApplicationContext().getPackageName());

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