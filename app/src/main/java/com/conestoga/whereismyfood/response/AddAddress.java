
package com.conestoga.whereismyfood.response;

import java.util.ArrayList;
import java.util.List;

import com.conestoga.whereismyfood.models.AddressDetails;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AddAddress {

    @SerializedName("data")
    private ArrayList<AddressDetails> addressDetailList;

    @SerializedName("message")
    private String message;

    @SerializedName("success")
    private String success;

    public ArrayList<AddressDetails> getAddressDetailList() {
        return addressDetailList;
    }

    public void setAddressDetailList(ArrayList<AddressDetails> addressDetailList) {
        this.addressDetailList = addressDetailList;
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
