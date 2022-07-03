package com.bceclub.app.Models;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class FavouriteConnectionModalClass {

    @SerializedName("success")
    private String success;
    @SerializedName("request_connection")
    private List<RequestConnection> requestConnection = null;

    public String getSuccess() {
        return success;
    }

    public void setSuccess(String success) {
        this.success = success;
    }

    public List<RequestConnection> getRequestConnection() {
        return requestConnection;
    }

    public void setRequestConnection(List<RequestConnection> requestConnection) {
        this.requestConnection = requestConnection;
    }


    public static class RequestConnection {

        @SerializedName("Image")
        private String image;
        @SerializedName("name")
        private String name;
        @SerializedName("company")
        private String company;
        @SerializedName("category")
        private String category;
        @SerializedName("club_name")
        private String clubName;

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

        public String getCategory() {
            return category;
        }

        public void setCategory(String category) {
            this.category = category;
        }

        public String getClubName() {
            return clubName;
        }

        public void setClubName(String clubName) {
            this.clubName = clubName;
        }

    }
}
