
package com.conestoga.whereismyfood.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class DailyDetail implements Parcelable {

    @Expose
    private String day;
    @SerializedName("dish_desc")
    private String dishDesc;
    @SerializedName("dish_name")
    private String dishName;
    @Expose
    private String ingredients;
    @SerializedName("sub_id")
    private String subId;

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public String getDishDesc() {
        return dishDesc;
    }

    public void setDishDesc(String dishDesc) {
        this.dishDesc = dishDesc;
    }

    public String getDishName() {
        return dishName;
    }

    public void setDishName(String dishName) {
        this.dishName = dishName;
    }

    public String getIngredients() {
        return ingredients;
    }

    public void setIngredients(String ingredients) {
        this.ingredients = ingredients;
    }

    public String getSubId() {
        return subId;
    }

    public void setSubId(String subId) {
        this.subId = subId;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.day);
        dest.writeString(this.dishDesc);
        dest.writeString(this.dishName);
        dest.writeString(this.ingredients);
        dest.writeString(this.subId);
    }

    public DailyDetail() {
    }

    protected DailyDetail(Parcel in) {
        this.day = in.readString();
        this.dishDesc = in.readString();
        this.dishName = in.readString();
        this.ingredients = in.readString();
        this.subId = in.readString();
    }

    public static final Parcelable.Creator<DailyDetail> CREATOR = new Parcelable.Creator<DailyDetail>() {
        @Override
        public DailyDetail createFromParcel(Parcel source) {
            return new DailyDetail(source);
        }

        @Override
        public DailyDetail[] newArray(int size) {
            return new DailyDetail[size];
        }
    };
}
