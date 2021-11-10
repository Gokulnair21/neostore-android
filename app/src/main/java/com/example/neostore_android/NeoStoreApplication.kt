package com.example.neostore_android

import android.app.Application
import android.content.Context
import com.example.neostore_android.repositories.*

class NeoStoreApplication : Application() {

    companion object {
        private const val USER_DATA = "UserData"
    }

    val productRepository: ProductRepository by lazy {
        ProductRepository()
    }
    val userRepository: UsersRepository by lazy {
        UsersRepository()
    }
    val cartRepository: CartRepository by lazy {
        CartRepository()
    }
    val orderRepository: OrderRepository by lazy {
        OrderRepository()
    }

    val addressRepository: AddressRepository by lazy {
        AddressRepository(this)
    }

    lateinit var preferenceRepository: PreferenceRepository


    override fun onCreate() {
        super.onCreate()
        preferenceRepository = PreferenceRepository(
            applicationContext.getSharedPreferences(
                USER_DATA,
                Context.MODE_PRIVATE
            )
        )
    }


}