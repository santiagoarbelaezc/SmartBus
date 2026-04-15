package com.smartbus.app.presentation.search

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
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
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.smartbus.app.R
import com.smartbus.app.ui.components.SmartBusButton
import com.smartbus.app.ui.components.SmartBusLoadingOverlay
import com.smartbus.app.ui.theme.Black
import com.smartbus.app.ui.theme.Charcoal
import com.smartbus.app.ui.theme.Gold
import com.smartbus.app.ui.theme.GoldDark
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
                title = {
                    Column {
                        Text("Buscar Viaje", color = White, fontWeight = FontWeight.ExtraBold, fontSize = 18.sp)
                        Text("Armenia • Quindío", color = Gold, fontSize = 11.sp, fontWeight = FontWeight.Medium)
                    }
                },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = null, tint = White)
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = Black)
            )
        },
        containerColor = Color(0xFFF5F6F8)
    ) { innerPadding ->

        Box(modifier = Modifier.fillMaxSize()) {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding),
                contentPadding = PaddingValues(bottom = 40.dp)
            ) {

                // ── Search header card ─────────────────────────────────
                item {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(Black)
                            .padding(horizontal = 20.dp)
                            .padding(top = 8.dp, bottom = 32.dp)
                    ) {
                        Column {
                            Text(
                                "Buscar Ruta",
                                color = White,
                                fontWeight = FontWeight.ExtraBold,
                                fontSize = 28.sp
                            )
                            Text(
                                "Encuentra el mejor viaje para ti",
                                color = White.copy(alpha = 0.6f),
                                fontSize = 14.sp,
                                fontWeight = FontWeight.Medium
                            )
                            
                            Spacer(modifier = Modifier.height(24.dp))
                            
                            Card(
                                shape = RoundedCornerShape(24.dp),
                                colors = CardDefaults.cardColors(containerColor = Charcoal),
                                elevation = CardDefaults.cardElevation(12.dp)
                            ) {
                            Column(modifier = Modifier.padding(20.dp)) {

                                // Origin field
                                ModernSearchInput(
                                    label = "Origen",
                                    value = uiState.origin,
                                    onValueChange = viewModel::onOriginChange,
                                    placeholder = "¿Desde dónde?",
                                    icon = Icons.Default.TripOrigin,
                                    iconColor = Gold
                                )

                                Spacer(modifier = Modifier.height(16.dp))
                                HorizontalDivider(color = White.copy(alpha = 0.05f))
                                Spacer(modifier = Modifier.height(16.dp))

                                // Destination field
                                ModernSearchInput(
                                    label = "Destino",
                                    value = uiState.destination,
                                    onValueChange = viewModel::onDestinationChange,
                                    placeholder = "¿Hacia dónde?",
                                    icon = Icons.Default.LocationOn,
                                    iconColor = Color(0xFFFBC02D)
                                )

                                Spacer(modifier = Modifier.height(16.dp))
                                HorizontalDivider(color = White.copy(alpha = 0.05f))
                                Spacer(modifier = Modifier.height(16.dp))

                                // Date field
                                ModernSearchInput(
                                    label = "Fecha",
                                    value = "Hoy, 15 Abr", // Mocked for now
                                    onValueChange = {},
                                    placeholder = "dd/mm/aaaa",
                                    icon = Icons.Default.CalendarToday,
                                    iconColor = Gold
                                )

                                Spacer(modifier = Modifier.height(16.dp))
                                HorizontalDivider(color = Color(0xFFF0F0F0))
                                Spacer(modifier = Modifier.height(16.dp))

                                // Badges row
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                                ) {
                                    InfoBadge(
                                        icon = Icons.Default.CalendarToday,
                                        text = "Hoy",
                                        modifier = Modifier.weight(1f)
                                    )
                                    InfoBadge(
                                        icon = Icons.Default.Group,
                                        text = "1 Persona",
                                        modifier = Modifier.weight(1f)
                                    )
                                    InfoBadge(
                                        icon = Icons.Default.EventSeat,
                                        text = "Cualquier clase",
                                        modifier = Modifier.weight(1.4f)
                                    )
                                }

                                Spacer(modifier = Modifier.height(20.dp))

                                // Search button
                                SmartBusButton(
                                    text = "Buscar Viajes",
                                    onClick = { isSearching = true },
                                    isSecondary = true,
                                    modifier = Modifier.fillMaxWidth()
                                )
                            }
                        }
                    }
                }
            }

                // ── Tabs ──────────────────────────────────────────────
                item {
                    Spacer(modifier = Modifier.height(8.dp))
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 20.dp),
                        horizontalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        RouteTab(
                            label = "🚌 Intermunicipales",
                            selected = uiState.selectedTab == RouteType.INTERCITY,
                            onClick = { viewModel.onTabSelected(RouteType.INTERCITY) },
                            modifier = Modifier.weight(1f)
                        )
                        RouteTab(
                            label = "🔴 Buses Tinto",
                            selected = uiState.selectedTab == RouteType.URBAN,
                            onClick = { viewModel.onTabSelected(RouteType.URBAN) },
                            modifier = Modifier.weight(1f)
                        )
                    }
                    Spacer(modifier = Modifier.height(16.dp))
                    
                    // Filter Chips Row
                    LazyRow(
                        modifier = Modifier.fillMaxWidth(),
                        contentPadding = PaddingValues(horizontal = 20.dp),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        items(SearchFilter.values()) { filter ->
                            val isSelected = uiState.selectedFilter == filter
                            val chipBg by animateColorAsState(
                                targetValue = if (isSelected) Gold else White,
                                animationSpec = tween(300),
                                label = "chipBg"
                            )
                            val chipText by animateColorAsState(
                                targetValue = if (isSelected) Black else Color.Gray,
                                animationSpec = tween(300),
                                label = "chipText"
                            )

                            Surface(
                                modifier = Modifier.clickable { 
                                    viewModel.onFilterSelected(filter)
                                },
                                shape = RoundedCornerShape(12.dp),
                                color = chipBg,
                                border = if (!isSelected) BorderStroke(1.dp, Color(0xFFE0E0E0)) else null,
                                shadowElevation = if (isSelected) 4.dp else 0.dp
                            ) {
                                Row(
                                    modifier = Modifier.padding(horizontal = 14.dp, vertical = 8.dp),
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    if (isSelected) {
                                        Icon(Icons.Default.FilterList, null, tint = Black, modifier = Modifier.size(14.dp))
                                        Spacer(modifier = Modifier.width(6.dp))
                                    }
                                    Text(
                                        filter.label,
                                        color = chipText,
                                        fontSize = 12.sp,
                                        fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium
                                    )
                                }
                            }
                        }
                    }
                    Spacer(modifier = Modifier.height(24.dp))
                }

                // ── INTERCITY routes ──────────────────────────────────
                if (uiState.selectedTab == RouteType.INTERCITY) {

                    val routes = viewModel.filteredIntercity()
                    val grouped = routes.groupBy { it.destination }

                    grouped.forEach { (destination, routeGroup) ->
                        // Destination header
                        item(key = "header_$destination") {
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(horizontal = 20.dp),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    Box(
                                        modifier = Modifier
                                            .size(36.dp)
                                            .clip(CircleShape)
                                            .background(
                                                Brush.linearGradient(listOf(Gold, GoldDark))
                                            ),
                                        contentAlignment = Alignment.Center
                                    ) {
                                        Icon(Icons.Default.Place, null, tint = Black, modifier = Modifier.size(18.dp))
                                    }
                                    Spacer(modifier = Modifier.width(10.dp))
                                    Column {
                                        Text(
                                            "Armenia → $destination",
                                            fontWeight = FontWeight.ExtraBold,
                                            fontSize = 16.sp,
                                            color = Black
                                        )
                                        Text(
                                            "${routeGroup.first().duration} · desde ${routeGroup.minByOrNull { it.priceFrom }?.priceFrom ?: ""}",
                                            fontSize = 12.sp,
                                            color = Color.Gray
                                        )
                                    }
                                }
                                Surface(
                                    color = Gold.copy(alpha = 0.15f),
                                    shape = RoundedCornerShape(8.dp)
                                ) {
                                    Text(
                                        "${routeGroup.size} opciones",
                                        color = GoldDark,
                                        fontSize = 11.sp,
                                        fontWeight = FontWeight.Bold,
                                        modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp)
                                    )
                                }
                            }
                            Spacer(modifier = Modifier.height(10.dp))
                        }

                        // Route cards for this destination
                        items(routeGroup, key = { "${it.destination}_${it.departureTime}_${it.companyName}" }) { route ->
                            IntercityRouteCard(
                                route = route,
                                onClick = onResultSelected,
                                modifier = Modifier.padding(horizontal = 20.dp, vertical = 5.dp)
                            )
                        }

                        item(key = "spacer_$destination") {
                            Spacer(modifier = Modifier.height(16.dp))
                        }
                    }

                } else {
                    // ── URBAN (Tinto) routes ──────────────────────────

                    // Bus Tinto hero image
                    item {
                        TintoBusHero()
                        Spacer(modifier = Modifier.height(16.dp))
                    }

                    val urbanRoutes = viewModel.filteredUrban()
                    items(urbanRoutes, key = { it.lineNumber }) { route ->
                        TintoRouteCard(
                            route = route,
                            onClick = onResultSelected,
                            modifier = Modifier.padding(horizontal = 20.dp, vertical = 6.dp)
                        )
                    }
                }
            }

            // Loading overlay
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

