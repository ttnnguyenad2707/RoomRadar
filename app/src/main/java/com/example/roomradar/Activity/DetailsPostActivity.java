package com.example.roomradar.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.roomradar.Database.entity.Images;
import com.example.roomradar.Database.entity.Post;
import com.example.roomradar.Database.entity.Security;
import com.example.roomradar.R;
import com.example.roomradar.StringConverter;
import com.example.roomradar.adapter.ImageAdapter;
import com.example.roomradar.service.ImagesService;
import com.example.roomradar.service.SecurityService;
import com.example.roomradar.service.UserService;
import com.example.roomradar.service.UtilsService;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class DetailsPostActivity extends AppCompatActivity {
    String DB_PATH_SUFFIX = "/databases/";
    SQLiteDatabase database = null;
    String DATABASE_NAME = "roomradar.db";
    private Geocoder geocoder;

    String address;
    private int type = GoogleMap.MAP_TYPE_NORMAL;
    private GoogleMap mMap;
    private Marker currentMarker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details_post);
        Intent intent = getIntent();
        Post post = (Post) intent.getSerializableExtra("post");

        geocoder = new Geocoder(this);
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.maps);
        mapFragment.getMapAsync(this::onMapReady);

        ImageView imageView = findViewById(R.id.post_detail_images);
        Button btnBackToList = findViewById(R.id.btnBackToList);
        TextView titledetail = findViewById(R.id.title_detailPost);
        TextView descriptiondetail = findViewById(R.id.description_detailPost);
        TextView pricedetail = findViewById(R.id.price_detailPost);
        TextView areadetail = findViewById(R.id.area_detailPost);
        TextView maxpeopledetail = findViewById(R.id.maxPeople_detailPost);
        TextView depositdetail = findViewById(R.id.deposit_detailPost);
        TextView categorydetail = findViewById(R.id.category_detailPost);

        TextView ownerdetail = findViewById(R.id.owner_detailPost);
        TextView creatAtdetail = findViewById(R.id.createdAt_detailPost);
        TextView phonedetail = findViewById(R.id.phone_detailPost);
        TextView addressdetail = findViewById(R.id.address_detailPost);


        ListView lv_sercurity = findViewById(R.id.list_security);
        ListView lv_utils = findViewById(R.id.list_utils);
        GridView lv_images = findViewById(R.id.grid_view_image);
/////////////////////////////////////////
        List<Integer> security = StringConverter.convertStringToList(post.getSecurity()); // convert String "[1,2,3]" => array [1,2,3]
        List<String> securityName = new ArrayList<>();
        SecurityService securityService = SecurityService.getInstance(DetailsPostActivity.this);
        for (Integer i : security) {
            securityName.add(securityService.getSecurityById(i).getName());
        }
        ArrayAdapter<String> adapterCommon = new ArrayAdapter<String>(DetailsPostActivity.this, android.R.layout.simple_list_item_1, securityName);
        lv_sercurity.setAdapter(adapterCommon);
///////////////////////////////
        List<Integer> utils = StringConverter.convertStringToList(post.getUtils()); // convert String "[1,2,3]" => array [1,2,3]
        List<String> utilsName = new ArrayList<>();
        UtilsService utilsService = UtilsService.getInstance(DetailsPostActivity.this);
        for (Integer i : utils) {
           utilsName.add(utilsService.getUtilsById(i).getName());
        }
        ArrayAdapter<String> adapterCommon1 = new ArrayAdapter<String>(DetailsPostActivity.this, android.R.layout.simple_list_item_1, utilsName);
        lv_utils.setAdapter(adapterCommon1);
        ////////////////////////////////
       int userId = post.getOwner();
        String lastName = UserService.getInstance(this).getUserLastName(userId);
        String phone = UserService.getInstance(this).getUserPhone(userId);

        /////////////////////////
        ImagesService imagesService = ImagesService.getInstance(this);
        List<Images> images = imagesService.getImagesByPost(post.getId());

//        ArrayAdapter<String> adapterString = new ArrayAdapter<String>(DetailsPostActivity.this, android.R.layout.simple_list_item_1,listUrl);
        ImageAdapter imageAdapter = new ImageAdapter(this,R.layout.item_image_list,images);
        lv_images.setAdapter(imageAdapter);


        Picasso.get().load(post.getThumbnail()).into(imageView);
        titledetail.setText(post.getTitle());
        descriptiondetail.setText(post.getDescription());
        pricedetail.setText(String.valueOf(post.getPrice()));
        areadetail.setText(String.valueOf(post.getArea()));
        creatAtdetail.setText(post.getCreated());
        ownerdetail.setText(lastName);
        phonedetail.setText(phone);
        depositdetail.setText(String.valueOf(post.getDeposit()));


        int maxPeople = post.getMaxPeople();
        maxpeopledetail.setText(String.valueOf(maxPeople));

        addressdetail.setText(post.getAddress());
        btnBackToList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void searchLocation(String address) {
        List<Address> addresses = null;
        LatLng latLng = null;
        try {
            addresses = geocoder.getFromLocationName(address, 1);
            if (addresses != null && addresses.size() > 0) {
                Address location = addresses.get(0);
                double latitude = location.getLatitude();
                double longitude = location.getLongitude();
                latLng = new LatLng(latitude, longitude);
            }
        } catch (IOException e) {
            e.printStackTrace();
            // Xử lý lỗi IO và hiển thị thông báo cho người dùng
            Toast.makeText(this, "Lỗi khi tìm địa chỉ.", Toast.LENGTH_SHORT).show();
        }

        if (latLng != null) {
            MarkerOptions markerOptions = new MarkerOptions()
                    .position(latLng)
                    .title("Vị trí")
                    .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED))
                    .snippet("Latitude: " + latLng.latitude + ", Longitude: " + latLng.longitude);


            mMap.addMarker(markerOptions);
            mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 15));
            mMap.setMapType(type);
        } else {
            // Xử lý trường hợp không tìm thấy địa chỉ
            Toast.makeText(this, "Không tìm thấy địa chỉ.", Toast.LENGTH_SHORT).show();
        }
    }
    public void onMapReady(@NonNull GoogleMap googleMap) {
        mMap = googleMap;
        Intent intent = getIntent();
        Post post = (Post) intent.getSerializableExtra("post");
        searchLocation(post.getAddress());
    }

}