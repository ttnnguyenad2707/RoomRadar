package com.example.roomradar.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import com.example.roomradar.Database.entity.Post;
import com.example.roomradar.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class DetailsPostActivity extends AppCompatActivity {
    String DB_PATH_SUFFIX = "/databases/";
    SQLiteDatabase database=null;
    String DATABASE_NAME="roomradar.db";


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
        TextView  interiordetail = findViewById(R.id.interior_detailPost);
        TextView  ownerdetail = findViewById(R.id.owner_detailPost);
        TextView  creatAtdetail = findViewById(R.id.createdAt_detailPost);
        TextView  phonedetail = findViewById(R.id.phone_detailPost);
        TextView addressdetail = findViewById(R.id.address_detailPost);

        ListView lv_sercurity = findViewById(R.id.list_security);
        ListView lv_utils = findViewById(R.id.list_utils);

        /////////////////////////////////////////////////
        Picasso.get().load(post.getThumbnail()).into(imageView);


        titledetail.setText(post.getTitle());
        descriptiondetail.setText(post.getDescription());
        pricedetail.setText(String.valueOf(post.getPrice()));
        areadetail.setText(String.valueOf(post.getArea()));
        depositdetail.setText(String.valueOf(post.getDeposit()));

        openDatabase();
        ArrayList<String> security = querySecurityFromDatabase(post.getId());
        ArrayAdapter<String> adapterCommon = new ArrayAdapter<String>(DetailsPostActivity.this,android.R.layout.simple_list_item_1,security);
        lv_sercurity.setAdapter(adapterCommon);
//
        int maxPeople = post.getMaxPeople();
        maxpeopledetail.setText(String.valueOf(maxPeople));
//
//        ArrayList<String> utilsList = post.getUtils();
//        String utilsText = TextUtils.join(", ", utilsList);
//        utilsdetail.setText(utilsText);
//        LocalDateTime createdAt = post.getCreatedAt();
//        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
//        String createdAtString = createdAt.format(formatter);
//        creatAtdetail.setText(createdAtString);
//
        addressdetail.setText(post.getAddress());
        btnBackToList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
    private String getDatabasePath() {
        return getApplicationInfo().dataDir + DB_PATH_SUFFIX+ DATABASE_NAME;
    }
    private void openDatabase() {
        String dbPath = getDatabasePath(DATABASE_NAME).getAbsolutePath();
        database = SQLiteDatabase.openDatabase(dbPath, null, SQLiteDatabase.OPEN_READONLY);
    }

    private ArrayList<String> querySecurityFromDatabase(int id) {
        ArrayList<String> security = new ArrayList<>();
        if (database != null) {
            Cursor cursor = database.rawQuery("SELECT Post.id AS post_id, Post.title, PostSecurity.security_id, Security.name AS security_name FROM Post INNER JOIN PostSecurity ON Post.id = PostSecurity.post_id INNER JOIN Security ON PostSecurity.security_id = Security.id WHERE post_id ="+id, null);
            if (cursor != null) {


                while (cursor.moveToNext()) {
                    String security_name = cursor.getString(3);
                    security.add(security_name);
                }
                cursor.close();
            }
        }
        return security;
    }
}