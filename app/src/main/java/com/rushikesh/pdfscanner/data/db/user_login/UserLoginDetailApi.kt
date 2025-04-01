package com.rushikesh.pdfscanner.data.db.user_login

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import com.rushikesh.pdfscanner.data.models.User

@Dao
interface UserLoginDetailApi {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addUser(user: User)

    @Delete
    suspend fun deleteUser(user: User)
}