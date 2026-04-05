package com.smartbus.app.presentation.points

data class PointsUiState(
    val levelName: String = "Oro",
    val totalPoints: Int = 12450,
    val pointsToNextLevel: Int = 2550,
    val progress: Float = 0.8f,
    val benefits: List<String> = listOf(
        "Embarque Prioritario",
        "Selección de Asientos Premium Gratis",
        "Sala VIP en Terminales Principales",
        "Doble Acumulación en Rutas Nacionales"
    ),
    val history: List<PointTransaction> = listOf(
        PointTransaction("Viaje Medellín - Bogotá", "15 Abr, 2026", 450),
        PointTransaction("Bono de Cumpleaños", "10 Abr, 2026", 1000),
        PointTransaction("Viaje Cali - Medellín", "01 Mar, 2026", -300), // Redimido
        PointTransaction("Viaje Bogotá - Bucaramanga", "20 Feb, 2026", 350)
    )
)

data class PointTransaction(
    val description: String,
    val date: String,
    val amount: Int
)
