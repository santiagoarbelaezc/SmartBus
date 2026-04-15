package com.smartbus.app.presentation.auth.login

data class LoginUiState(
    val email: String = "santiago@gmail.com",
    val password: String = "Santi2003",
    val isPasswordVisible: Boolean = false,
    val isLoading: Boolean = false,
    val error: String? = null
)
