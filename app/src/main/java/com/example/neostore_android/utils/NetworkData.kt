package com.example.neostore_android.utils

import kotlinx.serialization.SerialName

sealed class NetworkData<T>(val data: T? = null, val error: APIError? = null) {
    class Success<T>(data: T) : NetworkData<T>(data)
    class Error<T>(error: APIError? = null) : NetworkData<T>(error = error)
    class Loading<T> : NetworkData<T>()

}


data class APIError(
    val status: Long,
    var data: Boolean = false,
    val message: String,

    @SerialName("user_msg")
    val userMsg: String
)
