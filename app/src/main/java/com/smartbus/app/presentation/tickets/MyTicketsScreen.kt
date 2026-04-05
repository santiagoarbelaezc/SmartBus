package com.smartbus.app.presentation.tickets

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.BusAlert
import androidx.compose.material.icons.filled.QrCode
import androidx.compose.material3.*
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.smartbus.app.R
import com.smartbus.app.ui.components.DashedDivider
import com.smartbus.app.ui.components.SmartBusButton
import com.smartbus.app.ui.components.SmartBusCard
import com.smartbus.app.ui.components.StatusChip
import com.smartbus.app.ui.theme.Black
import com.smartbus.app.ui.theme.BorderGold
import com.smartbus.app.ui.theme.Gold
import com.smartbus.app.ui.theme.TextPrimary

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyTicketsScreen(
    viewModel: MyTicketsViewModel,
    onNavigateBack: () -> Unit,
    onTrackBus: (String) -> Unit
) {
    val uiState by viewModel.uiState.collectAsState()
    var selectedTab by remember { mutableIntStateOf(0) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Mis Tiquetes", style = MaterialTheme.typography.headlineMedium) },
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
        ) {
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
                    Text(stringResource(R.string.active), modifier = Modifier.padding(16.dp))
                }
                Tab(selected = selectedTab == 1, onClick = { selectedTab = 1 }) {
                    Text(stringResource(R.string.history), modifier = Modifier.padding(16.dp))
                }
            }

            val tickets = if (selectedTab == 0) uiState.activeTickets else uiState.historyTickets

            LazyColumn(
                contentPadding = PaddingValues(24.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp),
                modifier = Modifier.fillMaxSize()
            ) {
                items(tickets) { ticket ->
                    TicketItem(ticket = ticket, onTrack = { onTrackBus(ticket.id) })
                }
            }
        }
    }
}

@Composable
fun TicketItem(ticket: TicketInfo, onTrack: () -> Unit) {
    var expanded by remember { mutableStateOf(false) }

    SmartBusCard(
        modifier = Modifier.fillMaxWidth(),
        showBorder = true
    ) {
        Column(modifier = Modifier.fillMaxWidth()) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column {
                    Text("${ticket.origin} → ${ticket.destination}", fontWeight = FontWeight.Bold, fontSize = 18.sp)
                    Text(ticket.date, style = MaterialTheme.typography.bodySmall)
                }
                StatusChip(text = ticket.status)
            }
            
            Spacer(modifier = Modifier.height(16.dp))
            DashedDivider()
            Spacer(modifier = Modifier.height(16.dp))
            
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                Text("Asiento: ${ticket.seat}", fontWeight = FontWeight.Medium)
                Text("Puerta: 04", style = MaterialTheme.typography.bodySmall)
            }
            
            Spacer(modifier = Modifier.height(16.dp))
            
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                OutlinedButton(
                    onClick = { expanded = !expanded },
                    modifier = Modifier.weight(1f),
                    shape = RoundedCornerShape(12.dp),
                    colors = ButtonDefaults.outlinedButtonColors(contentColor = Black)
                ) {
                    Icon(Icons.Default.QrCode, contentDescription = null, tint = Gold)
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(if (expanded) "Ocultar QR" else "Ver QR")
                }
                
                IconButton(
                    onClick = onTrack,
                    modifier = Modifier
                        .background(Black, RoundedCornerShape(12.dp))
                        .size(48.dp)
                ) {
                    Icon(Icons.Default.BusAlert, contentDescription = null, tint = Gold)
                }
            }
            
            AnimatedVisibility(visible = expanded) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 24.dp)
                        .height(200.dp)
                        .background(Black, RoundedCornerShape(16.dp))
                        .border(1.dp, Gold, RoundedCornerShape(16.dp)),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(Icons.Default.QrCode, contentDescription = null, tint = Gold, modifier = Modifier.size(120.dp))
                }
            }
        }
    }
}
