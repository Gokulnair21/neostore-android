package com.example.neostore_android.services.network


import com.example.neostore_android.models.AuthResponse
import retrofit2.Call
import retrofit2.http.GET
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



}