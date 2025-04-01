package com.rushikesh.pdfscanner.authentication

import android.content.Context
import android.content.Intent
import android.content.IntentSender
import com.google.android.gms.auth.api.identity.BeginSignInRequest
import com.google.android.gms.auth.api.identity.BeginSignInRequest.GoogleIdTokenRequestOptions
import com.google.android.gms.auth.api.identity.SignInClient
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.auth
import com.rushikesh.pdfscanner.R
import com.rushikesh.pdfscanner.data.models.User
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class GoogleAuthUiClient @Inject constructor(
    @ApplicationContext val context: Context,
    private val onTapClient: SignInClient
) {
    private val auth = com.google.firebase.Firebase.auth
    suspend fun signIn(): IntentSender? {
        val result = try {
            onTapClient.beginSignIn(beginSignInRequest()).await()
        } catch (e: Exception) {
            e.printStackTrace()
            if(e is CancellationException) throw e
            null
        }
        return result?.pendingIntent?.intentSender
    }
suspend fun signInWithIntent(intent: Intent): SignInResult {
    val credentials = onTapClient.getSignInCredentialFromIntent(intent)
    val googleIdToken = credentials.googleIdToken
    val googleCredentials = GoogleAuthProvider.getCredential(googleIdToken, null)
    return try {
        val user = auth.signInWithCredential(googleCredentials).await().user
        SignInResult(user = user?.run {
            User(uid, displayName!!, photoUrl.toString())
        }, errorMessage = null)
    } catch (e: Exception) {
        e.printStackTrace()
        if(e is CancellationException) throw e
        SignInResult(
            null, errorMessage = e.message
        )
    }
}
    private fun beginSignInRequest(): BeginSignInRequest {
        return BeginSignInRequest.builder().setGoogleIdTokenRequestOptions(
            GoogleIdTokenRequestOptions.builder()
                .setSupported(true)
                .setFilterByAuthorizedAccounts(false)
                .setServerClientId(context.getString(R.string.web_client_id))
                .build()
        ).setAutoSelectEnabled(true).build()
    }
    suspend fun signOut() {
        try {
            onTapClient.signOut().await()
            auth.signOut()
        } catch (e: Exception) {
            e.printStackTrace()
            if(e is CancellationException) throw e
        }
    }
    fun getSignedInUser(): User? = auth.currentUser?.run {
        User(uid, displayName!!, photoUrl.toString())
    }
}