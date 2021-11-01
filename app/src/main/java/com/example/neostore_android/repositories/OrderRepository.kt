package com.example.neostore_android.repositories

import com.example.neostore_android.models.CommonPostResponse
import com.example.neostore_android.models.OrderDetailResponse
import com.example.neostore_android.models.OrderListResponse
import com.example.neostore_android.services.network.OrderAPI
import com.example.neostore_android.services.network.RetroFitService
import com.example.neostore_android.utils.NetworkData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn


class OrderRepository : BaseRepository() {

    private var orderRetrofitService: OrderAPI = RetroFitService.getOrderAPI()

    fun setOrder(
        accessToken: String,
        address: String
    ): Flow<NetworkData<CommonPostResponse>> = flow {
        emit(NetworkData.Loading())
        emit(getResponse { orderRetrofitService.order(accessToken, address) })

    }.flowOn(Dispatchers.IO)

    fun getOrderList(
        accessToken: String,
    ): Flow<NetworkData<OrderListResponse>> = flow {
        emit(NetworkData.Loading())
        emit(getResponse { orderRetrofitService.getOrderList(accessToken) })

    }.flowOn(Dispatchers.IO)

    fun getOrderDetail(
        accessToken: String,
        orderID: Number
    ): Flow<NetworkData<OrderDetailResponse>> = flow {
        emit(NetworkData.Loading())
        emit(getResponse { orderRetrofitService.getOrderDetail(accessToken, orderID) })

    }.flowOn(Dispatchers.IO)


}