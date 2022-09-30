package com.bceclub.app.Models;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class MembersList {
    @SerializedName("success")
    private String success;
    @SerializedName("membership_list")
    private List<Membership> membershipList = null;

    public String getSuccess() {
        return success;
    }

    public void setSuccess(String success) {
        this.success = success;
    }

    public List<Membership> getMembershipList() {
        return membershipList;
    }

    public void setMembershipList(List<Membership> membershipList) {
        this.membershipList = membershipList;
    }
}
