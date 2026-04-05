package com.smartbus.app.presentation.travelcredit

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.*
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.CheckCircle
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
import com.smartbus.app.ui.components.SmartBusButton
import com.smartbus.app.ui.components.SmartBusCard
import com.smartbus.app.ui.components.StatusChip
import com.smartbus.app.ui.theme.Black
import com.smartbus.app.ui.theme.Gold
import com.smartbus.app.ui.theme.GoldDark
import com.smartbus.app.ui.theme.White

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TravelCreditScreen(
    viewModel: TravelCreditViewModel,
    onNavigateBack: () -> Unit,
    onUseCredit: () -> Unit
) {
    val uiState by viewModel.uiState.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Crédito de Viaje", style = MaterialTheme.typography.headlineMedium) },
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
            Spacer(modifier = Modifier.height(16.dp))
            
            // Premium Embossed Card
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(220.dp),
                shape = RoundedCornerShape(20.dp),
                colors = CardDefaults.cardColors(containerColor = Black),
                elevation = CardDefaults.cardElevation(defaultElevation = 12.dp)
            ) {
                Box(modifier = Modifier.fillMaxSize().padding(24.dp)) {
                    Column {
                        Text("Travel Credit Premium", color = Gold, style = MaterialTheme.typography.labelSmall)
                        Spacer(modifier = Modifier.height(40.dp))
                        Text(
                            uiState.availableCredit,
                            style = MaterialTheme.typography.displayLarge,
                            color = White,
                            fontSize = 36.sp,
                            fontWeight = FontWeight.Bold
                        )
                        Text("Cupo disponible", color = White.copy(alpha = 0.5f), fontSize = 14.sp)
                    }
                    
                    StatusChip(
                        text = uiState.status,
                        modifier = Modifier.align(Alignment.TopEnd)
                    )
                    
                    Text(
                        "SMARTBUS COLOMBIA",
                        color = Gold.copy(alpha = 0.5f),
                        modifier = Modifier.align(Alignment.BottomEnd),
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold,
                        letterSpacing = 2.sp
                    )
                }
            }

            Spacer(modifier = Modifier.height(32.dp))

            Text("Viajes por nivel", style = MaterialTheme.typography.titleLarge)
            Spacer(modifier = Modifier.height(16.dp))
            
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                uiState.tripsInfo.forEach { info ->
                    SmartBusCard(modifier = Modifier.weight(1f), showBorder = true) {
                        Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.fillMaxWidth()) {
                            Text(info.level, fontSize = 12.sp, color = Gold)
                            Text(info.count.toString(), fontSize = 24.sp, fontWeight = FontWeight.Black)
                            Text("Viajes", fontSize = 10.sp)
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(40.dp))

            Text("Reglas y beneficios", style = MaterialTheme.typography.titleLarge)
            Spacer(modifier = Modifier.height(16.dp))
            
            LazyColumn(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                items(uiState.rules) { rule ->
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(Icons.Default.CheckCircle, contentDescription = null, tint = Gold, modifier = Modifier.size(20.dp))
                        Spacer(modifier = Modifier.width(12.dp))
                        Text(rule)
                    }
                }
            }

            Spacer(modifier = Modifier.weight(1f))

            SmartBusButton(
                text = stringResource(R.string.use_credit),
                onClick = onUseCredit,
                modifier = Modifier.padding(bottom = 24.dp)
            )
        }
    }
}
