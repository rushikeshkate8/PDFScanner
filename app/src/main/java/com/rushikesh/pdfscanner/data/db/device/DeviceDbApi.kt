package com.rushikesh.pdfscanner.data.db.device

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import com.rushikesh.pdfscanner.data.models.Device

@Dao
interface DeviceDbApi {
    @Insert(onConflict = OnConflictStrategy.Companion.REPLACE)
    suspend fun addDevice(device: Device)

    @Insert(onConflict = OnConflictStrategy.Companion.IGNORE)
    suspend fun addAll(devices: List<Device>)

}