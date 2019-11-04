package com.conestoga.whereismyfood.response;

import com.conestoga.whereismyfood.models.AddressDetails;
import com.conestoga.whereismyfood.models.UserDetails;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class GetUserDetails {

    @SerializedName("data")
    private UserDetails userDetails;

    @SerializedName("message")
    private String message;

    @SerializedName("success")
    private String success;

    public UserDetails getUserDetails() {
        return userDetails;
    }

    public void setUserDetails(UserDetails userDetails) {
        this.userDetails = userDetails;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getSuccess() {
        return success;
    }

    public void setSuccess(String success) {
        this.success = success;
    }
}