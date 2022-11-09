package com.bceclub.app.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class OneToOneReceive_Model {

    @SerializedName("oto_id")
    @Expose
    private String otoId;
    @SerializedName("created_on")
    @Expose
    private String createdOn;
    @SerializedName("meeting_on")
    @Expose
    private String meetingOn;
    @SerializedName("remark")
    @Expose
    private String remark;
    @SerializedName("receive_from")
    @Expose
    private ReceiveFrom receiveFrom;
    @SerializedName("status")
    @Expose
    private String status;

    public String getOtoId() {
        return otoId;
    }

    public void setOtoId(String otoId) {
        this.otoId = otoId;
    }

    public String getCreatedOn() {
        return createdOn;
    }

    public void setCreatedOn(String createdOn) {
        this.createdOn = createdOn;
    }

    public String getMeetingOn() {
        return meetingOn;
    }

    public void setMeetingOn(String meetingOn) {
        this.meetingOn = meetingOn;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public ReceiveFrom getReceiveFrom() {
        return receiveFrom;
    }

    public void setReceiveFrom(ReceiveFrom receiveFrom) {
        this.receiveFrom = receiveFrom;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public class ReceiveFrom {

        @SerializedName("name")
        @Expose
        private String name;
        @SerializedName("company")
        @Expose
        private String company;
        @SerializedName("club")
        @Expose
        private String club;
        @SerializedName("category")
        @Expose
        private String category;
        @SerializedName("image")
        @Expose
        private String image;
        @SerializedName("dist_name")
        @Expose
        private String distName;
        @SerializedName("cit_name")
        @Expose
        private String citName;

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

        public String getDistName() {
            return distName;
        }

        public void setDistName(String distName) {
            this.distName = distName;
        }

        public String getCitName() {
            return citName;
        }

        public void setCitName(String citName) {
            this.citName = citName;
        }
    }

}
