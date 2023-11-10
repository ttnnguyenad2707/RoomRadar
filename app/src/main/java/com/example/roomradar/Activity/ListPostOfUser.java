package com.example.roomradar.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.widget.ListView;

import com.example.roomradar.R;
import com.example.roomradar.adapter.PostAdapter;
import com.example.roomradar.adapter.PostAdapterListView;
import com.example.roomradar.Database.entity.Post;
import com.example.roomradar.service.PostService;

import java.util.*;

public class ListPostOfUser extends AppCompatActivity {

    List<Post> posts = new ArrayList<>();
    ListView lv_posts;
    PostAdapterListView adapter;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_post_of_user);
        PostService postService = PostService.getInstance(ListPostOfUser.this);

        lv_posts = findViewById(R.id.lv_posts);
        posts = postService.getAllPost();
        adapter = new PostAdapterListView(ListPostOfUser.this, R.layout.item_post_of_user, posts);
        lv_posts.setAdapter(adapter);
    }
}