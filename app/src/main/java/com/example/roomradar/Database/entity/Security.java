package com.example.roomradar.Database.entity;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Security {
    @PrimaryKey(autoGenerate = true)
    private int id;
    private String name;

    public Security(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public Security() {

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
