package com.smartbus.app.presentation.seatselection

import androidx.lifecycle.ViewModel
import com.smartbus.app.ui.components.SeatStatus
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class SeatSelectionViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(SeatSelectionUiState())
    val uiState: StateFlow<SeatSelectionUiState> = _uiState.asStateFlow()

    fun onSeatClick(seatId: Int) {
        val current = _uiState.value
        val newSeats = current.seats.map {
            when {
                it.id == seatId && it.status == SeatStatus.SELECTED ->
                    it.copy(status = SeatStatus.AVAILABLE)
                it.id == seatId ->
                    it.copy(status = SeatStatus.SELECTED)
                it.status == SeatStatus.SELECTED ->
                    it.copy(status = SeatStatus.AVAILABLE)
                else -> it
            }
        }
        val selectedId = if (current.selectedSeatId == seatId) null else seatId
        _uiState.value = current.copy(seats = newSeats, selectedSeatId = selectedId)
    }
}
