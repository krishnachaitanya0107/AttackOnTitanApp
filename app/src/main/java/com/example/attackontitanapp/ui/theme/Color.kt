package com.example.attackontitanapp.ui.theme

import androidx.compose.material.Colors
import androidx.compose.ui.graphics.Color

val Teal200 = Color(0xFF03DAC5)

val DarkGreen = Color(0xFF294023)
val MediumGreen = Color(0xFF3B5C32)
val LightGreen = Color(0xFF6CB359)

val LightGray = Color(0xFFD8D8D8)
val DarkGray = Color(0xFF2A2A2A)
val StarColor = Color(0xFFFFC94D)

val ShimmerLightGray = Color(0xFFF1F1F1)
val ShimmerMediumGray = Color(0xFFE3E3E3)
val ShimmerDarkGray = Color(0xFF1D1D1D)

val Colors.statusBarColor
    get() = if (isLight) DarkGreen else Color.Black

val Colors.welcomeScreenBackgroundColor
    get() = if (isLight) Color.White else Color.Black

val Colors.titleColor
    get() = if (isLight) DarkGray else LightGray

val Colors.descriptionColor
    get() = if (isLight) DarkGray.copy(alpha = 0.5f)
    else LightGray.copy(alpha = 0.5f)

val Colors.activeIndicatorColor
    get() = if (!isLight) MediumGreen else DarkGreen

val Colors.inactiveIndicatorColor
    get() = if (isLight) LightGray else DarkGray

val Colors.buttonBackgroundColor
    get() = if (!isLight) MediumGreen else DarkGreen

val Colors.topAppBarContentColor: Color
    get() = if (isLight) Color.White else LightGray

val Colors.topAppBarBackgroundColor: Color
    get() = if (isLight) MediumGreen else Color.Black