// ── Sub-composables ───────────────────────────────────────────────────────────

@Composable
private fun ModernSearchInput(
    label: String,
    value: String,
    onValueChange: (String) -> Unit,
    placeholder: String,
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    iconColor: Color
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.fillMaxWidth().clickable { /* Opens picker if needed */ }
    ) {
        Box(
            modifier = Modifier
                .size(42.dp)
                .clip(CircleShape)
                .background(iconColor.copy(alpha = 0.15f)),
            contentAlignment = Alignment.Center
        ) {
            Icon(icon, null, tint = iconColor, modifier = Modifier.size(18.dp))
        }
        Spacer(modifier = Modifier.width(16.dp))
        Column {
            Text(label, color = White.copy(alpha = 0.4f), fontSize = 12.sp, fontWeight = FontWeight.Medium)
            TextField(
                value = value,
                onValueChange = onValueChange,
                placeholder = { Text(placeholder, color = White.copy(alpha = 0.2f), fontSize = 15.sp) },
                singleLine = true,
                colors = TextFieldDefaults.colors(
                    unfocusedContainerColor = Color.Transparent,
                    focusedContainerColor   = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                    focusedIndicatorColor   = Color.Transparent,
                    cursorColor = Gold
                ),
                modifier = Modifier.offset(x = (-16).dp), // Adjust for TextField internal padding
                textStyle = LocalTextStyle.current.copy(
                    fontSize   = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color      = White
                )
            )
        }
    }
}

