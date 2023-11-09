package com.example.roomradar.Fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.example.roomradar.Database.entity.Post;
import com.example.roomradar.Database.entity.PostLikedByUser;
import com.example.roomradar.R;
import com.example.roomradar.adapter.PostAdapter;
import com.example.roomradar.service.PostLikeByUserService;
import com.example.roomradar.service.PostService;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FavoritePostFrament#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FavoritePostFrament extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public FavoritePostFrament() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment FavoritePostFrament.
     */
    // TODO: Rename and change types and number of parameters
    public static FavoritePostFrament newInstance(String param1, String param2) {
        FavoritePostFrament fragment = new FavoritePostFrament();
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
        SharedPreferences sharedPreferences = getContext().getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        int userId = sharedPreferences.getInt("User",0);
        // Bước 3: Hiển thị dữ liệu lên giao diện
        View view = inflater.inflate(R.layout.fragment_favorite_post_frament, container, false);

        PostLikeByUserService postlikeService = PostLikeByUserService.getInstance(getContext());
        PostService postService = PostService.getInstance(getContext());
        List<PostLikedByUser> postsLiked = postlikeService.getFavoriteByUser(userId);
        List<Post> posts = new ArrayList<>();
        for (PostLikedByUser p: postsLiked ) {
            if(p.getUser_id() == userId ){
                posts.add(postService.getPostById(p.getPost_id()));
            }
        }

        ListView listview = view.findViewById(R.id.listPostLikedFG) ;
        PostAdapter postAdapter = new PostAdapter(getActivity(), R.layout.item_post_layout, posts);
        listview.setAdapter(postAdapter);

        return view;
    }
}