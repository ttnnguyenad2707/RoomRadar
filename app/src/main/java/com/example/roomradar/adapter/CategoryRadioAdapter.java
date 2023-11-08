package com.example.roomradar.adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.RadioButton;
import android.widget.TextView;

import com.example.roomradar.R;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.roomradar.Database.entity.Category;

import java.util.ArrayList;
import java.util.List;

public class CategoryRadioAdapter extends ArrayAdapter<Category> {
    Activity context;
    List<Category> categories;
    int idLayout;


    public CategoryRadioAdapter(Activity context, int idLayout, List<Category> categories) {
        super(context, idLayout,categories);
        this.context = context;
        this.idLayout = idLayout;
        this.categories = categories;
    }

    int categoryId;
    public int getCategoryId() {
        return categoryId;
    }
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater myinfater = context.getLayoutInflater();
        convertView = myinfater.inflate(idLayout,null);

        Category category = categories.get(position);

        CheckBox checkBox = convertView.findViewById(R.id.checkbox);
        TextView label = convertView.findViewById(R.id.label);

        checkBox.setChecked(false);
        label.setText(category.getName());

        checkBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Lấy category được chọn tại vị trí hiện tại
                Category selectedCategory = categories.get(position);

                // Kiểm tra xem người dùng đã chọn category hiện tại chưa
                if (selectedCategory.getId() == categoryId) {
                    // Người dùng đã chọn category hiện tại, không cần thay đổi gì
                } else {
                    // Người dùng đã chọn một category khác, cần thay đổi categoryId
                    categoryId = selectedCategory.getId();

                    // Loại bỏ checked của tất cả các CheckBox khác
                    for (int i = 0; i < parent.getChildCount(); i++) {
                        View view = parent.getChildAt(i);
                        CheckBox otherCheckBox = view.findViewById(R.id.checkbox);
                        if (otherCheckBox != checkBox) {
                            otherCheckBox.setChecked(false);
                        }
                    }
                }
            }
        });
        return convertView;

    }
}
