package com.example.neostore_android.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.neostore_android.models.AuthResponse
import com.example.neostore_android.repositories.UsersRepository
import com.example.neostore_android.utils.NetworkData

class RegisterPageViewModel : ViewModel() {

    private val userRepository = UsersRepository()

    fun register(
        firstName: String,
        lastName: String,
        email: String,
        password: String,
        confirmPassword: String,
        gender: String,
        phoneNumber: String
    ): MutableLiveData<NetworkData<AuthResponse>> {


        val phone=phoneNumber.toLong()
        return userRepository.register(
            firstName = firstName,
            lastName = lastName,
            email = email,
            gender = gender,
            confirmPassword = confirmPassword,
            password = password,
            phoneNumber = phone
        )
    }

}