package com.example.neostore_android.repositories

import androidx.lifecycle.MutableLiveData
import com.example.neostore_android.models.AccountResponse
import com.example.neostore_android.models.AuthResponse
import com.example.neostore_android.models.CommonPostResponse
import com.example.neostore_android.services.network.RetroFitService
import com.example.neostore_android.services.network.UserAPI
import com.example.neostore_android.utils.APIError
import com.example.neostore_android.utils.NetworkData
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.Header
import retrofit2.http.POST

class UsersRepository {

    private val userRetrofitService: UserAPI = RetroFitService.getUserAPI()


    fun login(email: String, password: String): MutableLiveData<NetworkData<AuthResponse>> {
        val data = MutableLiveData<NetworkData<AuthResponse>>()
        data.value = NetworkData.Loading()
        userRetrofitService.login(email, password).enqueue(
            object : Callback<AuthResponse> {
                override fun onResponse(
                    call: Call<AuthResponse>,
                    response: Response<AuthResponse>
                ) = data.postValue(returnResponse(response))

                override fun onFailure(call: Call<AuthResponse>, t: Throwable) {
                    data.postValue(NetworkData.Error())
                }

            }
        )
        return data
    }

    fun register(
        firstName: String,
        lastName: String,
        email: String,
        password: String,
        confirmPassword: String,
        gender: String,
        phoneNumber: Number
    ): MutableLiveData<NetworkData<AuthResponse>> {
        val data = MutableLiveData<NetworkData<AuthResponse>>()
        data.value = NetworkData.Loading()
        userRetrofitService.register(
            firstName = firstName,
            lastName = lastName,
            email = email,
            password = password,
            confirmPassword = confirmPassword,
            gender = gender,
            phoneNumber = phoneNumber
        ).enqueue(
            object : Callback<AuthResponse> {
                override fun onResponse(
                    call: Call<AuthResponse>,
                    response: Response<AuthResponse>
                ) = data.postValue(returnResponse(response))

                override fun onFailure(call: Call<AuthResponse>, t: Throwable) {
                    data.postValue(NetworkData.Error())
                }

            }
        )
        return data
    }

    fun forgotPassword(email: String): MutableLiveData<NetworkData<CommonPostResponse>> {
        val data = MutableLiveData<NetworkData<CommonPostResponse>>()
        data.value = NetworkData.Loading()
        userRetrofitService.forgotPassword(email).enqueue(
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

    fun changePassword(
        accessToken: String,
        oldPassword: String,
        password: String,
        confirmPassword: String
    ): MutableLiveData<NetworkData<CommonPostResponse>> {
        val data = MutableLiveData<NetworkData<CommonPostResponse>>()
        data.value = NetworkData.Loading()
        userRetrofitService.changePassword(
            accessToken = accessToken,
            oldPassword = oldPassword,
            password = password,
            confirmPassword = confirmPassword
        ).enqueue(
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


    fun updateAccountDetails(
        accessToken: String,
        firstName: String,
        lastName: String,
        email: String,
        phoneNumber: Number,
        profilePic: String
    ): MutableLiveData<NetworkData<CommonPostResponse>> {
        val data = MutableLiveData<NetworkData<CommonPostResponse>>()
        data.value = NetworkData.Loading()
        userRetrofitService.updateAccountDetails(
            firstName = firstName,
            lastName = lastName,
            email = email,
            accessToken = accessToken,
            phoneNumber = phoneNumber,
            profilePic = profilePic
        ).enqueue(
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


    fun getUsersData(accessToken: String): MutableLiveData<NetworkData<AccountResponse>> {
        val data = MutableLiveData<NetworkData<AccountResponse>>()
        data.value = NetworkData.Loading()
        userRetrofitService.getUsersData(accessToken).enqueue(
            object : Callback<AccountResponse> {
                override fun onResponse(
                    call: Call<AccountResponse>,
                    response: Response<AccountResponse>
                ) = data.postValue(returnResponse(response))

                override fun onFailure(call: Call<AccountResponse>, t: Throwable) {
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
            val errorResponse: APIError? = gson.fromJson(data.errorBody()!!.charStream(), type)
            NetworkData.Error(errorResponse)
        }
    }

}