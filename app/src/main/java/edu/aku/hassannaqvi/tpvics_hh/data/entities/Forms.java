package edu.aku.hassannaqvi.tpvics_hh.data.entities;


import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;

import edu.aku.hassannaqvi.tpvics_hh.core.CONSTANTS;

@Entity(tableName = CONSTANTS.TABLE_FORMS)
public class Forms implements Serializable {

    @Ignore
    private final String _projectName = "HEALTH-FACILITY-AUDIT";
    @Ignore
    private final String round = "1";

    @ColumnInfo(name = "followupType")
    private String followupType = "";

    private String tehsilcode = "";

    @PrimaryKey(autoGenerate = true)
    private int id;

    @ColumnInfo
    private String formType = "";
    private String uid = "";
    private String formDate = ""; // Date
    private String username = ""; // Interviewer
    private String participantID = ""; // Child ID
    private String participantName = ""; // Child Name
    private String sa1 = "";     // Section 1 - Info
    private String sa2 = "";     // Section 2
    private String sa3 = "";     // Section 3
    private String sa4 = "";     // Section 4
    private String sa5 = "";     // Section 5
    private String sa6 = "";     // Section 6
    private String sa7 = "";     // Section 7
    private String sa8 = "";     // Section 8
    private String sa9 = "";     // Section 9
    private String istatus = "";
    private String endtime = "";
    private String districtcode = "";
    private String hfname = "";
    private String uccode = "";
    private String hfcode = "";
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
    @Ignore
    public Forms(Forms forms) {

        this.formType = forms.formType;
        this.followupType = forms.followupType;
        this.uid = forms.uid;
        this.formDate = forms.formDate;
        this.username = forms.username;
        this.participantID = forms.participantID;
        this.participantName = forms.participantName;
        this.hfcode = forms.hfcode;
        this.hfname = forms.hfname;
        this.sa1 = forms.sa1;
        this.sa2 = forms.sa2;
        this.sa3 = forms.sa3;
        this.sa4 = forms.sa4;
        this.sa5 = forms.sa5;
        this.sa6 = forms.sa6;
        this.sa7 = forms.sa7;
        this.sa8 = forms.sa8;
        this.sa9 = forms.sa9;
        this.istatus = forms.istatus;
        this.endtime = forms.endtime;
        this.districtcode = forms.districtcode;
        this.tehsilcode = forms.tehsilcode;
        this.uccode = forms.uccode;
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

    public String getFollowupType() {
        return followupType;
    }

    public void setFollowupType(String followupType) {
        this.followupType = followupType;
    }

    public Forms() {
    }

    @Ignore
    public JSONObject toJSONObject() throws JSONException {

        JSONObject json = new JSONObject();

        json.put("projectName", this._projectName);
        json.put("_id", this.id == 0 ? JSONObject.NULL : this.id);
        json.put("formType", this.formType == null ? JSONObject.NULL : this.formType);
        json.put("followup", this.followupType == null ? JSONObject.NULL : this.followupType);
        json.put("formDate", this.formDate == null ? JSONObject.NULL : this.formDate);
        json.put("uid", this.uid == null ? JSONObject.NULL : this.uid);
        json.put("username", this.username == null ? JSONObject.NULL : this.username);
//        json.put("participantID", this.participantID == null ? JSONObject.NULL : this.participantID);
//        json.put("participantName", this.participantName == null ? JSONObject.NULL : this.participantName);
        json.put("hfcode", this.hfcode == null ? JSONObject.NULL : this.hfcode);
        json.put("hfname", this.hfname == null ? JSONObject.NULL : this.hfname);
        json.put("districtcode", this.districtcode == null ? JSONObject.NULL : this.districtcode);
        json.put("tehsilcode", this.tehsilcode == null ? JSONObject.NULL : this.tehsilcode);
        json.put("endtime", this.endtime == null ? JSONObject.NULL : this.endtime);
        json.put("uccode", this.uccode == null ? JSONObject.NULL : this.uccode);
        json.put("gpsLat", this.gpsLat == null ? JSONObject.NULL : this.gpsLat);
        json.put("gpsLng", this.gpsLng == null ? JSONObject.NULL : this.gpsLng);
        json.put("gpsDT", this.gpsDT == null ? JSONObject.NULL : this.gpsDT);
        json.put("gpsAcc", this.gpsAcc == null ? JSONObject.NULL : this.gpsAcc);
        json.put("deviceID", this.deviceID == null ? JSONObject.NULL : this.deviceID);
        json.put("gpsElev", this.gpsElev == null ? JSONObject.NULL : this.gpsElev);
        json.put("devicetagID", this.devicetagID == null ? JSONObject.NULL : this.devicetagID);
        json.put("appversion", this.appversion == null ? JSONObject.NULL : this.appversion);
        json.put("istatus", this.istatus == null ? JSONObject.NULL : this.istatus);
        json.put("round", this.round);

        if (!this.sa1.equals("")) {
            json.put("sa1", this.sa1.equals("") ? JSONObject.NULL : new JSONObject(this.sa1));
        }
        if (!this.sa2.equals("")) {
            json.put("sa2", this.sa2.equals("") ? JSONObject.NULL : new JSONObject(this.sa2));
        }
        if (!this.sa3.equals("")) {
            json.put("sa3", this.sa3.equals("") ? JSONObject.NULL : new JSONObject(this.sa3));
        }
        if (!this.sa4.equals("")) {
            json.put("sa4", this.sa4.equals("") ? JSONObject.NULL : new JSONObject(this.sa4));
        }
        if (!this.sa5.equals("")) {
            json.put("sa5", this.sa5.equals("") ? JSONObject.NULL : new JSONObject(this.sa5));
        }
        if (!this.sa6.equals("")) {
            json.put("sa6", this.sa6.equals("") ? JSONObject.NULL : new JSONObject(this.sa6));
        }
        if (!this.sa7.equals("")) {
            json.put("sa7", this.sa7.equals("") ? JSONObject.NULL : new JSONObject(this.sa7));
        }
        if (!this.sa8.equals("")) {
            json.put("sa8", this.sa8.equals("") ? JSONObject.NULL : new JSONObject(this.sa8));
        }
        if (!this.sa9.equals("")) {
            json.put("sa9", this.sa9.equals("") ? JSONObject.NULL : new JSONObject(this.sa9));
        }


        return json;
    }

    public String getIstatus() {
        return istatus;
    }

    public void setIstatus(String istatus) {
        this.istatus = istatus;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFormType() {
        return formType;
    }

    public void setFormType(String formType) {
        this.formType = formType;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
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

    public String getParticipantID() {
        return participantID;
    }

    public void setParticipantID(String participantID) {
        this.participantID = participantID;
    }

    public String getParticipantName() {
        return participantName;
    }

    public void setParticipantName(String participantName) {
        this.participantName = participantName;
    }

    public String getHfcode() {
        return hfcode;
    }

    public void setHfcode(String hfcode) {
        this.hfcode = hfcode;
    }

    public String getHfname() {
        return hfname;
    }

    public void setHfname(String hfname) {
        this.hfname = hfname;
    }

    public String getSa1() {
        return sa1;
    }

    public void setSa1(String sa1) {
        this.sa1 = sa1;
    }

    public String getSa2() {
        return sa2;
    }

    public void setSa2(String sa2) {
        this.sa2 = sa2;
    }

    public String getSa3() {
        return sa3;
    }

    public void setSa3(String sa3) {
        this.sa3 = sa3;
    }

    public String getSa4() {
        return sa4;
    }

    public void setSa4(String sa4) {
        this.sa4 = sa4;
    }

    public String getSa5() {
        return sa5;
    }

    public void setSa5(String sa5) {
        this.sa5 = sa5;
    }

    public String getSa6() {
        return sa6;
    }

    public void setSa6(String sa6) {
        this.sa6 = sa6;
    }

    public String getSa7() {
        return sa7;
    }

    public void setSa7(String sa7) {
        this.sa7 = sa7;
    }

    public String getSa8() {
        return sa8;
    }

    public void setSa8(String sa8) {
        this.sa8 = sa8;
    }

    public String getSa9() {
        return sa9;
    }

    public void setSa9(String sa9) {
        this.sa9 = sa9;
    }

    public String getEndtime() {
        return endtime;
    }

    public void setEndtime(String endtime) {
        this.endtime = endtime;
    }

    public String getDistrictcode() {
        return districtcode;
    }

    public void setDistrictcode(String districtcode) {
        this.districtcode = districtcode;
    }

    public String getTehsilcode() {
        return tehsilcode;
    }

    public void setTehsilcode(String tehsilcode) {
        this.tehsilcode = tehsilcode;
    }

    public String getUccode() {
        return uccode;
    }

    public void setUccode(String uccode) {
        this.uccode = uccode;
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

}
