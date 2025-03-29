package com.rushikesh.pdfscanner.ui

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.android.gms.auth.api.identity.Identity
import com.rushikesh.pdfscanner.authentication.GoogleAuthUiClient
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(@ApplicationContext val applicatioContext: Context) : ViewModel() {
    fun signIn() {
        viewModelScope.launch {
            GoogleAuthUiClient(applicatioContext, Identity.getSignInClient(applicatioContext))
        }
    }
}