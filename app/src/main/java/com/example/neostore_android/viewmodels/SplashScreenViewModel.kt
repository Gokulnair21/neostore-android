package com.example.neostore_android.viewmodels

import androidx.lifecycle.*
import com.example.neostore_android.repositories.PreferenceRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class SplashScreenViewModel(private val preferenceRepository: PreferenceRepository) : ViewModel() {


    var data = MutableLiveData(0)

    init {
        getAccessToken()
    }


    private fun getAccessToken() = viewModelScope.launch {
        val accessToken = preferenceRepository.accessToken
        test()
        data.postValue((1..6).random())
    }

    suspend fun test()=withContext(Dispatchers.Default){
        delay(3000)
    }



    class Factory(private val preferenceRepository: PreferenceRepository) :
        ViewModelProvider.Factory {
        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            return SplashScreenViewModel(preferenceRepository) as T
        }
    }
}