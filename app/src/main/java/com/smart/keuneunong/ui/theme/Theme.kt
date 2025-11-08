package com.smart.keuneunong.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val DarkColorPalette = darkColorScheme(
    primary = Green700
)

private val LightColorPalette = lightColorScheme(
    primary = Blue500,
    onPrimary = Color.White,
    primaryContainer = Blue100,
    onPrimaryContainer = Blue900,

    secondary = Green500,
    onSecondary = Color.White,
    secondaryContainer = Green100,
    onSecondaryContainer = Green900,

    background = Gray50,
    onBackground = Gray900,

    surface = Color.White,
    onSurface = Gray900,
    surfaceVariant = Gray100,
    onSurfaceVariant = Gray500,
)

@Composable
fun JetpackComposeBoilerplateTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colorScheme = if (darkTheme) {
        DarkColorPalette
    } else {
        LightColorPalette
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        shapes = Shapes,
        content = content
    )
}