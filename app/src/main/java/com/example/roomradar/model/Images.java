package com.example.roomradar.model;

public class Images {
    private String id;
    private String url;

    public Images(String id, String url) {
        this.id = id;
        this.url = url;
    }

    public Images() {

    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    public String toString() {
        return "Images{" +
                "id='" + id + '\'' +
                ", url='" + url + '\'' +
                '}';
    }
}
