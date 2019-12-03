
package com.conestoga.whereismyfood.response;

import com.conestoga.whereismyfood.models.AddressDetails;
import com.conestoga.whereismyfood.models.ReviewDetails;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class ReviewResponse {

    @SerializedName("data")
    private ArrayList<ReviewDetails> reviewDetailsArrayList;

    @SerializedName("message")
    private String message;

    @SerializedName("success")
    private String success;

    public ArrayList<ReviewDetails> getReviewDetailsArrayList() {
        return reviewDetailsArrayList;
    }

    public void setReviewDetailsArrayList(ArrayList<ReviewDetails> reviewDetailsArrayList) {
        this.reviewDetailsArrayList = reviewDetailsArrayList;
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
