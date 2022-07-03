package com.bceclub.app.Models;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class BusinessLeadDetailModalClass {
    @SerializedName("success")
    private String success;
    @SerializedName("Receive_list")
    private List<Receive> receiveList = null;
    @SerializedName("Send_list")
    private List<Send> sendList = null;

    public String getSuccess() {
        return success;
    }

    public void setSuccess(String success) {
        this.success = success;
    }

    public List<Receive> getReceiveList() {
        return receiveList;
    }

    public void setReceiveList(List<Receive> receiveList) {
        this.receiveList = receiveList;
    }

    public List<Send> getSendList() {
        return sendList;
    }

    public void setSendList(List<Send> sendList) {
        this.sendList = sendList;
    }

    public class LeadStatus {

        @SerializedName("status")
        private String status;
        @SerializedName("amount")
        private String amount;
        @SerializedName("invoice")
        private String invoice;

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getAmount() {
            return amount;
        }

        public void setAmount(String amount) {
            this.amount = amount;
        }

        public String getInvoice() {
            return invoice;
        }

        public void setInvoice(String invoice) {
            this.invoice = invoice;
        }

    }

    public class Receive {

        @SerializedName("ref_id")
        private String ref_id;
        @SerializedName("date_info")
        private DateInfo dateInfo;
        @SerializedName("business_lead_details")
        private BusinessLeadDetails businessLeadDetails;
        @SerializedName("Receive_from")
        private ReceiveFrom receiveFrom;
        @SerializedName("lead_status")
        private LeadStatus leadStatus;

        public DateInfo getDateInfo() {
            return dateInfo;
        }

        public void setDateInfo(DateInfo dateInfo) {
            this.dateInfo = dateInfo;
        }

        public BusinessLeadDetails getBusinessLeadDetails() {
            return businessLeadDetails;
        }

        public void setBusinessLeadDetails(BusinessLeadDetails businessLeadDetails) {
            this.businessLeadDetails = businessLeadDetails;
        }

        public ReceiveFrom getReceiveFrom() {
            return receiveFrom;
        }

        public void setReceiveFrom(ReceiveFrom receiveFrom) {
            this.receiveFrom = receiveFrom;
        }

        public LeadStatus getLeadStatus() {
            return leadStatus;
        }

        public void setLeadStatus(LeadStatus leadStatus) {
            this.leadStatus = leadStatus;
        }

        public String getRef_id() {
            return ref_id;
        }

        public void setRef_id(String ref_id) {
            this.ref_id = ref_id;
        }
    }

    public class ReceiveFrom {

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


    public class Send {

        @SerializedName("date_info")
        private DateInfo__1 dateInfo;
        @SerializedName("business_lead_details")
        private BusinessLeadDetails__1 businessLeadDetails;
        @SerializedName("Business_lead_given_to")
        private BusinessLeadGivenTo businessLeadGivenTo;

        public DateInfo__1 getDateInfo() {
            return dateInfo;
        }

        public void setDateInfo(DateInfo__1 dateInfo) {
            this.dateInfo = dateInfo;
        }

        public BusinessLeadDetails__1 getBusinessLeadDetails() {
            return businessLeadDetails;
        }

        public void setBusinessLeadDetails(BusinessLeadDetails__1 businessLeadDetails) {
            this.businessLeadDetails = businessLeadDetails;
        }

        public BusinessLeadGivenTo getBusinessLeadGivenTo() {
            return businessLeadGivenTo;
        }

        public void setBusinessLeadGivenTo(BusinessLeadGivenTo businessLeadGivenTo) {
            this.businessLeadGivenTo = businessLeadGivenTo;
        }

    }

    public class DateInfo__1 {

        @SerializedName("date")
        private String date;
        @SerializedName("title")
        private String title;
        @SerializedName("rating")
        private String rating;

        public String getDate() {
            return date;
        }

        public void setDate(String date) {
            this.date = date;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getRating() {
            return rating;
        }

        public void setRating(String rating) {
            this.rating = rating;
        }

    }

    public class DateInfo {

        @SerializedName("date")
        private String date;
        @SerializedName("title")
        private String title;
        @SerializedName("rating")
        private String rating;

        public String getDate() {
            return date;
        }

        public void setDate(String date) {
            this.date = date;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getRating() {
            return rating;
        }

        public void setRating(String rating) {
            this.rating = rating;
        }

    }

    public class BusinessLeadGivenTo {

        @SerializedName("Image")
        private String image;
        @SerializedName("name")
        private String name;
        @SerializedName("company")
        private Object company;
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

        public Object getCompany() {
            return company;
        }

        public void setCompany(Object company) {
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

    public class BusinessLeadDetails__1 {

        @SerializedName("name")
        private String name;
        @SerializedName("mobile")
        private String mobile;
        @SerializedName("remark")
        private String remark;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getMobile() {
            return mobile;
        }

        public void setMobile(String mobile) {
            this.mobile = mobile;
        }

        public String getRemark() {
            return remark;
        }

        public void setRemark(String remark) {
            this.remark = remark;
        }

    }

    public class BusinessLeadDetails {

        @SerializedName("name")
        private String name;
        @SerializedName("mobile")
        private String mobile;
        @SerializedName("remark")
        private String remark;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getMobile() {
            return mobile;
        }

        public void setMobile(String mobile) {
            this.mobile = mobile;
        }

        public String getRemark() {
            return remark;
        }

        public void setRemark(String remark) {
            this.remark = remark;
        }

    }

}

