package com.rushikesh.pdfscanner.data.db.device

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.rushikesh.pdfscanner.data.db.user_login.UserDatabase
import com.rushikesh.pdfscanner.data.db.user_login.UserLoginDetailApi
import com.rushikesh.pdfscanner.data.models.Device
import com.rushikesh.pdfscanner.data.models.User


@Database(entities = [Device::class], version = 1, exportSchema = false)
abstract class DeviceDatabase : RoomDatabase() {
    abstract fun deviceDao(): DeviceDbApi

    companion object {
        @Volatile
        private var INSTANCE: DeviceDatabase? = null

        fun getInstance(context: Context): DeviceDatabase {
            // Ensures only one instance of the database is created.
            return INSTANCE ?: synchronized(this) {
                INSTANCE = Room.databaseBuilder(
                    context.applicationContext,
                    DeviceDatabase::class.java,
                    "device_database"
                ).build()
                INSTANCE!!
            }
        }
    }
}

