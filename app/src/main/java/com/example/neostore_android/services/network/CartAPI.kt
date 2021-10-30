package com.example.neostore_android.services.network

import com.example.neostore_android.models.CartListResponse
import com.example.neostore_android.models.CommonPostResponse
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.*

interface CartAPI {


    @POST("addToCart")
    @FormUrlEncoded
    suspend fun addToCart(
        @Header("access_token") accessToken: String,
        @Field("product_id") productID: Number,
        @Field("quantity") quantity: Number
    ): Response<CommonPostResponse>

    @POST("editCart")
    @FormUrlEncoded
    suspend fun editCart(
        @Header("access_token") accessToken: String, @Field("product_id") productID: Number,
        @Field("quantity") quantity: Number
    ): Response<CommonPostResponse>

    @POST("deleteCart")
    @FormUrlEncoded
    suspend fun deleteCart(
        @Header("access_token") accessToken: String,
        @Field("product_id") productID: Number,
    ): Response<CommonPostResponse>

    @GET("cart")
    suspend fun getCartList(@Header("access_token") accessToken: String): Response<CartListResponse>
}