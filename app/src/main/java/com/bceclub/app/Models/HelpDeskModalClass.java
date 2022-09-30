package com.bceclub.app.Models;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class HelpDeskModalClass {
    @SerializedName("success")
    private String success;
    @SerializedName("help_desk_list")
    private List<HelpDesk> helpDeskList = null;

    public String getSuccess() {
        return success;
    }

    public void setSuccess(String success) {
        this.success = success;
    }

    public List<HelpDesk> getHelpDeskList() {
        return helpDeskList;
    }

    public void setHelpDeskList(List<HelpDesk> helpDeskList) {
        this.helpDeskList = helpDeskList;
    }

    public static class HelpDesk {

        @SerializedName("ticket_date")
        private String ticketDate;
        @SerializedName("ticket_type")
        private String ticketType;
        @SerializedName("message")
        private String message;
        @SerializedName("reply_date")
        private String replyDate;

        public String getTicketDate() {
            return ticketDate;
        }

        public void setTicketDate(String ticketDate) {
            this.ticketDate = ticketDate;
        }

        public String getTicketType() {
            return ticketType;
        }

        public void setTicketType(String ticketType) {
            this.ticketType = ticketType;
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }

        public String getReplyDate() {
            return replyDate;
        }

        public void setReplyDate(String replyDate) {
            this.replyDate = replyDate;
        }

    }
}
