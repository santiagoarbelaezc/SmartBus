package com.smartbus.app.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.smartbus.app.ui.theme.Black

@Composable
fun GoogleSignInButton(onClick: () -> Unit, modifier: Modifier = Modifier) {
    OutlinedButton(
        onClick = onClick,
        modifier = modifier
            .fillMaxWidth()
            .height(52.dp),
        shape = RoundedCornerShape(16.dp),
        border = androidx.compose.foundation.BorderStroke(1.2.dp, Color.LightGray.copy(alpha = 0.5f)),
        colors = ButtonDefaults.outlinedButtonColors(contentColor = Black)
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            GoogleLogoIcon()
            Spacer(modifier = Modifier.width(12.dp))
            Text("Continuar con Google", fontWeight = FontWeight.Bold, fontSize = 15.sp)
        }
    }
}

@Composable
fun GoogleLogoIcon() {
    androidx.compose.foundation.Canvas(modifier = Modifier.size(20.dp)) {
        val strokeWidth = size.width * 0.25f
        drawArc(
            color = Color(0xFFEA4335),
            startAngle = 180f, sweepAngle = 90f, useCenter = false,
            style = androidx.compose.ui.graphics.drawscope.Stroke(width = strokeWidth),
            size = size, topLeft = androidx.compose.ui.geometry.Offset.Zero
        )
        drawArc(
            color = Color(0xFFFBBC05),
            startAngle = 135f, sweepAngle = 45f, useCenter = false,
            style = androidx.compose.ui.graphics.drawscope.Stroke(width = strokeWidth),
            size = size, topLeft = androidx.compose.ui.geometry.Offset.Zero
        )
        drawArc(
            color = Color(0xFF34A853),
            startAngle = 0f, sweepAngle = 180f, useCenter = false,
            style = androidx.compose.ui.graphics.drawscope.Stroke(width = strokeWidth),
            size = size, topLeft = androidx.compose.ui.geometry.Offset.Zero
        )
        drawArc(
            color = Color(0xFF4285F4),
            startAngle = -45f, sweepAngle = 45f, useCenter = false,
            style = androidx.compose.ui.graphics.drawscope.Stroke(width = strokeWidth),
            size = size, topLeft = androidx.compose.ui.geometry.Offset.Zero
        )
        drawLine(
            color = Color(0xFF4285F4),
            start = androidx.compose.ui.geometry.Offset(size.width * 0.5f, size.height * 0.5f),
            end = androidx.compose.ui.geometry.Offset(size.width, size.height * 0.5f),
            strokeWidth = strokeWidth
        )
    }
}
