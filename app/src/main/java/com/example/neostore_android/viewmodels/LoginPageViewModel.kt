package com.example.neostore_android.viewmodels

import androidx.lifecycle.*
import com.example.neostore_android.models.AuthResponse
import com.example.neostore_android.models.CommonPostResponse
import com.example.neostore_android.repositories.UsersRepository
import com.example.neostore_android.utils.NetworkData

class LoginPageViewModel : ViewModel() {


    private val userRepository = UsersRepository()

    fun forgotPassword(email: String): LiveData<NetworkData<CommonPostResponse>> =
        userRepository.forgotPassword(email).asLiveData()


    fun login(email: String, password: String): LiveData<NetworkData<AuthResponse>> {
        return userRepository.login(email, password).asLiveData()
    }
}