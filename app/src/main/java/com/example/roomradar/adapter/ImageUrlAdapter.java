package com.example.roomradar.adapter;

import android.app.Activity;
import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.example.roomradar.R;

import java.util.List;

public class ImageUrlAdapter extends ArrayAdapter<Uri> {
    private Activity context;
    int idLayout;
    private List<Uri> listImageUris;
    public ImageUrlAdapter(@NonNull Activity context,int idLayout, List<Uri> listImageUris) {
        super(context, idLayout, listImageUris);
        this.context = context;
        this.idLayout = idLayout;
        this.listImageUris = listImageUris;

    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater myinfater = context.getLayoutInflater();
        convertView = myinfater.inflate(idLayout,null);

        ImageView imageView = convertView.findViewById(com.example.roomradar.R.id.image_view_list);
        Glide.with(context)
                .load(listImageUris.get(position))
                .into(imageView);
        return convertView;
    }
}
