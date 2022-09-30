package com.bceclub.app.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class LoginModalClass {

    @SerializedName("success")
    public String success;
    @SerializedName("msg")
    public String msg;
    @SerializedName("id")
    public String id;
    @SerializedName("name")
    @Expose
    public String name;
    @SerializedName("email")
    @Expose
    public String email;
    @SerializedName("contact_no")
    @Expose
    public String contact_no;

}
