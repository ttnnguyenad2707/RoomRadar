package com.example.roomradar.Activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.roomradar.Database.entity.User;
import com.example.roomradar.adapter.UserListAdapter;
import com.example.roomradar.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class UserListActivity extends AppCompatActivity implements UserListAdapter.OnUserClickListener {

    private RecyclerView recyclerView;
    private UserListAdapter userListAdapter;
    private List<User> userList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_list);

        recyclerView = findViewById(R.id.recyclerViewUserList);
        getUserListFromFirebase();
        userList = new ArrayList<>();
        userListAdapter = new UserListAdapter(userList, this);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(userListAdapter);

        // Thực hiện load danh sách người dùng từ Firebase hoặc từ nguồn dữ liệu khác
        // Đây là nơi bạn cần thêm mã nguồn để lấy danh sách người dùng
//         userList.addAll(getUserListFromFirebase());
         userListAdapter.notifyDataSetChanged();
    }

    private void getUserListFromFirebase() {
        DatabaseReference usersRef = FirebaseDatabase.getInstance().getReference().child("users");

        usersRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                userList.clear();

                for (DataSnapshot userSnapshot : dataSnapshot.getChildren()) {
                    User user = userSnapshot.getValue(User.class);
                    if (user != null && !String.valueOf(user.getId()).equals(getCurrentUserId())) {
                        userList.add(user);
                    }
                }

                userListAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Xử lý lỗi nếu có
            }
        });
    }

    // Sự kiện khi người dùng click vào một item trong danh sách
    @Override
    public void onUserClick(User user) {
        // Mở ChatActivity và truyền thông tin người dùng được chọn
        Intent intent = new Intent(UserListActivity.this, ChatActivity.class);
        intent.putExtra("otherUserId", user.getId());
        startActivity(intent);
    }

    // Phương thức tạm thời để lấy ID người đang đăng nhập
    private String getCurrentUserId() {
        SharedPreferences sharedPreferences = this.getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        int userId = sharedPreferences.getInt("User",0);
        return String.valueOf(userId); // Thay thế bằng cách lấy ID người đang đăng nhập
    }
}
