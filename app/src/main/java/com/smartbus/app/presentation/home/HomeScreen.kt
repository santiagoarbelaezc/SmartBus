package com.smartbus.app.presentation.home

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.*
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.smartbus.app.R
import com.smartbus.app.core.AppLanguage
import com.smartbus.app.core.LanguageManager
import com.smartbus.app.ui.components.SmartBusCard
import com.smartbus.app.ui.theme.Black
import com.smartbus.app.ui.theme.Charcoal
import com.smartbus.app.ui.theme.Gold
import com.smartbus.app.ui.theme.GoldDark
import com.smartbus.app.ui.theme.White
import java.util.Calendar

// ── Static feed data ──────────────────────────────────────────────────────────

private data class TipItem(val icon: ImageVector, val iconBg: Color, val title: String, val body: String, val tag: String)
private data class StatItem(val icon: ImageVector, val value: String, val label: String)

private val busTips = listOf(
    TipItem(Icons.Default.Schedule, Color(0xFF0D47A1), "Llega 10 min antes", "Los buses salen puntual. Planea con tiempo y evita perder tu tiquete.", "Consejo"),
    TipItem(Icons.Default.QrCode,   Color(0xFF1B5E20), "Muestra tu QR", "Asegúrate de tener el QR descargado; algunas terminales no tienen señal.", "Recordatorio"),
    TipItem(Icons.Default.AirlineSeatReclineNormal, Color(0xFF6A1B9A), "Asientos delanteros", "Los primeros asientos son más estables y se bajan más rápido.", "Tip"),
    TipItem(Icons.Default.Luggage,  Color(0xFFE65100), "Equipaje de mano", "Máx. 10 kg en bodega gratis. Excesos se cobran en taquilla.", "Info"),
    TipItem(Icons.Default.NightsStay, Color(0xFF37474F), "Viajes nocturnos", "Lleva una chaqueta ligera; el A/C en viajes nocturnos puede ser intenso.", "Consejo"),
    TipItem(Icons.Default.Usb,      Color(0xFF006064), "Carga tu celular", "Muchos buses Premier tienen USB en cada asiento. ¡Aprovéchalo!", "Tip"),
)

private val statsRow = listOf(
    StatItem(Icons.Default.DirectionsBus, "1,240+", "Rutas activas"),
    StatItem(Icons.Default.People,        "320K+",  "Viajeros / mes"),
    StatItem(Icons.Default.Star,          "4.8",    "Calificación"),
    StatItem(Icons.Default.Shield,        "99.2%",  "Puntualidad"),
)

// ── Screen ────────────────────────────────────────────────────────────────────

