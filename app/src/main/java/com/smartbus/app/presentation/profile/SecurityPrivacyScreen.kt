package com.smartbus.app.presentation.profile

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.smartbus.app.ui.theme.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SecurityPrivacyScreen(
    viewModel: ProfileViewModel,
    onNavigateBack: () -> Unit
) {
    val uiState by viewModel.uiState.collectAsState()
    var showPasswordSheet by remember { mutableStateOf(false) }

    if (showPasswordSheet) {
        ChangePasswordBottomSheet(
            onDismiss = { showPasswordSheet = false },
            onConfirm = { old, new -> viewModel.changePassword(old, new) }
        )
    }

    Scaffold(
        containerColor = Color(0xFFF5F6F8),
        topBar = {
            TopAppBar(
                title = { Text("Seguridad y Privacidad", fontWeight = FontWeight.Bold) },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Atrás")
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Black,
                    titleContentColor = White,
                    navigationIconContentColor = White
                )
            )
        }
    ) { innerPadding ->
        LazyColumn(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize(),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // --- SEGURIDAD DE ACCESO ---
            item { SectionHeader("Acceso y Seguridad") }
            item {
                SettingsCard {
                    SwitchRow(
                        icon = Icons.Default.Fingerprint,
                        title = "Biometría",
                        subtitle = "Usar huella o rostro para entrar",
                        checked = uiState.isBiometricEnabled,
                        onCheckedChange = { viewModel.toggleBiometric(it) }
                    )
                    SettingsDivider()
                    SwitchRow(
                        icon = Icons.Default.Security,
                        title = "Verificación en 2 pasos",
                        subtitle = "Añade una capa extra de protección",
                        checked = uiState.is2FAEnabled,
                        onCheckedChange = { viewModel.toggle2FA(it) }
                    )
                    SettingsDivider()
                    ClickableRow(
                        icon = Icons.Default.Lock,
                        title = "Cambiar Contraseña",
                        onClick = { showPasswordSheet = true }
                    )
                }
            }

            // --- SESIONES ACTIVAS ---
            item { SectionHeader("Sesiones Activas") }
            item {
                SettingsCard {
                    if (uiState.activeSessions.isEmpty()) {
                        Text(
                            "No hay sesiones activas",
                            modifier = Modifier.padding(16.dp),
                            color = Color.Gray,
                            fontSize = 14.sp
                        )
                    } else {
                        uiState.activeSessions.forEachIndexed { index, session ->
                            SessionRow(name = session)
                            if (index < uiState.activeSessions.size - 1) SettingsDivider()
                        }
                    }
                }
            }
            if (uiState.activeSessions.isNotEmpty()) {
                item {
                    TextButton(
                        onClick = { viewModel.terminateAllSessions() },
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text("Cerrar todas las sesiones", color = ErrorRed, fontWeight = FontWeight.Bold)
                    }
                }
            }

            // --- PRIVACIDAD ---
            item { SectionHeader("Privacidad de Datos") }
            item {
                SettingsCard {
                    SwitchRow(
                        icon = Icons.Default.LocationOn,
                        title = "Compartir Ubicación",
                        subtitle = "Mejora las sugerencias de rutas",
                        checked = uiState.shareLocationData,
                        onCheckedChange = { viewModel.toggleLocationData(it) }
                    )
                    SettingsDivider()
                    ClickableRow(
                        icon = Icons.Default.Download,
                        title = "Exportar mis datos",
                        onClick = { /* Simulated */ }
                    )
                }
            }

            // --- GESTION PELIGROSA ---
            item {
                Card(
                    onClick = { /* Simulated */ },
                    colors = CardDefaults.cardColors(containerColor = ErrorRed.copy(alpha = 0.05f)),
                    border = androidx.compose.foundation.BorderStroke(1.dp, ErrorRed.copy(alpha = 0.2f)),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(Icons.Default.DeleteForever, null, tint = ErrorRed)
                        Spacer(modifier = Modifier.width(12.dp))
                        Text("Eliminar mi cuenta", color = ErrorRed, fontWeight = FontWeight.Bold)
                    }
                }
            }
        }
    }
}

@Composable
private fun SectionHeader(title: String) {
    Text(
        text = title.uppercase(),
        style = MaterialTheme.typography.labelSmall,
        fontWeight = FontWeight.ExtraBold,
        color = Color.Gray,
        letterSpacing = 1.2.sp,
        modifier = Modifier.padding(start = 4.dp, bottom = 4.dp)
    )
}

@Composable
private fun SettingsCard(content: @Composable ColumnScope.() -> Unit) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = White),
        elevation = CardDefaults.cardElevation(2.dp),
        shape = RoundedCornerShape(16.dp),
        content = content
    )
}

@Composable
private fun SwitchRow(
    icon: ImageVector,
    title: String,
    subtitle: String,
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .size(36.dp)
                .background(Gold.copy(alpha = 0.1f), RoundedCornerShape(10.dp)),
            contentAlignment = Alignment.Center
        ) {
            Icon(icon, null, tint = Gold, modifier = Modifier.size(20.dp))
        }
        Spacer(modifier = Modifier.width(16.dp))
        Column(modifier = Modifier.weight(1f)) {
            Text(title, fontWeight = FontWeight.SemiBold, fontSize = 15.sp)
            if (subtitle.isNotEmpty()) {
                Text(subtitle, color = Color.Gray, fontSize = 12.sp)
            }
        }
        Switch(
            checked = checked,
            onCheckedChange = onCheckedChange,
            colors = SwitchDefaults.colors(checkedThumbColor = Gold, checkedTrackColor = Gold.copy(alpha = 0.5f))
        )
    }
}

@Composable
private fun ClickableRow(
    icon: ImageVector,
    title: String,
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() }
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .size(36.dp)
                .background(Charcoal.copy(alpha = 0.1f), RoundedCornerShape(10.dp)),
            contentAlignment = Alignment.Center
        ) {
            Icon(icon, null, tint = Charcoal, modifier = Modifier.size(20.dp))
        }
        Spacer(modifier = Modifier.width(16.dp))
        Text(title, fontWeight = FontWeight.SemiBold, fontSize = 15.sp, modifier = Modifier.weight(1f))
        Icon(Icons.Default.ChevronRight, null, tint = Color.LightGray)
    }
}

@Composable
private fun SessionRow(name: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(Icons.Default.PhoneAndroid, null, tint = Color.Gray, modifier = Modifier.size(24.dp))
        Spacer(modifier = Modifier.width(16.dp))
        Column {
            Text(name, fontWeight = FontWeight.Bold, fontSize = 14.sp)
            Text("Activa ahora", color = Gold, fontSize = 11.sp, fontWeight = FontWeight.Bold)
        }
    }
}

@Composable
private fun SettingsDivider() {
    HorizontalDivider(modifier = Modifier.padding(horizontal = 16.dp), color = Color(0xFFF0F0F0))
}
