package com.example.neostore_android.repositories

import android.content.Context
import android.content.SharedPreferences
import androidx.fragment.app.FragmentActivity

class PreferenceRepository(context: Context) {
    companion object {
        private const val ACCESS_TOKEN = "access_token"
        private const val USER_DATA = "UserData"
    }

    private val sharedPreferences: SharedPreferences =
        context.getSharedPreferences(USER_DATA, Context.MODE_PRIVATE)
    private val editSharedPreferences = sharedPreferences.edit()


    var accessToken:String = sharedPreferences.getString(ACCESS_TOKEN,"")?:""

    fun setAccessToken(data: String): Boolean {
        editSharedPreferences.putString(ACCESS_TOKEN, data)
        return editSharedPreferences.commit()
    }


}