package com.example.roomradar.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.roomradar.R;
import com.example.roomradar.Database.entity.Message;

import java.util.List;

public class MessageAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final int VIEW_TYPE_SENDER = 1;
    private static final int VIEW_TYPE_RECEIVER = 2;

    private List<Message> messages;
    private String currentUserId;

    public MessageAdapter(List<Message> messages, String currentUserId) {
        this.messages = messages;
        this.currentUserId = currentUserId;
    }

    @Override
    public int getItemViewType(int position) {
        Message message = messages.get(position);
        return (message.getSenderId().equals(currentUserId)) ? VIEW_TYPE_SENDER : VIEW_TYPE_RECEIVER;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());

        if (viewType == VIEW_TYPE_SENDER) {
            View view = inflater.inflate(R.layout.item_message_sender, parent, false);
            return new SenderViewHolder(view);
        } else {
            View view = inflater.inflate(R.layout.item_message_receiver, parent, false);
            return new ReceiverViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        Message message = messages.get(position);

        if (holder instanceof SenderViewHolder) {
            ((SenderViewHolder) holder).bind(message);
        } else if (holder instanceof ReceiverViewHolder) {
            ((ReceiverViewHolder) holder).bind(message);
        }
    }

    @Override
    public int getItemCount() {
        return messages.size();
    }

    public static class SenderViewHolder extends RecyclerView.ViewHolder {
        TextView messageText;

        public SenderViewHolder(@NonNull View itemView) {
            super(itemView);
            messageText = itemView.findViewById(R.id.messageTextSender);
        }

        public void bind(Message message) {
            messageText.setText(message.getContent());
        }
    }

    public static class ReceiverViewHolder extends RecyclerView.ViewHolder {
        TextView messageText;

        public ReceiverViewHolder(@NonNull View itemView) {
            super(itemView);
            messageText = itemView.findViewById(R.id.messageTextReceiver);
        }

        public void bind(Message message) {
            messageText.setText(message.getContent());
        }
    }
}


