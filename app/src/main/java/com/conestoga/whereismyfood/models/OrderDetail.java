package com.conestoga.whereismyfood.models;

import com.google.gson.annotations.SerializedName;

public class OrderDetail {

    @SerializedName("order_id")
    private String orderId;

    @SerializedName("user_id")
    private String userId;

    @SerializedName("sub_id")
    private String subId;

    @SerializedName("receiver_name")
    private String receiverName;

    @SerializedName("phone_number")
    private String phoneNumber;

    @SerializedName("email_id")
    private String emailId;

    @SerializedName("days_selected")
    private String daysSelected;

    @SerializedName("time")
    private String time;

    @SerializedName("address_id")
    private String addressId;

    @SerializedName("order_total")
    private String orderTotal;

    @SerializedName("order_date")
    private String orderDate;

    @SerializedName("sub_name")
    private String subName;

    @SerializedName("vendor_name")
    private String vendorName;

    public String getVendorName() {
        return vendorName;
    }

    public void setVendorName(String vendorName) {
        this.vendorName = vendorName;
    }

    public String getSubName() {
        return subName;
    }

    public void setSubName(String subName) {
        this.subName = subName;
    }

    public String getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(String orderDate) {
        this.orderDate = orderDate;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getSubId() {
        return subId;
    }

    public void setSubId(String subId) {
        this.subId = subId;
    }

    public String getReceiverName() {
        return receiverName;
    }

    public void setReceiverName(String receiverName) {
        this.receiverName = receiverName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getEmailId() {
        return emailId;
    }

    public void setEmailId(String emailId) {
        this.emailId = emailId;
    }

    public String getDaysSelected() {
        return daysSelected;
    }

    public void setDaysSelected(String daysSelected) {
        this.daysSelected = daysSelected;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getAddressId() {
        return addressId;
    }

    public void setAddressId(String addressId) {
        this.addressId = addressId;
    }

    public String getOrderTotal() {
        return orderTotal;
    }

    public void setOrderTotal(String orderTotal) {
        this.orderTotal = orderTotal;
    }
}
