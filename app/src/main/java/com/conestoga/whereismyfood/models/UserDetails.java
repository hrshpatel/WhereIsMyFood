package com.conestoga.whereismyfood.models;

import com.google.gson.annotations.SerializedName;

public class UserDetails {

    @SerializedName("email_id")
    private String mEmailId;
    @SerializedName("first_name")
    private String mFirstName;
    @SerializedName("id")
    private String mId;
    @SerializedName("last_name")
    private String mLastName;
    @SerializedName("user_type")
    private String mUserType;

    @SerializedName("password")
    private String mPassword;

    public String getPassword() {
        return mPassword;
    }

    public void setPassword(String mPassword) {
        this.mPassword = mPassword;
    }

    public void setEmailId(String mEmailId) {
        this.mEmailId = mEmailId;
    }

    public void setFirstName(String mFirstName) {
        this.mFirstName = mFirstName;
    }

    public void setId(String mId) {
        this.mId = mId;
    }

    public void setLastName(String mLastName) {
        this.mLastName = mLastName;
    }

    public void setUserType(String mUserType) {
        this.mUserType = mUserType;
    }

    public String getEmailId() {
        return mEmailId;
    }

    public String getFirstName() {
        return mFirstName;
    }

    public String getId() {
        return mId;
    }

    public String getLastName() {
        return mLastName;
    }

    public String getUserType() {
        return mUserType;
    }

}
