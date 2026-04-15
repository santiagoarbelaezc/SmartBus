package com.smartbus.app.presentation.profile

data class ProfileUiState(
    val name: String = "Santiago Arbelaez",
    val email: String = "santiago@example.com",
    val phone: String = "+57 300 000 0000",
    val loyaltyLevel: String = "Oro",
    val totalTrips: Int = 47,
    val totalPoints: Int = 12450,
    val currentLanguage: String = "Español",
    val settings: List<SettingItem> = listOf(
        SettingItem("language",     "Idioma / Language",        "Español",              "🌐"),
        SettingItem("payment",      "Métodos de pago",          "Administra tus tarjetas", "💳"),
        SettingItem("notifications","Notificaciones",           "Alertas de viaje",     "🔔"),
        SettingItem("credit",       "Crédito de viaje",         "Ver cupo disponible",  "🎁"),
        SettingItem("help",         "Ayuda y Soporte",          "Centro de ayuda",      "❓"),
        SettingItem("terms",        "Términos y Condiciones",   "Políticas de privacidad", "📄")
    )
)

data class SettingItem(
    val id: String,
    val title: String,
    val description: String,
    val emoji: String
)
