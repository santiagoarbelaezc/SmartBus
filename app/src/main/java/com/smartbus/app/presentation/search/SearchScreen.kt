package com.smartbus.app.presentation.search

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.smartbus.app.R
import com.smartbus.app.ui.components.*
import com.smartbus.app.ui.theme.Black
import com.smartbus.app.ui.theme.Gold
import com.smartbus.app.ui.theme.White

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchScreen(
    viewModel: SearchViewModel,
    onNavigateBack: () -> Unit,
    onResultSelected: () -> Unit
) {
    val uiState by viewModel.uiState.collectAsState()
    var isSearching by remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Buscar Viaje", color = Gold, fontWeight = FontWeight.Bold) },
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
        Box(modifier = Modifier.fillMaxSize()) {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding),
                contentPadding = PaddingValues(bottom = 32.dp)
            ) {
                // Search Header with Card
                item {
                    Box(modifier = Modifier.fillMaxWidth()) {
                        Box(modifier = Modifier.fillMaxWidth().height(80.dp).background(Black))
                        
                        Card(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 20.dp)
                                .padding(top = 10.dp),
                            shape = RoundedCornerShape(24.dp),
                            colors = CardDefaults.cardColors(containerColor = White),
                            elevation = CardDefaults.cardElevation(defaultElevation = 10.dp)
                        ) {
                            Column(modifier = Modifier.padding(20.dp)) {
                                // Origin & Destination UI
                                SearchInputField(
                                    value = uiState.origin,
                                    onValueChange = viewModel::onOriginChange,
                                    label = "Origen",
                                    icon = Icons.Default.TripOrigin,
                                    iconColor = Gold
                                )
                                
                                Box(modifier = Modifier.fillMaxWidth().height(40.dp), contentAlignment = Alignment.CenterStart) {
                                    VerticalDivider(modifier = Modifier.padding(start = 22.dp).height(40.dp), color = Gold.copy(alpha = 0.2f))
                                    IconButton(
                                        onClick = { /* Swap */ },
                                        modifier = Modifier.padding(start = 8.dp).size(32.dp).background(White, RoundedCornerShape(8.dp))
                                    ) {
                                        Icon(Icons.Default.SwapVert, contentDescription = null, tint = Gold, modifier = Modifier.size(20.dp))
                                    }
                                }

                                SearchInputField(
                                    value = uiState.destination,
                                    onValueChange = viewModel::onDestinationChange,
                                    label = "Destino",
                                    icon = Icons.Default.LocationOn,
                                    iconColor = Black
                                )

                                Spacer(modifier = Modifier.height(20.dp))

                                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                                    SearchInfoBadge(icon = Icons.Default.CalendarToday, text = "24 Oct", modifier = Modifier.weight(1f))
                                    SearchInfoBadge(icon = Icons.Default.Group, text = "1 Per.", modifier = Modifier.weight(1f))
                                }

                                Spacer(modifier = Modifier.height(24.dp))

                                SmartBusButton(
                                    text = "Buscar Rutas",
                                    onClick = { 
                                        isSearching = true
                                        // Mock delay would happen in VM, but for UI feedback:
                                    },
                                    modifier = Modifier.fillMaxWidth().height(52.dp)
                                )
                            }
                        }
                    }
                }

                // Results Header
                item {
                    Spacer(modifier = Modifier.height(28.dp))
                    Row(
                        modifier = Modifier.fillMaxWidth().padding(horizontal = 24.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "Resultados Disponibles",
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.ExtraBold,
                            color = Black
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Surface(
                            color = Gold.copy(alpha = 0.2f),
                            shape = RoundedCornerShape(8.dp)
                        ) {
                            Text(
                                uiState.results.size.toString(),
                                modifier = Modifier.padding(horizontal = 8.dp, vertical = 2.dp),
                                color = Gold,
                                fontWeight = FontWeight.Bold,
                                fontSize = 12.sp
                            )
                        }
                    }
                    Spacer(modifier = Modifier.height(16.dp))
                }

                // Filters
                item {
                    LazyRow(
                        contentPadding = PaddingValues(horizontal = 24.dp),
                        horizontalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        item { SearchFilterChip(text = "Económico", selected = true) }
                        item { SearchFilterChip(text = "Rápido", selected = false) }
                        item { SearchFilterChip(text = "Mañana", selected = false) }
                    }
                    Spacer(modifier = Modifier.height(16.dp))
                }

                // Results list
                items(uiState.results) { result ->
                    Box(modifier = Modifier.padding(horizontal = 20.dp, vertical = 8.dp)) {
                        TripResultCard(result = result, onClick = onResultSelected)
                    }
                }
            }

            // High-fidelity Loading Overlay
            if (isSearching) {
                SmartBusLoadingOverlay()
                LaunchedEffect(Unit) {
                    kotlinx.coroutines.delay(1500)
                    isSearching = false
                }
            }
        }
    }
}

@Composable
fun SearchInputField(value: String, onValueChange: (String) -> Unit, label: String, icon: androidx.compose.ui.graphics.vector.ImageVector, iconColor: Color) {
    Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.fillMaxWidth()) {
        Icon(icon, contentDescription = null, tint = iconColor, modifier = Modifier.size(24.dp))
        Spacer(modifier = Modifier.width(16.dp))
        TextField(
            value = value,
            onValueChange = onValueChange,
            placeholder = { Text(label, fontSize = 16.sp, color = Color.Gray) },
            colors = TextFieldDefaults.colors(
                unfocusedContainerColor = Color.Transparent,
                focusedContainerColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                focusedIndicatorColor = Color.Transparent
            ),
            modifier = Modifier.weight(1f)
        )
    }
}

@Composable
fun SearchInfoBadge(icon: androidx.compose.ui.graphics.vector.ImageVector, text: String, modifier: Modifier = Modifier) {
    Surface(
        modifier = modifier,
        color = Color.LightGray.copy(alpha = 0.1f),
        shape = RoundedCornerShape(12.dp),
        border = androidx.compose.foundation.BorderStroke(1.dp, Color.LightGray.copy(alpha = 0.2f))
    ) {
        Row(
            modifier = Modifier.padding(vertical = 10.dp, horizontal = 12.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            Icon(icon, contentDescription = null, tint = Gold, modifier = Modifier.size(16.dp))
            Spacer(modifier = Modifier.width(8.dp))
            Text(text, fontSize = 13.sp, fontWeight = FontWeight.SemiBold)
        }
    }
}

@Composable
fun SearchFilterChip(text: String, selected: Boolean) {
    Surface(
        color = if (selected) Black else White,
        shape = RoundedCornerShape(12.dp),
        border = androidx.compose.foundation.BorderStroke(1.dp, if (selected) Black else Color.LightGray.copy(alpha = 0.4f)),
        onClick = {}
    ) {
        Text(
            text = text,
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp),
            color = if (selected) Gold else Color.Gray,
            fontWeight = if (selected) FontWeight.Bold else FontWeight.Normal,
            fontSize = 13.sp
        )
    }
}
