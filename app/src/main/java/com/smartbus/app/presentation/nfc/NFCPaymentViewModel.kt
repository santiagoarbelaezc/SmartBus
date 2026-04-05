package com.smartbus.app.presentation.nfc

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class NFCPaymentViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(NfcPaymentUiState())
    val uiState: StateFlow<NfcPaymentUiState> = _uiState.asStateFlow()

    fun onPointsToggle(use: Boolean) {
        _uiState.update { it.copy(usePoints = use) }
    }

    fun startReading() {
        viewModelScope.launch {
            _uiState.update { it.copy(status = NfcStatus.Reading) }
            delay(2000)
            _uiState.update { it.copy(status = NfcStatus.Success) }
            delay(3000)
            _uiState.update { it.copy(status = NfcStatus.Idle) }
        }
    }
}
