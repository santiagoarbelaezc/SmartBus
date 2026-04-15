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

    fun onFilterSelected(filter: SearchFilter) {
        _uiState.value = _uiState.value.copy(selectedFilter = filter)
    }

    fun filteredIntercity(): List<IntercityRoute> {
        val dest = _uiState.value.destination.trim().lowercase()
        val filter = _uiState.value.selectedFilter
        
        var list = if (dest.isBlank()) defaultIntercityRoutes
        else defaultIntercityRoutes.filter { it.destination.lowercase().contains(dest) }

        // Apply specific filters
        list = when (filter) {
            SearchFilter.ALL -> list
            SearchFilter.CHEAPEST -> list.sortedBy { parsePrice(it.priceFrom) }
            SearchFilter.FASTEST -> list.sortedBy { it.durationMinutes }
            SearchFilter.DIRECT -> list.filter { it.isDirect }
            SearchFilter.NIGHT -> list.filter { 
                val hour = it.departureTime.split(":")[0].toIntOrNull() ?: 0
                val isPM = it.departureTime.contains("PM", ignoreCase = true)
                (isPM && hour >= 6 && hour != 12) || (!isPM && hour == 12) // Simple PM check
            }
        }
        
        return list
    }

    fun filteredUrban(): List<UrbanRoute> {
        val q = _uiState.value.destination.trim().lowercase()
        val filter = _uiState.value.selectedFilter

        var list = if (q.isBlank()) defaultUrbanRoutes
        else defaultUrbanRoutes.filter {
            it.lineNumber.lowercase().contains(q) ||
            it.lineName.lowercase().contains(q) ||
            it.description.lowercase().contains(q)
        }

        // Apply filters to Urban if relevant
        list = when (filter) {
            SearchFilter.ALL -> list
            SearchFilter.CHEAPEST -> list.sortedBy { parsePrice(it.farePrice) }
            SearchFilter.NIGHT -> list.filter { it.lineName.contains("Nocturna", ignoreCase = true) }
            else -> list // Other filters might not apply well to urban yet
        }

        return list
    }

    private fun parsePrice(price: String): Int {
        return price.replace("$", "").replace(".", "").trim().toIntOrNull() ?: 0
    }
}
