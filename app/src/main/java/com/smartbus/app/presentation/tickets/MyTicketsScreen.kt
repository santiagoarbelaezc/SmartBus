package com.smartbus.app.presentation.tickets

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.smartbus.app.R
import com.smartbus.app.ui.components.DashedDivider
import com.smartbus.app.ui.components.SmartBusButton
import com.smartbus.app.ui.components.SmartBusCard
import com.smartbus.app.ui.components.StatusChip
import com.smartbus.app.ui.theme.Black
import com.smartbus.app.ui.theme.Gold
import com.smartbus.app.ui.theme.White

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
                title = { Text("Mis Tiquetes", color = Gold, fontWeight = FontWeight.Bold) },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(Icons.Default.ArrowBack, contentDescription = null, tint = White)
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = Black)
            )
        },
        containerColor = Color(0xFFF8F9FA)
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
        ) {
            // Premium TabRow
            Surface(color = Black, modifier = Modifier.fillMaxWidth()) {
                TabRow(
                    selectedTabIndex = selectedTab,
                    containerColor = Color.Transparent,
                    contentColor = Gold,
                    indicator = { tabPositions ->
                        TabRowDefaults.SecondaryIndicator(
                            Modifier.tabIndicatorOffset(tabPositions[selectedTab]),
                            color = Gold,
                            height = 3.dp
                        )
                    },
                    divider = {}
                ) {
                    val tabs = listOf("Activos", "Historial")
                    tabs.forEachIndexed { index, title ->
                        Tab(
                            selected = selectedTab == index,
                            onClick = { selectedTab = index },
                            text = { 
                                Text(
                                    title, 
                                    fontWeight = if (selectedTab == index) FontWeight.Bold else FontWeight.Normal,
                                    fontSize = 15.sp
                                ) 
                            }
                        )
                    }
                }
            }

            val tickets = if (selectedTab == 0) uiState.activeTickets else uiState.historyTickets

            if (tickets.isEmpty()) {
                EmptyTicketsState()
            } else {
                LazyColumn(
                    contentPadding = PaddingValues(20.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp),
                    modifier = Modifier.fillMaxSize()
                ) {
                    items(tickets) { ticket ->
                        TicketItemFixed(ticket = ticket, onTrack = { onTrackBus(ticket.id) })
                    }
                }
            }
        }
    }
}

@Composable
fun TicketItemFixed(ticket: TicketInfo, onTrack: () -> Unit) {
    var expanded by remember { mutableStateOf(false) }

    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(containerColor = White),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        border = androidx.compose.foundation.BorderStroke(1.dp, Color.LightGray.copy(alpha = 0.2f))
    ) {
        Column(modifier = Modifier.padding(20.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.Top
            ) {
                Row(modifier = Modifier.weight(1f), verticalAlignment = Alignment.CenterVertically) {
                    Surface(
                        modifier = Modifier.size(44.dp),
                        shape = RoundedCornerShape(10.dp),
                        color = Color.White,
                        shadowElevation = 2.dp
                    ) {
                        androidx.compose.foundation.Image(
                            painter = androidx.compose.ui.res.painterResource(id = ticket.companyImageRes),
                            contentDescription = ticket.companyName,
                            modifier = Modifier.fillMaxSize(),
                            contentScale = androidx.compose.ui.layout.ContentScale.Crop
                        )
                    }
                    Spacer(modifier = Modifier.width(16.dp))
                    Column {
                        Text(
                            text = "${ticket.origin} → ${ticket.destination}",
                            fontWeight = FontWeight.ExtraBold,
                            fontSize = 18.sp,
                            color = Black
                        )
                        Text(
                            text = ticket.companyName,
                            fontSize = 11.sp,
                            color = Gold,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
                Surface(
                    color = Gold.copy(alpha = 0.1f),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Text(
                        text = ticket.status,
                        modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp),
                        color = Gold,
                        fontWeight = FontWeight.Bold,
                        fontSize = 11.sp
                    )
                }
            }
            
            Spacer(modifier = Modifier.height(16.dp))
            DashedDivider(color = Color.LightGray.copy(alpha = 0.4f))
            Spacer(modifier = Modifier.height(16.dp))
            
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                Column {
                    Text("Asiento", color = Color.Gray, fontSize = 11.sp)
                    Text(ticket.seat, fontWeight = FontWeight.Bold)
                }
                Column(horizontalAlignment = Alignment.End) {
                    Text("Terminal", color = Color.Gray, fontSize = 11.sp)
                    Text("Puerta 04", fontWeight = FontWeight.Bold)
                }
            }
            
            Spacer(modifier = Modifier.height(20.dp))
            
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                SmartBusButton(
                    text = if (expanded) "Cerrar QR" else "Ver Tiquete",
                    onClick = { expanded = !expanded },
                    modifier = Modifier.weight(1f).height(48.dp)
                )
                
                IconButton(
                    onClick = onTrack,
                    modifier = Modifier
                        .background(Black, RoundedCornerShape(16.dp))
                        .size(48.dp)
                ) {
                    Icon(Icons.Default.MyLocation, contentDescription = null, tint = Gold)
                }
            }
            
            AnimatedVisibility(visible = expanded) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 20.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Box(
                        modifier = Modifier
                            .size(200.dp)
                            .background(Color.White)
                            .border(1.dp, Color.LightGray.copy(alpha = 0.3f), RoundedCornerShape(16.dp)),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(Icons.Default.QrCode2, contentDescription = null, tint = Black, modifier = Modifier.size(140.dp))
                    }
                    Spacer(modifier = Modifier.height(12.dp))
                    Text("Muestra este código al abordar", fontSize = 12.sp, color = Color.Gray)
                }
            }
        }
    }
}

@Composable
fun EmptyTicketsState() {
    Column(
        modifier = Modifier.fillMaxSize().padding(32.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Icon(
            Icons.Default.ConfirmationNumber,
            contentDescription = null,
            modifier = Modifier.size(80.dp),
            tint = Color.LightGray
        )
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            "No tienes tiquetes activos",
            fontWeight = FontWeight.Bold,
            color = Black,
            style = MaterialTheme.typography.titleMedium
        )
        Text(
            "Tus próximos viajes aparecerán aquí",
            color = Color.Gray,
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.bodyMedium
        )
    }
}
