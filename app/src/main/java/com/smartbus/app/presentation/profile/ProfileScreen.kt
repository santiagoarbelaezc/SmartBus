package com.smartbus.app.presentation.profile

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.HelpOutline
import androidx.compose.material.icons.automirrored.filled.Logout
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.smartbus.app.R
import com.smartbus.app.core.AppLanguage
import com.smartbus.app.core.LanguageManager
import com.smartbus.app.ui.components.LanguageSelectorBottomSheet
import com.smartbus.app.ui.theme.*
import com.smartbus.app.core.AppTheme
import com.smartbus.app.core.ThemeManager

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileScreen(
    viewModel: ProfileViewModel,
    onLogout: () -> Unit,
    onNavigateToTravelCredit: () -> Unit   = {},
    onNavigateToPaymentMethods: () -> Unit = {},
    onNavigateToNotifications: () -> Unit  = {},
    onNavigateToHelp: () -> Unit           = {},
    onNavigateToTerms: () -> Unit          = {}
) {
    val uiState by viewModel.uiState.collectAsState()
    val currentLang by LanguageManager.currentLanguage.collectAsState()
    val isEn = currentLang == AppLanguage.ENGLISH

    var showLanguageSheet by remember { mutableStateOf(false) }
    var showThemeSheet by remember { mutableStateOf(false) }
    val currentTheme by ThemeManager.currentTheme.collectAsState()

    if (showLanguageSheet) {
        LanguageSelectorBottomSheet(onDismiss = { showLanguageSheet = false })
    }
    if (showThemeSheet) {
        ThemeSelectorBottomSheet(onDismiss = { showThemeSheet = false })
    }

    Scaffold(
        containerColor = Color(0xFFF5F6F8),
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        if (isEn) "My Profile" else "Mi Perfil",
                        fontWeight = FontWeight.Bold,
                        color = White,
                        fontSize = 20.sp
                    )
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = Black)
            )
        }
    ) { innerPadding ->
        LazyColumn(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize(),
            contentPadding = PaddingValues(bottom = 48.dp)
        ) {

            // ── Hero Card ─────────────────────────────────────────────
            item {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(
                            Brush.verticalGradient(
                                listOf(Black, Color(0xFF1C1C1E))
                            )
                        )
                        .padding(top = 28.dp, bottom = 36.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        // Profile photo with gold ring
                        Box(
                            modifier = Modifier
                                .size(96.dp)
                                .clip(CircleShape)
                                .background(Brush.linearGradient(listOf(Gold, GoldDark)))
                                .padding(3.dp)
                                .clip(CircleShape)
                        ) {
                            val context = androidx.compose.ui.platform.LocalContext.current
                            val bmp = remember<androidx.compose.ui.graphics.ImageBitmap?> {
                                android.graphics.BitmapFactory.decodeStream(
                                    context.resources.openRawResource(R.raw.perfil)
                                )?.asImageBitmap()
                            }
                            if (bmp != null) {
                                androidx.compose.foundation.Image(
                                    bitmap = bmp,
                                    contentDescription = "Foto de perfil",
                                    contentScale = ContentScale.Crop,
                                    modifier = Modifier.fillMaxSize()
                                )
                            } else {
                                Box(
                                    modifier = Modifier.fillMaxSize().background(Charcoal),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Icon(Icons.Default.Person, null, tint = Gold, modifier = Modifier.size(52.dp))
                                }
                            }
                        }

                        Spacer(modifier = Modifier.height(14.dp))

                        Text(
                            uiState.name,
                            color = White,
                            fontWeight = FontWeight.ExtraBold,
                            fontSize = 22.sp
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            uiState.email,
                            color = White.copy(alpha = 0.55f),
                            fontSize = 13.sp
                        )
                        Spacer(modifier = Modifier.height(14.dp))

                        // Level badge
                        Surface(
                            color = Gold.copy(alpha = 0.18f),
                            shape = RoundedCornerShape(20.dp),
                            border = androidx.compose.foundation.BorderStroke(1.dp, Gold.copy(alpha = 0.4f))
                        ) {
                            Row(
                                modifier = Modifier.padding(horizontal = 14.dp, vertical = 6.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Icon(Icons.Default.Stars, null, tint = Gold, modifier = Modifier.size(15.dp))
                                Spacer(modifier = Modifier.width(6.dp))
                                Text(
                                    "${if (isEn) "Level" else "Nivel"} ${uiState.loyaltyLevel}",
                                    color = Gold,
                                    fontWeight = FontWeight.ExtraBold,
                                    fontSize = 13.sp
                                )
                            }
                        }
                    }
                }
            }

            // ── Stats row (overlapping cards) ────────────────────────
            item {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 20.dp)
                        .offset(y = (-20).dp),
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    ProfileStatCard(
                        modifier = Modifier.weight(1f),
                        value = uiState.totalTrips.toString(),
                        label = if (isEn) "Trips" else "Viajes",
                        icon = Icons.Default.DirectionsBus,
                        accentColor = Color(0xFF1B5E20)
                    )
                    ProfileStatCard(
                        modifier = Modifier.weight(1f),
                        value = "${uiState.totalPoints / 1000}K",
                        label = if (isEn) "Points" else "Puntos",
                        icon = Icons.Default.Stars,
                        accentColor = Color(0xFFF57F17)
                    )
                    ProfileStatCard(
                        modifier = Modifier.weight(1f),
                        value = uiState.loyaltyLevel,
                        label = if (isEn) "Level" else "Nivel",
                        icon = Icons.Default.MilitaryTech,
                        accentColor = Color(0xFF6A1B9A)
                    )
                }
            }

            // ── Section label: Account ────────────────────────────────
            item {
                ProfileSectionLabel(
                    title = if (isEn) "Account" else "Cuenta",
                    modifier = Modifier
                        .offset(y = (-10).dp)
                        .padding(horizontal = 20.dp)
                )
            }

            // ── Account settings card ─────────────────────────────────
            item {
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 20.dp)
                        .offset(y = (-6).dp),
                    shape = RoundedCornerShape(20.dp),
                    colors = CardDefaults.cardColors(containerColor = White),
                    elevation = CardDefaults.cardElevation(defaultElevation = 3.dp)
                ) {
                    Column {
                        ProfileMenuRow(
                            icon = Icons.Default.Language,
                            iconBg = Color(0xFF0D47A1),
                            title = if (isEn) "Language" else "Idioma",
                            subtitle = currentLang.displayName,
                            onClick = { showLanguageSheet = true }
                        )
                        ProfileDivider()
                        ProfileMenuRow(
                            icon = if (currentTheme == AppTheme.DARK) Icons.Default.DarkMode else Icons.Default.LightMode,
                            iconBg = if (currentTheme == AppTheme.DARK) Color(0xFF4527A0) else Color(0xFFFFB300),
                            title = if (isEn) "Appearance" else "Apariencia",
                            subtitle = if (isEn) currentTheme.displayNameEn else currentTheme.displayName,
                            onClick = { showThemeSheet = true }
                        )
                        ProfileDivider()
                        ProfileMenuRow(
                            icon = Icons.Default.CreditCard,
                            iconBg = Color(0xFF1B5E20),
                            title = if (isEn) "Payment Methods" else "Métodos de pago",
                            subtitle = if (isEn) "Manage your cards" else "Administra tus tarjetas",
                            onClick = onNavigateToPaymentMethods,
                            showBadge = false
                        )
                        ProfileDivider()
                        ProfileMenuRow(
                            icon = Icons.Default.Notifications,
                            iconBg = Color(0xFF6A1B9A),
                            title = if (isEn) "Notifications" else "Notificaciones",
                            subtitle = if (isEn) "Trip alerts" else "Alertas de viaje",
                            onClick = onNavigateToNotifications,
                            badgeCount = 2
                        )
                    }
                }
                Spacer(modifier = Modifier.height(16.dp))
            }

            // ── Section label: Support ────────────────────────────────
            item {
                ProfileSectionLabel(
                    title = if (isEn) "Support" else "Soporte",
                    modifier = Modifier.padding(horizontal = 20.dp)
                )
                Spacer(modifier = Modifier.height(8.dp))
            }

            // ── Support settings card ─────────────────────────────────
            item {
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 20.dp),
                    shape = RoundedCornerShape(20.dp),
                    colors = CardDefaults.cardColors(containerColor = White),
                    elevation = CardDefaults.cardElevation(defaultElevation = 3.dp)
                ) {
                    Column {
                        ProfileMenuRow(
                            icon = Icons.Default.AccountBalanceWallet,
                            iconBg = Color(0xFFE65100),
                            title = if (isEn) "Travel Credit" else "Crédito de viaje",
                            subtitle = if (isEn) "View available credit" else "Ver cupo disponible",
                            onClick = onNavigateToTravelCredit
                        )
                        ProfileDivider()
                        ProfileMenuRow(
                            icon = Icons.AutoMirrored.Filled.HelpOutline,
                            iconBg = Color(0xFF006064),
                            title = if (isEn) "Help & Support" else "Ayuda y Soporte",
                            subtitle = if (isEn) "Help center" else "Centro de ayuda",
                            onClick = onNavigateToHelp
                        )
                        ProfileDivider()
                        ProfileMenuRow(
                            icon = Icons.Default.Description,
                            iconBg = Color(0xFF37474F),
                            title = if (isEn) "Terms & Conditions" else "Términos y Condiciones",
                            subtitle = if (isEn) "Privacy policies" else "Políticas de privacidad",
                            onClick = onNavigateToTerms
                        )
                    }
                }
                Spacer(modifier = Modifier.height(28.dp))
            }

            // ── Logout ────────────────────────────────────────────────
            item {
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 20.dp)
                        .clickable { onLogout() },
                    shape = RoundedCornerShape(20.dp),
                    colors = CardDefaults.cardColors(containerColor = ErrorRed.copy(alpha = 0.07f)),
                    border = androidx.compose.foundation.BorderStroke(1.dp, ErrorRed.copy(alpha = 0.25f)),
                    elevation = CardDefaults.cardElevation(0.dp)
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 20.dp, vertical = 18.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center
                    ) {
                        Icon(Icons.AutoMirrored.Filled.Logout, null, tint = ErrorRed, modifier = Modifier.size(20.dp))
                        Spacer(modifier = Modifier.width(10.dp))
                        Text(
                            if (isEn) "Sign out" else "Cerrar sesión",
                            color = ErrorRed,
                            fontWeight = FontWeight.Bold,
                            fontSize = 15.sp
                        )
                    }
                }
            }

            // ── App version ───────────────────────────────────────────
            item {
                Spacer(modifier = Modifier.height(20.dp))
                Text(
                    "SmartBus v1.0.0",
                    modifier = Modifier.fillMaxWidth(),
                    color = Color.Gray,
                    fontSize = 11.sp,
                    fontWeight = FontWeight.Medium,
                    textAlign = androidx.compose.ui.text.style.TextAlign.Center
                )
            }
        }
    }
}

