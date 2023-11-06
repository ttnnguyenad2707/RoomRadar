package com.example.roomradar;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import com.example.roomradar.model.Post;
import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class DetailsPostActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details_post);
        Intent intent  = getIntent();
        Post post = (Post) intent.getSerializableExtra("post");
        ImageView imageView = findViewById(R.id.post_detail_images);
        Button btnBackToList = findViewById(R.id.btnBackToList);
        TextView titledetail = findViewById(R.id.title_detailPost);
        TextView descriptiondetail = findViewById(R.id.description_detailPost);
        TextView pricedetail = findViewById(R.id.price_detailPost);
        TextView areadetail = findViewById(R.id.area_detailPost);
        TextView maxpeopledetail = findViewById(R.id.maxPeople_detailPost);
        TextView depositdetail = findViewById(R.id.deposit_detailPost);
        TextView categorydetail = findViewById(R.id.category_detailPost);
        TextView  securitydetail = findViewById(R.id.security_detailPost);
        TextView  utilsdetail = findViewById(R.id.utils_detailPost);
        TextView  interiordetail = findViewById(R.id.interior_detailPost);
        TextView  ownerdetail = findViewById(R.id.owner_detailPost);
        TextView  creatAtdetail = findViewById(R.id.createdAt_detailPost);
        TextView  phonedetail = findViewById(R.id.phone_detailPost);
        TextView addressdetail = findViewById(R.id.address_detailPost);

/////////////////////////////////////////////////
//        Picasso.get().load((post.getImage())[0]).into(imageView);

//        titledetail.setText(post.getTitle());
//        descriptiondetail.setText(post.getDescription());
//        pricedetail.setText(post.getPrice());
//        areadetail.setText(post.getArea());
//        depositdetail.setText(post.getDeposit());
//
//        ArrayList<String> securityList = post.getSecurity();
//        String securityText = TextUtils.join(", ", securityList);
//        securitydetail.setText(securityText);
//
//        int maxPeople = post.getMaxPeople();
//        maxpeopledetail.setText(String.valueOf(maxPeople));
//
//        ArrayList<String> utilsList = post.getUtils();
//        String utilsText = TextUtils.join(", ", utilsList);
//        utilsdetail.setText(utilsText);
//
//        ArrayList<String> interiorList = post.getInterior();
//        String interiorText = TextUtils.join(", ", interiorList);
//        interiordetail.setText(interiorText);
//
//
//        LocalDateTime createdAt = post.getCreatedAt();
//        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
//        String createdAtString = createdAt.format(formatter);
//        creatAtdetail.setText(createdAtString);
//
//        addressdetail.setText(post.getAddress());
//        btnBackToList.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                finish();
//            }
//        });
    }
}