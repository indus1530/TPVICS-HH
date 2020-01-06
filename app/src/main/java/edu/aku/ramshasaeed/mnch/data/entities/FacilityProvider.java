package edu.aku.ramshasaeed.mnch.data.entities;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

import org.json.JSONException;
import org.json.JSONObject;

import edu.aku.ramshasaeed.mnch.data.AppDatabase;

@Entity(tableName = AppDatabase.Sub_DBConnection.TABLE_FACILITY_PROVIDER)
public class FacilityProvider {

    @PrimaryKey(autoGenerate = true)
    private int id;

    @ColumnInfo
    private String hf_district_code = "";
    private String hf_tehsil = "";
    private String hf_type = "";
    private String hf_name = "";
    private String hf_uen_code = "";


    public FacilityProvider() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }


    public String getHf_type() {
        return hf_type;
    }

    public void setHf_type(String hf_type) {
        this.hf_type = hf_type;
    }


    public String getHf_district_code() {
        return hf_district_code;
    }

    public void setHf_district_code(String hf_district_code) {
        this.hf_district_code = hf_district_code;
    }


    public String getHf_tehsil() {
        return hf_tehsil;
    }

    public void setHf_tehsil(String hf_tehsil) {
        this.hf_tehsil = hf_tehsil;
    }


    public String getHf_name() {
        return hf_name;
    }

    public void setHf_name(String hf_name) {
        this.hf_name = hf_name;
    }


    public String getHf_uen_code() {
        return hf_uen_code;
    }

    public void setHf_uen_code(String hf_uen_code) {
        this.hf_uen_code = hf_uen_code;
    }


    public FacilityProvider Sync(JSONObject jsonObjectCls) throws JSONException {
        this.hf_district_code =jsonObjectCls.getString("hf_district_code");
        this.hf_tehsil = jsonObjectCls.getString("tehsil_code");
        this.hf_type = jsonObjectCls.getString("hf_type");
        this.hf_name = jsonObjectCls.getString("hf_name");
        this.hf_uen_code = jsonObjectCls.getString("hf_uen_code");
        return this;
    }

}
