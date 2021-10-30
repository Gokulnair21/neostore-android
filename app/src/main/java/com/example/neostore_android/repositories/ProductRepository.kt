package com.example.neostore_android.repositories

import com.example.neostore_android.models.ProductListResponse
import com.example.neostore_android.models.ProductRatingResponse
import com.example.neostore_android.models.ProductResponse
import com.example.neostore_android.services.network.ProductAPI
import com.example.neostore_android.services.network.RetroFitService
import com.example.neostore_android.utils.NetworkData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn


class ProductRepository : BaseRepository() {
    private var productRetrofitService: ProductAPI = RetroFitService.getProductAPI()

    fun getProducts(productType: String): Flow<NetworkData<ProductListResponse>> = flow {
        emit(NetworkData.Loading())
        emit(getResponse { productRetrofitService.getProducts(productType) })
    }.flowOn(Dispatchers.IO)


    fun getProductDetails(productID: String): Flow<NetworkData<ProductResponse>> = flow {
        emit(NetworkData.Loading())
        emit(getResponse { productRetrofitService.getProductDetails(productID) })
    }.flowOn(Dispatchers.IO)


    fun setProductRating(
        productID: String,
        rating: Number
    ): Flow<NetworkData<ProductRatingResponse>> = flow {
        emit(NetworkData.Loading())
        emit(getResponse { productRetrofitService.setProductRating(productID, rating) })
    }.flowOn(Dispatchers.IO)


}