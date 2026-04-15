package com.smartbus.app.ui.components

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.smartbus.app.ui.theme.Black
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
    modifier: Modifier = Modifier,
    label: String = ""
) {
    val bgColor by animateColorAsState(
        targetValue = when (status) {
            SeatStatus.AVAILABLE -> White
            SeatStatus.SELECTED  -> Gold
            SeatStatus.OCCUPIED  -> Color(0xFFE0E0E0)
        },
        animationSpec = tween(180),
        label = "seat_bg"
    )
    val textColor = when (status) {
        SeatStatus.AVAILABLE -> Color(0xFF555555)
        SeatStatus.SELECTED  -> Black
        SeatStatus.OCCUPIED  -> Color(0xFFAAAAAA)
    }
    val borderColor = when (status) {
        SeatStatus.AVAILABLE -> Color(0xFFDDDDDD)
        SeatStatus.SELECTED  -> Gold
        SeatStatus.OCCUPIED  -> Color.Transparent
    }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
    ) {
        // Seat back (headrest)
        Box(
            modifier = Modifier
                .width(36.dp)
                .height(10.dp)
                .clip(RoundedCornerShape(topStart = 6.dp, topEnd = 6.dp))
                .background(
                    when (status) {
                        SeatStatus.AVAILABLE -> Color(0xFFBBBBBB)
                        SeatStatus.SELECTED  -> Color(0xFFD4A017)
                        SeatStatus.OCCUPIED  -> Color(0xFFCCCCCC)
                    }
                )
        )
        // Seat body
        Box(
            modifier = Modifier
                .size(width = 36.dp, height = 34.dp)
                .clip(RoundedCornerShape(bottomStart = 8.dp, bottomEnd = 8.dp, topStart = 2.dp, topEnd = 2.dp))
                .background(bgColor)
                .border(1.dp, borderColor, RoundedCornerShape(bottomStart = 8.dp, bottomEnd = 8.dp, topStart = 2.dp, topEnd = 2.dp))
                .clickable(enabled = status != SeatStatus.OCCUPIED) { onClick() },
            contentAlignment = Alignment.Center
        ) {
            if (label.isNotEmpty()) {
                Text(
                    label,
                    fontSize = 9.sp,
                    fontWeight = FontWeight.Bold,
                    color = textColor
                )
            }
        }
    }
}
