package edu.aku.hassannaqvi.tpvics_hh.get.server;

import android.content.Context;

import org.json.JSONArray;
import org.json.JSONObject;

import edu.aku.hassannaqvi.tpvics_hh.RMOperations.CrudOperations;
import edu.aku.hassannaqvi.tpvics_hh.RMOperations.SyncOperations;
import edu.aku.hassannaqvi.tpvics_hh.data.AppDatabase;
import edu.aku.hassannaqvi.tpvics_hh.data.DAO.FormsDAO;
import edu.aku.hassannaqvi.tpvics_hh.data.entities.Clusters;
import edu.aku.hassannaqvi.tpvics_hh.data.entities.Districts;
import edu.aku.hassannaqvi.tpvics_hh.data.entities.HFA;
import edu.aku.hassannaqvi.tpvics_hh.data.entities.UCs;
import edu.aku.hassannaqvi.tpvics_hh.data.entities.Users;

public abstract class GetSyncFncs {

    public static void syncUsers(Context mContext, JSONArray userlist) {

        AppDatabase db = AppDatabase.getDatabase(mContext);

        new SyncOperations(db).execute(FormsDAO.class.getName(), "formsDao", "deleteUsers");

        try {
            JSONArray jsonArray = userlist;
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObjectUser = jsonArray.getJSONObject(i);

                Users users = new Users();
                users.Sync(jsonObjectUser);

                new CrudOperations(mContext, FormsDAO.class.getName(), "formsDao", "insertUsers", users).execute().get();
            }
            db.close();

        } catch (Exception e) {
        }
    }

    public static void syncClusters(Context mContext, JSONArray clusterList) {

        AppDatabase db = AppDatabase.getDatabase(mContext);

        new SyncOperations(db).execute(FormsDAO.class.getName(), "formsDao", "deleteClusters");

        try {
            JSONArray jsonArray = clusterList;
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObjectUser = jsonArray.getJSONObject(i);

                Clusters clusters = new Clusters();
                clusters.Sync(jsonObjectUser);

                new CrudOperations(mContext, FormsDAO.class.getName(), "formsDao", "insertClusters", clusters).execute().get();
            }
            db.close();

        } catch (Exception e) {
        }
    }

    public static void syncDistricts(Context mContext, JSONArray distList) {

        AppDatabase db = AppDatabase.getDatabase(mContext);

        new SyncOperations(db).execute(FormsDAO.class.getName(), "formsDao", "deleteDistricts");

        try {
            JSONArray jsonArray = distList;
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObjectUser = jsonArray.getJSONObject(i);

                Districts dist = new Districts();
                dist.Sync(jsonObjectUser);

                new CrudOperations(mContext, FormsDAO.class.getName(), "formsDao", "insertDistricts", dist).execute().get();
            }
            db.close();

        } catch (Exception e) {
        }
    }

    public static void syncUcs(Context mContext, JSONArray ucsList) {

        AppDatabase db = AppDatabase.getDatabase(mContext);

        new SyncOperations(db).execute(FormsDAO.class.getName(), "formsDao", "deleteUCs");

        try {
            JSONArray jsonArray = ucsList;
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObjectUser = jsonArray.getJSONObject(i);

                UCs ucs = new UCs();
                ucs.Sync(jsonObjectUser);

                new CrudOperations(mContext, FormsDAO.class.getName(), "formsDao", "insertUcs", ucs).execute().get();
            }
            db.close();

        } catch (Exception e) {
        }
    }

    public static void syncHfa(Context mContext, JSONArray hfaList) {

        AppDatabase db = AppDatabase.getDatabase(mContext);

        new SyncOperations(db).execute(FormsDAO.class.getName(), "formsDao", "deleteHfa");

        try {
            JSONArray jsonArray = hfaList;
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObjectUser = jsonArray.getJSONObject(i);

                HFA hfa = new HFA();
                hfa.Sync(jsonObjectUser);

                new CrudOperations(mContext, FormsDAO.class.getName(), "formsDao", "insertHFA", hfa).execute().get();
            }
            db.close();

        } catch (Exception e) {
        }
    }

}
