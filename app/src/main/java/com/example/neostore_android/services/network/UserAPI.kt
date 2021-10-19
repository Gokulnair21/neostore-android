package com.example.neostore_android.services.network


import com.example.neostore_android.models.AuthResponse
import com.example.neostore_android.models.CommonPostResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Query

interface UserAPI {


    @POST("users/login")
    fun login(
        @Query("email") email: String,
        @Query("password") password: String
    ): Call<AuthResponse>

    @POST("users/register")
    fun register(
        @Query("first_name") firstName: String,
        @Query("last_name") lastName: String,
        @Query("email") email: String,
        @Query("password") password: String,
        @Query("confirm_password") confirmPassword: String,
        @Query("gender") gender: String,
        @Query("phone_no") phoneNumber: Number
    ): Call<AuthResponse>


    @POST("users/forgot")
    fun forgotPassword(@Query("email") email: String): Call<CommonPostResponse>


    @POST("users/change")
    fun changePassword(
        @Header("access_token") accessToken: String,
        @Query("old_password") oldPassword: String,
        @Query("password") password: String,
        @Query("confirm_password") confirmPassword: String
    ): Call<CommonPostResponse>

    @POST("users/update")
    fun updateAccountDetails(
        @Header("access_token") accessToken: String,
        @Query("first_name") firstName: String,
        @Query("last_name") lastName: String,
        @Query("email") email: String,
        @Query("phone_no") phoneNumber: Number,
        @Query("profile_pic") profilePic: String
    ): Call<CommonPostResponse>


    @POST("users/getUserData")
    fun getUsersData(@Header("access_token") accessToken: String)

}