@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@Composable
fun HomeScreen(
    viewModel: HomeViewModel,
    onNavigateToSearch: () -> Unit,
    onNavigateToTickets: () -> Unit,
    onNavigateToTracking: () -> Unit,
    onNavigateToPoints: () -> Unit,
    onNavigateToNFC: () -> Unit,
    onNavigateToProfile: () -> Unit = {}
) {
    val uiState by viewModel.uiState.collectAsState()
    val currentLang by LanguageManager.currentLanguage.collectAsState()
    val isEn = currentLang == AppLanguage.ENGLISH
    val filteredRoutes = viewModel.filteredRoutes()

    var showRouteDialog by remember { mutableStateOf(false) }
    var showTrackingDialog by remember { mutableStateOf(false) }

    val hour = Calendar.getInstance().get(Calendar.HOUR_OF_DAY)
    val greeting = when {
        hour < 12 -> if (isEn) "Good morning ☀️" else "Buenos días ☀️"
        hour < 18 -> if (isEn) "Good afternoon 🌤️" else "Buenas tardes 🌤️"
        else      -> if (isEn) "Good evening 🌙" else "Buenas noches 🌙"
    }

    Scaffold(
        floatingActionButton = {
            FloatingActionButton(
                onClick = onNavigateToNFC,
                containerColor = Black,
                contentColor = Gold,
                shape = CircleShape,
                elevation = FloatingActionButtonDefaults.elevation(8.dp)
            ) {
                Icon(Icons.Default.Nfc, contentDescription = "Pago NFC", modifier = Modifier.size(28.dp))
            }
        },
        topBar = {
            TopAppBar(
                title = {
                    Column {
                        Text(greeting, style = MaterialTheme.typography.labelMedium, color = Color.Gray)
                        Text(
                            uiState.userName,
                            style = MaterialTheme.typography.headlineSmall,
                            fontWeight = FontWeight.Bold
                        )
                    }
                },
                actions = {
                    // Gold level badge
                    Surface(
                        shape = RoundedCornerShape(20.dp),
                        color = Gold.copy(alpha = 0.15f),
                        modifier = Modifier
                            .padding(end = 8.dp)
                            .clickable { onNavigateToProfile() }
                    ) {
                        Row(
                            modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(Icons.Default.Stars, contentDescription = null, tint = Gold, modifier = Modifier.size(14.dp))
                            Spacer(modifier = Modifier.width(4.dp))
                            Text("Oro", color = Gold, fontSize = 12.sp, fontWeight = FontWeight.SemiBold)
                        }
                    }
                    // Avatar — tapping navigates to profile
                    Box(
                        modifier = Modifier
                            .padding(end = 16.dp)
                            .size(40.dp)
                            .clip(CircleShape)
                            .background(Gold)
                            .clickable { onNavigateToProfile() }
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
        LazyColumn(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize(),
            contentPadding = PaddingValues(bottom = 120.dp)
        ) {

            // ── 1. Search bar ─────────────────────────────────────────
            item {
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 20.dp)
                        .clickable { onNavigateToSearch() },
                    shape = RoundedCornerShape(18.dp),
                    colors = CardDefaults.cardColors(containerColor = Black),
                    elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 20.dp, vertical = 16.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Box(
                                modifier = Modifier
                                    .size(40.dp)
                                    .clip(RoundedCornerShape(12.dp))
                                    .background(Gold.copy(alpha = 0.15f)),
                                contentAlignment = Alignment.Center
                            ) {
                                Icon(Icons.Default.Search, null, tint = Gold, modifier = Modifier.size(20.dp))
                            }
                            Spacer(modifier = Modifier.width(14.dp))
                            Column {
                                Text(
                                    if (isEn) "Where are you going?" else "¿A dónde vas hoy?",
                                    color = White,
                                    fontWeight = FontWeight.SemiBold,
                                    fontSize = 15.sp
                                )
                                Text(
                                    if (isEn) "Tap to search routes" else "Toca para buscar rutas",
                                    color = White.copy(alpha = 0.45f),
                                    fontSize = 12.sp
                                )
                            }
                        }
                        Icon(Icons.Default.ArrowForwardIos, null, tint = Gold, modifier = Modifier.size(15.dp))
                    }
                }
                Spacer(modifier = Modifier.height(24.dp))
            }

            // ── 2. Quick Access ───────────────────────────────────────
            item {
                val menuItems = listOf(
                    Triple(Icons.Default.Search,              if (isEn) "Search"   else "Buscar",   onNavigateToSearch),
                    Triple(Icons.Default.ConfirmationNumber,  if (isEn) "Tickets"  else "Tiquetes", onNavigateToTickets),
                    Triple(Icons.Default.MyLocation,          if (isEn) "Tracking" else "Rastreo",  onNavigateToTracking),
                    Triple(Icons.Default.CardGiftcard,        if (isEn) "Points"   else "Puntos",   onNavigateToPoints)
                )
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 20.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    menuItems.forEach { (icon, label, action) ->
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally,
                            modifier = Modifier
                                .clip(RoundedCornerShape(14.dp))
                                .clickable { action() }
                                .padding(8.dp)
                        ) {
                            Box(
                                modifier = Modifier
                                    .size(56.dp)
                                    .clip(RoundedCornerShape(18.dp))
                                    .background(
                                        Brush.verticalGradient(
                                            listOf(Charcoal, Black)
                                        )
                                    ),
                                contentAlignment = Alignment.Center
                            ) {
                                Icon(icon, null, tint = Gold, modifier = Modifier.size(26.dp))
                            }
                            Spacer(modifier = Modifier.height(6.dp))
                            Text(
                                label,
                                fontSize = 11.sp,
                                fontWeight = FontWeight.Medium,
                                maxLines = 1,
                                overflow = TextOverflow.Ellipsis,
                                color = Black
                            )
                        }
                    }
                }
                Spacer(modifier = Modifier.height(28.dp))
            }

            // ── 3. Current trip (dynamic) ─────────────────────────────
            item {
                if (uiState.currentTrip != null) {
                    val trip = uiState.currentTrip!!
                    SectionHeader(
                        title = if (isEn) "Current Trip" else "Viaje Actual",
                        modifier = Modifier.padding(horizontal = 20.dp)
                    )
                    Spacer(modifier = Modifier.height(10.dp))

                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 20.dp),
                        shape = RoundedCornerShape(24.dp),
                        colors = CardDefaults.cardColors(containerColor = Charcoal),
                        elevation = CardDefaults.cardElevation(defaultElevation = 12.dp)
                    ) {
                        Column(modifier = Modifier.padding(24.dp)) {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Column {
                                    Row(verticalAlignment = Alignment.CenterVertically) {
                                        PulsingDot(color = Gold)
                                        Spacer(modifier = Modifier.width(8.dp))
                                        Text(
                                            "EN CURSO",
                                            color = Gold,
                                            fontSize = 11.sp,
                                            fontWeight = FontWeight.ExtraBold,
                                            letterSpacing = 1.sp
                                        )
                                    }
                                    Spacer(modifier = Modifier.height(6.dp))
                                    Text(
                                        "${trip.origin} → ${trip.destination}",
                                        color = White,
                                        fontWeight = FontWeight.ExtraBold,
                                        fontSize = 22.sp
                                    )
                                }
                                IconButton(
                                    onClick = { showTrackingDialog = true },
                                    modifier = Modifier
                                        .size(44.dp)
                                        .clip(CircleShape)
                                        .background(Gold.copy(alpha = 0.2f))
                                ) {
                                    Icon(Icons.Default.MyLocation, null, tint = Gold, modifier = Modifier.size(24.dp))
                                }
                            }
                            
                            Spacer(modifier = Modifier.height(20.dp))
                            HorizontalDivider(color = White.copy(alpha = 0.1f))
                            Spacer(modifier = Modifier.height(20.dp))
                            
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Column {
                                    Text("Llegada estimada", color = White.copy(alpha = 0.5f), fontSize = 11.sp)
                                    Text("12:45 PM", color = White, fontWeight = FontWeight.Bold, fontSize = 15.sp)
                                }
                                
                                Button(
                                    onClick = { showTrackingDialog = true },
                                    shape = RoundedCornerShape(12.dp),
                                    colors = ButtonDefaults.buttonColors(containerColor = Gold),
                                    contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp)
                                ) {
                                    Text("Sigue tu bus", color = Black, fontWeight = FontWeight.ExtraBold, fontSize = 12.sp)
                                }
                            }
                        }
                    }
                    Spacer(modifier = Modifier.height(28.dp))
                }
            }

            // ── 4. Next trip ──────────────────────────────────────────
            item {
                SectionHeader(
                    title = if (isEn) "Next Trip" else "Próximo Viaje",
                    modifier = Modifier.padding(horizontal = 20.dp)
                )
                Spacer(modifier = Modifier.height(10.dp))

                if (uiState.nextTrip != null) {
                    val trip = uiState.nextTrip!!
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 20.dp),
                        shape = RoundedCornerShape(20.dp),
                        colors = CardDefaults.cardColors(containerColor = Color.Transparent),
                        border = BorderStroke(1.dp, Gold.copy(alpha = 0.4f))
                    ) {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .background(
                                    Brush.linearGradient(listOf(Black, Charcoal, Black))
                                )
                                .padding(20.dp)
                        ) {
                            Column {
                                // Top Row: Ticket ID and Tracking
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Row(verticalAlignment = Alignment.CenterVertically) {
                                        Icon(
                                            Icons.Default.ConfirmationNumber,
                                            null,
                                            tint = Gold.copy(alpha = 0.7f),
                                            modifier = Modifier.size(14.dp)
                                        )
                                        Spacer(modifier = Modifier.width(6.dp))
                                        Text(
                                            "TICKET #SB-2047",
                                            color = Gold.copy(alpha = 0.7f),
                                            fontSize = 10.sp,
                                            fontWeight = FontWeight.Bold,
                                            letterSpacing = 1.sp
                                        )
                                    }
                                    
                                    IconButton(
                                        onClick = onNavigateToTracking,
                                        modifier = Modifier
                                            .size(32.dp)
                                            .clip(CircleShape)
                                            .background(Gold.copy(alpha = 0.15f))
                                    ) {
                                        Icon(Icons.Default.MyLocation, null, tint = Gold, modifier = Modifier.size(16.dp))
                                    }
                                }

                                Spacer(modifier = Modifier.height(12.dp))

                                // Main Route
                                Text(
                                    "${trip.origin} → ${trip.destination}",
                                    color = White,
                                    fontWeight = FontWeight.Black,
                                    style = MaterialTheme.typography.headlineSmall
                                )

                                Spacer(modifier = Modifier.height(20.dp))
                                
                                // Collective info row (Date, Time)
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                                ) {
                                    TripDetailPill(Icons.Default.CalendarToday, trip.date)
                                    TripDetailPill(Icons.Default.AccessTime, trip.time)
                                }

                                Spacer(modifier = Modifier.height(20.dp))

                                Button(
                                    onClick = { showRouteDialog = true },
                                    modifier = Modifier.fillMaxWidth(),
                                    shape = RoundedCornerShape(14.dp),
                                    colors = ButtonDefaults.buttonColors(containerColor = Black),
                                    border = BorderStroke(1.dp, Gold.copy(alpha = 0.3f)),
                                    contentPadding = PaddingValues(12.dp)
                                ) {
                                    Icon(Icons.Default.Map, null, tint = Gold, modifier = Modifier.size(18.dp))
                                    Spacer(modifier = Modifier.width(10.dp))
                                    Text("VER MAPA DE RUTA", color = Gold, fontSize = 12.sp, fontWeight = FontWeight.Black, letterSpacing = 1.sp)
                                }
                            }
                        }
                    }
                } else {
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 20.dp)
                            .clickable { onNavigateToSearch() },
                        shape = RoundedCornerShape(18.dp),
                        colors = CardDefaults.cardColors(containerColor = Color(0xFFF8F8F8)),
                        border = BorderStroke(1.dp, Gold.copy(alpha = 0.25f))
                    ) {
                        Row(
                            modifier = Modifier.padding(20.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(Icons.Default.DirectionsBus, null, tint = Gold.copy(alpha = 0.5f), modifier = Modifier.size(36.dp))
                            Spacer(modifier = Modifier.width(16.dp))
                            Column {
                                Text(if (isEn) "No upcoming trips" else "Sin viajes próximos", fontWeight = FontWeight.SemiBold)
                                Text(
                                    if (isEn) "Tap to search your next route" else "Toca para buscar tu próxima ruta",
                                    fontSize = 12.sp, color = Color.Gray
                                )
                            }
                        }
                    }
                }
                Spacer(modifier = Modifier.height(28.dp))
            }

            // ── 4. Stats banner ───────────────────────────────────────
            item {
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 20.dp),
                    shape = RoundedCornerShape(20.dp),
                    colors = CardDefaults.cardColors(containerColor = Color.Transparent)
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(
                                Brush.horizontalGradient(listOf(Gold, GoldDark))
                            )
                            .padding(vertical = 20.dp)
                    ) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceEvenly
                        ) {
                            statsRow.forEach { stat ->
                                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                    Icon(stat.icon, null, tint = Black, modifier = Modifier.size(20.dp))
                                    Spacer(modifier = Modifier.height(4.dp))
                                    Text(stat.value, color = Black, fontWeight = FontWeight.ExtraBold, fontSize = 16.sp)
                                    Text(stat.label, color = Black.copy(alpha = 0.65f), fontSize = 10.sp, fontWeight = FontWeight.Medium)
                                }
                            }
                        }
                    }
                }
                Spacer(modifier = Modifier.height(28.dp))
            }

            // ── 5. Promotions carousel ────────────────────────────────
            item {
                val pagerState = rememberPagerState { uiState.promotions.size }
                val promoGradients = listOf(
                    listOf(Color(0xFF1A0533), Color(0xFF2D0A5B)),
                    listOf(Color(0xFF0A1628), Color(0xFF0D2137)),
                    listOf(Color(0xFF1A1208), Color(0xFF2C1F00)),
                )

                SectionHeader(
                    title = if (isEn) "Exclusive Offers" else "Ofertas Exclusivas",
                    actionLabel = "${pagerState.currentPage + 1}/${uiState.promotions.size}",
                    modifier = Modifier.padding(horizontal = 20.dp)
                )
                Spacer(modifier = Modifier.height(12.dp))

                HorizontalPager(
                    state = pagerState,
                    contentPadding = PaddingValues(horizontal = 20.dp),
                    pageSpacing = 12.dp
                ) { page ->
                    val promo = uiState.promotions[page]
                    val gradColors = promoGradients[page % promoGradients.size]
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(130.dp),
                        shape = RoundedCornerShape(20.dp),
                        colors = CardDefaults.cardColors(containerColor = Color.Transparent),
                        border = BorderStroke(1.dp, Gold.copy(alpha = 0.3f))
                    ) {
                        Box(
                            modifier = Modifier
                                .fillMaxSize()
                                .background(Brush.linearGradient(gradColors))
                                .padding(20.dp)
                        ) {
                            // Decorative circle
                            Box(
                                modifier = Modifier
                                    .size(90.dp)
                                    .align(Alignment.BottomEnd)
                                    .offset(x = 20.dp, y = 20.dp)
                                    .clip(CircleShape)
                                    .background(Gold.copy(alpha = 0.08f))
                            )
                            Column {
                                Surface(
                                    color = Gold.copy(alpha = 0.18f),
                                    shape = RoundedCornerShape(6.dp)
                                ) {
                                    Text(
                                        "★ OFERTA",
                                        color = Gold,
                                        fontSize = 9.sp,
                                        fontWeight = FontWeight.ExtraBold,
                                        letterSpacing = 1.sp,
                                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 3.dp)
                                    )
                                }
                                Spacer(modifier = Modifier.height(8.dp))
                                Text(promo.title, fontWeight = FontWeight.ExtraBold, color = White, fontSize = 16.sp)
                                Spacer(modifier = Modifier.height(4.dp))
                                Text(promo.description, fontSize = 12.sp, color = White.copy(alpha = 0.7f), maxLines = 2)
                            }
                        }
                    }
                }

                // Pager dots
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 10.dp),
                    horizontalArrangement = Arrangement.Center
                ) {
                    repeat(uiState.promotions.size) { index ->
                        val isCurrent = pagerState.currentPage == index
                        Box(
                            modifier = Modifier
                                .padding(horizontal = 3.dp)
                                .size(if (isCurrent) 20.dp else 6.dp, 6.dp)
                                .clip(RoundedCornerShape(3.dp))
                                .background(if (isCurrent) Gold else Gold.copy(alpha = 0.3f))
                        )
                    }
                }
                Spacer(modifier = Modifier.height(28.dp))
            }

            // ── 6. Partner companies ──────────────────────────────────
            item {
                SectionHeader(
                    title = if (isEn) "Partner Companies" else "Empresas Aliadas",
                    modifier = Modifier.padding(horizontal = 20.dp)
                )
                Spacer(modifier = Modifier.height(12.dp))

                val partners = listOf(
                    R.raw.busbolivariano to "Bolivariano",
                    R.raw.busflota      to "Flota",
                    R.raw.busgacela     to "Gacela",
                    R.raw.bustinto      to "Tinto",
                    R.raw.smartbus      to "SmartBus"
                )
                LazyRow(
                    contentPadding = PaddingValues(horizontal = 20.dp),
                    horizontalArrangement = Arrangement.spacedBy(14.dp)
                ) {
                    items(partners) { (imgRes, name) ->
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally,
                            modifier = Modifier.clickable { }
                        ) {
                            Surface(
                                modifier = Modifier.size(72.dp),
                                shape = RoundedCornerShape(18.dp),
                                color = White,
                                shadowElevation = 6.dp,
                                border = BorderStroke(1.dp, Gold.copy(alpha = 0.15f))
                            ) {
                                Image(
                                    painter = painterResource(id = imgRes),
                                    contentDescription = name,
                                    modifier = Modifier.fillMaxSize().padding(8.dp),
                                    contentScale = ContentScale.Fit
                                )
                            }
                            Spacer(modifier = Modifier.height(6.dp))
                            Text(name, fontSize = 10.sp, fontWeight = FontWeight.Medium, color = Black)
                        }
                    }
                }
                Spacer(modifier = Modifier.height(28.dp))
            }

            // ── 7. Bus tips feed ──────────────────────────────────────
            item {
                SectionHeader(
                    title = if (isEn) "SmartBus Tips" else "Consejos SmartBus",
                    modifier = Modifier.padding(horizontal = 20.dp)
                )
                Spacer(modifier = Modifier.height(12.dp))
            }

            items(busTips) { tip ->
                TipCard(tip = tip, modifier = Modifier.padding(horizontal = 20.dp, vertical = 6.dp))
            }

            item { Spacer(modifier = Modifier.height(20.dp)) }

            // ── 8. Available routes header + filters ──────────────────
            item {
                SectionHeader(
                    title = if (isEn) "Available Routes" else "Rutas Disponibles",
                    actionLabel = if (isEn) "See all" else "Ver todas",
                    onAction = onNavigateToSearch,
                    modifier = Modifier.padding(horizontal = 20.dp)
                )
                Spacer(modifier = Modifier.height(12.dp))

                LazyRow(
                    contentPadding = PaddingValues(horizontal = 20.dp),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    items(RouteFilter.entries) { filter ->
                        val isSelected = uiState.selectedFilter == filter
                        val chipBg by animateColorAsState(
                            targetValue = if (isSelected) Black else Color(0xFFF0F0F0),
                            animationSpec = tween(200), label = "chip_bg"
                        )
                        val textColor by animateColorAsState(
                            targetValue = if (isSelected) Gold else Color.Gray,
                            animationSpec = tween(200), label = "chip_text"
                        )
                        Surface(
                            modifier = Modifier
                                .clip(RoundedCornerShape(20.dp))
                                .clickable { viewModel.onFilterSelected(filter) },
                            color = chipBg,
                            shape = RoundedCornerShape(20.dp)
                        ) {
                            Text(
                                if (isEn) filter.labelEn else filter.label,
                                color = textColor,
                                fontWeight = if (isSelected) FontWeight.SemiBold else FontWeight.Normal,
                                fontSize = 13.sp,
                                modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
                            )
                        }
                    }
                }
                Spacer(modifier = Modifier.height(14.dp))
            }

            // ── 9. Route cards ────────────────────────────────────────
            items(filteredRoutes) { route ->
                RouteCard(
                    route = route,
                    isEn = isEn,
                    onClick = onNavigateToSearch,
                    modifier = Modifier
                        .padding(horizontal = 20.dp)
                        .padding(bottom = 12.dp)
                )
            }
        }
    }

    if (showRouteDialog) {
        Dialog(onDismissRequest = { showRouteDialog = false }) {
            Surface(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight()
                    .padding(16.dp),
                shape = RoundedCornerShape(24.dp),
                color = White
            ) {
                Column(
                    modifier = Modifier.padding(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            "Mapa de Ruta",
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold,
                            color = Black
                        )
                        IconButton(onClick = { showRouteDialog = false }) {
                            Icon(Icons.Default.Close, null, tint = Color.Gray)
                        }
                    }
                    
                    Spacer(modifier = Modifier.height(12.dp))
                    
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(400.dp),
                        shape = RoundedCornerShape(16.dp),
                        elevation = CardDefaults.cardElevation(4.dp)
                    ) {
                        Image(
                            painter = painterResource(id = R.raw.medellin_bogota2),
                            contentDescription = "Ruta Medellín - Bogotá",
                            modifier = Modifier.fillMaxSize(),
                            contentScale = ContentScale.Crop
                        )
                    }
                    
                    Spacer(modifier = Modifier.height(16.dp))
                    
                    Text(
                        "Ruta Medellín - Bogotá",
                        style = MaterialTheme.typography.bodyMedium,
                        color = Color.Gray,
                        fontWeight = FontWeight.Medium
                    )
                    
                    Spacer(modifier = Modifier.height(8.dp))
                }
            }
        }
    }

    // ── Tracking Dialog ───────────────────────────────────────────────────────
    if (showTrackingDialog) {
        Dialog(onDismissRequest = { showTrackingDialog = false }) {
            Surface(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight()
                    .padding(16.dp),
                shape = RoundedCornerShape(24.dp),
                color = White
            ) {
                Column(
                    modifier = Modifier.padding(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column {
                            Text(
                                "Seguimiento en Vivo",
                                style = MaterialTheme.typography.titleMedium,
                                fontWeight = FontWeight.Bold,
                                color = Black
                            )
                            Text(
                                "En ruta Armenia - Filandia",
                                fontSize = 11.sp,
                                color = Color.Gray
                            )
                        }
                        IconButton(onClick = { showTrackingDialog = false }) {
                            Icon(Icons.Default.Close, null, tint = Color.Gray)
                        }
                    }
                    
                    Spacer(modifier = Modifier.height(16.dp))
                    
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(400.dp),
                        shape = RoundedCornerShape(16.dp),
                        elevation = CardDefaults.cardElevation(8.dp)
                    ) {
                        Image(
                            painter = painterResource(id = R.raw.rastreo),
                            contentDescription = "Rastreo",
                            modifier = Modifier.fillMaxSize(),
                            contentScale = ContentScale.Crop
                        )
                    }
                    
                    Spacer(modifier = Modifier.height(20.dp))
                    
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceEvenly
                    ) {
                        TrackingInfoItem("Velocidad", "64 km/h")
                        TrackingInfoItem("ETA", "1h 20m")
                        TrackingInfoItem("Estado", "A tiempo")
                    }
                    
                    Spacer(modifier = Modifier.height(16.dp))
                }
            }
        }
    }
}

