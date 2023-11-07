package com.example.roomradar;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import java.util.ArrayList;

public class DatabaseHelper {
    private static final String DB_PATH_SUFFIX = "/databases/";
    private static final String DATABASE_NAME = "roomradar.db";
    private SQLiteDatabase database;
    private Context context;

    public DatabaseHelper(Context context) {
        this.context = context;
        openDatabase();
    }

    private String getDatabasePath() {
        return context.getApplicationInfo().dataDir + DB_PATH_SUFFIX + DATABASE_NAME;
    }

    private void openDatabase() {
        String dbPath = getDatabasePath();
        database = SQLiteDatabase.openDatabase(dbPath, null, SQLiteDatabase.OPEN_READONLY);
    }

    public ArrayList<String> querySecurityFromDatabase(int id) {
        ArrayList<String> security = new ArrayList<>();
        if (database != null) {
            Cursor cursor = database.rawQuery("SELECT Post.id AS post_id, Post.title, PostSecurity.security_id, Security.name AS security_name FROM Post INNER JOIN PostSecurity ON Post.id = PostSecurity.post_id INNER JOIN Security ON PostSecurity.security_id = Security.id WHERE post_id =" + id, null);
            if (cursor != null) {
                while (cursor.moveToNext()) {
                    String security_name = cursor.getString(3);
                    security.add(security_name);
                }
                cursor.close();
            }
        }
        return security;
    }
}

