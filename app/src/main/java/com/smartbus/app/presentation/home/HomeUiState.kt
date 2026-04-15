package com.smartbus.app.presentation.home

enum class RouteFilter(val label: String, val labelEn: String) {
    ALL("Todos", "All"),
    CHEAP("Económico", "Budget"),
    NIGHT("Nocturno", "Night"),
    DIRECT("Directo", "Direct")
}

data class HomeUiState(
    val userName: String = "Santiago",
    val currentTrip: TripInfo? = TripInfo(
        origin = "Armenia",
        destination = "Filandia",
        date = "15 Abr, 2026",
        time = "Ahora"
    ),
    val nextTrip: TripInfo? = TripInfo(
        origin = "Medellín",
        destination = "Bogotá",
        date = "15 Abr, 2026",
        time = "08:30 AM"
    ),
    val promotions: List<PromoItem> = listOf(
        PromoItem("Lujo a tu alcance 🚌", "Viaja con 20% desc. en rutas Premier este fin de semana"),
        PromoItem("Tu fidelidad premia ⭐", "Doble puntos en todos los viajes este fin de semana"),
        PromoItem("Viaja ahora, paga después 💳", "Usa tu crédito SmartBus y paga en 30 días sin intereses")
    ),
    val popularRoutes: List<RouteItem> = listOf(
        RouteItem("Medellín", "Bogotá", "08:30", "$85.000", "5h 30min", "Bolivariano", 92, false, false),
        RouteItem("Bogotá", "Cali", "07:00", "$72.000", "7h 00min", "Flota Magdalena", 85, false, true),
        RouteItem("Medellín", "Cartagena", "21:00", "$110.000", "13h 00min", "Gacela", 78, true, true),
        RouteItem("Bogotá", "Bucaramanga", "06:30", "$65.000", "6h 30min", "Copetran", 88, false, true),
        RouteItem("Cali", "Medellín", "09:00", "$75.000", "8h 00min", "Bolivariano", 95, false, false),
        RouteItem("Pereira", "Bogotá", "05:00", "$58.000", "8h 30min", "Expreso Palmira", 80, false, false),
        RouteItem("Barranquilla", "Cartagena", "10:00", "$28.000", "2h 00min", "Unitransco", 76, false, true),
        RouteItem("Manizales", "Medellín", "07:30", "$35.000", "3h 30min", "Flota Ospina", 91, false, false)
    ),
    val selectedFilter: RouteFilter = RouteFilter.ALL
)

data class TripInfo(
    val origin: String,
    val destination: String,
    val date: String,
    val time: String
)

data class PromoItem(
    val title: String,
    val description: String
)

data class RouteItem(
    val origin: String,
    val destination: String,
    val departureTime: String,
    val price: String,
    val duration: String,
    val company: String,
    val occupancy: Int,
    val isNight: Boolean,
    val isDirect: Boolean
)
