package com.smartbus.app.core

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

enum class AppTheme(val displayName: String, val displayNameEn: String, val emoji: String) {
    LIGHT("Claro", "Light", "☀️"),
    DARK("Oscuro", "Dark", "🌙"),
    SYSTEM("Sistema", "System", "📱")
}

/**
 * Singleton que mantiene el tema activo de la app.
 * Toda la UI observa este StateFlow para recomponerse ante cambios.
 */
object ThemeManager {
    private val _currentTheme = MutableStateFlow(AppTheme.LIGHT)
    val currentTheme: StateFlow<AppTheme> = _currentTheme.asStateFlow()

    fun setTheme(theme: AppTheme) {
        _currentTheme.value = theme
    }
}
