package com.example.roomradar.service;

import android.content.Context;

import com.example.roomradar.Database.database;
import com.example.roomradar.Database.entity.Security;

import java.util.List;

public class SecurityService {
    private static SecurityService InstanceService;
    private database db;
    private SecurityService(Context context){
        db= database.getInstance(context);
    }
    public static SecurityService getInstance(Context context){
        if(InstanceService==null){
            InstanceService = new SecurityService(context);
        }
        return InstanceService;
    }
    public List<Security> getAllSecurity () {
        return db.securityDAO().getAllSecurity();

    }

    public Security getSecurityById(int id) {
        return db.securityDAO().getSecurityById(id);
    }


}