@Composable
private fun TrackingInfoItem(label: String, value: String) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text(label, fontSize = 10.sp, color = Color.Gray)
        Text(value, fontSize = 13.sp, fontWeight = FontWeight.Bold, color = Black)
    }
}

@Composable
private fun PulsingDot(color: Color = Color.White) {
    val infiniteTransition = rememberInfiniteTransition(label = "pulse")
    val alpha by infiniteTransition.animateFloat(
        initialValue = 1f,
        targetValue = 0.2f,
        animationSpec = infiniteRepeatable(
            animation = tween(600, easing = LinearEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "dot_alpha"
    )
    Box(
        modifier = Modifier
            .size(8.dp)
            .clip(CircleShape)
            .background(color.copy(alpha = alpha))
    )
}

// ── Sub-composables ───────────────────────────────────────────────────────────

@Composable
private fun SectionHeader(
    title: String,
    modifier: Modifier = Modifier,
    actionLabel: String? = null,
    onAction: (() -> Unit)? = null
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            title,
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.ExtraBold,
            color = Black
        )
        if (actionLabel != null && onAction != null) {
            TextButton(onClick = onAction, contentPadding = PaddingValues(0.dp)) {
                Text(actionLabel, color = Gold, fontSize = 12.sp, fontWeight = FontWeight.SemiBold)
            }
        } else if (actionLabel != null) {
            Text(actionLabel, fontSize = 12.sp, color = Color.Gray)
        }
    }
}

