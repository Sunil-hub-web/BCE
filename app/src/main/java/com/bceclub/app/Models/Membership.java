package com.bceclub.app.Models;

import com.google.gson.annotations.SerializedName;

public class Membership {
    @SerializedName("id")
    private String id;
    @SerializedName("name")
    private String name;
    @SerializedName("club_name")
    private String clubName;
    @SerializedName("address")
    private String address;
    @SerializedName("category")
    private String category;
    @SerializedName("sub_category")
    private String subCategory;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getClubName() {
        return clubName;
    }

    public void setClubName(String clubName) {
        this.clubName = clubName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getSubCategory() {
        return subCategory;
    }

    public void setSubCategory(String subCategory) {
        this.subCategory = subCategory;
    }
}
