package edu.aku.hassannaqvi.tpvics_hh.ui.sections;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import edu.aku.hassannaqvi.tpvics_hh.R;
import edu.aku.hassannaqvi.tpvics_hh.utils.Util;

public class SectionCHCActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_section_ch_c);
    }

    public void BtnContinue() {

    }

    public void BtnEnd() {

        Util.openEndActivity(this);
    }

}
