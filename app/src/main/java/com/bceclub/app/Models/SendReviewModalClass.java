package com.bceclub.app.Models;

public class SendReviewModalClass {
    String user_id;
    String rv_to_urid;
    String rv_rating;
    String rv_remark;

    public SendReviewModalClass(String user_id, String rv_to_urid, String rv_rating, String rv_remark) {
        this.user_id = user_id;
        this.rv_to_urid = rv_to_urid;
        this.rv_rating = rv_rating;
        this.rv_remark = rv_remark;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getRv_to_urid() {
        return rv_to_urid;
    }

    public void setRv_to_urid(String rv_to_urid) {
        this.rv_to_urid = rv_to_urid;
    }

    public String getRv_rating() {
        return rv_rating;
    }

    public void setRv_rating(String rv_rating) {
        this.rv_rating = rv_rating;
    }

    public String getRv_remark() {
        return rv_remark;
    }

    public void setRv_remark(String rv_remark) {
        this.rv_remark = rv_remark;
    }
}
