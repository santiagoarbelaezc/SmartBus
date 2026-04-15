package com.smartbus.app.presentation.search

enum class RouteType { INTERCITY, URBAN }

data class SearchUiState(
    val origin: String = "",
    val destination: String = "",
    val selectedTab: RouteType = RouteType.INTERCITY,
    val intercityRoutes: List<IntercityRoute> = defaultIntercityRoutes,
    val urbanRoutes: List<UrbanRoute> = defaultUrbanRoutes
)

data class IntercityRoute(
    val destination: String,
    val origin: String = "Armenia",
    val duration: String,
    val durationMinutes: Int,
    val priceFrom: String,
    val companyName: String,
    val companyImageRes: Int,
    val departureTime: String,
    val isPopular: Boolean = false,
    val isDirect: Boolean = true,
    val seatsAvailable: Int = 24,
    val occupancy: Float = 0.4f,
    val tag: String? = null
)

data class UrbanRoute(
    val lineNumber: String,
    val lineName: String,
    val description: String,
    val busImageRes: Int,
    val logoRes: Int,
    val farePrice: String = "\$2.900",
    val frequency: String,
    val operatingHours: String,
    val color: Long
)

// ── Static data ───────────────────────────────────────────────────────────────

val defaultIntercityRoutes = listOf(
    IntercityRoute(
        destination      = "Medellín",
        duration         = "7 horas",
        durationMinutes  = 420,
        priceFrom        = "\$55.000",
        companyName      = "Bolivariano",
        companyImageRes  = com.smartbus.app.R.raw.busbolivariano,
        departureTime    = "06:00 AM",
        isPopular        = true,
        seatsAvailable   = 8,
        occupancy        = 0.75f,
        tag              = "Popular"
    ),
    IntercityRoute(
        destination      = "Medellín",
        duration         = "7 horas",
        durationMinutes  = 420,
        priceFrom        = "\$48.000",
        companyName      = "Flota Occidental",
        companyImageRes  = com.smartbus.app.R.raw.busflota,
        departureTime    = "08:30 AM",
        seatsAvailable   = 22,
        occupancy        = 0.3f
    ),
    IntercityRoute(
        destination      = "Bogotá",
        duration         = "8 horas",
        durationMinutes  = 480,
        priceFrom        = "\$65.000",
        companyName      = "Bolivariano",
        companyImageRes  = com.smartbus.app.R.raw.busbolivariano,
        departureTime    = "07:00 AM",
        isPopular        = true,
        seatsAvailable   = 15,
        occupancy        = 0.6f,
        tag              = "Ejecutivo"
    ),
    IntercityRoute(
        destination      = "Bogotá",
        duration         = "8 horas",
        durationMinutes  = 480,
        priceFrom        = "\$58.000",
        companyName      = "Flota Occidental",
        companyImageRes  = com.smartbus.app.R.raw.busflota,
        departureTime    = "10:00 PM",
        seatsAvailable   = 30,
        occupancy        = 0.2f,
        tag              = "Nocturno"
    ),
    IntercityRoute(
        destination      = "Cali",
        duration         = "3 horas",
        durationMinutes  = 180,
        priceFrom        = "\$30.000",
        companyName      = "Flota Occidental",
        companyImageRes  = com.smartbus.app.R.raw.busflota,
        departureTime    = "09:00 AM",
        isPopular        = true,
        seatsAvailable   = 20,
        occupancy        = 0.5f,
        tag              = "Directo"
    ),
    IntercityRoute(
        destination      = "Cali",
        duration         = "3 horas",
        durationMinutes  = 180,
        priceFrom        = "\$28.000",
        companyName      = "Bolivariano",
        companyImageRes  = com.smartbus.app.R.raw.busbolivariano,
        departureTime    = "02:00 PM",
        seatsAvailable   = 18,
        occupancy        = 0.4f
    ),
    IntercityRoute(
        destination      = "Circasia",
        duration         = "20 min",
        durationMinutes  = 20,
        priceFrom        = "\$4.500",
        companyName      = "Gacela",
        companyImageRes  = com.smartbus.app.R.raw.busgacela,
        departureTime    = "Cada 15 min",
        seatsAvailable   = 40,
        occupancy        = 0.15f,
        tag              = "Frecuente"
    ),
    IntercityRoute(
        destination      = "Filandia",
        duration         = "40 min",
        durationMinutes  = 40,
        priceFrom        = "\$6.000",
        companyName      = "Gacela",
        companyImageRes  = com.smartbus.app.R.raw.busgacela,
        departureTime    = "Cada 30 min",
        seatsAvailable   = 35,
        occupancy        = 0.2f,
        tag              = "Turismo"
    ),
    IntercityRoute(
        destination      = "Salento",
        duration         = "30 min",
        durationMinutes  = 30,
        priceFrom        = "\$5.000",
        companyName      = "Gacela",
        companyImageRes  = com.smartbus.app.R.raw.busgacela,
        departureTime    = "Cada 20 min",
        isPopular        = true,
        seatsAvailable   = 30,
        occupancy        = 0.65f,
        tag              = "⭐ Turístico"
    )
)

