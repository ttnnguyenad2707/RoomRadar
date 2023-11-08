package com.example.roomradar.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;


import com.example.roomradar.Database.entity.Category;
import com.example.roomradar.Database.entity.Security;
import com.example.roomradar.Database.entity.Utils;
import com.example.roomradar.R;
import com.example.roomradar.service.CategoryService;
import com.example.roomradar.service.SecurityService;
import com.example.roomradar.service.UtilsService;

import java.util.List;

public class AddPostActivity extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_post);

        CategoryService categoryService = CategoryService.getInstance(AddPostActivity.this);
        UtilsService utilService = UtilsService.getInstance(AddPostActivity.this);
        SecurityService securityService = SecurityService.getInstance(AddPostActivity.this);

        List<Category> categoryList = categoryService.getAllCategory();
        List<Utils> utilsList = utilService.getAllUtils();
        List<Security> securityList = securityService.getAllSecurity();





//        RadioGroup radioGroup = findViewById(R.id.add_category);
//        EditText edt_title = findViewById(R.id.add_title);
//        EditText edt_description = findViewById(R.id.add_description);
//        EditText edt_address = findViewById(R.id.add_address);
//        EditText edt_area = findViewById(R.id.add_area);
//        EditText edt_maxPeople = findViewById(R.id.add_maxpeople);
//        EditText edt_price = findViewById(R.id.add_price);
//        EditText edt_deposit = findViewById(R.id.add_desposit);
//        CheckBox ckb_security_gated = findViewById(R.id.add_security_gated);
//        CheckBox ckb_security_alarm = findViewById(R.id.add_security_alarm);
//        EditText edt_images = findViewById(R.id.add_images);

//        findViewById(R.id.add_submit).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                int id_select = radioGroup.getCheckedRadioButtonId();
//                RadioButton category_selected = findViewById(id_select);
//                String category = category_selected.getText().toString();
//
//                String title = edt_title.getText().toString();
//                String description = edt_description.getText().toString();
//                String address =  edt_address.getText().toString();
//                int area =  Integer.parseInt(edt_area.getText().toString()) ;
//                int maxPeople =  Integer.parseInt(edt_maxPeople.getText().toString());
//                String price =  edt_price.getText().toString();
//                String deposit =  edt_deposit.getText().toString();
//                String images = edt_images.getText().toString();
//
//                ArrayList<String> security = new ArrayList<>();
//                if(ckb_security_alarm.isChecked()){
//                    security.add(ckb_security_alarm.getText().toString());
//                }
//                if (ckb_security_gated.isChecked()){
//                    security.add(ckb_security_gated.getText().toString());
//                }
//                String imagesArray[] = {images};
//
//                LocalDateTime createdAt = LocalDateTime.now();
//
//
//
//
//
//
//            }
//        });



    }

    public static class bottombar extends AppCompatActivity {

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
//            setContentView(R.layout.activity_bottombar);
        }
    }
}