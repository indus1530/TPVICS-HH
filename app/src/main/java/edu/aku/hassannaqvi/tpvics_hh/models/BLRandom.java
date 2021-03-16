package edu.aku.hassannaqvi.tpvics_hh.models;


import android.database.Cursor;
import android.provider.BaseColumns;

import org.json.JSONException;
import org.json.JSONObject;

public class BLRandom {

    private static final String TAG = "BLRandom_CONTRACT";

    private String _ID;
    private String LUID;
    private String ebCode; // hh05
    private String pCode; // hh02
    private String structure;  // Structure
    private String extension; // Extension
    private String hh;
    private String hhhead;
    private String randomDT;
    private String contact;
    private String selStructure;
    private String sno;
    private String tabno;

    private String rndType;
    private String assignHH;

    public String getEbcode() {
        return ebCode;
    }

    public void setEbcode(String ebcode) {
        this.ebCode = ebcode;
    }

    public String getTabno() {
        return tabno;
    }

    public void setTabno(String tabno) {
        this.tabno = tabno;
    }

    public BLRandom() {
    }

    public BLRandom Sync(JSONObject jsonObject) throws JSONException {
        this._ID = jsonObject.getString(BLRandomHHTable.COLUMN_ID);
        this.LUID = jsonObject.getString(BLRandomHHTable.COLUMN_LUID);
        this.pCode = jsonObject.getString(BLRandomHHTable.COLUMN_P_CODE);
        this.ebCode = jsonObject.getString(BLRandomHHTable.COLUMN_EB_CODE);
        this.structure = jsonObject.getString(BLRandomHHTable.COLUMN_STRUCTURE_NO);
        this.structure = String.format("%04d", Integer.valueOf(this.structure));

        this.extension = jsonObject.getString(BLRandomHHTable.COLUMN_FAMILY_EXT_CODE);
        this.extension = String.format("%03d", Integer.valueOf(this.extension));

        this.tabno = jsonObject.getString(BLRandomHHTable.COLUMN_TAB_NO);

        this.hh = tabno + "-" + structure + "-" + extension;
        this.randomDT = jsonObject.getString(BLRandomHHTable.COLUMN_RANDOMDT);
        this.hhhead = jsonObject.getString(BLRandomHHTable.COLUMN_HH_HEAD);
        this.contact = jsonObject.getString(BLRandomHHTable.COLUMN_CONTACT);
        this.selStructure = jsonObject.getString(BLRandomHHTable.COLUMN_HH_SELECTED_STRUCT);
        this.sno = jsonObject.getString(BLRandomHHTable.COLUMN_SNO_HH);

        return this;
    }

    public BLRandom hydrate(Cursor cursor) {
        this._ID = cursor.getString(cursor.getColumnIndex(BLRandomHHTable.COLUMN_ID));
        this.LUID = cursor.getString(cursor.getColumnIndex(BLRandomHHTable.COLUMN_LUID));
        this.pCode = cursor.getString(cursor.getColumnIndex(BLRandomHHTable.COLUMN_P_CODE));
        this.ebCode = cursor.getString(cursor.getColumnIndex(BLRandomHHTable.COLUMN_EB_CODE));
        this.structure = cursor.getString(cursor.getColumnIndex(BLRandomHHTable.COLUMN_STRUCTURE_NO));
        this.extension = cursor.getString(cursor.getColumnIndex(BLRandomHHTable.COLUMN_FAMILY_EXT_CODE));
        this.hh = cursor.getString(cursor.getColumnIndex(BLRandomHHTable.COLUMN_HH));
        this.randomDT = cursor.getString(cursor.getColumnIndex(BLRandomHHTable.COLUMN_RANDOMDT));
        this.hhhead = cursor.getString(cursor.getColumnIndex(BLRandomHHTable.COLUMN_HH_HEAD));
        this.contact = cursor.getString(cursor.getColumnIndex(BLRandomHHTable.COLUMN_CONTACT));
        this.selStructure = cursor.getString(cursor.getColumnIndex(BLRandomHHTable.COLUMN_HH_SELECTED_STRUCT));
        this.sno = cursor.getString(cursor.getColumnIndex(BLRandomHHTable.COLUMN_SNO_HH));

        return this;
    }

    public String get_ID() {
        return _ID;
    }

    public void set_ID(String _ID) {
        this._ID = _ID;
    }

    public String getLUID() {
        return LUID;
    }

    public void setLUID(String LUID) {
        this.LUID = LUID;
    }

    public String getpCode() {
        return pCode;
    }

    public void setpCode(String pCode) {
        this.pCode = pCode;
    }

    public String getStructure() {
        return structure;
    }

    public void setStructure(String structure) {
        this.structure = structure;
    }

    public String getExtension() {
        return extension;
    }

    public void setExtension(String extension) {
        this.extension = extension;
    }

    public String getHh() {
        return hh;
    }

    public void setHh(String hh) {
        this.hh = hh;
    }

    public String getRandomDT() {
        return randomDT;
    }

    public void setRandomDT(String randomDT) {
        this.randomDT = randomDT;
    }

    public String getHhhead() {
        return hhhead;
    }

    public void setHhhead(String hhhead) {
        this.hhhead = hhhead;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getSelStructure() {
        return selStructure;
    }

    public void setSelStructure(String selStructure) {
        this.selStructure = selStructure;
    }

    public String getAssignHH() {
        return assignHH;
    }

    public void setAssignHH(String assignHH) {
        this.assignHH = assignHH;
    }

    public String getRndType() {
        return rndType;
    }

    public void setRndType(String rndType) {
        this.rndType = rndType;
    }

    public String getSno() {
        return sno;
    }

    public void setSno(String sno) {
        this.sno = sno;
    }

    public static abstract class BLRandomHHTable implements BaseColumns {

        public static final String TABLE_NAME = "bl_randomised";
        public static final String COLUMN_ID = "_id";
        public static final String COLUMN_RANDOMDT = "randDT";
        public static final String COLUMN_LUID = "UID";
        public static final String COLUMN_P_CODE = "hh02";
        public static final String COLUMN_EB_CODE = "hh05";
        public static final String COLUMN_STRUCTURE_NO = "hh03";
        public static final String COLUMN_FAMILY_EXT_CODE = "hh07";
        public static final String COLUMN_HH = "hh";
        public static final String COLUMN_HH_HEAD = "hh08";
        public static final String COLUMN_CONTACT = "hh09";
        public static final String COLUMN_HH_SELECTED_STRUCT = "hhss";
        public static final String COLUMN_SNO_HH = "sno";
        public static final String COLUMN_TAB_NO = "tabNo";
        public static String COLUMN_DIST_CODE = "dist_id";

        public static String _URI = "bl_random.php";
    }

}