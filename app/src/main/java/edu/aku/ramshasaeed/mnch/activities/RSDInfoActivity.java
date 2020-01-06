package edu.aku.ramshasaeed.mnch.activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.RadioGroup;
import android.widget.Toast;

import org.json.JSONException;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

import edu.aku.ramshasaeed.mnch.R;
import edu.aku.ramshasaeed.mnch.RMOperations.crudOperations;
import edu.aku.ramshasaeed.mnch.core.MainApp;
import edu.aku.ramshasaeed.mnch.data.DAO.FormsDAO;
import edu.aku.ramshasaeed.mnch.data.DAO.GetFncDAO;
import edu.aku.ramshasaeed.mnch.data.entities.District;
import edu.aku.ramshasaeed.mnch.data.entities.FacilityProvider;
import edu.aku.ramshasaeed.mnch.data.entities.Forms;
import edu.aku.ramshasaeed.mnch.data.entities.Tehsil;
import edu.aku.ramshasaeed.mnch.databinding.ActivityRsdinfoBinding;
import edu.aku.ramshasaeed.mnch.get.db.GetAllDBData;
import edu.aku.ramshasaeed.mnch.get.db.GetIndDBData;
import edu.aku.ramshasaeed.mnch.utils.DateUtils;
import edu.aku.ramshasaeed.mnch.validation.ClearClass;
import edu.aku.ramshasaeed.mnch.validation.validatorClass;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;
import static edu.aku.ramshasaeed.mnch.activities.LoginActivity.db;

public class RSDInfoActivity extends AppCompatActivity {
    private ActivityRsdinfoBinding bi;
    private String mon = new SimpleDateFormat("MMM-yy").format(new Date().getTime());
    private List<String> reportingMonth, districtNames, districtCodes, tehsilName, tehsilCode, UcNames, ucCode, hfName, hfCode;
    private Map<String, FacilityProvider> facilityMap;
    private Map<String, String> tehsilMap;
    private Map<String, String> UcMap;
    private Map<String, String> FacilityProvider;
    public static Forms fc;
    private static final String TAG = RSDInfoActivity.class.getName();
    private String hf_type;
    private String type;

    Forms getForms;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        bi = DataBindingUtil.setContentView(this, R.layout.activity_rsdinfo);
        bi.setCallback(this);

        type = getIntent().getStringExtra(MainApp.FORM_TYPE);
//        this.setTitle(type.equals(MainApp.RSD) ? "ROUTINE SERVICE DELIVERY" : type.equals(MainApp.DHMT) ? "DHMT" : type.equals(MainApp.QOC) ? "QUALITY OF CARE" : "");
        this.setTitle(type.equals(MainApp.RSD) ? "DHIS Data-Validation Tools for Decision Making"
                : type.equals(MainApp.DHMT) ? "Performance Evaluation of District Team Meetings"
                : type.equals(MainApp.QOC) ? "Key Quality Indicator Tool for Health Facility" : "");

        MainApp.FORM_SUB_TYPE.clear();
        fc = null;

        tempVisible(this);

