package com.smartbus.app.presentation.payment

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class PaymentViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(PaymentUiState())
    val uiState: StateFlow<PaymentUiState> = _uiState.asStateFlow()

    fun onCardNumberChange(value: String) { _uiState.value = _uiState.value.copy(selectedCardNumber = value) }
    fun onCardNameChange(value: String) { _uiState.value = _uiState.value.copy(cardName = value) }
    fun onExpiryChange(value: String) { _uiState.value = _uiState.value.copy(expiry = value) }
    fun onCvcChange(value: String) { _uiState.value = _uiState.value.copy(cvc = value) }
    fun onCardSelect(index: Int) { _uiState.value = _uiState.value.copy(selectedCardIndex = index) }
}
