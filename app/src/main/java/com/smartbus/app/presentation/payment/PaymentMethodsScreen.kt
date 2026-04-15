package com.smartbus.app.presentation.payment

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.smartbus.app.ui.theme.Black
import com.smartbus.app.ui.theme.Charcoal
import com.smartbus.app.ui.theme.Gold
import com.smartbus.app.ui.theme.GoldDark
import com.smartbus.app.ui.theme.White

private data class SavedCard(
    val last4: String,
    val brand: String,
    val holder: String,
    val expiry: String,
    val isDefault: Boolean = false,
    val gradientStart: Color = Color(0xFF1A1A2E),
    val gradientEnd: Color = Color(0xFF16213E)
)

private val savedCards = listOf(
    SavedCard("4521", "Visa", "SANTIAGO ARBELAEZ", "12/27", true, Color(0xFF0D1B2A), Color(0xFF1B263B)),
    SavedCard("8834", "Mastercard", "SANTIAGO ARBELAEZ", "08/26", false, Color(0xFF1A0533), Color(0xFF2D0A5B)),
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PaymentMethodsScreen(onNavigateBack: () -> Unit) {
    var showAddSheet by remember { mutableStateOf(false) }

    if (showAddSheet) {
        AddCardSheet(onDismiss = { showAddSheet = false })
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Métodos de Pago", fontWeight = FontWeight.Bold, color = White) },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(Icons.Default.ArrowBack, null, tint = White)
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = Black)
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = { showAddSheet = true },
                containerColor = Gold,
                contentColor = Black,
                shape = CircleShape
            ) {
                Icon(Icons.Default.Add, null)
            }
        },
        containerColor = Color(0xFFF8F9FA)
    ) { padding ->
        LazyColumn(
            modifier = Modifier.padding(padding).fillMaxSize(),
            contentPadding = PaddingValues(20.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            item {
                Text(
                    "Tus tarjetas guardadas",
                    fontWeight = FontWeight.ExtraBold,
                    fontSize = 18.sp,
                    color = Black
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text("Toca una tarjeta para gestionar", fontSize = 13.sp, color = Color.Gray)
            }

            items(savedCards.size) { i ->
                CreditCardView(card = savedCards[i])
            }

            item { Spacer(modifier = Modifier.height(8.dp)) }

            // Other payment options
            item {
                Text("Otras opciones", fontWeight = FontWeight.ExtraBold, fontSize = 16.sp, color = Black)
                Spacer(modifier = Modifier.height(12.dp))
            }

            val otherOptions = listOf(
                Triple(Icons.Default.AccountBalance, "PSE / Transferencia bancaria", "Pago directo desde tu banco"),
                Triple(Icons.Default.PhoneAndroid,   "Nequi / Daviplata",            "Billetera digital"),
                Triple(Icons.Default.Nfc,             "Pago NFC SmartBus",            "Toca y paga en el bus"),
            )

            items(otherOptions.size) { i ->
                val (icon, title, sub) = otherOptions[i]
                Card(
                    shape = RoundedCornerShape(14.dp),
                    colors = CardDefaults.cardColors(containerColor = White),
                    elevation = CardDefaults.cardElevation(2.dp)
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth().padding(16.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Box(
                            modifier = Modifier
                                .size(44.dp)
                                .clip(RoundedCornerShape(12.dp))
                                .background(Gold.copy(alpha = 0.1f)),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(icon, null, tint = Gold, modifier = Modifier.size(22.dp))
                        }
                        Spacer(modifier = Modifier.width(14.dp))
                        Column(modifier = Modifier.weight(1f)) {
                            Text(title, fontWeight = FontWeight.SemiBold, fontSize = 14.sp)
                            Text(sub, fontSize = 12.sp, color = Color.Gray)
                        }
                        Icon(Icons.Default.ChevronRight, null, tint = Color.LightGray)
                    }
                }
            }
        }
    }
}

@Composable
private fun CreditCardView(card: SavedCard) {
    var expanded by remember { mutableStateOf(false) }

    Column {
        // The physical card
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .height(190.dp)
                .clickable { expanded = !expanded },
            shape = RoundedCornerShape(20.dp),
            elevation = CardDefaults.cardElevation(8.dp)
        ) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Brush.linearGradient(listOf(card.gradientStart, card.gradientEnd)))
                    .padding(24.dp)
            ) {
                // Default badge
                if (card.isDefault) {
                    Surface(
                        color = Gold.copy(alpha = 0.2f),
                        shape = RoundedCornerShape(6.dp),
                        modifier = Modifier.align(Alignment.TopEnd)
                    ) {
                        Text(
                            "✓ Predeterminada",
                            color = Gold,
                            fontSize = 10.sp,
                            fontWeight = FontWeight.ExtraBold,
                            modifier = Modifier.padding(horizontal = 8.dp, vertical = 3.dp)
                        )
                    }
                }

                Column(modifier = Modifier.fillMaxSize(), verticalArrangement = Arrangement.SpaceBetween) {
                    // Brand
                    Text(card.brand, color = Gold, fontWeight = FontWeight.ExtraBold, fontSize = 18.sp, letterSpacing = 1.sp)

                    // Card number
                    Text(
                        "•••• •••• •••• ${card.last4}",
                        color = White,
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        letterSpacing = 3.sp
                    )

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Column {
                            Text("TITULAR", color = White.copy(alpha = 0.5f), fontSize = 9.sp, letterSpacing = 1.sp)
                            Text(card.holder, color = White, fontSize = 12.sp, fontWeight = FontWeight.SemiBold)
                        }
                        Column(horizontalAlignment = Alignment.End) {
                            Text("VENCE", color = White.copy(alpha = 0.5f), fontSize = 9.sp, letterSpacing = 1.sp)
                            Text(card.expiry, color = White, fontSize = 12.sp, fontWeight = FontWeight.SemiBold)
                        }
                    }
                }
            }
        }

        // Actions when expanded
        AnimatedVisibility(visible = expanded) {
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(bottomStart = 16.dp, bottomEnd = 16.dp),
                colors = CardDefaults.cardColors(containerColor = White),
                elevation = CardDefaults.cardElevation(2.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth().padding(12.dp),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    TextButton(onClick = {}) {
                        Icon(Icons.Default.Star, null, tint = Gold, modifier = Modifier.size(16.dp))
                        Spacer(modifier = Modifier.width(4.dp))
                        Text("Predeterminar", color = Gold, fontSize = 12.sp)
                    }
                    TextButton(onClick = {}) {
                        Icon(Icons.Default.Delete, null, tint = Color(0xFFE53935), modifier = Modifier.size(16.dp))
                        Spacer(modifier = Modifier.width(4.dp))
                        Text("Eliminar", color = Color(0xFFE53935), fontSize = 12.sp)
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun AddCardSheet(onDismiss: () -> Unit) {
    var number by remember { mutableStateOf("") }
    var name by remember { mutableStateOf("") }
    var expiry by remember { mutableStateOf("") }
    var cvv by remember { mutableStateOf("") }

    ModalBottomSheet(onDismissRequest = onDismiss, containerColor = White) {
        Column(modifier = Modifier.padding(24.dp).navigationBarsPadding()) {
            Text("Agregar tarjeta", fontWeight = FontWeight.ExtraBold, fontSize = 20.sp)
            Spacer(modifier = Modifier.height(20.dp))

            OutlinedTextField(
                value = number, onValueChange = { number = it },
                label = { Text("Número de tarjeta") },
                leadingIcon = { Icon(Icons.Default.CreditCard, null, tint = Gold) },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp)
            )
            Spacer(modifier = Modifier.height(12.dp))
            OutlinedTextField(
                value = name, onValueChange = { name = it },
                label = { Text("Nombre del titular") },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp)
            )
            Spacer(modifier = Modifier.height(12.dp))
            Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                OutlinedTextField(
                    value = expiry, onValueChange = { expiry = it },
                    label = { Text("MM/AA") },
                    modifier = Modifier.weight(1f),
                    shape = RoundedCornerShape(12.dp)
                )
                OutlinedTextField(
                    value = cvv, onValueChange = { cvv = it },
                    label = { Text("CVV") },
                    modifier = Modifier.weight(1f),
                    shape = RoundedCornerShape(12.dp)
                )
            }
            Spacer(modifier = Modifier.height(24.dp))
            Button(
                onClick = onDismiss,
                modifier = Modifier.fillMaxWidth().height(52.dp),
                shape = RoundedCornerShape(14.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Black)
            ) {
                Text("Guardar tarjeta", color = Gold, fontWeight = FontWeight.Bold)
            }
            Spacer(modifier = Modifier.height(8.dp))
        }
    }
}
