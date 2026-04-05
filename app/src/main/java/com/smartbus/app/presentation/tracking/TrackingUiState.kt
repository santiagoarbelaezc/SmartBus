package com.smartbus.app.presentation.tracking

data class TrackingUiState(
    val routeName: String = "Medellín → Bogotá",
    val origin: String = "Medellín",
    val destination: String = "Bogotá",
    val eta: String = "2h 45m",
    val status: String = "En camino",
    val busPlate: String = "SBT-452"
)
