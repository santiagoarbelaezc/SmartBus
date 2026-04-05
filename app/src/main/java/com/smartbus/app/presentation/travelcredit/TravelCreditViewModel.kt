package com.smartbus.app.presentation.travelcredit

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class TravelCreditViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(TravelCreditUiState())
    val uiState: StateFlow<TravelCreditUiState> = _uiState.asStateFlow()
}
