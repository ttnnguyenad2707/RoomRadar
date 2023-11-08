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

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.roomradar.Database.entity.User;
import com.example.roomradar.R;
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
//                Intent intent = new Intent(LoginActivity.this,AddPostActivity.class);
//                startActivity(intent);
                requestQueue = Volley.newRequestQueue(LoginActivity.this);
                String emailValue = email.getText().toString();
                String passwordValue = password.getText().toString();
                sendApiRequest(emailValue,passwordValue);
            }
        });
    }
    private void sendApiRequest(String email, String password) {
        // ... Code gửi yêu cầu API sử dụng requestQueue ...
        // truyền dữ liệu bào body
        JSONObject requestBody = new JSONObject();
        try {
            requestBody.put("email", email);
            requestBody.put("password", password);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, api, requestBody,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
//                        Log.d("API Response", response.toString());
                        //lấy dữ liệu từ api gửi về
                        User user = new User();
//                        try {
                            Gson gson = new Gson();
                            String jsonString = response.toString();
                            JsonObject jsonObject = gson.fromJson(jsonString, JsonObject.class);
                            String firstname = jsonObject.get("firstname").getAsString();
                            String lastname = jsonObject.get("lastname").getAsString();
                            String email = jsonObject.get("email").getAsString();
                            boolean admin = jsonObject.get("admin").getAsBoolean();
                            user.setFirstname(firstname);
                            user.setLastname(lastname);
                            user.setEmail(email);
                            user.setAdmin(admin);
                            Log.d("user1", "onCreate: " + user.toString());
                            SharedPreferences sharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
                            SharedPreferences.Editor editor = sharedPreferences.edit();
                            String userJsonString = gson.toJson(user); // Chuyển đổi đối tượng User thành chuỗi JSON
                            editor.putString("username", userJsonString); // Lưu trữ chuỗi JSON vào SharedPreferences
                            editor.apply();
                            Intent intent = new Intent(LoginActivity.this, MainActivity2.class);
                            startActivity(intent);

//                        } catch (JSONException e) {
//
//                        }
//                        Log.d("API",user.toString());
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // Xử lý lỗi
                        Log.e("API Error", error.toString());
                    }
                });

        requestQueue.add(jsonObjectRequest);
    }

    public void onTextViewClick(View view) {
        // Thực hiện các thao tác khi TextView được nhấp
        // Ví dụ: Chuyển hướng sang màn hình khác
        Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
        startActivity(intent);
    }
}