@Composable
private fun InfoBadge(
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    text: String,
    modifier: Modifier = Modifier
) {
    Surface(
        modifier = modifier,
        color = Color(0xFFF5F5F5),
        shape = RoundedCornerShape(10.dp),
        border = androidx.compose.foundation.BorderStroke(1.dp, Color(0xFFEEEEEE))
    ) {
        Row(
            modifier = Modifier.padding(horizontal = 10.dp, vertical = 9.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            Icon(icon, null, tint = Gold, modifier = Modifier.size(14.dp))
            Spacer(modifier = Modifier.width(5.dp))
            Text(
                text,
                fontSize = 11.sp,
                fontWeight = FontWeight.SemiBold,
                color = Color(0xFF555555),
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        }
    }
}

@Composable
private fun RouteTab(
    label: String,
    selected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val bg by animateColorAsState(
        targetValue = if (selected) Black else White,
        animationSpec = tween(200),
        label = "tab_bg"
    )
    val textColor by animateColorAsState(
        targetValue = if (selected) Gold else Color(0xFF888888),
        animationSpec = tween(200),
        label = "tab_text"
    )

    Card(
        modifier = modifier
            .height(46.dp)
            .clickable { onClick() },
        shape = RoundedCornerShape(14.dp),
        colors = CardDefaults.cardColors(containerColor = bg),
        elevation = CardDefaults.cardElevation(if (selected) 4.dp else 1.dp)
    ) {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            Text(
                label,
                color = textColor,
                fontWeight = if (selected) FontWeight.ExtraBold else FontWeight.Medium,
                fontSize = 13.sp
            )
        }
    }
}

@Composable
private fun IntercityRouteCard(
    route: IntercityRoute,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val occupancyColor = when {
        route.occupancy >= 0.8f -> Color(0xFFE53935)
        route.occupancy >= 0.5f -> Color(0xFFFFA726)
        else                    -> Color(0xFF43A047)
    }
    val occupancyLabel = when {
        route.occupancy >= 0.8f -> "Casi lleno"
        route.occupancy >= 0.5f -> "Disponible"
        else                    -> "Amplio"
    }

    Card(
        modifier = modifier
            .fillMaxWidth()
            .clickable { onClick() },
        shape = RoundedCornerShape(18.dp),
        colors = CardDefaults.cardColors(containerColor = White),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {

            // Top row: company + tag
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    // Company logo
                    val context = LocalContext.current
                    val bmp = remember(route.companyImageRes) {
                        android.graphics.BitmapFactory.decodeStream(
                            context.resources.openRawResource(route.companyImageRes)
                        )?.asImageBitmap()
                    }
                    Surface(
                        modifier = Modifier.size(38.dp),
                        shape = RoundedCornerShape(10.dp),
                        color = White,
                        shadowElevation = 2.dp,
                        border = androidx.compose.foundation.BorderStroke(1.dp, Color(0xFFF0F0F0))
                    ) {
                        if (bmp != null) {
                            androidx.compose.foundation.Image(
                                bitmap = bmp,
                                contentDescription = route.companyName,
                                modifier = Modifier.fillMaxSize().padding(4.dp),
                                contentScale = ContentScale.Fit
                            )
                        } else {
                            Icon(Icons.Default.DirectionsBus, null, tint = Gold)
                        }
                    }
                    Spacer(modifier = Modifier.width(10.dp))
                    Column {
                        Text(
                            route.companyName,
                            fontWeight = FontWeight.ExtraBold,
                            fontSize = 14.sp,
                            color = Black
                        )
                        Text(
                            if (route.isDirect) "Directo" else "Con paradas",
                            fontSize = 11.sp,
                            color = Color.Gray
                        )
                    }
                }

                // Tag badge
                if (route.tag != null) {
                    Surface(
                        color = Gold.copy(alpha = 0.13f),
                        shape = RoundedCornerShape(8.dp),
                        border = androidx.compose.foundation.BorderStroke(1.dp, Gold.copy(alpha = 0.3f))
                    ) {
                        Text(
                            route.tag,
                            color = GoldDark,
                            fontSize = 10.sp,
                            fontWeight = FontWeight.ExtraBold,
                            modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(14.dp))

            // Timeline row
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Column(horizontalAlignment = Alignment.Start) {
                    Text(
                        route.departureTime,
                        fontWeight = FontWeight.ExtraBold,
                        fontSize = 18.sp,
                        color = Black
                    )
                    Text("Armenia", fontSize = 11.sp, color = Color.Gray)
                }

                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.weight(1f).padding(horizontal = 12.dp)
                ) {
                    Text(
                        route.duration,
                        fontSize = 11.sp,
                        color = Gold,
                        fontWeight = FontWeight.SemiBold
                    )
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Box(modifier = Modifier.size(6.dp).clip(CircleShape).background(Gold))
                        HorizontalDivider(
                            modifier = Modifier.weight(1f),
                            color = Gold.copy(alpha = 0.4f),
                            thickness = 1.5.dp
                        )
                        Icon(Icons.Default.DirectionsBus, null, tint = Gold, modifier = Modifier.size(16.dp))
                        HorizontalDivider(
                            modifier = Modifier.weight(1f),
                            color = Gold.copy(alpha = 0.4f),
                            thickness = 1.5.dp
                        )
                        Box(modifier = Modifier.size(6.dp).clip(CircleShape).background(Gold))
                    }
                    Text(
                        if (route.isDirect) "Sin paradas" else "Con escalas",
                        fontSize = 10.sp,
                        color = Color.Gray
                    )
                }

                Column(horizontalAlignment = Alignment.End) {
                    Text(
                        route.destination,
                        fontWeight = FontWeight.ExtraBold,
                        fontSize = 18.sp,
                        color = Black
                    )
                    Text("Llegada", fontSize = 11.sp, color = Color.Gray)
                }
            }

            Spacer(modifier = Modifier.height(14.dp))
            HorizontalDivider(color = Color(0xFFF0F0F0))
            Spacer(modifier = Modifier.height(12.dp))

            // Bottom row: seats + occupancy + price
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    // Seats chip
                    Surface(
                        color = occupancyColor.copy(alpha = 0.12f),
                        shape = RoundedCornerShape(8.dp)
                    ) {
                        Text(
                            "● $occupancyLabel",
                            color = occupancyColor,
                            fontSize = 11.sp,
                            fontWeight = FontWeight.SemiBold,
                            modifier = Modifier.padding(horizontal = 8.dp, vertical = 5.dp)
                        )
                    }
                    Text(
                        "${route.seatsAvailable} asientos",
                        fontSize = 12.sp,
                        color = Color.Gray
                    )
                }

                Column(horizontalAlignment = Alignment.End) {
                    Text(
                        route.priceFrom,
                        color = Gold,
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Black
                    )
                    Text("desde / persona", fontSize = 10.sp, color = Color.Gray)
                }
            }
        }
    }
}

