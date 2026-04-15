package com.smartbus.app.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.graphics.Color
import com.smartbus.app.core.AppTheme
import com.smartbus.app.core.ThemeManager

// Colores extra para el dark scheme que no están en Color.kt base
private val Color_Surface_Dark         = Color(0xFF1E1E1E)
private val Color_Surface_Variant_Dark = Color(0xFF252525)
private val TextSecondary_Dark         = Color(0xFF9A9A9A)

// ─── DARK Color Scheme ────────────────────────────────────────────────────────
// Fondo: negro carbón + acentos dorados. Premium, elegante, nocturno.
private val SmartBusDarkColorScheme = darkColorScheme(
    primary             = Gold,           // CTA, iconos activos, acentos
    onPrimary           = Black,          // texto sobre primary
    primaryContainer    = Charcoal,       // cards, chips de categoría
    onPrimaryContainer  = Gold,

    secondary           = GoldDark,       // acentos secundarios
    onSecondary         = Black,
    secondaryContainer  = Color_Surface_Dark,
    onSecondaryContainer= OffWhite,

    tertiary            = Gold,
    onTertiary          = Black,

    background          = Black,          // fondo de pantalla
    onBackground        = White,

    surface             = Charcoal,       // cards, modales, bottom sheets
    onSurface           = White,
    surfaceVariant      = Color_Surface_Variant_Dark,
    onSurfaceVariant    = TextSecondary_Dark,

    outline             = Gold.copy(alpha = 0.3f),
    outlineVariant      = Color(0xFF2A2A2A),

    error               = ErrorRed,
    onError             = White,

    inversePrimary      = Black,
    inverseSurface      = White,
    inverseOnSurface    = Black,

    scrim               = Black.copy(alpha = 0.8f)
)

// ─── LIGHT Color Scheme ───────────────────────────────────────────────────────
// Fondo: blanco roto con acentos negro/dorado. Limpio, profesional, legible.
private val SmartBusLightColorScheme = lightColorScheme(
    primary             = Black,          // CTA, iconos activos
    onPrimary           = White,
    primaryContainer    = Color(0xFFF5F0E8),   // gold tinted container
    onPrimaryContainer  = Color(0xFF2C1A00),

    secondary           = Gold,           // acentos secundarios
    onSecondary         = Black,
    secondaryContainer  = Color(0xFFFFF8E7),
    onSecondaryContainer= Color(0xFF3A2800),

    tertiary            = Charcoal,
    onTertiary          = White,

    background          = Color(0xFFFAFAFA),   // blanco roto
    onBackground        = TextPrimary,

    surface             = White,          // cards, modales
    onSurface           = TextPrimary,
    surfaceVariant      = Color(0xFFF3F3F3),
    onSurfaceVariant    = TextSecondary,

    outline             = Color(0xFFE0D5C5),   // gold-tinted border
    outlineVariant      = Color(0xFFEEEEEE),

    error               = ErrorRed,
    onError             = White,

    inversePrimary      = Gold,
    inverseSurface      = Black,
    inverseOnSurface    = White,

    scrim               = Black.copy(alpha = 0.5f)
)



@Composable
fun SmartBusTheme(
    content: @Composable () -> Unit
) {
    val selectedTheme by ThemeManager.currentTheme.collectAsState()
    val systemDark = isSystemInDarkTheme()

    val isDark = when (selectedTheme) {
        AppTheme.DARK   -> true
        AppTheme.LIGHT  -> false
        AppTheme.SYSTEM -> systemDark
    }

    val colorScheme = if (isDark) SmartBusDarkColorScheme else SmartBusLightColorScheme

    MaterialTheme(
        colorScheme = colorScheme,
        typography  = Typography,
        content     = content
    )
}