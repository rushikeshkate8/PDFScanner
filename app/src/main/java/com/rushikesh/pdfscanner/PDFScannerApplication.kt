package com.rushikesh.pdfscanner

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class PDFScannerApplication : Application() {
    override fun onCreate() {
        super.onCreate()
    }
}