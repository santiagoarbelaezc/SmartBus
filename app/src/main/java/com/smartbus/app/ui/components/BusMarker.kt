package com.smartbus.app.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DirectionsBus
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.smartbus.app.ui.theme.Black
import com.smartbus.app.ui.theme.Gold

@Composable
fun BusMarker() {
    Surface(
        modifier = Modifier
            .size(48.dp)
            .clip(CircleShape)
            .background(Gold)
            .padding(2.dp)
            .clip(CircleShape)
            .background(Black),
        shape = CircleShape,
        color = Color.Transparent
    ) {
        Box(contentAlignment = Alignment.Center) {
            Icon(
                Icons.Default.DirectionsBus,
                contentDescription = null,
                tint = Gold,
                modifier = Modifier.size(24.dp)
            )
        }
    }
}
