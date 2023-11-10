package com.example.roomradar.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.roomradar.Database.entity.User;
import com.example.roomradar.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class UserListAdapter extends RecyclerView.Adapter<UserListAdapter.UserViewHolder> {

    private List<User> userList;
    private OnUserClickListener onUserClickListener;

    public interface OnUserClickListener {
        void onUserClick(User user);
    }

    public UserListAdapter(List<User> userList, OnUserClickListener onUserClickListener) {
        this.userList = userList;
        this.onUserClickListener = onUserClickListener;
    }

    @NonNull
    @Override
    public UserViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_user, parent, false);
        return new UserViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull UserViewHolder holder, int position) {
        User user = userList.get(position);
        holder.bind(user);
    }

    @Override
    public int getItemCount() {
        return userList.size();
    }

    public class UserViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView usernameTextView;
        private ImageView avatarUser;

        public UserViewHolder(@NonNull View itemView) {
            super(itemView);
            usernameTextView = itemView.findViewById(R.id.textViewUsername);
            avatarUser = itemView.findViewById(R.id.avatarUser);
            itemView.setOnClickListener(this);
        }

        public void bind(User user) {
            usernameTextView.setText(user.getFirstname());
            if (user.getAvatar() == null) {
                avatarUser.setImageResource(R.drawable.baseline_account_box_24);
            }
            else{
                Picasso.get().load(user.getAvatar()).into(avatarUser);
            }
//            avatarUser.setImageResource(user.getAvatar() == null ? R.drawable.baseline_account_box_24 : user.getAvatar() );
        }

        @Override
        public void onClick(View view) {
            int position = getAdapterPosition();
            if (position != RecyclerView.NO_POSITION && onUserClickListener != null) {
                onUserClickListener.onUserClick(userList.get(position));
            }
        }
    }
}
