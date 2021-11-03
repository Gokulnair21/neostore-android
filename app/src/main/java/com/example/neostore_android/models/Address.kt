package com.example.neostore_android.models

import androidx.room.Entity
import androidx.room.Ignore
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
    val country: String,

    @Ignore
    var isSelected: Boolean = false
) {

    constructor(
        id: Int,
        addressType: String,
        address: String,
        landmark: String,
        city: String,
        state: String,
        zipCode: String,
        country: String,
    ) : this(id, addressType, address, landmark, city, state, zipCode, country, false)

    override fun toString(): String {
        return "${address},${landmark},${city}-${zipCode},${state},${country}"
    }
}