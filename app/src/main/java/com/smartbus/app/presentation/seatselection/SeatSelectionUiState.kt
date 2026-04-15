package com.smartbus.app.presentation.seatselection

import com.smartbus.app.ui.components.SeatStatus

// 10 filas × 4 asientos = 40 asientos en total
// Columnas: A (izq-ventana), B (izq-pasillo), C (der-pasillo), D (der-ventana)
private val occupiedIds = setOf(3, 7, 8, 12, 15, 19, 22, 26, 28, 31, 35, 38)

fun generateSeats(): List<SeatItem> {
    val rows = 10
    val cols = listOf("A", "B", "C", "D")
    val seats = mutableListOf<SeatItem>()
    var id = 1
    for (row in 1..rows) {
        for (col in cols) {
            val label = "$col$row"
            val status = if (occupiedIds.contains(id)) SeatStatus.OCCUPIED else SeatStatus.AVAILABLE
            seats.add(SeatItem(id = id, label = label, col = col, row = row, status = status))
            id++
        }
    }
    return seats
}

data class SeatSelectionUiState(
    val origin: String = "Armenia",
    val destination: String = "Medellín",
    val departureTime: String = "06:00 AM",
    val company: String = "Bolivariano",
    val seats: List<SeatItem> = generateSeats(),
    val selectedSeatId: Int? = null
) {
    val routeSummary get() = "$origin → $destination"
    val selectedLabel get() = seats.find { it.id == selectedSeatId }?.label
}

data class SeatItem(
    val id: Int,
    val label: String,
    val col: String,
    val row: Int,
    val status: SeatStatus
)
