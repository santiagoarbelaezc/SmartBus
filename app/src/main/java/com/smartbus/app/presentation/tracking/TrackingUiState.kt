package com.smartbus.app.presentation.tracking

data class TrackingUiState(
    val routeName: String = "Armenia → Filandia",
    val origin: String = "Armenia",
    val destination: String = "Filandia",
    val eta: String = "51 minutos",
    val status: String = "En camino",
    val busPlate: String = "SBT-452"
)
