package com.rushikesh.pdfscanner.di

import android.content.Context
import com.google.android.gms.auth.api.identity.Identity
import com.rushikesh.pdfscanner.authentication.GoogleAuthUiClient
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AuthenticationModule {
    @Provides
    @Singleton
    fun getGoogleAuthUiClient(@ApplicationContext context: Context): GoogleAuthUiClient {
        val onTapClient = Identity.getSignInClient(context)
        return GoogleAuthUiClient(context, onTapClient)
    }
}