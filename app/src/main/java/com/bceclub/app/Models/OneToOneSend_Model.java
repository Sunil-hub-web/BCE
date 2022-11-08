package com.bceclub.app.Models;

public class OneToOneSend_Model {

    String Created_On,Meeting_On,Remark,clientName,serviceProvide,locationName,locationDetails;

    public OneToOneSend_Model(String created_On, String meeting_On, String remark, String clientName,
                              String serviceProvide, String locationName, String locationDetails) {
        Created_On = created_On;
        Meeting_On = meeting_On;
        Remark = remark;
        this.clientName = clientName;
        this.serviceProvide = serviceProvide;
        this.locationName = locationName;
        this.locationDetails = locationDetails;
    }

    public String getCreated_On() {
        return Created_On;
    }

    public void setCreated_On(String created_On) {
        Created_On = created_On;
    }

    public String getMeeting_On() {
        return Meeting_On;
    }

    public void setMeeting_On(String meeting_On) {
        Meeting_On = meeting_On;
    }

    public String getRemark() {
        return Remark;
    }

    public void setRemark(String remark) {
        Remark = remark;
    }

    public String getClientName() {
        return clientName;
    }

    public void setClientName(String clientName) {
        this.clientName = clientName;
    }

    public String getServiceProvide() {
        return serviceProvide;
    }

    public void setServiceProvide(String serviceProvide) {
        this.serviceProvide = serviceProvide;
    }

    public String getLocationName() {
        return locationName;
    }

    public void setLocationName(String locationName) {
        this.locationName = locationName;
    }

    public String getLocationDetails() {
        return locationDetails;
    }

    public void setLocationDetails(String locationDetails) {
        this.locationDetails = locationDetails;
    }
}
