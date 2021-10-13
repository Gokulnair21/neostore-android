package com.example.neostore_android.services.network

import com.example.neostore_android.models.ProductListResponse
import com.example.neostore_android.models.ProductRatingResponse
import com.example.neostore_android.models.ProductResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface ProductAPI {

    @GET("products/getList")
    fun getProducts(
        @Query("product_category_id") productCategoryId: String,
        @Query("limit") limit: Number = 10,
        @Query("page") page: Number = 1
    ): Call<ProductListResponse>


    @GET("products/getDetail")
    fun getProductDetails(@Query("product_id") productID: String): Call<ProductResponse>


    @POST("products/setRating")
    fun setProductRating(
        @Query("product_id") productID: String,
        @Query("rating") rating: Number
    ): Call<ProductRatingResponse>
}
