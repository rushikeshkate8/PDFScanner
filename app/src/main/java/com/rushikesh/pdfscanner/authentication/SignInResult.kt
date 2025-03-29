package com.rushikesh.pdfscanner.authentication

data class SignInResult(
    val userData: UserData?,
    val errorMessage: String?
)

data class UserData(
    val userId: String?,
    val username: String?,
    val profileUrl: String?
)