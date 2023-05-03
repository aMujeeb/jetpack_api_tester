package com.mujapps.composetesterx.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val DarkColorPalette = darkColors(
    primary = primary_color,
    primaryVariant = primary_dark_color,
    secondary = accent_color,
    background = background_color,
    surface = white_background,
    onBackground = border_color,
    onSurface = text_color
)

private val LightColorPalette = lightColors(
    primary = primary_color,
    primaryVariant = primary_dark_color,
    secondary = accent_color,
    background = background_color,
    surface = white_background,
    onBackground = border_color,
    onSurface = text_color
    /* Other default colors to override
    background = Color.White,
    surface = Color.White,
    onPrimary = Color.White,
    onSecondary = Color.Black,
    onBackground = Color.Black,
    onSurface = Color.Black,
    */
)

@Composable
fun ComposeTesterXTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colors = if (darkTheme) {
        DarkColorPalette
    } else {
        LightColorPalette
    }

    MaterialTheme(
        colors = colors,
        typography = Typography,
        shapes = Shapes,
        content = content
    )
}