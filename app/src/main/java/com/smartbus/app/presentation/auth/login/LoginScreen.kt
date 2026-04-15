package com.smartbus.app.presentation.auth.login

import androidx.compose.animation.*
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
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
import androidx.compose.ui.draw.clip
import com.smartbus.app.ui.components.SmartBusButton
import com.smartbus.app.ui.components.SmartBusTextField
import com.smartbus.app.ui.components.SmartBusLoadingOverlay
import com.smartbus.app.ui.components.GoogleSignInButton
import com.smartbus.app.ui.theme.Black
import com.smartbus.app.ui.theme.Gold

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun LoginScreen(
    viewModel: LoginViewModel,
    onLoginSuccess: () -> Unit,
    onNavigateToRegister: () -> Unit,
    onNavigateToForgotPassword: () -> Unit = {}
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
            // Black Top Background (38%)
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(0.38f)
                    .background(Black),
                contentAlignment = Alignment.TopCenter
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.padding(top = 32.dp)
                ) {
                    Image(
                        painter = painterResource(id = R.raw.smartbus),
                        contentDescription = null,
                        modifier = Modifier.size(220.dp)
                    )
                }
            }

            // White Card Section (68%)
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
                        .padding(start = 32.dp, top = 32.dp, end = 32.dp, bottom = 24.dp)
                        .fillMaxSize()
                        .verticalScroll(rememberScrollState()),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(24.dp)
                ) {
                    // ── Header ────────────────────────────────────────
                    Column(modifier = Modifier.fillMaxWidth()) {

                        // "SmartBus" label with gold accent bar
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Box(
                                modifier = Modifier
                                    .width(4.dp)
                                    .height(28.dp)
                                    .clip(RoundedCornerShape(2.dp))
                                    .background(Gold)
                            )
                            Spacer(modifier = Modifier.width(10.dp))
                            Text(
                                "SmartBus",
                                color = Gold,
                                style = MaterialTheme.typography.headlineMedium,
                                fontWeight = FontWeight.Black,
                                letterSpacing = 1.3.sp
                            )
                        }

                        Spacer(modifier = Modifier.height(10.dp))

                        Text(
                            text = "Bienvenido de nuevo",
                            style = MaterialTheme.typography.headlineSmall,
                            fontWeight = FontWeight.ExtraBold,
                            color = Black
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = "Ingresa tus credenciales para continuar",
                            style = MaterialTheme.typography.bodySmall,
                            color = Color.Gray,
                            letterSpacing = 0.2.sp
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
                            onClick = onNavigateToForgotPassword,
                            modifier = Modifier.align(Alignment.End),
                            contentPadding = PaddingValues(0.dp)
                        ) {
                            Text("¿Olvidaste tu contraseña?", color = Gold, fontSize = 11.sp, fontWeight = FontWeight.SemiBold)
                        }
                    }

                    SmartBusButton(
                        text = "Iniciar Sesión",
                        onClick = { viewModel.login(onLoginSuccess) },
                        modifier = Modifier.height(50.dp),
                        enabled = true // Always allow
                    )

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

            // High-fidelity Loading Overlay
            if (uiState.isLoading) {
                SmartBusLoadingOverlay()
            }
        }
    }
}
