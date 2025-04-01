package com.rushikesh.pdfscanner.ui.screens

import android.app.Activity.RESULT_OK
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.IntentSenderRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.rushikesh.pdfscanner.ui.viewmodels.LoginViewModel
import com.rushikesh.pdfscanner.R

@Composable
fun LoginScreen(navController: NavController, viewModel: LoginViewModel = hiltViewModel()) {
    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.StartIntentSenderForResult(),
        onResult = { result ->
            if (result.resultCode == RESULT_OK) {
                viewModel.signInWithIntent(
                    intent = result.data ?: return@rememberLauncherForActivityResult
                )
                navController.navigate("home")
            }
        }
    )

    Box(contentAlignment = Alignment.Center, modifier = Modifier.fillMaxSize()) {
        IconButton({
            viewModel.signIn { intentSender ->
                launcher.launch(
                    IntentSenderRequest.Builder(
                        intentSender
                    ).build()
                )
            }
        }, modifier = Modifier.fillMaxWidth()) {
            Image(
                painter = painterResource(if (!isSystemInDarkTheme()) R.drawable.android_light_rd_ctn else R.drawable.android_dark_rd_ctn),
                "Continue using Google",
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}