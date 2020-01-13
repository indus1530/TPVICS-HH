package edu.aku.hassannaqvi.tpvics_hh.data.entities;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import org.json.JSONException;
import org.json.JSONObject;

import edu.aku.hassannaqvi.tpvics_hh.core.CONSTANTS;

@Entity(tableName = CONSTANTS.TABLE_HFA)
public class HFA {

    @PrimaryKey(autoGenerate = true)
    private int id;

    @ColumnInfo
    private String dist_code = "";
    private String uc_code = "";
    private String hf_code = "";
    private String hf_name = "";


    @Ignore
    public HFA(HFA hfa) {
        this.dist_code = hfa.dist_code;
        this.uc_code = hfa.uc_code;
        this.hf_code = hfa.hf_code;
        this.hf_name = hfa.hf_name;
    }

    @Ignore
    public HFA(String dist_code, String uc_code, String hf_code, String hf_name) {
        this.dist_code = dist_code;
        this.uc_code = uc_code;
        this.hf_code = hf_code;
        this.hf_name = hf_name;
    }

    public HFA() {
    }

    public HFA Sync(JSONObject jsonObjectCls) throws JSONException {

        this.dist_code = jsonObjectCls.getString("dist_code");
        this.uc_code = jsonObjectCls.getString("uc_code");
        this.hf_code = jsonObjectCls.getString("hf_code");
        this.hf_name = jsonObjectCls.getString("hf_name");
        return this;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDist_code() {
        return dist_code;
    }

    public void setDist_code(String dist_code) {
        this.dist_code = dist_code;
    }

    public String getUc_code() {
        return uc_code;
    }

    public void setUc_code(String uc_code) {
        this.uc_code = uc_code;
    }

    public String getHf_code() {
        return hf_code;
    }

    public void setHf_code(String hf_code) {
        this.hf_code = hf_code;
    }

    public String getHf_name() {
        return hf_name;
    }

    public void setHf_name(String hf_name) {
        this.hf_name = hf_name;
    }


}
