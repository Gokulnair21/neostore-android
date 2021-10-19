package com.example.neostore_android.services.network

import com.example.neostore_android.models.CommonPostResponse
import com.example.neostore_android.models.OrderDetailResponse
import com.example.neostore_android.models.OrderListResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Query

interface OrderAPI {

    @POST("order")
    fun order(
        @Header("access_token") accessToken: String,
        @Query("address") address: String
    ): Call<CommonPostResponse>


    @GET("orderList")
    fun getOrderList(@Header("access_token") accessToken: String): Call<OrderListResponse>

    @GET("orderDetail")
    fun getOrderDetail(
        @Header("access_token") accessToken: String,
        @Query("order_id") orderID: Number
    ): Call<OrderDetailResponse>


}