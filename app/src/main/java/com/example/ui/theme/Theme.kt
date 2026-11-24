package com.example.ui.theme

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Shapes
import androidx.compose.material3.Typography
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.R

object KraftPalette {
    val Backdrop = Color(0xFFEFECE4)
    val SheetBackground = Color(0xFFFAF8F3)
    val CardSurface = Color(0xFFFFFFFF)
    val SoftSurface = Color(0xFFF5F2EB)
    val SoftWarmSurface = Color(0xFFFAF5EB)
    val HeaderSurface = Color(0xFFF5F0E6)
    val MediaBackground = Color(0xFFF5EFE6)
    val Border = Color(0xFFE5DEC9)
    val StrongBorder = Color(0xFFD8CFC0)
    val SubtleDivider = Color(0xFFF0EAE0)
    val AmberPrimary = Color(0xFFB45309)
    val AmberDeep = Color(0xFF92400E)
    val AmberDark = Color(0xFF78350F)
    val AmberLight = Color(0xFFFEF3C7)
    val AmberGold = Color(0xFFFDE68A)
    val AmberTint = Color(0xFFFFFBEB)
    val InkPrimary = Color(0xFF1C1917)
    val InkSecondary = Color(0xFF44403C)
    val InkMuted = Color(0xFF57534E)
    val InkSubtle = Color(0xFF78716C)
    val InkHint = Color(0xFFA8A29E)
    val HalftoneDot = Color(0xFFC8B69E)
    val WhatsAppGreen = Color(0xFF15803D)
    val EitaaOrange = Color(0xFFEA580C)
    val RubikaPurple = Color(0xFF6D28D9)
    val DangerRed = Color(0xFFB91C1C)
    val DangerBg = Color(0xFFFEF2F2)
    val SuccessBg = Color(0xFFDCFCE7)
}

val VazirmatnFontFamily = FontFamily(
    Font(R.font.vazirmatn_regular, FontWeight.Normal),
    Font(R.font.vazirmatn_medium, FontWeight.Medium),
    Font(R.font.vazirmatn_semibold, FontWeight.SemiBold),
    Font(R.font.vazirmatn_bold, FontWeight.Bold),
    Font(R.font.vazirmatn_extrabold, FontWeight.ExtraBold),
    Font(R.font.vazirmatn_extrabold, FontWeight.Black),
)

val JetBrainsMonoFontFamily = FontFamily(
    Font(R.font.jetbrains_mono_medium, FontWeight.Normal),
    Font(R.font.jetbrains_mono_medium, FontWeight.Medium),
    Font(R.font.jetbrains_mono_medium, FontWeight.SemiBold),
    Font(R.font.jetbrains_mono_medium, FontWeight.Bold),
)

private val KraftLightColorScheme = lightColorScheme(
    primary = KraftPalette.AmberDeep,
    onPrimary = Color.White,
    primaryContainer = KraftPalette.AmberLight,
    onPrimaryContainer = KraftPalette.AmberDark,
    secondary = KraftPalette.AmberPrimary,
    onSecondary = Color.White,
    secondaryContainer = KraftPalette.SoftWarmSurface,
    onSecondaryContainer = KraftPalette.AmberDeep,
    tertiary = KraftPalette.WhatsAppGreen,
    onTertiary = Color.White,
    background = KraftPalette.Backdrop,
    onBackground = KraftPalette.InkPrimary,
    surface = KraftPalette.SheetBackground,
    onSurface = KraftPalette.InkPrimary,
    surfaceVariant = KraftPalette.SoftSurface,
    onSurfaceVariant = KraftPalette.InkMuted,
    outline = KraftPalette.Border,
    outlineVariant = KraftPalette.SubtleDivider,
    error = KraftPalette.DangerRed,
    onError = Color.White,
    errorContainer = KraftPalette.DangerBg,
    onErrorContainer = KraftPalette.DangerRed,
)

val CatalogTypography = Typography(
    displaySmall = TextStyle(
        fontFamily = VazirmatnFontFamily,
        fontWeight = FontWeight.Black,
        fontSize = 26.sp,
        lineHeight = 36.sp,
    ),
    headlineMedium = TextStyle(
        fontFamily = VazirmatnFontFamily,
        fontWeight = FontWeight.Black,
        fontSize = 22.sp,
        lineHeight = 32.sp,
    ),
    headlineSmall = TextStyle(
        fontFamily = VazirmatnFontFamily,
        fontWeight = FontWeight.ExtraBold,
        fontSize = 19.sp,
        lineHeight = 28.sp,
    ),
    titleLarge = TextStyle(
        fontFamily = VazirmatnFontFamily,
        fontWeight = FontWeight.ExtraBold,
        fontSize = 17.sp,
        lineHeight = 26.sp,
    ),
    titleMedium = TextStyle(
        fontFamily = VazirmatnFontFamily,
        fontWeight = FontWeight.Bold,
        fontSize = 15.sp,
        lineHeight = 22.sp,
    ),
    titleSmall = TextStyle(
        fontFamily = VazirmatnFontFamily,
        fontWeight = FontWeight.Bold,
        fontSize = 13.sp,
        lineHeight = 20.sp,
    ),
    bodyLarge = TextStyle(
        fontFamily = VazirmatnFontFamily,
        fontWeight = FontWeight.Normal,
        fontSize = 14.sp,
        lineHeight = 24.sp,
    ),
    bodyMedium = TextStyle(
        fontFamily = VazirmatnFontFamily,
        fontWeight = FontWeight.Normal,
        fontSize = 13.sp,
        lineHeight = 22.sp,
    ),
    bodySmall = TextStyle(
        fontFamily = VazirmatnFontFamily,
        fontWeight = FontWeight.Medium,
        fontSize = 11.5.sp,
        lineHeight = 18.sp,
    ),
    labelLarge = TextStyle(
        fontFamily = VazirmatnFontFamily,
        fontWeight = FontWeight.Bold,
        fontSize = 13.sp,
        lineHeight = 18.sp,
    ),
    labelMedium = TextStyle(
        fontFamily = VazirmatnFontFamily,
        fontWeight = FontWeight.SemiBold,
        fontSize = 11.5.sp,
        lineHeight = 16.sp,
    ),
    labelSmall = TextStyle(
        fontFamily = VazirmatnFontFamily,
        fontWeight = FontWeight.Medium,
        fontSize = 10.5.sp,
        lineHeight = 14.sp,
    ),
)

val CatalogShapes = Shapes(
    small = RoundedCornerShape(12.dp),
    medium = RoundedCornerShape(16.dp),
    large = RoundedCornerShape(24.dp),
    extraLarge = RoundedCornerShape(28.dp),
)

@Composable
fun MohammadCartonTheme(
    content: @Composable () -> Unit,
) {
    MaterialTheme(
        colorScheme = KraftLightColorScheme,
        typography = CatalogTypography,
        shapes = CatalogShapes,
        content = content,
    )
}
