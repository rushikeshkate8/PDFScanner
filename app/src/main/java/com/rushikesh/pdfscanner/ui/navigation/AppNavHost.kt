package com.rushikesh.pdfscanner.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.rushikesh.pdfscanner.ui.screens.ComposeReportScreen
import com.rushikesh.pdfscanner.ui.screens.HomeScreen
import com.rushikesh.pdfscanner.ui.screens.ImageCaptureScreen
import com.rushikesh.pdfscanner.ui.screens.LoginScreen

@Composable
fun AppNavHost(navController: NavHostController) {
    NavHost(navController, startDestination = "login") {
        composable("login") { LoginScreen(navController) }
        composable("devices") { HomeScreen() }
        composable("pdf_viewer") {ComposeReportScreen()}
        composable("image_capture") { ImageCaptureScreen() }
    }
}