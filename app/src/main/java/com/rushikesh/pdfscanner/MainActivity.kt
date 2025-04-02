package com.rushikesh.pdfscanner

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import com.rushikesh.pdfscanner.ui.navigation.AppNavHost
import com.rushikesh.pdfscanner.ui.screens.CustomBottomBar
import com.rushikesh.pdfscanner.ui.theme.DarkColorScheme
import com.rushikesh.pdfscanner.ui.theme.LightColorScheme
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private val TAG = MainActivity::class.simpleName


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //enableEdgeToEdge()
        setContent {
            val navController = rememberNavController()
            MaterialTheme(colorScheme = if (isSystemInDarkTheme()) DarkColorScheme else LightColorScheme) {
                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    bottomBar = { CustomBottomBar(navController) }) { innerPadding ->
//                    //AppNavHost()
//                    //ComposeReportScreen()
//                    ImageCaptureScreen()
//                }
                    AppNavHost(navController)
                }
            }
        }

    }
}
