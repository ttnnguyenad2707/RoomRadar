package com.example.roomradar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import android.os.Bundle;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity2 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        // Add the default fragment to the activity
        HomeFragment fragment = new HomeFragment();
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.frameLayout2, fragment)
                .commit();

        // Set a listener for BottomNavigationView
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavigationView);
        bottomNavigationView.setOnItemSelectedListener(item -> {
            if (item.getItemId() == R.id.navigation_item1) {
                // Replace the fragment with Fragment1
                HomeFragment fragment1 = new HomeFragment();
                fragmentManager.beginTransaction()
                        .replace(R.id.frameLayout2, fragment1)
                        .commit();
                return true;
            } else if (item.getItemId() == R.id.navigation_item2) {
                // Replace the fragment with Fragment2
                FavoritePostFrament fragment2 = new FavoritePostFrament();
                fragmentManager.beginTransaction()
                        .replace(R.id.frameLayout2, fragment2)
                        .commit();
                return true;

            }
            else if (item.getItemId() == R.id.navigation_item3) {
                // Replace the fragment with Fragment3
                addPostFragment fragment3 = new addPostFragment();
                fragmentManager.beginTransaction()
                        .replace(R.id.frameLayout2, fragment3)
                        .commit();
                return true;

            } else if (item.getItemId() == R.id.navigation_item4) {
                // Replace the fragment with Fragment4
                ChatFragment fragment4 = new ChatFragment();
                fragmentManager.beginTransaction()
                        .replace(R.id.frameLayout2, fragment4)
                        .commit();
                return true;
            } else if (item.getItemId() == R.id.navigation_item5) {
                // Replace the fragment with Fragment4
                BlankFragment fragment5 = new BlankFragment();
                fragmentManager.beginTransaction()
                        .replace(R.id.frameLayout2, fragment5)
                        .commit();
                return true;
            }
            else {
                return false;
            }
        });
    }

}