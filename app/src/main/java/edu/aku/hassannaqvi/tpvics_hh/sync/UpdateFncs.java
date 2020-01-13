package edu.aku.hassannaqvi.tpvics_hh.sync;

import java.util.Date;
import java.util.concurrent.ExecutionException;

import edu.aku.hassannaqvi.tpvics_hh.RMOperations.UpdateSyncedStatus;
import edu.aku.hassannaqvi.tpvics_hh.data.DAO.FormsDAO;

import static edu.aku.hassannaqvi.tpvics_hh.ui.LoginActivity.db;

public abstract class UpdateFncs {

    public static void updateSyncedForms_04_05(int _id) {

//        new SyncOperations(db).execute(FormsDAO.class.getName(), "formsDao", "updateSyncedForms_04_05");
        try {
            new UpdateSyncedStatus(db, new Date().toString(), _id).execute(FormsDAO.class.getName(), "formsDao", "updateSyncedForms_04_05").get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

    public static void updateSyncedForms(int _id) {

//        new SyncOperations(db).execute(FormsDAO.class.getName(), "formsDao", "updateSyncedForms_04_05");
        try {
            new UpdateSyncedStatus(db, new Date().toString(), _id).execute(FormsDAO.class.getName(), "formsDao", "updateSyncedForms").get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }
}
