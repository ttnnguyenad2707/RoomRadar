package com.example.roomradar.service;

import android.content.Context;

import com.example.roomradar.Database.database;
import com.example.roomradar.Database.entity.Images;

import java.util.List;

public class ImagesService {
    private static ImagesService InstanceService;
    private database db;
    private ImagesService(Context context){
        db= database.getInstance(context);
    }
    public static ImagesService getInstance(Context context){
        if(InstanceService==null){
            InstanceService = new ImagesService(context);
        }
        return InstanceService;
    }
    public long addNewImages(Images images){
        return db.imageDAO().addNewImage(images);
    }

    public void updateImage(Images images){
        db.imageDAO().updateImage(images);
    }
    public List<Images> getImagesByPost(int postId){
        return db.imageDAO().getImagesByPost(postId);
    }
}
