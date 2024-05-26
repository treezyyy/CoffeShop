package com.example.coffeshop.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.example.coffeshop.R

val OswaldBold = FontFamily(
    Font(R.font.heavy, FontWeight.SemiBold)
)
val OswaldLight = FontFamily(
    Font(R.font.book, FontWeight.Light)
)

val coffeshopTypography = Typography(
    displayLarge = androidx.compose.ui.text.TextStyle(
        fontFamily = OswaldBold,
        fontWeight = FontWeight.SemiBold,
        fontSize = 24.sp
    ),
    bodyLarge = androidx.compose.ui.text.TextStyle(
        fontFamily = OswaldLight,
        fontWeight = FontWeight.Light,
        fontSize = 16.sp
    )
)