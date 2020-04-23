package edu.aku.hassannaqvi.tpvics_hh.utils;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;

import edu.aku.hassannaqvi.tpvics_hh.R;


public class SkipQuestions {

    public void disableQuestions(Context c, LinearLayout layout) {

        for (int i = 0; i < layout.getChildCount(); i++) {
            View child = layout.getChildAt(i);


            if (child instanceof TextView) {
                Drawable bg = child.getBackground();
                if (bg instanceof ColorDrawable) {
                    child.setBackgroundColor(c.getResources().getColor(R.color.gray));

                }
            } else if (child instanceof RadioButton || child instanceof CheckBox) {
                child.setEnabled(false);
                ((CompoundButton) child).setChecked(false);
            } else if (child instanceof EditText) {
                child.setEnabled(false);

                ((EditText) child).setHintTextColor(c.getResources().getColor(R.color.gray));
                ((EditText) child).setText(null);
            } else if (child instanceof LinearLayout) {
                disableQuestions(c, (LinearLayout) child);
            }
        }
    }

    public void enableQuestions(Context c, LinearLayout layout) {

        for (int i = 0; i < layout.getChildCount(); i++) {
            View child = layout.getChildAt(i);


            if (child instanceof RadioButton || child instanceof CheckBox) {
                child.setEnabled(true);
                ((CompoundButton) child).setChecked(false);
            } else if (child instanceof EditText) {
                child.setEnabled(false);
                ((EditText) child).setHintTextColor(c.getResources().getColor(R.color.gray));
                ((EditText) child).setText(null);
            } else if (child instanceof LinearLayout) {
                enableQuestions(c, (LinearLayout) child);
            }
        }
    }
}
