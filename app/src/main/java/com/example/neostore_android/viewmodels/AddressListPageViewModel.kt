package com.example.neostore_android.viewmodels

import androidx.lifecycle.*
import com.example.neostore_android.models.Address
import com.example.neostore_android.models.CommonPostResponse
import com.example.neostore_android.repositories.AddressRepository
import com.example.neostore_android.repositories.OrderRepository
import com.example.neostore_android.utils.NetworkData
import kotlinx.coroutines.launch

class AddressListPageViewModel(
    private val addressRepository: AddressRepository,
    private val orderRepository: OrderRepository,
    private val accessToken: String
) : ViewModel() {


    var addresses = addressRepository.addresses.asLiveData()

    fun insert(address: Address) = viewModelScope.launch {
        addressRepository.insert(address)
        addresses = addressRepository.addresses.asLiveData()
    }

    fun delete(address: Address) = viewModelScope.launch {
        addressRepository.delete(address)
    }


    fun placeOrder(address: String): LiveData<NetworkData<CommonPostResponse>> =
        orderRepository.setOrder(accessToken, address).asLiveData()

    class Factory(
        private val addressRepository: AddressRepository,
        private val orderRepository: OrderRepository,
        private val accessToken: String
    ) : ViewModelProvider.Factory {
        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            return AddressListPageViewModel(addressRepository, orderRepository, accessToken) as T
        }
    }

}