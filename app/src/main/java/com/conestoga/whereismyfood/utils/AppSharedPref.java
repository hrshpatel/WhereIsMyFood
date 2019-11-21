package com.conestoga.whereismyfood.utils;


import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

/**
 * {@link AppSharedPref} : This Singleton class handles to store data corresponding App details which
 * are stored in {@link SharedPreferences}
 *
 * @author Harsh Patel
 * @Date : 10/28/2019
 */

public class AppSharedPref {

    private static AppSharedPref instance = null;
    private SharedPreferences sh;

    private AppSharedPref(Context mContext) {
        sh = PreferenceManager.getDefaultSharedPreferences(mContext);

    }

    /**
     * @param mContext
     * @return {@link AppSharedPref}
     */
    public synchronized static AppSharedPref getInstance(Context mContext) {

        if (instance == null) {
            instance = new AppSharedPref(mContext);
        }
        return instance;
    }

    public void clear() {
        sh.edit().clear().apply();
    }


    public void clearData() {
        sh.edit().clear().apply();
    }

    public String getUserId() {
        return sh.getString("uid", "");
    }

    public void setUserId(String uid) {
        sh.edit().putString("uid", uid).apply();
    }

    public void setFirstName(String firstName) {
        sh.edit().putString("first_name", firstName).apply();
    }

    public String getFirstName() {
        return sh.getString("first_name", "");
    }

    public void setLastName(String lastName) {
        sh.edit().putString("last_name", lastName).apply();
    }

    public String getLastName() {
        return sh.getString("last_name", "");
    }

    public void setUserType(String userType) {
        sh.edit().putString("user_type", userType).apply();
    }

    public String getUserType() {
        return sh.getString("user_type", "");
    }

    public void setEmailId(String emailId) {
        sh.edit().putString("email_id", emailId).apply();
    }

    public String getEmailId() {
        return sh.getString("email_id", "");
    }

    public void setVendorName(String vendorName) {
        sh.edit().putString("vendor_name", vendorName).apply();
    }

    public String getVendorName() {
        return sh.getString("vendor_name", "");
    }

    public void setPhoneNumber(String phoneNumber) {
        sh.edit().putString("phone_no", phoneNumber).apply();
    }

    public String getPhoneNumber() {
        return sh.getString("phone_no", "");
    }

    public void setIsLogin(boolean isLogin) {
        sh.edit().putBoolean("isLogin", isLogin).apply();
    }

    public boolean getIsLogin() {
        return sh.getBoolean("isLogin", false);
    }

    public void logout() {
        setIsLogin(false);
        setEmailId("");
        setFirstName("");
        setLastName("");
        setPhoneNumber("");
        setUserId("");
        setUserType("");
        setVendorName("");
    }

}
