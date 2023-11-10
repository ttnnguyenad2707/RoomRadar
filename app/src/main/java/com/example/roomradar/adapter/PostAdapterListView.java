package com.example.roomradar.adapter;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.example.roomradar.Activity.EditPostActivity;
import com.example.roomradar.Activity.ListPostOfUser;
import com.example.roomradar.Database.entity.PostLikedByUser;
import com.example.roomradar.R;
import com.example.roomradar.Database.entity.Post;
import com.example.roomradar.service.PostLikeByUserService;
import com.example.roomradar.service.PostService;
import com.squareup.picasso.Picasso;

import java.util.List;

public class PostAdapterListView extends ArrayAdapter<Post> {
    Activity context;
    int idLayout;
    List<Post> myList;

    public PostAdapterListView( Activity context, int idLayout, List<Post> myList) {
        super(context, idLayout,myList);
        this.context = context;
        this.idLayout = idLayout;
        this.myList = myList;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View v, @NonNull ViewGroup parent) {
        LayoutInflater myinfater = context.getLayoutInflater();

        v = myinfater.inflate(idLayout,null);

        Post post = myList.get(position);

        TextView tv_title = v.findViewById(R.id.tv_title_post);
        TextView tv_price = v.findViewById(R.id.tv_price_post);
        TextView tv_area = v.findViewById(R.id.tv_area_post);
        Button btn_edit = v.findViewById(R.id.btn_edit_post);
        Button btn_delete = v.findViewById(R.id.btn_delete_post);
        ImageView img = v.findViewById(R.id.img_post);

        tv_title.setText(post.getTitle());
        tv_price.setText(post.getPrice()+"VNĐ");
        tv_area.setText(post.getArea()+"m2");
        if(post.getThumbnail() != null){
            Glide.with(context).load(post.getThumbnail()).into(img);
        }

        btn_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, EditPostActivity.class);
                intent.putExtra("post", post);
                context.startActivity(intent);
            }
        });

        btn_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
                alertDialogBuilder.setMessage("Do you want to delete this post?");

                alertDialogBuilder.setPositiveButton("Yes",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                PostService postService = PostService.getInstance(context);
                                postService.deletePost(post);
                                Intent intent = new Intent(context, ListPostOfUser.class);
                                context.startActivity(intent);
                            }
                        });

                alertDialogBuilder.setNegativeButton("No",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        });
                alertDialogBuilder.setOnCancelListener(new DialogInterface.OnCancelListener() {
                    @Override
                    public void onCancel(DialogInterface dialog) {
                        dialog.dismiss();
                    }
                });

                AlertDialog alertDialog = alertDialogBuilder.create();
                alertDialog.show();
            }
        });

        return v;
    }
}