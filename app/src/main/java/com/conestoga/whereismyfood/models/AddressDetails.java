package com.conestoga.whereismyfood.models;

import com.google.gson.annotations.SerializedName;

public class AddressDetails {

    @SerializedName("user_id")
    private String userId;

    @SerializedName("email_id")
    private String emailId;

    @SerializedName("suite_no")
    private String suite_no;

    @SerializedName("street")
    private String street;

    @SerializedName("city")
    private String city;

    @SerializedName("province")
    private String province;

    @SerializedName("zipcode")
    private String zipCode;

    @SerializedName("phone_no")
    private String phoneNo;

    @SerializedName("name")
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getEmailId() {
        return emailId;
    }

    public void setEmailId(String emailId) {
        this.emailId = emailId;
    }

    public String getSuite_no() {
        return suite_no;
    }

    public void setSuite_no(String suite_no) {
        this.suite_no = suite_no;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getZipCode() {
        return zipCode;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

    public String getPhoneNo() {
        return phoneNo;
    }

    public void setPhoneNo(String phoneNo) {
        this.phoneNo = phoneNo;
    }
}
