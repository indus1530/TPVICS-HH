package edu.aku.ramshasaeed.mnch.data.entities;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

import org.json.JSONException;
import org.json.JSONObject;

import edu.aku.ramshasaeed.mnch.data.AppDatabase;

@Entity(tableName = AppDatabase.Sub_DBConnection.TABLE_TEHSIL)
public class Tehsil {

    @PrimaryKey(autoGenerate = true)
    private int id;

    @ColumnInfo
    private String tehsil_code = "";
    private String tehsil_name = "";
    private String district_code = "";

    public Tehsil() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTehsil_code() {
        return tehsil_code;
    }

    public void setTehsil_code(String tehsil_code) {
        this.tehsil_code = tehsil_code;
    }

    public String getTehsil_name() {
        return tehsil_name;
    }

    public void setTehsil_name(String tehsil_name) {
        this.tehsil_name = tehsil_name;
    }

    public String getDistrict_code() {
        return district_code;
    }

    public void setDistrict_code(String district_code) {
        this.district_code = district_code;
    }

    public Tehsil Sync(JSONObject jsonObjectCls) throws JSONException {

        this.tehsil_code = jsonObjectCls.getString("tehsil_code");
        this.tehsil_name = jsonObjectCls.getString("tehsil_name");
        this.district_code = jsonObjectCls.getString("district_code");
        return this;
    }
}
