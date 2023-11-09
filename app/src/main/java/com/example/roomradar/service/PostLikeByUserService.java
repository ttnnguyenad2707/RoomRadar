package com.example.roomradar.service;

import android.content.Context;

import com.example.roomradar.Database.database;
import com.example.roomradar.Database.entity.PostLikedByUser;

import java.util.List;

public class PostLikeByUserService {
    private static PostLikeByUserService InstanceService;
    private database db;
    private PostLikeByUserService(Context context){
        db= database.getInstance(context);
    }
    public static PostLikeByUserService getInstance(Context context){
        if(InstanceService==null){
            InstanceService = new PostLikeByUserService(context);
        }
        return InstanceService;
    }
    public void addToFavorite(PostLikedByUser postLikedByUser){
        db.postLikeByUserDAO().addPostToFavorite(postLikedByUser);
    }
    public List<PostLikedByUser> getFavoriteByUser(int userId){
        return db.postLikeByUserDAO().listFavoriteByUser(userId);
    }
    public void deletePostFavorite(PostLikedByUser postLikedByUser){
        db.postLikeByUserDAO().deletePostFavorite(postLikedByUser);
    }
}
