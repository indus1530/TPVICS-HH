package edu.aku.hassannaqvi.tpvics_hh.ui.other;

import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.validatorcrawler.aliazaz.Validator;

import edu.aku.hassannaqvi.tpvics_hh.R;
import edu.aku.hassannaqvi.tpvics_hh.databinding.ActivityChildEndingBinding;

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
            bi.istatusa.setEnabled(false);
            bi.istatusb.setEnabled(true);
            bi.istatusc.setEnabled(true);
            bi.istatusd.setEnabled(true);
            bi.istatuse.setEnabled(true);
            bi.istatusf.setEnabled(true);
            bi.istatus96.setEnabled(true);
        }

//
    }

    public void BtnEnd() {
        if (formValidation()) {
            SaveDraft();
            if (UpdateDB()) {
                finish();
            } else {
                Toast.makeText(this, "Error in updating db!!", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void SaveDraft() {

        /*MainApp.fc.setIstatus(bi.istatusa.isChecked() ? "1"
                : bi.istatusb.isChecked() ? "2"
                : bi.istatusc.isChecked() ? "3"
                : bi.istatusd.isChecked() ? "4"
                : bi.istatuse.isChecked() ? "5"
                : bi.istatusf.isChecked() ? "6"
                : bi.istatus96.isChecked() ? "96"
                : "0");

        MainApp.fc.setIstatus88x(bi.istatus96x.getText().toString());
        MainApp.fc.setEndingdatetime(new SimpleDateFormat("dd-MM-yy HH:mm").format(new Date().getTime()));*/
    }

    public boolean UpdateDB() {

        /*DatabaseHelper db = MainApp.appInfo.getDbHelper();
        int updcount = db.updateEnding();
        if (updcount == 1) {
            return true;
        } else {
            Toast.makeText(this, "Updating Database... ERROR!", Toast.LENGTH_SHORT).show();
            return false;
        }*/
        return true;

    }


    private boolean formValidation() {
        return Validator.emptyCheckingContainer(this, bi.fldGrpEnd);
    }


    @Override
    public void onBackPressed() {
        Toast.makeText(getApplicationContext(), "You Can't go back", Toast.LENGTH_LONG).show();
    }

}
