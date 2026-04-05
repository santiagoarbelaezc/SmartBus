package com.smartbus.app.presentation.auth.register

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class RegisterViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(RegisterUiState())
    val uiState: StateFlow<RegisterUiState> = _uiState.asStateFlow()

    fun onFullNameChange(name: String) = _uiState.update { it.copy(fullName = name, error = null) }
    fun onDocumentTypeChange(type: String) = _uiState.update { it.copy(documentType = type, error = null) }
    fun onDocumentNumberChange(number: String) = _uiState.update { it.copy(documentNumber = number, error = null) }
    fun onEmailChange(email: String) = _uiState.update { it.copy(email = email, error = null) }
    fun onPhoneChange(phone: String) = _uiState.update { it.copy(phone = phone, error = null) }
    fun onPasswordChange(pass: String) = _uiState.update { it.copy(password = pass, error = null) }
    fun onConfirmPasswordChange(pass: String) = _uiState.update { it.copy(confirmPassword = pass, error = null) }
    fun onTermsToggle(accepted: Boolean) = _uiState.update { it.copy(termsAccepted = accepted, error = null) }
    fun togglePasswordVisibility() = _uiState.update { it.copy(isPasswordVisible = !it.isPasswordVisible) }

    fun register(onSuccess: () -> Unit) {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, error = null) }
            delay(1500) // Simulate network request
            _uiState.update { it.copy(isLoading = false) }
            onSuccess()
        }
    }
}
