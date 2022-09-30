package com.bceclub.app.Models;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ConnectionListModalClass {
    @SerializedName("success")
    private String success;
    @SerializedName("resuest_received")
    private List<ResuestReceived> resuestReceived = null;
    @SerializedName("resuest_send")
    private List<ResuestSend> resuestSend = null;

    public String getSuccess() {
        return success;
    }

    public void setSuccess(String success) {
        this.success = success;
    }

    public List<ResuestReceived> getResuestReceived() {
        return resuestReceived;
    }

    public void setResuestReceived(List<ResuestReceived> resuestReceived) {
        this.resuestReceived = resuestReceived;
    }

    public List<ResuestSend> getResuestSend() {
        return resuestSend;
    }

    public void setResuestSend(List<ResuestSend> resuestSend) {
        this.resuestSend = resuestSend;
    }


    public class RequestFrom {

        @SerializedName("company")
        private String company;
        @SerializedName("club_name")
        private String clubName;
        @SerializedName("category")
        private String category;
        @SerializedName("address")
        private String address;

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

    public class RequestTo {

        @SerializedName("company")
        private String company;
        @SerializedName("club_name")
        private String clubName;
        @SerializedName("category")
        private String category;
        @SerializedName("address")
        private String address;

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

    public class ResuestReceived {

        @SerializedName("request_id")
        private String requestId;
        @SerializedName("date")
        private String date;
        @SerializedName("request_from")
        private RequestFrom requestFrom;
        @SerializedName("status")
        private String status;

        public String getRequestId() {
            return requestId;
        }

        public void setRequestId(String requestId) {
            this.requestId = requestId;
        }

        public String getDate() {
            return date;
        }

        public void setDate(String date) {
            this.date = date;
        }

        public RequestFrom getRequestFrom() {
            return requestFrom;
        }

        public void setRequestFrom(RequestFrom requestFrom) {
            this.requestFrom = requestFrom;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

    }

    public class ResuestSend {

        @SerializedName("date")
        private String date;
        @SerializedName("request_to")
        private RequestTo requestTo;
        @SerializedName("status")
        private String status;

        public String getDate() {
            return date;
        }

        public void setDate(String date) {
            this.date = date;
        }

        public RequestTo getRequestTo() {
            return requestTo;
        }

        public void setRequestTo(RequestTo requestTo) {
            this.requestTo = requestTo;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

    }
}
