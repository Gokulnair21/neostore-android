package com.example.neostore_android.models

import com.google.gson.annotations.SerializedName


data class CartListResponse(
    val status: Long,

    @SerializedName("data")
    val cartProduct: List<CartProduct>?
)

data class CartProduct(
    val id: Long,

    @SerializedName("product_id")
    val productID: Long,

    var quantity: Long,

    @SerializedName("product")
    val cartProductItem: CartProductItem
)

data class CartProductItem(
    val id: Long,
    val name: String,
    val cost: Long,

    @SerializedName("product_category")
    val productCategory: String,

    @SerializedName("product_images")
    val productImages: String,

    @SerializedName("sub_total")
    val subTotal: Long
)
