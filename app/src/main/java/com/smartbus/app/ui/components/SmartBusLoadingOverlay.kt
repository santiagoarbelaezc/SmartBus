package com.smartbus.app.ui.components

import androidx.compose.animation.core.*
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.smartbus.app.R
import com.smartbus.app.ui.theme.Gold

@Composable
fun SmartBusLoadingOverlay() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black.copy(alpha = 0.7f)), // Glassmorphism-like effect
        contentAlignment = Alignment.Center
    ) {
        val infiniteTransition = rememberInfiniteTransition(label = "loading")
        val scale by infiniteTransition.animateFloat(
            initialValue = 0.8f,
            targetValue = 1.2f,
            animationSpec = infiniteRepeatable(
                animation = tween(1000, easing = FastOutSlowInEasing),
                repeatMode = RepeatMode.Reverse
            ),
            label = "scale"
        )

        val alpha by infiniteTransition.animateFloat(
            initialValue = 0.4f,
            targetValue = 1f,
            animationSpec = infiniteRepeatable(
                animation = tween(1000, easing = FastOutSlowInEasing),
                repeatMode = RepeatMode.Reverse
            ),
            label = "alpha"
        )

        // Pulsing background circle
        Box(
            modifier = Modifier
                .size(120.dp)
                .scale(scale)
                .background(Gold.copy(alpha = 0.2f * alpha), CircleShape)
        )

        // Logo
        Surface(
            modifier = Modifier.size(80.dp),
            shape = CircleShape,
            color = Color.Transparent
        ) {
            Image(
                painter = painterResource(id = R.raw.smartbus),
                contentDescription = "Loading",
                modifier = Modifier.size(60.dp).scale(scale)
            )
        }
    }
}
