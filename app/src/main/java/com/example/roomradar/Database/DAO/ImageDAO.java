package com.example.roomradar.Database.DAO;


import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.roomradar.Database.entity.Images;

import java.util.List;

@Dao
public interface ImageDAO {
    @Insert
    void addNewImage(Images images);
    @Query("select * from images where postId =:postId ")
    List<Images> getImagesByPost(int postId);

}
