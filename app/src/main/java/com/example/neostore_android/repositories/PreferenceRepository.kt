package com.example.neostore_android.repositories

import android.content.SharedPreferences
import java.lang.Exception

class PreferenceRepository(private val sharedPreferences: SharedPreferences) {


    var accessToken: String = sharedPreferences.getString(ACCESS_TOKEN, "") ?: ""


    fun setAccessToken(data: String?): Boolean {
        val editSharedPreferences: SharedPreferences.Editor = sharedPreferences.edit()
        return try {
            editSharedPreferences.putString(ACCESS_TOKEN, data)
            editSharedPreferences.apply()
            accessToken = sharedPreferences.getString(ACCESS_TOKEN, "") ?: ""
            true
        } catch (e: Exception) {
            false
        }
    }

    companion object {
        private const val ACCESS_TOKEN = "access_token"
    }


}

