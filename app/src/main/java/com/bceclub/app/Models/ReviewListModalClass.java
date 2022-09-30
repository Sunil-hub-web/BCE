package com.bceclub.app.Models;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ReviewListModalClass {
    @SerializedName("success")
    private String success;
    @SerializedName("receive_list")
    private List<ReviewItem> receiveList = null;
    @SerializedName("send_list")
    private List<ReviewItem> sendList = null;

    public String getSuccess() {
        return success;
    }

    public void setSuccess(String success) {
        this.success = success;
    }

    public List<ReviewItem> getReceiveList() {
        return receiveList;
    }

    public void setReceiveList(List<ReviewItem> receiveList) {
        this.receiveList = receiveList;
    }

    public List<ReviewItem> getSendList() {
        return sendList;
    }

    public void setSendList(List<ReviewItem> sendList) {
        this.sendList = sendList;
    }
}
