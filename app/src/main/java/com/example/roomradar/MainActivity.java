package com.example.roomradar;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.TextView;
import com.google.gson.Gson;

import java.io.Serializable;

public class MainActivity extends AppCompatActivity {
    private Gson gson = new Gson();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SharedPreferences sharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        String jsonUser = sharedPreferences.getString("username", ""); // Trả về "" nếu không tìm thấy giá trị

        if (!jsonUser.isEmpty()) {
            // Người dùng đã đăng nhập trước đó, thực hiện các hành động tương ứng
            User savedObj = gson.fromJson(jsonUser, User.class);
            TextView lastname = findViewById(R.id.Textviewlastname);
            lastname.setText(savedObj.getLastname());
        } else {
            // Người dùng chưa đăng nhập, hiển thị màn hình đăng nhập hoặc các tùy chọn khác
            Intent intent = new Intent(MainActivity.this, LoginActivity.class);
            startActivity(intent);
        }
//        Intent intent = getIntent();
//        User user = (User) intent.getSerializableExtra("user");
    }
}