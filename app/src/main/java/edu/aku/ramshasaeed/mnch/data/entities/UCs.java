package edu.aku.ramshasaeed.mnch.data.entities;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

import org.json.JSONException;
import org.json.JSONObject;

import edu.aku.ramshasaeed.mnch.data.AppDatabase;

@Entity(tableName = AppDatabase.Sub_DBConnection.TABLE_UCs)
public class UCs {

    @PrimaryKey(autoGenerate = true)
    private int id;

    @ColumnInfo
    private String uc_code = "";
    private String uc_name = "";
    private String tehsil_code = "";
    private String uen_code = "";

    public UCs() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUc_code() {
        return uc_code;
    }

    public void setUc_code(String uc_code) {
        this.uc_code = uc_code;
    }

    public String getUc_name() {
        return uc_name;
    }

    public void setUc_name(String uc_name) {
        this.uc_name = uc_name;
    }

    public String getTehsil_code() {
        return tehsil_code;
    }

    public void setTehsil_code(String tehsil_code) {
        this.tehsil_code = tehsil_code;
    }

    public String getUen_code() {
        return uen_code;
    }

    public void setUen_code(String uen_code) {
        this.uen_code = uen_code;
    }

    public UCs Sync(JSONObject jsonObjectCls) throws JSONException {

        this.uc_code = jsonObjectCls.getString("uc_code");
        this.uc_name = jsonObjectCls.getString("uc_name");
        this.tehsil_code = jsonObjectCls.getString("tehsil_code");
//        this.uen_code = jsonObjectCls.getString("hf_uen_code");
        return this;
    }
}
