package com.smartbus.app.presentation.search

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
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
import com.smartbus.app.ui.components.DashedDivider
import com.smartbus.app.ui.components.SmartBusButton
import com.smartbus.app.ui.components.SmartBusCard
import com.smartbus.app.ui.components.TripResultCard
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

    // Using Scaffold to handle TopAppBar and prevent overlaps
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(stringResource(R.string.where_to_go), color = Gold, style = MaterialTheme.typography.titleLarge) },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(Icons.Default.ArrowBack, contentDescription = null, tint = White)
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = Black)
            )
        },
        containerColor = White
    ) { innerPadding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
            contentPadding = PaddingValues(bottom = 32.dp)
        ) {
            // Header & Search Card Item
            item {
                Box(modifier = Modifier.fillMaxWidth()) {
                    // Header background extension
                    Box(modifier = Modifier.fillMaxWidth().height(100.dp).background(Black))
                    
                    // Search Card
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 24.dp)
                            .padding(top = 20.dp),
                        shape = RoundedCornerShape(20.dp),
                        colors = CardDefaults.cardColors(containerColor = White),
                        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
                    ) {
                        Column(modifier = Modifier.padding(20.dp)) {
                            // Origin
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Icon(Icons.Default.Circle, contentDescription = null, tint = Gold, modifier = Modifier.size(12.dp))
                                Spacer(modifier = Modifier.width(12.dp))
                                TextField(
                                    value = uiState.origin,
                                    onValueChange = viewModel::onOriginChange,
                                    placeholder = { Text(stringResource(R.string.origin_city)) },
                                    colors = TextFieldDefaults.colors(
                                        unfocusedContainerColor = Color.Transparent,
                                        focusedContainerColor = Color.Transparent,
                                        unfocusedIndicatorColor = Color.Transparent,
                                        focusedIndicatorColor = Color.Transparent
                                    ),
                                    modifier = Modifier.weight(1f)
                                )
                            }

                            // Dashed Line with Swap Button
                            Row(
                                modifier = Modifier.fillMaxWidth().height(24.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Box(modifier = Modifier.width(12.dp).fillMaxHeight(), contentAlignment = Alignment.Center) {
                                    DashedDivider(
                                        modifier = Modifier.fillMaxHeight().width(1.dp).offset(x = 5.dp),
                                        color = Gold.copy(alpha = 0.3f)
                                    )
                                }
                                Spacer(modifier = Modifier.weight(1f))
                                IconButton(onClick = { /* Swap */ }, modifier = Modifier.size(32.dp)) {
                                    Icon(Icons.Default.SwapVert, contentDescription = "Swap", tint = Gold)
                                }
                            }

                            // Destination
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Icon(Icons.Default.Circle, contentDescription = null, tint = Black, modifier = Modifier.size(12.dp))
                                Spacer(modifier = Modifier.width(12.dp))
                                TextField(
                                    value = uiState.destination,
                                    onValueChange = viewModel::onDestinationChange,
                                    placeholder = { Text(stringResource(R.string.destination_city)) },
                                    colors = TextFieldDefaults.colors(
                                        unfocusedContainerColor = Color.Transparent,
                                        focusedContainerColor = Color.Transparent,
                                        unfocusedIndicatorColor = Color.Transparent,
                                        focusedIndicatorColor = Color.Transparent
                                    ),
                                    modifier = Modifier.weight(1f)
                                )
                            }

                            HorizontalDivider(modifier = Modifier.padding(vertical = 12.dp), color = Color.LightGray.copy(alpha = 0.2f))

                            // Date and Passengers
                            Row(modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
                                Row(modifier = Modifier.weight(1f), verticalAlignment = Alignment.CenterVertically) {
                                    Icon(Icons.Default.CalendarMonth, contentDescription = null, tint = Gold, modifier = Modifier.size(20.dp))
                                    Spacer(modifier = Modifier.width(8.dp))
                                    Text("24 Oct, 2024", fontSize = 14.sp)
                                }
                                VerticalDivider(modifier = Modifier.height(24.dp).padding(horizontal = 12.dp))
                                Row(modifier = Modifier.weight(1f), verticalAlignment = Alignment.CenterVertically) {
                                    Icon(Icons.Default.Person, contentDescription = null, tint = Gold, modifier = Modifier.size(20.dp))
                                    Spacer(modifier = Modifier.width(8.dp))
                                    Text("1 Pasajero", fontSize = 14.sp)
                                }
                            }

                            Spacer(modifier = Modifier.height(24.dp))

                            SmartBusButton(
                                text = "Buscar",
                                onClick = { /* Search */ },
                                modifier = Modifier.fillMaxWidth()
                            )
                        }
                    }
                }
            }

            // Results Section Label & Count
            item {
                Spacer(modifier = Modifier.height(32.dp))
                Row(
                    modifier = Modifier.fillMaxWidth().padding(horizontal = 24.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = stringResource(R.string.available_results),
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                        color = Black
                    )
                    Badge(containerColor = Gold, contentColor = Black) {
                        Text(uiState.results.size.toString(), modifier = Modifier.padding(horizontal = 4.dp))
                    }
                }
                Spacer(modifier = Modifier.height(16.dp))
            }

            // Filter Row
            item {
                LazyRow(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    contentPadding = PaddingValues(horizontal = 24.dp)
                ) {
                    item { FilterChip(selected = true, onClick = {}, label = { Text(stringResource(R.string.filter_cheapest)) }) }
                    item { FilterChip(selected = false, onClick = {}, label = { Text(stringResource(R.string.filter_fastest)) }) }
                    item { FilterChip(selected = false, onClick = {}, label = { Text(stringResource(R.string.filter_available)) }) }
                }
                Spacer(modifier = Modifier.height(16.dp))
            }

            // Actual Results
            items(uiState.results) { result ->
                Box(modifier = Modifier.padding(horizontal = 24.dp, vertical = 6.dp)) {
                    TripResultCard(result = result, onClick = onResultSelected)
                }
            }
        }
    }
}
