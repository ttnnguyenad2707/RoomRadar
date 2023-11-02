package com.example.roomradar;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;

import com.example.roomradar.adapter.PostAdapter;
import com.example.roomradar.model.Post;
import com.example.roomradar.model.User;
import com.google.gson.Gson;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private Gson gson = new Gson();
    String api = "https://roomradar.onrender.com/api/v1/post/getAll/1";
    private RequestQueue requestQueue;

    PostAdapter postAdapter;
    GridView gridView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            // Thực hiện các tùy chỉnh cho thanh tiêu đề
            actionBar.setTitle("Ứng dụng của tôi");
            actionBar.setSubtitle("Mô tả ứng dụng");
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        findViewById(R.id.img_search).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,AddPostActivity.class);
                startActivity(intent);
            }
        });
        findViewById(R.id.btn_profile).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,LoginActivity.class);
                startActivity(intent);
            }
        });

        SharedPreferences sharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        String jsonUser = sharedPreferences.getString("username", ""); // Trả về "" nếu không tìm thấy giá trị

        if (!jsonUser.isEmpty()) {
            // Người dùng đã đăng nhập trước đó, thực hiện các hành động tương ứng
            User savedObj = gson.fromJson(jsonUser, User.class);
            gridView = findViewById(R.id.newPostView);
            requestQueue = Volley.newRequestQueue(MainActivity.this);
            sendApiRequest(new ApiResponseListener<List<Post>>() {
                @Override
                public void onSuccess(List<Post> response) {
                    // Xử lý danh sách posts tại đây
                    postAdapter = new PostAdapter(MainActivity.this, R.layout.item_post_layout, response);
                    gridView.setAdapter(postAdapter);
                    gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            Intent intent = new Intent(MainActivity.this, DetailsPostActivity.class);
                            intent.putExtra("post", response.get(position));
                            startActivity(intent);
                        }
                    });
                }

                @Override
                public void onError(Exception error) {
                    // Xử lý lỗi tại đây
                }
            });
        } else {
            // Người dùng chưa đăng nhập, hiển thị màn hình đăng nhập hoặc các tùy chọn khác
            Intent intent = new Intent(MainActivity.this, LoginActivity.class);
            startActivity(intent);
        }
//        Intent intent = getIntent();
//        User user = (User) intent.getSerializableExtra("user");


    }

        private void sendApiRequest(final ApiResponseListener<List<Post>> listener) {
            Log.d("call", "API");
            JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, api, null,
                    new Response.Listener<JSONArray>() {
                        @Override
                        public void onResponse(JSONArray response) {
                            try {
                                List<Post> posts = new ArrayList<>();

                                for (int i = 0; i < response.length(); i++) {
                                    JSONObject jsonObject = response.getJSONObject(i);
                                    Post post = new Post();

                                    post.setTitle(jsonObject.optString("title"));
                                    post.setAddress(jsonObject.optString("address"));
                                    post.setPrice(jsonObject.optString("price"));
                                    post.setDescription(jsonObject.optString("description"));
                                    post.setOwner(jsonObject.optString("owner"));
                                    post.setArea(jsonObject.optString("area"));
//                                    post.setPhone(jsonObject.optString("phone"));
                                    post.setMaxPeople(jsonObject.optInt("maxPeople"));
                                    post.setDeposit(jsonObject.optString("deposit"));
//                                    post.setCreatedAt(jsonObject.optString("createdAt"));
                                    String createdAtString = jsonObject.optString("createdAt");
                                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
                                    LocalDateTime createdAtDateTime = LocalDateTime.parse(createdAtString, formatter);
                                    post.setCreatedAt(createdAtDateTime);
                                    if (jsonObject.isNull("security")) {
                                        post.setSecurity((new ArrayList<>()));
                                    } else {
                                        JSONArray securityArray = jsonObject.getJSONArray("security");
                                        if (securityArray.length() == 0) {
                                            post.setSecurity((new ArrayList<>()));
                                        } else {
                                            post.setSecurity(getSecurityFromJsonArray(securityArray));
                                        }
                                    }
                                    if (jsonObject.isNull("utils")) {
                                        post.setUtils((new ArrayList<>()));
                                    } else {
                                        JSONArray utilsArray = jsonObject.getJSONArray("utils");
                                        if (utilsArray.length() == 0) {
                                            post.setUtils((new ArrayList<>()));
                                        } else {
                                            post.setUtils(getUtilsFromJsonArray(utilsArray));
                                        }
                                    }

                                    if (jsonObject.isNull("interior")) {
                                        post.setInterior((new ArrayList<>()));
                                    } else {
                                        JSONArray interiorArray = jsonObject.getJSONArray("interior");
                                        if (interiorArray.length() == 0) {
                                            post.setInterior((new ArrayList<>()));
                                        } else {
                                            post.setInterior(getInteriorFromJsonArray(interiorArray));
                                        }
                                    }

                                    if (jsonObject.isNull("images")) {
                                        post.setImage(new String[0]);
                                    } else {
                                        JSONArray imageArray = jsonObject.getJSONArray("images");
                                        if (imageArray.length() == 0) {
                                            post.setImage(new String[0]);
                                        } else {
                                            post.setImage(getImageListFromJsonArray(imageArray));
                                        }
                                    }

                                    Log.d("post", post.toString());
                                    posts.add(post);
                                }

                                // Gọi callback và truyền danh sách posts về
                                listener.onSuccess(posts);

                            } catch (JSONException e) {
                                // Gọi callback và truyền lỗi về
                                listener.onError(e);
                            }
                        }
                    },
                    new Response.ErrorListener() {
                        public void onErrorResponse(VolleyError error) {
                            // Gọi callback và truyền lỗi về
                            listener.onError(error);
                        }
                    });

            requestQueue.add(jsonArrayRequest);
        }

    private String[] getImageListFromJsonArray(JSONArray jsonArray) throws JSONException {
        String[] imageArray = new String[jsonArray.length()];
        for (int i = 0; i < jsonArray.length(); i++) {
            String image = jsonArray.getString(i);
            imageArray[i] = image;
        }
        return imageArray;
    }
    private ArrayList<String> getSecurityFromJsonArray(JSONArray jsonArray) {
        ArrayList<String> securityList = new ArrayList<>();

        try {
            for (int i = 0; i < jsonArray.length(); i++) {
                String securityItem = jsonArray.getString(i);
                securityList.add(securityItem);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return securityList;
    }
    private ArrayList<String> getUtilsFromJsonArray(JSONArray jsonArray) {
        ArrayList<String> utilsList = new ArrayList<>();

        try {
            for (int i = 0; i < jsonArray.length(); i++) {
                String utilItem = jsonArray.getString(i);
                utilsList.add(utilItem);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return utilsList;
    }
    private ArrayList<String> getInteriorFromJsonArray(JSONArray jsonArray) {
        ArrayList<String> interiorList = new ArrayList<>();

        try {
            for (int i = 0; i < jsonArray.length(); i++) {
                String interiorItem = jsonArray.getString(i);
                interiorList.add(interiorItem);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return interiorList;
    }



    interface ApiResponseListener<T> {
        void onSuccess(T response);

        void onError(Exception error);
    }
}