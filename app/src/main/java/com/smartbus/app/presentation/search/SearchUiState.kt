package com.smartbus.app.presentation.search

data class SearchUiState(
    val origin: String = "",
    val destination: String = "",
    val results: List<TripResult> = listOf(
        TripResult("08:00 AM", "9h 30m", "$110.000", 12, 0.4f),
        TripResult("12:30 PM", "9h 45m", "$125.000", 5, 0.8f),
        TripResult("09:00 PM", "10h 00m", "$105.000", 20, 0.2f)
    )
)

data class TripResult(
    val departureTime: String,
    val duration: String,
    val price: String,
    val seatsAvailable: Int,
    val occupancy: Float
)
