package com.example.roomradar.adapter;

import android.app.Activity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import com.example.roomradar.Database.entity.Security;
import com.example.roomradar.R;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;


import java.util.ArrayList;
import java.util.List;

public class SecurityCheckBoxAdapter extends ArrayAdapter<Security> {
    Activity context;
    List<Security> securities;
    int idLayout;


    public SecurityCheckBoxAdapter(Activity context, int idLayout, List<Security> securities) {
        super(context, idLayout,securities);
        this.context = context;
        this.idLayout = idLayout;
        this.securities = securities;
    }


    List<Integer> securityId = new ArrayList<>();
    public List<Integer> getSecurityId() {
        return securityId;
    }
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater myinfater = context.getLayoutInflater();

        convertView = myinfater.inflate(idLayout,null);
        Security security = securities.get(position);

        CheckBox checkBox = convertView.findViewById(R.id.checkbox);
        TextView label = convertView.findViewById(R.id.label);

        checkBox.setChecked(false);
        label.setText(security.getName());


        checkBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkBox.isChecked()) {
                    // Nếu checkBox được kiểm tra (checked)
                    if (!securityId.contains(security.getId())) {
                        // Kiểm tra xem securityId đã chứa security.getId chưa
                        securityId.add(security.getId()); // Nếu chưa có, thì thêm vào danh sách
                    }
                } else {
                    // Nếu checkBox không được kiểm tra (unchecked)
                    if (securityId.contains(security.getId())) {
                        // Kiểm tra xem securityId đã chứa security.getId chưa
                        securityId.remove(Integer.valueOf(security.getId())); // Nếu đã có, thì loại bỏ nó khỏi danh sách
                    }
                }
            }
        });
        return convertView;
    }
}
