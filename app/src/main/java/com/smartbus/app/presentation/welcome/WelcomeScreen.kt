package com.smartbus.app.presentation.welcome

import androidx.compose.animation.*
import androidx.compose.animation.core.*
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.smartbus.app.R
import com.smartbus.app.ui.components.GoogleSignInButton
import com.smartbus.app.ui.theme.Black
import com.smartbus.app.ui.theme.Gold

@Composable
fun WelcomeScreen(
    onNavigateToLogin: () -> Unit,
    onNavigateToRegister: () -> Unit,
    onGoogleSignIn: () -> Unit
) {
    var startAnimations by remember { mutableStateOf(false) }
    
    LaunchedEffect(Unit) {
        startAnimations = true
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(
                        Black,
                        Color(0xFF1A1A1A),
                        Black
                    )
                )
            )
    ) {
        // Decorative background glow - moved higher and subtle
        Surface(
            modifier = Modifier
                .align(Alignment.TopCenter)
                .offset(y = (-90).dp)
                .size(380.dp),
            color = Gold.copy(alpha = 0.04f),
            shape = androidx.compose.foundation.shape.CircleShape,
            border = null
        ) {}

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 32.dp, vertical = 40.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.padding(top = 10.dp) // Pushes everything up
            ) {
                // Logo & Title Section - Entry from top
                AnimatedVisibility(
                    visible = startAnimations,
                    enter = fadeIn(tween(800)) + slideInVertically(tween(800)) { -80 }
                ) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Image(
                            painter = painterResource(id = R.raw.smartbus),
                            contentDescription = null,
                            modifier = Modifier.size(190.dp)
                        )
                        
                        Spacer(modifier = Modifier.height(8.dp))

                        Text(
                            text = "SmartBus",
                            color = Gold,
                            style = MaterialTheme.typography.displayMedium,
                            fontWeight = FontWeight.Black,
                            letterSpacing = 8.sp,
                            textAlign = TextAlign.Center
                        )
                        
                        Text(
                            text = "COLOMBIA",
                            color = Gold.copy(alpha = 0.6f),
                            style = MaterialTheme.typography.labelLarge,
                            letterSpacing = 6.sp
                        )
                        
                        Spacer(modifier = Modifier.height(20.dp))

                        Text(
                            text = "Viaja con inteligencia, comodidad y seguridad por todo el país.",
                            color = Color.White.copy(alpha = 0.7f),
                            style = MaterialTheme.typography.bodyLarge,
                            textAlign = TextAlign.Center,
                            lineHeight = 26.sp,
                            modifier = Modifier.padding(horizontal = 8.dp)
                        )
                    }
                }
            }

            // Actions Container Animation
            AnimatedVisibility(
                visible = startAnimations,
                enter = fadeIn(tween(800, delayMillis = 600)) + slideInVertically(tween(800, delayMillis = 600)) { 40 }
            ) {
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    verticalArrangement = Arrangement.spacedBy(14.dp)
                ) {
                    // Unified Button Styles: Both use the premium glass/outline style as requested
                    WelcomeActionButton(
                        text = "Iniciar Sesión",
                        onClick = onNavigateToLogin,
                        isPrimary = true
                    )

                    WelcomeActionButton(
                        text = "Crear Cuenta",
                        onClick = onNavigateToRegister,
                        isPrimary = false
                    )

                    Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.padding(vertical = 4.dp)) {
                        HorizontalDivider(modifier = Modifier.weight(1f), color = Color.White.copy(alpha = 0.15f))
                        Text(
                            "o",
                            modifier = Modifier.padding(horizontal = 16.dp),
                            color = Color.White.copy(alpha = 0.4f),
                            style = MaterialTheme.typography.bodySmall,
                            fontSize = 12.sp
                        )
                        HorizontalDivider(modifier = Modifier.weight(1f), color = Color.White.copy(alpha = 0.15f))
                    }

                    GoogleSignInButton(
                        onClick = onGoogleSignIn,
                        modifier = Modifier.height(56.dp)
                    )
                }
            }
        }
    }
}

@Composable
fun WelcomeActionButton(
    text: String,
    onClick: () -> Unit,
    isPrimary: Boolean
) {
    Button(
        onClick = onClick,
        modifier = Modifier.fillMaxWidth().height(56.dp),
        shape = RoundedCornerShape(16.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = if (isPrimary) Gold.copy(alpha = 0.15f) else Color.White.copy(alpha = 0.08f),
            contentColor = Gold
        ),
        border = androidx.compose.foundation.BorderStroke(
            1.2.dp, 
            if (isPrimary) Gold else Gold.copy(alpha = 0.4f)
        )
    ) {
        Text(text, fontWeight = FontWeight.ExtraBold, fontSize = 16.sp)
    }
}
