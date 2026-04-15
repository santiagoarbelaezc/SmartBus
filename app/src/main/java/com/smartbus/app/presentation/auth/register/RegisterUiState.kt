package com.smartbus.app.presentation.auth.register

data class RegisterUiState(
    val fullName: String = "Santiago Arbelaez Contreras",
    val documentType: String = "C.C.",
    val documentNumber: String = "1001361185",
    val email: String = "santiago@gmail.com",
    val phone: String = "3054078225",
    val password: String = "Santi2003",
    val confirmPassword: String = "Santi2003",
    val isPasswordVisible: Boolean = false,
    val termsAccepted: Boolean = true,
    val isLoading: Boolean = false,
    val error: String? = null
)

val documentTypes = listOf("C.C.", "C.E.", "Pasaporte", "T.I.")
