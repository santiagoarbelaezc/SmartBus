package com.smartbus.app.presentation.seatselection

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.smartbus.app.ui.components.SeatBox
import com.smartbus.app.ui.components.SeatStatus
import com.smartbus.app.ui.theme.Black
import com.smartbus.app.ui.theme.Charcoal
import com.smartbus.app.ui.theme.Gold
import com.smartbus.app.ui.theme.GoldDark
import com.smartbus.app.ui.theme.White

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SeatSelectionScreen(
    viewModel: SeatSelectionViewModel,
    onNavigateBack: () -> Unit,
    onContinueToPayment: () -> Unit
) {
    val uiState by viewModel.uiState.collectAsState()
    val rows = uiState.seats.groupBy { it.row }.toSortedMap()

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Column {
                        Text(
                            "Selecciona tu asiento",
                            color = White,
                            fontWeight = FontWeight.ExtraBold,
                            fontSize = 17.sp
                        )
                        Text(
                            "${uiState.origin} → ${uiState.destination}  ·  ${uiState.departureTime}",
                            color = Gold,
                            fontSize = 11.sp
                        )
                    }
                },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, null, tint = White)
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = Black)
            )
        },
        bottomBar = {
            BottomContinueBar(
                selectedLabel = uiState.selectedLabel,
                enabled = uiState.selectedSeatId != null,
                onClick = onContinueToPayment
            )
        },
        containerColor = Color(0xFFF5F6F8)
    ) { innerPadding ->

        LazyColumn(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize(),
            contentPadding = PaddingValues(bottom = 24.dp)
        ) {

            // ── Trip info card ─────────────────────────────────────────
            item {
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 20.dp, vertical = 16.dp),
                    shape = RoundedCornerShape(20.dp),
                    colors = CardDefaults.cardColors(containerColor = Color.Transparent),
                    elevation = CardDefaults.cardElevation(0.dp),
                    border = androidx.compose.foundation.BorderStroke(1.dp, Gold.copy(alpha = 0.4f))
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(Brush.linearGradient(listOf(Black, Charcoal)))
                            .padding(horizontal = 20.dp, vertical = 14.dp)
                    ) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            TripPillInfo(Icons.Default.DirectionsBus, uiState.company)
                            TripPillInfo(Icons.Default.AccessTime,    uiState.departureTime)
                            TripPillInfo(Icons.Default.AirlineSeatReclineNormal,
                                "${uiState.seats.count { it.status == SeatStatus.AVAILABLE }} libres")
                        }
                    }
                }
            }

            // ── Legend ────────────────────────────────────────────────
            item {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 20.dp),
                    horizontalArrangement = Arrangement.spacedBy(16.dp, Alignment.CenterHorizontally)
                ) {
                    SeatLegendItem(color = White, border = Color(0xFFDDDDDD), label = "Libre")
                    SeatLegendItem(color = Gold,  border = Gold,              label = "Seleccionado")
                    SeatLegendItem(color = Color(0xFFE0E0E0), border = Color.Transparent, label = "Ocupado")
                }
                Spacer(modifier = Modifier.height(16.dp))
            }

            // ── Bus body ──────────────────────────────────────────────
            item {
                BusLayout(
                    rows = rows,
                    onSeatClick = { viewModel.onSeatClick(it) }
                )
            }
        }
    }
}

// ── Bus layout ────────────────────────────────────────────────────────────────

