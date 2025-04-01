package com.rushikesh.pdfscanner.authentication

import com.rushikesh.pdfscanner.data.models.User


data class SignInResult(
    val user: User?,
    val errorMessage: String?
)

//data class UserData(
//    val userId: String?,
//    val username: String?,
//    val profileUrl: String?
//)