@Composable
private fun TripDetailPill(icon: ImageVector, text: String) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .clip(RoundedCornerShape(8.dp))
            .background(White.copy(alpha = 0.08f))
            .padding(horizontal = 10.dp, vertical = 6.dp)
    ) {
        Icon(icon, null, tint = Gold, modifier = Modifier.size(12.dp))
        Spacer(modifier = Modifier.width(5.dp))
        Text(text, color = White, fontSize = 11.sp, fontWeight = FontWeight.SemiBold)
    }
}

@Composable
private fun TipCard(tip: TipItem, modifier: Modifier = Modifier) {
    Card(
        modifier = modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = White),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        border = BorderStroke(1.dp, Color(0xFFF0F0F0))
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.Top
        ) {
            // Icon box
            Box(
                modifier = Modifier
                    .size(48.dp)
                    .clip(RoundedCornerShape(14.dp))
                    .background(tip.iconBg.copy(alpha = 0.12f)),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = tip.icon,
                    contentDescription = null,
                    tint = tip.iconBg,
                    modifier = Modifier.size(24.dp)
                )
            }
            Spacer(modifier = Modifier.width(14.dp))
            Column(modifier = Modifier.weight(1f)) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(tip.title, fontWeight = FontWeight.Bold, fontSize = 14.sp, color = Black)
                    Surface(
                        color = Gold.copy(alpha = 0.12f),
                        shape = RoundedCornerShape(6.dp)
                    ) {
                        Text(
                            tip.tag,
                            color = GoldDark,
                            fontSize = 9.sp,
                            fontWeight = FontWeight.ExtraBold,
                            modifier = Modifier.padding(horizontal = 8.dp, vertical = 3.dp)
                        )
                    }
                }
                Spacer(modifier = Modifier.height(4.dp))
                Text(tip.body, fontSize = 12.sp, color = Color.Gray, lineHeight = 17.sp)
            }
        }
    }
}

