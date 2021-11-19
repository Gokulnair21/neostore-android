package com.example.neostore_android.utils

import com.google.gson.annotations.SerializedName

sealed class NetworkData<T>(val data: T? = null, val error: APIError? = null) {
    class Success<T>(data: T) : NetworkData<T>(data)
    class Error<T>(error: APIError? = null) : NetworkData<T>(error = error)
    class Loading<T> : NetworkData<T>()

}


data class APIError(
    val status: Long,
    val message: String,

    @SerializedName("user_msg")
    val userMsg: String
)
