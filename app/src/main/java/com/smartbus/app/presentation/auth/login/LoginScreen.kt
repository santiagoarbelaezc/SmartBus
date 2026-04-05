package com.smartbus.app.presentation.auth.login

import androidx.compose.animation.*
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.smartbus.app.R
import com.smartbus.app.ui.components.SmartBusButton
import com.smartbus.app.ui.components.SmartBusTextField
import com.smartbus.app.ui.theme.Black
import com.smartbus.app.ui.theme.Gold

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun LoginScreen(
    viewModel: LoginViewModel,
    onLoginSuccess: () -> Unit,
    onNavigateToRegister: () -> Unit
) {
    val uiState by viewModel.uiState.collectAsState()
    val snackbarHostState = remember { SnackbarHostState() }

    LaunchedEffect(uiState.error) {
        uiState.error?.let {
            snackbarHostState.showSnackbar(it)
        }
    }

    Scaffold(
        snackbarHost = { SnackbarHost(snackbarHostState) }
    ) { paddingValues ->
        Box(modifier = Modifier.fillMaxSize().padding(paddingValues).background(Color.White)) {
            // Black Top Background (35%)
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(0.38f)
                    .background(Black),
                contentAlignment = Alignment.Center
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.padding(bottom = 20.dp)
                ) {
                    // Larger smartbus.png icon
                    Image(
                        painter = painterResource(id = R.raw.smartbus),
                        contentDescription = null,
                        modifier = Modifier.size(140.dp) // Significantly larger
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        "SmartBus",
                        color = Gold,
                        style = MaterialTheme.typography.headlineLarge,
                        fontWeight = FontWeight.Black,
                        letterSpacing = 4.sp
                    )
                }
            }

            // White Card Section
            Surface(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(0.68f)
                    .align(Alignment.BottomCenter),
                shape = RoundedCornerShape(topStart = 32.dp, topEnd = 32.dp),
                color = Color.White,
                shadowElevation = 16.dp
            ) {
                Column(
                    modifier = Modifier
                        .padding(horizontal = 32.dp, vertical = 24.dp)
                        .fillMaxSize(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.SpaceBetween
                ) {
                    Column(modifier = Modifier.fillMaxWidth()) {
                        Text(
                            text = "Bienvenido",
                            style = MaterialTheme.typography.headlineSmall,
                            fontWeight = FontWeight.Bold,
                            color = Black
                        )
                        Text(
                            text = "Ingresa para continuar",
                            style = MaterialTheme.typography.bodySmall,
                            color = Color.Gray
                        )
                    }

                    Column(modifier = Modifier.fillMaxWidth()) {
                        SmartBusTextField(
                            value = uiState.email,
                            onValueChange = viewModel::onEmailChange,
                            label = "Correo electrónico",
                            leadingIcon = { Icon(Icons.Default.Email, contentDescription = null, tint = Gold) }
                        )

                        Spacer(modifier = Modifier.height(12.dp))

                        SmartBusTextField(
                            value = uiState.password,
                            onValueChange = viewModel::onPasswordChange,
                            label = "Contraseña",
                            leadingIcon = { Icon(Icons.Default.Lock, contentDescription = null, tint = Gold) },
                            trailingIcon = {
                                IconButton(onClick = viewModel::togglePasswordVisibility) {
                                    val icon = if (uiState.isPasswordVisible) Icons.Default.Visibility else Icons.Default.VisibilityOff
                                    Icon(icon, contentDescription = null, tint = Gold)
                                }
                            },
                            visualTransformation = if (uiState.isPasswordVisible) VisualTransformation.None else PasswordVisualTransformation()
                        )
                        
                        TextButton(
                            onClick = { /* Handle forgot password */ },
                            modifier = Modifier.align(Alignment.End),
                            contentPadding = PaddingValues(0.dp)
                        ) {
                            Text("¿Olvidaste tu contraseña?", color = Gold, fontSize = 11.sp, fontWeight = FontWeight.SemiBold)
                        }
                    }

                    Box(contentAlignment = Alignment.Center) {
                        SmartBusButton(
                            text = if (uiState.isLoading) "" else "Iniciar Sesión",
                            onClick = { viewModel.login(onLoginSuccess) },
                            enabled = !uiState.isLoading,
                            modifier = Modifier.height(50.dp)
                        )
                        if (uiState.isLoading) {
                            CircularProgressIndicator(modifier = Modifier.size(24.dp), color = Color.White, strokeWidth = 2.dp)
                        }
                    }

                    // Social Login Section
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            HorizontalDivider(modifier = Modifier.weight(1f), color = Color.LightGray.copy(alpha = 0.5f))
                            Text(
                                "O continúa con",
                                modifier = Modifier.padding(horizontal = 16.dp),
                                style = MaterialTheme.typography.labelSmall,
                                color = Color.Gray
                            )
                            HorizontalDivider(modifier = Modifier.weight(1f), color = Color.LightGray.copy(alpha = 0.5f))
                        }

                        Spacer(modifier = Modifier.height(16.dp))

                        GoogleSignInButton(onClick = { /* Mock Google Sign-In */ })
                    }

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text("¿No tienes cuenta?", color = Color.Gray, fontSize = 13.sp)
                        TextButton(onClick = onNavigateToRegister) {
                            Text("Regístrate", color = Gold, fontWeight = FontWeight.Bold, fontSize = 13.sp)
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun GoogleSignInButton(onClick: () -> Unit) {
    OutlinedButton(
        onClick = onClick,
        modifier = Modifier
            .fillMaxWidth()
            .height(50.dp),
        shape = RoundedCornerShape(12.dp),
        border = androidx.compose.foundation.BorderStroke(1.dp, Color.LightGray.copy(alpha = 0.5f)),
        colors = ButtonDefaults.outlinedButtonColors(contentColor = Black)
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            GoogleLogoIcon()
            Spacer(modifier = Modifier.width(12.dp))
            Text("Continuar con Google", fontWeight = FontWeight.SemiBold, fontSize = 14.sp)
        }
    }
}

@Composable
fun GoogleLogoIcon() {
    androidx.compose.foundation.Canvas(modifier = Modifier.size(20.dp)) {
        val strokeWidth = size.width * 0.25f
        
        // Google Red (Top segment)
        drawArc(
            color = Color(0xFFEA4335),
            startAngle = 180f, sweepAngle = 90f, useCenter = false,
            style = androidx.compose.ui.graphics.drawscope.Stroke(width = strokeWidth),
            size = size, topLeft = androidx.compose.ui.geometry.Offset.Zero
        )
        // Google Yellow (Left segment)
        drawArc(
            color = Color(0xFFFBBC05),
            startAngle = 135f, sweepAngle = 45f, useCenter = false,
            style = androidx.compose.ui.graphics.drawscope.Stroke(width = strokeWidth),
            size = size, topLeft = androidx.compose.ui.geometry.Offset.Zero
        )
        // Google Green (Bottom segment)
        drawArc(
            color = Color(0xFF34A853),
            startAngle = 0f, sweepAngle = 180f, useCenter = false,
            style = androidx.compose.ui.graphics.drawscope.Stroke(width = strokeWidth),
            size = size, topLeft = androidx.compose.ui.geometry.Offset.Zero
        )
        // Google Blue (Right segment)
        drawArc(
            color = Color(0xFF4285F4),
            startAngle = -45f, sweepAngle = 45f, useCenter = false,
            style = androidx.compose.ui.graphics.drawscope.Stroke(width = strokeWidth),
            size = size, topLeft = androidx.compose.ui.geometry.Offset.Zero
        )
        // Horizontal bar
        drawLine(
            color = Color(0xFF4285F4),
            start = androidx.compose.ui.geometry.Offset(size.width * 0.5f, size.height * 0.5f),
            end = androidx.compose.ui.geometry.Offset(size.width, size.height * 0.5f),
            strokeWidth = strokeWidth
        )
    }
}
