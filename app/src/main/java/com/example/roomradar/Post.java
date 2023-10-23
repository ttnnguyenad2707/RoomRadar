package com.example.roomradar;

import java.io.Serializable;
import java.util.Arrays;

public class Post implements Serializable {
    private String title;
    private String description;
    private String address;
    private int area;
    private int maxPeople;
    private String price;
    private String deposit;
    private String security[];
    private String utils[];
    private String interior[];
    private String image[];
    private String owner;
    private String createdAt;

    public Post(String title, String description, String address, int area, int maxPeople, String price, String deposit, String[] security, String[] utils, String[] interior, String[] image, String owner, String createdAt) {
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

    public Post() {

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

    public String[] getSecurity() {
        return security;
    }

    public void setSecurity(String[] security) {
        this.security = security;
    }

    public String[] getUtils() {
        return utils;
    }

    public void setUtils(String[] utils) {
        this.utils = utils;
    }

    public String[] getInterior() {
        return interior;
    }

    public void setInterior(String[] interior) {
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

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
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
                ", security=" + Arrays.toString(security) +
                ", utils=" + Arrays.toString(utils) +
                ", interior=" + Arrays.toString(interior) +
                ", image=" + Arrays.toString(image) +
                ", owner='" + owner + '\'' +
                ", createdAt='" + createdAt + '\'' +
                '}';
    }
}
