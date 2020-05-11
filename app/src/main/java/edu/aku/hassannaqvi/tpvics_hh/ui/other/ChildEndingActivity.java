package edu.aku.hassannaqvi.tpvics_hh.ui.other;

import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.validatorcrawler.aliazaz.Validator;

import edu.aku.hassannaqvi.tpvics_hh.R;
import edu.aku.hassannaqvi.tpvics_hh.core.DatabaseHelper;
import edu.aku.hassannaqvi.tpvics_hh.core.MainApp;
import edu.aku.hassannaqvi.tpvics_hh.databinding.ActivityChildEndingBinding;
import edu.aku.hassannaqvi.tpvics_hh.ui.sections.SectionSubInfoActivity;

import static edu.aku.hassannaqvi.tpvics_hh.CONSTANTS.CHILD_ENDING_AGE_ISSUE;

public class ChildEndingActivity extends AppCompatActivity {

    ActivityChildEndingBinding bi;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        bi = DataBindingUtil.setContentView(this, R.layout.activity_child_ending);
        bi.setCallback(this);

        boolean check = getIntent().getBooleanExtra("complete", false);

        if (check) {
            bi.istatusa.setEnabled(true);
            bi.istatusb.setEnabled(false);
            bi.istatusc.setEnabled(false);
            bi.istatusd.setEnabled(false);
            bi.istatuse.setEnabled(false);
            bi.istatusf.setEnabled(false);
            bi.istatus96.setEnabled(false);
        } else {
            boolean monthChild = getIntent().getBooleanExtra(CHILD_ENDING_AGE_ISSUE, false);
            boolean otherOptions = !monthChild;
            bi.istatusa.setEnabled(false);
            bi.istatusb.setEnabled(otherOptions);
            bi.istatusc.setEnabled(otherOptions);
            bi.istatusd.setEnabled(otherOptions);
            bi.istatuse.setEnabled(monthChild);
            bi.istatusf.setEnabled(otherOptions);
            bi.istatus96.setEnabled(otherOptions);
        }

    }

    public void BtnEnd() {
        if (formValidation()) {
            SaveDraft();
            if (UpdateDB()) {
                SectionSubInfoActivity.mainVModel.setChildListU5(MainApp.child);
                finish();
            } else {
                Toast.makeText(this, "Failed to Update Database!", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void SaveDraft() {

        MainApp.child.setCstatus(bi.istatusa.isChecked() ? "1"
                : bi.istatusb.isChecked() ? "2"
                : bi.istatusc.isChecked() ? "3"
                : bi.istatusd.isChecked() ? "4"
                : bi.istatuse.isChecked() ? "5"
                : bi.istatusf.isChecked() ? "6"
                : bi.istatus96.isChecked() ? "96"
                : "0");

        MainApp.child.setCstatus88x(bi.istatus96x.getText().toString());
    }

    public boolean UpdateDB() {

        DatabaseHelper db = MainApp.appInfo.getDbHelper();
        int updcount = db.updateChildEnding();
        if (updcount == 1) {
            return true;
        } else {
            Toast.makeText(this, "Updating Database... ERROR!", Toast.LENGTH_SHORT).show();
            return false;
        }

    }


    private boolean formValidation() {
        return Validator.emptyCheckingContainer(this, bi.fldGrpEnd);
    }


    @Override
    public void onBackPressed() {
        Toast.makeText(getApplicationContext(), "You Can't go back", Toast.LENGTH_LONG).show();
    }

}
