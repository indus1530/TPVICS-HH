package edu.aku.hassannaqvi.tpvics_hh.data.DAO;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import edu.aku.hassannaqvi.tpvics_hh.data.AppDatabase;
import edu.aku.hassannaqvi.tpvics_hh.data.AppDatabase.Sub_DBConnection;
import edu.aku.hassannaqvi.tpvics_hh.data.entities.District;
import edu.aku.hassannaqvi.tpvics_hh.data.entities.FacilityProvider;
import edu.aku.hassannaqvi.tpvics_hh.data.entities.Forms;
import edu.aku.hassannaqvi.tpvics_hh.data.entities.Tehsil;
import edu.aku.hassannaqvi.tpvics_hh.data.entities.UCs;
import edu.aku.hassannaqvi.tpvics_hh.data.entities.Users;

@Dao
public interface FormsDAO {

    /*Form*/
    @Insert
    Long insertForm(Forms forms);

    @Delete
    void delete(Forms forms);

    @Update
    int updateForm(Forms forms);


    /*Others Get Apis*/
    @Insert
    Long insertUsers(Users users);

    @Insert
    Long insertDistrict(District district);

    @Insert
    Long insertFacilityProvider(FacilityProvider facility_provider);

    @Insert
    Long insertTehsil(Tehsil tehsil);
//
    @Insert
    Long insertUCs(UCs ucs);

    @Query("DELETE from " + AppDatabase.Sub_DBConnection.TABLE_USERS)
    int deleteUsers();

    @Query("DELETE from " + Sub_DBConnection.TABLE_DISTRICT)
    int deleteDistrict();

    @Query("DELETE from " + Sub_DBConnection.TABLE_FACILITY_PROVIDER)
    int deleteFacilityProvider();

    @Query("DELETE from " + Sub_DBConnection.TABLE_TEHSIL)
    int deleteTehsil();

    @Query("DELETE from " + Sub_DBConnection.TABLE_UCs)
    int deleteUcs();


    /**
     * Updating only sync and syncDate
     * By order id
     */
    @Query("UPDATE Forms SET synced = '1', synced_date= :date WHERE id =:id")
    int updateSyncedForms(String date, int id);

}
