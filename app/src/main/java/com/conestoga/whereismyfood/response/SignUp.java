
package com.conestoga.whereismyfood.response;

import com.conestoga.whereismyfood.models.UserDetails;
import com.google.gson.annotations.SerializedName;

public class SignUp {

    @SerializedName("data")
    private UserDetails mUserDetails;

    @SerializedName("message")
    private String mMessage;

    @SerializedName("success")
    private int mSuccess;

    public UserDetails getUserDetails() {
        return mUserDetails;
    }

    public String getMessage() {
        return mMessage;
    }

    public int getSuccess() {
        return mSuccess;
    }

}
