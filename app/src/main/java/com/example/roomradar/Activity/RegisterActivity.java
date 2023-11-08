package com.example.roomradar.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.roomradar.Database.entity.User;
import com.example.roomradar.R;
import com.example.roomradar.service.UserService;

import org.json.JSONException;
import org.json.JSONObject;

public class RegisterActivity extends AppCompatActivity {

    String api = "https://be-prm-v4.onrender.com/api/v1/auth/register";
    private RequestQueue requestQueue;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        Button Register = findViewById(R.id.register);
        EditText Email = findViewById(R.id.registerEmail4);
        EditText Password = findViewById(R.id.registerPassword4);
        EditText Firstname = findViewById(R.id.Firstname);
        EditText Lastname = findViewById(R.id.Lastname);
        Register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                requestQueue = Volley.newRequestQueue(RegisterActivity.this);
                String email = Email.getText().toString();
                String password = Password.getText().toString();
                String firstname = Firstname.getText().toString();
                String lastname = Lastname.getText().toString();
                //check usser toonf tai

                User user = new User(firstname,lastname,email,password);
                UserService userService = UserService.getInstance(RegisterActivity.this);
                userService.insertUser(user);



            }
        });
    }

    public void onTextViewClick(View view) {
        // Thực hiện các thao tác khi TextView được nhấp
        // Ví dụ: Chuyển hướng sang màn hình khác
        Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
        startActivity(intent);
    }
}