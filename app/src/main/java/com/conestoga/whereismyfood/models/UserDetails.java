package com.conestoga.whereismyfood.models;

import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;

import com.conestoga.whereismyfood.BR;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class UserDetails extends BaseObservable {

    @SerializedName("email_id")
    private String emailId;
    @SerializedName("first_name")
    private String firstName;
    @SerializedName("id")
    private String id;
    @SerializedName("last_name")
    private String lastName;
    @SerializedName("user_type")
    private String userType;

    @SerializedName("vendor_name")
    private String vendorName;

    @SerializedName("phone_no")
    private String phoneNo;

    @SerializedName("password")
    private String password;

    @SerializedName("oldPassword")
    private String oldPassword;

    @SerializedName("newPassword")
    private String newPassword;

    @SerializedName("addressList")
    private ArrayList<AddressDetails> addressDetailsList;

    public ArrayList<AddressDetails> getAddressDetailsList() {
        return addressDetailsList;
    }

    public void setAddressDetailsList(ArrayList<AddressDetails> addressDetailsList) {
        this.addressDetailsList = addressDetailsList;
    }

    @Bindable
    public String getOldPassword() {
        return oldPassword;
    }

    public void setOldPassword(String oldPassword) {
        this.oldPassword = oldPassword;
    }

    @Bindable
    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }

    @Bindable
    public String getPhoneNo() {
        return phoneNo;
    }

    public void setPhoneNo(String mPhoneNo) {
        this.phoneNo = mPhoneNo;
        notifyPropertyChanged(com.conestoga.whereismyfood.BR.userDetails);
    }

    @Bindable
    public String getVendorName() {
        return vendorName;
    }

    public void setVendorName(String mVendorName) {
        this.vendorName = mVendorName;
        notifyPropertyChanged(com.conestoga.whereismyfood.BR.userDetails);
    }

    @Bindable
    public String getPassword() {
        return password;
    }

    public void setPassword(String mPassword) {
        this.password = mPassword;
        notifyPropertyChanged(com.conestoga.whereismyfood.BR.userDetails);
    }

    public void setEmailId(String mEmailId) {
        this.emailId = mEmailId;
        notifyPropertyChanged(com.conestoga.whereismyfood.BR.userDetails);
    }

    public void setFirstName(String mFirstName) {
        this.firstName = mFirstName;
        notifyPropertyChanged(com.conestoga.whereismyfood.BR.userDetails);
    }

    public void setId(String mId) {
        this.id = mId;
    }

    public void setLastName(String mLastName) {
        this.lastName = mLastName;
    }

    public void setUserType(String mUserType) {
        this.userType = mUserType;
    }

    @Bindable
    public String getEmailId() {
        return emailId;
    }

    @Bindable
    public String getFirstName() {
        return firstName;
    }

    @Bindable
    public String getId() {
        return id;
    }

    @Bindable
    public String getLastName() {
        return lastName;
    }

    @Bindable
    public String getUserType() {
        return userType;
    }

}
