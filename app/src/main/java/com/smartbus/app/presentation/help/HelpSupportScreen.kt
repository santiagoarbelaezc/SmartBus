package com.smartbus.app.presentation.help

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
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
import com.smartbus.app.ui.theme.Gold
import com.smartbus.app.ui.theme.White

private data class FaqItem(val question: String, val answer: String)
private data class ContactOption(val icon: ImageVector, val label: String, val sub: String, val color: Color)

private val faqs = listOf(
    FaqItem("¿Cómo cancelo mi tiquete?", "Puedes cancelar hasta 2 horas antes de la salida desde 'Mis Tiquetes' → menú de opciones. Se aplica política de reembolso según la empresa."),
    FaqItem("¿Cómo funciona el crédito SmartBus?", "El crédito se acumula con cada viaje según tu nivel de fidelidad. Puedes usarlo en tu próxima compra desde 'Crédito de Viaje'."),
    FaqItem("¿Qué pasa si pierdo mi bus?", "Contacta a la empresa directamente. Algunas permiten cambio de turno por único costo. SmartBus no administra cambios de turno."),
    FaqItem("¿Cómo agrego una tarjeta de crédito?", "Ve a Perfil → Métodos de Pago → toca el botón + en la esquina inferior derecha."),
    FaqItem("¿El NFC funciona en todos los buses?", "Actualmente disponible en rutas Bolivariano y Gacela. Se está expandiendo a más empresas en 2026."),
)

private val contacts = listOf(
    ContactOption(Icons.Default.Chat,  "Chat en vivo",    "Respuesta en < 5 min",  Color(0xFF1B5E20)),
    ContactOption(Icons.Default.Email, "Correo",          "soporte@smartbus.co",    Color(0xFF0D47A1)),
    ContactOption(Icons.Default.Phone, "Línea de ayuda",  "01 8000 123 456",        Color(0xFF4A148C)),
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HelpSupportScreen(onNavigateBack: () -> Unit) {
    var expandedFaq by remember { mutableIntStateOf(-1) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Ayuda y Soporte", fontWeight = FontWeight.Bold, color = White) },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(Icons.Default.ArrowBack, null, tint = White)
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = Black)
            )
        },
        containerColor = Color(0xFFF8F9FA)
    ) { padding ->
        LazyColumn(
            modifier = Modifier.padding(padding).fillMaxSize(),
            contentPadding = PaddingValues(20.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            // Header card
            item {
                Card(
                    shape = RoundedCornerShape(20.dp),
                    colors = CardDefaults.cardColors(containerColor = Black),
                    elevation = CardDefaults.cardElevation(6.dp)
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth().padding(20.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        Text("🤝", fontSize = 40.sp)
                        Column {
                            Text("Estamos aquí para ayudarte", color = White, fontWeight = FontWeight.ExtraBold, fontSize = 16.sp)
                            Text("Soporte disponible 24/7", color = Gold, fontSize = 13.sp)
                        }
                    }
                }
                Spacer(modifier = Modifier.height(8.dp))
            }

            // Contact options
            item {
                Text("Contáctanos", fontWeight = FontWeight.ExtraBold, fontSize = 16.sp, color = Black)
                Spacer(modifier = Modifier.height(8.dp))
                Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                    contacts.forEach { opt ->
                        Card(
                            modifier = Modifier.weight(1f).clickable { },
                            shape = RoundedCornerShape(14.dp),
                            colors = CardDefaults.cardColors(containerColor = White),
                            elevation = CardDefaults.cardElevation(2.dp)
                        ) {
                            Column(
                                modifier = Modifier.fillMaxWidth().padding(12.dp),
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                Box(
                                    modifier = Modifier
                                        .size(44.dp)
                                        .clip(RoundedCornerShape(12.dp))
                                        .background(opt.color.copy(alpha = 0.12f)),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Icon(opt.icon, null, tint = opt.color, modifier = Modifier.size(22.dp))
                                }
                                Spacer(modifier = Modifier.height(8.dp))
                                Text(opt.label, fontWeight = FontWeight.SemiBold, fontSize = 12.sp, color = Black)
                                Text(opt.sub, fontSize = 10.sp, color = Color.Gray)
                            }
                        }
                    }
                }
                Spacer(modifier = Modifier.height(8.dp))
            }

            // FAQ header
            item {
                Text("Preguntas Frecuentes", fontWeight = FontWeight.ExtraBold, fontSize = 16.sp, color = Black)
            }

            // FAQ accordion
            items(faqs.size) { i ->
                val faq = faqs[i]
                val isOpen = expandedFaq == i
                Card(
                    shape = RoundedCornerShape(14.dp),
                    colors = CardDefaults.cardColors(containerColor = White),
                    elevation = CardDefaults.cardElevation(if (isOpen) 4.dp else 1.dp),
                    border = if (isOpen) androidx.compose.foundation.BorderStroke(1.dp, Gold.copy(alpha = 0.4f)) else null
                ) {
                    Column(modifier = Modifier.clickable { expandedFaq = if (isOpen) -1 else i }) {
                        Row(
                            modifier = Modifier.fillMaxWidth().padding(16.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(faq.question, fontWeight = FontWeight.SemiBold, fontSize = 14.sp, color = Black, modifier = Modifier.weight(1f))
                            Icon(
                                if (isOpen) Icons.Default.KeyboardArrowUp else Icons.Default.KeyboardArrowDown,
                                null, tint = Gold, modifier = Modifier.size(20.dp)
                            )
                        }
                        if (isOpen) {
                            HorizontalDivider(color = Color(0xFFF0F0F0))
                            Text(
                                faq.answer,
                                modifier = Modifier.padding(16.dp),
                                fontSize = 13.sp,
                                color = Color.Gray,
                                lineHeight = 19.sp
                            )
                        }
                    }
                }
            }
        }
    }
}
