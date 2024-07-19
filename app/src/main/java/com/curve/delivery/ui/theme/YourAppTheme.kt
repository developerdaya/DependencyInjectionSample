package com.curve.delivery.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.*
import androidx.compose.material.ripple.LocalRippleTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Shapes
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.graphics.Color

@Composable
fun YourAppTheme(content: @Composable () -> Unit) {
    val colors = if (isSystemInDarkTheme()) {
        darkColorScheme(
            primary = Color(0xFF1EB980),
            tertiary = Color(0xFF045D56),
            secondary = Color(0xFF1EB980)
        )
    } else {
        lightColorScheme(
            primary = Color(0xFF1EB980),
            tertiary = Color(0xFF045D56),
            secondary = Color(0xFF1EB980)
        )
    }

    CompositionLocalProvider(LocalRippleTheme provides NoRippleTheme) {
        MaterialTheme(
            colorScheme = colors,
            typography = Typography,
            content = content
        )
    }
}
