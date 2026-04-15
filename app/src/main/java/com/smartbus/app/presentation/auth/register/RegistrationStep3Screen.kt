package com.smartbus.app.presentation.auth.register

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.smartbus.app.ui.components.SmartBusButton
import com.smartbus.app.ui.components.SmartBusCard
import com.smartbus.app.ui.components.SmartBusTextField
import com.smartbus.app.ui.theme.Black
import com.smartbus.app.ui.theme.Gold

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RegistrationStep3Screen(
    viewModel: RegisterViewModel,
    onFinish: () -> Unit,
    onSkip: () -> Unit
) {
    var selectedMethod by remember { mutableStateOf("Card") }
    val scrollState = rememberScrollState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Método de Pago", color = Gold, fontWeight = FontWeight.Bold) },
                actions = {
                    TextButton(onClick = onSkip) {
                        Text("Omitir", color = Gold.copy(alpha = 0.7f))
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = Black)
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .background(Color.White)
                .verticalScroll(scrollState)
                .padding(24.dp)
        ) {
            Text(
                "Asegura tus viajes",
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold,
                color = Black
            )
            Text(
                "Guarda un método de pago para reservar tus pasajes de forma instantánea.",
                style = MaterialTheme.typography.bodySmall,
                color = Color.Gray
            )
            
            Spacer(modifier = Modifier.height(24.dp))

            // Payment Options
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                PaymentMethodChip("Nequi", selectedMethod == "Nequi", Modifier.weight(1f)) { selectedMethod = "Nequi" }
                PaymentMethodChip("Daviplata", selectedMethod == "Daviplata", Modifier.weight(1f)) { selectedMethod = "Daviplata" }
                PaymentMethodChip("Tarjeta", selectedMethod == "Card", Modifier.weight(1f)) { selectedMethod = "Card" }
            }

            Spacer(modifier = Modifier.height(32.dp))

            // Card Input Section (Mock)
            if (selectedMethod == "Card") {
                Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {
                    SmartBusTextField(
                        value = "",
                        onValueChange = {},
                        label = "Número de tarjeta",
                        leadingIcon = { Icon(Icons.Default.CreditCard, contentDescription = null, tint = Gold) }
                    )
                    
                    Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                        SmartBusTextField(
                            value = "",
                            onValueChange = {},
                            label = "MM/YY",
                            modifier = Modifier.weight(1f)
                        )
                        SmartBusTextField(
                            value = "",
                            onValueChange = {},
                            label = "CVV",
                            modifier = Modifier.weight(1f)
                        )
                    }
                    
                    SmartBusTextField(
                        value = "",
                        onValueChange = {},
                        label = "Nombre en la tarjeta"
                    )
                }
            } else {
                // Mock Wallet Section
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(20.dp))
                        .background(Color.LightGray.copy(alpha = 0.1f))
                        .padding(24.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Icon(
                        Icons.Default.AccountBalanceWallet,
                        contentDescription = null,
                        modifier = Modifier.size(60.dp),
                        tint = Gold
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    Text(
                        "Vincular tu cuenta de $selectedMethod",
                        fontWeight = FontWeight.Bold,
                        color = Black
                    )
                    Text(
                        "Serás redirigido para autorizar el pago automático.",
                        fontSize = 12.sp,
                        color = Color.Gray,
                        textAlign = TextAlign.Center
                    )
                }
            }

            Spacer(modifier = Modifier.height(48.dp))

            SmartBusButton(
                text = "Finalizar Registro",
                onClick = onFinish,
                modifier = Modifier.fillMaxWidth().height(56.dp)
            )
            
            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}

@Composable
fun PaymentMethodChip(
    name: String,
    isSelected: Boolean,
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    Surface(
        modifier = modifier
            .height(50.dp)
            .clickable(onClick = onClick),
        shape = RoundedCornerShape(14.dp),
        color = if (isSelected) Gold else Color.LightGray.copy(alpha = 0.1f),
        border = if (isSelected) null else BorderStroke(1.dp, Color.LightGray.copy(alpha = 0.3f))
    ) {
        Box(contentAlignment = Alignment.Center) {
            Text(
                text = name,
                color = if (isSelected) Black else Color.Gray,
                fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal,
                fontSize = 13.sp
            )
        }
    }
}
