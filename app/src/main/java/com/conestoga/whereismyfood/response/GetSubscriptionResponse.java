
package com.conestoga.whereismyfood.response;

import com.conestoga.whereismyfood.models.SubscriptionModel;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class GetSubscriptionResponse {

    @SerializedName("data")
    private ArrayList<SubscriptionModel> subscriptionModelList;
    @Expose
    private String message;
    @Expose
    private String success;

    public ArrayList<SubscriptionModel> getSubscriptionModelList() {
        return subscriptionModelList;
    }

    public void setSubscriptionModelList(ArrayList<SubscriptionModel> subscriptionModelList) {
        this.subscriptionModelList = subscriptionModelList;
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
