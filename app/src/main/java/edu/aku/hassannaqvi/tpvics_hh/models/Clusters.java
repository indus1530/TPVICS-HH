package edu.aku.hassannaqvi.tpvics_hh.models;


import android.database.Cursor;
import android.provider.BaseColumns;

import org.json.JSONException;
import org.json.JSONObject;

public class Clusters {

    private static final String TAG = "EnumBlock_CONTRACT";
    String dist_code;
    String ebcode;
    String geoarea;
    String cluster;

    public Clusters() {
        // Default Constructor
    }

    public Clusters Sync(JSONObject jsonObject) throws JSONException {
        this.dist_code = jsonObject.getString(ClusterTable.COLUMN_DIST_CODE);
        this.ebcode = jsonObject.getString(ClusterTable.COLUMN_ENUM_BLOCK_CODE);
        this.geoarea = jsonObject.getString(ClusterTable.COLUMN_GEO_AREA);
        this.cluster = jsonObject.getString(ClusterTable.COLUMN_CLUSTER_AREA);
        return this;
    }

    public Clusters HydrateEnum(Cursor cursor) {
        this.ebcode = cursor.getString(cursor.getColumnIndex(ClusterTable.COLUMN_ENUM_BLOCK_CODE));
        this.dist_code = cursor.getString(cursor.getColumnIndex(ClusterTable.COLUMN_DIST_CODE));
        this.geoarea = cursor.getString(cursor.getColumnIndex(ClusterTable.COLUMN_GEO_AREA));
        this.cluster = cursor.getString(cursor.getColumnIndex(ClusterTable.COLUMN_CLUSTER_AREA));
        return this;
    }

    public String getDist_code() {
        return dist_code;
    }

    public void setDist_code(String dsit_code) {
        this.dist_code = dsit_code;
    }

    public String getEbcode() {
        return ebcode;
    }

    public void setEbcode(String ebcode) {
        this.ebcode = ebcode;
    }

    public String getGeoarea() {
        return geoarea;
    }

    public void setGeoarea(String geoarea) {
        this.geoarea = geoarea;
    }

    public String getCluster() {
        return cluster;
    }

    public void setCluster(String cluster) {
        this.cluster = cluster;
    }

    public JSONObject toJSONObject() throws JSONException {

        JSONObject json = new JSONObject();
        json.put(ClusterTable.COLUMN_ENUM_BLOCK_CODE, this.ebcode == null ? JSONObject.NULL : this.ebcode);
        json.put(ClusterTable.COLUMN_DIST_CODE, this.dist_code == null ? JSONObject.NULL : this.dist_code);
        json.put(ClusterTable.COLUMN_GEO_AREA, this.geoarea == null ? JSONObject.NULL : this.geoarea);
        json.put(ClusterTable.COLUMN_CLUSTER_AREA, this.cluster == null ? JSONObject.NULL : this.cluster);
        return json;
    }


    public static abstract class ClusterTable implements BaseColumns {

        public static final String TABLE_NAME = "clusters";
        public static final String COLUMN_DIST_CODE = "dist_id";
        public static final String COLUMN_ENUM_BLOCK_CODE = "ebcode";
        public static final String COLUMN_GEO_AREA = "geoarea";
        public static final String COLUMN_CLUSTER_AREA = "cluster_no";
    }
}