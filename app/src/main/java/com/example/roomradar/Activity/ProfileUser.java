package com.example.roomradar.Activity;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.roomradar.service.UserService;
import com.example.roomradar.Database.entity.User;
import com.example.roomradar.R;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class ProfileUser extends AppCompatActivity {
    private static final int REQUEST_CODE_GALLERY = 1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_user);

        UserService userService = UserService.getInstance(ProfileUser.this);
        SharedPreferences sharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        int userId = sharedPreferences.getInt("User", 0);
        User userCheck = userService.getUserById(userId);
        TextView viewname= findViewById(R.id.viewname);
        TextView email= findViewById(R.id.viewemail);
        TextView phonenumber= findViewById(R.id.viewPhone);
        TextView vipuser= findViewById(R.id.viewVip);
        ImageView imageView = findViewById(R.id.item_post_dislike);
        Log.d("users", "onCreate: "+userCheck);
        viewname.setText(userCheck.getFirstname()+userCheck.getLastname());
        email.setText(userCheck.getEmail());
        if (userCheck.getPhone().equals("")) {
            phonenumber.setText("Vui lòng cập nhật số điện thoại");
        } else {
            phonenumber.setText(userCheck.getPhone());
        }
        if(userCheck.getAdmin() == false){
            vipuser.setText("Tài khoản bình thường");
        }
        else{
            vipuser.setText("Tài khoản quản trị viên");
        }

        try {
            byte[] avatar = userCheck.getAvatar(); // Mảng byte avatar từ cơ sở dữ liệu

            if (avatar != null && avatar.length > 0) {
                Bitmap bitmap = BitmapFactory.decodeByteArray(avatar, 0, avatar.length);
                imageView.setImageBitmap(bitmap);
            } else {
                // Xử lý khi mảng byte avatar rỗng
                // Ví dụ: Gán một hình ảnh mặc định cho ImageView
                imageView.setImageResource(R.drawable.baseline_account_box_24);
            }
        } catch (Exception e) {
            // Xử lý lỗi trong khối if
            // Ví dụ: In thông báo lỗi hoặc thực hiện hành động khác
            e.printStackTrace();
            // Chuyển luồng điều khiển sang phần else
            // Ví dụ: Gán một hình ảnh mặc định cho ImageView
            imageView.setImageResource(R.drawable.baseline_account_box_24);
        }

        EditText editName = findViewById(R.id.editTextText);
        EditText editEmail = findViewById(R.id.editTextText2);
        EditText editPhone= findViewById(R.id.editTextText3);
        Button buttonUpdte = findViewById(R.id.button2);
        ImageView view = findViewById(R.id.updateimage);
         String[] updateName = {""};
         String[] updateEmail = {""};
         String[] updatePhone = {""};

         buttonUpdte.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View view) {
                 updateName[0] = editName.getText().toString();
                 updateEmail[0] = editEmail.getText().toString();
                 updatePhone[0] = editPhone.getText().toString();
                 String phoneRegex = "^(0|\\+84)\\d{9,10}$";
                 String emailRegex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$";
                 String nameRegex = "^[a-zA-Z]+$";
                 Log.d("users", "onCreate1: "+updateEmail[0].length());
                 boolean All = true;
                 if(updateName[0].length()>0){
                     if(updateName[0].matches(nameRegex)){
                         userCheck.setFirstname("");
                         userCheck.setLastname(updateName[0]);
                     }
                     else{
                         Toast.makeText(ProfileUser.this, "Vui lòng nhâp đúng tên", Toast.LENGTH_SHORT).show();
                         All = false;
                     }
                 }
                 if(updateEmail[0].length()>0){
                     if(updateEmail[0].matches(emailRegex)){
                         userCheck.setEmail(updateEmail[0]);
                     }
                     else{
                         Toast.makeText(ProfileUser.this, "Vui lòng nhâp đúng email", Toast.LENGTH_SHORT).show();
                         All = false;
                     }
                 }
                 if(updatePhone[0].length()>0){
                     if(updatePhone[0].matches(phoneRegex)){
                         userCheck.setPhone(updatePhone[0]);
                     }
                     else{
                         Toast.makeText(ProfileUser.this, "Vui lòng nhập đúng số điện thoại", Toast.LENGTH_SHORT).show();
                         All = false;
                     }
                 }
                 if(All == true){
                     userService.updateUser(userCheck);
                     Toast.makeText(ProfileUser.this, "Cập nhật thành công", Toast.LENGTH_SHORT).show();
                     recreate();
                 }
                 else {
                     Toast.makeText(ProfileUser.this, "Có lỗi xảy ra", Toast.LENGTH_SHORT).show();
                 }
             }
         });
         view.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View view) {
                 Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                 startActivityForResult(intent, REQUEST_CODE_GALLERY);
             }
         });



    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_CODE_GALLERY && resultCode == RESULT_OK && data != null) {
            Uri imageUri = data.getData();
            SharedPreferences sharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
            int userId = sharedPreferences.getInt("User", 0);
            try {
                // Chuyển Uri thành mảng byte
                InputStream inputStream = getContentResolver().openInputStream(imageUri);
                byte[] imageData = getBytesFromInputStream(inputStream);

                // Tạo entity và gán dữ liệu ảnh
                UserService userService = UserService.getInstance(ProfileUser.this);
                User user = userService.getUserById(userId);
                user.setAvatar(imageData);
                userService.updateUser(user);
                // Thực hiện thao tác chèn vào Room

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    // Phương thức để chuyển InputStream thành mảng byte
    private byte[] getBytesFromInputStream(InputStream inputStream) throws IOException {
        ByteArrayOutputStream byteBuffer = new ByteArrayOutputStream();
        int bufferSize = 1024;
        byte[] buffer = new byte[bufferSize];
        int len;
        while ((len = inputStream.read(buffer)) != -1) {
            byteBuffer.write(buffer, 0, len);
        }
        return byteBuffer.toByteArray();
    }
}