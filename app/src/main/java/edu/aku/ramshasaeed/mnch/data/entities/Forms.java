package edu.aku.ramshasaeed.mnch.data.entities;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;

import edu.aku.ramshasaeed.mnch.data.AppDatabase;

@Entity(tableName = AppDatabase.Sub_DBConnection.TABLE_FORMS)
public class Forms implements Serializable {

    @Ignore
    private final String _projectName = "mnch";
    @Ignore
    private final String _surveyType = "";

    @PrimaryKey(autoGenerate = true)
    private int id;

    @ColumnInfo
    private String uid = "";
    private String username = ""; // Interviewer
    private String formType = "";
    private String formDate = ""; // Date
    private String reportingMonth = "";

    @ColumnInfo(name = "district_code")
    private String districtCode = "";

    @ColumnInfo(name = "tehsil_code")
    private String tehsilCode = "";

    @ColumnInfo(name = "hf_type")
    private String facilityType = "";

    @ColumnInfo(name = "hf_code")
    private String facilityCode = "";

    @ColumnInfo(name = "hf_name")
    private String facilityName = "";

    private String sA = "";
    private String sB = "";
    private String sC = "";
    private String sD = "";
    private String sE = "";
    private String sF = "";
    private String sG = "";
    private String starttime = "";
    private String endtime = "";
    private String gpsLat = "";
    private String gpsLng = "";
    private String gpsDT = "";
    private String gpsAcc = "";
    private String gpsElev = "";
    private String deviceID = "";
    private String devicetagID = "";
    private String synced = "";
    private String synced_date = "";
    private String appversion = "";
    private String istatus = ""; // Interview Status
    private String istatus96x = ""; // Interview Status


    /*    private String district = "";
        private String tehsil = "";
        private String ucs = "";
        private String uen = ""; */


    @Ignore
    public Forms(Forms forms) {

        this.uid = forms.uid;
        this.formDate = forms.formDate;
        this.formType = forms.formType;
        this.reportingMonth = forms.reportingMonth;
        this.districtCode = forms.districtCode;
        this.tehsilCode = forms.tehsilCode;
        this.facilityType = forms.facilityType;
        this.facilityCode = forms.facilityCode;
        this.facilityName = forms.facilityName;
        this.username = forms.username;
        this.istatus = forms.istatus;
        this.istatus96x = forms.istatus96x;
        this.sA = forms.sA;
        this.sB = forms.sB;
        this.sC = forms.sC;
        this.sD = forms.sD;
        this.sE = forms.sE;
        this.sF = forms.sF;
        this.sG = forms.sG;
        this.starttime = forms.starttime;
        this.endtime = forms.endtime;
        this.gpsLat = forms.gpsLat;
        this.gpsLng = forms.gpsLng;
        this.gpsDT = forms.gpsDT;
        this.gpsAcc = forms.gpsAcc;
        this.gpsElev = forms.gpsElev;
        this.deviceID = forms.deviceID;
        this.devicetagID = forms.devicetagID;
        this.synced = forms.synced;
        this.synced_date = forms.synced_date;
        this.appversion = forms.appversion;

    }

    public Forms() {
    }

    public String getReportingMonth() {
        return reportingMonth;
    }

