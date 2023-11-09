package com.example.roomradar.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.roomradar.R;
import com.example.roomradar.Database.entity.Message;

import java.util.List;

public class MessageAdapter extends RecyclerView.Adapter<MessageAdapter.MessageViewHolder> {

    private List<Message> messages;
    private Context context;
    private String currentUserId; // ID của người đang đăng nhập

    public MessageAdapter(List<Message> messages, Context context, String currentUserId) {
        this.messages = messages;
        this.context = context;
        this.currentUserId = currentUserId;
    }

    @NonNull
    @Override
    public MessageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_message, parent, false);
        return new MessageViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MessageViewHolder holder, int position) {
        Message message = messages.get(position);

        // Hiển thị tin nhắn theo ID của người gửi và người nhận
        if (message.getSenderId().equals(currentUserId)) {
            // Tin nhắn gửi đi
            holder.messageText.setBackgroundResource(R.drawable.bubble_background_sender);
        } else {
            // Tin nhắn nhận được
            holder.messageText.setBackgroundResource(R.drawable.bubble_background_receiver);
        }

        holder.messageText.setText(message.getContent());
    }

    @Override
    public int getItemCount() {
        return messages.size();
    }

    public static class MessageViewHolder extends RecyclerView.ViewHolder {
        TextView messageText;

        public MessageViewHolder(@NonNull View itemView) {
            super(itemView);
            messageText = itemView.findViewById(R.id.messageText);
        }
    }
}