@Composable
private fun TintoBusHero() {
    val context = LocalContext.current
    val bmp = remember {
        android.graphics.BitmapFactory.decodeStream(
            context.resources.openRawResource(R.raw.bustinto)
        )?.asImageBitmap()
    }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp)
            .height(180.dp),
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(containerColor = Color.Transparent),
        elevation = CardDefaults.cardElevation(6.dp)
    ) {
        Box(modifier = Modifier.fillMaxSize()) {
            // Bus photo
            if (bmp != null) {
                androidx.compose.foundation.Image(
                    bitmap = bmp,
                    contentDescription = "Bus Tinto Armenia",
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.Crop
                )
            } else {
                Box(modifier = Modifier.fillMaxSize().background(Color(0xFFE53935)))
            }

            // Dark overlay
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(
                        Brush.verticalGradient(
                            listOf(Color.Transparent, Black.copy(alpha = 0.85f))
                        )
                    )
            )

            // Content on top
            Column(
                modifier = Modifier
                    .align(Alignment.BottomStart)
                    .padding(18.dp)
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Surface(
                        color = Color(0xFFE53935),
                        shape = RoundedCornerShape(6.dp)
                    ) {
                        Text(
                            "🔴 TINTO",
                            color = White,
                            fontWeight = FontWeight.ExtraBold,
                            fontSize = 10.sp,
                            letterSpacing = 1.sp,
                            modifier = Modifier.padding(horizontal = 8.dp, vertical = 3.dp)
                        )
                    }
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("Armenia, Quindío", color = White.copy(alpha = 0.7f), fontSize = 12.sp)
                }
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    "Buses Urbanos de Armenia",
                    color = White,
                    fontWeight = FontWeight.ExtraBold,
                    fontSize = 20.sp
                )
                Text(
                    "El transporte con historia de la ciudad",
                    color = White.copy(alpha = 0.6f),
                    fontSize = 12.sp
                )
            }
        }
    }
}

