package com.smartbus.app.presentation.auth.register

data class RegisterUiState(
    val fullName: String = "",
    val documentType: String = "C.C.",
    val documentNumber: String = "",
    val email: String = "",
    val phone: String = "",
    val password: String = "",
    val confirmPassword: String = "",
    val isPasswordVisible: Boolean = false,
    val termsAccepted: Boolean = false,
    val isLoading: Boolean = false,
    val error: String? = null
)

val documentTypes = listOf("C.C.", "C.E.", "Pasaporte", "T.I.")
