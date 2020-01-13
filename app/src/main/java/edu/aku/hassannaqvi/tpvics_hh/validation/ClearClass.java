package edu.aku.hassannaqvi.tpvics_hh.validation;

import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import androidx.cardview.widget.CardView;

/**
 * Created by ali.azaz on 03/19/18.
 */

public class ClearClass {

    public static void ClearRadioButton(LinearLayout container, RadioGroup rdGrp) {
        if (rdGrp.getCheckedRadioButtonId() == -1) {

            rdGrp.clearCheck();

            for (int i = 0; i < container.getChildCount(); i++) {
                View v = container.getChildAt(i);
                if (v instanceof RadioButton) {
                    v.setEnabled(false);
                }
            }
        }
    }

    public static void ClearRadioButton(LinearLayout container, RadioGroup rdGrp, EditText othertxt) {
        if (rdGrp.getCheckedRadioButtonId() == -1) {

            rdGrp.clearCheck();
            othertxt.setText(null);

            for (int i = 0; i < container.getChildCount(); i++) {
                View v = container.getChildAt(i);
                if (v instanceof RadioButton) {
                    v.setEnabled(false);
                }
            }
        }
    }

    public static void ClearCheckBoxes(LinearLayout container) {
        for (int i = 0; i < container.getChildCount(); i++) {
            View v = container.getChildAt(i);
            if (v instanceof CheckBox) {
                ((CheckBox) v).setChecked(false);
                v.setEnabled(false);
            }
        }
    }

    public static void ClearCheckBoxes(LinearLayout container, EditText othertxt) {

        othertxt.setText(null);

        for (int i = 0; i < container.getChildCount(); i++) {
            View v = container.getChildAt(i);
            if (v instanceof CheckBox) {
                ((CheckBox) v).setChecked(false);
                v.setEnabled(false);
            }
        }
    }

    public static void ClearAllCardFields(LinearLayout container) {
        for (int i = 0; i < container.getChildCount(); i++) {
            View v = container.getChildAt(i);
            if (v instanceof CheckBox) {
                ((CheckBox) v).setChecked(false);
                ((CheckBox) v).setError(null);
            } else if (v instanceof RadioGroup) {
                ((RadioGroup) v).clearCheck();

            } else if (v instanceof EditText) {
                ((EditText) v).setText(null);
                ((EditText) v).setError(null);
                v.clearFocus();

            } else if (v instanceof CardView) {
                ClearAllCardFields((CardView) v);
            } else if (v instanceof LinearLayout) {
                ClearAllCardFields((LinearLayout) v);
            }
        }
    }

    public static void ClearAllCardFields(CardView container) {
        for (int i = 0; i < container.getChildCount(); i++) {
            View v = container.getChildAt(i);
            if (v instanceof CheckBox) {
                ((CheckBox) v).setChecked(false);
                ((CheckBox) v).setError(null);
            } else if (v instanceof RadioGroup) {
                ((RadioGroup) v).clearCheck();
            } else if (v instanceof EditText) {
                ((EditText) v).setText(null);
                ((EditText) v).setError(null);
                v.clearFocus();

            } else if (v instanceof LinearLayout) {
                for (int k = 0; k < ((LinearLayout) v).getChildCount(); k++) {
                    View v1 = ((LinearLayout) v).getChildAt(k);
                    if (v1 instanceof CheckBox) {
                        ((CheckBox) v1).setChecked(false);
                        ((CheckBox) v1).setError(null);
                    } else if (v1 instanceof RadioGroup) {
                        ((RadioGroup) v1).clearCheck();
                    } else if (v1 instanceof EditText) {
                        ((EditText) v1).setText(null);
                        ((EditText) v1).setError(null);
                        v1.clearFocus();
                    }
                }
            }
        }
    }

    public static void ClearAllFields(View container, Boolean flag) {
        for (int i = 0; i < ((ViewGroup) container).getChildCount(); i++) {
            View v = ((ViewGroup) container).getChildAt(i);
            if (v instanceof CheckBox) {
                ((CheckBox) v).setChecked(false);
                ((CheckBox) v).setError(null);
                if (flag != null)
                    v.setEnabled(flag);

            } else if (v instanceof RadioGroup) {
                ((RadioGroup) v).clearCheck();
                if (flag != null) {
                    for (int j = 0; j < ((RadioGroup) v).getChildCount(); j++) {
                        ((RadioGroup) v).getChildAt(j).setEnabled(flag);
                    }
                }

            } else if (v instanceof EditText) {
                ((EditText) v).setText(null);
                ((EditText) v).setError(null);
                v.clearFocus();

                if (flag != null)
                    v.setEnabled(flag);

            } else if (v instanceof RadioButton) {
                if (flag != null)
                    v.setEnabled(flag);
            } else if (v instanceof CardView) {
                ClearAllFields(v, flag);
            } else if (v instanceof LinearLayout) {
                ClearAllFields(v, flag);
            }

        }
    }

}
