package com.example.neostore_android.services.network


import com.example.neostore_android.models.AccountResponse
import com.example.neostore_android.models.AuthResponse
import com.example.neostore_android.models.CommonPostResponse
import retrofit2.Response
import retrofit2.http.*

interface UserAPI {


    @POST("users/login")
    @FormUrlEncoded
    suspend fun login(
        @Field("email") email: String,
        @Field("password") password: String
    ): Response<AuthResponse>


    @POST("users/register")
    @FormUrlEncoded
    suspend fun register(
        @Field("first_name") firstName: String,
        @Field("last_name") lastName: String,
        @Field("email") email: String,
        @Field("password") password: String,
        @Field("confirm_password") confirmPassword: String,
        @Field("gender") gender: String,
        @Field("phone_no") phoneNumber: Number
    ): Response<AuthResponse>


    @POST("users/forgot")
    @FormUrlEncoded
    suspend fun forgotPassword(@Field("email") email: String): Response<CommonPostResponse>


    @POST("users/change")
    @FormUrlEncoded
    suspend fun changePassword(
        @Header("access_token") accessToken: String,
        @Field("old_password") oldPassword: String,
        @Field("password") password: String,
        @Field("confirm_password") confirmPassword: String
    ): Response<CommonPostResponse>

    @POST("users/update")
    @FormUrlEncoded
    suspend fun updateAccountDetails(
        @Header("access_token") accessToken: String,
        @Field("first_name") firstName: String,
        @Field("last_name") lastName: String,
        @Field("email") email: String,
        @Field("phone_no") phoneNumber: Number,
        @Field("profile_pic") profilePic: String,
        @Field("dob") dob: String
    ): Response<CommonPostResponse>


    @GET("users/getUserData")
    suspend fun getUsersData(@Header("access_token") accessToken: String): Response<AccountResponse>

}