package com.smartbus.app.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.smartbus.app.presentation.search.TripResult
import com.smartbus.app.ui.theme.Gold

@Composable
fun TripResultCard(
    result: TripResult,
    onClick: () -> Unit
) {
    SmartBusCard(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        onClick = onClick
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Departure Time
                Column(horizontalAlignment = Alignment.Start) {
                    Text(result.departureTime, fontSize = 20.sp, fontWeight = FontWeight.ExtraBold)
                    Text("Salida", fontSize = 10.sp, color = MaterialTheme.colorScheme.secondary)
                }

                // Duration / Path
                Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.weight(1f)) {
                    Text(result.duration, fontSize = 12.sp, color = Gold, fontWeight = FontWeight.Medium)
                    DashedDivider(modifier = Modifier.padding(vertical = 4.dp), color = Gold.copy(alpha = 0.3f))
                }

                // Arrival Time (Mock: Departure + Duration)
                Column(horizontalAlignment = Alignment.End) {
                    Text("Llegada", fontSize = 10.sp, color = MaterialTheme.colorScheme.secondary)
                    Text("14:30", fontSize = 20.sp, fontWeight = FontWeight.ExtraBold) // Mock arrival
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    // Company Logo
                    Surface(
                        modifier = Modifier.size(36.dp),
                        shape = androidx.compose.foundation.shape.RoundedCornerShape(8.dp),
                        color = Color.White,
                        shadowElevation = 2.dp
                    ) {
                        androidx.compose.foundation.Image(
                            painter = androidx.compose.ui.res.painterResource(id = result.companyImageRes),
                            contentDescription = result.companyName,
                            modifier = Modifier.fillMaxSize(),
                            contentScale = androidx.compose.ui.layout.ContentScale.Crop
                        )
                    }
                    Spacer(modifier = Modifier.width(12.dp))
                    Text(result.companyName, fontSize = 14.sp, fontWeight = FontWeight.Bold)
                }

                Column(horizontalAlignment = Alignment.End) {
                    Text(result.price, color = Gold, fontSize = 20.sp, fontWeight = FontWeight.Black)
                    Text("por persona", fontSize = 10.sp, color = MaterialTheme.colorScheme.secondary)
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                AssistChip(
                    onClick = { },
                    label = { Text("${result.seatsAvailable} asientos", fontSize = 11.sp) },
                    colors = AssistChipDefaults.assistChipColors(
                        labelColor = if (result.seatsAvailable > 10) Color(0xFF4CAF50) else Color(0xFFF44336)
                    ),
                    border = null
                )
                
                LinearProgressIndicator(
                    progress = { result.occupancy },
                    modifier = Modifier
                        .width(100.dp)
                        .height(4.dp),
                    color = Gold,
                    trackColor = Gold.copy(alpha = 0.1f),
                    strokeCap = androidx.compose.ui.graphics.StrokeCap.Round
                )
            }
        }
    }
}
