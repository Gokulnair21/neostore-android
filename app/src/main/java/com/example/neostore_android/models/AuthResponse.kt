package com.example.neostore_android.models

import com.google.gson.annotations.SerializedName


data class AuthResponse (
    val status: Long,

    @SerializedName("data")
    val user: List<User>,

    val message: String,

    @SerializedName("user_msg")
    val userMsg: String
)


data class User (
    val id: Long,

    @SerializedName("role_id")
    val roleID: Long,

    @SerializedName("first_name")
    val firstName: String,

    @SerializedName("last_name")
    val lastName: String,

    val email: String,
    val username: String,

    @SerializedName("profile_pic")
    val profilePic: String? = null,

    @SerializedName("country_id")
    val countryID: String? = null,

    val gender: String,

    @SerializedName("phone_no")
    val phoneNo: Long,

    val dob: String? = null,

    @SerializedName("is_active")
    val isActive: Boolean,

    val created: String,
    val modified: String,

    @SerializedName("access_token")
    val accessToken: String
)
