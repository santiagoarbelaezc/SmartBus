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
        val currentSeats = _uiState.value.seats
        val newSeats = currentSeats.map {
            if (it.id == seatId) {
                if (it.status == SeatStatus.SELECTED) it.copy(status = SeatStatus.AVAILABLE)
                else it.copy(status = SeatStatus.SELECTED)
            } else if (it.status == SeatStatus.SELECTED) {
                it.copy(status = SeatStatus.AVAILABLE)
            } else it
        }
        
        val selectedId = if (_uiState.value.selectedSeatId == seatId) null else seatId
        
        _uiState.value = _uiState.value.copy(
            seats = newSeats,
            selectedSeatId = selectedId
        )
    }
}
