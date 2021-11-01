package com.example.neostore_android.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.neostore_android.models.OrderDetailResponse
import com.example.neostore_android.repositories.OrderRepository
import com.example.neostore_android.utils.NetworkData
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class OrderDetailsPageViewModel(
    private val orderRepository: OrderRepository,
    private val accessToken: String,
    private val orderID: Number
) : ViewModel() {


    val orderDetail = MutableLiveData<NetworkData<OrderDetailResponse>>()

    private fun getOrderDetails() = viewModelScope.launch {
        orderRepository.getOrderDetail(accessToken, orderID).collect {
            orderDetail.postValue(it)
        }
    }


    init {
        getOrderDetails()
    }

    class Factory(
        private val orderRepository: OrderRepository,
        private val accessToken: String,
        private val orderID: Number
    ) : ViewModelProvider.Factory {
        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            return OrderDetailsPageViewModel(orderRepository, accessToken, orderID) as T
        }
    }
}