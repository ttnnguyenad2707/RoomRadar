package com.example.roomradar.adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import com.example.roomradar.Database.entity.Utils;
import com.example.roomradar.R;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;


import java.util.ArrayList;
import java.util.List;

public class UtilsCheckboxAdapter extends ArrayAdapter<Utils> {
    Activity context;
    List<Utils> utils;
    int idLayout;


    public UtilsCheckboxAdapter(Activity context, int idLayout, List<Utils> utils) {
        super(context, idLayout,utils);
        this.context = context;
        this.idLayout = idLayout;
        this.utils = utils;
    }

    List<Integer> utilsId = new ArrayList<>();
    public List<Integer> getUtilsId() {
        return utilsId;
    }

    public void setUtilsId(List<Integer> utilsId) {
        this.utilsId = utilsId;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater myinfater = context.getLayoutInflater();
        convertView = myinfater.inflate(idLayout,null);
        Utils util = utils.get(position);
        CheckBox checkBox = convertView.findViewById(R.id.checkbox);
        TextView label = convertView.findViewById(R.id.label);
        checkBox.setChecked(utilsId.contains(util.getId()));
        label.setText(util.getName());

        checkBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkBox.isChecked()) {
                    // Nếu checkBox được kiểm tra (checked)
                    if (!utilsId.contains(util.getId())) {
                        // Kiểm tra xem securityId đã chứa security.getId chưa
                        utilsId.add(util.getId()); // Nếu chưa có, thì thêm vào danh sách
                    }
                } else {
                    // Nếu checkBox không được kiểm tra (unchecked)
                    if (utilsId.contains(util.getId())) {
                        // Kiểm tra xem securityId đã chứa security.getId chưa
                        utilsId.remove(Integer.valueOf(util.getId())); // Nếu đã có, thì loại bỏ nó khỏi danh sách
                    }
                }
            }
        });
        return convertView;
    }
}
