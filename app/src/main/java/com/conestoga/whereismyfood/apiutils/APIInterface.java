package com.conestoga.whereismyfood.apiutils;

import com.conestoga.whereismyfood.models.UserDetails;
import com.conestoga.whereismyfood.response.SignUp;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface APIInterface {

    @POST("register.php")
    Call<SignUp> registerUser(@Body UserDetails userDetails);

    @POST("login.php")
    Call<SignUp> loginUser(@Body UserDetails userDetails);
}