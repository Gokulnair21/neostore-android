package com.example.neostore_android.services.network

import com.example.neostore_android.models.CartListResponse
import com.example.neostore_android.models.CommonPostResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Query

interface CartAPI {


    @POST("addToCart")
    fun addToCart(
        @Header("access_token") accessToken: String,
        @Query("product_id") productID: Number,
        @Query("quantity") quantity: Number
    ): Call<CommonPostResponse>

    @POST("editCart")
    fun editCart(
        @Header("access_token") accessToken: String, @Query("product_id") productID: Number,
        @Query("quantity") quantity: Number
    ): Call<CommonPostResponse>

    @POST("deleteCart")
    fun deleteCart(
        @Header("access_token") accessToken: String,
        @Query("product_id") productID: Number,
    ): Call<CommonPostResponse>

    @GET("cart")
    fun getCartList(@Header("access_token") accessToken: String): Call<CartListResponse>
}