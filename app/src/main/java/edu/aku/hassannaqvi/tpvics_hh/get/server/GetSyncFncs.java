package edu.aku.hassannaqvi.tpvics_hh.get.server;

import org.json.JSONArray;
import org.json.JSONObject;

import edu.aku.hassannaqvi.tpvics_hh.RMOperations.CrudOperations;
import edu.aku.hassannaqvi.tpvics_hh.RMOperations.SyncOperations;
import edu.aku.hassannaqvi.tpvics_hh.data.DAO.FormsDAO;
import edu.aku.hassannaqvi.tpvics_hh.data.entities.District;
import edu.aku.hassannaqvi.tpvics_hh.data.entities.FacilityProvider;
import edu.aku.hassannaqvi.tpvics_hh.data.entities.Tehsil;
import edu.aku.hassannaqvi.tpvics_hh.data.entities.UCs;
import edu.aku.hassannaqvi.tpvics_hh.data.entities.Users;

import static edu.aku.hassannaqvi.tpvics_hh.activities.LoginActivity.db;

public abstract class GetSyncFncs {

    public static void syncUsers(JSONArray userlist) {

        new SyncOperations(db).execute(FormsDAO.class.getName(), "formsDao", "deleteUsers");

        try {
            JSONArray jsonArray = userlist;
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObjectUser = jsonArray.getJSONObject(i);

                Users users = new Users();
                users.Sync(jsonObjectUser);

                new CrudOperations(db, users).execute(FormsDAO.class.getName(), "formsDao", "insertUsers").get();
            }
            db.close();

        } catch (Exception e) {
        }
    }

    public static void syncDistricts(JSONArray clusterList) {

        new SyncOperations(db).execute(FormsDAO.class.getName(), "formsDao", "deleteDistrict");

        try {
            JSONArray jsonArray = clusterList;
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObjectUser = jsonArray.getJSONObject(i);

                District district = new District();
                district.Sync(jsonObjectUser);

                new CrudOperations(db, district).execute(FormsDAO.class.getName(), "formsDao", "insertDistrict").get();
            }
            db.close();

        } catch (Exception e) {
        }
    }
    public static void syncTehsil(JSONArray clusterList) {

        new SyncOperations(db).execute(FormsDAO.class.getName(), "formsDao", "deleteTehsil");

        try {
            JSONArray jsonArray = clusterList;
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObjectUser = jsonArray.getJSONObject(i);

                Tehsil tehsil = new Tehsil();
                tehsil.Sync(jsonObjectUser);

                new CrudOperations(db, tehsil).execute(FormsDAO.class.getName(), "formsDao", "insertTehsil").get();
            }
            db.close();

        } catch (Exception e) {
        }
    }

    public static void syncUCs(JSONArray clusterList) {

        new SyncOperations(db).execute(FormsDAO.class.getName(), "formsDao", "deleteUcs");

        try {
            JSONArray jsonArray = clusterList;
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObjectUser = jsonArray.getJSONObject(i);

                UCs uCs = new UCs();
                uCs.Sync(jsonObjectUser);

                new CrudOperations(db, uCs).execute(FormsDAO.class.getName(), "formsDao", "insertUCs").get();
            }
            db.close();

        } catch (Exception e) {
        }
    }

    public static void syncFacilityProvider(JSONArray dataList) {

        new SyncOperations(db).execute(FormsDAO.class.getName(), "formsDao", "deleteFacilityProvider");

        try {
            JSONArray jsonArray = dataList;
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObjectdata = jsonArray.getJSONObject(i);

                FacilityProvider facility_provider = new FacilityProvider();
                facility_provider.Sync(jsonObjectdata);

                new CrudOperations(db, facility_provider).execute(FormsDAO.class.getName(), "formsDao", "insertFacilityProvider").get();
            }
            db.close();

        } catch (Exception e) {
        }
    }

}
