package com.smartbus.app.domain.model

import androidx.compose.ui.graphics.Color

data class SavedCard(
    val last4: String,
    val brand: String,
    val holder: String,
    val expiry: String,
    val isDefault: Boolean = false,
    val gradientStart: Color = Color(0xFF1A1A2E),
    val gradientEnd: Color = Color(0xFF16213E)
)

val defaultSavedCards = listOf(
    SavedCard("4521", "Visa", "SANTIAGO ARBELAEZ", "12/27", true, Color(0xFF0D1B2A), Color(0xFF1B263B)),
    SavedCard("8834", "Mastercard", "SANTIAGO ARBELAEZ", "08/26", false, Color(0xFF1A0533), Color(0xFF2D0A5B)),
)
