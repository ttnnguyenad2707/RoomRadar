package com.example.roomradar.Database.DAO;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.roomradar.Database.entity.Post;

@Dao
public interface PostDao {
    @Insert
    void insertPost(Post post);

    @Query("SELECT * FROM Post")
    Post getAllPost();
}
