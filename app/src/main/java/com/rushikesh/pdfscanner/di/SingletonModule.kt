package com.rushikesh.pdfscanner.di

import android.content.Context
import com.google.android.gms.auth.api.identity.Identity
import com.rushikesh.pdfscanner.authentication.GoogleAuthUiClient
import com.rushikesh.pdfscanner.data.db.device.DeviceDatabase
import com.rushikesh.pdfscanner.data.db.device.DeviceDbApi
import com.rushikesh.pdfscanner.data.db.user_login.UserDatabase
import com.rushikesh.pdfscanner.data.db.user_login.UserLoginDetailApi
import com.rushikesh.pdfscanner.data.network.device_details.DeviceDetails
import com.rushikesh.pdfscanner.data.network.device_details.DeviceDetailsApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class SingletonModule {
    @Provides
    @Singleton
    fun getGoogleAuthUiClient(@ApplicationContext context: Context): GoogleAuthUiClient {
        val onTapClient = Identity.getSignInClient(context)
        return GoogleAuthUiClient(context, onTapClient)
    }

    @Provides
    @Singleton
    fun getUserDatabase(@ApplicationContext context: Context): UserDatabase {
        return UserDatabase.getInstance(context)
    }
    @Provides
    @Singleton
    fun getUserDao(userDatabase: UserDatabase): UserLoginDetailApi {
        return userDatabase.userDao()
    }

    @Provides
    @Singleton
    fun getDeviceDetailsInstance(): DeviceDetails {
        return DeviceDetails()
    }

    @Provides
    @Singleton
    fun getDeviceDetailsApi(deviceDetails: DeviceDetails): DeviceDetailsApi {
        return deviceDetails.api
    }

    @Provides
    @Singleton
    fun getDeviceDatabase(@ApplicationContext context: Context): DeviceDatabase {
        return DeviceDatabase.getInstance(context)
    }

    @Provides
    @Singleton
    fun getDeviceDbApi(deviceDatabase: DeviceDatabase): DeviceDbApi {
        return deviceDatabase.deviceDao()
    }

}