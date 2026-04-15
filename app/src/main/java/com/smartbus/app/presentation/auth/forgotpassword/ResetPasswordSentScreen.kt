package com.smartbus.app.presentation.auth.forgotpassword

import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Email
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.smartbus.app.ui.components.SmartBusButton
import com.smartbus.app.ui.theme.Black
import com.smartbus.app.ui.theme.Gold

@Composable
fun ResetPasswordSentScreen(
    onBackToLogin: () -> Unit
) {
    // Animación de pulso del icono
    val infiniteTransition = rememberInfiniteTransition(label = "pulse")
    val scale by infiniteTransition.animateFloat(
        initialValue = 0.95f,
        targetValue = 1.05f,
        animationSpec = infiniteRepeatable(
            animation = tween(800, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "icon_scale"
    )

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Black),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 40.dp)
        ) {
            // Ícono animado
            Box(
                modifier = Modifier
                    .size(120.dp)
                    .scale(scale)
                    .clip(CircleShape)
                    .background(Gold.copy(alpha = 0.15f)),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    Icons.Default.Email,
                    contentDescription = null,
                    tint = Gold,
                    modifier = Modifier.size(60.dp)
                )
            }

            Spacer(modifier = Modifier.height(32.dp))

            Text(
                "¡Correo enviado!",
                style = MaterialTheme.typography.headlineMedium,
                fontWeight = FontWeight.Black,
                color = Gold,
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(12.dp))

            Text(
                "Revisa tu bandeja de entrada y sigue las instrucciones para restablecer tu contraseña.",
                style = MaterialTheme.typography.bodyMedium,
                color = Color.White.copy(alpha = 0.7f),
                textAlign = TextAlign.Center,
                lineHeight = 22.sp
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                "Si no lo encuentras, revisa tu carpeta de spam.",
                style = MaterialTheme.typography.bodySmall,
                color = Color.White.copy(alpha = 0.4f),
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(48.dp))

            // Indicadores de pasos
            Row(
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                StepIndicator(text = "1", label = "Abre el\ncorreo", done = true)
                Box(
                    modifier = Modifier
                        .height(1.dp)
                        .weight(1f)
                        .background(Gold.copy(alpha = 0.3f))
                )
                StepIndicator(text = "2", label = "Haz clic\nen el enlace", done = false)
                Box(
                    modifier = Modifier
                        .height(1.dp)
                        .weight(1f)
                        .background(Gold.copy(alpha = 0.3f))
                )
                StepIndicator(text = "3", label = "Nueva\ncontraseña", done = false)
            }

            Spacer(modifier = Modifier.height(48.dp))

            SmartBusButton(
                text = "Volver al inicio de sesión",
                onClick = onBackToLogin,
                modifier = Modifier.height(50.dp)
            )
        }
    }
}

@Composable
private fun StepIndicator(text: String, label: String, done: Boolean) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Box(
            modifier = Modifier
                .size(36.dp)
                .clip(CircleShape)
                .background(if (done) Gold else Gold.copy(alpha = 0.2f)),
            contentAlignment = Alignment.Center
        ) {
            if (done) {
                Icon(
                    Icons.Default.CheckCircle,
                    contentDescription = null,
                    tint = Black,
                    modifier = Modifier.size(20.dp)
                )
            } else {
                Text(text, color = Gold, fontWeight = FontWeight.Bold, fontSize = 14.sp)
            }
        }
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            label,
            color = Color.White.copy(alpha = 0.5f),
            fontSize = 9.sp,
            textAlign = TextAlign.Center,
            lineHeight = 12.sp
        )
    }
}
