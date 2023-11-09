package com.example.roomradar.Activity;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.cloudinary.Configuration;
import com.cloudinary.android.MediaManager;
import com.cloudinary.android.callback.ErrorInfo;
import com.cloudinary.android.callback.UploadCallback;
import com.example.roomradar.Database.entity.Category;
import com.example.roomradar.Database.entity.Images;
import com.example.roomradar.Database.entity.Post;
import com.example.roomradar.Database.entity.Security;
import com.example.roomradar.Database.entity.Utils;
import com.example.roomradar.R;
import com.example.roomradar.StringConverter;
import com.example.roomradar.adapter.CategoryRadioAdapter;
import com.example.roomradar.adapter.ImageAdapter;
import com.example.roomradar.adapter.SecurityCheckBoxAdapter;
import com.example.roomradar.adapter.UtilsCheckboxAdapter;
import com.example.roomradar.service.CategoryService;
import com.example.roomradar.service.ImagesService;
import com.example.roomradar.service.PostService;
import com.example.roomradar.service.SecurityService;
import com.example.roomradar.service.UtilsService;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EditPostActivity extends AppCompatActivity {

    String imageURL;
    int id = -99;
    Boolean isDoneUploadImage = true, isUploadThumbnail = true, isDoneUploadThumbnail = true;
    Uri uri;
    ListView lv_images;
    ListView lv_category;
    ListView lv_security;
    ListView lv_utils;
    EditText edt_title;
    EditText edt_description;
    EditText edt_address;
    EditText edt_area;
    EditText edt_maxPeople;
    EditText edt_price;
    EditText edt_deposit;
    ImageView thumbnail;
    Button submitSubton, btn_edit_thumbnail, btn_edit_img;
    CategoryRadioAdapter categoryRadioAdapter;
    SecurityCheckBoxAdapter securityCheckBoxAdapter;
    UtilsCheckboxAdapter utilsCheckboxAdapter;
    ImageAdapter imageAdapter;
    ImagesService imagesService;
    List<Images> images = new ArrayList<>();
    List<Images> newImages = new ArrayList<>();
    List<Images> updateImages = new ArrayList<>();
    Post post = new Post();
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_post);

        Intent intent = getIntent();
        post = (Post) intent.getSerializableExtra("post");

        CategoryService categoryService = CategoryService.getInstance(EditPostActivity.this);
        UtilsService utilService = UtilsService.getInstance(EditPostActivity.this);
        SecurityService securityService = SecurityService.getInstance(EditPostActivity.this);
        imagesService = ImagesService.getInstance(this);

        List<Category> categoryList = categoryService.getAllCategory();
        List<Utils> utilsList = utilService.getAllUtils();
        List<Security> securityList = securityService.getAllSecurity();
        images = imagesService.getImagesByPost(post.getId());

        lv_images = findViewById(R.id.edit_preview_images);
        lv_category = findViewById(R.id.editPost_lv_category);
        lv_security = findViewById(R.id.editPost_lv_security);
        lv_utils = findViewById(R.id.editPost_lv_utils);
        edt_title = findViewById(R.id.edit_title);
        edt_description = findViewById(R.id.edit_description);
        edt_address = findViewById(R.id.edit_address);
        edt_area = findViewById(R.id.edit_area);
        edt_maxPeople = findViewById(R.id.edit_max_people);
        edt_price = findViewById(R.id.edit_price);
        edt_deposit = findViewById(R.id.edit_desposit);
        thumbnail = findViewById(R.id.edit_preview_image);
        btn_edit_thumbnail = findViewById(R.id.btn_edit_up_thumbnails);
        btn_edit_img = findViewById(R.id.btn_edit_up_images);
        submitSubton = findViewById(R.id.edit_submit);

        categoryRadioAdapter = new CategoryRadioAdapter(EditPostActivity.this, R.layout.item_check_box, categoryList);
        categoryRadioAdapter.setCategoryId(post.getCategory());
        lv_category.setAdapter(categoryRadioAdapter);
        securityCheckBoxAdapter = new SecurityCheckBoxAdapter(EditPostActivity.this, R.layout.item_check_box, securityList);
        securityCheckBoxAdapter.setSecurityId(StringConverter.convertStringToList(post.getSecurity()));
        lv_security.setAdapter(securityCheckBoxAdapter);
        utilsCheckboxAdapter = new UtilsCheckboxAdapter(EditPostActivity.this, R.layout.item_check_box, utilsList);
        utilsCheckboxAdapter.setUtilsId(StringConverter.convertStringToList(post.getUtils()));
        lv_utils.setAdapter(utilsCheckboxAdapter);
        imageAdapter = new ImageAdapter(this,R.layout.item_image_list,images);
        lv_images.setAdapter(imageAdapter);

        edt_title.setText(post.getTitle());
        edt_address.setText(post.getAddress());
        edt_area.setText(post.getArea()+"");
        edt_description.setText(post.getDescription());
        edt_maxPeople.setText(post.getMaxPeople()+"");
        edt_price.setText(post.getPrice()+"");
        edt_deposit.setText(post.getDeposit()+"");
        Picasso.get().load(post.getThumbnail()).into(thumbnail);

        ActivityResultLauncher<Intent> activityResultLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                new ActivityResultCallback<ActivityResult>() {
                    @Override
                    public void onActivityResult(ActivityResult result) {
                        if (result.getResultCode() == Activity.RESULT_OK){
                            Intent data = result.getData();
                            uri = data.getData();
                            if(isUploadThumbnail)
                                thumbnail.setImageURI(uri);
                            MediaManager.get().upload(uri)
                                .unsigned("PRM392") // Thay "your_unsigned_preset" bằng unsigned preset của bạn
                                .callback(new UploadCallback() {
                                    @Override
                                    public void onStart(String requestId) {
                                        // Xử lý khi bắt đầu upload
                                    }

                                    @Override
                                    public void onProgress(String requestId, long bytes, long totalBytes) {
                                        // Xử lý khi upload đang tiến hành

                                    }

                                    @Override
                                    public void onSuccess(String requestId, Map resultData) {
                                        // Xử lý khi upload thành công

                                        String publicId = (String) resultData.get("public_id");
                                        String url = (String) resultData.get("url");
                                        if(isUploadThumbnail){
                                            imageURL = url;
                                            isDoneUploadThumbnail = true;
                                        }else{
                                            updateImageList(url);
                                        }
                                    }

                                    @Override
                                    public void onError(String requestId, ErrorInfo error) {
                                        // Xử lý khi xảy ra lỗi
                                    }

                                    @Override
                                    public void onReschedule(String requestId, ErrorInfo error) {
                                        // Xử lý khi cần reschedule upload
                                    }
                                }).dispatch();
                        } else {
                            Toast.makeText(EditPostActivity.this, "No Image Selected", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
        );

        btn_edit_thumbnail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent photoPicker = new Intent(Intent.ACTION_PICK);
                photoPicker.setType("image/*");
                activityResultLauncher.launch(photoPicker);
                isDoneUploadThumbnail = false;
                isUploadThumbnail = true;
            }
        });

        btn_edit_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent photoPicker = new Intent(Intent.ACTION_PICK);
                photoPicker.setType("image/*");
                activityResultLauncher.launch(photoPicker);
                isDoneUploadImage = false;
                isUploadThumbnail = false;
            }
        });

        submitSubton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!isDoneUploadImage || !isDoneUploadThumbnail){
                    Toast.makeText(EditPostActivity.this, "Please wait to upload images...", Toast.LENGTH_SHORT).show();
                }else{
                    editPost();
                }
            }
        });

    }

    private void updateImageList(String url) {
        Images selectedImg = imageAdapter.getSelectedImage();
        if(selectedImg == null){
            Images img = new Images(url, post.getId());
            img.setId(id++);
            images.add(img);
        }
        else{
            for (int i = 0; i <images.size(); i++) {
                if(images.get(i).getId() == selectedImg.getId()){
                    images.get(i).setUrl(url);
                    break;
                }
            }
        }
        imageAdapter.notifyDataSetChanged();
        isDoneUploadImage = true;
    }

    private void editPost() {
        List<Integer> securityId = securityCheckBoxAdapter.getSecurityId();
        List<Integer> utils = utilsCheckboxAdapter.getUtilsId();
        int categoryId = categoryRadioAdapter.getCategoryId();

        String title = edt_title.getText().toString();
        String description = edt_description.getText().toString();
        String address = edt_address.getText().toString();
        float area = Float.parseFloat(edt_area.getText().toString());
        int maxPeople = Integer.parseInt(edt_maxPeople.getText().toString());
        float price = Float.valueOf(edt_price.getText().toString());
        float deposit = Float.valueOf(edt_deposit.getText().toString());
        Date currentTime = new Date();

        // Define a format for the time as a string
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        // Format the current time as a string
        String currentTimeString = dateFormat.format(currentTime);

        SharedPreferences sharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        int ownerId = sharedPreferences.getInt("User",0);
        Post editPost = new Post(title, description, address, area, maxPeople, price, deposit, ownerId, currentTimeString, categoryId, imageURL, securityId.toString(), utils.toString());
        editPost.setId(post.getId());
        PostService postService = PostService.getInstance(EditPostActivity.this);
        postService.updatePost(editPost);

        for (Images img : images) {
            if(img.getId() < 0){
                img = new Images(img.getUrl(), img.getPostId());
                imagesService.addNewImages(img);
            }
            else{
                imagesService.updateImage(img);
            }
        }


        Intent intent = new Intent(EditPostActivity.this, ListPostOfUser.class);
        startActivity(intent);
    }
}