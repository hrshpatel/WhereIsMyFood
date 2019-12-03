package com.conestoga.whereismyfood.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class ReviewDetails implements Parcelable {

    @SerializedName("review_id")
    private String reviewId;

    @SerializedName("user_id")
    private String userId;

    @SerializedName("sub_id")
    private String subId;

    @SerializedName("ratings")
    private float ratings;

    @SerializedName("comments")
    private String comments;

    @SerializedName("date_of_rating")
    private String dateRated;

    @SerializedName("first_name")
    private String firstName;

    @SerializedName("last_name")
    private String lastName;

    @SerializedName("email_id")
    private String emailId;

    @SerializedName("user_img_url")
    private String userImage;

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmailId() {
        return emailId;
    }

    public void setEmailId(String emailId) {
        this.emailId = emailId;
    }

    public String getUserImage() {
        return userImage;
    }

    public void setUserImage(String userImage) {
        this.userImage = userImage;
    }

    public String getReviewId() {
        return reviewId;
    }

    public void setReviewId(String reviewId) {
        this.reviewId = reviewId;
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

    public float getRatings() {
        return ratings;
    }

    public void setRatings(float ratings) {
        this.ratings = ratings;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public String getDateRated() {
        return dateRated;
    }

    public void setDateRated(String dateRated) {
        this.dateRated = dateRated;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.reviewId);
        dest.writeString(this.userId);
        dest.writeString(this.subId);
        dest.writeFloat(this.ratings);
        dest.writeString(this.comments);
        dest.writeString(this.dateRated);
        dest.writeString(this.firstName);
        dest.writeString(this.lastName);
        dest.writeString(this.emailId);
        dest.writeString(this.userImage);
    }

    public ReviewDetails() {
    }

    protected ReviewDetails(Parcel in) {
        this.reviewId = in.readString();
        this.userId = in.readString();
        this.subId = in.readString();
        this.ratings = in.readFloat();
        this.comments = in.readString();
        this.dateRated = in.readString();
        this.firstName = in.readString();
        this.lastName = in.readString();
        this.emailId = in.readString();
        this.userImage = in.readString();
    }

    public static final Parcelable.Creator<ReviewDetails> CREATOR = new Parcelable.Creator<ReviewDetails>() {
        @Override
        public ReviewDetails createFromParcel(Parcel source) {
            return new ReviewDetails(source);
        }

        @Override
        public ReviewDetails[] newArray(int size) {
            return new ReviewDetails[size];
        }
    };
}
