package com.example.roomradar;

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
import android.widget.Toast;

import com.example.roomradar.adapter.PostAdapter;
import com.example.roomradar.model.Post;

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
        processCopy();
//
//        // Bước 2: Mở cơ sở dữ liệu và truy vấn dữ liệu
        openDatabase();


        List<Post> posts = queryDataFromDatabase();

        // Bước 3: Hiển thị dữ liệu lên giao diện
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


        return view;
    }
    private void processCopy() {
        File dbFile = getActivity().getDatabasePath(DATABASE_NAME);
        if (!dbFile.exists()) {
            try {
                CopyDataBaseFromAsset();
                Toast.makeText(getContext(), "Copying success from Assets folder", Toast.LENGTH_LONG).show();
            } catch (Exception e) {
                Toast.makeText(getContext(), e.toString(), Toast.LENGTH_LONG).show();
            }
        }
    }
    private String getDatabasePath() {
        return getContext().getApplicationInfo().dataDir + DB_PATH_SUFFIX+ DATABASE_NAME;
    }

    public void CopyDataBaseFromAsset() {
        // TODO Auto-generated method stub
        try {
            InputStream myInput;
            myInput = getContext().getAssets().open(DATABASE_NAME);
            // Path to the just created empty db
            String outFileName = getDatabasePath();
            // if the path doesn't exist first, create it
            File f = new File(getContext().getApplicationInfo().dataDir + DB_PATH_SUFFIX);
            if (!f.exists())
                f.mkdir();
            // Open the empty db as the output stream
            OutputStream myOutput = new FileOutputStream(outFileName);
            // transfer bytes from the inputfile to the outputfile
            // Truyền bytes dữ liệu từ input đến output
            int size = myInput.available();
            byte[] buffer = new byte[size];
            myInput.read(buffer);
            myOutput.write(buffer);
            // Close the streams
            myOutput.flush();
            myOutput.close();
            myInput.close();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
    private void openDatabase() {
        String dbPath = getActivity().getDatabasePath(DATABASE_NAME).getAbsolutePath();
        database = SQLiteDatabase.openDatabase(dbPath, null, SQLiteDatabase.OPEN_READONLY);
    }

    private List<Post> queryDataFromDatabase() {
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
                    Post post = new Post(id,title, description, address, area, maxPeople, price, deposit, owner, createdAt,category, thumbnail);
                    posts.add(post);
                }
                cursor.close();
            }
        }
        return posts;
    }


}