package com.rushikesh.pdfscanner.data.models

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "device_details")
data class Device(
    @PrimaryKey
    @SerializedName("id")
    val deviceId: Int,

    val name: String?,

    @Embedded
    val data: ExtraData?
)

data class ExtraData (
    @SerializedName("color")
    val color: String? = null,

    @SerializedName("capacity")
    val capacity: String? = null,

    @SerializedName("capacity GB")
    val capacityGB: Int? = null,

    @SerializedName("price")
    val price: Double? = null,

    @SerializedName("generation")
    val generation: String? = null,

    @SerializedName("year")
    val year: Int? = null,

    @SerializedName("CPU model")
    val cpuModel: String? = null,

    @SerializedName("Hard disk size")
    val hardDiskSize: String? = null,

    @SerializedName("Strap Colour")
    val strapColor: String? = null,

    @SerializedName("Case Size")
    val caseSize: String? = null,

    @SerializedName("Description")
    val description: String? = null,

    @SerializedName("Screen size")
    val screenSize: Double? = null
)
