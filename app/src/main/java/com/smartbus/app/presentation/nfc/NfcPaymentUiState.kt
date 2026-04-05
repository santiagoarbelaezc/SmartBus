package com.smartbus.app.presentation.nfc

enum class NfcStatus {
    Idle, Reading, Success, Error
}

data class NfcPaymentUiState(
    val status: NfcStatus = NfcStatus.Idle,
    val availableBalance: String = "$45.000",
    val approxFare: String = "$2.950 COP",
    val lastPayment: String = "Hoy, 4:15 PM - Ruta 247",
    val usePoints: Boolean = false
)
