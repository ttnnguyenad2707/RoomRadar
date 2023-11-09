package com.example.roomradar.Fragments;

import android.app.Activity;
import android.content.ClipData;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.cloudinary.Configuration;
import com.cloudinary.android.callback.ErrorInfo;
import com.cloudinary.android.callback.UploadCallback;
import com.example.roomradar.Activity.AddPostActivity;
import com.example.roomradar.Activity.DetailsPostActivity;
import com.example.roomradar.Database.entity.Category;
import com.example.roomradar.Database.entity.Images;
import com.example.roomradar.Database.entity.Post;
import com.example.roomradar.Database.entity.Security;
import com.example.roomradar.Database.entity.Utils;
import com.example.roomradar.R;
import com.example.roomradar.adapter.CategoryRadioAdapter;
import com.example.roomradar.adapter.ImageAdapter;
import com.example.roomradar.adapter.ImageUrlAdapter;
import com.example.roomradar.adapter.SecurityCheckBoxAdapter;
import com.example.roomradar.adapter.UtilsCheckboxAdapter;
import com.example.roomradar.service.CategoryService;
import com.example.roomradar.service.ImagesService;
import com.example.roomradar.service.PostService;
import com.example.roomradar.service.SecurityService;
import com.example.roomradar.service.UtilsService;
import com.cloudinary.android.MediaManager;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Addpostfragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Addpostfragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public Addpostfragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Addpostfragment.
     */
    // TODO: Rename and change types and number of parameters
    public static Addpostfragment newInstance(String param1, String param2) {
        Addpostfragment fragment = new Addpostfragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }
    boolean isDoneUploadThumbnails = false;
    boolean isDoneuploadListImage = false;
    String urlImage;
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
    List<String> uploadedImageUrls = new ArrayList<>();

    Button submitSubton;
    CategoryRadioAdapter categoryRadioAdapter;
    SecurityCheckBoxAdapter securityCheckBoxAdapter;
    UtilsCheckboxAdapter utilsCheckboxAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_addpostfragment, container, false);
