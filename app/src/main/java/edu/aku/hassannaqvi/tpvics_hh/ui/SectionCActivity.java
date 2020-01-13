package edu.aku.hassannaqvi.tpvics_hh.ui;

import android.os.Bundle;
import android.widget.RadioGroup;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import edu.aku.hassannaqvi.tpvics_hh.R;
import edu.aku.hassannaqvi.tpvics_hh.databinding.ActivitySectionCBinding;
import edu.aku.hassannaqvi.tpvics_hh.validation.ClearClass;
import edu.aku.hassannaqvi.tpvics_hh.validation.ValidatorClass;

public class SectionCActivity extends AppCompatActivity {

    private ActivitySectionCBinding bi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        bi = DataBindingUtil.setContentView(this, R.layout.activity_section_c);
        bi.setCallback(this);

        setListeners();
    }

    private void setListeners() {

        bi.hs04.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {

                if (bi.hs04b.isChecked()) {
                    ClearClass.ClearAllFields(bi.fldGrpCVhs05, null);
                }
            }
        });

        bi.hs07.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {

                if (!bi.hs07h.isChecked() || !bi.hs07h.isChecked()) {
                    ClearClass.ClearAllFields(bi.fldGrpCVhs08, null);
                }
            }
        });

        bi.hs09.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {

                if (bi.hs09b.isChecked()) {
                    ClearClass.ClearAllFields(bi.fldGrpCVhs0x, null);
                }
            }
        });

        bi.hs11.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {

                if (bi.hs11b.isChecked()) {
                    ClearClass.ClearAllFields(bi.fldGrpCVhs12, null);
                }
            }
        });

        bi.hs22.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {

                if (bi.hs22b.isChecked()) {
                    ClearClass.ClearAllFields(bi.fldGrpCVhs23, null);
                }
            }
        });

        bi.hs24.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {

                if (bi.hs24b.isChecked()) {
                    ClearClass.ClearAllFields(bi.fldGrpCVhs25, null);
                }
            }
        });
    }

    private boolean formValidation() {
        return ValidatorClass.EmptyCheckingContainer(this, bi.fldGrpSecC);
    }
}
