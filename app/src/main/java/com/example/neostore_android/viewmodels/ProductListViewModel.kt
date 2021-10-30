package com.example.neostore_android.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.neostore_android.models.ProductListResponse
import com.example.neostore_android.repositories.ProductRepository
import com.example.neostore_android.utils.NetworkData
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class ProductListViewModel(private val productType: String) : ViewModel() {

    val products = MutableLiveData<NetworkData<ProductListResponse>>()

    private val productRepository = ProductRepository()

    init {
        getProducts()
    }

    fun getProducts() = viewModelScope.launch {
        productRepository.getProducts(productType).collect {
            products.postValue(it)
        }
    }

    class Factory(private val productType: String) : ViewModelProvider.Factory {
        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            return ProductListViewModel(productType) as T
        }
    }
}