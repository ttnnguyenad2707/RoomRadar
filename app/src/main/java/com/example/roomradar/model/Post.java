package com.example.roomradar.model;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;

public class Post implements Serializable {
    private String title;
    private String description;
    private String address;
    private int area;
    private int maxPeople;
    private String price;
    private String deposit;
    private ArrayList<String> security;
    private ArrayList<String> utils;
    private ArrayList<String> interior;
    private String image[];
    private String owner;
    private LocalDateTime createdAt;

    public Post() {
    }

    public Post(String title, String description, String address, int area, int maxPeople, String price, String deposit, ArrayList<String> security, ArrayList<String> utils, ArrayList<String> interior, String[] image, String owner, LocalDateTime createdAt) {
        this.title = title;
        this.description = description;
        this.address = address;
        this.area = area;
        this.maxPeople = maxPeople;
        this.price = price;
        this.deposit = deposit;
        this.security = security;
        this.utils = utils;
        this.interior = interior;
        this.image = image;
        this.owner = owner;
        this.createdAt = createdAt;
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

    public int getArea() {
        return area;
    }

    public void setArea(int area) {
        this.area = area;
    }

    public int getMaxPeople() {
        return maxPeople;
    }

    public void setMaxPeople(int maxPeople) {
        this.maxPeople = maxPeople;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getDeposit() {
        return deposit;
    }

    public void setDeposit(String deposit) {
        this.deposit = deposit;
    }

    public ArrayList<String> getSecurity() {
        return security;
    }

    public void setSecurity(ArrayList<String> security) {
        this.security = security;
    }

    public ArrayList<String> getUtils() {
        return utils;
    }

    public void setUtils(ArrayList<String> utils) {
        this.utils = utils;
    }

    public ArrayList<String> getInterior() {
        return interior;
    }

    public void setInterior(ArrayList<String> interior) {
        this.interior = interior;
    }

    public String[] getImage() {
        return image;
    }

    public void setImage(String[] image) {
        this.image = image;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    @Override
    public String toString() {
        return "Post{" +
                "title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", address='" + address + '\'' +
                ", area=" + area +
                ", maxPeople=" + maxPeople +
                ", price='" + price + '\'' +
                ", deposit='" + deposit + '\'' +
                ", security=" + security +
                ", utils=" + utils +
                ", interior=" + interior +
                ", image=" + Arrays.toString(image) +
                ", owner='" + owner + '\'' +
                ", createdAt='" + createdAt + '\'' +
                '}';
    }
}
