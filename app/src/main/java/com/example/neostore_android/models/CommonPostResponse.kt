package com.example.neostore_android.models

import com.google.gson.annotations.SerializedName


data class CommonPostResponse (
    val status: Long,
    val message: String,

    @SerializedName("user_msg")
    val userMsg: String
)
