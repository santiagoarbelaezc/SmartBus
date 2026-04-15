package com.smartbus.app.ui.components

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.smartbus.app.ui.theme.Black
import com.smartbus.app.ui.theme.Gold
import com.smartbus.app.ui.theme.White

data class NavigationItem(
    val route: String,
    val icon: ImageVector,
    val label: String,
    val badgeCount: Int = 0
)

@Composable
fun BottomNavBar(
    selectedRoute: String,
    onItemClick: (String) -> Unit,
    items: List<NavigationItem>
) {
    NavigationBar(
        containerColor = White,
        tonalElevation = 12.dp,
        modifier = Modifier.fillMaxWidth()
    ) {
        items.forEach { item ->
            val isSelected = selectedRoute.contains(item.route, ignoreCase = true)

            val iconTint by animateColorAsState(
                targetValue = if (isSelected) Gold else Color(0xFF9E9E9E),
                animationSpec = tween(250),
                label = "icon_tint_${item.route}"
            )
            val labelColor by animateColorAsState(
                targetValue = if (isSelected) Black else Color(0xFF9E9E9E),
                animationSpec = tween(250),
                label = "label_color_${item.route}"
            )
            val pillWidth by animateDpAsState(
                targetValue = if (isSelected) 32.dp else 0.dp,
                animationSpec = tween(250),
                label = "pill_width_${item.route}"
            )

            NavigationBarItem(
                selected = isSelected,
                onClick = { onItemClick(item.route) },
                icon = {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        // Badge + Icon
                        BadgedBox(
                            badge = {
                                if (item.badgeCount > 0) {
                                    Badge(
                                        containerColor = Gold,
                                        contentColor = Black
                                    ) {
                                        Text(
                                            text = item.badgeCount.toString(),
                                            fontSize = 9.sp,
                                            fontWeight = FontWeight.Bold
                                        )
                                    }
                                }
                            }
                        ) {
                            Icon(
                                imageVector = item.icon,
                                contentDescription = item.label,
                                tint = iconTint,
                                modifier = Modifier.size(24.dp)
                            )
                        }

                        // Gold pill indicator below icon
                        Box(
                            modifier = Modifier
                                .padding(top = 4.dp)
                                .height(3.dp)
                                .width(pillWidth)
                                .clip(RoundedCornerShape(50))
                                .background(Gold)
                        )
                    }
                },
                label = {
                    Text(
                        text = item.label,
                        color = labelColor,
                        fontSize = 10.sp,
                        fontWeight = if (isSelected) FontWeight.SemiBold else FontWeight.Normal
                    )
                },
                colors = NavigationBarItemDefaults.colors(
                    indicatorColor = Color.Transparent,
                    selectedIconColor = Gold,
                    unselectedIconColor = Color(0xFF9E9E9E)
                )
            )
        }
    }
}
