package com.smartbus.app.presentation.profile

import androidx.lifecycle.ViewModel
import com.smartbus.app.core.AppLanguage
import com.smartbus.app.core.LanguageManager
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class ProfileViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(ProfileUiState())
    val uiState: StateFlow<ProfileUiState> = _uiState.asStateFlow()

    fun onLanguageChanged(language: AppLanguage) {
        LanguageManager.setLanguage(language)
        _uiState.update { it.copy(currentLanguage = language.displayName) }
    }

    fun updateProfile(name: String, email: String, phone: String, dept: String, city: String) {
        _uiState.update { it.copy(name = name, email = email, phone = phone, department = dept, city = city) }
    }

    fun changePassword(old: String, new: String) {
        // Simulation of password change success
    }

    fun toggleBiometric(enabled: Boolean) {
        _uiState.update { it.copy(isBiometricEnabled = enabled) }
    }

    fun toggle2FA(enabled: Boolean) {
        _uiState.update { it.copy(is2FAEnabled = enabled) }
    }

    fun toggleLocationData(enabled: Boolean) {
        _uiState.update { it.copy(shareLocationData = enabled) }
    }

    fun terminateAllSessions() {
        _uiState.update { it.copy(activeSessions = emptyList()) }
    }
}
