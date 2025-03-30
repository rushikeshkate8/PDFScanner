package com.rushikesh.pdfscanner.ui.viewmodels

import android.content.Intent
import android.content.IntentSender
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rushikesh.pdfscanner.authentication.GoogleAuthUiClient
import com.rushikesh.pdfscanner.authentication.SignInResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor() :
    ViewModel() {
    private val TAG = LoginViewModel::class.simpleName
    @Inject
    lateinit var googleAuthUiClient: GoogleAuthUiClient
    fun signIn(launcher: (IntentSender) -> Unit) {
        var intentSender: IntentSender? = null
        viewModelScope.launch {
            intentSender = withContext(Dispatchers.IO) {
                googleAuthUiClient.signIn()
            }
            launcher(intentSender!!)
        }
    }

    fun signInWithIntent(intent: Intent): SignInResult? {
        var signInResult: SignInResult? = null
        viewModelScope.launch {
            signInResult = withContext(Dispatchers.IO) {
                googleAuthUiClient.signInWithIntent(intent)
            }
            Log.i(TAG, signInResult.toString())
        }
        return signInResult
    }
}