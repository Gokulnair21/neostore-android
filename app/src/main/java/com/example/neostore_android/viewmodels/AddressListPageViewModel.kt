package com.example.neostore_android.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.neostore_android.models.Address
import com.example.neostore_android.repositories.AddressRepository
import kotlinx.coroutines.launch

class AddressListPageViewModel(private val addressRepository: AddressRepository) : ViewModel() {


    var addresses = addressRepository.addresses.asLiveData()

    fun insert(address: Address) = viewModelScope.launch {
        addressRepository.insert(address)
        addresses = addressRepository.addresses.asLiveData()
    }

    fun delete(address: Address) = viewModelScope.launch {
        addressRepository.delete(address)
    }

    class Factory(private val addressRepository: AddressRepository) : ViewModelProvider.Factory {
        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            return AddressListPageViewModel(addressRepository) as T
        }
    }

}