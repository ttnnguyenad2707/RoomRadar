package com.example.roomradar.Database.DAO;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.roomradar.Database.entity.Utils;

import java.util.List;

@Dao
public interface UtilsDAO {
    @Query("select * from utils")
    List<Utils> getAllUtils();
    @Insert
    void addNewUtils(Utils utils);

}
