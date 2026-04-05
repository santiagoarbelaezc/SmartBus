package com.smartbus.app.presentation.seatselection

import com.smartbus.app.ui.components.SeatStatus

data class SeatSelectionUiState(
    val routeSummary: String = "Medellín → Bogotá",
    val seats: List<SeatItem> = List(40) { index ->
        SeatItem(
            id = index + 1,
            status = if (index % 5 == 0) SeatStatus.OCCUPIED else SeatStatus.AVAILABLE
        )
    },
    val selectedSeatId: Int? = null
)

data class SeatItem(
    val id: Int,
    val status: SeatStatus
)
