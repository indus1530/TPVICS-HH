package edu.aku.ramshasaeed.mnch.activities;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import edu.aku.ramshasaeed.mnch.R;
import edu.aku.ramshasaeed.mnch.core.MainApp;
import edu.aku.ramshasaeed.mnch.databinding.RsdMainBinding;

import static edu.aku.ramshasaeed.mnch.activities.RSDInfoActivity.fc;

public class RsdMain extends AppCompatActivity {
    RsdMainBinding bi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        bi = DataBindingUtil.setContentView(this, R.layout.rsd_main);
        bi.setCallback(this);
        this.setTitle(getString(R.string.routineone) + "(" + fc.getReportingMonth() + ")");


        /*for (String item : MainApp.FORM_SUB_TYPE) {
            if (item.contains("f1")) {
                bi.form01.setEnabled(false);
            }
            if (item.contains("f2")) {
                bi.form02.setEnabled(false);
            }
            if (item.contains("f3")) {
                bi.form03.setEnabled(false);
            }
            if (item.contains("f4")) {
                bi.form04.setEnabled(false);
            }
            if (item.contains("f5")) {
                bi.form05.setEnabled(false);
            }
            if (item.contains("f6")) {
                bi.form06.setEnabled(false);
            }
        }*/

        if (!fc.getSA().equals(""))
            bi.form01.setEnabled(false);

        if (!fc.getSB().equals(""))
            bi.form02.setEnabled(false);

        if (!fc.getSC().equals(""))
            bi.form03.setEnabled(false);

        if (!fc.getSD().equals(""))
            bi.form04.setEnabled(false);

        if (!fc.getSE().equals(""))
            bi.form05.setEnabled(false);

        if (!fc.getSF().equals(""))
            bi.form06.setEnabled(false);

    }


    public void openForm(View v) {
        OpenFormFunc(v.getId());
    }

    public void BtnContinue() {

        if (!bi.form01.isEnabled()
                && !bi.form02.isEnabled()
                && !bi.form03.isEnabled()
                && !bi.form04.isEnabled()
                && !bi.form05.isEnabled()
                && !bi.form06.isEnabled()) {
            //startActivity(new Intent(this, EndingActivity.class).putExtra("complete", true));
            MainApp.endActivity(this, this, EndingActivity.class, true, RSDInfoActivity.fc);
        } else {
            Toast.makeText(this, "Sections still in Pending!", Toast.LENGTH_SHORT).show();
        }
    }


    public void BtnEnd() {
        if (bi.form01.isEnabled()
                || bi.form02.isEnabled()
                || bi.form03.isEnabled()
                || bi.form04.isEnabled()
                || bi.form05.isEnabled()
                || bi.form06.isEnabled()) {
            //startActivity(new Intent(this, EndingActivity.class).putExtra("complete", false));
            MainApp.endActivity(this, this, EndingActivity.class, false, RSDInfoActivity.fc);
        } else {
            Toast.makeText(this, "ALL SECTIONS FILLED \n Good to GO GREEN!", Toast.LENGTH_SHORT).show();
        }

    }

    private void OpenFormFunc(int id) {
        Intent oF = new Intent();
        if (!MainApp.userName.equals("0000")) {
            switch (id) {
                case R.id.form01:
                    oF = new Intent(RsdMain.this, Rsd01.class);
                    break;
                case R.id.form02:
                    oF = new Intent(RsdMain.this, Rsd02.class);
                    break;
                case R.id.form03:
                    oF = new Intent(RsdMain.this, Rsd03.class);
                    break;
                case R.id.form04:
                    oF = new Intent(RsdMain.this, Rsd04.class);
                    break;
                case R.id.form05:
                    oF = new Intent(RsdMain.this, Rsd05.class);
                    break;
                case R.id.form06:
                    oF = new Intent(RsdMain.this, Rsd06.class);
                    break;
            }
            startActivity(oF);
        } else {
            Toast.makeText(getApplicationContext(), "Please login Again!", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onBackPressed() {
        Toast.makeText(this, "You can't go back", Toast.LENGTH_SHORT).show();
    }

}
