package com.conestoga.whereismyfood.apiutils;

import com.conestoga.whereismyfood.models.AddressDetails;
import com.conestoga.whereismyfood.models.OrderDetail;
import com.conestoga.whereismyfood.models.ReviewDetails;
import com.conestoga.whereismyfood.models.SubscriptionModel;
import com.conestoga.whereismyfood.models.UserDetails;
import com.conestoga.whereismyfood.response.AddAddress;
import com.conestoga.whereismyfood.response.CheckOut;
import com.conestoga.whereismyfood.response.GetSubById;
import com.conestoga.whereismyfood.response.GetSubscriptionResponse;
import com.conestoga.whereismyfood.response.GetUserDetails;
import com.conestoga.whereismyfood.response.OrdersResponse;
import com.conestoga.whereismyfood.response.ReviewResponse;
import com.conestoga.whereismyfood.response.SignUp;

import java.util.List;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface APIInterface {

    @POST("register.php")
    Call<SignUp> registerUser(@Body UserDetails userDetails);

    @Multipart
    @POST("updateAccount.php")
    Call<SignUp> updateAccount(@Part("json") RequestBody jsonBody, @Part MultipartBody.Part imageFile);

    @POST("login.php")
    Call<SignUp> loginUser(@Body UserDetails userDetails);

    @POST("addAddress.php")
    Call<AddAddress> addAddress(@Body AddressDetails addressDetails);

    @GET("getUserDetails.php")
    Call<GetUserDetails> getUserDetails(@Query("email_id") String emailId);

    @Multipart
    @POST("addSubscription.php")
    Call<SignUp> addSubscription(@Part("json") RequestBody jsonBody, @Part List<MultipartBody.Part> imageFiles);

    @GET("getSubscription.php")
    Call<GetSubscriptionResponse> getSubscription(@Query("email_id") String emailId);

    @GET("getSubscription.php")
    Call<GetSubscriptionResponse> getSubscription();

    @GET("getSubById.php")
    Call<GetSubById> getSubById(@Query("sub_id") String subId);

    @POST("checkOut.php")
    Call<CheckOut> checkOut(@Body OrderDetail orderDetail);

    @POST("addReview.php")
    Call<ReviewResponse> addReview(@Body ReviewDetails reviewDetails);

    @GET("getOrderHistory.php")
    Call<OrdersResponse> getOrderHistory(@Query("user_id") String userId);

    @GET("getOrderHistory.php")
    Call<OrdersResponse> getOrderHistoryVendors(@Query("vendor_name") String vendorName);
}