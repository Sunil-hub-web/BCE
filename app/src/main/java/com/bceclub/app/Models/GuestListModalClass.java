package com.bceclub.app.Models;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class GuestListModalClass {

    @SerializedName("success")
    private String success;
    @SerializedName("guest_list")
    private List<Guest> guestList = null;

    public String getSuccess() {
        return success;
    }

    public void setSuccess(String success) {
        this.success = success;
    }

    public List<Guest> getGuestList() {
        return guestList;
    }

    public void setGuestList(List<Guest> guestList) {
        this.guestList = guestList;
    }

    public static class From {

        @SerializedName("image")
        private String image;
        @SerializedName("name")
        private String name;
        @SerializedName("company")
        private String company;
        @SerializedName("club_name")
        private String clubName;
        @SerializedName("category")
        private String category;
        @SerializedName("address")
        private String address;

        public String getImage() {
            return image;
        }

        public void setImage(String image) {
            this.image = image;
        }

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

        public String getClubName() {
            return clubName;
        }

        public void setClubName(String clubName) {
            this.clubName = clubName;
        }

        public String getCategory() {
            return category;
        }

        public void setCategory(String category) {
            this.category = category;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

    }

    public static class Guest {

        @SerializedName("date")
        private String date;
        @SerializedName("guest_details")
        private GuestDetails guestDetails;
        @SerializedName("from")
        private From from;

        public String getDate() {
            return date;
        }

        public void setDate(String date) {
            this.date = date;
        }

        public GuestDetails getGuestDetails() {
            return guestDetails;
        }

        public void setGuestDetails(GuestDetails guestDetails) {
            this.guestDetails = guestDetails;
        }

        public From getFrom() {
            return from;
        }

        public void setFrom(From from) {
            this.from = from;
        }

    }

    public static class GuestDetails {

        @SerializedName("name")
        private String name;
        @SerializedName("email")
        private String email;
        @SerializedName("mobile")
        private String mobile;
        @SerializedName("business_name")
        private String businessName;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public String getMobile() {
            return mobile;
        }

        public void setMobile(String mobile) {
            this.mobile = mobile;
        }

        public String getBusinessName() {
            return businessName;
        }

        public void setBusinessName(String businessName) {
            this.businessName = businessName;
        }

    }

}

