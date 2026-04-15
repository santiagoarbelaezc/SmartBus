package com.smartbus.app.presentation.notifications

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.smartbus.app.ui.theme.Black
import com.smartbus.app.ui.theme.Charcoal
import com.smartbus.app.ui.theme.Gold
import com.smartbus.app.ui.theme.White

private data class NotificationItem(
    val icon: ImageVector,
    val iconBg: Color,
    val title: String,
    val body: String,
    val time: String,
    val isUnread: Boolean = false
)

private val sampleNotifications = listOf(
    NotificationItem(Icons.Default.ConfirmationNumber, Color(0xFF1B5E20), "Tiquete confirmado", "Tu tiquete Medellín → Bogotá para el 15 Abr está listo.", "Hace 5 min", true),
    NotificationItem(Icons.Default.MyLocation, Color(0xFF0D47A1), "Bus en camino", "SmartBus #247 saldrá en 30 minutos. ¡Prepárate!", "Hace 28 min", true),
    NotificationItem(Icons.Default.Stars, Color(0xFFF57F17), "Puntos acumulados", "Ganaste 450 puntos por tu último viaje. Nivel Oro cerca.", "Hace 2h", false),
    NotificationItem(Icons.Default.LocalOffer, Color(0xFF6A1B9A), "Oferta especial 🎉", "20% de descuento en rutas nocturnas este fin de semana.", "Hace 5h", false),
    NotificationItem(Icons.Default.CreditCard, Color(0xFF37474F), "Pago procesado", "Pago de \$85.000 por tu tiquete fue procesado correctamente.", "Ayer", false),
    NotificationItem(Icons.Default.Info, Color(0xFF00695C), "Actualización de ruta", "La ruta Bogotá → Cali cambia de terminal el 20 de Abril.", "Hace 2 días", false),
    NotificationItem(Icons.Default.Star, Color(0xFFBF360C), "Califica tu viaje", "¿Cómo fue tu experiencia en el viaje del 10 de Abril?", "Hace 4 días", false),
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NotificationsScreen(onNavigateBack: () -> Unit) {
    var filterUnread by remember { mutableStateOf(false) }
    val displayed = if (filterUnread) sampleNotifications.filter { it.isUnread } else sampleNotifications
    val unreadCount = sampleNotifications.count { it.isUnread }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text("Notificaciones", fontWeight = FontWeight.Bold, color = White)
                        if (unreadCount > 0) {
                            Spacer(modifier = Modifier.width(8.dp))
                            Surface(color = Gold, shape = CircleShape) {
                                Text(
                                    unreadCount.toString(),
                                    color = Black,
                                    fontSize = 11.sp,
                                    fontWeight = FontWeight.ExtraBold,
                                    modifier = Modifier.padding(horizontal = 7.dp, vertical = 2.dp)
                                )
                            }
                        }
                    }
                },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(Icons.Default.ArrowBack, null, tint = White)
                    }
                },
                actions = {
                    TextButton(onClick = {}) {
                        Text("Leer todo", color = Gold, fontSize = 12.sp)
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = Black)
            )
        },
        containerColor = Color(0xFFF8F9FA)
    ) { padding ->
        LazyColumn(
            modifier = Modifier.padding(padding).fillMaxSize(),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            // Filter chip
            item {
                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    FilterChip(
                        selected = !filterUnread,
                        onClick = { filterUnread = false },
                        label = { Text("Todas") },
                        colors = FilterChipDefaults.filterChipColors(
                            selectedContainerColor = Black,
                            selectedLabelColor = Gold
                        )
                    )
                    FilterChip(
                        selected = filterUnread,
                        onClick = { filterUnread = true },
                        label = { Text("No leídas ($unreadCount)") },
                        colors = FilterChipDefaults.filterChipColors(
                            selectedContainerColor = Black,
                            selectedLabelColor = Gold
                        )
                    )
                }
                Spacer(modifier = Modifier.height(4.dp))
            }

            items(displayed.size) { i ->
                val notif = displayed[i]
                Card(
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = if (notif.isUnread) White else Color(0xFFF5F5F5)
                    ),
                    elevation = CardDefaults.cardElevation(if (notif.isUnread) 3.dp else 0.dp),
                    border = if (notif.isUnread)
                        androidx.compose.foundation.BorderStroke(1.dp, Gold.copy(alpha = 0.25f))
                    else null
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth().padding(16.dp),
                        verticalAlignment = Alignment.Top
                    ) {
                        Box(
                            modifier = Modifier
                                .size(46.dp)
                                .clip(RoundedCornerShape(14.dp))
                                .background(notif.iconBg.copy(alpha = 0.15f)),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(notif.icon, null, tint = notif.iconBg, modifier = Modifier.size(24.dp))
                        }
                        Spacer(modifier = Modifier.width(14.dp))
                        Column(modifier = Modifier.weight(1f)) {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Text(notif.title, fontWeight = FontWeight.Bold, fontSize = 14.sp, color = Black)
                                if (notif.isUnread) {
                                    Box(
                                        modifier = Modifier
                                            .size(8.dp)
                                            .clip(CircleShape)
                                            .background(Gold)
                                    )
                                }
                            }
                            Spacer(modifier = Modifier.height(3.dp))
                            Text(notif.body, fontSize = 12.sp, color = Color.Gray, lineHeight = 17.sp)
                            Spacer(modifier = Modifier.height(6.dp))
                            Text(notif.time, fontSize = 11.sp, color = Gold, fontWeight = FontWeight.Medium)
                        }
                    }
                }
            }
        }
    }
}
