package com.example.neostore_android.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.neostore_android.models.AuthResponse
import com.example.neostore_android.repositories.UsersRepository
import com.example.neostore_android.utils.NetworkData

class LoginPageViewModel: ViewModel() {


    private val userRepository=UsersRepository()






    fun login(email:String,password:String):MutableLiveData<NetworkData<AuthResponse>>{
        return userRepository.login(email,password)
    }
}