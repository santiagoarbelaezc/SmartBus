package com.smartbus.app.presentation.nfc

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.Nfc
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.smartbus.app.R
import com.smartbus.app.ui.components.NfcWaveAnimation
import com.smartbus.app.ui.theme.Black
import com.smartbus.app.ui.theme.Charcoal
import com.smartbus.app.ui.theme.Gold
import com.smartbus.app.ui.theme.White

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NFCPaymentScreen(
    viewModel: NFCPaymentViewModel,
    onNavigateBack: () -> Unit
) {
    val uiState by viewModel.uiState.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(stringResource(R.string.nfc_payment_title), color = Gold) },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(Icons.Default.ArrowBack, contentDescription = null, tint = White)
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = Color.Transparent)
            )
        },
        containerColor = Black
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(40.dp))

            // Central Animation Section
            Box(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth(),
                contentAlignment = Alignment.Center
            ) {
                NfcWaveAnimation(isPlaying = uiState.status != NfcStatus.Success)

                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    AnimatedContent(
                        targetState = uiState.status,
                        transitionSpec = { fadeIn() togetherWith fadeOut() },
                        label = "NFCStatusIcon"
                    ) { status ->
                        when (status) {
                            NfcStatus.Success -> Icon(Icons.Default.CheckCircle, null, Modifier.size(80.dp), Gold)
                            NfcStatus.Error -> Icon(Icons.Default.Cancel, null, Modifier.size(80.dp), Color.Red)
                            else -> Icon(Icons.Outlined.Nfc, null, Modifier.size(80.dp), Gold)
                        }
                    }

                    Spacer(modifier = Modifier.height(24.dp))

                    Text(
                        text = when (uiState.status) {
                            NfcStatus.Idle -> stringResource(R.string.nfc_instruction)
                            NfcStatus.Reading -> stringResource(R.string.reading)
                            NfcStatus.Success -> stringResource(R.string.payment_success)
                            NfcStatus.Error -> stringResource(R.string.payment_error)
                        },
                        color = White,
                        style = MaterialTheme.typography.headlineMedium,
                        fontWeight = FontWeight.Bold
                    )
                    
                    Text(
                        text = stringResource(R.string.nfc_sub_instruction),
                        color = MaterialTheme.colorScheme.secondary,
                        style = MaterialTheme.typography.bodyLarge
                    )
                }
            }

            // Bottom Info Card
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(24.dp),
                shape = RoundedCornerShape(24.dp),
                colors = CardDefaults.cardColors(containerColor = Charcoal),
                border = androidx.compose.foundation.BorderStroke(1.dp, Gold.copy(alpha = 0.3f))
            ) {
                Column(modifier = Modifier.padding(24.dp)) {
                    Text(stringResource(R.string.available_balance), color = Gold, style = MaterialTheme.typography.labelSmall)
                    Text(uiState.availableBalance, color = White, fontSize = 28.sp, fontWeight = FontWeight.Bold)
                    
                    Spacer(modifier = Modifier.height(16.dp))
                    
                    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                        Column {
                            Text(stringResource(R.string.approx_fare), color = White.copy(alpha = 0.5f), fontSize = 12.sp)
                            Text(uiState.approxFare, color = White, fontWeight = FontWeight.Medium)
                        }
                        Column(horizontalAlignment = Alignment.End) {
                            Text(stringResource(R.string.last_payment), color = White.copy(alpha = 0.5f), fontSize = 12.sp)
                            Text(uiState.lastPayment, color = White.copy(alpha = 0.7f), fontSize = 11.sp)
                        }
                    }

                    Spacer(modifier = Modifier.height(24.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(stringResource(R.string.use_points_discount), color = White, fontSize = 14.sp)
                        Switch(
                            checked = uiState.usePoints,
                            onCheckedChange = viewModel::onPointsToggle,
                            colors = SwitchDefaults.colors(checkedTrackColor = Gold)
                        )
                    }
                }
            }
            
            // Mock Trigger for reading
            if (uiState.status == NfcStatus.Idle) {
                Button(onClick = viewModel::startReading, colors = ButtonDefaults.buttonColors(containerColor = Gold)) {
                    Text("Simular acercamiento", color = Black)
                }
                Spacer(modifier = Modifier.height(16.dp))
            }
        }
    }
}
