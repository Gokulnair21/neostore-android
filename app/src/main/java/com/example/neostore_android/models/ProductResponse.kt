package com.example.neostore_android.models

import com.google.gson.annotations.SerializedName


data class ProductResponse (
    val status: Long,

    @SerializedName("data")
    val data: Product
)

data class Product (
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
    val productImages: List<ProductImage>
)

data class ProductImage (
    val id: Long,

    @SerializedName("product_id")
    val productID: Long,

    val image: String,
    val created: String,
    val modified: String
)