val defaultUrbanRoutes = listOf(
    UrbanRoute(
        lineNumber     = "36",
        lineName       = "Línea 36",
        description    = "Terminal – Centro – Estadio – El Bosque",
        busImageRes    = com.smartbus.app.R.raw.bustinto,
        logoRes        = com.smartbus.app.R.raw.logotinto,
        farePrice      = "\$2.900",
        frequency      = "Cada 8 min",
        operatingHours = "5:00 AM – 11:00 PM",
        color          = 0xFFE53935
    ),
    UrbanRoute(
        lineNumber     = "22N",
        lineName       = "Línea 22N (Nocturna)",
        description    = "Cementerio – La Glorieta – Centro – Uniandes",
        busImageRes    = com.smartbus.app.R.raw.bustinto,
        logoRes        = com.smartbus.app.R.raw.logotinto,
        farePrice      = "\$3.200",
        frequency      = "Cada 15 min",
        operatingHours = "10:00 PM – 4:00 AM",
        color          = 0xFF1565C0
    ),
    UrbanRoute(
        lineNumber     = "35",
        lineName       = "Línea 35",
        description    = "Barcelona – Centro – La Castellana – El Caimo",
        busImageRes    = com.smartbus.app.R.raw.bustinto,
        logoRes        = com.smartbus.app.R.raw.logotinto,
        farePrice      = "\$2.900",
        frequency      = "Cada 10 min",
        operatingHours = "5:30 AM – 10:30 PM",
        color          = 0xFF2E7D32
    ),
    UrbanRoute(
        lineNumber     = "28",
        lineName       = "Línea 28",
        description    = "Norte – Hospital – El Bosque – Centro",
        busImageRes    = com.smartbus.app.R.raw.bustinto,
        logoRes        = com.smartbus.app.R.raw.logotinto,
        farePrice      = "\$2.900",
        frequency      = "Cada 12 min",
        operatingHours = "5:00 AM – 10:30 PM",
        color          = 0xFF6A1B9A
    ),
    UrbanRoute(
        lineNumber     = "16",
        lineName       = "Línea 16",
        description    = "Sena – Hospital – Centro – Granada",
        busImageRes    = com.smartbus.app.R.raw.bustinto,
        logoRes        = com.smartbus.app.R.raw.logotinto,
        farePrice      = "\$2.900",
        frequency      = "Cada 10 min",
        operatingHours = "5:15 AM – 10:45 PM",
        color          = 0xFFEF6C00
    ),
    UrbanRoute(
        lineNumber     = "12",
        lineName       = "Línea 12",
        description    = "Puerto Espejo – Centro – UniQuindio",
        busImageRes    = com.smartbus.app.R.raw.bustinto,
        logoRes        = com.smartbus.app.R.raw.logotinto,
        farePrice      = "\$2.900",
        frequency      = "Cada 15 min",
        operatingHours = "5:30 AM – 10:00 PM",
        color          = 0xFF00838F
    ),
    UrbanRoute(
        lineNumber     = "19",
        lineName       = "Línea 19",
        description    = "Coliseo – Zuldemayda – Centro – Hospital",
        busImageRes    = com.smartbus.app.R.raw.bustinto,
        logoRes        = com.smartbus.app.R.raw.logotinto,
        farePrice      = "\$2.900",
        frequency      = "Cada 8 min",
        operatingHours = "5:00 AM – 11:00 PM",
        color          = 0xFFFF8F00
    )
)

// Legacy compat — kept so references in other screens still compile
data class TripResult(
    val departureTime: String,
    val duration: String,
    val price: String,
    val seatsAvailable: Int,
    val occupancy: Float,
    val companyName: String = "SmartBus",
    val companyImageRes: Int = com.smartbus.app.R.raw.smartbus
)
