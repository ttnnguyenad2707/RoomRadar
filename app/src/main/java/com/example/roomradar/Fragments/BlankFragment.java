package com.example.roomradar.Fragments;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.example.roomradar.Database.entity.Post;
import com.example.roomradar.Activity.DetailsPostActivity;
import com.example.roomradar.R;
import com.example.roomradar.adapter.PostAdapter;
import com.example.roomradar.service.PostService;

import java.util.ArrayList;
import java.util.List;

public class BlankFragment extends Fragment {

    PostAdapter postAdapter;
    GridView searchResultGridView;
    Button searchButton;  // Thêm nút searchButton

    List<Post> allPosts;
    List<Post> searchResults;

    // Database

    public BlankFragment() {
        // Required empty public constructor
    }

    public static BlankFragment newInstance() {
        return new BlankFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Initialize your lists, for example, from your database
        PostService postService = PostService.getInstance(getContext());
        allPosts = postService.getAllPost();
        searchResults = new ArrayList<>();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inside onCreateView method
        View view = inflater.inflate(R.layout.fragment_blank, container, false);

        searchResultGridView = view.findViewById(R.id.newPostView);
        searchButton = view.findViewById(R.id.searchButton);
        EditText searchEditText = view.findViewById(R.id.searchEditText);  // Add this line

// Set up GridView
        postAdapter = new PostAdapter(getActivity(), R.layout.item_post_layout, searchResults);
        searchResultGridView.setAdapter(postAdapter);

// Set up item click listener
        searchResultGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // Handle item click, for example, start DetailsPostActivity
                Intent intent = new Intent(getContext(), DetailsPostActivity.class);
                intent.putExtra("post", searchResults.get(position));
                startActivity(intent);
            }
        });

// Set up search button click listener
        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String searchTerm = searchEditText.getText().toString();
                performSearch(searchTerm);
            }
        });

// Open the database

        return view;

    }


    private void performSearch(String searchTerm) {
        // Clear previous search results
        searchResults.clear();

        // Perform search logic and populate searchResults list
        for (Post post : allPosts) {
            if (post.getTitle().toLowerCase().contains(searchTerm.toLowerCase())) {
                searchResults.add(post);
            }
        }

        if (searchResults.isEmpty()) {
            // No matching posts found, show a Toast
            Toast.makeText(getActivity(), "No matching posts found", Toast.LENGTH_SHORT).show();
        }

        // Update GridView
        postAdapter.notifyDataSetChanged();
    }


}