@Composable
private fun TintoRouteCard(
    route: UrbanRoute,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val lineColor = Color(route.color)
    val context   = LocalContext.current
    val busBmp    = remember(route.busImageRes) {
        android.graphics.BitmapFactory.decodeStream(
            context.resources.openRawResource(route.busImageRes)
        )?.asImageBitmap()
    }
    val logoBmp   = remember(route.logoRes) {
        android.graphics.BitmapFactory.decodeStream(
            context.resources.openRawResource(route.logoRes)
        )?.asImageBitmap()
    }

    Card(
        modifier = modifier
            .fillMaxWidth()
            .clickable { onClick() },
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(containerColor = White),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Column {
            // Bus photo strip
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(120.dp)
            ) {
                if (busBmp != null) {
                    androidx.compose.foundation.Image(
                        bitmap = busBmp,
                        contentDescription = route.lineName,
                        modifier = Modifier.fillMaxSize(),
                        contentScale = ContentScale.Crop
                    )
                }
                // Gradient overlay
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(
                            Brush.horizontalGradient(
                                listOf(lineColor.copy(alpha = 0.85f), Color.Transparent)
                            )
                        )
                )
                // Line number badge
                Box(
                    modifier = Modifier
                        .padding(16.dp)
                        .size(56.dp)
                        .clip(RoundedCornerShape(14.dp))
                        .background(White)
                        .align(Alignment.CenterStart),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        route.lineNumber,
                        color = lineColor,
                        fontWeight = FontWeight.Black,
                        fontSize = 22.sp
                    )
                }

                // Logo tinto
                if (logoBmp != null) {
                    Surface(
                        modifier = Modifier
                            .align(Alignment.TopEnd)
                            .padding(12.dp)
                            .size(40.dp),
                        shape = RoundedCornerShape(10.dp),
                        color = White,
                        shadowElevation = 2.dp
                    ) {
                        androidx.compose.foundation.Image(
                            bitmap = logoBmp,
                            contentDescription = "Tinto",
                            modifier = Modifier.fillMaxSize().padding(4.dp),
                            contentScale = ContentScale.Fit
                        )
                    }
                }
            }

            // Bottom info
            Column(modifier = Modifier.padding(16.dp)) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column {
                        Text(
                            route.lineName,
                            fontWeight = FontWeight.ExtraBold,
                            fontSize = 16.sp,
                            color = Black
                        )
                        Text(
                            route.description,
                            fontSize = 12.sp,
                            color = Color.Gray,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis,
                            modifier = Modifier.fillMaxWidth(0.75f)
                        )
                    }
                    Text(
                        route.farePrice,
                        color = Gold,
                        fontWeight = FontWeight.Black,
                        fontSize = 18.sp
                    )
                }

                Spacer(modifier = Modifier.height(12.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    // Frequency chip
                    Surface(
                        color = lineColor.copy(alpha = 0.1f),
                        shape = RoundedCornerShape(8.dp)
                    ) {
                        Row(
                            modifier = Modifier.padding(horizontal = 10.dp, vertical = 6.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(Icons.Default.Schedule, null, tint = lineColor, modifier = Modifier.size(13.dp))
                            Spacer(modifier = Modifier.width(4.dp))
                            Text(route.frequency, color = lineColor, fontSize = 11.sp, fontWeight = FontWeight.SemiBold)
                        }
                    }
                    // Hours chip
                    Surface(
                        color = Color(0xFFF0F0F0),
                        shape = RoundedCornerShape(8.dp)
                    ) {
                        Row(
                            modifier = Modifier.padding(horizontal = 10.dp, vertical = 6.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(Icons.Default.AccessTime, null, tint = Color.Gray, modifier = Modifier.size(13.dp))
                            Spacer(modifier = Modifier.width(4.dp))
                            Text(route.operatingHours, color = Color.Gray, fontSize = 11.sp)
                        }
                    }
                }
            }
        }
    }
}
