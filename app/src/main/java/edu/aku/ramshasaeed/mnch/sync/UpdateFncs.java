package edu.aku.ramshasaeed.mnch.sync;


import java.util.Date;
import java.util.concurrent.ExecutionException;

import edu.aku.ramshasaeed.mnch.RMOperations.UpdateSyncedStatus;
import edu.aku.ramshasaeed.mnch.data.DAO.FormsDAO;

import static edu.aku.ramshasaeed.mnch.activities.LoginActivity.db;

public abstract class UpdateFncs {



    public static void updateSyncedForms(int _id) {

//        new syncOperations(db).execute(FormsDAO.class.getName(), "formsDao", "updateSyncedForms_04_05");
        try {
            new UpdateSyncedStatus(db, new Date().toString(),_id).execute(FormsDAO.class.getName(), "formsDao", "updateSyncedForms").get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }
}
