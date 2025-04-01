package com.rushikesh.pdfscanner.data.network.device_details

import com.rushikesh.pdfscanner.data.models.Device
import retrofit2.http.GET

interface DeviceDetailsApi {
    @GET("objects")
    suspend fun getAllDevices(): List<Device>
}