package com.example.drpotato.model;

import com.google.gson.annotations.SerializedName;

public class Potato {
    private int id;
    private String name;
    @SerializedName("image_url")
    private String imageUrl;
    private String description;
    private String prevention;
    private String gejala;

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

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPrevention() {
        return prevention;
    }

    public void setPrevention(String prevention) {
        this.prevention = prevention;
    }

    public String getGejala() {
        return gejala;
    }

    public void setGejala(String gejala) {
        this.gejala = gejala;
    }

    public Potato(int id, String name, String imageUrl, String description, String prevention, String gejala) {
        this.id = id;
        this.name = name;
        this.imageUrl = imageUrl;
        this.description = description;
        this.prevention = prevention;
        this.gejala = gejala;
    }

    @Override
    public String toString() {
        return "Potato{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", imageUrl='" + imageUrl + '\'' +
                ", description='" + description + '\'' +
                ", prevention='" + prevention + '\'' +
                ", gejala='" + gejala + '\'' +
                '}';
    }
}
