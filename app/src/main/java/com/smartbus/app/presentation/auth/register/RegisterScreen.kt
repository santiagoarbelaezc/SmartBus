package com.smartbus.app.presentation.auth.register

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RegisterScreen(
    viewModel: RegisterViewModel,
    onRegisterSuccess: () -> Unit,
    onNavigateToLogin: () -> Unit
) {
    val uiState by viewModel.uiState.collectAsState()
    var showDocTypeMenu by remember { mutableStateOf(false) }

    Box(modifier = Modifier.fillMaxSize().background(Color.White)) {
        // Black Top Background (30%)
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(0.3f)
                .background(Black),
            contentAlignment = Alignment.Center
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.padding(bottom = 20.dp)
            ) {
                Image(
                    painter = painterResource(id = R.raw.icon_smartbus),
                    contentDescription = null,
                    modifier = Modifier.size(60.dp)
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "Crea tu Cuenta",
                    color = Gold,
                    style = MaterialTheme.typography.headlineSmall,
                    fontWeight = FontWeight.Bold,
                    letterSpacing = 2.sp
                )
            }
        }

        // White Card Section (75%)
        Surface(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(0.75f)
                .align(Alignment.BottomCenter),
            shape = RoundedCornerShape(topStart = 32.dp, topEnd = 32.dp),
            color = Color.White,
            shadowElevation = 16.dp
        ) {
            LazyColumn(
                modifier = Modifier
                    .padding(horizontal = 32.dp)
                    .fillMaxSize(),
                contentPadding = PaddingValues(vertical = 32.dp)
            ) {
                item {
                    Text(
                        text = "Información Personal",
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.Bold,
                        color = Black
                    )
                    Text(
                        text = "Completa tus datos para viajar seguro",
                        style = MaterialTheme.typography.bodySmall,
                        color = Color.Gray
                    )
                    Spacer(modifier = Modifier.height(24.dp))
                }

                item {
                    SmartBusTextField(
                        value = uiState.fullName,
                        onValueChange = viewModel::onFullNameChange,
                        label = "Nombre completo",
                        leadingIcon = { Icon(Icons.Default.Person, contentDescription = null, tint = Gold) }
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                }

                item {
                    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                        // Document Type Dropdown (Mock)
                        Box(modifier = Modifier.weight(0.35f)) {
                            ExposedDropdownMenuBox(
                                expanded = showDocTypeMenu,
                                onExpandedChange = { showDocTypeMenu = it }
                            ) {
                                OutlinedTextField(
                                    value = uiState.documentType,
                                    onValueChange = {},
                                    readOnly = true,
                                    label = { Text("Tipo", fontSize = 12.sp) },
                                    modifier = Modifier.menuAnchor(),
                                    trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = showDocTypeMenu) },
                                    shape = RoundedCornerShape(12.dp),
                                    colors = OutlinedTextFieldDefaults.colors(
                                        focusedBorderColor = Gold,
                                        unfocusedBorderColor = Gold.copy(alpha = 0.3f)
                                    )
                                )
                                ExposedDropdownMenu(
                                    expanded = showDocTypeMenu,
                                    onDismissRequest = { showDocTypeMenu = false }
                                ) {
                                    documentTypes.forEach { type ->
                                        DropdownMenuItem(
                                            text = { Text(type) },
                                            onClick = {
                                                viewModel.onDocumentTypeChange(type)
                                                showDocTypeMenu = false
                                            }
                                        )
                                    }
                                }
                            }
                        }

                        // Document Number
                        SmartBusTextField(
                            value = uiState.documentNumber,
                            onValueChange = viewModel::onDocumentNumberChange,
                            label = "Documento",
                            modifier = Modifier.weight(0.65f),
                            leadingIcon = { Icon(Icons.Default.Badge, contentDescription = null, tint = Gold) }
                        )
                    }
                    Spacer(modifier = Modifier.height(16.dp))
                }

                item {
                    SmartBusTextField(
                        value = uiState.phone,
                        onValueChange = viewModel::onPhoneChange,
                        label = "Celular",
                        leadingIcon = { 
                            Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.padding(start = 12.dp)) {
                                Text("🇨🇴", fontSize = 16.sp)
                                Spacer(modifier = Modifier.width(4.dp))
                                Text("+57", color = Black, fontWeight = FontWeight.Bold, fontSize = 14.sp)
                                VerticalDivider(modifier = Modifier.height(16.dp).padding(horizontal = 8.dp), color = Gold.copy(alpha = 0.3f))
                            }
                        }
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                }

                item {
                    SmartBusTextField(
                        value = uiState.email,
                        onValueChange = viewModel::onEmailChange,
                        label = "Correo electrónico",
                        leadingIcon = { Icon(Icons.Default.Email, contentDescription = null, tint = Gold) }
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                }

                item {
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
                    Spacer(modifier = Modifier.height(16.dp))
                }

                item {
                    SmartBusTextField(
                        value = uiState.confirmPassword,
                        onValueChange = viewModel::onConfirmPasswordChange,
                        label = "Confirmar contraseña",
                        leadingIcon = { Icon(Icons.Default.Lock, contentDescription = null, tint = Gold) },
                        visualTransformation = if (uiState.isPasswordVisible) VisualTransformation.None else PasswordVisualTransformation()
                    )
                    Spacer(modifier = Modifier.height(24.dp))
                }

                item {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Checkbox(
                            checked = uiState.termsAccepted,
                            onCheckedChange = viewModel::onTermsToggle,
                            colors = CheckboxDefaults.colors(checkedColor = Gold, uncheckedColor = Gold.copy(alpha = 0.5f))
                        )
                        Text(
                            text = "Acepto los Términos y Condiciones y la Política de Privacidad",
                            style = MaterialTheme.typography.bodySmall,
                            color = Black.copy(alpha = 0.7f),
                            fontSize = 11.sp
                        )
                    }
                    Spacer(modifier = Modifier.height(32.dp))
                }

                item {
                    SmartBusButton(
                        text = "Registrarme",
                        onClick = onRegisterSuccess,
                        enabled = uiState.termsAccepted
                    )
                    Spacer(modifier = Modifier.height(24.dp))
                }

                item {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text("¿Ya tienes una cuenta?", color = Color.Gray, fontSize = 14.sp)
                        TextButton(onClick = onNavigateToLogin) {
                            Text("Inicia Sesión", color = Gold, fontWeight = FontWeight.Bold, fontSize = 14.sp)
                        }
                    }
                }
            }
        }
    }
}
