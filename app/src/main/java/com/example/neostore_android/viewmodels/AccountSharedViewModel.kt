package com.example.neostore_android.viewmodels

import androidx.lifecycle.*
import com.example.neostore_android.models.AccountResponse
import com.example.neostore_android.models.CommonPostResponse
import com.example.neostore_android.models.User
import com.example.neostore_android.repositories.UsersRepository
import com.example.neostore_android.utils.NetworkData
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class AccountSharedViewModel(private val accessToken: String) : ViewModel() {
    private val usersRepository = UsersRepository()

    val account = MutableLiveData<NetworkData<AccountResponse>>()

    fun getAccountDetails() = viewModelScope.launch {
        usersRepository.getUsersData(accessToken).collect {
            account.postValue(it)
        }
    }

    fun updateAccountDetails(
        firstName: String,
        lastName: String,
        email: String,
        phoneNumber: String,
        profilePic: String,
        dob: String
    ): LiveData<NetworkData<CommonPostResponse>> = usersRepository.updateAccountDetails(
        accessToken,
        firstName,
        lastName,
        email,
        phoneNumber.toLong(),
        profilePic,
        dob
    ).asLiveData()


    fun changePassword(
        oldPassword: String,
        password: String,
        confirmPassword: String
    ): LiveData<NetworkData<CommonPostResponse>> =
        usersRepository.changePassword(accessToken, oldPassword, password, confirmPassword)
            .asLiveData()


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

