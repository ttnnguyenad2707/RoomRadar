package com.example.roomradar.Database;
import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import com.example.roomradar.Database.DAO.CategoryDAO;
import com.example.roomradar.Database.DAO.PostDao;
import com.example.roomradar.Database.entity.Category;
import com.example.roomradar.Database.entity.Images;
import com.example.roomradar.Database.entity.Post;
import com.example.roomradar.Database.entity.Security;
import com.example.roomradar.Database.entity.Utils;

@Database(entities = {Post.class, Images.class, Category.class, Security.class, Utils.class},version = 4)
public abstract class database extends RoomDatabase {
    private  static database instance;
    public static database getInstance(Context context){
        if(instance==null){
            instance = Room.databaseBuilder(context.getApplicationContext(),database.class,"roomradar")
                    .fallbackToDestructiveMigration().allowMainThreadQueries()
                    .build();

        }
        return instance;
    }

    public abstract PostDao postDao();
    public abstract CategoryDAO categoryDAO();

}
