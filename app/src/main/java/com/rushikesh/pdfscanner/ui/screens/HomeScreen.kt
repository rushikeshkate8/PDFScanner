package com.rushikesh.pdfscanner.ui.screens

import android.R.attr.text
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.rushikesh.pdfscanner.data.models.Device
import com.rushikesh.pdfscanner.data.models.ExtraData
import com.rushikesh.pdfscanner.ui.viewmodels.HomeViewModel

@Composable
fun HomeScreen(viewModel: HomeViewModel = hiltViewModel()) {
    var devices =  remember {mutableStateOf(listOf<Device>())}
    viewModel.syncDeviceDataInDb { devices.value = it }
//    LazyColumn { items(devices.value) { device ->
//        Text(text = device.toString(), modifier = Modifier.fillMaxSize().padding(8.dp))
//    } }
    DeviceListScreen(devices.value)
}


@Composable
fun DeviceListScreen(devices: List<Device>) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(8.dp)
    ) {
        LazyColumn(
            contentPadding = PaddingValues(vertical = 8.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(devices) { device ->
                DeviceCard(device)
            }
        }
    }
}

@Composable
fun DeviceCard(device: Device) {
    var expanded = remember { mutableStateOf(false) }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .animateContentSize(
                animationSpec = spring(
                    dampingRatio = Spring.DampingRatioMediumBouncy,
                    stiffness = Spring.StiffnessLow
                )
            ).padding(8.dp),
        shape = RoundedCornerShape(12.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            // Header row with expand/collapse button
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { expanded.value = !expanded.value },
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Device name and basic info
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = device.name ?: "Unknown Device",
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.Bold
                    )

                    device.data?.let { data ->
                        // Show a few key details in collapsed view
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            data.color?.let { color ->
                                ColorDot(colorName = color)
                                Spacer(modifier = Modifier.width(8.dp))
                                Text(
                                    text = color,
                                    style = MaterialTheme.typography.bodyMedium,
                                    color = MaterialTheme.colorScheme.onSurfaceVariant
                                )
                            }

                            data.capacity?.let {
                                Text(
                                    text = " • $it",
                                    style = MaterialTheme.typography.bodyMedium,
                                    color = MaterialTheme.colorScheme.onSurfaceVariant
                                )
                            }

                            data.price?.let {
                                Text(
                                    text = " • $$it",
                                    style = MaterialTheme.typography.bodyMedium,
                                    color = MaterialTheme.colorScheme.onSurfaceVariant
                                )
                            }
                        }
                    }
                }

                // Expand/collapse icon
                Icon(
                    imageVector = if (expanded.value) Icons.Filled.KeyboardArrowUp else Icons.Filled.KeyboardArrowDown,
                    contentDescription = if (expanded.value) "Show less" else "Show more",
                    tint = MaterialTheme.colorScheme.primary
                )
            }

            // Expanded details
            if (expanded.value) {
                Spacer(modifier = Modifier.height(16.dp))

                device.data?.let { data ->
                    // Create a list of spec pairs to display
                    val specs = listOfNotNull(
                        data.generation?.let { "Generation" to it },
                        data.year?.let { "Year" to it.toString() },
                        data.cpuModel?.let { "CPU Model" to it },
                        data.hardDiskSize?.let { "Hard Disk Size" to it },
                        data.capacityGB?.let { "Capacity (GB)" to it.toString() },
                        data.strapColor?.let { "Strap Color" to it },
                        data.caseSize?.let { "Case Size" to it },
                        data.screenSize?.let { "Screen Size" to "$it inches" }
                    )

                    if (specs.isNotEmpty()) {
                        Text(
                            text = "Specifications",
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.padding(bottom = 8.dp)
                        )

                        specs.forEach { (name, value) ->
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(vertical = 4.dp),
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Text(
                                    text = name,
                                    style = MaterialTheme.typography.bodyMedium,
                                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                                    modifier = Modifier.weight(1f)
                                )
                                Text(
                                    text = value,
                                    style = MaterialTheme.typography.bodyMedium,
                                    fontWeight = FontWeight.SemiBold,
                                    modifier = Modifier.weight(1f)
                                )
                            }

                            if (specs.last() != (name to value)) {
                                Divider(
                                    modifier = Modifier.padding(vertical = 4.dp),
                                    color = MaterialTheme.colorScheme.outlineVariant.copy(alpha = 0.5f)
                                )
                            }
                        }
                    }

                    // Display description if available
                    data.description?.let {
                        Spacer(modifier = Modifier.height(16.dp))
                        Text(
                            text = "Description",
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = it,
                            style = MaterialTheme.typography.bodyMedium
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun ColorDot(colorName: String) {
    Box(
        modifier = Modifier
            .size(12.dp)
            .background(
                when (colorName.lowercase()) {
                    "black" -> Color.Black
                    "white" -> Color.White
                    "silver" -> Color.LightGray
                    "gold" -> Color(0xFFFFD700)
                    "red" -> Color.Red
                    "blue" -> Color.Blue
                    "green" -> Color.Green
                    else -> MaterialTheme.colorScheme.primary
                },
                shape = RoundedCornerShape(6.dp)
            )
    )
}

// Preview function with sample data
@Preview
@Composable
fun DeviceListPreview() {
    MaterialTheme {
        DeviceListScreen(
            devices = listOf(
                Device(
                    deviceId = 1001,
                    name = "iPhone 13 Pro",
                    data = ExtraData(
                        color = "Gold",
                        capacity = "256GB",
                        capacityGB = 256,
                        price = 999.99,
                        generation = "13 Pro",
                        year = 2021,
                        cpuModel = "A15 Bionic",
                        screenSize = 6.1,
                        description = "The iPhone 13 Pro is Apple's premium smartphone featuring advanced camera capabilities."
                    )
                ),
                Device(
                    deviceId = 1002,
                    name = "MacBook Pro",
                    data = ExtraData(
                        color = "Silver",
                        capacity = "512GB",
                        capacityGB = 512,
                        price = 1999.99,
                        generation = "M2 Pro",
                        year = 2023,
                        cpuModel = "Apple M2 Pro",
                        hardDiskSize = "512GB SSD",
                        screenSize = 14.2,
                        description = "Powerful laptop for professional use with the latest M2 Pro chip."
                    )
                ),
                Device(
                    deviceId = 1003,
                    name = "Apple Watch Series 8",
                    data = ExtraData(
                        color = "Black",
                        strapColor = "Midnight",
                        caseSize = "45mm",
                        price = 399.99,
                        generation = "Series 8",
                        year = 2022
                    )
                )
            )
        )
    }
}