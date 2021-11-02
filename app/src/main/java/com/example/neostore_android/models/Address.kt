package com.example.neostore_android.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "AddressTable")
data class Address(

    @PrimaryKey(autoGenerate = true)
    var id: Int = 0,

    val addressType: String,
    val address: String,
    val landmark: String,
    val city: String,
    val state: String,
    val zipCode: String,
    val country: String
) {

    override fun toString(): String {
        return "${address},${landmark},${city}-${zipCode},${state},${country}"
    }
}