package com.waynebloom.fetchingfromfetch.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.waynebloom.fetchingfromfetch.R

private val spaceGroteskFamily = FontFamily(
    Font(R.font.space_grotesk, style = FontStyle.Normal),
    Font(R.font.space_grotesk_light, weight = FontWeight.Light),
)

private val spaceMonoFamily = FontFamily(
    Font(R.font.space_mono, style = FontStyle.Normal),
    Font(R.font.space_mono_italic, style = FontStyle.Italic),
)

private val defaultTypography = Typography()
val Typography = defaultTypography.let {
    it.copy(
        bodyLarge = TextStyle(
            fontFamily = spaceMonoFamily,
            fontWeight = FontWeight.Normal,
            fontSize = 16.sp,
            lineHeight = 24.sp,
            letterSpacing = 0.5.sp
        ),
        titleLarge = it.titleLarge.copy(
            fontFamily = spaceGroteskFamily,
        ),
        headlineSmall = it.labelLarge.copy(
            fontFamily = spaceGroteskFamily,
        ),
    )
}