package com.bceclub.app.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class onetonedet_model {

    @SerializedName("success")
    @Expose
    private String success;
    @SerializedName("receive_list")
    @Expose
    private List<OneToOneReceive_Model> receiveList = null;
    @SerializedName("send_list")
    @Expose
    private List<OneToOneSend_Model> sendList = null;

    public String getSuccess() {
        return success;
    }

    public void setSuccess(String success) {
        this.success = success;
    }

    public List<OneToOneReceive_Model> getReceiveList() {
        return receiveList;
    }

    public void setReceiveList(List<OneToOneReceive_Model> receiveList) {
        this.receiveList = receiveList;
    }

    public List<OneToOneSend_Model> getSendList() {
        return sendList;
    }

    public void setSendList(List<OneToOneSend_Model> sendList) {
        this.sendList = sendList;
    }
}
