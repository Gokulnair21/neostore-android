package com.example.neostore_android.viewmodels

import androidx.lifecycle.*
import com.example.neostore_android.models.CommonPostResponse
import com.example.neostore_android.models.ProductRatingResponse
import com.example.neostore_android.models.ProductResponse
import com.example.neostore_android.repositories.CartRepository
import com.example.neostore_android.repositories.ProductRepository
import com.example.neostore_android.utils.NetworkData
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class ProductDetailsPageViewModel(
    private val productID: String, private val cartRepository: CartRepository,
    private val productRepository: ProductRepository,
    private val accessToken: String
) : ViewModel() {


    val product = MutableLiveData<NetworkData<ProductResponse>>()

    init {
        getProductDetails()
    }

    fun getProductDetails() = viewModelScope.launch {
        productRepository.getProductDetails(productID).collect {
            product.postValue(it)
        }

    }

    fun setProductRating(rating: Number): LiveData<NetworkData<ProductRatingResponse>> =
        productRepository.setProductRating(productID, rating).asLiveData()

    fun addToCart(quantity: Number): LiveData<NetworkData<CommonPostResponse>> =
        cartRepository.addToCart(accessToken, productID.toInt(), quantity).asLiveData()

    class Factory(
        private val productID: String,
        private val cartRepository: CartRepository,
        private val productRepository: ProductRepository,
        private val accessToken: String
    ) : ViewModelProvider.Factory {
        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            return ProductDetailsPageViewModel(
                productID,
                cartRepository,
                productRepository,
                accessToken
            ) as T
        }
    }
}
