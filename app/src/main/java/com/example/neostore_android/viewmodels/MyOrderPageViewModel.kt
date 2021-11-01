package com.example.neostore_android.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.neostore_android.models.OrderListResponse
import com.example.neostore_android.repositories.OrderRepository
import com.example.neostore_android.utils.NetworkData
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class MyOrderPageViewModel(
    private val orderRepository: OrderRepository,
    private val accessToken: String
) : ViewModel() {


    val orderList = MutableLiveData<NetworkData<OrderListResponse>>()

    private fun getOrderList() = viewModelScope.launch {
        orderRepository.getOrderList(accessToken).collect {
            orderList.postValue(it)
        }
    }

    init {
        getOrderList()
    }


    class Factory(
        private val orderRepository: OrderRepository,
        private val accessToken: String
    ) : ViewModelProvider.Factory {
        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            return MyOrderPageViewModel(orderRepository, accessToken) as T
        }
    }
}

