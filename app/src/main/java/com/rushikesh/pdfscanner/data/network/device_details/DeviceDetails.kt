package com.rushikesh.pdfscanner.data.network.device_details

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class DeviceDetails {
    private val BASE_URL = "https://api.restful-api.dev"

    // Lazy initialization of Retrofit instance
    val api: DeviceDetailsApi by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL) // Set base API URL
            .addConverterFactory(GsonConverterFactory.create()) // Use Gson for JSON parsing
            .build()
            .create(DeviceDetailsApi::class.java) // Create API interface implementation
    }
}