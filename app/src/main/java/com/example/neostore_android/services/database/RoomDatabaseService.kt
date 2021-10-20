package com.example.neostore_android.services.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.neostore_android.models.Address


@Database(entities = [Address::class], version = 1, exportSchema = false)
abstract class RoomDatabaseService : RoomDatabase() {

    abstract fun addressDAO(): AddressDAO

    companion object {

        @Volatile
        private var INSTANCE: RoomDatabaseService? = null

        fun getDatabase(context: Context): RoomDatabaseService {

            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    RoomDatabaseService::class.java,
                    "address_database"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}