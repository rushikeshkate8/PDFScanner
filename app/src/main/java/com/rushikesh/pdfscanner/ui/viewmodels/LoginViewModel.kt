package com.rushikesh.pdfscanner.ui.viewmodels

import android.content.Intent
import android.content.IntentSender
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rushikesh.pdfscanner.authentication.GoogleAuthUiClient
import com.rushikesh.pdfscanner.authentication.SignInResult
import com.rushikesh.pdfscanner.data.models.User
import com.rushikesh.pdfscanner.data.db.user_login.UserLoginDetailApi
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(val googleAuthUiClient: GoogleAuthUiClient, val userLoginDetailApi: UserLoginDetailApi) :
    ViewModel() {
    private val TAG = LoginViewModel::class.simpleName

    fun signIn(launcher: (IntentSender) -> Unit) {
        var intentSender: IntentSender? = null
        viewModelScope.launch {
            intentSender = withContext(Dispatchers.IO) {
                googleAuthUiClient.signIn()
            }
            launcher(intentSender!!)
        }
    }

    fun signInWithIntent(intent: Intent) {
        viewModelScope.launch {
            var signInResult: SignInResult? = null
            signInResult = withContext(Dispatchers.IO) {
                googleAuthUiClient.signInWithIntent(intent)
            }
            Log.i(TAG, signInResult.toString())
            if(signInResult?.user != null) addUser(signInResult.user)
        }
    }

    fun addUser(user: User) = viewModelScope.launch(Dispatchers.IO) {
        userLoginDetailApi.addUser(user)
    }
}