
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

    @SerializedName("img_url")
    private String imgUrl;

    public String getImgUrl() {
        return imgUrl;
    }

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
