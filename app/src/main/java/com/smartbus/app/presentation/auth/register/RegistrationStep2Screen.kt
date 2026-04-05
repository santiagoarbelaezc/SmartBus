package com.smartbus.app.presentation.auth.register

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.smartbus.app.R
import com.smartbus.app.ui.components.SmartBusButton
import com.smartbus.app.ui.components.SmartBusTextField
import com.smartbus.app.ui.theme.Black
import com.smartbus.app.ui.theme.Gold
import com.smartbus.app.ui.theme.White

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RegistrationStep2Screen(
    viewModel: RegisterViewModel,
    onNext: () -> Unit,
    onSkip: () -> Unit
) {
    val scrollState = rememberScrollState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Personaliza tu Viaje", color = Gold, fontWeight = FontWeight.Bold) },
                actions = {
                    TextButton(onClick = onSkip) {
                        Text("Saltar", color = Gold.copy(alpha = 0.7f))
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = Black)
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .background(Color.White)
                .verticalScroll(scrollState)
                .padding(24.dp)
        ) {
            Text(
                "¿Dónde te encuentras?",
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold,
                color = Black
            )
            Text(
                "Dinos tu ciudad y dirección para sugerirte las mejores rutas.",
                style = MaterialTheme.typography.bodySmall,
                color = Color.Gray
            )
            
            Spacer(modifier = Modifier.height(16.dp))
            
            SmartBusTextField(
                value = "Armenia, Quindío",
                onValueChange = {},
                label = "Ciudad",
                leadingIcon = { Icon(Icons.Default.LocationOn, contentDescription = null, tint = Gold) }
            )
            
            Spacer(modifier = Modifier.height(12.dp))
            
            SmartBusTextField(
                value = "",
                onValueChange = {},
                label = "Dirección (Opcional)",
                leadingIcon = { Icon(Icons.Default.Home, contentDescription = null, tint = Gold) }
            )

            Spacer(modifier = Modifier.height(32.dp))

            Text(
                "Rutas Locales",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                color = Black
            )
            Text(
                "Selecciona las empresas que usas para moverte por tu ciudad.",
                style = MaterialTheme.typography.bodySmall,
                color = Color.Gray
            )
            
            Spacer(modifier = Modifier.height(12.dp))
            
            RouteSelectionRow(
                routes = listOf(
                    RouteItemData("Tinto", R.raw.bustinto),
                    RouteItemData("Bus Calarcá", R.raw.smartbus), // Fallback if no specific img
                    RouteItemData("Circasia", R.raw.smartbus),
                    RouteItemData("Filandia", R.raw.smartbus)
                )
            )

            Spacer(modifier = Modifier.height(32.dp))

            Text(
                "Rutas Interdepartamentales",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                color = Black
            )
            Text(
                "Empresas de confianza para tus viajes largos.",
                style = MaterialTheme.typography.bodySmall,
                color = Color.Gray
            )
            
            Spacer(modifier = Modifier.height(12.dp))
            
            RouteSelectionRow(
                routes = listOf(
                    RouteItemData("Bolivariano", R.raw.busbolivariano),
                    RouteItemData("Flota Occidental", R.raw.busflota), // Fix mapping
                    RouteItemData("Gacela", R.raw.busgacela), // If this is Magdalena, name it as such or keep Gacela if requested
                    RouteItemData("Flota Magdalena", R.raw.busgacela) // Fix mapping
                )
            )

            Spacer(modifier = Modifier.height(40.dp))

            SmartBusButton(
                text = "Siguiente",
                onClick = onNext,
                modifier = Modifier.fillMaxWidth().height(56.dp)
            )
            
            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}

data class RouteItemData(val name: String, val imageRes: Int)

@Composable
fun RouteSelectionRow(routes: List<RouteItemData>) {
    var selectedRoutes by remember { mutableStateOf(setOf<String>()) }

    LazyRow(
        horizontalArrangement = Arrangement.spacedBy(12.dp),
        contentPadding = PaddingValues(horizontal = 4.dp)
    ) {
        items(routes) { route ->
            val isSelected = selectedRoutes.contains(route.name)
            Column(
                modifier = Modifier
                    .width(120.dp)
                    .clickable {
                        selectedRoutes = if (isSelected) {
                            selectedRoutes - route.name
                        } else {
                            selectedRoutes + route.name
                        }
                    },
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Box(
                    modifier = Modifier
                        .size(100.dp)
                        .clip(RoundedCornerShape(16.dp))
                        .background(if (isSelected) Gold.copy(alpha = 0.2f) else Color.LightGray.copy(alpha = 0.1f))
                        .border(
                            width = 2.dp,
                            color = if (isSelected) Gold else Color.Transparent,
                            shape = RoundedCornerShape(16.dp)
                        )
                ) {
                    Image(
                        painter = painterResource(id = route.imageRes),
                        contentDescription = route.name,
                        modifier = Modifier.fillMaxSize(),
                        contentScale = ContentScale.Crop
                    )
                    if (isSelected) {
                        Icon(
                            Icons.Default.CheckCircle,
                            contentDescription = null,
                            tint = Gold,
                            modifier = Modifier
                                .align(Alignment.TopEnd)
                                .padding(8.dp)
                                .size(24.dp)
                        )
                    }
                }
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = route.name,
                    fontSize = 12.sp,
                    fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal,
                    color = if (isSelected) Gold else Black,
                    textAlign = androidx.compose.ui.text.style.TextAlign.Center
                )
            }
        }
    }
}
