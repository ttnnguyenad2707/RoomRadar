package com.example.roomradar;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.roomradar.adapter.PostAdapter;
import com.example.roomradar.model.Post;
import com.example.roomradar.model.User;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link HomeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HomeFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private Gson gson = new Gson();
    String api = "https://roomradar-v2.onrender.com/api/v1/post";
    private RequestQueue requestQueue;

    PostAdapter postAdapter;
    GridView gridView;
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public HomeFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment HomeFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static HomeFragment newInstance(String param1, String param2) {
        HomeFragment fragment = new HomeFragment();
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

        ActionBar actionBar = ((AppCompatActivity) getActivity()).getSupportActionBar();
        if (actionBar != null) {
            // Thực hiện các tùy chỉnh cho thanh tiêu đề
            actionBar.setTitle("Ứng dụng của tôi");
            actionBar.setSubtitle("Mô tả ứng dụng");
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        // Trong onCreateView() của Fragment
        View view = inflater.inflate(R.layout.activity_main, container, false);

// Tìm kiếm và thiết lập OnClickListener cho ImageView
        view.findViewById(R.id.img_search).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), AddPostActivity.class);
                startActivity(intent);
            }
        });

// Tìm kiếm và thiết lập OnClickListener cho Button
        view.findViewById(R.id.btn_profile).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), LoginActivity.class);
                startActivity(intent);
            }
        });
        // Trong onCreateView() của Fragment
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        String jsonUser = sharedPreferences.getString("username", ""); // Trả về "" nếu không tìm thấy giá trị

        if (!jsonUser.isEmpty()) {
            // Người dùng đã đăng nhập trước đó, thực hiện các hành động tương ứng
            User savedObj = gson.fromJson(jsonUser, User.class);
            gridView = view.findViewById(R.id.newPostView);
            // Trong onCreateView() của Fragment
            requestQueue = Volley.newRequestQueue(getActivity());
            sendApiRequest(new MainActivity.ApiResponseListener<List<Post>>() {
                @Override
                public void onSuccess(List<Post> response) {
                    // Xử lý danh sách posts tại đây
                    postAdapter = new PostAdapter(getActivity(), R.layout.item_post_layout, response);
                    gridView.setAdapter(postAdapter);
                    gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            Intent intent = new Intent(getActivity(), DetailsPostActivity.class);
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
            Intent intent = new Intent(getActivity(), LoginActivity.class);
            startActivity(intent);
        }
        return view;

    }

    private void sendApiRequest(final MainActivity.ApiResponseListener<List<Post>> listener) {
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
                                    }
                                    else {
                                        post.setSecurity(getSecurityFromJsonArray(securityArray));
                                    }
                                }
                                if (jsonObject.isNull("utils")) {
                                    post.setUtils((new ArrayList<>()));
                                } else {
                                    JSONArray utilsArray = jsonObject.getJSONArray("utils");
                                    if (utilsArray.length() == 0) {
                                        post.setUtils((new ArrayList<>()));
                                    }
                                    else {
                                        post.setUtils(getUtilsFromJsonArray(utilsArray));
                                    }
                                }

                                if (jsonObject.isNull("interior")) {
                                    post.setInterior((new ArrayList<>()));
                                } else {
                                    JSONArray interiorArray = jsonObject.getJSONArray("interior");
                                    if (interiorArray.length() == 0) {
                                        post.setInterior((new ArrayList<>()));
                                    }
                                    else {
                                        post.setInterior(getInteriorFromJsonArray(interiorArray));
                                    }
                                }

                                if (jsonObject.isNull("images")) {
                                    post.setImage(new String[0]);
                                } else {
                                    JSONArray imageArray = jsonObject.getJSONArray("images");
                                    if (imageArray.length() == 0) {
                                        post.setImage(new String[0]);
                                    }
                                    else {
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

    /**
     * A simple {@link Fragment} subclass.
     * Use the {@link CreatePostFragment#newInstance} factory method to
     * create an instance of this fragment.
     */
    public static class CreatePostFragment extends Fragment {

        // TODO: Rename parameter arguments, choose names that match
        // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
        private static final String ARG_PARAM1 = "param1";
        private static final String ARG_PARAM2 = "param2";

        // TODO: Rename and change types of parameters
        private String mParam1;
        private String mParam2;

        public CreatePostFragment() {
            // Required empty public constructor
        }

        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment CreatePostFragment.
         */
        // TODO: Rename and change types and number of parameters
        public static CreatePostFragment newInstance(String param1, String param2) {
            CreatePostFragment fragment = new CreatePostFragment();
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

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            // Inflate the layout for this fragment
            return inflater.inflate(R.layout.fragment_create_post, container, false);
        }
    }
}