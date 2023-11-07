package com.example.roomradar.Database.DAO;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.roomradar.Database.entity.Category;

import java.util.List;


@Dao
public interface CategoryDAO {
    @Insert
    void insertCategory(Category category);

    @Query("SELECT * FROM CATEGORY")
    List<Category> getAllCategory();
}
