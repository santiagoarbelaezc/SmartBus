package com.smartbus.app.presentation.home

data class HomeUiState(
    val userName: String = "Santiago",
    val nextTrip: TripInfo? = TripInfo(
        origin = "Medellín",
        destination = "Bogotá",
        date = "15 Abr, 2026",
        time = "08:30 AM"
    ),
    val promotions: List<PromoItem> = listOf(
        PromoItem("Lujo a tu alcance", "Viaja con 20% desc. en rutas Premier"),
        PromoItem("Tu fidelidad premia", "Doble puntos este fin de semana")
    )
)

data class TripInfo(
    val origin: String,
    val destination: String,
    val date: String,
    val time: String
)

data class PromoItem(
    val title: String,
    val description: String
)
