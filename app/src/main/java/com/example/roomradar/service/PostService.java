package com.example.roomradar.service;

import android.content.Context;

import com.example.roomradar.Database.database;
import com.example.roomradar.Database.entity.Post;

import java.util.List;

public class PostService {
    private static PostService InstanceService;
    private database db;
    private PostService(Context context){
        db= database.getInstance(context);
    }
    public static PostService getInstance(Context context){
        if(InstanceService==null){
            InstanceService = new PostService(context);
        }
        return InstanceService;
    }

    public long addNewPost(Post post){
        return db.postDao().insertPost(post);
    }
    public void updatePost(Post post){
        db.postDao().updatePost(post);
    }
    public void deletePost(Post post){
        db.postDao().deletePost(post);
    }
    public List<Post> getAllPost(){
        return db.postDao().getAllPost();
    }
    public Post getPostById(int postId){
        return db.postDao().getPostById(postId);
    }
}
