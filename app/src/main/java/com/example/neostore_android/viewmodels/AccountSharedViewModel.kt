package com.example.neostore_android.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.neostore_android.models.AccountResponse
import com.example.neostore_android.models.CommonPostResponse
import com.example.neostore_android.models.User
import com.example.neostore_android.repositories.UsersRepository
import com.example.neostore_android.utils.NetworkData

class AccountSharedViewModel(private val accessToken: String) : ViewModel() {
    private val usersRepository = UsersRepository()

    var account = MutableLiveData<NetworkData<AccountResponse>>()

    private fun getAccountDetails() {
        account = usersRepository.getUsersData(accessToken)
    }

    fun updateAccountDetails(
        accessToken: String,
        firstName: String,
        lastName: String,
        email: String,
        phoneNumber: Number,
        profilePic: String
    ): MutableLiveData<NetworkData<CommonPostResponse>> {
        return usersRepository.updateAccountDetails(
            accessToken,
            firstName,
            lastName,
            email,
            phoneNumber,
            profilePic
        )
    }

    fun changePassword(
        accessToken: String,
        oldPassword: String,
        password: String,
        confirmPassword: String
    ): MutableLiveData<NetworkData<CommonPostResponse>> =
        usersRepository.changePassword(accessToken, oldPassword, password, confirmPassword)


    init {
        getAccountDetails()
    }

    class Factory(private val accessToken: String) : ViewModelProvider.Factory {
        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            return AccountSharedViewModel(accessToken) as T
        }
    }

}

