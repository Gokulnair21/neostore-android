package com.example.neostore_android.repositories

import androidx.lifecycle.MutableLiveData
import com.example.neostore_android.models.ProductListResponse
import com.example.neostore_android.services.network.ProductAPI
import com.example.neostore_android.services.network.RetroFitService
import com.example.neostore_android.utils.APIError
import com.example.neostore_android.utils.NetworkData
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ProductRepository {
    private var productRetrofitService: ProductAPI = RetroFitService.getProductAPI()

    fun getProducts(): MutableLiveData<NetworkData<ProductListResponse>> {
        val productsData = MutableLiveData<NetworkData<ProductListResponse>>()
        productsData.value = NetworkData.Loading()
        productRetrofitService.getProducts("1").enqueue(
            object : Callback<ProductListResponse> {
                override fun onResponse(
                    call: Call<ProductListResponse>,
                    response: Response<ProductListResponse>
                ) = productsData.postValue(returnResponse(response))

                override fun onFailure(call: Call<ProductListResponse>, t: Throwable) {
                    productsData.postValue(NetworkData.Error())
                }

            }
        )
        return productsData

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