package com.smartbus.app.presentation.search

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class SearchViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(SearchUiState())
    val uiState: StateFlow<SearchUiState> = _uiState.asStateFlow()

    fun onOriginChange(value: String) {
        _uiState.value = _uiState.value.copy(origin = value)
    }

    fun onDestinationChange(value: String) {
        _uiState.value = _uiState.value.copy(destination = value)
    }
}
