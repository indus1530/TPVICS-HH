package edu.aku.ramshasaeed.mnch.data.DAO;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Query;

import java.util.List;

import edu.aku.ramshasaeed.mnch.data.AppDatabase;
import edu.aku.ramshasaeed.mnch.data.entities.District;
import edu.aku.ramshasaeed.mnch.data.entities.FacilityProvider;
import edu.aku.ramshasaeed.mnch.data.entities.Forms;
import edu.aku.ramshasaeed.mnch.data.entities.Tehsil;
import edu.aku.ramshasaeed.mnch.data.entities.UCs;
import edu.aku.ramshasaeed.mnch.data.entities.Users;

@Dao
public interface GetFncDAO {

    @Query("SELECT * FROM " + AppDatabase.Sub_DBConnection.TABLE_FORMS)
    List<Forms> getAll();

    @Query("SELECT * FROM " + AppDatabase.Sub_DBConnection.TABLE_FORMS + " WHERE id=:id")
    Forms getLastForm(int id);

    @Query("SELECT * FROM " + AppDatabase.Sub_DBConnection.TABLE_FORMS + " WHERE formDate LIKE :date")
    List<Forms> getTodaysForms(String date);

    @Query("SELECT * FROM " + AppDatabase.Sub_DBConnection.TABLE_FORMS + " WHERE synced = ''")
    List<Forms> getUnSyncedForms();

    @Query("SELECT * FROM " + AppDatabase.Sub_DBConnection.TABLE_DISTRICT)
    List<District> getAllDistricts();

    @Query("SELECT * FROM " + AppDatabase.Sub_DBConnection.TABLE_FORMS)
    List<Forms> getForms();

    @Query("SELECT * FROM " + AppDatabase.Sub_DBConnection.TABLE_FORMS + " WHERE synced = '' AND formType = :formType AND istatus = '1'")
    List<Forms> getUnSyncedForms(String formType);

    @Query("SELECT * FROM " + AppDatabase.Sub_DBConnection.TABLE_FORMS + " WHERE formType = :formType")
    List<Forms> getForms(String formType);

    @Query("SELECT * FROM " + AppDatabase.Sub_DBConnection.TABLE_USERS + " where ROW_USERNAME=:username AND ROW_PASSWORD=:password")
    Users login(String username, String password);/*

    @Query("SELECT * FROM " + AppDatabase.Sub_DBConnection.TABLE_FORMS + " where reportingMonth=:reportingMonth AND facilityType='1' AND districtCode=:districtCode AND facilityCode=:facilityCode")
    Forms getPendingPublicForm(String reportingMonth, String districtCode, String facilityCode);

    @Query("SELECT * FROM " + AppDatabase.Sub_DBConnection.TABLE_FORMS + " where reportingMonth=:reportingMonth AND facilityType='2' AND districtCode=:districtCode AND facilityCode=:facilityCode")
    Forms getPendingPrivateForm(String reportingMonth, String districtCode, String facilityCode);*/

    @Query("SELECT * FROM " + AppDatabase.Sub_DBConnection.TABLE_FORMS + " where reportingMonth=:reportingMonth AND hf_code=:hf_code")
    Forms getPendingFormo(String reportingMonth, String hf_code);

    @Query("SELECT * FROM " + AppDatabase.Sub_DBConnection.TABLE_UCs + " where tehsil_code=:tehsil_code")
    List<UCs> getUCs(String tehsil_code);

    /*@Query("SELECT * FROM " + AppDatabase.Sub_DBConnection.TABLE_FACILITY_PROVIDER + " where hf_district_code =:hf_district_code")
    List<FacilityProvider> getFacilityProvider(String hf_district_code);*/

    @Query("SELECT * FROM " + AppDatabase.Sub_DBConnection.TABLE_FACILITY_PROVIDER + " where hf_tehsil LIKE :hf_tehsil  AND hf_type =:hf_type")
    List<FacilityProvider> getFacilityProvider(String hf_tehsil, String hf_type);

    /*@Query("SELECT * FROM " + AppDatabase.Sub_DBConnection.TABLE_FACILITY_PROVIDER + " where hf_tehsil =:hf_tehsil")
    List<FacilityProvider> getFacilityProvider(String hf_tehsil);*/

    @Query("SELECT * FROM " + AppDatabase.Sub_DBConnection.TABLE_TEHSIL + " where district_code =:district_code")
    List<Tehsil> getTehsil(String district_code);

    @Query("SELECT * FROM " + AppDatabase.Sub_DBConnection.TABLE_FORMS + " WHERE synced = '0' AND istatus = '2' AND formType =:formType")
    List<Forms> getPendingForms(String formType);

}
