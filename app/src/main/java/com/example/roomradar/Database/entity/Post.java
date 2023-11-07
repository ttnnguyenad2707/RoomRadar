package com.example.roomradar.Database.entity;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;

@Entity
public class Post implements Serializable {
    @PrimaryKey(autoGenerate = true)
    private int id;
    @SerializedName("title")
    private String title;
    @SerializedName("description")
    private String description;
    @SerializedName("address")
    private String address;
    @SerializedName("area")
    private float area;
    @SerializedName("maxPeople")
    private int maxPeople;
    @SerializedName("price")
    private float price;
    @SerializedName("deposit")
    private float deposit;
    @SerializedName("owner")
    private String owner;
    @SerializedName("created")
    private String created;
    @SerializedName("category")
    private String category;
    @SerializedName("thumbnail")
    private String thumbnail;


    public Post() {
    }

    public Post(int id, String title, String description, String address, float area, int maxPeople, float price, float deposit, String owner, String created, String category, String thumbnail) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.address = address;
        this.area = area;
        this.maxPeople = maxPeople;
        this.price = price;
        this.deposit = deposit;
        this.owner = owner;
        this.created = created;
        this.category = category;
        this.thumbnail = thumbnail;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public float getArea() {
        return area;
    }

    public void setArea(float area) {
        this.area = area;
    }

    public int getMaxPeople() {
        return maxPeople;
    }

    public void setMaxPeople(int maxPeople) {
        this.maxPeople = maxPeople;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public float getDeposit() {
        return deposit;
    }

    public void setDeposit(float deposit) {
        this.deposit = deposit;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public String getCreated() {
        return created;
    }

    public void setCreated(String created) {
        this.created = created;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }
}
