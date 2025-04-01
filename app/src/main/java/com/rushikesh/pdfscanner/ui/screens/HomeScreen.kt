package com.rushikesh.pdfscanner.ui.screens

import android.R.attr.text
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.rushikesh.pdfscanner.data.models.Device
import com.rushikesh.pdfscanner.ui.viewmodels.HomeViewModel

@Composable
fun HomeScreen(viewModel: HomeViewModel = hiltViewModel()) {
    var devices =  remember {mutableStateOf(listOf<Device>())}
    viewModel.syncDeviceDataInDb { devices.value = it }
    LazyColumn { items(devices.value) { device ->
        Text(text = device.toString(), modifier = Modifier.fillMaxSize().padding(8.dp))
    } }
}