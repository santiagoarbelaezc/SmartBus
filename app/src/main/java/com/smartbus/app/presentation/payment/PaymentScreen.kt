package com.smartbus.app.presentation.payment

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.CreditCard
import androidx.compose.material3.*
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.smartbus.app.R
import com.smartbus.app.ui.components.SmartBusButton
import com.smartbus.app.ui.components.SmartBusCard
import com.smartbus.app.ui.components.SmartBusTextField
import com.smartbus.app.ui.theme.Gold
import com.smartbus.app.ui.theme.TextPrimary

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
                title = { Text("Pago", style = MaterialTheme.typography.headlineMedium) },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(Icons.Default.ArrowBack, contentDescription = null)
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = Color.Transparent)
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
                .padding(horizontal = 24.dp)
        ) {
            // Summary Card
            SmartBusCard(modifier = Modifier.fillMaxWidth()) {
                Text("Resumen del viaje", style = MaterialTheme.typography.labelLarge)
                Spacer(modifier = Modifier.height(8.dp))
                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                    Text(uiState.route, fontWeight = FontWeight.Bold)
                    Text(uiState.total, color = Gold, fontWeight = FontWeight.Bold, fontSize = 18.sp)
                }
                Text("${uiState.date} • Asiento ${uiState.seat}", style = MaterialTheme.typography.bodySmall)
            }

            Spacer(modifier = Modifier.height(32.dp))

            // Payment Method Selector
            TabRow(
                selectedTabIndex = selectedTab,
                containerColor = Color.Transparent,
                contentColor = Gold,
                indicator = { tabPositions ->
                    TabRowDefaults.SecondaryIndicator(
                        Modifier.tabIndicatorOffset(tabPositions[selectedTab]),
                        color = Gold
                    )
                },
                divider = {}
            ) {
                Tab(selected = selectedTab == 0, onClick = { selectedTab = 0 }) {
                    Text("Tarjeta", modifier = Modifier.padding(16.dp), fontWeight = if (selectedTab == 0) FontWeight.Bold else FontWeight.Normal)
                }
                Tab(selected = selectedTab == 1, onClick = { selectedTab = 1 }) {
                    Text("Crédito", modifier = Modifier.padding(16.dp), fontWeight = if (selectedTab == 1) FontWeight.Bold else FontWeight.Normal)
                }
                Tab(selected = selectedTab == 2, onClick = { selectedTab = 2 }) {
                    Text("Puntos", modifier = Modifier.padding(16.dp), fontWeight = if (selectedTab == 2) FontWeight.Bold else FontWeight.Normal)
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            // Card Form
            SmartBusTextField(
                value = uiState.selectedCardNumber,
                onValueChange = viewModel::onCardNumberChange,
                label = "Número de tarjeta",
                leadingIcon = { Icon(Icons.Default.CreditCard, contentDescription = null) }
            )
            Spacer(modifier = Modifier.height(16.dp))
            SmartBusTextField(
                value = uiState.cardName,
                onValueChange = viewModel::onCardNameChange,
                label = "Nombre en la tarjeta"
            )
            Spacer(modifier = Modifier.height(16.dp))
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(16.dp)) {
                SmartBusTextField(
                    value = uiState.expiry,
                    onValueChange = viewModel::onExpiryChange,
                    label = "MM/AA",
                    modifier = Modifier.weight(1f)
                )
                SmartBusTextField(
                    value = uiState.cvc,
                    onValueChange = viewModel::onCvcChange,
                    label = "CVC",
                    modifier = Modifier.weight(1f),
                    visualTransformation = PasswordVisualTransformation()
                )
            }

            Spacer(modifier = Modifier.weight(1f))

            SmartBusButton(
                text = stringResource(R.string.pay_now),
                onClick = onPaymentSuccess,
                isSecondary = true,
                modifier = Modifier.padding(bottom = 24.dp)
            )
        }
    }
}
