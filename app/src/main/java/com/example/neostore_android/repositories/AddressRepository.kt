package com.example.neostore_android.repositories

import android.content.Context
import com.example.neostore_android.models.Address
import com.example.neostore_android.services.database.AddressDAO
import com.example.neostore_android.services.database.RoomDatabaseService

class AddressRepository( private val addressDAO: AddressDAO) {




    val addresses = addressDAO.getAllAddresses()


    suspend  fun insert(address: Address)=addressDAO.insertAddress(address)

    suspend  fun delete(address: Address)=addressDAO.deleteAddress(address)


}