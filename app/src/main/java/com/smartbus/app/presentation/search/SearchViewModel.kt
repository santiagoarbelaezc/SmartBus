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

    fun onTabSelected(tab: RouteType) {
        _uiState.value = _uiState.value.copy(selectedTab = tab)
    }

    fun filteredIntercity(): List<IntercityRoute> {
        val dest = _uiState.value.destination.trim().lowercase()
        return if (dest.isBlank()) defaultIntercityRoutes
        else defaultIntercityRoutes.filter { it.destination.lowercase().contains(dest) }
    }

    fun filteredUrban(): List<UrbanRoute> {
        val q = _uiState.value.destination.trim().lowercase()
        return if (q.isBlank()) defaultUrbanRoutes
        else defaultUrbanRoutes.filter {
            it.lineNumber.lowercase().contains(q) ||
            it.lineName.lowercase().contains(q) ||
            it.description.lowercase().contains(q)
        }
    }
}