//
        CategoryService categoryService = CategoryService.getInstance(getActivity().getApplicationContext());

        UtilsService utilService = UtilsService.getInstance(getActivity().getApplicationContext());
        SecurityService securityService = SecurityService.getInstance(getActivity().getApplicationContext());
        categoryService.insertCategory(new Category("Nhà Nguyên Căn"));
        utilService.addnewUtils(new Utils("Tu lanh"));
        securityService.addNewSecurity(new Security("PCCC"));

        List<Category> categoryList = categoryService.getAllCategory();
        List<Utils> utilsList = utilService.getAllUtils();
        List<Security> securityList = securityService.getAllSecurity();

        lv_images = view.findViewById(R.id.add_preview_images);
        lv_category = view.findViewById(R.id.addPost_lv_category);
        lv_security = view.findViewById(R.id.addPost_lv_security);
        lv_utils = view.findViewById(R.id.addPost_lv_utils);
        edt_title = view.findViewById(R.id.add_title);
        edt_description = view.findViewById(R.id.add_description);
        edt_address = view.findViewById(R.id.add_address);
        edt_area = view.findViewById(R.id.add_area);
        edt_maxPeople = view.findViewById(R.id.add_maxpeople);
        edt_price = view.findViewById(R.id.add_price);
        edt_deposit = view.findViewById(R.id.add_desposit);
        submitSubton = view.findViewById(R.id.add_submit);


        categoryRadioAdapter = new CategoryRadioAdapter(getActivity(), R.layout.item_check_box, categoryList);
        lv_category.setAdapter(categoryRadioAdapter);
        securityCheckBoxAdapter = new SecurityCheckBoxAdapter(getActivity(), R.layout.item_check_box, securityList);
        lv_security.setAdapter(securityCheckBoxAdapter);
        utilsCheckboxAdapter = new UtilsCheckboxAdapter(getActivity(), R.layout.item_check_box, utilsList);
        lv_utils.setAdapter(utilsCheckboxAdapter);

        view.findViewById(R.id.up_images).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType("image/*");
                intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
                startActivityForResult(intent, 99);
            }
        });

        view.findViewById(R.id.up_thumbnails).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent, 1);
            }
        });

        submitSubton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isDoneuploadListImage == false || isDoneUploadThumbnails == false){
                    Toast.makeText(getContext(), "Đang upload images ....", Toast.LENGTH_SHORT).show();
                }
                 else  {
                    createPost(urlImage,uploadedImageUrls);
                }

            }
        });



        return view;
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 99 && resultCode == Activity.RESULT_OK && data != null) {

            ClipData clipData = data.getClipData();
            List<Uri> selectedImageUris = new ArrayList<>();

            // Hiển thị ảnh đã chọn trong ImageView
            if (clipData != null) {
                // Nếu người dùng đã chọn nhiều hơn một ảnh
                int count = clipData.getItemCount();
                for (int i = 0; i < count; i++) {
                    Uri imageUri = clipData.getItemAt(i).getUri();
                    selectedImageUris.add(imageUri);
                }
            } else {
                // Nếu người dùng chỉ chọn một ảnh
                Uri imageUri = data.getData();
                selectedImageUris.add(imageUri);
            }
            ImageUrlAdapter imageUrlAdapter = new ImageUrlAdapter(getActivity(),R.layout.item_image_list,selectedImageUris);
            lv_images.setAdapter((imageUrlAdapter));

            for (Uri selectedImageUri : selectedImageUris) {
                MediaManager.get().upload(selectedImageUri)
                        .unsigned("PRM392") // Thay "your_unsigned_preset" bằng unsigned preset của bạn
                        .callback(new UploadCallback() {
                            @Override
                            public void onStart(String requestId) {
                                // Xử lý khi bắt đầu upload
                                Log.d("Start", "oke");

                            }

                            @Override
                            public void onProgress(String requestId, long bytes, long totalBytes) {
                                // Xử lý khi upload đang tiến hành
                                Log.d("onProgress", "oke");

                            }

                            @Override
                            public void onSuccess(String requestId, Map resultData) {
                                // Xử lý khi upload thành công
                                String publicId = (String) resultData.get("public_id");
                                String url = (String) resultData.get("url");
                                Log.d("onSuccess", "oke");

                                uploadedImageUrls.add(url);

                                // Kiểm tra nếu đã hoàn thành tải lên tất cả ảnh
                                if (uploadedImageUrls.size() == selectedImageUris.size()) {
                                    isDoneuploadListImage = true;
                                }
                            }

                            @Override
                            public void onError(String requestId, ErrorInfo error) {
                                Log.d("eror", "erro" + error.toString());
                                // Xử lý khi xảy ra lỗi
                            }

                            @Override
                            public void onReschedule(String requestId, ErrorInfo error) {
                                // Xử lý khi cần reschedule upload
                                Log.d("onReschedule", "oke");
                            }
                        }).dispatch();
            }
        } else if (requestCode == 1 && resultCode == Activity.RESULT_OK && data != null) {
            Uri selectedImageUri = data.getData();

            ImageView previewImageView = getView().findViewById(R.id.preview_image);
            Glide.with(this)
                    .load(selectedImageUri)
                    .into(previewImageView);

            MediaManager.get().upload(selectedImageUri)
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
                            urlImage = url;
                            isDoneUploadThumbnails = true;


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

        }
    }
    private void createPost(String url,List<String> images) {

        List<Integer> securityId = securityCheckBoxAdapter.getSecurityId();
        List<Integer> utils = utilsCheckboxAdapter.getUtilsId();
        int categoryId = categoryRadioAdapter.getCategoryId();

        String title = edt_title.getText().toString();
        String description = edt_description.getText().toString();
        String address = edt_address.getText().toString();
        int area = Integer.parseInt(edt_area.getText().toString());
        int maxPeople = Integer.parseInt(edt_maxPeople.getText().toString());
        float price = Float.valueOf(edt_price.getText().toString());
        float deposit = Float.valueOf(edt_deposit.getText().toString());
        Date currentTime = new Date();

        // Define a format for the time as a string
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        // Format the current time as a string
        String currentTimeString = dateFormat.format(currentTime);

        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        int ownerId = sharedPreferences.getInt("User",0);
        Post post = new Post(title, description, address, area, maxPeople, price, deposit, ownerId, currentTimeString, categoryId, url, securityId.toString(), utils.toString());



        PostService postService = PostService.getInstance(getContext());
        long postId = postService.addNewPost(post);
        ImagesService imagesService = ImagesService.getInstance(getContext());
        for (String s: images) {
            imagesService.addNewImages(new Images(s,(int) postId));
        }


        Intent intent = new Intent(getActivity(), DetailsPostActivity.class);
        intent.putExtra("post", post);
        startActivity(intent);
    }


}