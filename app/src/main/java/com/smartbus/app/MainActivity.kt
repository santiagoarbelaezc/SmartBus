package com.smartbus.app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.smartbus.app.core.navigation.AppNavigation
import com.smartbus.app.ui.theme.SmartBusTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            SmartBusTheme {
                AppNavigation()
            }
        }
    }
}