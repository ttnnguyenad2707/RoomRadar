package com.example.roomradar.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.roomradar.R;
import com.example.roomradar.Database.entity.Post;

import java.util.List;

public class PostAdapterRecyclerView extends RecyclerView.Adapter<PostAdapterRecyclerView.PostHolder
        > {
    List<Post> posts;
    Context context;
    public PostAdapterRecyclerView(List<Post> posts,Context context){
        this.posts = posts;
        this.context=context;

    }
    @NonNull
    @Override
    public PostAdapterRecyclerView.PostHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull PostAdapterRecyclerView.PostHolder holder, int position) {
        Post post = posts.get(position);
//        if(post.getImage() == null){
//            holder.img_post.setBackgroundResource(R.drawable.ic_launcher_background);
//        }else{
//            Glide.with(context).load(post.getImage()[0]).into(holder.img_post);
//        }
        holder.tv_title_post.setText(post.getTitle());
//        holder.tv_price_post.setText(post.getPrice());
//        holder.tv_area_post.setText(post.getArea());

        holder.btn_edit_post.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        holder.btn_delete_post.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    @Override
    public int getItemCount() {
        return 0;
    }
    public class PostHolder extends RecyclerView.ViewHolder{
        ImageView img_post;
        TextView tv_title_post,tv_price_post,tv_area_post;
        Button btn_edit_post,btn_delete_post;

        public PostHolder(@NonNull View itemView) {
            super(itemView);
            img_post = itemView.findViewById(R.id.img_post);
            tv_title_post = itemView.findViewById(R.id.tv_title_post);
            tv_price_post = itemView.findViewById(R.id.tv_price_post);
            tv_area_post = itemView.findViewById(R.id.tv_area_post);
            btn_edit_post = itemView.findViewById(R.id.btn_edit_post);
            btn_delete_post = itemView.findViewById(R.id.btn_delete_post);

        }
    }
}
