package com.example.neostore_android.viewmodels

import androidx.lifecycle.*
import com.example.neostore_android.models.AuthResponse
import com.example.neostore_android.models.CommonPostResponse
import com.example.neostore_android.repositories.CartRepository
import com.example.neostore_android.repositories.UsersRepository
import com.example.neostore_android.utils.NetworkData

class LoginActivityViewModel(private val usersRepository: UsersRepository) : ViewModel() {



    fun forgotPassword(email: String): LiveData<NetworkData<CommonPostResponse>> =
        usersRepository.forgotPassword(email).asLiveData()


    fun login(email: String, password: String): LiveData<NetworkData<AuthResponse>> {
        return usersRepository.login(email, password).asLiveData()
    }


    fun register(
        firstName: String,
        lastName: String,
        email: String,
        password: String,
        confirmPassword: String,
        gender: String,
        phoneNumber: String
    ): LiveData<NetworkData<AuthResponse>> {


        val phone = phoneNumber.toLong()
        return usersRepository.register(
            firstName = firstName,
            lastName = lastName,
            email = email,
            gender = gender,
            confirmPassword = confirmPassword,
            password = password,
            phoneNumber = phone
        ).asLiveData()
    }


    class Factory(
        private val usersRepository: UsersRepository
    ) : ViewModelProvider.Factory {
        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            return LoginActivityViewModel(usersRepository) as T
        }
    }
}