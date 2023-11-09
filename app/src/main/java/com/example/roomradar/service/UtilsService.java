package com.example.roomradar.service;

import android.content.Context;

import com.example.roomradar.Database.database;
import com.example.roomradar.Database.entity.Security;
import com.example.roomradar.Database.entity.Utils;

import java.util.List;

public class UtilsService {
    private static UtilsService InstanceService;
    private database db;
    private UtilsService(Context context){
        db= database.getInstance(context);
    }
    public static UtilsService getInstance(Context context){
        if(InstanceService==null){
            InstanceService = new UtilsService(context);
        }
        return InstanceService;
    }

    public List<Utils> getAllUtils(){
        return db.utilsDAO().getAllUtils();
    }
    public void addnewUtils(Utils utils){
        db.utilsDAO().addNewUtils(utils);
    }

    public Utils getUtilsById(int id) {
        return db.utilsDAO().geUtilsById(id);
    }


}