@Composable
private fun BusLayout(
    rows: Map<Int, List<SeatItem>>,
    onSeatClick: (Int) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // ─── Bus front (driver cabin) ──────────────────────────────
        BusFront()
        Spacer(modifier = Modifier.height(4.dp))

        // ─── Column headers ────────────────────────────────────────
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 8.dp),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Left side labels: A B
            SeatColHeader("A")
            Spacer(modifier = Modifier.width(4.dp))
            SeatColHeader("B")
            // Aisle label
            Box(modifier = Modifier.width(40.dp), contentAlignment = Alignment.Center) {
                Text("🚶", fontSize = 14.sp)
            }
            // Right side labels: C D
            SeatColHeader("C")
            Spacer(modifier = Modifier.width(4.dp))
            SeatColHeader("D")
        }
        Spacer(modifier = Modifier.height(6.dp))

        // ─── Seat rows ──────────────────────────────────────────────
        rows.forEach { (rowNum, seatsInRow) ->
            val a = seatsInRow.find { it.col == "A" }
            val b = seatsInRow.find { it.col == "B" }
            val c = seatsInRow.find { it.col == "C" }
            val d = seatsInRow.find { it.col == "D" }

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 5.dp, horizontal = 8.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                // Col A (window left)
                a?.let {
                    SeatBox(
                        status = it.status,
                        label = it.label,
                        onClick = { onSeatClick(it.id) }
                    )
                }
                Spacer(modifier = Modifier.width(4.dp))
                // Col B (aisle left)
                b?.let {
                    SeatBox(
                        status = it.status,
                        label = it.label,
                        onClick = { onSeatClick(it.id) }
                    )
                }

                // Aisle with row number
                Box(
                    modifier = Modifier.width(40.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        rowNum.toString(),
                        fontSize = 10.sp,
                        color = Color(0xFFAAAAAA),
                        fontWeight = FontWeight.Bold
                    )
                }

                // Col C (aisle right)
                c?.let {
                    SeatBox(
                        status = it.status,
                        label = it.label,
                        onClick = { onSeatClick(it.id) }
                    )
                }
                Spacer(modifier = Modifier.width(4.dp))
                // Col D (window right)
                d?.let {
                    SeatBox(
                        status = it.status,
                        label = it.label,
                        onClick = { onSeatClick(it.id) }
                    )
                }
            }

            // Emergency exit indicator at row 5
            if (rowNum == 5) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 8.dp, vertical = 4.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    ExitDoor()
                    HorizontalDivider(
                        modifier = Modifier.weight(1f).padding(horizontal = 8.dp),
                        color = Color(0xFFE8E8E8),
                        thickness = 1.dp
                    )
                    ExitDoor()
                }
            }
        }

        // ── Bus back ───────────────────────────────────────────────
        Spacer(modifier = Modifier.height(8.dp))
        BusBack()
    }
}

// ── Sub-composables ───────────────────────────────────────────────────────────

@Composable
private fun BusFront() {
    Box(
        modifier = Modifier
            .width(220.dp)
            .height(60.dp)
            .clip(RoundedCornerShape(topStart = 40.dp, topEnd = 40.dp, bottomStart = 8.dp, bottomEnd = 8.dp))
            .background(Brush.verticalGradient(listOf(Black, Color(0xFF2C2C2C)))),
        contentAlignment = Alignment.Center
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Icon(Icons.Default.DirectionsBus, null, tint = Gold, modifier = Modifier.size(22.dp))
            Text("FRENTE DEL BUS", color = Gold, fontSize = 8.sp, fontWeight = FontWeight.ExtraBold, letterSpacing = 1.sp)
        }
        // Side mirrors
        Box(
            modifier = Modifier
                .size(width = 10.dp, height = 20.dp)
                .clip(RoundedCornerShape(3.dp))
                .background(Charcoal)
                .align(Alignment.CenterStart)
                .offset(x = (-8).dp)
        )
        Box(
            modifier = Modifier
                .size(width = 10.dp, height = 20.dp)
                .clip(RoundedCornerShape(3.dp))
                .background(Charcoal)
                .align(Alignment.CenterEnd)
                .offset(x = 8.dp)
        )
    }
}

