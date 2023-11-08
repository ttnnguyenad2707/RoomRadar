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

import com.example.roomradar.Database.entity.Images;
import com.example.roomradar.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class ImageAdapter extends ArrayAdapter<Images> {
    private Activity context;
    int idLayout;
    private List<Images> listImageUris;

    public ImageAdapter(Activity context,int idLayout, List<Images> listImageUris) {
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

        String url = listImageUris.get(position).getUrl();
        ImageView image_post = convertView.findViewById(R.id.image_view_list);
        Picasso.get().load(url).into(image_post);

        return convertView;
    }
}
