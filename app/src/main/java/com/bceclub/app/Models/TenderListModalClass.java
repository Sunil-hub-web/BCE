package com.bceclub.app.Models;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class TenderListModalClass {
    @SerializedName("success")
    private String success;
    @SerializedName("tender_list")
    private List<Tender> tenderList = null;

    public String getSuccess() {
        return success;
    }

    public void setSuccess(String success) {
        this.success = success;
    }

    public List<Tender> getTenderList() {
        return tenderList;
    }

    public void setTenderList(List<Tender> tenderList) {
        this.tenderList = tenderList;
    }

    public static class Tender {

        @SerializedName("no_of_tender")
        private String noOfTender;
        @SerializedName("date")
        private String date;
        @SerializedName("info_file")
        private String infoFile;

        public String getNoOfTender() {
            return noOfTender;
        }

        public void setNoOfTender(String noOfTender) {
            this.noOfTender = noOfTender;
        }

        public String getDate() {
            return date;
        }

        public void setDate(String date) {
            this.date = date;
        }

        public String getInfoFile() {
            return infoFile;
        }

        public void setInfoFile(String infoFile) {
            this.infoFile = infoFile;
        }

    }
}
