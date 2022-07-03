package com.bceclub.app.Models;

public class Members {
    String profilePic;
    String name;
    String clubName;
    String designation;

    public Members(String profilePic, String name, String clubName, String designation) {
        this.profilePic = profilePic;
        this.name = name;
        this.clubName = clubName;
        this.designation = designation;
    }

    public String getProfilePic() {
        return profilePic;
    }

    public void setProfilePic(String profilePic) {
        this.profilePic = profilePic;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getClubName() {
        return clubName;
    }

    public void setClubName(String clubName) {
        this.clubName = clubName;
    }

    public String getDesignation() {
        return designation;
    }

    public void setDesignation(String designation) {
        this.designation = designation;
    }
}
