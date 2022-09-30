package com.bceclub.app.Models;

public class MemberShipListModalClass {
    Membership membership;
    String profilePic;

    public MemberShipListModalClass(Membership membership, String profilePic) {
        this.membership = membership;
        this.profilePic = profilePic;
    }


    public Membership getMembership() {
        return membership;
    }

    public void setMembership(Membership membership) {
        this.membership = membership;
    }

    public String getProfilePic() {
        return profilePic;
    }

    public void setProfilePic(String profilePic) {
        this.profilePic = profilePic;
    }
}
