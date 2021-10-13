package com.example.neostore_android.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.neostore_android.models.ProductListResponse
import com.example.neostore_android.repositories.ProductRepository
import com.example.neostore_android.utils.NetworkData

class ProductListViewModel : ViewModel() {

    var products = MutableLiveData<NetworkData<ProductListResponse>>()

    private val productRepository = ProductRepository()

    fun getProducts() {
        products = productRepository.getProducts()
    }
}