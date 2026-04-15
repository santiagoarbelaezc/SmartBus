package com.smartbus.app.core

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

enum class AppLanguage(val code: String, val displayName: String, val flag: String) {
    SPANISH("es", "Español", "🇨🇴"),
    ENGLISH("en", "English", "🇺🇸")
}

/**
 * Singleton que mantiene el idioma activo de la app.
 * Toda la UI observa este StateFlow para recomponerse ante cambios.
 */
object LanguageManager {
    private val _currentLanguage = MutableStateFlow(AppLanguage.SPANISH)
    val currentLanguage: StateFlow<AppLanguage> = _currentLanguage.asStateFlow()

    fun setLanguage(language: AppLanguage) {
        _currentLanguage.value = language
    }
}
