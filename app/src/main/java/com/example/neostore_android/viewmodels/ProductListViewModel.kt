package com.example.neostore_android.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.neostore_android.models.ProductListResponse
import com.example.neostore_android.repositories.ProductRepository
import com.example.neostore_android.utils.NetworkData

class ProductListViewModel(private val productType: String) : ViewModel() {

    var products = MutableLiveData<NetworkData<ProductListResponse>>()

    private val productRepository = ProductRepository()

    init {
        getProducts()
    }
    fun getProducts() {
        products=productRepository.getProducts(productType)
    }

    class Factory(private val productType: String) : ViewModelProvider.Factory {
        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            return ProductListViewModel(productType) as T
        }
    }
}