package com.conestoga.whereismyfood.response;

import com.conestoga.whereismyfood.models.OrderDetail;
import com.conestoga.whereismyfood.models.UserDetails;
import com.google.gson.annotations.SerializedName;

public class CheckOut {

    @SerializedName("data")
    private OrderDetail orderDetail;

    @SerializedName("message")
    private String message;

    @SerializedName("success")
    private String success;

    public OrderDetail getOrderDetail() {
        return orderDetail;
    }

    public void setOrderDetail(OrderDetail orderDetail) {
        this.orderDetail = orderDetail;
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