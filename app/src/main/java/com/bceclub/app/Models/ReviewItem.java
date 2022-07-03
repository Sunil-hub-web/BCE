package com.bceclub.app.Models;

import com.google.gson.annotations.SerializedName;

public class ReviewItem {
    @SerializedName("date")
    private String date;
    @SerializedName("rating")
    private String rating;
    @SerializedName("remark")
    private String remark;
    @SerializedName("receive_from")
    private ReceiveFrom receiveFrom;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
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
}
