package com.smartbus.app.presentation.seatselection

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.smartbus.app.R
import com.smartbus.app.ui.components.SeatBox
import com.smartbus.app.ui.components.SeatStatus
import com.smartbus.app.ui.components.SmartBusButton
import com.smartbus.app.ui.theme.Black
import com.smartbus.app.ui.theme.Gold
import com.smartbus.app.ui.theme.White

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SeatSelectionScreen(
    viewModel: SeatSelectionViewModel,
    onNavigateBack: () -> Unit,
    onContinueToPayment: () -> Unit
) {
    val uiState by viewModel.uiState.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Selecciona tu asiento", style = MaterialTheme.typography.headlineMedium) },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(Icons.Default.ArrowBack, contentDescription = null)
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = Color.Transparent)
            )
        },
        bottomBar = {
            Surface(
                modifier = Modifier.fillMaxWidth(),
                tonalElevation = 8.dp,
                shadowElevation = 16.dp,
                color = White
            ) {
                Column(
                    modifier = Modifier
                        .padding(24.dp)
                        .navigationBarsPadding()
                ) {
                    uiState.selectedSeatId?.let { id ->
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text("Asiento seleccionado:", style = MaterialTheme.typography.bodyMedium)
                            Text("#$id", fontWeight = FontWeight.Bold, color = Gold, fontSize = 20.sp)
                        }
                        Spacer(modifier = Modifier.height(16.dp))
                    }
                    
                    SmartBusButton(
                        text = stringResource(R.string.continue_to_payment),
                        onClick = onContinueToPayment,
                        enabled = uiState.selectedSeatId != null
                    )
                }
            }
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
                .padding(horizontal = 24.dp)
        ) {
            Text(
                uiState.routeSummary,
                style = MaterialTheme.typography.labelLarge,
                modifier = Modifier.padding(vertical = 16.dp)
            )

            // ── Route image ───────────────────────────────────────────
            val context = androidx.compose.ui.platform.LocalContext.current
            val routeBitmap = remember {
                android.graphics.BitmapFactory.decodeStream(
                    context.resources.openRawResource(R.raw.ruta)
                )?.asImageBitmap()
            }
            routeBitmap?.let { bmp ->
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(160.dp)
                        .padding(bottom = 16.dp),
                    shape = RoundedCornerShape(16.dp),
                    elevation = CardDefaults.cardElevation(4.dp)
                ) {
                    androidx.compose.foundation.Image(
                        bitmap = bmp,
                        contentDescription = "Trayecto de la ruta",
                        contentScale = ContentScale.Crop,
                        modifier = Modifier.fillMaxSize()
                    )
                }
            }

            // Bus Grid
            LazyVerticalGrid(
                columns = GridCells.Fixed(4),
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp),
                modifier = Modifier.weight(1f)
            ) {
                items(uiState.seats) { seat ->
                    // Add aisle space
                    val isRightSide = seat.id % 4 == 0 || (seat.id - 3) % 4 == 0
                    Row {
                        SeatBox(
                            status = seat.status,
                            onClick = { viewModel.onSeatClick(seat.id) }
                        )
                        if (seat.id % 4 == 2) {
                            Spacer(modifier = Modifier.width(24.dp))
                        }
                    }
                }
            }

            // Legend
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 24.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                LegendItem("Libre", SeatStatus.AVAILABLE)
                LegendItem("Seleccionado", SeatStatus.SELECTED)
                LegendItem("Ocupado", SeatStatus.OCCUPIED)
            }
        }
    }
}

@Composable
fun LegendItem(label: String, status: SeatStatus) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        SeatBox(status = status, onClick = {}, modifier = Modifier.size(16.dp))
        Spacer(modifier = Modifier.width(8.dp))
        Text(label, fontSize = 12.sp)
    }
}

