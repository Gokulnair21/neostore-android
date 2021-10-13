package com.example.neostore_android.models

import kotlinx.serialization.SerialName


data class ProductResponse (
    val status: Long,

    @SerialName("data")
    val data: List<Product>
)

data class Product (
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
    val productImages: List<ProductImage>
)

data class ProductImage (
    val id: Long,

    @SerialName("product_id")
    val productID: Long,

    val image: String,
    val created: String,
    val modified: String
)
