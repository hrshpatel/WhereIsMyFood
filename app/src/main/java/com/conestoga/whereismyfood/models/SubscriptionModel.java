package com.conestoga.whereismyfood.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.conestoga.whereismyfood.utils.CommonUtils;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class SubscriptionModel implements Parcelable {

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

    private String ratings;

    private String ratingCount;

    public String getRatings() {
        return ratings;
    }

    public void setRatings(String ratings) {
        this.ratings = ratings;
    }

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

    public String getRatingCount() {
        return ratingCount;
    }

    public void setRatingCount(String ratingCount) {
        this.ratingCount = ratingCount;
    }

    public boolean isMonday() {
        if (isMonday) {
            return isMonday;
        }
        if (CommonUtils.isNullString(dishNameMon)) {
            isMonday = false;
            return isMonday;
        } else {
            isMonday = true;
            return isMonday;
        }
    }

    public void setMonday(boolean monday) {
        isMonday = monday;
    }

    public boolean isTuesday() {
        if (isTuesday) {
            return isTuesday;
        }
        if (CommonUtils.isNullString(dishNameTue)) {
            isTuesday = false;
            return isTuesday;
        } else {
            isTuesday = true;
            return isTuesday;
        }
    }

    public void setTuesday(boolean tuesday) {
        isTuesday = tuesday;
    }

    public boolean isWednesday() {
        if (isWednesday) {
            return isWednesday;
        }
        if (CommonUtils.isNullString(dishNameWed)) {
            isWednesday = false;
            return isWednesday;
        } else {
            isWednesday = true;
            return isWednesday;
        }
    }

    public void setWednesday(boolean wednesday) {
        isWednesday = wednesday;
    }

    public boolean isThursday() {
        if (isThursday) {
            return isThursday;
        }
        if (CommonUtils.isNullString(dishNameThurs)) {
            isThursday = false;
            return isThursday;
        } else {
            isThursday = true;
            return isThursday;
        }
    }

    public void setThursday(boolean thursday) {
        isThursday = thursday;
    }

    public boolean isFriday() {
        if (isFriday) {
            return isFriday;
        }
        if (CommonUtils.isNullString(dishNameFri)) {
            isFriday = false;
            return isFriday;
        } else {
            isFriday = true;
            return isFriday;
        }
    }

    public void setFriday(boolean friday) {
        isFriday = friday;
    }

    public boolean isSaturday() {
        if (isSaturday) {
            return isSaturday;
        }
        if (CommonUtils.isNullString(dishNameSat)) {
            isSaturday = false;
            return isSaturday;
        } else {
            isSaturday = true;
            return isSaturday;
        }
    }

    public void setSaturday(boolean saturday) {
        isSaturday = saturday;
    }

    public boolean isSunday() {
        if (isSunday) {
            return isSunday;
        }
        if (CommonUtils.isNullString(dishNameSun)) {
            isSunday = false;
            return isSunday;
        } else {
            isSunday = true;
            return isSunday;
        }
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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.userId);
        dest.writeList(this.dailyDetailList);
        dest.writeString(this.subId);
        dest.writeString(this.phone_no);
        dest.writeString(this.emailId);
        dest.writeString(this.subDescription);
        dest.writeString(this.subName);
        dest.writeString(this.vendorName);
        dest.writeString(this.price);
        dest.writeByte(this.isMonday ? (byte) 1 : (byte) 0);
        dest.writeByte(this.isTuesday ? (byte) 1 : (byte) 0);
        dest.writeByte(this.isWednesday ? (byte) 1 : (byte) 0);
        dest.writeByte(this.isThursday ? (byte) 1 : (byte) 0);
        dest.writeByte(this.isFriday ? (byte) 1 : (byte) 0);
        dest.writeByte(this.isSaturday ? (byte) 1 : (byte) 0);
        dest.writeByte(this.isSunday ? (byte) 1 : (byte) 0);
        dest.writeString(this.dishNameMon);
        dest.writeString(this.ingredientsMon);
        dest.writeString(this.dishDescMon);
        dest.writeString(this.dishNameTue);
        dest.writeString(this.ingredientsTue);
        dest.writeString(this.dishDescTue);
        dest.writeString(this.dishNameWed);
        dest.writeString(this.ingredientsWed);
        dest.writeString(this.dishDescWed);
        dest.writeString(this.dishNameThurs);
        dest.writeString(this.ingredientsThurs);
        dest.writeString(this.dishDescThurs);
        dest.writeString(this.dishNameFri);
        dest.writeString(this.ingredientsFri);
        dest.writeString(this.dishDescFri);
        dest.writeString(this.dishNameSat);
        dest.writeString(this.ingredientsSat);
        dest.writeString(this.dishDescSat);
        dest.writeString(this.dishNameSun);
        dest.writeString(this.ingredientsSun);
        dest.writeString(this.dishDescSun);
        dest.writeStringList(this.imageList);
        dest.writeString(this.ratings);
        dest.writeString(this.ratingCount);
    }

    public SubscriptionModel() {
    }

    protected SubscriptionModel(Parcel in) {
        this.userId = in.readString();
        this.dailyDetailList = new ArrayList<DailyDetail>();
        in.readList(this.dailyDetailList, DailyDetail.class.getClassLoader());
        this.subId = in.readString();
        this.phone_no = in.readString();
        this.emailId = in.readString();
        this.subDescription = in.readString();
        this.subName = in.readString();
        this.vendorName = in.readString();
        this.price = in.readString();
        this.isMonday = in.readByte() != 0;
        this.isTuesday = in.readByte() != 0;
        this.isWednesday = in.readByte() != 0;
        this.isThursday = in.readByte() != 0;
        this.isFriday = in.readByte() != 0;
        this.isSaturday = in.readByte() != 0;
        this.isSunday = in.readByte() != 0;
        this.dishNameMon = in.readString();
        this.ingredientsMon = in.readString();
        this.dishDescMon = in.readString();
        this.dishNameTue = in.readString();
        this.ingredientsTue = in.readString();
        this.dishDescTue = in.readString();
        this.dishNameWed = in.readString();
        this.ingredientsWed = in.readString();
        this.dishDescWed = in.readString();
        this.dishNameThurs = in.readString();
        this.ingredientsThurs = in.readString();
        this.dishDescThurs = in.readString();
        this.dishNameFri = in.readString();
        this.ingredientsFri = in.readString();
        this.dishDescFri = in.readString();
        this.dishNameSat = in.readString();
        this.ingredientsSat = in.readString();
        this.dishDescSat = in.readString();
        this.dishNameSun = in.readString();
        this.ingredientsSun = in.readString();
        this.dishDescSun = in.readString();
        this.imageList = in.createStringArrayList();
        this.ratings = in.readString();
        this.ratingCount = in.readString();
    }

    public static final Parcelable.Creator<SubscriptionModel> CREATOR = new Parcelable.Creator<SubscriptionModel>() {
        @Override
        public SubscriptionModel createFromParcel(Parcel source) {
            return new SubscriptionModel(source);
        }

        @Override
        public SubscriptionModel[] newArray(int size) {
            return new SubscriptionModel[size];
        }
    };
}
