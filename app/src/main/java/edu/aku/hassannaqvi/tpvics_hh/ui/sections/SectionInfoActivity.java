package edu.aku.hassannaqvi.tpvics_hh.ui.sections;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
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

import java.text.SimpleDateFormat;
import java.util.Date;

import edu.aku.hassannaqvi.tpvics_hh.R;
import edu.aku.hassannaqvi.tpvics_hh.contracts.BLRandomContract;
import edu.aku.hassannaqvi.tpvics_hh.contracts.EnumBlockContract;
import edu.aku.hassannaqvi.tpvics_hh.contracts.FormsContract;
import edu.aku.hassannaqvi.tpvics_hh.core.DatabaseHelper;
import edu.aku.hassannaqvi.tpvics_hh.core.MainApp;
import edu.aku.hassannaqvi.tpvics_hh.databinding.ActivitySectionInfoBinding;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.functions.Function;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class SectionInfoActivity extends AppCompatActivity {

    ActivitySectionInfoBinding bi;
    private DatabaseHelper db;
    private BLRandomContract bl;
    private String message = "Household found";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        bi = DataBindingUtil.setContentView(this, R.layout.activity_section_info);
        bi.setCallback(this);
        db = MainApp.appInfo.getDbHelper();
        setUIComponent();
        setTitle(R.string.hhsec);
    }

    public void hh11TextChanged(CharSequence s, int start, int before, int count) {
        bi.fldGrpSectionA01.setVisibility(View.GONE);
        bi.fldGrpSectionA02.setVisibility(View.GONE);
        Clear.clearAllFields(bi.fldGrpSectionA01);
    }

    private void setUIComponent() {

        bi.hh12.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                //      Toast.makeText(SectionHHActivity.this, charSequence+" i="+i+" i1="+i1+" i2="+i2, Toast.LENGTH_LONG).show();

                if (i == 1 && i1 == 0 && i2 == 1) {
                    bi.hh12.setText(bi.hh12.getText().toString() + "-");
                }
                if (i == 6 && i1 == 0 && i2 == 1) {
                    bi.hh12.setText(bi.hh12.getText().toString() + "-");
                }
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                if (i == 0 && i1 == 0 && i2 == 1) {
                    bi.hh12.setText(bi.hh12.getText().toString() + "-");
                }
                if (i == 2 && i1 == 1 && i2 == 0) {
                    bi.hh12.setText(bi.hh12.getText().toString().substring(0, 1));
                }
                if (i == 1 && i1 == 4 && i2 == 5) {
                    bi.hh12.setText(bi.hh12.getText().toString() + "-");
                }
                if (i == 7 && i1 == 1 && i2 == 0) {
                    bi.hh12.setText(bi.hh12.getText().toString().substring(0, 6));
                }

            }

            @Override
            public void afterTextChanged(Editable editable) {
                bi.hh12.setSelection(bi.hh12.getText().toString().length());
            }
        });

    }

    public void BtnContinue() {
        if (formValidation()) {
            try {
                SaveDraft();
            } catch (Exception e) {
                e.printStackTrace();
            }
            finish();
            startActivity(new Intent(SectionInfoActivity.this, SectionSubInfoActivity.class));
        }
    }

    private void SaveDraft() throws JSONException {
        if (MainApp.fc != null) return;
        MainApp.fc = new FormsContract();
        MainApp.fc.setFormDate(new SimpleDateFormat("dd-MM-yy HH:mm").format(new Date().getTime()));
        MainApp.fc.setUser(MainApp.userName);
        MainApp.fc.setDeviceID(MainApp.appInfo.getDeviceID());
        MainApp.fc.setDevicetagID(MainApp.appInfo.getTagName());
        MainApp.fc.setAppversion(MainApp.appInfo.getAppVersion());
        MainApp.fc.setClusterCode(bi.hh11.getText().toString());
        MainApp.fc.setHhno(bi.hh12.getText().toString());
        MainApp.fc.setLuid(bl.getLUID());
        MainApp.setGPS(this); // Set GPS

        JSONObject json = new JSONObject();

        json.put("imei", MainApp.IMEI);
        json.put("rndid", bl.get_ID());
        json.put("luid", bl.getLUID());
        json.put("randDT", bl.getRandomDT());
        json.put("hh06", bl.getStructure());
        json.put("hh11", bl.getExtension());
        json.put("hhhead", bl.getHhhead());
        json.put("hh063", bl.getContact());
        json.put("hhss", bl.getSelStructure());


        json.put("hh11", bi.hh11.getText().toString());
        json.put("geoarea", bi.hh09txt.getText().toString() + ", " + bi.geoarea.getText().toString());
  /*      json.put("hh07", bi.hh07.getText().toString());
        json.put("hh08", bi.hh08.getText().toString());
        json.put("hh09", bi.hh09.getText().toString());*/
        json.put("hh11", bi.hh11.getText().toString());
        json.put("hh12", bi.hh12.getText().toString());
        json.put("hh063", MainApp.userName);
        //json.put("hh074", bi.hh074.getText().toString());
        MainApp.fc.setsInfo(String.valueOf(json));
    }

    private boolean formValidation() {
        return Validator.emptyCheckingContainer(this, bi.fldGrpSectionA);
    }

    public void BtnCheckCluster() {

        if (!Validator.emptyTextBox(this, bi.hh11)) return;
        boolean loginFlag;
        if (bi.hh11.getText().toString().length() != 6) {
            Toast.makeText(this, "Invalid Cluster length!!", Toast.LENGTH_SHORT).show();
            return;
        }
        /*int cluster = Integer.parseInt(bi.hh11.getText().toString().substring(3, 6));
        if (cluster < 500) {
            loginFlag = !(MainApp.userName.equals("test1234") || MainApp.userName.equals("dmu@aku") || MainApp.userName.substring(0, 4).equals("user"));
        } else {
            loginFlag = MainApp.userName.equals("test1234") || MainApp.userName.equals("dmu@aku") || MainApp.userName.substring(0, 4).equals("user");
        }
        if (!loginFlag) {
            Toast.makeText(this, "Can't proceed test cluster for current user!!", Toast.LENGTH_SHORT).show();
            return;
        }*/

        //Check HH Exist or not
        getEnumerationBlock()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(enumBlockContract -> {
                    String selected = enumBlockContract.getGeoarea();
                    if (!selected.equals("")) {

                        String[] selSplit = selected.split("\\|");

                        bi.fldGrpSectionA01.setVisibility(View.VISIBLE);
                             /*       bi.hh06.setText(selSplit[0]);
                                    bi.hh07.setText(selSplit[1].equals("") ? "----" : selSplit[1]);
                                    bi.hh08.setText(selSplit[2].equals("") ? "----" : selSplit[2]);
                                    bi.hh09.setText(selSplit[3]);*/
                        bi.hh09txt.setText(selSplit[3]);
                        bi.geoarea.setText(new StringBuilder(selSplit[2]).append(", ").append(selSplit[1]).append(", ").append(selSplit[0]));
                    }
                }, error -> {
                    Toast.makeText(this, "Sorry cluster not found!!", Toast.LENGTH_SHORT).show();
                    bi.hh09txt.setText("Village");
                    bi.geoarea.setText(new StringBuilder("Tehsil").append(", ").append("District").append(", ").append("Province"));
                });
    }

    private Observable<EnumBlockContract> getEnumerationBlock() {
        return Observable.create(emitter -> {
            emitter.onNext(db.getEnumBlock(bi.hh11.getText().toString()));
            emitter.onComplete();
        });
    }

    private Observable<BLRandomContract> getBLRandomBlock() {
        return Observable.create(emitter -> {
            emitter.onNext(db.getHHFromBLRandom(bi.hh11.getText().toString(), bi.hh12.getText().toString().toUpperCase()));
            emitter.onComplete();
        });
    }

    private Observable<FormsContract> getFilledForm() {
        return Observable.create(emitter -> {
            emitter.onNext(db.getFilledForm(bi.hh11.getText().toString(), bi.hh12.getText().toString().toUpperCase()));
            emitter.onComplete();
        });
    }

    public void BtnCheckHH() {
        if (!Validator.emptyTextBox(this, bi.hh11)) return;
        bl = null;
        MainApp.fc = null;
        getBLRandomBlock()
                .flatMap((Function<BLRandomContract, Observable<FormsContract>>)
                        blRandomContract -> {
                            bl = blRandomContract;
                            return getFilledForm();
                        })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .filter(form -> {
                    if (!form.getIstatus().equals("")) {
                        message = "Household already exist";
                        return false;
                    } else return true;
                })
                .subscribe(form -> {
                    MainApp.fc = form;
                    blRandomExist(bl, message);
                }, error -> {
                    blRandomExist(bl, message);
                });

    }

    private void blRandomExist(BLRandomContract bl, String message) {
        if (bl != null) {
            bi.hh12msg.setText(message);
            bi.hh12name.setText(bl.getHhhead().toUpperCase());
            bi.fldGrpSectionA02.setVisibility(View.VISIBLE);
        } else {
            bi.hh12msg.setText("Household not found!!");
            bi.hh12name.setText(null);
            bi.fldGrpSectionA02.setVisibility(View.GONE);
            Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
        }
    }

    public void showTooltip(@NotNull View view) {
        if (view.getId() != View.NO_ID) {
            String package_name = getApplicationContext().getPackageName();

            // Question Number Textview ID must be prefixed with q_ e.g.: 'q_aa12a'
            String infoid = view.getResources().getResourceName(view.getId()).replace(package_name + ":id/q_", "");

            // Question info text must be suffixed with _info e.g.: aa12a_info
            int stringRes = this.getResources().getIdentifier(infoid + "_info", "string", getApplicationContext().getPackageName());

            // Fetch info text from strings.xml
            //String infoText = (String) getResources().getText(stringRes);

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
