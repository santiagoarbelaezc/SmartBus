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
            Spacer(modifier = Modifier.height(32.dp))

            // Realistic Bus Reader (Validator) UI
            Surface(
                modifier = Modifier
                    .width(200.dp)
                    .height(280.dp),
                shape = RoundedCornerShape(topStart = 40.dp, topEnd = 40.dp, bottomStart = 20.dp, bottomEnd = 20.dp),
                color = Charcoal,
                border = androidx.compose.foundation.BorderStroke(4.dp, Color.DarkGray)
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.padding(16.dp)
                ) {
                    // Validator Screen
                    Surface(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(110.dp)
                            .padding(8.dp),
                        shape = RoundedCornerShape(8.dp),
                        color = Black,
                        border = androidx.compose.foundation.BorderStroke(1.dp, Gold.copy(alpha = 0.3f))
                    ) {
                        Box(contentAlignment = Alignment.Center) {
                            androidx.compose.animation.AnimatedContent(
                                targetState = uiState.status,
                                transitionSpec = { fadeIn() togetherWith fadeOut() },
                                label = "ValidatorScreen"
                            ) { status ->
                                when (status) {
                                    NfcStatus.Idle -> Text("ACERQUE SU CELULAR", color = Gold, fontSize = 10.sp, fontWeight = FontWeight.Bold, textAlign = androidx.compose.ui.text.style.TextAlign.Center)
                                    NfcStatus.Reading -> Text("LEYENDO...", color = Gold, fontSize = 12.sp, fontWeight = FontWeight.Bold)
                                    NfcStatus.Success -> Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                        Icon(Icons.Default.Check, tint = Color.Green, contentDescription = null, modifier = Modifier.size(32.dp))
                                        Text("APROBADO", color = Color.Green, fontSize = 10.sp, fontWeight = FontWeight.Bold)
                                    }
                                    NfcStatus.Error -> Text("INTENTE DE NUEVO", color = Color.Red, fontSize = 10.sp, fontWeight = FontWeight.Bold)
                                }
                            }
                        }
                    }
                    
                    Spacer(modifier = Modifier.height(24.dp))
                    
                    // NFC Target Icon
                    Icon(
                        Icons.Outlined.Nfc,
                        contentDescription = null,
                        modifier = Modifier.size(70.dp),
                        tint = if (uiState.status == NfcStatus.Success) Color.Green else Gold.copy(alpha = 0.5f)
                    )
                }
            }

            Spacer(modifier = Modifier.weight(1f))

            // Phone Animation Overlay (Simulated)
            Box(
                modifier = Modifier.fillMaxWidth().height(160.dp),
                contentAlignment = Alignment.Center
            ) {
                NfcWaveAnimation(isPlaying = uiState.status == NfcStatus.Idle || uiState.status == NfcStatus.Reading)
                
                if (uiState.status == NfcStatus.Success) {
                     Text("¡Pago Exitoso!", color = Gold, style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.Bold)
                } else {
                     Text("Pagar por NFC", color = White.copy(alpha = 0.7f), fontSize = 14.sp)
                }
            }

            // Bottom Balance Card
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
