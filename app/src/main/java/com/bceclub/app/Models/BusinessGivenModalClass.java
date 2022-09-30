package com.bceclub.app.Models;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class BusinessGivenModalClass {
    @SerializedName("success")
    private String success;
    @SerializedName("Receive_list")
    private List<Receive> receiveList = null;
    @SerializedName("Send_list")
    private List<Receive> sendList = null;

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

    public List<Receive> getSendList() {
        return sendList;
    }

    public void setSendList(List<Receive> sendList) {
        this.sendList = sendList;
    }

    public class Receive {

        @SerializedName("date")
        private String date;
        @SerializedName("Slip_details")
        private SlipDetails slipDetails;
        @SerializedName("Receive_from")
        private ReceiveFrom receiveFrom;

        public String getDate() {
            return date;
        }

        public void setDate(String date) {
            this.date = date;
        }

        public SlipDetails getSlipDetails() {
            return slipDetails;
        }

        public void setSlipDetails(SlipDetails slipDetails) {
            this.slipDetails = slipDetails;
        }

        public ReceiveFrom getReceiveFrom() {
            return receiveFrom;
        }

        public void setReceiveFrom(ReceiveFrom receiveFrom) {
            this.receiveFrom = receiveFrom;
        }

    }

    public static class ReceiveFrom {

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

    }


    public class SlipDetails {

        @SerializedName("amount")
        private String amount;
        @SerializedName("remark")
        private String remark;
        @SerializedName("invoice")
        private String invoice;

        public String getAmount() {
            return amount;
        }

        public void setAmount(String amount) {
            this.amount = amount;
        }

        public String getRemark() {
            return remark;
        }

        public void setRemark(String remark) {
            this.remark = remark;
        }

        public String getInvoice() {
            return invoice;
        }

        public void setInvoice(String invoice) {
            this.invoice = invoice;
        }

    }


}
