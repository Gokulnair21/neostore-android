package com.example.neostore_android.services.network

import com.example.neostore_android.models.ProductListResponse
import com.example.neostore_android.models.ProductRatingResponse
import com.example.neostore_android.models.ProductResponse
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.*

interface ProductAPI {

    @GET("products/getList")
    suspend fun getProducts(
        @Query("product_category_id") productCategoryId: String,
        @Query("limit") limit: Number = 10,
        @Query("page") page: Number = 1
    ): Response<ProductListResponse>


    @GET("products/getDetail")
    suspend fun getProductDetails(@Query("product_id") productID: String): Response<ProductResponse>


    @POST("products/setRating")
    @FormUrlEncoded
    suspend fun setProductRating(
        @Field("product_id") productID: String,
        @Field("rating") rating: Number
    ): Response<ProductRatingResponse>
}
