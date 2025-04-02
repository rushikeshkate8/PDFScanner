package com.rushikesh.pdfscanner.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Camera
import androidx.compose.material.icons.filled.CameraAlt
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.PictureAsPdf
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState

enum class BottomNavItem(
    val title: String,
    val icon: androidx.compose.ui.graphics.vector.ImageVector
) {
    PDFViewer("PDF Viewer", Icons.Filled.PictureAsPdf),
    DeviceList("Devices", Icons.Filled.List),
    ImageCapture("Image Capture", Icons.Filled.CameraAlt)
}

@Composable
fun CustomBottomBar(
    navController: NavHostController
) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(95.dp).padding(top = 16.dp).background(MaterialTheme.colorScheme.surface), // Standard bottom bar height
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceAround // Distribute items
    ) {

        // Left Tab (Device List)
        NavigationBarItem(
            selected = "devices" == currentRoute ?: "devices"
            ,
            onClick = { navController.navigate("devices") },
            icon = {
                Icon(
                    BottomNavItem.DeviceList.icon,
                    contentDescription = BottomNavItem.DeviceList.title
                )
            },
            label = { Text(BottomNavItem.DeviceList.title) }
        )

        // Spacer to push the central button to the middle visually
        Spacer(modifier = Modifier.weight(0.5f, fill = true))

        // Right Tab (PDF Viewer)
        NavigationBarItem(
            selected = "pdf_viewer" == currentRoute ?: "devices",
            onClick = { navController.navigate("pdf_viewer")},
            icon = {
                Icon(
                    BottomNavItem.PDFViewer.icon,
                    contentDescription = BottomNavItem.PDFViewer.title
                )
            },
            label = { Text(BottomNavItem.PDFViewer.title) }
        )
    }

    // Central Overlapping Action Button
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 16.dp), // Adjust bottom padding to position the button
        contentAlignment = Alignment.BottomCenter
    ) {
        FloatingActionButton(
            onClick = {navController.navigate("image_capture")},
            elevation = FloatingActionButtonDefaults.elevation(6.dp), modifier = Modifier.size(60.dp) // Optional elevation
        ) {
            Icon(Icons.Filled.CameraAlt, contentDescription = "Capture Image")
        }
    }
}
//
//@Composable
//fun MainScreen() {
//    var currentScreen by remember { mutableStateOf(BottomNavItem.PDFViewer.title) }
//
//    Scaffold(
//        bottomBar = {
//            CustomBottomBar(
//                currentScreen = currentScreen,
//                onTabClick = { item ->
//                    currentScreen = item.title
//                    // Handle navigation to the respective screen (PDF Viewer or Device List)
//                    println("Clicked on ${item.title}")
//                },
//                onCaptureClick = {
//                    // Handle the image capture action
//                    println("Capture Image clicked")
//                    currentScreen = BottomNavItem.ImageCapture.title
//                }
//            )
//        }
//    ) { paddingValues ->
//        // Content of your screens based on the currentScreen state
//        Box(modifier = Modifier.padding(paddingValues)) {
//            when (currentScreen) {
//                BottomNavItem.PDFViewer.title -> Text("PDF Viewer Screen Content")
//                BottomNavItem.DeviceList.title -> Text("Device List Screen Content")
//                else -> Text("Unknown Screen")
//            }
//
//            // You would likely have your Image Capture Screen logic here,
//            // possibly triggered by the central button's action.
//            if (/* Some condition to show Image Capture Screen */ false) {
//                Text("Image Capture Screen Content")
//            }
//        }
//    }
//}
//
//@Composable
//fun PreviewCustomBottomBar() {
//    CustomBottomBar(
//        currentScreen = BottomNavItem.PDFViewer.title,
//        onTabClick = {},
//        onCaptureClick = {}
//    )
//}

//@Preview(showBackground = true)
//@Composable
//fun PreviewMainScreen() {
//    MainScreen()
//}