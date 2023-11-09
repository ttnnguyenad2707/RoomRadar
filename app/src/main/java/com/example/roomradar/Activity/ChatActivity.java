package com.example.roomradar.Activity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.roomradar.R;
import com.example.roomradar.Database.entity.Message;
import com.example.roomradar.adapter.MessageAdapter;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import java.util.ArrayList;
import java.util.List;

public class ChatActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private MessageAdapter messageAdapter;
    private List<Message> messages = new ArrayList<>();
    private String currentUserId; // ID của người đang đăng nhập
    private String otherUserId; // ID của người bạn đang chat với

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        // Lấy ID của người đang đăng nhập và ID của người bạn đang chat với từ Intent hoặc SharedPreferences
        SharedPreferences sharedPreferences = this.getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        int ownerId = sharedPreferences.getInt("User",0);

        currentUserId = String.valueOf(ownerId); // Thay thế bằng cách lấy từ Intent hoặc SharedPreferences
        otherUserId = "1"; // Thay thế bằng cách lấy từ Intent hoặc SharedPreferences

        recyclerView = findViewById(R.id.recyclerView);
        messageAdapter = new MessageAdapter(messages, this, currentUserId);
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
}
