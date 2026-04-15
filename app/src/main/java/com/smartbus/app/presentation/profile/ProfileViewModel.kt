package com.smartbus.app.presentation.profile

import androidx.lifecycle.ViewModel
import com.smartbus.app.core.AppLanguage
import com.smartbus.app.core.LanguageManager
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class ProfileViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(ProfileUiState())
    val uiState: StateFlow<ProfileUiState> = _uiState.asStateFlow()

    fun onLanguageChanged(language: AppLanguage) {
        LanguageManager.setLanguage(language)
        _uiState.value = _uiState.value.copy(currentLanguage = language.displayName)
    }
}
