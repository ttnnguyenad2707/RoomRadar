package com.example.roomradar.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.example.roomradar.R;
import com.example.roomradar.adapter.PostAdapterRecyclerView;
import com.example.roomradar.Database.entity.Post;
import java.util.*;

public class ListPostOfUser extends AppCompatActivity {

    List<Post> posts = new ArrayList<>();
    RecyclerView rec_posts;
    PostAdapterRecyclerView adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_post_of_user);


    }
}