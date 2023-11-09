package com.example.roomradar.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.example.roomradar.R;

public class LikePostListActivity extends AppCompatActivity {
    private RecyclerView rcvListLikePostData ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_like_post_list);
    }
}