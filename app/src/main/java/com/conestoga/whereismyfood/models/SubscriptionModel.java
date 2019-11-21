package com.conestoga.whereismyfood.models;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class SubscriptionModel {

    @SerializedName("user_id")
    private String userId;

    @SerializedName("daily_details")
    private ArrayList<DailyDetail> dailyDetailList;

    @SerializedName("sub_id")
    private String subId;

    @SerializedName("phone_no")
    private String phone_no;

    @SerializedName("email_id")
    private String emailId;

    @SerializedName("sub_description")
    private String subDescription;

    @SerializedName("sub_name")
    private String subName;

    @SerializedName("vendor_name")
    private String vendorName;

    @SerializedName("price")
    private String price;

    private boolean isMonday;

    private boolean isTuesday;

    private boolean isWednesday;

    private boolean isThursday;

    private boolean isFriday;

    private boolean isSaturday;

    private boolean isSunday;

    @SerializedName("dish_name_mon")
    private String dishNameMon;

    @SerializedName("ingredients_mon")
    private String ingredientsMon;

    @SerializedName("dish_desc_mon")
    private String dishDescMon;

    @SerializedName("dish_name_tue")
    private String dishNameTue;

    @SerializedName("ingredients_tue")
    private String ingredientsTue;

    @SerializedName("dish_desc_tue")
    private String dishDescTue;

    @SerializedName("dish_name_wed")
    private String dishNameWed;

    @SerializedName("ingredients_wed")
    private String ingredientsWed;

    @SerializedName("dish_desc_wed")
    private String dishDescWed;

    @SerializedName("dish_name_thurs")
    private String dishNameThurs;

    @SerializedName("ingredients_thurs")
    private String ingredientsThurs;

    @SerializedName("dish_desc_thurs")
    private String dishDescThurs;

    @SerializedName("dish_name_fri")
    private String dishNameFri;

    @SerializedName("ingredients_fri")
    private String ingredientsFri;

    @SerializedName("dish_desc_fri")
    private String dishDescFri;

    @SerializedName("dish_name_sat")
    private String dishNameSat;

    @SerializedName("ingredients_sat")
    private String ingredientsSat;

    @SerializedName("dish_desc_sat")
    private String dishDescSat;

    @SerializedName("dish_name_sun")
    private String dishNameSun;

    @SerializedName("ingredients_sun")
    private String ingredientsSun;

    @SerializedName("dish_desc_sun")
    private String dishDescSun;

    @SerializedName("images")
    private ArrayList<String> imageList;

    public ArrayList<DailyDetail> getDailyDetailList() {
        return dailyDetailList;
    }

    public void setDailyDetailList(ArrayList<DailyDetail> dailyDetailList) {
        this.dailyDetailList = dailyDetailList;
    }
    
    public ArrayList<String> getImageList() {
        return imageList;
    }

    public void setImageList(ArrayList<String> imageList) {
        this.imageList = imageList;
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

    public String getPhone_no() {
        return phone_no;
    }

    public void setPhone_no(String phone_no) {
        this.phone_no = phone_no;
    }

    public String getEmailId() {
        return emailId;
    }

    public void setEmailId(String emailId) {
        this.emailId = emailId;
    }

    public String getSubDescription() {
        return subDescription;
    }

    public void setSubDescription(String subDescription) {
        this.subDescription = subDescription;
    }

    public String getSubName() {
        return subName;
    }

    public void setSubName(String subName) {
        this.subName = subName;
    }

    public String getVendorName() {
        return vendorName;
    }

    public void setVendorName(String vendorName) {
        this.vendorName = vendorName;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public boolean isMonday() {
        return isMonday;
    }

    public void setMonday(boolean monday) {
        isMonday = monday;
    }

    public boolean isTuesday() {
        return isTuesday;
    }

    public void setTuesday(boolean tuesday) {
        isTuesday = tuesday;
    }

    public boolean isWednesday() {
        return isWednesday;
    }

    public void setWednesday(boolean wednesday) {
        isWednesday = wednesday;
    }

    public boolean isThursday() {
        return isThursday;
    }

    public void setThursday(boolean thursday) {
        isThursday = thursday;
    }

    public boolean isFriday() {
        return isFriday;
    }

    public void setFriday(boolean friday) {
        isFriday = friday;
    }

    public boolean isSaturday() {
        return isSaturday;
    }

    public void setSaturday(boolean saturday) {
        isSaturday = saturday;
    }

    public boolean isSunday() {
        return isSunday;
    }

    public void setSunday(boolean sunday) {
        isSunday = sunday;
    }

    public String getDishNameMon() {
        return dishNameMon;
    }

    public void setDishNameMon(String dishNameMon) {
        this.dishNameMon = dishNameMon;
    }

    public String getIngredientsMon() {
        return ingredientsMon;
    }

    public void setIngredientsMon(String ingredientsMon) {
        this.ingredientsMon = ingredientsMon;
    }

    public String getDishDescMon() {
        return dishDescMon;
    }

    public void setDishDescMon(String dishDescMon) {
        this.dishDescMon = dishDescMon;
    }

    public String getDishNameTue() {
        return dishNameTue;
    }

    public void setDishNameTue(String dishNameTue) {
        this.dishNameTue = dishNameTue;
    }

    public String getIngredientsTue() {
        return ingredientsTue;
    }

    public void setIngredientsTue(String ingredientsTue) {
        this.ingredientsTue = ingredientsTue;
    }

    public String getDishDescTue() {
        return dishDescTue;
    }

    public void setDishDescTue(String dishDescTue) {
        this.dishDescTue = dishDescTue;
    }

    public String getDishNameWed() {
        return dishNameWed;
    }

    public void setDishNameWed(String dishNameWed) {
        this.dishNameWed = dishNameWed;
    }

    public String getIngredientsWed() {
        return ingredientsWed;
    }

    public void setIngredientsWed(String ingredientsWed) {
        this.ingredientsWed = ingredientsWed;
    }

    public String getDishDescWed() {
        return dishDescWed;
    }

    public void setDishDescWed(String dishDescWed) {
        this.dishDescWed = dishDescWed;
    }

    public String getDishNameThurs() {
        return dishNameThurs;
    }

    public void setDishNameThurs(String dishNameThurs) {
        this.dishNameThurs = dishNameThurs;
    }

    public String getIngredientsThurs() {
        return ingredientsThurs;
    }

    public void setIngredientsThurs(String ingredientsThurs) {
        this.ingredientsThurs = ingredientsThurs;
    }

    public String getDishDescThurs() {
        return dishDescThurs;
    }

    public void setDishDescThurs(String dishDescThurs) {
        this.dishDescThurs = dishDescThurs;
    }

    public String getDishNameFri() {
        return dishNameFri;
    }

    public void setDishNameFri(String dishNameFri) {
        this.dishNameFri = dishNameFri;
    }

    public String getIngredientsFri() {
        return ingredientsFri;
    }

    public void setIngredientsFri(String ingredientsFri) {
        this.ingredientsFri = ingredientsFri;
    }

    public String getDishDescFri() {
        return dishDescFri;
    }

    public void setDishDescFri(String dishDescFri) {
        this.dishDescFri = dishDescFri;
    }

    public String getDishNameSat() {
        return dishNameSat;
    }

    public void setDishNameSat(String dishNameSat) {
        this.dishNameSat = dishNameSat;
    }

    public String getIngredientsSat() {
        return ingredientsSat;
    }

    public void setIngredientsSat(String ingredientsSat) {
        this.ingredientsSat = ingredientsSat;
    }

    public String getDishDescSat() {
        return dishDescSat;
    }

    public void setDishDescSat(String dishDescSat) {
        this.dishDescSat = dishDescSat;
    }

    public String getDishNameSun() {
        return dishNameSun;
    }

    public void setDishNameSun(String dishNameSun) {
        this.dishNameSun = dishNameSun;
    }

    public String getIngredientsSun() {
        return ingredientsSun;
    }

    public void setIngredientsSun(String ingredientsSun) {
        this.ingredientsSun = ingredientsSun;
    }

    public String getDishDescSun() {
        return dishDescSun;
    }

    public void setDishDescSun(String dishDescSun) {
        this.dishDescSun = dishDescSun;
    }
}
