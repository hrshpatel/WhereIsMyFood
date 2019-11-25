
package com.conestoga.whereismyfood.response;

import com.conestoga.whereismyfood.models.SubscriptionModel;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class GetSubById {

    @SerializedName("data")
    private SubscriptionModel subscriptionModel;
    @Expose
    private String message;
    @Expose
    private String success;

    public SubscriptionModel getSubscriptionModel() {
        return subscriptionModel;
    }

    public void setSubscriptionModel(SubscriptionModel subscriptionModel) {
        this.subscriptionModel = subscriptionModel;
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
