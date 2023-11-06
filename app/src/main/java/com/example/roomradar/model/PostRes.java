package com.example.roomradar.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class PostRes {
    @SerializedName("posts")
    private List<Post> posts;

    public List<Post> getPosts() {
        return posts;
    }
}
