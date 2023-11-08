package com.example.roomradar.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.roomradar.Database.entity.User;
import com.example.roomradar.R;
import com.example.roomradar.service.UserService;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

import org.json.JSONException;
import org.json.JSONObject;

public class LoginActivity extends AppCompatActivity {
    String api = "https://be-prm-v4.onrender.com/api/v1/auth/login";
//    String api = "https://03db-2405-4803-f8a3-1e90-153e-54ef-4d3b-5bc8.ngrok-free.app/api/v1/auth/login";
//String api = "https://cf3d-118-70-211-228.ngrok-free.app/api/v1/auth/login";
    private RequestQueue requestQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Button buttonLogin = findViewById(R.id.login);
        EditText email = findViewById(R.id.registerEmail4);
        EditText password = findViewById(R.id.registerPassword4);
        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                UserService userService = UserService.getInstance(LoginActivity.this);
                String emailValue = email.getText().toString();
                String passwordValue = password.getText().toString();
                User userCheck = userService.verifyUser(emailValue,passwordValue);
                if(userCheck != null){
                    Toast.makeText(getApplicationContext(), "Dang nhap thanh cong", Toast.LENGTH_LONG).show();
                    SharedPreferences sharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putInt("User",userCheck.getId());
                    editor.apply();
                    Intent intent = new Intent(LoginActivity.this,MainActivity2.class);
                    startActivity(intent);
                }
                else{
                    Toast.makeText(getApplicationContext(), "Dang nhap khong thanh cong", Toast.LENGTH_LONG).show();
                }
            }
        });
    }


    public void onTextViewClick(View view) {
        // Thực hiện các thao tác khi TextView được nhấp
        // Ví dụ: Chuyển hướng sang màn hình khác
        Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
        startActivity(intent);
    }
}