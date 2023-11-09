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

    @Query("Select * from User where email= :Email ")
    User getUserByEmail(String Email);
    @Query("Select * from User where email= :Email and password=:Password")
    User verifyUser(String Email, String Password);
    @Update
    Void updateUser(User user);

    @Query("SELECT lastName FROM user WHERE id = :userId")
    String getUserLastName(int userId);

    @Query("SELECT phone FROM user WHERE id = :userId")
    String getUserPhone(int userId);
}
