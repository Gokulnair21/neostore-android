package com.example.neostore_android

import android.app.Application
import com.example.neostore_android.repositories.*

class NeoStoreApplication : Application() {

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
        preferenceRepository = PreferenceRepository(applicationContext)
    }

}