package com.bceclub.app.Models;

import com.google.gson.annotations.SerializedName;

public class ReceiveFrom {

    @SerializedName("name")
    private String name;
    @SerializedName("company")
    private String company;
    @SerializedName("club")
    private String club;
    @SerializedName("category")
    private String category;
    @SerializedName("image")
    private String image;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getClub() {
        return club;
    }

    public void setClub(String club) {
        this.club = club;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
