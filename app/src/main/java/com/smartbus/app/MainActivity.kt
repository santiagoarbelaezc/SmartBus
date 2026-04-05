package com.smartbus.app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.ConfirmationNumber
import androidx.compose.material.icons.filled.Stars
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.smartbus.app.navigation.AppNavGraph
import com.smartbus.app.navigation.Screen
import com.smartbus.app.ui.components.BottomNavBar
import com.smartbus.app.ui.components.NavigationItem
import com.smartbus.app.ui.theme.SmartBusTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            SmartBusTheme {
                val navController = rememberNavController()
                val navBackStackEntry by navController.currentBackStackEntryAsState()
                val currentRoute = navBackStackEntry?.destination?.route ?: Screen.Home.route

                val navItems = listOf(
                    NavigationItem(Screen.Home.route, Icons.Default.Home, "Inicio"),
                    NavigationItem(Screen.Search.route, Icons.Default.Search, "Buscar"),
                    NavigationItem(Screen.MyTickets.route, Icons.Default.ConfirmationNumber, "Tiquetes"),
                    NavigationItem(Screen.Points.route, Icons.Default.Stars, "Puntos"),
                    NavigationItem(Screen.Profile.route, Icons.Default.Person, "Perfil")
                )

                val showBottomBar = currentRoute in listOf(
                    Screen.Home.route,
                    Screen.Search.route,
                    Screen.MyTickets.route,
                    Screen.Points.route,
                    Screen.Profile.route
                )

                Scaffold(
                    bottomBar = {
                        if (showBottomBar) {
                            BottomNavBar(
                                selectedRoute = currentRoute,
                                onItemClick = { route ->
                                    navController.navigate(route) {
                                        popUpTo(Screen.Home.route) { saveState = true }
                                        launchSingleTop = true
                                        restoreState = true
                                    }
                                },
                                items = navItems
                            )
                        }
                    }
                ) { innerPadding ->
                    Box(modifier = Modifier.padding(
                        bottom = if (showBottomBar) innerPadding.calculateBottomPadding() else 0.dp
                    )) {
                        AppNavGraph(navController = navController)
                    }
                }
            }
        }
    }
}