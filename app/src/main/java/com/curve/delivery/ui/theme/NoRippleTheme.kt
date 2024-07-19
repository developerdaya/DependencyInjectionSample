package com.curve.delivery.ui.theme

import androidx.compose.runtime.Composable
import androidx.compose.material.ripple.RippleAlpha
import androidx.compose.material.ripple.RippleTheme
import androidx.compose.ui.graphics.Color

object NoRippleTheme : RippleTheme {
    @Composable
    override fun defaultColor() = Color.Unspecified

    @Composable
    override fun rippleAlpha() = RippleAlpha(
        pressedAlpha = 0.0f,
        draggedAlpha = 0.0f,
        focusedAlpha = 0.0f,
        hoveredAlpha = 0.0f,


    )
}
