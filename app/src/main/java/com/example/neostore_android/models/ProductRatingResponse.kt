package com.example.neostore_android.models

import kotlinx.serialization.SerialName



data class ProductRatingResponse(
    val status: Long,

    @SerialName("data")
    val data: ProductRating,

    val message: String,

    @SerialName("user_msg")
    val userMsg: String
)

data class ProductRating(
    val id: Long,

    @SerialName("product_category_id")
    val productCategoryID: Long,

    val name: String,
    val producer: String,
    val description: String,
    val cost: Long,
    val rating: Long,

    @SerialName("view_count")
    val viewCount: Long,

    val created: String,
    val modified: String,

    @SerialName("product_images")
    val productImages: String
)
