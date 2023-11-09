package com.example.roomradar.Database.DAO;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.roomradar.Database.entity.Post;

import java.util.List;

@Dao
public interface PostDao {
    @Insert
    long insertPost(Post post);

    @Query("SELECT * FROM Post")
    List<Post> getAllPost();
    @Query("select * from Post where id=:postId ")
    Post getPostById(int postId);
}
