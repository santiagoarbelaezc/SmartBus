package com.smartbus.app.ui.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.HorizontalDivider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.unit.dp
import com.smartbus.app.ui.theme.BorderGold

@Composable
fun TicketCard(
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit
) {
    SmartBusCard(modifier = modifier) {
        content()
        DashedDivider(modifier = Modifier.padding(vertical = 16.dp))
    }
}
