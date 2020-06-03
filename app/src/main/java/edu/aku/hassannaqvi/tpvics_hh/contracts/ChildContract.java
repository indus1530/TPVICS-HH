package edu.aku.hassannaqvi.tpvics_hh.contracts;

import android.database.Cursor;
import android.provider.BaseColumns;

import org.json.JSONException;
import org.json.JSONObject;
import org.threeten.bp.LocalDate;

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
    private String childName;
    private String childSerial;
    private String gender;
    private String agey;
    private String agem;
    private String cluster;
    private String hhno;
    private String cstatus;
    private String cstatus88x;

    //Date Settings
    private LocalDate localDate = null, calculatedDOB = null;


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

    public LocalDate getLocalDate() {
        return localDate;
    }

    public void setLocalDate(LocalDate localDate) {
        this.localDate = localDate;
    }

    public LocalDate getCalculatedDOB() {
        return calculatedDOB;
    }

    public void setCalculatedDOB(LocalDate calculatedDOB) {
        this.calculatedDOB = calculatedDOB;
    }

    public ChildContract hydrate(Cursor cursor) {
        this._ID = cursor.getString(cursor.getColumnIndex(ChildTable.COLUMN_ID));
        this.UID = cursor.getString(cursor.getColumnIndex(ChildTable.COLUMN_UID));
        this._UUID = cursor.getString(cursor.getColumnIndex(ChildTable.COLUMN_UUID));
        this.deviceId = cursor.getString(cursor.getColumnIndex(ChildTable.COLUMN_DEVICEID));
        this.formDate = cursor.getString(cursor.getColumnIndex(ChildTable.COLUMN_FORMDATE));
        this.user = cursor.getString(cursor.getColumnIndex(ChildTable.COLUMN_USER));
        this.sCA = cursor.getString(cursor.getColumnIndex(ChildTable.COLUMN_SCA));
        this.sCB = cursor.getString(cursor.getColumnIndex(ChildTable.COLUMN_SCB));
        this.sCC = cursor.getString(cursor.getColumnIndex(ChildTable.COLUMN_SCC));
        this.devicetagID = cursor.getString(cursor.getColumnIndex(ChildTable.COLUMN_DEVICETAGID));

        this.childName = cursor.getString(cursor.getColumnIndex(ChildTable.COLUMN_CHILDNAME));
        this.childSerial = cursor.getString(cursor.getColumnIndex(ChildTable.COLUMN_CHILDSERIAL));
        this.gender = cursor.getString(cursor.getColumnIndex(ChildTable.COLUMN_GENDER));
        this.agey = cursor.getString(cursor.getColumnIndex(ChildTable.COLUMN_AGEY));
        this.agem = cursor.getString(cursor.getColumnIndex(ChildTable.COLUMN_AGEM));
        this.cluster = cursor.getString(cursor.getColumnIndex(ChildTable.COLUMN_CLUSTERCODE));
        this.hhno = cursor.getString(cursor.getColumnIndex(ChildTable.COLUMN_HHNO));
        this.cstatus = cursor.getString(cursor.getColumnIndex(ChildTable.COLUMN_CSTATUS));
        this.cstatus88x = cursor.getString(cursor.getColumnIndex(ChildTable.COLUMN_CSTATUS88x));

        return this;

    }

    public JSONObject toJSONObject() throws JSONException {

        JSONObject json = new JSONObject();
        json.put(ChildTable.COLUMN_ID, this._ID == null ? JSONObject.NULL : this._ID);
        json.put(ChildTable.COLUMN_UID, this.UID == null ? JSONObject.NULL : this.UID);
        json.put(ChildTable.COLUMN_UUID, this._UUID == null ? JSONObject.NULL : this._UUID);
        json.put(ChildTable.COLUMN_DEVICEID, this.deviceId == null ? JSONObject.NULL : this.deviceId);
        json.put(ChildTable.COLUMN_FORMDATE, this.formDate == null ? JSONObject.NULL : this.formDate);
        json.put(ChildTable.COLUMN_USER, this.user == null ? JSONObject.NULL : this.user);

        json.put(ChildTable.COLUMN_CHILDNAME, this.childName == null ? JSONObject.NULL : this.childName);
        json.put(ChildTable.COLUMN_CHILDSERIAL, this.childSerial == null ? JSONObject.NULL : this.childSerial);
        json.put(ChildTable.COLUMN_GENDER, this.gender == null ? JSONObject.NULL : this.gender);
        json.put(ChildTable.COLUMN_AGEY, this.agey == null ? JSONObject.NULL : this.agey);
        json.put(ChildTable.COLUMN_AGEM, this.agem == null ? JSONObject.NULL : this.agem);
        json.put(ChildTable.COLUMN_CLUSTERCODE, this.cluster == null ? JSONObject.NULL : this.cluster);
        json.put(ChildTable.COLUMN_HHNO, this.hhno == null ? JSONObject.NULL : this.hhno);
        json.put(ChildTable.COLUMN_CSTATUS, this.cstatus == null ? JSONObject.NULL : this.cstatus);
        json.put(ChildTable.COLUMN_CSTATUS88x, this.cstatus88x == null ? JSONObject.NULL : this.cstatus88x);

        if (this.sCA != null && !this.sCA.equals("")) {
            json.put(ChildTable.COLUMN_SCA, this.sCA.equals("") ? JSONObject.NULL : new JSONObject(this.sCA));
        }
        if (this.sCB != null && !this.sCB.equals("")) {
            json.put(ChildTable.COLUMN_SCB, this.sCB.equals("") ? JSONObject.NULL : new JSONObject(this.sCB));
        }
        if (this.sCC != null && !this.sCC.equals("")) {
            json.put(ChildTable.COLUMN_SCC, this.sCC.equals("") ? JSONObject.NULL : new JSONObject(this.sCC));
        }
        json.put(ChildTable.COLUMN_DEVICETAGID, this.devicetagID == null ? JSONObject.NULL : this.devicetagID);

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

    public String getgender() {
        return gender;
    }

    public void setgender(String gender) {
        this.gender = gender;
    }

    public String getagey() {
        return agey;
    }

    public void setagey(String agey) {
        this.agey = agey;
    }

    public String getagem() {
        return agem;
    }

    public void setagem(String agem) {
        this.agem = agem;
    }

    public String getcluster() {
        return cluster;
    }

    public void setcluster(String cluster) {
        this.cluster = cluster;
    }

    public String gethhno() {
        return hhno;
    }

    public void sethhno(String hhno) {
        this.hhno = hhno;
    }

    public String getCstatus() {
        return cstatus;
    }

    public void setCstatus(String cstatus) {
        this.cstatus = cstatus;
    }

    public String getCstatus88x() {
        return cstatus88x;
    }

    public void setCstatus88x(String cstatus88x) {
        this.cstatus88x = cstatus88x;
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

    public static abstract class ChildTable implements BaseColumns {

        public static final String TABLE_NAME = "child_table";
        public static final String COLUMN_ID = "_id";
        public static final String COLUMN_UID = "_uid";
        public static final String COLUMN_UUID = "_uuid";
        public static final String COLUMN_DEVICEID = "deviceid";
        public static final String COLUMN_FORMDATE = "formdate";
        public static final String COLUMN_USER = "username";
        public static final String COLUMN_SCA = "sca";
        public static final String COLUMN_SCB = "scb";
        public static final String COLUMN_SCC = "scc";
        public static final String COLUMN_DEVICETAGID = "tagid";
        public static final String COLUMN_SYNCED = "synced";
        public static final String COLUMN_SYNCED_DATE = "synced_date";
        public static final String COLUMN_CHILDSERIAL = "ec13";
        public static final String COLUMN_CHILDNAME = "ec14";
        public static final String COLUMN_GENDER = "ec15";
        public static final String COLUMN_AGEY = "agey";
        public static final String COLUMN_AGEM = "agem";
        public static final String COLUMN_CLUSTERCODE = "cluster_code";
        public static final String COLUMN_HHNO = "hhno";
        public static final String COLUMN_CSTATUS = "cstatus";
        public static final String COLUMN_CSTATUS88x = "cstatus88x";


    }


}
