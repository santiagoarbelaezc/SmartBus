package com.smartbus.app.presentation.travelcredit

data class TravelCreditUiState(
    val availableCredit: String = "$450.000",
    val status: String = "Activa",
    val tripsInfo: List<LevelTripInfo> = listOf(
        LevelTripInfo("Bronce", 1),
        LevelTripInfo("Plata", 2),
        LevelTripInfo("Oro", 3)
    ),
    val rules: List<String> = listOf(
        "30 días sin intereses",
        "Cargo automático a tu cuenta",
        "Válido en rutas nacionales"
    )
)

data class LevelTripInfo(
    val level: String,
    val count: Int
)
