package com.smartbus.app.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.smartbus.app.ui.theme.BorderGold
import com.smartbus.app.ui.theme.Charcoal
import com.smartbus.app.ui.theme.Gold
import com.smartbus.app.ui.theme.White

enum class SeatStatus {
    AVAILABLE, SELECTED, OCCUPIED
}

@Composable
fun SeatBox(
    status: SeatStatus,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val backgroundColor = when (status) {
        SeatStatus.AVAILABLE -> White
        SeatStatus.SELECTED -> Gold
        SeatStatus.OCCUPIED -> Charcoal
    }

    val borderColor = if (status == SeatStatus.AVAILABLE) BorderGold else Color.Transparent

    Box(
        modifier = modifier
            .size(48.dp)
            .clip(RoundedCornerShape(8.dp))
            .background(backgroundColor)
            .then(
                if (status == SeatStatus.AVAILABLE) Modifier.border(1.dp, borderColor, RoundedCornerShape(8.dp))
                else Modifier
            )
            .clickable(enabled = status != SeatStatus.OCCUPIED) { onClick() }
    )
}