    public void setReportingMonth(String reportingMonth) {
        this.reportingMonth = reportingMonth;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getFormType() {
        return formType;
    }

    public void setFormType(String formType) {
        this.formType = formType;
    }

    public String getDistrictCode() {
        return districtCode;
    }

    public void setDistrictCode(String districtCode) {
        this.districtCode = districtCode;
    }

    public String getTehsilCode() {
        return tehsilCode;
    }

    public void setTehsilCode(String tehsilCode) {
        this.tehsilCode = tehsilCode;
    }

    public String getFacilityType() {
        return facilityType;
    }

    public void setFacilityType(String facilityType) {
        this.facilityType = facilityType;
    }

    public String getFacilityCode() {
        return facilityCode;
    }

    public void setFacilityCode(String facilityCode) {
        this.facilityCode = facilityCode;
    }

    public String getFacilityName() {
        return facilityName;
    }

    public void setFacilityName(String facilityName) {
        this.facilityName = facilityName;
    }

    public String getFormDate() {
        return formDate;
    }

    public void setFormDate(String formDate) {
        this.formDate = formDate;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getIstatus() {
        return istatus;
    }

    public void setIstatus(String istatus) {
        this.istatus = istatus;
    }

    public String getIstatus96x() {
        return istatus96x;
    }

    public void setIstatus96x(String istatus96x) {
        this.istatus96x = istatus96x;
    }

    public String getSA() {
        return sA;
    }

    public void setSA(String sA) {
        this.sA = sA;
    }

    public String getSB() {
        return sB;
    }

    public void setSB(String sB) {
        this.sB = sB;
    }

    public String getSC() {
        return sC;
    }

    public void setSC(String sC) {
        this.sC = sC;
    }

    public String getSD() {
        return sD;
    }

    public void setSD(String sD) {
        this.sD = sD;
    }

    public String getSE() {
        return sE;
    }

    public void setSE(String sE) {
        this.sE = sE;
    }

    public String getSF() {
        return sF;
    }

    public void setSF(String sF) {
        this.sF = sF;
    }

    public String getSG() {
        return sG;
    }

    public void setSG(String sG) {
        this.sG = sG;
    }

    public String getStarttime() {
        return starttime;
    }

    public void setStarttime(String starttime) {
        this.starttime = starttime;
    }

    public String getEndtime() {
        return endtime;
    }

    public void setEndtime(String endtime) {
        this.endtime = endtime;
    }

    public String getGpsLat() {
        return gpsLat;
    }

    public void setGpsLat(String gpsLat) {
        this.gpsLat = gpsLat;
    }

    public String getGpsLng() {
        return gpsLng;
    }

    public void setGpsLng(String gpsLng) {
        this.gpsLng = gpsLng;
    }

    public String getGpsDT() {
        return gpsDT;
    }

    public void setGpsDT(String gpsDT) {
        this.gpsDT = gpsDT;
    }

    public String getGpsAcc() {
        return gpsAcc;
    }

    public void setGpsAcc(String gpsAcc) {
        this.gpsAcc = gpsAcc;
    }

    public String getGpsElev() {
        return gpsElev;
    }

    public void setGpsElev(String gpsElev) {
        this.gpsElev = gpsElev;
    }

    public String getDeviceID() {
        return deviceID;
    }

    public void setDeviceID(String deviceID) {
        this.deviceID = deviceID;
    }

    public String getDevicetagID() {
        return devicetagID;
    }

    public void setDevicetagID(String devicetagID) {
        this.devicetagID = devicetagID;
    }

    public String getSynced() {
        return synced;
    }

    public void setSynced(String synced) {
        this.synced = synced;
    }

    public String getSynced_date() {
        return synced_date;
    }

    public void setSynced_date(String synced_date) {
        this.synced_date = synced_date;
    }

    public String getAppversion() {
        return appversion;
    }

    public void setAppversion(String appversion) {
        this.appversion = appversion;
    }

    @Ignore
    public JSONObject toJSONObject() throws JSONException {

        JSONObject json = new JSONObject();

        json.put("projectName", this._projectName);
        json.put("_id", this.id == 0 ? JSONObject.NULL : this.id);
        json.put("uid", this.uid == null ? JSONObject.NULL : this.uid);
        json.put("formType", this.formType == null ? JSONObject.NULL : this.formType);
        json.put("formDate", this.formDate == null ? JSONObject.NULL : this.formDate);
        json.put("reportingMonth", this.reportingMonth == null ? JSONObject.NULL : this.reportingMonth);
        json.put("district_code", this.districtCode == null ? JSONObject.NULL : this.districtCode);
        json.put("tehsil_code", this.tehsilCode == null ? JSONObject.NULL : this.tehsilCode);
        json.put("hf_type", this.facilityType == null ? JSONObject.NULL : this.facilityType);
        json.put("hf_code", this.facilityCode == null ? JSONObject.NULL : this.facilityCode);
        json.put("hf_name", this.facilityName == null ? JSONObject.NULL : this.facilityName);
        json.put("username", this.username == null ? JSONObject.NULL : this.username);
        json.put("istatus", this.istatus == null ? JSONObject.NULL : this.istatus);
        json.put("istatus96x", this.istatus96x == null ? JSONObject.NULL : this.istatus96x);
        json.put("starttime", this.starttime == null ? JSONObject.NULL : this.starttime);
        json.put("endtime", this.endtime == null ? JSONObject.NULL : this.endtime);
        json.put("gpsLat", this.gpsLat == null ? JSONObject.NULL : this.gpsLat);
        json.put("gpsLng", this.gpsLng == null ? JSONObject.NULL : this.gpsLng);
        json.put("gpsDT", this.gpsDT == null ? JSONObject.NULL : this.gpsDT);
        json.put("gpsAcc", this.gpsAcc == null ? JSONObject.NULL : this.gpsAcc);
        json.put("gpsElev", this.gpsElev == null ? JSONObject.NULL : this.gpsElev);
        json.put("deviceID", this.deviceID == null ? JSONObject.NULL : this.deviceID);
        json.put("devicetagID", this.devicetagID == null ? JSONObject.NULL : this.devicetagID);
        json.put("appversion", this.appversion == null ? JSONObject.NULL : this.appversion);

        if (!this.sA.equals("")) {
            json.put("sA", this.sA.equals("") ? JSONObject.NULL : new JSONObject(this.sA));
        }
        if (!this.sB.equals("")) {
            json.put("sB", this.sB.equals("") ? JSONObject.NULL : new JSONObject(this.sB));
        }
        if (!this.sC.equals("")) {
            json.put("sC", this.sC.equals("") ? JSONObject.NULL : new JSONObject(this.sC));
        }
        if (!this.sD.equals("")) {
            json.put("sD", this.sD.equals("") ? JSONObject.NULL : new JSONObject(this.sD));
        }
        if (!this.sE.equals("")) {
            json.put("sE", this.sE.equals("") ? JSONObject.NULL : new JSONObject(this.sE));
        }
        if (!this.sF.equals("")) {
            json.put("sF", this.sF.equals("") ? JSONObject.NULL : new JSONObject(this.sF));
        }
        if (!this.sG.equals("")) {
            json.put("sG", this.sG.equals("") ? JSONObject.NULL : new JSONObject(this.sG));
        }

        return json;
    }


}
