package com.example.roomradar.Fragments;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.roomradar.Activity.DetailsPostActivity;
import com.example.roomradar.Database.entity.Category;
import com.example.roomradar.R;
import com.example.roomradar.adapter.CategoryButtonAdapter;
import com.example.roomradar.adapter.PostAdapter;
import com.example.roomradar.Database.entity.Post;
import com.example.roomradar.service.CategoryService;
import com.example.roomradar.service.PostService;

import org.checkerframework.checker.units.qual.C;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link HomeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HomeFragment extends Fragment {

    PostAdapter postAdapter;
    GridView gridView;

    String DB_PATH_SUFFIX = "/databases/";
    SQLiteDatabase database=null;
    String DATABASE_NAME="roomradar.db";

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
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
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        View view = inflater.inflate(R.layout.fragment_home, container, false);
//
//        // Bước 2: Mở cơ sở dữ liệu và truy vấn dữ liệu



        // Bước 3: Hiển thị dữ liệu lên giao diện
        PostService postService = PostService.getInstance(getContext());
        List<Post> posts = postService.getAllPost();
        gridView = view.findViewById(R.id.newPostView);
        postAdapter = new PostAdapter(getActivity(), R.layout.item_post_layout, posts);
        gridView.setAdapter(postAdapter);
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getContext(), DetailsPostActivity.class);
                intent.putExtra("post",posts.get(position));
                startActivity(intent);
            }
        });

        CategoryService categoryService = CategoryService.getInstance(getContext());
        List<Category> categoryList = categoryService.getAllCategory();
        CategoryButtonAdapter categoryButtonAdapter = new CategoryButtonAdapter(getActivity(),R.layout.item_category_button,categoryList);
        GridView listCategory = view.findViewById(R.id.listCategory);
        listCategory.setAdapter(categoryButtonAdapter);


        return view;
    }



}