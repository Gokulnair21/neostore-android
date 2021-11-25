package com.example.neostore_android

import android.app.Application
import android.content.Context
import com.example.neostore_android.repositories.*
import com.example.neostore_android.services.database.RoomDatabaseService
import com.example.neostore_android.services.network.RetroFitService

class NeoStoreApplication : Application() {

    companion object {
        private const val USER_DATA = "UserData"
    }

    val productRepository: ProductRepository by lazy {
        ProductRepository(RetroFitService.getProductAPI())
    }
    val userRepository: UsersRepository by lazy {
        UsersRepository(RetroFitService.getUserAPI())
    }
    val cartRepository: CartRepository by lazy {
        CartRepository(RetroFitService.getCartAPI())
    }
    val orderRepository: OrderRepository by lazy {
        OrderRepository(RetroFitService.getOrderAPI())
    }

    val addressRepository: AddressRepository by lazy {
        AddressRepository(RoomDatabaseService.getDatabase(this).addressDAO())
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