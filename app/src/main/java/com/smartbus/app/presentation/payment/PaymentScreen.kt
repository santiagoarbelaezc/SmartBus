package com.smartbus.app.presentation.payment

import androidx.compose.foundation.background
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.smartbus.app.domain.model.defaultSavedCards
import com.smartbus.app.ui.components.CreditCardView
import com.smartbus.app.ui.components.SmartBusButton
import com.smartbus.app.ui.components.SmartBusLoadingOverlay
import com.smartbus.app.R
import com.smartbus.app.ui.components.*
import com.smartbus.app.ui.theme.Gold
import com.smartbus.app.ui.theme.TextPrimary
import com.smartbus.app.ui.theme.Black
import com.smartbus.app.ui.theme.White
import com.smartbus.app.ui.theme.Charcoal

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PaymentScreen(
    viewModel: PaymentViewModel,
    onNavigateBack: () -> Unit,
    onTrackBus: () -> Unit,
    onPaymentSuccess: () -> Unit
) {
    val uiState by viewModel.uiState.collectAsState()
    var selectedTab by remember { mutableIntStateOf(0) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Pago", style = MaterialTheme.typography.headlineMedium, color = White) },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(Icons.Default.ArrowBack, contentDescription = null, tint = White)
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = Black)
            )
        },
        containerColor = Black
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
                .padding(horizontal = 24.dp)
        ) {
            // Summary Card
            SmartBusCard(
                modifier = Modifier.fillMaxWidth(),
                containerColor = Charcoal
            ) {
                Text("Resumen del viaje", style = MaterialTheme.typography.labelLarge, color = White.copy(alpha = 0.6f))
                Spacer(modifier = Modifier.height(8.dp))
                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                    Text(uiState.route, fontWeight = FontWeight.Bold, color = White, fontSize = 18.sp)
                    Text(uiState.total, color = Gold, fontWeight = FontWeight.Bold, fontSize = 20.sp)
                }
                Text("${uiState.date} • Asiento ${uiState.seat}", style = MaterialTheme.typography.bodySmall, color = White.copy(alpha = 0.4f))
            }

            Spacer(modifier = Modifier.height(32.dp))

            // Payment Method Selector (MODERN LIST STYLE)
            Text(
                "Selecciona un método de pago",
                style = MaterialTheme.typography.titleSmall,
                fontWeight = FontWeight.Bold,
                color = White,
                modifier = Modifier.padding(bottom = 16.dp)
            )

            val methods = listOf("Tarjeta de Crédito/Débito", "PSE", "Nequi")
            val icons = listOf(Icons.Default.CreditCard, Icons.Default.AccountBalance, Icons.Default.PhoneAndroid)
            
            Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                methods.forEachIndexed { index, method ->
                    val isSelected = uiState.selectedCardIndex == index
                    PaymentMethodItem(
                        title = method,
                        icon = icons[index],
                        isSelected = isSelected,
                        onClick = { viewModel.onCardSelect(index) }
                    )
                }
            }

            Spacer(modifier = Modifier.height(32.dp))

            // Security Note
            Surface(
                color = Gold.copy(alpha = 0.08f),
                shape = RoundedCornerShape(12.dp),
                border = BorderStroke(1.dp, Gold.copy(alpha = 0.2f))
            ) {
                Row(
                    modifier = Modifier.padding(16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(Icons.Default.Lock, null, tint = Gold, modifier = Modifier.size(16.dp))
                    Spacer(modifier = Modifier.width(12.dp))
                    Text(
                        "Tu pago está protegido con encriptación de grado bancario",
                        color = Gold.copy(alpha = 0.8f),
                        fontSize = 12.sp,
                        lineHeight = 16.sp,
                        fontWeight = FontWeight.Medium
                    )
                }
            }

            Spacer(modifier = Modifier.weight(1f))

            SmartBusButton(
                text = "Pagar ${uiState.total}",
                onClick = onPaymentSuccess,
                isSecondary = true,
                modifier = Modifier.padding(bottom = 24.dp)
            )
        }
    }
}

@Composable
fun PaymentMethodItem(
    title: String,
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    Surface(
        onClick = onClick,
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        color = Charcoal,
        border = if (isSelected) BorderStroke(1.dp, Gold) else null
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(40.dp)
                    .clip(RoundedCornerShape(12.dp))
                    .background(White.copy(alpha = 0.05f)),
                contentAlignment = Alignment.Center
            ) {
                Icon(icon, null, tint = if (isSelected) Gold else White.copy(alpha = 0.4f), modifier = Modifier.size(20.dp))
            }
            Spacer(modifier = Modifier.width(16.dp))
            Text(
                title,
                color = if (isSelected) White else White.copy(alpha = 0.7f),
                fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium,
                modifier = Modifier.weight(1f)
            )
            if (isSelected) {
                Icon(Icons.Default.CheckCircle, null, tint = Gold, modifier = Modifier.size(22.dp))
            }
        }
    }
}
