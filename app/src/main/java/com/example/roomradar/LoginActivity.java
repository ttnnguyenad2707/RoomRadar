package com.example.roomradar;

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
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class LoginActivity extends AppCompatActivity {
    String api = "https://roomradar.onrender.com/api/v1/auth/login";
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
                        // Xử lý phản hồi từ API
                        Log.d("API Response", response.toString());
                        //lấy dữ liệu từ api gửi về
                        User user = new User();
                        try {
                            Log.d("GO to try catch","Try catch error");

                            user.setStatus(response.getBoolean("status"));
                            user.setId(response.getString("_id"));
                            user.setFirstname(response.getString("firstname"));
                            user.setLastname(response.getString("lastname"));
                            user.setEmailUser(response.getString("email"));
                            user.setAdmin(response.getBoolean("admin"));
                            user.setCreatedAt(response.getString("createdAt"));
                            user.setUpdateAt(response.getString("updatedAt"));
                            Log.d("API",user.toString());

                            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                            intent.putExtra("user", user);
                            startActivity(intent);

                        } catch (JSONException e) {
                            Log.d("Erro try catch","Error");
                        }
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
}