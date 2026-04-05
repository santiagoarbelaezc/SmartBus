package com.smartbus.app.presentation.auth.register

import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.smartbus.app.ui.theme.Black
import com.smartbus.app.ui.theme.Gold
import kotlinx.coroutines.delay

@Composable
fun RegistrationLoadingScreen(
    onFinished: () -> Unit
) {
    var loadingText by remember { mutableStateOf("Creando cuenta...") }
    val dotCount = 3
    
    LaunchedEffect(Unit) {
        delay(800)
        loadingText = "Personalizando tu experiencia..."
        delay(1000)
        loadingText = "Casi listo..."
        delay(1200)
        onFinished()
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Black),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            ElegantDotsLoading()
            
            Spacer(modifier = Modifier.height(32.dp))
            
            Text(
                text = loadingText,
                color = Gold,
                style = MaterialTheme.typography.bodyLarge,
                fontWeight = FontWeight.Medium,
                letterSpacing = 1.sp
            )
        }
    }
}

@Composable
fun ElegantDotsLoading() {
    val infiniteTransition = rememberInfiniteTransition(label = "dots")
    
    val dot1Scale by infiniteTransition.animateFloat(
        initialValue = 0.6f,
        targetValue = 1.2f,
        animationSpec = infiniteRepeatable(
            animation = tween(600, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "dot1"
    )

    val dot2Scale by infiniteTransition.animateFloat(
        initialValue = 0.6f,
        targetValue = 1.2f,
        animationSpec = infiniteRepeatable(
            animation = tween(600, delayMillis = 200, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "dot2"
    )

    val dot3Scale by infiniteTransition.animateFloat(
        initialValue = 0.6f,
        targetValue = 1.2f,
        animationSpec = infiniteRepeatable(
            animation = tween(600, delayMillis = 400, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "dot3"
    )

    Row(
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        DotView(scale = dot1Scale)
        DotView(scale = dot2Scale)
        DotView(scale = dot3Scale)
    }
}

@Composable
fun DotView(scale: Float) {
    Box(
        modifier = Modifier
            .size(12.dp)
            .scale(scale)
            .background(Gold, CircleShape)
    )
}