// ── Helpers ───────────────────────────────────────────────────────────────────

@Composable
private fun ProfileSectionLabel(title: String, modifier: Modifier = Modifier) {
    Text(
        text = title.uppercase(),
        style = MaterialTheme.typography.labelSmall,
        fontWeight = FontWeight.ExtraBold,
        color = Color(0xFF9E9E9E),
        letterSpacing = 1.2.sp,
        modifier = modifier
    )
}

@Composable
private fun ProfileDivider() {
    HorizontalDivider(
        modifier = Modifier.padding(start = 76.dp, end = 20.dp),
        color = Color(0xFFF0F0F0),
        thickness = 1.dp
    )
}

@Composable
private fun ProfileMenuRow(
    icon: ImageVector,
    iconBg: Color,
    title: String,
    subtitle: String,
    onClick: () -> Unit,
    badgeCount: Int = 0,
    showBadge: Boolean = badgeCount > 0
) {
    var pressed by remember { mutableStateOf(false) }
    val scale by animateFloatAsState(
        targetValue = if (pressed) 0.97f else 1f,
        animationSpec = tween(100),
        label = "row_scale"
    )

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .scale(scale)
            .clickable {
                pressed = true
                onClick()
            }
            .padding(horizontal = 20.dp, vertical = 14.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Colored icon box
        Box(
            modifier = Modifier
                .size(42.dp)
                .clip(RoundedCornerShape(13.dp))
                .background(iconBg),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                icon,
                contentDescription = null,
                tint = White,
                modifier = Modifier.size(22.dp)
            )
        }

        Spacer(modifier = Modifier.width(16.dp))

        Column(modifier = Modifier.weight(1f)) {
            Text(
                title,
                fontWeight = FontWeight.SemiBold,
                fontSize = 15.sp,
                color = Color(0xFF1A1A1A)
            )
            Text(
                subtitle,
                fontSize = 12.sp,
                color = Color(0xFF9E9E9E)
            )
        }

        if (showBadge && badgeCount > 0) {
            Surface(
                color = Gold,
                shape = CircleShape
            ) {
                Text(
                    badgeCount.toString(),
                    color = Black,
                    fontSize = 10.sp,
                    fontWeight = FontWeight.ExtraBold,
                    modifier = Modifier.padding(horizontal = 7.dp, vertical = 2.dp)
                )
            }
            Spacer(modifier = Modifier.width(8.dp))
        }

        Icon(
            Icons.Default.ChevronRight,
            null,
            tint = Color(0xFFDDDDDD),
            modifier = Modifier.size(20.dp)
        )
    }
}

