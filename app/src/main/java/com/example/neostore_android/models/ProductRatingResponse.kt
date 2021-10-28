package com.example.neostore_android.models

import com.google.gson.annotations.SerializedName


data class ProductRatingResponse(
    val status: Long,

    @SerializedName("data")
    val data: ProductRating,

    val message: String,

    @SerializedName("user_msg")
    val userMsg: String
)

data class ProductRating(
    val id: Long,

    @SerializedName("product_category_id")
    val productCategoryID: Long,

    val name: String,
    val producer: String,
    val description: String,
    val cost: Long,
    val rating: Long,

    @SerializedName("view_count")
    val viewCount: Long,

    val created: String,
    val modified: String,

    @SerializedName("product_images")
    val productImages: String
)
