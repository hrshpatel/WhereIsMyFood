
package com.conestoga.whereismyfood.response;

import com.conestoga.whereismyfood.models.AddressDetails;
import com.conestoga.whereismyfood.models.OrderDetail;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class OrdersResponse {

    @SerializedName("data")
    private ArrayList<OrderDetail> orderDetailArrayList;

    @SerializedName("message")
    private String message;

    @SerializedName("success")
    private String success;

    public ArrayList<OrderDetail> getOrderDetailArrayList() {
        return orderDetailArrayList;
    }

    public void setOrderDetailArrayList(ArrayList<OrderDetail> orderDetailArrayList) {
        this.orderDetailArrayList = orderDetailArrayList;
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
