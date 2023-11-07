package com.example.roomradar.service;

import android.content.Context;

import com.example.roomradar.Database.DAO.CategoryDAO;
import com.example.roomradar.Database.database;
import com.example.roomradar.Database.entity.Category;

import java.util.List;

public class CategoryService {
    private static CategoryService InstanceService;
    private database db;
    private CategoryService(Context context){
        db= database.getInstance(context);
    }
    public static CategoryService getInstance(Context context){
        if(InstanceService==null){
            InstanceService = new CategoryService(context);
        }
        return InstanceService;
    }
    public List<Category> getAllCategory () {
        return db.categoryDAO().getAllCategory();

    }

    public void insertCategory (Category category) {
        db.categoryDAO().insertCategory(category);

    }
}
