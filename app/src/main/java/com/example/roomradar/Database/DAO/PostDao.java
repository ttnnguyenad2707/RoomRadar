package com.example.roomradar.Database.DAO;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.roomradar.Database.entity.Post;

import java.util.List;

@Dao
public interface PostDao {
    @Insert
    long insertPost(Post post);
    @Update
    void updatePost(Post post);
    @Delete
    void deletePost(Post post);
    @Query("SELECT * FROM Post")
    List<Post> getAllPost();
    @Query("select * from Post where id=:postId ")
    Post getPostById(int postId);
    @Query("select * from Post where category=:categoryId")
    List<Post> getPostByCategory(int categoryId);
}