@Composable
private fun RouteCard(
    route: RouteItem,
    isEn: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val occupancyColor = when {
        route.occupancy >= 90 -> Color(0xFFE53935)
        route.occupancy >= 70 -> Color(0xFFFFA726)
        else                  -> Color(0xFF43A047)
    }
    val occupancyLabel = when {
        route.occupancy >= 90 -> if (isEn) "Almost full" else "Casi lleno"
        route.occupancy >= 70 -> if (isEn) "Available" else "Disponible"
        else                  -> if (isEn) "Spacious" else "Amplio"
    }

    Card(
        modifier = modifier.fillMaxWidth().clickable { onClick() },
        shape = RoundedCornerShape(18.dp),
        colors = CardDefaults.cardColors(containerColor = White),
        elevation = CardDefaults.cardElevation(defaultElevation = 3.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.weight(1f)) {
                    Box(
                        modifier = Modifier
                            .size(38.dp)
                            .clip(RoundedCornerShape(12.dp))
                            .background(Black),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(Icons.Default.DirectionsBus, null, tint = Gold, modifier = Modifier.size(20.dp))
                    }
                    Spacer(modifier = Modifier.width(10.dp))
                    Column {
                        Text(
                            "${route.origin} → ${route.destination}",
                            style = MaterialTheme.typography.titleSmall,
                            fontWeight = FontWeight.ExtraBold,
                            color = Black,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )
                        Text(route.company, fontSize = 11.sp, color = Color.Gray)
                    }
                }
                Row(horizontalArrangement = Arrangement.spacedBy(4.dp)) {
                    if (route.isNight) {
                        Surface(color = Color(0xFF1A237E).copy(alpha = 0.1f), shape = RoundedCornerShape(6.dp)) {
                            Text("🌙", fontSize = 11.sp, modifier = Modifier.padding(horizontal = 6.dp, vertical = 3.dp))
                        }
                    }
                    if (route.isDirect) {
                        Surface(color = Color(0xFF1B5E20).copy(alpha = 0.1f), shape = RoundedCornerShape(6.dp)) {
                            Text("⚡", fontSize = 11.sp, modifier = Modifier.padding(horizontal = 6.dp, vertical = 3.dp))
                        }
                    }
                    Surface(color = occupancyColor.copy(alpha = 0.12f), shape = RoundedCornerShape(8.dp)) {
                        Text(
                            occupancyLabel,
                            color = occupancyColor,
                            fontSize = 10.sp,
                            fontWeight = FontWeight.SemiBold,
                            modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(12.dp))
            HorizontalDivider(color = Color(0xFFF0F0F0))
            Spacer(modifier = Modifier.height(12.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                RouteDetailItem(Icons.Default.AccessTime, if (isEn) "Departure" else "Salida",  route.departureTime)
                RouteDetailItem(Icons.Default.Schedule,   if (isEn) "Duration"  else "Duración", route.duration)
                Column(horizontalAlignment = Alignment.End) {
                    Text(if (isEn) "Price" else "Precio", fontSize = 10.sp, color = Color(0xFF9E9E9E))
                    Text(route.price, fontSize = 17.sp, fontWeight = FontWeight.ExtraBold, color = Gold)
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            // Occupancy bar
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                Text(if (isEn) "Occupancy" else "Ocupación", fontSize = 10.sp, color = Color(0xFF9E9E9E))
                Text("${route.occupancy}%", fontSize = 10.sp, color = occupancyColor, fontWeight = FontWeight.Bold)
            }
            Spacer(modifier = Modifier.height(5.dp))
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(5.dp)
                    .clip(RoundedCornerShape(50))
                    .background(Color(0xFFF0F0F0))
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth(fraction = route.occupancy / 100f)
                        .fillMaxHeight()
                        .background(
                            Brush.horizontalGradient(
                                listOf(occupancyColor.copy(alpha = 0.55f), occupancyColor)
                            ),
                            RoundedCornerShape(50)
                        )
                )
            }
        }
    }
}

@Composable
private fun RouteDetailItem(icon: ImageVector, label: String, value: String) {
    Column(horizontalAlignment = Alignment.Start) {
        Text(label, fontSize = 10.sp, color = Color(0xFF9E9E9E))
        Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(icon, null, tint = Black, modifier = Modifier.size(12.dp))
            Spacer(modifier = Modifier.width(3.dp))
            Text(value, fontSize = 12.sp, fontWeight = FontWeight.SemiBold, color = Black, maxLines = 1, overflow = TextOverflow.Ellipsis)
        }
    }
}
