package com.example.roomradar;

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

import com.example.roomradar.adapter.PostAdapter;
import com.example.roomradar.model.Post;

import java.util.ArrayList;
import java.util.List;

public class BlankFragment extends Fragment {

    PostAdapter postAdapter;
    GridView searchResultGridView;
    Button searchButton;  // Thêm nút searchButton

    List<Post> allPosts;
    List<Post> searchResults;

    // Database
    private static final String DATABASE_NAME = "roomradar.db";
    private SQLiteDatabase database = null;

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
        allPosts = queryAllPostsFromDatabase();
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
        openDatabase();

        return view;

    }

    private void openDatabase() {
        String dbPath = getActivity().getDatabasePath(DATABASE_NAME).getAbsolutePath();
        database = SQLiteDatabase.openDatabase(dbPath, null, SQLiteDatabase.OPEN_READONLY);
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

    private List<Post> queryAllPostsFromDatabase() {
        List<Post> posts = new ArrayList<>();
        if (database != null) {
            Cursor cursor = database.rawQuery("SELECT Post.*, User.firstname AS owner, categories.name as categoriesName FROM Post INNER JOIN User ON Post.owner = User.id INNER JOIN categories ON Post.categories = categories.id", null);
            if (cursor != null) {
                int titleIndex = cursor.getColumnIndex("title");
                int descriptionIndex = cursor.getColumnIndex("description");
                int addressIndex = cursor.getColumnIndex("address");
                int areaIndex = cursor.getColumnIndex("area");
                int maxPeopleIndex = cursor.getColumnIndex("maxPeople");
                int priceIndex = cursor.getColumnIndex("price");
                int depositIndex = cursor.getColumnIndex("deposit");
                int ownerIndex = cursor.getColumnIndex("owner");
                int createdAtIndex = cursor.getColumnIndex("createdAt");
                int thumbnailIndex = cursor.getColumnIndex("thumbnail");
                int categoryIndex = cursor.getColumnIndex("categoriesName");

                while (cursor.moveToNext()) {
                    int id = cursor.getInt(0);
                    String title = cursor.getString(titleIndex);
                    String description = cursor.getString(descriptionIndex);
                    String address = cursor.getString(addressIndex);
                    float area = cursor.getFloat(areaIndex);
                    int maxPeople = cursor.getInt(maxPeopleIndex);
                    float price = cursor.getFloat(priceIndex);
                    float deposit = cursor.getFloat(depositIndex);
                    String owner = cursor.getString(ownerIndex);
                    String createdAt = cursor.getString(createdAtIndex);
                    String thumbnail = cursor.getString(thumbnailIndex);
                    String category = cursor.getString(categoryIndex);
                    Post post = new Post(id, title, description, address, area, maxPeople, price, deposit, owner, createdAt, category, thumbnail);
                    posts.add(post);
                }
                cursor.close();
            }
        }
        return posts;
    }
}
