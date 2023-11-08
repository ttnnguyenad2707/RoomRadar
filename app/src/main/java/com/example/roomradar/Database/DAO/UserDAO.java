package com.example.roomradar.Database.DAO;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.roomradar.Database.entity.User;

@Dao
public interface UserDAO {
    @Insert
    void insertUser(User user);

    @Query("Select * from User where id =:UserId ")
    User getUserById(int UserId);

    @Update
    User updateUser(User user);
}
