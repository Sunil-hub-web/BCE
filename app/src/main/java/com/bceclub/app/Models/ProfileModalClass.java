package com.bceclub.app.Models;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ProfileModalClass {

    @SerializedName("success")
    @Expose
    private String success;
    @SerializedName("msg")
    @Expose
    private String msg;
    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("img")
    @Expose
    private String img;
    @SerializedName("user_info")
    @Expose
    private List<UserInfoModalClass> userInfo = null;

    public String getSuccess() {
        return success;
    }

    public void setSuccess(String success) {
        this.success = success;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public List<UserInfoModalClass> getUserInfo() {
        return userInfo;
    }

    public void setUserInfo(List<UserInfoModalClass> userInfo) {
        this.userInfo = userInfo;
    }

}