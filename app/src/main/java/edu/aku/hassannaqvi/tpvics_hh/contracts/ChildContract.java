package edu.aku.hassannaqvi.tpvics_hh.contracts;

import android.database.Cursor;
import android.provider.BaseColumns;

import org.json.JSONException;
import org.json.JSONObject;

public class ChildContract {


    private String _ID = "";
    private String UID = "";
    private String _UUID = "";
    private String deviceId = "";
    private String formDate = ""; // Date
    private String user = ""; // Interviewer
    private String sCA = "";
    private String sCB = "";
    private String sCC = "";
    private String devicetagID = "";
    private String synced = "";
    private String synced_date = "";

    //Temporary
    private String childName;
    private String childSerial;

     /*
    saved in JSON
    =============
    * hhno
    * cluster
    * i1_fm_uid
    * i1_fm_serial
    * i1_res_fm_uid
    * i1_res_fm_serial
    * i2_fm_uid
    * i2_fm_serial
    * i2_res_fm_uid
    * i2_res_fm_serial
    * j_fm_uid
    * j_fm_serial
    * j_res_fm_uid
    * j_res_fm_serial
    * */


    public ChildContract hydrate(Cursor cursor) {
        this._ID = cursor.getString(cursor.getColumnIndex(SingleChild.COLUMN__ID));
        this.UID = cursor.getString(cursor.getColumnIndex(SingleChild.COLUMN_UID));
        this._UUID = cursor.getString(cursor.getColumnIndex(SingleChild.COLUMN__UUID));
        this.deviceId = cursor.getString(cursor.getColumnIndex(SingleChild.COLUMN_DEVICEID));
        this.formDate = cursor.getString(cursor.getColumnIndex(SingleChild.COLUMN_FORMDATE));
        this.user = cursor.getString(cursor.getColumnIndex(SingleChild.COLUMN_USER));
        this.sCA = cursor.getString(cursor.getColumnIndex(SingleChild.COLUMN_SCA));
        this.sCB = cursor.getString(cursor.getColumnIndex(SingleChild.COLUMN_SCB));
        this.sCC = cursor.getString(cursor.getColumnIndex(SingleChild.COLUMN_SCC));
        this.devicetagID = cursor.getString(cursor.getColumnIndex(SingleChild.COLUMN_DEVICETAGID));

        return this;

    }

    public JSONObject toJSONObject() throws JSONException {

        JSONObject json = new JSONObject();
        json.put(SingleChild.COLUMN__ID, this._ID == null ? JSONObject.NULL : this._ID);
        json.put(SingleChild.COLUMN_UID, this.UID == null ? JSONObject.NULL : this.UID);
        json.put(SingleChild.COLUMN__UUID, this._UUID == null ? JSONObject.NULL : this._UUID);
        json.put(SingleChild.COLUMN_DEVICEID, this.deviceId == null ? JSONObject.NULL : this.deviceId);
        json.put(SingleChild.COLUMN_FORMDATE, this.formDate == null ? JSONObject.NULL : this.formDate);
        json.put(SingleChild.COLUMN_USER, this.user == null ? JSONObject.NULL : this.user);

        if (this.sCA != null && !this.sCA.equals("")) {
            json.put(SingleChild.COLUMN_SCA, this.sCA.equals("") ? JSONObject.NULL : new JSONObject(this.sCA));
        }
        if (this.sCB != null && !this.sCB.equals("")) {
            json.put(SingleChild.COLUMN_SCB, this.sCB.equals("") ? JSONObject.NULL : new JSONObject(this.sCB));
        }
        if (this.sCC != null && !this.sCC.equals("")) {
            json.put(SingleChild.COLUMN_SCC, this.sCC.equals("") ? JSONObject.NULL : new JSONObject(this.sCC));
        }
        json.put(SingleChild.COLUMN_DEVICETAGID, this.devicetagID == null ? JSONObject.NULL : this.devicetagID);

        return json;

    }

    public String getChildName() {
        return childName;
    }

    public void setChildName(String childName) {
        this.childName = childName;
    }

    public String getChildSerial() {
        return childSerial;
    }

    public void setChildSerial(String childSerial) {
        this.childSerial = childSerial;
    }

    public String get_ID() {
        return _ID;
    }

    public void set_ID(String _ID) {
        this._ID = _ID;
    }

    public String getUID() {
        return UID;
    }

    public void setUID(String UID) {
        this.UID = UID;
    }

    public String get_UUID() {
        return _UUID;
    }

    public void set_UUID(String _UUID) {
        this._UUID = _UUID;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public String getFormDate() {
        return formDate;
    }

    public void setFormDate(String formDate) {
        this.formDate = formDate;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getsCA() {
        return sCA;
    }

    public void setsCA(String sCA) {
        this.sCA = sCA;
    }

    public String getsCB() {
        return sCB;
    }

    public void setsCB(String sCB) {
        this.sCB = sCB;
    }

    public String getsCC() {
        return sCC;
    }

    public void setsCC(String sCC) {
        this.sCC = sCC;
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

    public static abstract class SingleChild implements BaseColumns {

        public static final String TABLE_NAME = "child_table";
        public static final String COLUMN__ID = "_id";
        public static final String COLUMN_UID = "_uid";
        public static final String COLUMN__UUID = "_uuid";
        public static final String COLUMN_DEVICEID = "deviceid";
        public static final String COLUMN_FORMDATE = "formdate";
        public static final String COLUMN_USER = "username";
        public static final String COLUMN_SCA = "sca";
        public static final String COLUMN_SCB = "scb";
        public static final String COLUMN_SCC = "scc";
        public static final String COLUMN_DEVICETAGID = "tagid";
        public static final String COLUMN_SYNCED = "synced";
        public static final String COLUMN_SYNCED_DATE = "synced_date";


    }


}
