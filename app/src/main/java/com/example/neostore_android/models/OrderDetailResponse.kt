package com.example.neostore_android.models

import com.google.gson.annotations.SerializedName




data class OrderDetailResponse(
    val status: Long,

    @SerializedName("data")
    val order: Order
)

data class Order(
    val id: Long,
    val cost: Double,
    val created: String,

    @SerializedName("order_details")
    val orderDetails: List<OrderDetail>
)

data class OrderDetail(
    val id: Long,

    @SerializedName("order_id")
    val orderID: Long,

    @SerializedName("product_id")
    val productID: Long,

    val quantity: Long,
    val total: Long,

    @SerializedName("prod_name")
    val prodName: String,

    @SerializedName("prod_cat_name")
    val prodCatName: String,

    @SerializedName("prod_image")
    val prodImage: String
)
