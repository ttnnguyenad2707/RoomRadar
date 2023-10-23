package com.example.roomradar;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    String api = "https://roomradar.onrender.com/api/v1/post/getAll/1";
    private RequestQueue requestQueue;

    PostAdapter postAdapter;
    GridView gridView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViewById(R.id.btn_profile).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,LoginActivity.class);
                startActivity(intent);
            }
        });

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
                        Intent intent = new Intent(MainActivity.this, PostDetails.class);
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

    interface ApiResponseListener<T> {
        void onSuccess(T response);

        void onError(Exception error);
    }
}