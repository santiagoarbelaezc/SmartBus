package com.smartbus.app.presentation.terms

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.smartbus.app.ui.theme.Black
import com.smartbus.app.ui.theme.Gold
import com.smartbus.app.ui.theme.White

private data class TermsSection(val number: String, val title: String, val body: String)

private val sections = listOf(
    TermsSection("1", "Aceptación de Términos",
        "Al utilizar SmartBus aceptas estos términos y condiciones en su totalidad. Si no estás de acuerdo con alguna parte, por favor abstente de usar la aplicación."),
    TermsSection("2", "Descripción del Servicio",
        "SmartBus es una plataforma de compra y gestión de tiquetes de transporte terrestre interprovincial en Colombia. No somos la empresa transportadora; actuamos como intermediario tecnológico."),
    TermsSection("3", "Registro y Cuenta",
        "Debes proporcionar información veraz al crear tu cuenta. Eres responsable de mantener la confidencialidad de tu contraseña. Notifícanos inmediatamente si sospechas uso no autorizado."),
    TermsSection("4", "Compras y Pagos",
        "Los precios mostrados incluyen todos los impuestos aplicables. El pago se procesa de forma segura vía pasarelas certificadas PCI-DSS. SmartBus no almacena datos de tarjetas."),
    TermsSection("5", "Política de Cancelación",
        "Cancelaciones hasta 2 horas antes de la salida aplican para reembolso parcial según la empresa. Pasadas las 2 horas, el tiquete no es reembolsable. SmartBus cobra una tarifa de servicio no reembolsable."),
    TermsSection("6", "Privacidad de Datos",
        "Tus datos personales son tratados conforme a la Ley 1581 de 2012 (Colombia). Solo compartimos información necesaria con las empresas de transporte para ejecutar el servicio."),
    TermsSection("7", "Limitación de Responsabilidad",
        "SmartBus no se hace responsable por retrasos, cancelaciones o incidentes ocurridos durante el viaje. La responsabilidad recae en la empresa transportadora."),
    TermsSection("8", "Modificaciones",
        "Nos reservamos el derecho de modificar estos términos. Te notificaremos por correo electrónico y dentro de la app con al menos 15 días de anticipación."),
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TermsScreen(onNavigateBack: () -> Unit) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Términos y Condiciones", fontWeight = FontWeight.Bold, color = White) },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(Icons.Default.ArrowBack, null, tint = White)
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = Black)
            )
        },
        containerColor = Color(0xFFF8F9FA)
    ) { padding ->
        LazyColumn(
            modifier = Modifier.padding(padding).fillMaxSize(),
            contentPadding = PaddingValues(20.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            item {
                Card(
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(containerColor = Gold.copy(alpha = 0.08f)),
                    border = androidx.compose.foundation.BorderStroke(1.dp, Gold.copy(alpha = 0.3f))
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text("📋 Última actualización", color = Gold, fontSize = 11.sp, fontWeight = FontWeight.SemiBold)
                        Text("14 de Abril de 2026", color = Black, fontWeight = FontWeight.Bold, fontSize = 14.sp)
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            "Por favor lee detenidamente estos términos antes de usar SmartBus.",
                            fontSize = 12.sp, color = Color.Gray
                        )
                    }
                }
                Spacer(modifier = Modifier.height(4.dp))
            }

            items(sections.size) { i ->
                val section = sections[i]
                Card(
                    shape = RoundedCornerShape(14.dp),
                    colors = CardDefaults.cardColors(containerColor = White),
                    elevation = CardDefaults.cardElevation(1.dp)
                ) {
                    Column(modifier = Modifier.padding(18.dp)) {
                        Row(verticalAlignment = androidx.compose.ui.Alignment.CenterVertically) {
                            Surface(
                                color = Black,
                                shape = RoundedCornerShape(8.dp)
                            ) {
                                Text(
                                    section.number,
                                    color = Gold,
                                    fontWeight = FontWeight.ExtraBold,
                                    fontSize = 13.sp,
                                    modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp)
                                )
                            }
                            Spacer(modifier = Modifier.width(12.dp))
                            Text(section.title, fontWeight = FontWeight.ExtraBold, fontSize = 15.sp, color = Black)
                        }
                        Spacer(modifier = Modifier.height(10.dp))
                        Text(section.body, fontSize = 13.sp, color = Color.Gray, lineHeight = 19.sp)
                    }
                }
            }

            item { Spacer(modifier = Modifier.height(8.dp)) }
        }
    }
}
