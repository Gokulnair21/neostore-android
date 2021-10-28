package com.example.neostore_android.services.network


import com.example.neostore_android.models.AccountResponse
import com.example.neostore_android.models.AuthResponse
import com.example.neostore_android.models.CommonPostResponse
import retrofit2.Call
import retrofit2.http.*

interface UserAPI {


    @POST("users/login")
    @FormUrlEncoded
    fun login(
        @Field("email") email: String,
        @Field("password") password: String
    ): Call<AuthResponse>


    @POST("users/register")
    @FormUrlEncoded
    fun register(
        @Field("first_name") firstName: String,
        @Field("last_name") lastName: String,
        @Field("email") email: String,
        @Field("password") password: String,
        @Field("confirm_password") confirmPassword: String,
        @Field("gender") gender: String,
        @Field("phone_no") phoneNumber: Number
    ): Call<AuthResponse>


    @POST("users/forgot")
    @FormUrlEncoded
    fun forgotPassword(@Field("email") email: String): Call<CommonPostResponse>


    @POST("users/change")
    @FormUrlEncoded
    fun changePassword(
        @Header("access_token") accessToken: String,
        @Field("old_password") oldPassword: String,
        @Field("password") password: String,
        @Field("confirm_password") confirmPassword: String
    ): Call<CommonPostResponse>

    @POST("users/update")
    @FormUrlEncoded
    fun updateAccountDetails(
        @Header("access_token") accessToken: String,
        @Field("first_name") firstName: String,
        @Field("last_name") lastName: String,
        @Field("email") email: String,
        @Field("phone_no") phoneNumber: Number,
        @Field("profile_pic") profilePic: String
    ): Call<CommonPostResponse>


    @GET("users/getUserData")
    fun getUsersData(@Header("access_token") accessToken: String): Call<AccountResponse>

}