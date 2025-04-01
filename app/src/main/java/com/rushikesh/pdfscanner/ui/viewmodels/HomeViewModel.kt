package com.rushikesh.pdfscanner.ui.viewmodels

import android.util.Log
import androidx.compose.runtime.DisposableEffect
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.rushikesh.pdfscanner.data.db.device.DeviceDbApi
import com.rushikesh.pdfscanner.data.models.Device
import com.rushikesh.pdfscanner.data.network.device_details.DeviceDetailsApi
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    val deviceDetailsApi: DeviceDetailsApi,
    val deviceDbApi: DeviceDbApi
) : ViewModel() {
    private val TAG = HomeViewModel::class.simpleName

    init {

    }

    fun syncDeviceDataInDb(devices: (List<Device>) -> Unit) {
        viewModelScope.launch(Dispatchers.IO) {
            val devices = deviceDetailsApi.getAllDevices()
            devices(devices)
            Log.i(
                TAG,
                devices.toString()
            )
            devices.forEach { device -> deviceDbApi.addDevice(device) }
        }
    }
}