package com.example.roomradar.Activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.roomradar.Database.entity.User;
import com.example.roomradar.R;
import com.example.roomradar.Database.entity.Message;
import com.example.roomradar.adapter.MessageAdapter;
import com.example.roomradar.service.UserService;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class ChatActivity extends AppCompatActivity {
    private DatabaseReference usersRef;
    private RecyclerView recyclerView;
    private MessageAdapter messageAdapter;
    private List<Message> messages = new ArrayList<>();
    private String currentUserId; // ID của người đang đăng nhập
    private String otherUserId; // ID của người bạn đang chat với

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        UserService userService = UserService.getInstance(this);

        SharedPreferences sharedPreferences = this.getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        int userId = sharedPreferences.getInt("User",0);

        Intent intent = getIntent();
        int ownerId = (int) intent.getSerializableExtra("otherUserId");

        currentUserId = String.valueOf(userId); // Thay thế bằng cách lấy từ Intent hoặc SharedPreferences
        otherUserId = String.valueOf(ownerId); // Thay thế bằng cách lấy từ Intent hoặc SharedPreferences

        usersRef = FirebaseDatabase.getInstance().getReference("users");
        checkAndSaveOtherUserInfo();

        TextView nameUserReceiver = findViewById(R.id.nameUserReceiver);
        nameUserReceiver.setText(userService.getUserById(Integer.valueOf(otherUserId)).getFirstname());

        recyclerView = findViewById(R.id.recyclerView);
        messageAdapter = new MessageAdapter(messages,  currentUserId);
        recyclerView.setAdapter(messageAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Lắng nghe tin nhắn từ Firebase Realtime Database
        DatabaseReference messagesRef = FirebaseDatabase.getInstance().getReference("messages");
        messagesRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                Message receivedMessage = snapshot.getValue(Message.class);

                // Kiểm tra xem tin nhắn có phải là giữa hai người được chọn không
                if ((receivedMessage.getSenderId().equals(currentUserId) && receivedMessage.getReceiverId().equals(otherUserId)) ||
                        (receivedMessage.getSenderId().equals(otherUserId) && receivedMessage.getReceiverId().equals(currentUserId))) {
                    messages.add(receivedMessage);
                    messageAdapter.notifyItemInserted(messages.size() - 1);
                    recyclerView.scrollToPosition(messages.size() - 1);
                }
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }

            // Các phương thức khác của ChildEventListener
        });

        // Xử lý sự kiện khi người dùng gửi tin nhắn
        EditText messageInput = findViewById(R.id.messageInput);
        Button sendButton = findViewById(R.id.sendButton);

        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String messageContent = messageInput.getText().toString().trim();

                if (!messageContent.isEmpty()) {
                    String messageId = messagesRef.push().getKey();

                    Message message = new Message(currentUserId, otherUserId, messageContent);
                    messagesRef.child(messageId).setValue(message);

                    messageInput.setText(""); // Xóa nội dung trong input sau khi gửi
                }
            }
        });
    }

    private void checkAndSaveOtherUserInfo() {
        usersRef.child(otherUserId).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (!dataSnapshot.exists()) {
                    // Nếu thông tin người dùng không tồn tại trên Firebase, hãy lưu nó
                    saveOtherUserInfoToFirebase();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Xử lý lỗi nếu có
            }
        });
    }

    private void saveOtherUserInfoToFirebase() {
        // Tạo đối tượng người dùng
        UserService userService = UserService.getInstance(this);

        User otherUser = userService.getUserById(Integer.valueOf(otherUserId));

        // Lưu thông tin người dùng lên Firebase
        usersRef.child(otherUserId).setValue(otherUser);
    }
}
