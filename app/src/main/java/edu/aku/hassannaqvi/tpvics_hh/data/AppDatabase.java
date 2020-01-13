package edu.aku.hassannaqvi.tpvics_hh.data;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import edu.aku.hassannaqvi.tpvics_hh.core.MainApp;
import edu.aku.hassannaqvi.tpvics_hh.data.DAO.FormsDAO;
import edu.aku.hassannaqvi.tpvics_hh.data.DAO.GetFncDAO;
import edu.aku.hassannaqvi.tpvics_hh.data.entities.District;
import edu.aku.hassannaqvi.tpvics_hh.data.entities.FacilityProvider;
import edu.aku.hassannaqvi.tpvics_hh.data.entities.Forms;
import edu.aku.hassannaqvi.tpvics_hh.data.entities.Tehsil;
import edu.aku.hassannaqvi.tpvics_hh.data.entities.UCs;
import edu.aku.hassannaqvi.tpvics_hh.data.entities.Users;

@Database(entities = {Users.class, Forms.class, District.class,
        FacilityProvider.class, Tehsil.class, UCs.class}, version = AppDatabase.Sub_DBConnection.DATABASE_VERSION, exportSchema = false)

public abstract class AppDatabase extends RoomDatabase {

    // Alter table for Database Update
    /*static final Migration MIGRATION_v1_v2 = new Migration(1, 2) {
        @Override
        public void migrate(SupportSQLiteDatabase database) {
            database.execSQL("ALTER TABLE forms "
                    + " ADD COLUMN reportingMonth TEXT");
            database.execSQL("ALTER TABLE forms "
                    + " ADD COLUMN district_code TEXT");
            database.execSQL("ALTER TABLE forms "
                    + " ADD COLUMN tehsil_code TEXT");
            database.execSQL("ALTER TABLE forms "
                    + " ADD COLUMN hf_type TEXT");
            database.execSQL("ALTER TABLE forms "
                    + " ADD COLUMN hf_code TEXT");
            database.execSQL("ALTER TABLE forms "
                    + " ADD COLUMN hf_name TEXT");
        }
    };*/

    private static AppDatabase sInstance;

    public static AppDatabase getDatabase(final Context context) {
        if (sInstance == null) {
            synchronized (AppDatabase.class) {
                if (sInstance == null) {
                    sInstance = Room.databaseBuilder(context, AppDatabase.class, Sub_DBConnection.DATABASE_NAME)
                            //.addMigrations(MIGRATION_v1_v2)
                            .setJournalMode(JournalMode.TRUNCATE)
                            .build();
                }
            }
        }
        return sInstance;
    }

    public abstract FormsDAO formsDao();

    public abstract GetFncDAO getFncDao();

    public interface Sub_DBConnection {
        String DATABASE_NAME = MainApp.AppName;
        int DATABASE_VERSION = 1;
        String TABLE_FORMS = "forms";
        String TABLE_USERS = "users";
        String TABLE_DISTRICT = "district";
        String TABLE_FACILITY_PROVIDER = "facility_provider";
        String TABLE_TEHSIL = "tehsil";
        String TABLE_UCs = "ucs";
    }
}
