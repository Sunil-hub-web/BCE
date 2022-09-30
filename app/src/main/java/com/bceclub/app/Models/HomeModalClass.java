package com.bceclub.app.Models;

import com.google.gson.annotations.SerializedName;

public class HomeModalClass {
    @SerializedName("success")
    private String success;
    @SerializedName("profile_info")
    private ProfileInfo profileInfo;
    @SerializedName("meetingwise")
    private Meetingwise meetingwise;
    @SerializedName("totalinfo")
    private Totalinfo totalinfo;

    public String getSuccess() {
        return success;
    }

    public void setSuccess(String success) {
        this.success = success;
    }

    public ProfileInfo getProfileInfo() {
        return profileInfo;
    }

    public void setProfileInfo(ProfileInfo profileInfo) {
        this.profileInfo = profileInfo;
    }

    public Meetingwise getMeetingwise() {
        return meetingwise;
    }

    public void setMeetingwise(Meetingwise meetingwise) {
        this.meetingwise = meetingwise;
    }

    public Totalinfo getTotalinfo() {
        return totalinfo;
    }

    public void setTotalinfo(Totalinfo totalinfo) {
        this.totalinfo = totalinfo;
    }


    public static class Meetingwise {

        @SerializedName("guestlist")
        private String guestlist;
        @SerializedName("upcoming_meeting_dt")
        private String upcomingMeetingDt;
        @SerializedName("request_alert")
        private String requestAlert;
        @SerializedName("no_of_lead_given")
        private String noOfLeadGiven;
        @SerializedName("total_business_given")
        private String totalBusinessGiven;
        @SerializedName("total_business_receive")
        private String totalBusinessReceive;
        @SerializedName("mark_ask")
        private String markAsk;
        @SerializedName("review")
        private String review;

        public String getGuestlist() {
            return guestlist;
        }

        public void setGuestlist(String guestlist) {
            this.guestlist = guestlist;
        }

        public String getUpcomingMeetingDt() {
            return upcomingMeetingDt;
        }

        public void setUpcomingMeetingDt(String upcomingMeetingDt) {
            this.upcomingMeetingDt = upcomingMeetingDt;
        }

        public String getRequestAlert() {
            return requestAlert;
        }

        public void setRequestAlert(String requestAlert) {
            this.requestAlert = requestAlert;
        }

        public String getNoOfLeadGiven() {
            return noOfLeadGiven;
        }

        public void setNoOfLeadGiven(String noOfLeadGiven) {
            this.noOfLeadGiven = noOfLeadGiven;
        }

        public String getTotalBusinessGiven() {
            return totalBusinessGiven;
        }

        public void setTotalBusinessGiven(String totalBusinessGiven) {
            this.totalBusinessGiven = totalBusinessGiven;
        }

        public String getTotalBusinessReceive() {
            return totalBusinessReceive;
        }

        public void setTotalBusinessReceive(String totalBusinessReceive) {
            this.totalBusinessReceive = totalBusinessReceive;
        }

        public String getMarkAsk() {
            return markAsk;
        }

        public void setMarkAsk(String markAsk) {
            this.markAsk = markAsk;
        }

        public String getReview() {
            return review;
        }

        public void setReview(String review) {
            this.review = review;
        }

    }

    public static class ProfileInfo {

        @SerializedName("Image")
        private String image;
        @SerializedName("name")
        private String name;
        @SerializedName("company")
        private String company;
        @SerializedName("club_name")
        private String clubName;
        @SerializedName("category")
        private String category;
        @SerializedName("renew_on")
        private String renewOn;

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

        public String getRenewOn() {
            return renewOn;
        }

        public void setRenewOn(String renewOn) {
            this.renewOn = renewOn;
        }

    }

    public static class Totalinfo {

        @SerializedName("total_guest_list")
        private String totalGuestList;
        @SerializedName("no_of_lead_given")
        private String noOfLeadGiven;
        @SerializedName("total_business_given")
        private String totalBusinessGiven;
        @SerializedName("total_business_receive")
        private String totalBusinessReceive;
        @SerializedName("total_review")
        private String totalReview;

        public String getTotalGuestList() {
            return totalGuestList;
        }

        public void setTotalGuestList(String totalGuestList) {
            this.totalGuestList = totalGuestList;
        }

        public String getNoOfLeadGiven() {
            return noOfLeadGiven;
        }

        public void setNoOfLeadGiven(String noOfLeadGiven) {
            this.noOfLeadGiven = noOfLeadGiven;
        }

        public String getTotalBusinessGiven() {
            return totalBusinessGiven;
        }

        public void setTotalBusinessGiven(String totalBusinessGiven) {
            this.totalBusinessGiven = totalBusinessGiven;
        }

        public String getTotalBusinessReceive() {
            return totalBusinessReceive;
        }

        public void setTotalBusinessReceive(String totalBusinessReceive) {
            this.totalBusinessReceive = totalBusinessReceive;
        }

        public String getTotalReview() {
            return totalReview;
        }

        public void setTotalReview(String totalReview) {
            this.totalReview = totalReview;
        }

    }
}
