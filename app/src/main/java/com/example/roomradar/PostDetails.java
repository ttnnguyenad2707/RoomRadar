package com.example.roomradar;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

public class PostDetails extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_details);
        Intent intent  = getIntent();

        Post post = (Post) intent.getSerializableExtra("post");
        ImageView imageView = findViewById(R.id.post_detail_image);
        TextView postDetailsTitle = findViewById(R.id.post_detail_title);

        Picasso.get().load((post.getImage())[0]).into(imageView);
        postDetailsTitle.setText(post.getTitle());

        findViewById(R.id.btn_back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}