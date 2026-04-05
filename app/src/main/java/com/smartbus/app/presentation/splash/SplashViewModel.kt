package com.smartbus.app.presentation.splash

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class SplashViewModel : ViewModel() {
    private val _isSessionActive = MutableStateFlow<Boolean?>(null)
    val isSessionActive: StateFlow<Boolean?> = _isSessionActive

    init {
        checkSession()
    }

    private fun checkSession() {
        viewModelScope.launch {
            delay(2000) // Mock delay
            _isSessionActive.value = true // Mock: always session active for now
        }
    }
}
