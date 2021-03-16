package edu.aku.hassannaqvi.tpvics_hh.models;


import android.database.Cursor;
import android.provider.BaseColumns;

import org.json.JSONException;
import org.json.JSONObject;

public class Districts {

    private static final String TAG = "District_CONTRACT";
    String dist_id;
    String district;
    String province;

    public Districts() {
        // Default Constructor
    }

    public Districts Sync(JSONObject jsonObject) throws JSONException {
        this.dist_id = jsonObject.getString(DistrictTable.COLUMN_DIST_ID);
        this.district = jsonObject.getString(DistrictTable.COLUMN_DIST_NAME);
        this.province = jsonObject.getString(DistrictTable.COLUMN_PROVINCE_NAME);
        return this;
    }

    public Districts Hydrate(Cursor cursor) {
        this.dist_id = cursor.getString(cursor.getColumnIndex(DistrictTable.COLUMN_DIST_ID));
        this.district = cursor.getString(cursor.getColumnIndex(DistrictTable.COLUMN_DIST_NAME));
        this.province = cursor.getString(cursor.getColumnIndex(DistrictTable.COLUMN_PROVINCE_NAME));
        return this;
    }

    public String getDist_id() {
        return dist_id;
    }

    public void setDist_id(String dist_id) {
        this.dist_id = dist_id;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public JSONObject toJSONObject() throws JSONException {

        JSONObject json = new JSONObject();
        json.put(DistrictTable.COLUMN_DIST_ID, this.dist_id == null ? JSONObject.NULL : this.dist_id);
        json.put(DistrictTable.COLUMN_DIST_NAME, this.district == null ? JSONObject.NULL : this.district);
        json.put(DistrictTable.COLUMN_PROVINCE_NAME, this.province == null ? JSONObject.NULL : this.province);
        return json;
    }


    public static abstract class DistrictTable implements BaseColumns {

        public static final String TABLE_NAME = "districts";
        public static final String COLUMN_DIST_ID = "dist_id";
        public static final String COLUMN_DIST_NAME = "district";
        public static final String COLUMN_PROVINCE_NAME = "province";
        public static final String _URI = "districts.php";
    }
}