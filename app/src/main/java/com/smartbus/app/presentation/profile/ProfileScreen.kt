package com.smartbus.app.presentation.profile

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.smartbus.app.core.AppLanguage
import com.smartbus.app.core.LanguageManager
import com.smartbus.app.ui.components.LanguageSelectorBottomSheet
import com.smartbus.app.ui.components.PointsBadge
import com.smartbus.app.ui.theme.Black
import com.smartbus.app.ui.theme.ErrorRed
import com.smartbus.app.ui.theme.Gold
import com.smartbus.app.ui.theme.White

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileScreen(
    viewModel: ProfileViewModel,
    onLogout: () -> Unit,
    onNavigateToTravelCredit: () -> Unit = {}
) {
    val uiState by viewModel.uiState.collectAsState()
    val currentLang by LanguageManager.currentLanguage.collectAsState()
    val isEn = currentLang == AppLanguage.ENGLISH

    var showLanguageSheet by remember { mutableStateOf(false) }

    if (showLanguageSheet) {
        LanguageSelectorBottomSheet(onDismiss = { showLanguageSheet = false })
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        if (isEn) "My Profile" else "Mi Perfil",
                        style = MaterialTheme.typography.headlineMedium
                    )
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = Color.Transparent)
            )
        }
    ) { innerPadding ->
        LazyColumn(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize(),
            contentPadding = PaddingValues(bottom = 32.dp)
        ) {
            // ── Header con avatar y stats ────────────────────────────
            item {
                // Fondo degradado superior
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(
                            Brush.verticalGradient(
                                colors = listOf(Black.copy(alpha = 0.05f), Color.Transparent)
                            )
                        )
                        .padding(24.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        // Avatar con borde dorado
                        Box(
                            modifier = Modifier
                                .size(100.dp)
                                .clip(CircleShape)
                                .background(Gold)
                                .padding(3.dp)
                                .clip(CircleShape)
                                .background(White),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                Icons.Default.Person,
                                contentDescription = null,
                                tint = Black,
                                modifier = Modifier.size(60.dp)
                            )
                        }

                        Spacer(modifier = Modifier.height(12.dp))
                        Text(uiState.name, style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.Bold)
                        Text(uiState.email, style = MaterialTheme.typography.bodySmall, color = Color.Gray)
                        Spacer(modifier = Modifier.height(8.dp))
                        PointsBadge(level = uiState.loyaltyLevel)
                    }
                }
            }

            // ── Stats (Viajes / Puntos / Nivel) ─────────────────────
            item {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 24.dp),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    StatCard(
                        modifier = Modifier.weight(1f),
                        value = uiState.totalTrips.toString(),
                        label = if (isEn) "Trips" else "Viajes",
                        emoji = "🚌"
                    )
                    StatCard(
                        modifier = Modifier.weight(1f),
                        value = "${uiState.totalPoints / 1000}K",
                        label = if (isEn) "Points" else "Puntos",
                        emoji = "⭐"
                    )
                    StatCard(
                        modifier = Modifier.weight(1f),
                        value = uiState.loyaltyLevel,
                        label = if (isEn) "Level" else "Nivel",
                        emoji = "🏅"
                    )
                }
                Spacer(modifier = Modifier.height(32.dp))
            }

            // ── Sección de Configuración ─────────────────────────────
            item {
                Text(
                    if (isEn) "Settings" else "Configuración",
                    style = MaterialTheme.typography.labelLarge,
                    modifier = Modifier.padding(horizontal = 24.dp, vertical = 4.dp)
                )
                Spacer(modifier = Modifier.height(8.dp))
            }

            items(uiState.settings) { item ->
                SettingTile(
                    item = item,
                    isEn = isEn,
                    subtitle = when (item.id) {
                        "language" -> if (isEn) currentLang.displayName else currentLang.displayName
                        else -> if (isEn) translateDescription(item.description) else item.description
                    },
                    onClick = {
                        when (item.id) {
                            "language" -> showLanguageSheet = true
                            "credit" -> onNavigateToTravelCredit()
                            else -> { /* Pantallas futuras */ }
                        }
                    }
                )
                HorizontalDivider(
                    modifier = Modifier.padding(horizontal = 24.dp),
                    color = Color(0xFFF0F0F0)
                )
            }

            // ── Cerrar Sesión ────────────────────────────────────────
            item {
                Spacer(modifier = Modifier.height(32.dp))

                OutlinedButton(
                    onClick = onLogout,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 24.dp)
                        .height(50.dp),
                    shape = RoundedCornerShape(12.dp),
                    colors = ButtonDefaults.outlinedButtonColors(contentColor = ErrorRed),
                    border = androidx.compose.foundation.BorderStroke(1.dp, ErrorRed.copy(alpha = 0.5f))
                ) {
                    Icon(Icons.Default.Logout, contentDescription = null, modifier = Modifier.size(18.dp))
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        if (isEn) "Sign out" else "Cerrar sesión",
                        fontWeight = FontWeight.SemiBold
                    )
                }
            }
        }
    }
}

@Composable
private fun StatCard(modifier: Modifier = Modifier, value: String, label: String, emoji: String) {
    Card(
        modifier = modifier,
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFFF8F8F8)),
        elevation = CardDefaults.cardElevation(defaultElevation = 0.dp),
        border = androidx.compose.foundation.BorderStroke(1.dp, Color(0xFFEEEEEE))
    ) {
        Column(
            modifier = Modifier.padding(vertical = 16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(emoji, fontSize = 22.sp)
            Spacer(modifier = Modifier.height(4.dp))
            Text(value, fontWeight = FontWeight.Black, fontSize = 20.sp, color = Black)
            Text(label, fontSize = 11.sp, color = Color.Gray)
        }
    }
}

@Composable
private fun SettingTile(
    item: SettingItem,
    isEn: Boolean,
    subtitle: String,
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() }
            .padding(horizontal = 24.dp, vertical = 16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Icono emoji en caja
        Box(
            modifier = Modifier
                .size(42.dp)
                .clip(RoundedCornerShape(12.dp))
                .background(Gold.copy(alpha = 0.1f)),
            contentAlignment = Alignment.Center
        ) {
            Text(item.emoji, fontSize = 20.sp)
        }

        Spacer(modifier = Modifier.width(16.dp))

        Column(modifier = Modifier.weight(1f)) {
            Text(
                if (isEn) translateTitle(item.title) else item.title,
                fontWeight = FontWeight.SemiBold,
                fontSize = 15.sp,
                color = Black
            )
            Text(subtitle, fontSize = 12.sp, color = Color.Gray)
        }

        Icon(Icons.Default.ChevronRight, contentDescription = null, tint = Color(0xFFCCCCCC), modifier = Modifier.size(20.dp))
    }
}

// Traducciones simples para EN
private fun translateTitle(title: String) = when (title) {
    "Idioma / Language"     -> "Language"
    "Métodos de pago"       -> "Payment Methods"
    "Notificaciones"        -> "Notifications"
    "Crédito de viaje"      -> "Travel Credit"
    "Ayuda y Soporte"       -> "Help & Support"
    "Términos y Condiciones"-> "Terms & Conditions"
    else -> title
}

private fun translateDescription(desc: String) = when (desc) {
    "Administra tus tarjetas" -> "Manage your cards"
    "Alertas de viaje"        -> "Trip alerts"
    "Ver cupo disponible"     -> "View available credit"
    "Centro de ayuda"         -> "Help center"
    "Políticas de privacidad" -> "Privacy policies"
    else -> desc
}
