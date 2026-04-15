package com.smartbus.app.presentation.home

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class HomeViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(HomeUiState())
    val uiState: StateFlow<HomeUiState> = _uiState.asStateFlow()

    fun onFilterSelected(filter: RouteFilter) {
        _uiState.value = _uiState.value.copy(selectedFilter = filter)
    }

    fun filteredRoutes(): List<RouteItem> {
        val all = _uiState.value.popularRoutes
        return when (_uiState.value.selectedFilter) {
            RouteFilter.ALL -> all
            RouteFilter.CHEAP -> all.sortedBy { it.price }
            RouteFilter.NIGHT -> all.filter { it.isNight }
            RouteFilter.DIRECT -> all.filter { it.isDirect }
        }
    }
}
