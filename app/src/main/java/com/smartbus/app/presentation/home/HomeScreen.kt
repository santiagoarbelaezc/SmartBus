package com.smartbus.app.presentation.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.smartbus.app.R
import com.smartbus.app.ui.components.SmartBusCard
import com.smartbus.app.ui.theme.Black
import com.smartbus.app.ui.theme.Gold
import com.smartbus.app.ui.theme.TextPrimary
import com.smartbus.app.ui.theme.White

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    viewModel: HomeViewModel,
    onNavigateToSearch: () -> Unit,
    onNavigateToTickets: () -> Unit,
    onNavigateToTracking: () -> Unit,
    onNavigateToPoints: () -> Unit
) {
    val uiState by viewModel.uiState.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        stringResource(R.string.greeting_hola, uiState.userName),
                        style = MaterialTheme.typography.headlineMedium
                    )
                },
                actions = {
                    Box(
                        modifier = Modifier
                            .padding(end = 16.dp)
                            .size(40.dp)
                            .clip(CircleShape)
                            .background(Gold)
                            .padding(2.dp)
                            .clip(CircleShape)
                            .background(White)
                    ) {
                        Icon(
                            Icons.Default.Person,
                            contentDescription = "Profile",
                            tint = Black,
                            modifier = Modifier.align(Alignment.Center)
                        )
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
            
            // Next Trip Card
            Text(
                stringResource(R.string.next_trip),
                style = MaterialTheme.typography.labelLarge,
                modifier = Modifier.padding(bottom = 8.dp)
            )
            
            uiState.nextTrip?.let { trip ->
                SmartBusCard(modifier = Modifier.fillMaxWidth()) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column {
                            Text(
                                "${trip.origin} → ${trip.destination}",
                                style = MaterialTheme.typography.titleLarge,
                                fontWeight = FontWeight.Bold
                            )
                            Text(
                                "${trip.date} • ${trip.time}",
                                style = MaterialTheme.typography.bodyLarge
                            )
                        }
                        Icon(
                            Icons.Default.ChevronRight,
                            contentDescription = null,
                            tint = Gold
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(32.dp))

            // Quick Access Grid
            val menuItems = listOf(
                Pair(Icons.Default.Search, stringResource(R.string.search_route)),
                Pair(Icons.Default.ConfirmationNumber, stringResource(R.string.my_tickets)),
                Pair(Icons.Default.MyLocation, stringResource(R.string.tracking)),
                Pair(Icons.Default.CardGiftcard, stringResource(R.string.points))
            )

            LazyVerticalGrid(
                columns = GridCells.Fixed(2),
                horizontalArrangement = Arrangement.spacedBy(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp),
                modifier = Modifier.height(200.dp)
            ) {
                items(menuItems) { item ->
                    Card(
                        onClick = {
                            when(item.second) {
                                menuItems[0].second -> onNavigateToSearch()
                                menuItems[1].second -> onNavigateToTickets()
                                menuItems[2].second -> onNavigateToTracking()
                                menuItems[3].second -> onNavigateToPoints()
                            }
                        },
                        modifier = Modifier.height(90.dp),
                        shape = RoundedCornerShape(16.dp),
                        colors = CardDefaults.cardColors(containerColor = Black)
                    ) {
                        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                Icon(item.first, contentDescription = null, tint = Gold)
                                Text(item.second, color = Gold, fontSize = 12.sp, fontWeight = FontWeight.Medium)
                            }
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(32.dp))

            // Promo Banner
            Text(
                "Promociones Exclusivas",
                style = MaterialTheme.typography.labelLarge,
                modifier = Modifier.padding(bottom = 8.dp)
            )
            
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(110.dp),
                colors = CardDefaults.cardColors(containerColor = Gold.copy(alpha = 0.1f)),
                border = androidx.compose.foundation.BorderStroke(1.dp, Gold)
            ) {
                Column(
                    modifier = Modifier.padding(16.dp)
                ) {
                    Text(
                        uiState.promotions.first().title,
                        fontWeight = FontWeight.Bold,
                        color = Black
                    )
                    Text(
                        uiState.promotions.first().description,
                        fontSize = 14.sp
                    )
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            // Trusted Partners (New Section)
            Text(
                "Empresas Aliadas",
                style = MaterialTheme.typography.labelLarge,
                modifier = Modifier.padding(bottom = 8.dp)
            )
            
            androidx.compose.foundation.lazy.LazyRow(
                horizontalArrangement = Arrangement.spacedBy(16.dp),
                contentPadding = PaddingValues(bottom = 24.dp)
            ) {
                val partners = listOf(
                    R.raw.busbolivariano,
                    R.raw.busflota,
                    R.raw.busgacela,
                    R.raw.smartbus
                )
                items(partners) { partnerImg ->
                    Surface(
                        modifier = Modifier.size(60.dp),
                        shape = RoundedCornerShape(12.dp),
                        color = White,
                        shadowElevation = 2.dp
                    ) {
                        androidx.compose.foundation.Image(
                            painter = androidx.compose.ui.res.painterResource(id = partnerImg),
                            contentDescription = null,
                            modifier = Modifier.fillMaxSize(),
                            contentScale = androidx.compose.ui.layout.ContentScale.Crop
                        )
                    }
                }
            }
        }
    }
}
