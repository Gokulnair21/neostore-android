package com.example.neostore_android.repositories

import com.example.neostore_android.models.CartListResponse
import com.example.neostore_android.models.CommonPostResponse
import com.example.neostore_android.services.network.CartAPI
import com.example.neostore_android.services.network.RetroFitService
import com.example.neostore_android.utils.NetworkData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn


class CartRepository(private val cartRetrofitService: CartAPI) : BaseRepository() {


    fun addToCart(
        accessToken: String,
        productID: Number,
        quantity: Number
    ): Flow<NetworkData<CommonPostResponse>> = flow {
        emit(NetworkData.Loading())
        emit(getResponse { cartRetrofitService.addToCart(accessToken, productID, quantity) })
    }.flowOn(Dispatchers.IO)


    fun editToCart(
        accessToken: String,
        productID: Number,
        quantity: Number
    ): Flow<NetworkData<CommonPostResponse>> = flow {
        emit(NetworkData.Loading())
        emit(getResponse { cartRetrofitService.editCart(accessToken, productID, quantity) })
    }.flowOn(Dispatchers.IO)


    fun deleteCart(
        accessToken: String,
        productID: Number,
    ): Flow<NetworkData<CommonPostResponse>> = flow {
        emit(NetworkData.Loading())
        emit(getResponse { cartRetrofitService.deleteCart(accessToken, productID) })
    }.flowOn(Dispatchers.IO)


    fun getCartList(
        accessToken: String,
    ): Flow<NetworkData<CartListResponse>> = flow {
        emit(NetworkData.Loading())
        emit(getResponse { cartRetrofitService.getCartList(accessToken) })
    }.flowOn(Dispatchers.IO)

}