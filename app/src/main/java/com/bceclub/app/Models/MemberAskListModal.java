package com.bceclub.app.Models;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class MemberAskListModal {
    @SerializedName("success")
    private String success;
    @SerializedName("ask_history")
    private List<AskHistory> askHistory = null;

    public String getSuccess() {
        return success;
    }

    public void setSuccess(String success) {
        this.success = success;
    }

    public List<AskHistory> getAskHistory() {
        return askHistory;
    }

    public void setAskHistory(List<AskHistory> askHistory) {
        this.askHistory = askHistory;
    }

    public class AskHistory {

        @SerializedName("meeting_date")
        private String meetingDate;
        @SerializedName("name")
        private String name;
        @SerializedName("deparment")
        private String deparment;
        @SerializedName("conmpany")
        private String conmpany;
        @SerializedName("reason")
        private String reason;

        public String getMeetingDate() {
            return meetingDate;
        }

        public void setMeetingDate(String meetingDate) {
            this.meetingDate = meetingDate;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getDeparment() {
            return deparment;
        }

        public void setDeparment(String deparment) {
            this.deparment = deparment;
        }

        public String getConmpany() {
            return conmpany;
        }

        public void setConmpany(String conmpany) {
            this.conmpany = conmpany;
        }

        public String getReason() {
            return reason;
        }

        public void setReason(String reason) {
            this.reason = reason;
        }

    }

}