        bi.rGpp.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {

                if (checkedId == bi.pub.getId()) {
                    hf_type = "1";
                    ClearClass.ClearAllFields(bi.llrsdInfo01, null);
                    ClearClass.ClearAllFields(bi.llrsdInfo02, null);
                    ClearClass.ClearAllFields(bi.llrsdInfo04, null);
                    bi.llrsdInfo01.setVisibility(VISIBLE);
                    bi.llrsdInfo02.setVisibility(VISIBLE);
                    bi.llrsdInfo04.setVisibility(VISIBLE);
                } else if (checkedId == bi.pvt.getId()) {
                    hf_type = "2";
                    ClearClass.ClearAllFields(bi.llrsdInfo01, null);
                    ClearClass.ClearAllFields(bi.llrsdInfo02, null);
                    ClearClass.ClearAllFields(bi.llrsdInfo04, null);
                    bi.llrsdInfo01.setVisibility(VISIBLE);
                    bi.llrsdInfo02.setVisibility(VISIBLE);
                    bi.llrsdInfo04.setVisibility(VISIBLE);
                }
            }
        });

        if (type.equals(MainApp.DHMT)) {
            ClearClass.ClearAllFields(bi.llrsdInfo02, null);
            ClearClass.ClearAllFields(bi.llrsdInfo03, null);
            ClearClass.ClearAllFields(bi.llrsdInfo04, null);
            bi.llrsdInfo01.setVisibility(VISIBLE);
            bi.llrsdInfo02.setVisibility(GONE);
            bi.llrsdInfo03.setVisibility(GONE);
            bi.llrsdInfo04.setVisibility(GONE);
        }


        bi.hfMtime.setTimeFormat("HH:mm");
        bi.hfMtime.setIs24HourView(true);

    }

    private void tempVisible(final Context context) {

        districtNames = new ArrayList<>();
        districtCodes = new ArrayList<>();

        districtNames.add("....");
        districtCodes.add("....");
        Collection<District> districts;
        try {
            districts = (Collection<District>) new GetAllDBData(db, GetFncDAO.class.getName(), "getFncDao", "getAllDistricts").execute().get();

            if (districts != null) {
                for (District d : districts) {
                    districtNames.add(d.getDistrict_name());
                    districtCodes.add(d.getDistrict_code());
                }
                // Creating adapter for spinner
                ArrayAdapter<String> dataAdapter = new ArrayAdapter<>(context,
                        android.R.layout.simple_spinner_dropdown_item, districtNames);

                // Drop down layout style - list view with radio button
                dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

                // attaching data adapter to spinner
                bi.hfDistrict.setAdapter(dataAdapter);

            } else {
                Toast.makeText(this, "District not found!!", Toast.LENGTH_SHORT).show();
            }

        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }


        if (!type.equals(MainApp.DHMT)) {
            bi.hfDistrict.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    if (position == 0) return;

                    tehsilName = new ArrayList<>();
                    tehsilCode = new ArrayList<>();
                    tehsilName.add("....");
                    tehsilCode.add("....");

                    Collection<Tehsil> tehsils;
                    try {
                        tehsils =
                                (Collection<Tehsil>)
                                        new GetAllDBData(db, GetFncDAO.class.getName(), "getFncDao", "getTehsil")
                                                .execute(districtCodes.get(position)).get();

                        if (tehsils.size() != 0) {
                            for (Tehsil fp : tehsils) {
                                tehsilName.add(fp.getTehsil_name());
                                tehsilCode.add(fp.getTehsil_code());
                            }
                        }

                        bi.hfTehsil.setAdapter(new ArrayAdapter<>(context,
                                android.R.layout.simple_spinner_dropdown_item, tehsilName));

                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    } catch (ExecutionException e) {
                        e.printStackTrace();
                    }

                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });

            bi.hfTehsil.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    if (position == 0) return;

                    hfName = new ArrayList<>();
                    hfCode = new ArrayList<>();
                    hfCode.add("....");
                    hfName.add("....");

                    Collection<FacilityProvider> hfp;
                    try {
                        hfp =
                                /*(Collection<FacilityProvider>)
                                        new GetAllDBData(db, GetFncDAO.class.getName(), "getFncDao", "getFacilityProvider")
                                                .execute(tehsilName.get(position)).get();*/


                                (Collection<FacilityProvider>)
                                        new GetAllDBData(db, GetFncDAO.class.getName(), "getFncDao", "getFacilityProvider")
                                                .execute("%" + tehsilCode.get(position) + "%", hf_type).get();

                        if (hfp.size() != 0) {
                            for (FacilityProvider fp : hfp) {
                                hfName.add(fp.getHf_name());
                                hfCode.add(fp.getHf_uen_code());

                            }
                        }

                        bi.hfname.setAdapter(new ArrayAdapter<>(context,
                                android.R.layout.simple_spinner_dropdown_item, hfName));

                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    } catch (ExecutionException e) {
                        e.printStackTrace();
                    }

                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });

        }

        reportingMonth = new ArrayList<>();
        reportingMonth.add("....");
        reportingMonth.add(mon.toUpperCase());
        reportingMonth.add(DateUtils.getMonthsBack("MMM-yy", -1).toUpperCase());
        reportingMonth.add(DateUtils.getMonthsBack("MMM-yy", -2).toUpperCase());
        // Creating adapter for spinner
        ArrayAdapter<String> monAdapter = new ArrayAdapter<>(context,
                android.R.layout.simple_spinner_dropdown_item, reportingMonth);
        // Drop down layout style - list view with radio button
        monAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // attaching data adapter to spinner
        bi.reportMonth.setAdapter(monAdapter);
        bi.reportMonth.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                setTitle(type.equals(MainApp.RSD) ? "DHIS Data-Validation Tools for Decision Making (" + bi.reportMonth.getSelectedItem() + ")"
                        : type.equals(MainApp.DHMT) ? "Performance Evaluation of District Team Meetings (" + bi.reportMonth.getSelectedItem() + ")"
                        : type.equals(MainApp.QOC) ? "Key Quality Indicator Tool for Health Facility (" + bi.reportMonth.getSelectedItem() + ")" : " ");

                ClearClass.ClearAllFields(bi.llrsdInfo01, null);
                ClearClass.ClearAllFields(bi.llrsdInfo02, null);
                ClearClass.ClearAllFields(bi.llrsdInfo03, null);
                ClearClass.ClearAllFields(bi.llrsdInfo04, null);

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


    }


    public void BtnContinue() {

        if (formValidation()) {

            if (fc != null) {
                finish();
                startActivity(new Intent(RSDInfoActivity.this, type.equals(MainApp.QOC) ? Qoc1.class : type.equals(MainApp.DHMT) ? DHMT_MonitoringActivity.class : RsdMain.class));
                return;
            }

            try {
                SaveDraft();
            } catch (JSONException e) {
                e.printStackTrace();
            }
            if (UpdateDB()) {
                finish();
                startActivity(new Intent(RSDInfoActivity.this, type.equals(MainApp.QOC) ? Qoc1.class : type.equals(MainApp.DHMT) ? DHMT_MonitoringActivity.class : RsdMain.class));

            } else {
                Toast.makeText(this, "Failed to Update Database!", Toast.LENGTH_SHORT).show();
            }
        }
    }

    public void BtnEnd() {
        MainApp.endActivity(this, this, EndingActivity.class, false, RSDInfoActivity.fc);
    }

    public boolean formValidation() {
        if (!validatorClass.EmptyCheckingContainer(this, bi.llrsdInfo))
            return false;

        if (type.equals(MainApp.RSD)) {
            Object getData = null;

            try {
                getData = new GetIndDBData(db, GetFncDAO.class.getName(), "getFncDao", "getPendingFormo")
                        .execute(bi.reportMonth.getSelectedItem().toString(),
                                hfCode.get(bi.hfname.getSelectedItemPosition())).get();
            } catch (ExecutionException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            /*if (bi.pub.isChecked()) {
                try {
                    getData = new GetIndDBData(db, GetFncDAO.class.getName(), "getFncDao", "getPendingPublicForm")
                            .execute(bi.reportMonth.getSelectedItem().toString(),
                                    districtCodes.get(bi.hfDistrict.getSelectedItemPosition()),
                                    hfCode.get(bi.hfname.getSelectedItemPosition())).get();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            } else {
                try {
                    getData = new GetIndDBData(db, GetFncDAO.class.getName(), "getFncDao", "getPendingPrivateForm")
                            .execute(bi.reportMonth.getSelectedItem().toString(),
                                    districtCodes.get(bi.hfDistrict.getSelectedItemPosition()),
                                    hfCode.get(bi.hfname.getSelectedItemPosition())).get();
                    //bi.hfName.getText().toString()).get();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }*/

            if (getData == null) {
                fc = null;
                return true;
            }

            if (((Forms) getData).getIstatus().equals("1")) {
                Toast.makeText(this, "Facility already filled for this Month", Toast.LENGTH_LONG).show();
                return false;
            }

            fc = (Forms) getData;

        }

        return true;

    }

    private void SaveDraft() throws JSONException {

        fc = new Forms();
        fc.setDevicetagID(MainApp.getTagName(this));
        fc.setFormType(type);
        fc.setAppversion(MainApp.versionName + "." + MainApp.versionCode);
        fc.setUsername(MainApp.userName);
        fc.setFormDate(new SimpleDateFormat("dd-MM-yyyy HH:mm").format(new Date().getTime()));
        fc.setDeviceID(MainApp.deviceId);
        fc.setReportingMonth(bi.reportMonth.getSelectedItem().toString());
        fc.setDistrictCode(districtCodes.get(bi.hfDistrict.getSelectedItemPosition()));

        if (!type.equals(MainApp.DHMT)) {
            fc.setTehsilCode(tehsilCode.get(bi.hfTehsil.getSelectedItemPosition()));
            fc.setFacilityType(bi.pub.isChecked() ? "1" : bi.pvt.isChecked() ? "2" : "0");
            fc.setFacilityCode(hfCode.get(bi.hfname.getSelectedItemPosition()));
            fc.setFacilityName(bi.hfname.getSelectedItem().toString());
        }

        setGPS(fc); // Set GPS
    }

    public void setGPS(Forms fc) {
        SharedPreferences GPSPref = getSharedPreferences("GPSCoordinates", Context.MODE_PRIVATE);
        try {
            String lat = GPSPref.getString("Latitude", "0");
            String lang = GPSPref.getString("Longitude", "0");
            String acc = GPSPref.getString("Accuracy", "0");
            String elevation = GPSPref.getString("Elevation", "0");

            if (lat == "0" && lang == "0") {
                Toast.makeText(this, "Could not obtained GPS points", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "GPS set", Toast.LENGTH_SHORT).show();
            }

            String date = DateFormat.format("dd-MM-yyyy HH:mm", Long.parseLong(GPSPref.getString("Time", "0"))).toString();

            fc.setGpsLat(lat);
            fc.setGpsLng(lang);
            fc.setGpsAcc(acc);
            fc.setGpsDT(date); // Timestamp is converted to date above
            fc.setGpsElev(elevation);

            Toast.makeText(this, "GPS set", Toast.LENGTH_SHORT).show();

        } catch (Exception e) {
            Log.e(TAG, "setGPS: " + e.getMessage());
        }

    }

    private boolean UpdateDB() {

        try {

            Long longID = new crudOperations(db, fc).execute(FormsDAO.class.getName(), "formsDao", "insertForm").get();

            if (longID != 0) {
                fc.setId(longID.intValue());
                fc.setUid(MainApp.deviceId + fc.getId());
                longID = new crudOperations(db, fc).execute(FormsDAO.class.getName(), "formsDao", "updateForm").get();
                return longID == 1;

            } else {
                return false;
            }

        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        return false;

    }

}
