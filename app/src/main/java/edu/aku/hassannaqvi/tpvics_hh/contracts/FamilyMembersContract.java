package edu.aku.hassannaqvi.tpvics_hh.contracts;

import android.database.Cursor;
import android.provider.BaseColumns;

import org.json.JSONException;
import org.json.JSONObject;

public class FamilyMembersContract {
    private String _id;
    private String uid;
    private String uuid;
    private String formdate;
    private String clusterno;
    private String hhno;

    private String serialno;
    private String name;
    private String age;
    private String monthfm;
    private String father_name;
    private String mother_name;
    private String gender;
    private String sD;

    public FamilyMembersContract() {
    }

    public FamilyMembersContract hydrate(Cursor cursor) {
        this._id = cursor.getString(cursor.getColumnIndex(SingleMember.COLUMN_ID));
        this.uid = cursor.getString(cursor.getColumnIndex(SingleMember.COLUMN_UID));
        this.uuid = cursor.getString(cursor.getColumnIndex(SingleMember.COLUMN_UUID));
        this.formdate = cursor.getString(cursor.getColumnIndex(SingleMember.COLUMN_FORMDATE));
        this.clusterno = cursor.getString(cursor.getColumnIndex(SingleMember.COLUMN_CLUSTERNO));
        this.hhno = cursor.getString(cursor.getColumnIndex(SingleMember.COLUMN_HHNO));
        this.serialno = cursor.getString(cursor.getColumnIndex(SingleMember.COLUMN_SERIAL_NO));
        this.name = cursor.getString(cursor.getColumnIndex(SingleMember.COLUMN_NAME));
        this.father_name = cursor.getString(cursor.getColumnIndex(SingleMember.COLUMN_FATHER_NAME));
        this.age = cursor.getString(cursor.getColumnIndex(SingleMember.COLUMN_AGE));
        this.monthfm = cursor.getString(cursor.getColumnIndex(SingleMember.COLUMN_MONTH_FM));
        this.mother_name = cursor.getString(cursor.getColumnIndex(SingleMember.COLUMN_MOTHER_NAME));
        this.gender = cursor.getString(cursor.getColumnIndex(SingleMember.COLUMN_GENDER));
        this.sD = cursor.getString(cursor.getColumnIndex(SingleMember.COLUMN_SD));

        return this;
    }

    public JSONObject toJSONObject() throws JSONException {

        JSONObject json = new JSONObject();
        json.put(SingleMember.COLUMN_ID, this._id == null ? JSONObject.NULL : this._id);
        json.put(SingleMember.COLUMN_UID, this.uid == null ? JSONObject.NULL : this.uid);
        json.put(SingleMember.COLUMN_UUID, this.uuid == null ? JSONObject.NULL : this.uuid);
        json.put(SingleMember.COLUMN_FORMDATE, this.formdate == null ? JSONObject.NULL : this.formdate);
        json.put(SingleMember.COLUMN_CLUSTERNO, this.clusterno == null ? JSONObject.NULL : this.clusterno);
        json.put(SingleMember.COLUMN_HHNO, this.hhno == null ? JSONObject.NULL : this.hhno);
        json.put(SingleMember.COLUMN_SERIAL_NO, this.serialno == null ? JSONObject.NULL : this.serialno);
        json.put(SingleMember.COLUMN_NAME, this.name == null ? JSONObject.NULL : this.name);
        json.put(SingleMember.COLUMN_FATHER_NAME, this.father_name == null ? JSONObject.NULL : this.father_name);
        json.put(SingleMember.COLUMN_AGE, this.age == null ? JSONObject.NULL : this.age);
        json.put(SingleMember.COLUMN_MONTH_FM, this.monthfm == null ? JSONObject.NULL : this.monthfm);
        json.put(SingleMember.COLUMN_MOTHER_NAME, this.mother_name == null ? JSONObject.NULL : this.mother_name);
        json.put(SingleMember.COLUMN_GENDER, this.gender == null ? JSONObject.NULL : this.gender);

        if (!this.sD.equals("")) {
            json.put(SingleMember.COLUMN_SD, new JSONObject(this.sD));
        }

        return json;
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getFormdate() {
        return formdate;
    }

    public void setFormdate(String formdate) {
        this.formdate = formdate;
    }

    public String getClusterno() {
        return clusterno;
    }

    public void setClusterno(String clusterno) {
        this.clusterno = clusterno;
    }

    public String getHhno() {
        return hhno;
    }

    public void setHhno(String hhno) {
        this.hhno = hhno;
    }

    public String getSerialno() {
        return serialno;
    }

    public void setSerialno(String serialno) {
        this.serialno = serialno;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFatherName() {
        return father_name;
    }

    public void setFather_name(String father_name) {
        this.father_name = father_name;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getMonthfm() {
        return monthfm;
    }

    public void setMonthfm(String monthfm) {
        this.monthfm = monthfm;
    }

    public String getMotherName() {
        return mother_name;
    }

    public void setMother_name(String mother_name) {
        this.mother_name = mother_name;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getsD() {
        return sD;
    }

    public void setsD(String sD) {
        this.sD = sD;
    }

    public static abstract class SingleMember implements BaseColumns {

        public static final String TABLE_NAME = "childrenroaster";
        public static final String COLUMN_ID = "_id";
        public static final String COLUMN_UID = "_uid";
        public static final String COLUMN_UUID = "_uuid";
        public static final String COLUMN_FORMDATE = "formdate";
        public static final String COLUMN_AGE = "age";
        public static final String COLUMN_MONTH_FM = "monthfm";
        public static final String COLUMN_CLUSTERNO = "clusterno";
        public static final String COLUMN_HHNO = "hhno";
        public static final String COLUMN_FATHER_NAME = "father_name";
        public static final String COLUMN_NAME = "name";
        public static final String COLUMN_SERIAL_NO = "serial_no";
        public static final String COLUMN_MOTHER_NAME = "mother_name";
        public static final String COLUMN_GENDER = "gender";
        public static final String COLUMN_SD = "sD";
        public static final String COLUMN_SYNCED = "synced";
        public static final String COLUMN_SYNCED_DATE = "synced_date";

    }
}
