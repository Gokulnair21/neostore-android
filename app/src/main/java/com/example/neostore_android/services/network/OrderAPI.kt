package com.example.neostore_android.services.network

import com.example.neostore_android.models.CommonPostResponse
import com.example.neostore_android.models.OrderDetailResponse
import com.example.neostore_android.models.OrderListResponse
import retrofit2.Response
import retrofit2.http.*

interface OrderAPI {

    @POST("order")
    @FormUrlEncoded
    suspend fun order(
        @Header("access_token") accessToken: String,
        @Field("address") address: String
    ): Response<CommonPostResponse>


    @GET("orderList")
    suspend fun getOrderList(@Header("access_token") accessToken: String): Response<OrderListResponse>

    @GET("orderDetail")
    suspend fun getOrderDetail(
        @Header("access_token") accessToken: String,
        @Query("order_id") orderID: Number
    ): Response<OrderDetailResponse>


}