@Composable
private fun ProfileStatCard(
    modifier: Modifier,
    value: String,
    label: String,
    icon: ImageVector,
    accentColor: Color
) {
    Card(
        modifier = modifier,
        shape = RoundedCornerShape(18.dp),
        colors = CardDefaults.cardColors(containerColor = White),
        elevation = CardDefaults.cardElevation(6.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Box(
                modifier = Modifier
                    .size(38.dp)
                    .clip(RoundedCornerShape(12.dp))
                    .background(accentColor.copy(alpha = 0.13f)),
                contentAlignment = Alignment.Center
            ) {
                Icon(icon, null, tint = accentColor, modifier = Modifier.size(20.dp))
            }
            Spacer(modifier = Modifier.height(8.dp))
            Text(value, fontWeight = FontWeight.Black, fontSize = 18.sp, color = Color(0xFF1A1A1A))
            Text(label, fontSize = 11.sp, color = Color(0xFF9E9E9E), fontWeight = FontWeight.Medium)
        }
    }
}
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ThemeSelectorBottomSheet(onDismiss: () -> Unit) {
    val currentTheme by ThemeManager.currentTheme.collectAsState()
    val currentLang by LanguageManager.currentLanguage.collectAsState()
    val isEn = currentLang == AppLanguage.ENGLISH
    val sheetState = rememberModalBottomSheetState()
    val scope = rememberCoroutineScope()

    ModalBottomSheet(
        onDismissRequest = onDismiss,
        sheetState = sheetState,
        containerColor = MaterialTheme.colorScheme.surface,
        dragHandle = { BottomSheetDefaults.DragHandle(color = Gold.copy(alpha = 0.3f)) }
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 32.dp)
        ) {
            Text(
                if (isEn) "Select Theme" else "Seleccionar Tema",
                modifier = Modifier.padding(20.dp),
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold
            )

            AppTheme.values().forEach { theme ->
                val isSelected = theme == currentTheme
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable {
                            ThemeManager.setTheme(theme)
                            onDismiss()
                        }
                        .padding(horizontal = 20.dp, vertical = 14.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Surface(
                            shape = CircleShape,
                            color = if (isSelected) Gold.copy(alpha = 0.15f) else Color.Transparent,
                            modifier = Modifier.size(42.dp)
                        ) {
                            Box(contentAlignment = Alignment.Center) {
                                Text(theme.emoji, fontSize = 20.sp)
                            }
                        }
                        Spacer(modifier = Modifier.width(16.dp))
                        Text(
                            if (isEn) theme.displayNameEn else theme.displayName,
                            style = MaterialTheme.typography.bodyLarge,
                            fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium
                        )
                    }
                    if (isSelected) {
                        RadioButton(selected = true, onClick = null, colors = RadioButtonDefaults.colors(selectedColor = Gold))
                    }
                }
            }
        }
    }
}
