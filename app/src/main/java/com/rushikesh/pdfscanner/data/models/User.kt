package com.rushikesh.pdfscanner.data.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "UserLoginData")
data class User(
    @PrimaryKey
    val uid: String,
    val displayName: String,
    val photoUrl: String
)
