package com.rushikesh.pdfscanner.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.rushikesh.pdfscanner.ui.screens.HomeScreen
import com.rushikesh.pdfscanner.ui.screens.LoginScreen

@Composable
fun AppNavHost() {
    val navController = rememberNavController()
    NavHost(navController, startDestination = "home") {
        composable("login") { LoginScreen(navController) }
        composable("home") { HomeScreen() }
    }
}