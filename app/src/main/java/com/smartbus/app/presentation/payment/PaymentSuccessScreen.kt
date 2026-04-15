package com.smartbus.app.presentation.payment

import androidx.compose.foundation.background
import androidx.compose.foundation.verticalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.smartbus.app.ui.components.SmartBusButton
import com.smartbus.app.ui.theme.Black
import com.smartbus.app.ui.theme.Gold
import com.smartbus.app.ui.theme.White

@Composable
fun PaymentSuccessScreen(
    onBackToHome: () -> Unit
) {
    // Mock details based on previous state
    val destination = "Filandia"
    val seat = "#12"
    val price = "$6.000"
    val date = "15 Abr, 2026"

    val scrollState = androidx.compose.foundation.rememberScrollState()
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Black)
            .verticalScroll(scrollState)
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Success Icon
        Surface(
            modifier = Modifier.size(100.dp),
            color = Gold.copy(alpha = 0.15f),
            shape = CircleShape
        ) {
            Box(contentAlignment = Alignment.Center) {
                Icon(
                    imageVector = Icons.Default.Check,
                    contentDescription = null,
                    tint = Gold,
                    modifier = Modifier.size(50.dp)
                )
            }
        }

        Spacer(modifier = Modifier.height(32.dp))

        Text(
            "¡Pago Exitoso!",
            color = Gold,
            style = MaterialTheme.typography.displaySmall,
            fontWeight = FontWeight.Black
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            "Tu viaje y asiento ya están reservados.",
            color = White.copy(alpha = 0.7f),
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.bodyLarge
        )

        Spacer(modifier = Modifier.height(48.dp))

        // Receipt Card
        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(24.dp),
            colors = CardDefaults.cardColors(containerColor = Color(0xFF1A1A1A))
        ) {
            Column(modifier = Modifier.padding(24.dp)) {
                Text(
                    "DETALLES DE RESERVA",
                    color = Gold,
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold,
                    letterSpacing = 1.5.sp
                )
                
                Spacer(modifier = Modifier.height(16.dp))

                DetailRow("Destino", destination)
                DetailRow("Asiento", seat)
                DetailRow("Fecha", date)
                DetailRow("Total Pagado", price, isGold = true)

                Spacer(modifier = Modifier.height(24.dp))
                
                HorizontalDivider(color = White.copy(alpha = 0.1f))
                
                Spacer(modifier = Modifier.height(16.dp))
                
                Text(
                    "Se ha enviado un comprobante a tu correo sant***@gmail.com",
                    fontSize = 12.sp,
                    color = White.copy(alpha = 0.4f),
                    textAlign = TextAlign.Center,
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }

        Spacer(modifier = Modifier.height(48.dp))

        SmartBusButton(
            text = "Volver al Inicio",
            onClick = onBackToHome,
            isSecondary = true,
            modifier = Modifier.fillMaxWidth()
        )
    }
}

@Composable
private fun DetailRow(label: String, value: String, isGold: Boolean = false) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(label, color = White.copy(alpha = 0.5f), fontSize = 14.sp)
        Text(
            value,
            color = if (isGold) Gold else White,
            fontWeight = FontWeight.Bold,
            fontSize = if (isGold) 18.sp else 14.sp
        )
    }
}
