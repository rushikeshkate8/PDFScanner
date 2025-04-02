package com.rushikesh.pdfscanner.ui.screens

import android.content.Context
import android.graphics.pdf.PdfRenderer
import android.net.Uri
import android.os.ParcelFileDescriptor
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTransformGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import androidx.core.net.toUri
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.net.URL

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ComposeReportScreen(pdfUrl: String = "https://fssservices.bookxpert.co/GeneratedPDF/Companies/nadc/2024-2025/BalanceSheet.pdf") {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Balance Sheet Report") },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.onPrimaryContainer
                )
            )
        }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            ComposePdfViewer(pdfUrl)
        }
    }
}

@Composable
fun ComposePdfViewer(pdfUrl: String) {
    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()
    val lazyListState = rememberLazyListState()

    var isLoading by remember { mutableStateOf(true) }
    var errorMessage by remember { mutableStateOf<String?>(null) }
    var pdfPageCount by remember { mutableStateOf(0) }
    var pdfPages by remember { mutableStateOf(listOf<PdfPage>()) }

    // Load PDF when the composable is first launched
    LaunchedEffect(pdfUrl) {
        coroutineScope.launch {
            try {
                isLoading = true
                errorMessage = null

                val file = downloadPdfToCache(context, pdfUrl)
                val pages = renderPdfPages(context, file.toUri())

                pdfPages = pages
                pdfPageCount = pages.size
                isLoading = false
            } catch (e: Exception) {
                Log.e("PDF_VIEWER", "Error loading PDF", e)
                isLoading = false
                errorMessage = "Failed to load PDF: ${e.localizedMessage ?: "Unknown error"}"
            }
        }
    }

    Box(modifier = Modifier.fillMaxSize()) {
        when {
            isLoading -> {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        CircularProgressIndicator()
                        Spacer(modifier = Modifier.height(16.dp))
                        Text("Loading PDF...")
                    }
                }
            }

            errorMessage != null -> {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier.padding(16.dp)) {
                        Icon(
                            imageVector = Icons.Default.Info,
                            contentDescription = "Error",
                            tint = MaterialTheme.colorScheme.error,
                            modifier = Modifier.size(48.dp)
                        )
                        Spacer(modifier = Modifier.height(16.dp))
                        Text(
                            text = errorMessage ?: "Unknown error occurred",
                            style = MaterialTheme.typography.bodyLarge,
                            color = MaterialTheme.colorScheme.error
                        )
                        Spacer(modifier = Modifier.height(16.dp))
                        Button(onClick = {
                            coroutineScope.launch {
                                isLoading = true
                                errorMessage = null
                                try {
                                    val file = downloadPdfToCache(context, pdfUrl)
                                    val pages = renderPdfPages(context, file.toUri())

                                    pdfPages = pages
                                    pdfPageCount = pages.size
                                    isLoading = false
                                } catch (e: Exception) {
                                    isLoading = false
                                    errorMessage = "Failed to load PDF: ${e.localizedMessage ?: "Unknown error"}"
                                }
                            }
                        }) {
                            Text("Retry")
                        }
                    }
                }
            }

            else -> {
                LazyColumn(
                    state = lazyListState,
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.spacedBy(8.dp),
                    contentPadding = PaddingValues(8.dp)
                ) {
                    items(pdfPages) { page ->
                        PdfPageView(page)
                    }
                }
            }
        }
    }
}

@Composable
fun PdfPageView(page: PdfPage) {
    var scale by remember { mutableStateOf(1f) }
    var offsetX by remember { mutableStateOf(0f) }
    var offsetY by remember { mutableStateOf(0f) }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(4.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Box(modifier = Modifier.fillMaxWidth()) {
            Image(
                bitmap = page.bitmap.asImageBitmap(),
                contentDescription = "PDF Page ${page.pageNumber}",
                modifier = Modifier
                    .fillMaxWidth()
                    .graphicsLayer {
                        scaleX = scale
                        scaleY = scale
                        translationX = offsetX
                        translationY = offsetY
                    }
                    .pointerInput(Unit) {
                        detectTransformGestures { _, pan, zoom, _ ->
                            scale = (scale * zoom).coerceIn(1f, 5f)

                            // Only apply pan if zoomed in
                            if (scale > 1f) {
                                offsetX += pan.x
                                offsetY += pan.y

                                // Limit panning to reasonable bounds
                                val maxX = (size.width * (scale - 1)) / 2
                                val maxY = (size.height * (scale - 1)) / 2

                                offsetX = offsetX.coerceIn(-maxX, maxX)
                                offsetY = offsetY.coerceIn(-maxY, maxY)
                            } else {
                                // Reset offsets when zoom level is at minimum
                                offsetX = 0f
                                offsetY = 0f
                            }
                        }
                    }
            )

            // Page number indicator
            Box(
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .padding(8.dp)
                    .background(
                        MaterialTheme.colorScheme.surface.copy(alpha = 0.7f),
                        shape = MaterialTheme.shapes.small
                    )
                    .padding(horizontal = 8.dp, vertical = 4.dp)
            ) {
                Text(
                    text = "Page ${page.pageNumber}",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurface
                )
            }
        }
    }
}

data class PdfPage(
    val pageNumber: Int,
    val bitmap: android.graphics.Bitmap
)

// Download PDF file to cache directory
private suspend fun downloadPdfToCache(context: Context, pdfUrl: String): File = withContext(Dispatchers.IO) {
    val fileName = "pdf_${pdfUrl.hashCode()}.pdf"
    val file = File(context.cacheDir, fileName)

    if (file.exists()) {
        return@withContext file
    }

    try {
        val url = URL(pdfUrl)
        val connection = url.openConnection()
        connection.connect()

        val inputStream = connection.getInputStream()
        val outputStream = FileOutputStream(file)

        inputStream.use { input ->
            outputStream.use { output ->
                input.copyTo(output)
            }
        }

        return@withContext file
    } catch (e: IOException) {
        throw IOException("Failed to download PDF: ${e.message}", e)
    }
}

// Render PDF pages using PdfRenderer
private suspend fun renderPdfPages(context: Context, pdfUri: Uri): List<PdfPage> = withContext(Dispatchers.IO) {
    val pages = mutableListOf<PdfPage>()

    try {
        val contentResolver = context.contentResolver
        val pfd = contentResolver.openFileDescriptor(pdfUri, "r")

        pfd?.use { parcelFileDescriptor ->
            val renderer = PdfRenderer(parcelFileDescriptor)

            for (i in 0 until renderer.pageCount) {
                renderer.openPage(i).use { page ->
                    val width = page.width * 2  // Multiply by 2 for higher resolution
                    val height = page.height * 2

                    val bitmap = android.graphics.Bitmap.createBitmap(
                        width,
                        height,
                        android.graphics.Bitmap.Config.ARGB_8888
                    )

                    page.render(
                        bitmap,
                        null,
                        null,
                        PdfRenderer.Page.RENDER_MODE_FOR_DISPLAY
                    )

                    pages.add(PdfPage(i + 1, bitmap))
                }
            }

            renderer.close()
        }

        return@withContext pages
    } catch (e: Exception) {
        throw Exception("Failed to render PDF: ${e.message}", e)
    }
}