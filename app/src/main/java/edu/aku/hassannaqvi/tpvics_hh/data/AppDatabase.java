package edu.aku.hassannaqvi.tpvics_hh.data;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.migration.Migration;
import androidx.sqlite.db.SupportSQLiteDatabase;

import edu.aku.hassannaqvi.tpvics_hh.core.CONSTANTS;
import edu.aku.hassannaqvi.tpvics_hh.data.DAO.FormsDAO;
import edu.aku.hassannaqvi.tpvics_hh.data.DAO.GetFncDAO;
import edu.aku.hassannaqvi.tpvics_hh.data.entities.Clusters;
import edu.aku.hassannaqvi.tpvics_hh.data.entities.Districts;
import edu.aku.hassannaqvi.tpvics_hh.data.entities.Forms;
import edu.aku.hassannaqvi.tpvics_hh.data.entities.HFA;
import edu.aku.hassannaqvi.tpvics_hh.data.entities.UCs;
import edu.aku.hassannaqvi.tpvics_hh.data.entities.Users;

@Database(entities = {Forms.class, Clusters.class, Users.class, Districts.class, UCs.class, HFA.class}, version = CONSTANTS.DATABASE_VERSION, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {

    // Alter table for Database Update
    static final Migration MIGRATION_v1_v2 = new Migration(1, 2) {
        @Override
        public void migrate(SupportSQLiteDatabase database) {
            database.execSQL("ALTER TABLE " + CONSTANTS.TABLE_FORMS
                    + " ADD COLUMN followupType TEXT");
        }
    };

    private static AppDatabase sInstance;

    public static AppDatabase getDatabase(final Context context) {
        if (sInstance == null) {
            synchronized (AppDatabase.class) {
                if (sInstance == null) {
                    sInstance = Room.databaseBuilder(context, AppDatabase.class, CONSTANTS.DATABASE_NAME)
//                            .addMigrations(MIGRATION_v1_v2)
                            .setJournalMode(RoomDatabase.JournalMode.TRUNCATE)
                            .build();
                }
            }
        }
        return sInstance;
    }

    public abstract FormsDAO formsDao();

    public abstract GetFncDAO getFncDao();

}
