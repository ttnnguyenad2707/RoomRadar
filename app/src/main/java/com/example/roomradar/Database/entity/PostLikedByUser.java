package com.example.roomradar.Database.entity;

import androidx.room.Entity;
import androidx.room.ForeignKey;

@Entity(tableName = "post_liked_by_user",
        primaryKeys = {"post_id", "user_id"},
        foreignKeys = {
                @ForeignKey(entity = Post.class, parentColumns = "id", childColumns = "post_id"),
                @ForeignKey(entity = User.class, parentColumns = "id", childColumns = "user_id")
        })
public class PostLikedByUser {
    private int post_id;

    private int user_id;

    public PostLikedByUser() {
    }

    public PostLikedByUser(int post_id, int user_id) {
        this.post_id = post_id;
        this.user_id = user_id;
    }

    public int getPost_id() {
        return post_id;
    }

    public void setPost_id(int post_id) {
        this.post_id = post_id;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }
}
