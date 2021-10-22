package com.example.neostore_android.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.neostore_android.models.ProductRatingResponse
import com.example.neostore_android.models.ProductResponse
import com.example.neostore_android.repositories.ProductRepository
import com.example.neostore_android.utils.NetworkData

class ProductDetailsPageViewModel(private val productID: String) : ViewModel() {


    private val productRepository = ProductRepository()

    var product = MutableLiveData<NetworkData<ProductResponse>>()
    var productRating = MutableLiveData<NetworkData<ProductRatingResponse>>()

    init {
        getProductDetails()
    }

    fun getProductDetails() {
        product = productRepository.getProductDetails(productID)
    }

    fun setProductRating(rating: Number) {
        productRating = productRepository.setProductRating(productID, rating)
    }

    class Factory(private val productID: String) : ViewModelProvider.Factory {
        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            return ProductDetailsPageViewModel(productID) as T
        }
    }
}
