package com.example.roomradar.Database.DAO;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.roomradar.Database.entity.PostLikedByUser;

import java.util.List;

@Dao
public interface PostLikeByUserDAO {
    @Insert
    void addPostToFavorite(PostLikedByUser postLikedByUser);

    @Query("select * from post_liked_by_user where user_id=:userId")
    List<PostLikedByUser> listFavoriteByUser(int userId);
    @Delete
    void deletePostFavorite(PostLikedByUser postLikedByUser);

}
