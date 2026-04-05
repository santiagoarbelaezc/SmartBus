package com.smartbus.app.presentation.profile

data class ProfileUiState(
    val name: String = "Santiago Arbelaez",
    val email: String = "santiago@example.com",
    val phone: String = "+57 300 000 0000",
    val loyaltyLevel: String = "Oro",
    val settings: List<SettingItem> = listOf(
        SettingItem("Métodos de pago", "Administra tus tarjetas"),
        SettingItem("Notificaciones", "Alertas de viaje"),
        SettingItem("Ayuda y Soporte", "Centro de ayuda"),
        SettingItem("Términos y Condiciones", "Políticas de privacidad")
    )
)

data class SettingItem(
    val title: String,
    val description: String
)
