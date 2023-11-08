package com.example.roomradar.Database.entity;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Images {
    @PrimaryKey(autoGenerate = true)
    private int id;
    private String url;
    private int postId;

    public Images(String url,int postId) {
        this.postId = postId;
        this.url = url;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public int getPostId() {
        return postId;
    }

    public void setPostId(int postId) {
        this.postId = postId;
    }
}
