package com.example.neostore_android.models

import com.google.gson.annotations.SerializedName


data class AccountResponse(
    val status: Long,

    @SerializedName("data")
    val account: Account
)

data class Account(
    @SerializedName("user_data")
    val user: User,

    @SerializedName("product_categories")
    val productCategories: List<ProductCategory>,

    @SerializedName("total_carts")
    val totalCarts: Long,

    @SerializedName("total_orders")
    val totalOrders: Long
)

data class ProductCategory(
    val id: Long,
    val name: String,

    @SerializedName("icon_image")
    val iconImage: String,

    val created: String,
    val modified: String
)

