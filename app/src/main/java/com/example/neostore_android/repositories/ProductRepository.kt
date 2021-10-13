package com.example.neostore_android.repositories

import androidx.lifecycle.MutableLiveData
import com.example.neostore_android.models.ProductListResponse
import com.example.neostore_android.models.ProductRatingResponse
import com.example.neostore_android.models.ProductResponse
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
        val data = MutableLiveData<NetworkData<ProductListResponse>>()
        data.value = NetworkData.Loading()
        productRetrofitService.getProducts("1").enqueue(
            object : Callback<ProductListResponse> {
                override fun onResponse(
                    call: Call<ProductListResponse>,
                    response: Response<ProductListResponse>
                ) = data.postValue(returnResponse(response))

                override fun onFailure(call: Call<ProductListResponse>, t: Throwable) {
                    data.postValue(NetworkData.Error())
                }

            }
        )
        return data

    }

    fun getProductDetails(productID: String): MutableLiveData<NetworkData<ProductResponse>> {
        val data = MutableLiveData<NetworkData<ProductResponse>>()
        data.value = NetworkData.Loading()
        productRetrofitService.getProductDetails(productID).enqueue(
            object : Callback<ProductResponse> {
                override fun onResponse(
                    call: Call<ProductResponse>,
                    response: Response<ProductResponse>
                ) = data.postValue(returnResponse(response))

                override fun onFailure(call: Call<ProductResponse>, t: Throwable) {
                    data.postValue(NetworkData.Error())
                }

            }
        )
        return data

    }


    fun setProductRating(
        productID: String,
        rating: Number
    ): MutableLiveData<NetworkData<ProductRatingResponse>> {
        val data = MutableLiveData<NetworkData<ProductRatingResponse>>()
        data.value = NetworkData.Loading()
        productRetrofitService.setProductRating(productID, rating).enqueue(
            object : Callback<ProductRatingResponse> {
                override fun onResponse(
                    call: Call<ProductRatingResponse>,
                    response: Response<ProductRatingResponse>
                ) = data.postValue(returnResponse(response))

                override fun onFailure(call: Call<ProductRatingResponse>, t: Throwable) {
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