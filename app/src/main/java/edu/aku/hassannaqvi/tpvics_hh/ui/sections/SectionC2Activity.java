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
import edu.aku.hassannaqvi.tpvics_hh.contracts.FormsContract;
import edu.aku.hassannaqvi.tpvics_hh.core.DatabaseHelper;
import edu.aku.hassannaqvi.tpvics_hh.core.MainApp;
import edu.aku.hassannaqvi.tpvics_hh.databinding.ActivitySectionC2Binding;
import edu.aku.hassannaqvi.tpvics_hh.ui.list_activity.FamilyMembersListActivity;
import edu.aku.hassannaqvi.tpvics_hh.ui.other.EndingActivity;

import static edu.aku.hassannaqvi.tpvics_hh.core.MainApp.selectedChildren;
import static edu.aku.hassannaqvi.tpvics_hh.utils.UtilKt.openEndActivity;

public class SectionC2Activity extends AppCompatActivity {

    ActivitySectionC2Binding bi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        bi = DataBindingUtil.setContentView(this, R.layout.activity_section_c2);
        bi.setCallback(this);
        setupSkips();

    }


    private void setupSkips() {

        bi.ss22.setOnCheckedChangeListener(((radioGroup, i) -> {
            if (i == bi.ss22b.getId()) {
                Clear.clearAllFields(bi.ss23cv, false);
            } else {
                Clear.clearAllFields(bi.ss23cv, true);
            }
        }));


        bi.ss24.setOnCheckedChangeListener(((radioGroup, i) -> {
            if (i == bi.ss24b.getId()) {
                Clear.clearAllFields(bi.ss25cvall, false);
            } else {
                Clear.clearAllFields(bi.ss25cvall, true);
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

                selectedChildren = FamilyMembersListActivity.mainVModel.getAllUnder12();
                Class<?> T = EndingActivity.class;
                if (selectedChildren.getFirst().size() > 0) T = SectionCHAActivity.class;
                startActivity(new Intent(this, T).putExtra("complete", true));
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


        json.put("ss16a", bi.ss16a.isChecked() ? "1" : "0");
        json.put("ss16b", bi.ss16b.isChecked() ? "2" : "0");
        json.put("ss16c", bi.ss16c.isChecked() ? "3" : "0");
        json.put("ss16d", bi.ss16d.isChecked() ? "4" : "0");
        json.put("ss16e", bi.ss16e.isChecked() ? "5" : "0");
        json.put("ss16f", bi.ss16f.isChecked() ? "6" : "0");
        json.put("ss1696", bi.ss1696.isChecked() ? "96" : "0");

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

        json.put("ss18", bi.ss18a.isChecked() ? "1"
                : bi.ss18b.isChecked() ? "2"
                : bi.ss18c.isChecked() ? "3"
                : bi.ss18d.isChecked() ? "4"
                : bi.ss18e.isChecked() ? "5"
                : bi.ss18f.isChecked() ? "6"
                : bi.ss18g.isChecked() ? "7"
                : bi.ss18h.isChecked() ? "8"
                : bi.ss18i.isChecked() ? "9"
                : bi.ss18j.isChecked() ? "10"
                : bi.ss18k.isChecked() ? "11"
                : bi.ss1896.isChecked() ? "96"
                : "0");
        json.put("ss1896x", bi.ss1896x.getText().toString());

        json.put("ss19", bi.ss19a.isChecked() ? "1"
                : bi.ss19b.isChecked() ? "2"
                : bi.ss19c.isChecked() ? "3"
                : bi.ss19d.isChecked() ? "4"
                : bi.ss19e.isChecked() ? "5"
                : bi.ss19f.isChecked() ? "6"
                : bi.ss19g.isChecked() ? "7"
                : bi.ss19h.isChecked() ? "8"
                : bi.ss19i.isChecked() ? "9"
                : bi.ss19j.isChecked() ? "10"
                : bi.ss19k.isChecked() ? "11"
                : bi.ss19l.isChecked() ? "12"
                : bi.ss19m.isChecked() ? "13"
                : bi.ss19n.isChecked() ? "14"
                : bi.ss1996.isChecked() ? "96"
                : "0");
        json.put("ss1996x", bi.ss1996x.getText().toString());

        json.put("ss20", bi.ss20a.isChecked() ? "1"
                : bi.ss20b.isChecked() ? "2"
                : bi.ss20c.isChecked() ? "3"
                : bi.ss20d.isChecked() ? "4"
                : bi.ss20e.isChecked() ? "5"
                : bi.ss20f.isChecked() ? "6"
                : bi.ss20g.isChecked() ? "7"
                : bi.ss20h.isChecked() ? "8"
                : bi.ss20i.isChecked() ? "9"
                : bi.ss20j.isChecked() ? "10"
                : bi.ss20k.isChecked() ? "11"
                : bi.ss20l.isChecked() ? "12"
                : bi.ss20m.isChecked() ? "13"
                : bi.ss20n.isChecked() ? "14"
                : bi.ss20o.isChecked() ? "15"
                : bi.ss20p.isChecked() ? "16"
                : bi.ss2096.isChecked() ? "96"
                : "0");
        json.put("ss2096x", bi.ss2096x.getText().toString());


        json.put("ss21", bi.ss21.getText().toString());


        json.put("ss22", bi.ss22a.isChecked() ? "1"
                : bi.ss22b.isChecked() ? "2"
                : "0");


        json.put("ss23", bi.ss23a.isChecked() ? "1"
                : bi.ss23b.isChecked() ? "2"
                : bi.ss2398.isChecked() ? "98"
                : "0");
        json.put("ss23ax", bi.ss23ax.getText().toString());
        json.put("ss23bx", bi.ss23bx.getText().toString());


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
        json.put("ss2598", bi.ss2598.isChecked() ? "98" : "0");


        json.put("ss26", bi.ss26a.isChecked() ? "1"
                : bi.ss26b.isChecked() ? "2"
                : bi.ss2698.isChecked() ? "98"
                : "0");

        json.put("ss28", bi.ss28.getText().toString());


        MainApp.fc.setsM(String.valueOf(json));

    }


    private boolean formValidation() {

        return Validator.emptyCheckingContainer(this, bi.fldGrpSectionM);

    }


    public void BtnEnd() {
        openEndActivity(this);
    }


    @Override
    public void onBackPressed() {
        Toast.makeText(this, "You can't go back", Toast.LENGTH_SHORT).show();
    }

}