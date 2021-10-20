package com.example.neostore_android.services.database

import androidx.room.*
import com.example.neostore_android.models.Address
import kotlinx.coroutines.flow.Flow

@Dao
interface AddressDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAddress(address: Address)

    @Delete
    suspend fun deleteAddress(address: Address)

    @Query("SELECT * FROM AddressTable ORDER BY id DESC")
    fun getAllAddresses(): Flow<List<Address>>


}