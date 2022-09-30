package com.bceclub.app.Models;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class PaymentModalClass {
    @SerializedName("success")
    private String success;
    @SerializedName("payment_list")
    private List<Payment> paymentList = null;

    public String getSuccess() {
        return success;
    }

    public void setSuccess(String success) {
        this.success = success;
    }

    public List<Payment> getPaymentList() {
        return paymentList;
    }

    public void setPaymentList(List<Payment> paymentList) {
        this.paymentList = paymentList;
    }

    public static class Payment {

        @SerializedName("date")
        private String date;
        @SerializedName("type")
        private String type;
        @SerializedName("ref_id")
        private String refId;
        @SerializedName("currency")
        private String currency;
        @SerializedName("method")
        private String method;
        @SerializedName("number")
        private String number;
        @SerializedName("amount")
        private String amount;
        @SerializedName("gst")
        private String gst;
        @SerializedName("paid_amount")
        private String paidAmount;

        public String getDate() {
            return date;
        }

        public void setDate(String date) {
            this.date = date;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getRefId() {
            return refId;
        }

        public void setRefId(String refId) {
            this.refId = refId;
        }

        public String getCurrency() {
            return currency;
        }

        public void setCurrency(String currency) {
            this.currency = currency;
        }

        public String getMethod() {
            return method;
        }

        public void setMethod(String method) {
            this.method = method;
        }

        public String getNumber() {
            return number;
        }

        public void setNumber(String number) {
            this.number = number;
        }

        public String getAmount() {
            return amount;
        }

        public void setAmount(String amount) {
            this.amount = amount;
        }

        public String getGst() {
            return gst;
        }

        public void setGst(String gst) {
            this.gst = gst;
        }

        public String getPaidAmount() {
            return paidAmount;
        }

        public void setPaidAmount(String paidAmount) {
            this.paidAmount = paidAmount;
        }

    }
}
