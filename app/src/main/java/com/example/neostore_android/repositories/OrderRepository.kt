package com.example.neostore_android.repositories

import androidx.lifecycle.MutableLiveData
import com.example.neostore_android.models.CommonPostResponse
import com.example.neostore_android.models.OrderDetailResponse
import com.example.neostore_android.models.OrderListResponse
import com.example.neostore_android.services.network.OrderAPI
import com.example.neostore_android.services.network.RetroFitService
import com.example.neostore_android.utils.APIError
import com.example.neostore_android.utils.NetworkData
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class OrderRepository {

    private var orderRetrofitService: OrderAPI = RetroFitService.getOrderAPI()

    fun setOrder(
        accessToken: String,
        address: String
    ): MutableLiveData<NetworkData<CommonPostResponse>> {
        val data = MutableLiveData<NetworkData<CommonPostResponse>>()
        data.value = NetworkData.Loading()
        orderRetrofitService.order(accessToken, address).enqueue(
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

    fun getOrderList(
        accessToken: String,
    ): MutableLiveData<NetworkData<OrderListResponse>> {
        val data = MutableLiveData<NetworkData<OrderListResponse>>()
        data.value = NetworkData.Loading()
        orderRetrofitService.getOrderList(accessToken).enqueue(
            object : Callback<OrderListResponse> {
                override fun onResponse(
                    call: Call<OrderListResponse>,
                    response: Response<OrderListResponse>
                ) = data.postValue(returnResponse(response))

                override fun onFailure(call: Call<OrderListResponse>, t: Throwable) {
                    data.postValue(NetworkData.Error())
                }

            }
        )
        return data

    }

    fun getOrderDetail(
        accessToken: String,
        orderID: Number
    ): MutableLiveData<NetworkData<OrderDetailResponse>> {
        val data = MutableLiveData<NetworkData<OrderDetailResponse>>()
        data.value = NetworkData.Loading()
        orderRetrofitService.getOrderDetail(accessToken, orderID).enqueue(
            object : Callback<OrderDetailResponse> {
                override fun onResponse(
                    call: Call<OrderDetailResponse>,
                    response: Response<OrderDetailResponse>
                ) = data.postValue(returnResponse(response))

                override fun onFailure(call: Call<OrderDetailResponse>, t: Throwable) {
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