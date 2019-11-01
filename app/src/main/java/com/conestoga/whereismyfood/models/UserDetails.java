package com.conestoga.whereismyfood.models;

import com.google.gson.annotations.SerializedName;

public class UserDetails {

    @SerializedName("email_id")
    private String emailId;
    @SerializedName("first_name")
    private String firstName;
    @SerializedName("id")
    private String id;
    @SerializedName("last_name")
    private String lastName;
    @SerializedName("user_type")
    private String userType;

    @SerializedName("vendor_name")
    private String vendorName;

    @SerializedName("phone_no")
    private String phoneNo;

    @SerializedName("password")
    private String password;

    @SerializedName("oldPassword")
    private String oldPassword;

    @SerializedName("newPassword")
    private String newPassword;

    public String getOldPassword() {
        return oldPassword;
    }

    public void setOldPassword(String oldPassword) {
        this.oldPassword = oldPassword;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }

    public String getPhoneNo() {
        return phoneNo;
    }

    public void setPhoneNo(String mPhoneNo) {
        this.phoneNo = mPhoneNo;
    }

    public String getVendorName() {
        return vendorName;
    }

    public void setVendorName(String mVendorName) {
        this.vendorName = mVendorName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String mPassword) {
        this.password = mPassword;
    }

    public void setEmailId(String mEmailId) {
        this.emailId = mEmailId;
    }

    public void setFirstName(String mFirstName) {
        this.firstName = mFirstName;
    }

    public void setId(String mId) {
        this.id = mId;
    }

    public void setLastName(String mLastName) {
        this.lastName = mLastName;
    }

    public void setUserType(String mUserType) {
        this.userType = mUserType;
    }

    public String getEmailId() {
        return emailId;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getId() {
        return id;
    }

    public String getLastName() {
        return lastName;
    }

    public String getUserType() {
        return userType;
    }

}
