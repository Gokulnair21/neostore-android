package com.example.neostore_android.viewmodels

import androidx.lifecycle.*
import com.example.neostore_android.models.CartListResponse
import com.example.neostore_android.models.CommonPostResponse
import com.example.neostore_android.repositories.CartRepository
import com.example.neostore_android.utils.NetworkData
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class MyCartPageViewModel(
    private val accessToken: String,
    private val cartRepository: CartRepository
) : ViewModel() {


    val cart = MutableLiveData<NetworkData<CartListResponse>>()


    fun getCartItems() = viewModelScope.launch {
        cartRepository.getCartList(accessToken).collect {
            cart.postValue(it)
        }
    }


    fun editCartItem(
        productID: Number,
        quantity: Number
    ): LiveData<NetworkData<CommonPostResponse>> =
        cartRepository.editToCart(accessToken, productID, quantity).asLiveData()


    fun deleteCartItem(
        productID: Number,
    ): LiveData<NetworkData<CommonPostResponse>> =
        cartRepository.deleteCart(accessToken, productID).asLiveData()


    init {
        getCartItems()
    }

    class Factory(
        private val accessToken: String,
        private val cartRepository: CartRepository
    ) : ViewModelProvider.Factory {
        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            return MyCartPageViewModel(accessToken, cartRepository) as T
        }
    }

}