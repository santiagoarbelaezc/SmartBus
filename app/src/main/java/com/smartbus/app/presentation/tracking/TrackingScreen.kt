package com.smartbus.app.presentation.tracking

import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat
import android.graphics.Bitmap
import android.graphics.Canvas
import com.google.android.gms.maps.model.BitmapDescriptor
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MapStyleOptions
import com.google.maps.android.compose.*
import com.smartbus.app.R
import com.smartbus.app.ui.components.StatusChip
import com.smartbus.app.ui.theme.Black
import com.smartbus.app.ui.theme.Charcoal
import com.smartbus.app.ui.theme.Gold
import com.smartbus.app.ui.theme.White

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TrackingScreen(
    viewModel: TrackingViewModel,
    onNavigateBack: () -> Unit
) {
    val uiState by viewModel.uiState.collectAsState()
    val context = LocalContext.current
    
    val cameraPositionState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(LatLng(4.7110, -74.0721), 13f)
    }

    val scaffoldState = rememberBottomSheetScaffoldState()

    BottomSheetScaffold(
        scaffoldState = scaffoldState,
        sheetPeekHeight = 160.dp,
        sheetShape = RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp),
        sheetContainerColor = Charcoal,
        sheetContent = {
            TrackingBottomSheetContent(uiState)
        },
        topBar = {
            TopAppBar(
                title = { Text(stringResource(R.string.tracking), color = White) },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(Icons.Default.ArrowBack, contentDescription = null, tint = White)
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = Black.copy(alpha = 0.5f))
            )
        }
    ) { innerPadding ->
        Box(modifier = Modifier.padding(innerPadding).fillMaxSize()) {
            GoogleMap(
                modifier = Modifier.fillMaxSize(),
                cameraPositionState = cameraPositionState,
                properties = MapProperties(
                    mapStyleOptions = MapStyleOptions.loadRawResourceStyle(context, R.raw.dark_map_style)
                ),
                uiSettings = MapUiSettings(zoomControlsEnabled = false)
            ) {
                // Bus Marker
                val busIcon = bitmapDescriptorFromVector(context, R.drawable.ic_bus_marker)
                Marker(
                    state = MarkerState(position = LatLng(4.7110, -74.0721)),
                    title = "SmartBus #247",
                    icon = busIcon
                )

                // Polyline Route
                Polyline(
                    points = listOf(
                        LatLng(4.7110, -74.0721),
                        LatLng(4.7150, -74.0750),
                        LatLng(4.7200, -74.0800),
                        LatLng(4.7250, -74.0850)
                    ),
                    color = Gold,
                    width = 8f
                )
            }
        }
    }
}

@Composable
fun TrackingBottomSheetContent(uiState: TrackingUiState) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(24.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column {
                Text(
                    text = "${uiState.origin} → ${uiState.destination}",
                    style = MaterialTheme.typography.titleLarge,
                    color = White,
                    fontWeight = FontWeight.Bold
                )
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(Icons.Default.Schedule, null, Modifier.size(16.dp), Gold)
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        text = "Llega en ${uiState.eta}",
                        color = Gold,
                        fontSize = 14.sp
                    )
                }
            }
            StatusChip(text = uiState.status)
        }

        Spacer(modifier = Modifier.height(24.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceAround
        ) {
            TrackingInfoItem(Icons.Default.Speed, "62 km/h", "Velocidad")
            TrackingInfoItem(Icons.Default.Person, "Carlos R.", "Conductor")
            TrackingInfoItem(Icons.Default.Star, "4.8", "Calificación")
        }

        Spacer(modifier = Modifier.height(24.dp))
        
        Text("Detalles del Vehículo", color = White.copy(alpha = 0.5f), style = MaterialTheme.typography.labelSmall)
        Text("Placa: ${uiState.busPlate} • Aire Acondicionado • WiFi Gratis", color = White, fontSize = 14.sp)
        
        Spacer(modifier = Modifier.height(16.dp))
    }
}

@Composable
fun TrackingInfoItem(icon: androidx.compose.ui.graphics.vector.ImageVector, value: String, label: String) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Icon(icon, null, Modifier.size(24.dp), Gold)
        Text(value, color = White, fontWeight = FontWeight.Bold, fontSize = 16.sp)
        Text(label, color = White.copy(alpha = 0.5f), fontSize = 10.sp)
    }
}

fun bitmapDescriptorFromVector(context: android.content.Context, vectorResId: Int): BitmapDescriptor? {
    return ContextCompat.getDrawable(context, vectorResId)?.run {
        setBounds(0, 0, intrinsicWidth, intrinsicHeight)
        val bitmap = Bitmap.createBitmap(intrinsicWidth, intrinsicHeight, Bitmap.Config.ARGB_8888)
        draw(Canvas(bitmap))
        BitmapDescriptorFactory.fromBitmap(bitmap)
    }
}
