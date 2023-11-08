package com.example.roomradar.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.roomradar.Database.entity.User;
import com.example.roomradar.R;
import com.example.roomradar.service.UserService;

public class ResetPassword extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_password);


        UserService userService = UserService.getInstance(ResetPassword.this);
        SharedPreferences sharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        int userId = sharedPreferences.getInt("User", 0);
        User userCheck = userService.getUserById(userId);
        EditText oldPassword = findViewById(R.id.editTextText4);
        EditText newPassword = findViewById(R.id.editTextText5);
        Button reset = findViewById(R.id.resetpassword);

        reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
              String oldP =  oldPassword.getText().toString();
              String newP =  newPassword.getText().toString();
              String passwordRegex = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,}$";
              if(newP.matches(passwordRegex)){
                  if(userCheck.getPassword().equals(oldP)){
                      userCheck.setPassword(newP);
                      userService.updateUser(userCheck);
                      Toast.makeText(ResetPassword.this, "Cập nhật mật khẩu thành công", Toast.LENGTH_SHORT).show();
                      SharedPreferences.Editor editor = sharedPreferences.edit();
                      editor.remove("User");
                      editor.apply();
                      Intent intent = new Intent(ResetPassword.this,LoginActivity.class);
                      startActivity(intent);
                  }else {
                      Toast.makeText(ResetPassword.this, "Mật khẩu của bạn không chính xác", Toast.LENGTH_SHORT).show();
                  }
              }
              else{
                  Toast.makeText(ResetPassword.this, "Mật khẩu mới của bạn không đúng cú pháp", Toast.LENGTH_SHORT).show();
              }

            }
        });

    }
}