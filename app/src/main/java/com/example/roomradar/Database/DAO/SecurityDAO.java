package com.example.roomradar.Database.DAO;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.roomradar.Database.entity.Category;
import com.example.roomradar.Database.entity.Security;

import java.util.List;

@Dao
public interface SecurityDAO {
    @Query("select * from Security")
    List<Security> getAllSecurity();
    @Query("select * from security where id=:id")
    Security getSecurityById(int id);

}
