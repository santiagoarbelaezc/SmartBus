package com.smartbus.app.presentation.auth.forgotpassword

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

data class ForgotPasswordUiState(
    val email: String = "",
    val isLoading: Boolean = false,
    val error: String? = null
)

class ForgotPasswordViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(ForgotPasswordUiState())
    val uiState: StateFlow<ForgotPasswordUiState> = _uiState.asStateFlow()

    fun onEmailChange(value: String) {
        _uiState.value = _uiState.value.copy(email = value, error = null)
    }

    fun sendResetEmail(onSuccess: () -> Unit) {
        val email = _uiState.value.email.trim()
        if (email.isBlank() || !email.contains("@")) {
            _uiState.value = _uiState.value.copy(error = "Ingresa un correo válido")
            return
        }
        _uiState.value = _uiState.value.copy(isLoading = true, error = null)
        // Simulación: en producción aquí iría la llamada a Firebase / backend
        android.os.Handler(android.os.Looper.getMainLooper()).postDelayed({
            _uiState.value = _uiState.value.copy(isLoading = false)
            onSuccess()
        }, 1500)
    }
}