@Composable
private fun BusBack() {
    Box(
        modifier = Modifier
            .width(220.dp)
            .height(30.dp)
            .clip(RoundedCornerShape(bottomStart = 16.dp, bottomEnd = 16.dp))
            .background(Brush.verticalGradient(listOf(Color(0xFF2C2C2C), Black))),
        contentAlignment = Alignment.Center
    ) {
        Text("PARTE TRASERA", color = Gold.copy(alpha = 0.6f), fontSize = 7.sp, fontWeight = FontWeight.ExtraBold, letterSpacing = 1.sp)
    }
}

@Composable
private fun SeatColHeader(label: String) {
    Box(modifier = Modifier.size(width = 36.dp, height = 20.dp), contentAlignment = Alignment.Center) {
        Text(label, fontSize = 11.sp, fontWeight = FontWeight.ExtraBold, color = Color(0xFF888888))
    }
}

@Composable
private fun ExitDoor() {
    Surface(
        color = Color(0xFFE53935).copy(alpha = 0.1f),
        shape = RoundedCornerShape(6.dp),
        border = androidx.compose.foundation.BorderStroke(1.dp, Color(0xFFE53935).copy(alpha = 0.4f))
    ) {
        Text(
            "🚪 SALIDA",
            color = Color(0xFFE53935),
            fontSize = 9.sp,
            fontWeight = FontWeight.ExtraBold,
            modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
        )
    }
}

@Composable
private fun SeatLegendItem(color: Color, border: Color, label: String) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Box(
            modifier = Modifier
                .size(18.dp)
                .clip(RoundedCornerShape(5.dp))
                .background(color)
                .then(
                    if (border != Color.Transparent)
                        Modifier.then(
                            androidx.compose.ui.draw.drawWithCache {
                                onDrawWithContent {
                                    drawContent()
                                }
                            }
                        )
                    else Modifier
                )
        ) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .clip(RoundedCornerShape(5.dp))
                    .background(color)
                    .padding(1.dp)
            )
        }
        Spacer(modifier = Modifier.width(6.dp))
        Text(label, fontSize = 12.sp, color = Color(0xFF555555), fontWeight = FontWeight.Medium)
    }
}

@Composable
private fun TripPillInfo(
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    text: String
) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Icon(icon, null, tint = Gold, modifier = Modifier.size(15.dp))
        Spacer(modifier = Modifier.width(5.dp))
        Text(text, color = White, fontSize = 12.sp, fontWeight = FontWeight.SemiBold)
    }
}

@Composable
private fun BottomContinueBar(
    selectedLabel: String?,
    enabled: Boolean,
    onClick: () -> Unit
) {
    Surface(
        modifier = Modifier.fillMaxWidth(),
        color = White,
        shadowElevation = 16.dp
    ) {
        Column(
            modifier = Modifier
                .padding(horizontal = 20.dp, vertical = 16.dp)
                .navigationBarsPadding()
        ) {
            if (selectedLabel != null) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(12.dp))
                        .background(Gold.copy(alpha = 0.1f))
                        .padding(horizontal = 16.dp, vertical = 10.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(Icons.Default.AirlineSeatReclineNormal, null, tint = Gold, modifier = Modifier.size(18.dp))
                        Spacer(modifier = Modifier.width(8.dp))
                        Text("Asiento seleccionado", fontSize = 13.sp, color = Color(0xFF555555))
                    }
                    Text(
                        selectedLabel,
                        fontWeight = FontWeight.ExtraBold,
                        fontSize = 20.sp,
                        color = Black
                    )
                }
                Spacer(modifier = Modifier.height(12.dp))
            }

            Button(
                onClick = onClick,
                enabled = enabled,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(52.dp),
                shape = RoundedCornerShape(16.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Black,
                    disabledContainerColor = Color(0xFFE0E0E0)
                )
            ) {
                Text(
                    if (enabled) "Continuar al pago" else "Selecciona un asiento",
                    color = if (enabled) Gold else Color.Gray,
                    fontWeight = FontWeight.ExtraBold,
                    fontSize = 16.sp
                )
            }
        }
    }
}
