package edu.aku.hassannaqvi.tpvics_hh.JSON;

import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;

import androidx.cardview.widget.CardView;

import org.json.JSONException;
import org.json.JSONObject;

import edu.aku.hassannaqvi.tpvics_hh.validation.ValidatorClass;
import io.blackbox_vision.datetimepickeredittext.view.DatePickerInputEditText;

public abstract class GeneratorClass {

    private static JSONObject formJSON;

    public static JSONObject getContainerJSON(View lv, boolean flag, String... convention) {

        if (flag)
            formJSON = new JSONObject();

        try {

            for (int i = 0; i < ((ViewGroup) lv).getChildCount(); i++) {
                View view = ((ViewGroup) lv).getChildAt(i);

                String assig_id = convention.length > 0 ? convention[0] : "";

                if (view instanceof RadioGroup) {

                    RadioGroup rdp = (RadioGroup) view;
                    assig_id += ValidatorClass.getIDComponent(rdp);
                    int rdbID = rdp.getCheckedRadioButtonId();

                    if (rdbID != -1) {

                        for (byte j = 0; j < ((RadioGroup) view).getChildCount(); j++) {

                            if (rdbID == ((RadioGroup) view).getChildAt(j).getId()) {

                                RadioButton rdb = rdp.findViewById(((RadioGroup) view).getChildAt(j).getId());

                                formJSON.put(assig_id, getValues(ValidatorClass.getIDComponent(rdb)));


                                for (byte k = 0; k < ((RadioGroup) view).getChildCount(); k++) {

                                    if (((RadioGroup) view).getChildAt(k).getTag() == null)
                                        continue;

                                    if (((RadioGroup) view).getChildAt(k).getTag().equals(ValidatorClass.getIDComponent(rdb))) {

                                        assig_id = ValidatorClass.getIDComponent(((RadioGroup) view).getChildAt(k));
                                        formJSON.put(assig_id, ((EditText) ((RadioGroup) view).getChildAt(k)).getText().toString());

                                        break;
                                    }

                                }

                                break;
                            }

                        }
                    } else {
                        formJSON.put(assig_id, "0");
                    }
                } else if (view instanceof DatePickerInputEditText) {
                    assig_id += ValidatorClass.getIDComponent(view);
                    formJSON.put(assig_id, ((DatePickerInputEditText) view).getText().toString());
                } else if (view instanceof EditText) {
                    assig_id += ValidatorClass.getIDComponent(view);
                    formJSON.put(assig_id, ((EditText) view).getText().toString());
                } else if (view instanceof CheckBox) {
                    assig_id += ValidatorClass.getIDComponent(view);
                    if (((CheckBox) view).isChecked()) {
                        formJSON.put(assig_id, getValues(assig_id));
                    } else {
                        formJSON.put(assig_id, "0");
                    }
                } else if (view instanceof Spinner) {
                    assig_id += ValidatorClass.getIDComponent(view);
                    if (((Spinner) view).getSelectedItemPosition() != 0) {
                        formJSON.put(assig_id, ((Spinner) view).getSelectedItem());
                    } else {
                        formJSON.put(assig_id, "");
                    }
                } else if (view instanceof CardView) {
                    getContainerJSON(view, false, assig_id);
                } else if (view instanceof LinearLayout) {
                    getContainerJSON(view, false, assig_id);
                }

            }
        } catch (JSONException e) {
            e.printStackTrace();
        }


        return formJSON;
    }

    private static String getValues(String nameconv) {

        if (nameconv.length() > 0) {

            String str = nameconv.substring(nameconv.length() - (nameconv.length() >= 2 ? 2 : 1)
            );

            char[] str_str = str.toLowerCase().toCharArray();

            if (str.charAt(str.length() - 1) <= '9') {
                return String.valueOf(Integer.parseInt(str));
            } else {

                String ascii_letters = "abcdefghijklmnopqrstuvwxyz";

                for (byte i = 0; i < ascii_letters.length(); i++) {
                    if (str_str[str.length() - 1] == ascii_letters.charAt(i)) {
                        return String.valueOf(i + 1);
                    }
                }

                return "0";

            }
        } else {
            return "";
        }
    }

}
