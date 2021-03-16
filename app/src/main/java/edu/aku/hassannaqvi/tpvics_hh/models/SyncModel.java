package edu.aku.hassannaqvi.tpvics_hh.models;

import org.apache.commons.lang3.StringUtils;

public class SyncModel {
    String tableName;
    String status;
    int statusID;
    String message;

    public SyncModel(String tableName) {

        this.tableName = tableName;
        this.status = StringUtils.EMPTY;
        this.statusID = 0;
        this.message = StringUtils.EMPTY;


    }


    public String gettableName() {
        return tableName;
    }

    public void settableName(String tableName) {
        this.tableName = tableName;
    }

    public String getstatus() {
        return status;
    }

    public void setstatus(String status) {
        this.status = status;
    }

    public int getstatusID() {
        return statusID;
    }

    public void setstatusID(int statusID) {
        this.statusID = statusID;
    }

    public String getmessage() {
        return message;
    }

    public void setmessage(String message) {
        this.message = message;
    }
}
