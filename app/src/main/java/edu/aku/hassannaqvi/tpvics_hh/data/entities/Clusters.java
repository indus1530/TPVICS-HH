package edu.aku.hassannaqvi.tpvics_hh.data.entities;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import org.json.JSONException;
import org.json.JSONObject;

import edu.aku.hassannaqvi.tpvics_hh.core.CONSTANTS;


@Entity(tableName = CONSTANTS.TABLE_CLUSTERS)
public class Clusters {

    @PrimaryKey(autoGenerate = true)
    private int id;

    @ColumnInfo
    private String cluster_code = "";
    private String cluster_name = "";

    public Clusters() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCluster_code() {
        return cluster_code;
    }

    public void setCluster_code(String cluster_code) {
        this.cluster_code = cluster_code;
    }

    public String getCluster_name() {
        return cluster_name;
    }

    public void setCluster_name(String cluster_name) {
        this.cluster_name = cluster_name;
    }

    public Clusters Sync(JSONObject jsonObjectCls) throws JSONException {

        this.cluster_code = jsonObjectCls.getString("cluster_code");
        this.cluster_name = jsonObjectCls.getString("cluster_name");
        return this;
    }
}