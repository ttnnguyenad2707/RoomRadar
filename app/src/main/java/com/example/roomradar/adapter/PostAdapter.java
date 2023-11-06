package com.example.roomradar.adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.roomradar.R;
import com.example.roomradar.model.Post;
import com.squareup.picasso.Picasso;

import java.util.List;

public class PostAdapter extends ArrayAdapter<Post> {
    Activity context;
    int idLayout;
    List<Post> myList;

    public PostAdapter( Activity context, int idLayout, List<Post> myList) {
        super(context, idLayout,myList);
        this.context = context;
        this.idLayout = idLayout;
        this.myList = myList;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater myinfater = context.getLayoutInflater();

        convertView = myinfater.inflate(idLayout,null);

        Post myPost = myList.get(position);

//        ImageView image_post = convertView.findViewById(R.id.image_post);
//        Picasso.get().load(myPost.getThumbnail()).into(image_post);

        TextView titlePost = convertView.findViewById(R.id.title_post);
        titlePost.setText(myPost.getTitle());

        TextView addressPost = convertView.findViewById(R.id.address_post);
        addressPost.setText(myPost.getAddress());

        TextView pricePost = convertView.findViewById(R.id.price_post);
        pricePost.setText(String.valueOf(myPost.getPrice()) + "VND/Tháng");



        return convertView;
    }
}
