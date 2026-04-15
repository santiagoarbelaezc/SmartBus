package com.smartbus.app.presentation.payment

data class PaymentUiState(
    val route: String = "Medellín → Bogotá",
    val date: String = "15 Abr, 2026",
    val seat: String = "#12",
    val total: String = "$110.000",
    val selectedCardIndex: Int = 0,
    val selectedCardNumber: String = "",
    val cardName: String = "",
    val expiry: String = "",
    val cvc: String = ""
)
