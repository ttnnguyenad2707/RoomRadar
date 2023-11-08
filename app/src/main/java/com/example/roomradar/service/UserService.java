package com.example.roomradar.service;

import android.content.Context;

import com.example.roomradar.Database.database;
import com.example.roomradar.Database.entity.User;

public class UserService {
    private static UserService InstanceService;
    private database db;
    private UserService(Context context){
        db= database.getInstance(context);
    }

    public static UserService getInstance(Context context){
        if(InstanceService==null){
            InstanceService = new UserService(context);
        }
        return InstanceService;
    }
    public void insertUser(User user) {
        db.userDAO().insertUser(user);
    }
    public User getUserById(int id) {

        return db.userDAO().getUserById(id);
    }
}
