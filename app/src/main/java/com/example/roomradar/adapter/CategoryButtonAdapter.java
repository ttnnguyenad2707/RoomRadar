package com.example.roomradar.adapter;

import android.app.Activity;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.roomradar.Activity.FilterResultActivity;
import com.example.roomradar.Database.entity.Category;
import com.example.roomradar.R;

import java.util.List;

public class CategoryButtonAdapter extends ArrayAdapter<Category> {
    Activity activity;
    List<Category> categories;
    int idLayout;
    public CategoryButtonAdapter(Activity activity, int idLayout, List<Category> categories) {
        super(activity, idLayout,categories);
        this.activity = activity;
        this.idLayout = idLayout;
        this.categories = categories;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater myinfater = activity.getLayoutInflater();
        convertView = myinfater.inflate(idLayout,null);

        Category category = categories.get(position);
        Button button = convertView.findViewById(R.id.button_cateogory);
        button.setText(category.getName());
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), FilterResultActivity.class);
                intent.putExtra("category",category.getId());
                intent.putExtra("categoryName",category.getName());
                getContext().startActivity(intent);
            }
        });
        return convertView;
    }
}
