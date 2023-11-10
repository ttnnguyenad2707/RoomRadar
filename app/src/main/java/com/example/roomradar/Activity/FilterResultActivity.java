package com.example.roomradar.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.roomradar.Database.entity.Post;
import com.example.roomradar.R;
import com.example.roomradar.adapter.PostAdapter;
import com.example.roomradar.service.PostService;

import java.util.List;

public class FilterResultActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filter_result);
        Intent intent = getIntent();
        int categoryId =(int) intent.getSerializableExtra("category");
        String categoryName = (String) intent.getSerializableExtra("categoryName");

        TextView categoryNameTextView = findViewById(R.id.categoryName);
        categoryNameTextView.setText(categoryName);

        PostService postService = PostService.getInstance(this);
        List<Post> postList = postService.getPostByCategory(categoryId);
        PostAdapter postAdapter = new PostAdapter(this,R.layout.item_post_layout,postList);

        ListView listFilterResult = findViewById(R.id.list_filter_result);
        listFilterResult.setAdapter(postAdapter);
        listFilterResult.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(FilterResultActivity.this, DetailsPostActivity.class);
                intent.putExtra("post",postList.get(position));
                startActivity(intent);
            }
        });


    }
}