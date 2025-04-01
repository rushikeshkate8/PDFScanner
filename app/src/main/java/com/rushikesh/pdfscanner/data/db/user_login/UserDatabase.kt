package com.rushikesh.pdfscanner.data.db.user_login

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.rushikesh.pdfscanner.data.models.User

@Database(entities = [User::class], version = 1, exportSchema = false)
abstract class UserDatabase : RoomDatabase(){
        abstract fun userDao(): UserLoginDetailApi
        companion object {
            @Volatile
            private var INSTANCE: UserDatabase? = null

            fun getInstance(context: Context): UserDatabase {
                // Ensures only one instance of the database is created.
                return INSTANCE ?: synchronized(this) {
                    INSTANCE = Room.databaseBuilder(
                        context.applicationContext,
                        UserDatabase::class.java,
                        "user_database"
                    ).build()
                    INSTANCE!!
                }
            }
        }
    }
