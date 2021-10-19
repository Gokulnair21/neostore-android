package com.example.neostore_android.repositories

import androidx.lifecycle.MutableLiveData
import com.example.neostore_android.models.CartListResponse
import com.example.neostore_android.models.CommonPostResponse
import com.example.neostore_android.services.network.CartAPI
import com.example.neostore_android.services.network.RetroFitService
import com.example.neostore_android.utils.APIError
import com.example.neostore_android.utils.NetworkData
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CartRepository {


    private var cartRetrofitService: CartAPI = RetroFitService.getCartAPI()


    fun addToCart(
        accessToken: String,
        productID: Number,
        quantity: Number
    ): MutableLiveData<NetworkData<CommonPostResponse>> {
        val data = MutableLiveData<NetworkData<CommonPostResponse>>()
        data.value = NetworkData.Loading()
        cartRetrofitService.addToCart(accessToken, productID, quantity).enqueue(
            object : Callback<CommonPostResponse> {
                override fun onResponse(
                    call: Call<CommonPostResponse>,
                    response: Response<CommonPostResponse>
                ) = data.postValue(returnResponse(response))

                override fun onFailure(call: Call<CommonPostResponse>, t: Throwable) {
                    data.postValue(NetworkData.Error())
                }

            }
        )
        return data

    }

    fun editToCart(
        accessToken: String,
        productID: Number,
        quantity: Number
    ): MutableLiveData<NetworkData<CommonPostResponse>> {
        val data = MutableLiveData<NetworkData<CommonPostResponse>>()
        data.value = NetworkData.Loading()
        cartRetrofitService.editCart(accessToken, productID, quantity).enqueue(
            object : Callback<CommonPostResponse> {
                override fun onResponse(
                    call: Call<CommonPostResponse>,
                    response: Response<CommonPostResponse>
                ) = data.postValue(returnResponse(response))

                override fun onFailure(call: Call<CommonPostResponse>, t: Throwable) {
                    data.postValue(NetworkData.Error())
                }

            }
        )
        return data

    }

    fun addToCart(
        accessToken: String,
        productID: Number,
    ): MutableLiveData<NetworkData<CommonPostResponse>> {
        val data = MutableLiveData<NetworkData<CommonPostResponse>>()
        data.value = NetworkData.Loading()
        cartRetrofitService.deleteCart(accessToken, productID).enqueue(
            object : Callback<CommonPostResponse> {
                override fun onResponse(
                    call: Call<CommonPostResponse>,
                    response: Response<CommonPostResponse>
                ) = data.postValue(returnResponse(response))

                override fun onFailure(call: Call<CommonPostResponse>, t: Throwable) {
                    data.postValue(NetworkData.Error())
                }

            }
        )
        return data

    }

    fun getCartList(
        accessToken: String,
    ): MutableLiveData<NetworkData<CartListResponse>> {
        val data = MutableLiveData<NetworkData<CartListResponse>>()
        data.value = NetworkData.Loading()
        cartRetrofitService.getCartList(accessToken).enqueue(
            object : Callback<CartListResponse> {
                override fun onResponse(
                    call: Call<CartListResponse>,
                    response: Response<CartListResponse>
                ) = data.postValue(returnResponse(response))

                override fun onFailure(call: Call<CartListResponse>, t: Throwable) {
                    data.postValue(NetworkData.Error())
                }

            }
        )
        return data

    }

    private fun <T : Any> returnResponse(data: Response<T>): NetworkData<T> {
        return if (data.code() == 200) {
            NetworkData.Success(data.body()!!)
        } else {
            val gson = Gson()
            val type = object : TypeToken<APIError>() {}.type
            var errorResponse: APIError? = gson.fromJson(data.errorBody()!!.charStream(), type)
            NetworkData.Error(errorResponse)
        }
    }
}