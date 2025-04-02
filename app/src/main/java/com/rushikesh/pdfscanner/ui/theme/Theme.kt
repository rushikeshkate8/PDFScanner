package com.rushikesh.pdfscanner.ui.theme

import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext

val DarkColorScheme = darkColorScheme(
    primary = Color(0xFF8AB4F8), // A brighter blue to stand out on dark background
    onPrimary = Color.Black,
    secondary = Color(0xFFB2FF59), // A more vibrant green for dark theme
    onSecondary = Color.Black,
    tertiary = Color(0xFFFFD54F), // A brighter yellow-orange for dark theme accents
    onTertiary = Color.Black,
    background = Color(0xFF1E1E1E), // Dark gray background
    onBackground = Color(0xFFE0E0E0), // Light gray text for readability
    surface = Color(0xFF303030), // Slightly lighter dark gray for surfaces
    onSurface = Color(0xFFD1D1D1), // Light gray text on surfaces
    primaryContainer = Color(0xFF295391), // Darker shade of primary
    onPrimaryContainer = Color(0xFFD0E4FF), // Lighter shade of primary
    secondaryContainer = Color(0xFF558B2F), // Darker shade of secondary
    onSecondaryContainer = Color(0xFFB7FFDA), // Lighter shade of secondary
    tertiaryContainer = Color(0xFFB38F00), // Darker shade of tertiary
    onTertiaryContainer = Color(0xFFFFD9B3), // Lighter shade of tertiary
    error = Color(0xFFFFCDD2),
    onError = Color(0xFFB71C1C),
    errorContainer = Color(0xFF8C1D18),
    onErrorContainer = Color(0xFFFFCDD2),
    outline = Color(0xFF757575) // Medium gray for borders and outlines
)


 val LightColorScheme = lightColorScheme(
    primary = Color(0xFF4A86E8), // A clean, slightly vibrant blue (for primary actions, highlights)
    onPrimary = Color.White,
    secondary = Color(0xFF69F0AE), // A fresh, subtle green (for secondary actions, success states)
    onSecondary = Color.Black,
    tertiary = Color(0xFFFFB74D), // A warm orange (for occasional accents, warnings)
    onTertiary = Color.Black,
    background = Color(0xFFF7F9FC), // Very light gray, almost white (clean background)
    onBackground = Color(0xFF212121), // Dark gray (for primary text)
    surface = Color.White, // White (for cards, dialogs, elevated surfaces)
    onSurface = Color(0xFF424242), // Medium gray (for secondary text on surfaces)
    primaryContainer = Color(0xFFD0E4FF), // Lighter shade of primary
    onPrimaryContainer = Color(0xFF004085), // Darker shade of primary
    secondaryContainer = Color(0xFFB7FFDA), // Lighter shade of secondary
    onSecondaryContainer = Color(0xFF004D39), // Darker shade of secondary
    tertiaryContainer = Color(0xFFFFD9B3), // Lighter shade of tertiary
    onTertiaryContainer = Color(0xFF664700), // Darker shade of tertiary
    error = Color(0xFFF44336), // Standard red for errors
    onError = Color.White,
    errorContainer = Color(0xFFFDECEA),
    onErrorContainer = Color(0xFF410E0B),
    outline = Color(0xFFBDBDBD) // Light gray for borders and outlines
)

@Composable
fun PDFScannerTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    // Dynamic color is available on Android 12+
    dynamicColor: Boolean = true,
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }

        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}