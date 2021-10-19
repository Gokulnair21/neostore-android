package com.example.neostore_android.models

import com.google.gson.annotations.SerializedName


data class OrderListResponse(
    val status: Long,

    @SerializedName("data")
    val orderList: List<OrderList>?
)

data class OrderList(
    val id: Long,
    val cost: Double,
    val created: String
)
