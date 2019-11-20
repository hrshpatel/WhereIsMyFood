
package com.conestoga.whereismyfood.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class DailyDetail {

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

}
