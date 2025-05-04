package com.example.aibasedlanguagetranslator.ui.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

@Composable
fun TranslateBottomBar() {
    NavigationBar(containerColor = Color.White) {
        NavigationBarItem(
            icon = { Icon(Icons.Default.Home, contentDescription = "Home") },
            label = { Text("Home") },
            selected = true,
            onClick = {},
            colors = NavigationBarItemDefaults.colors(
                selectedIconColor = Color.White,
                selectedTextColor = Color(0xFF4285F4),
                indicatorColor = Color(0xFF4285F4),
                unselectedIconColor = Color.Gray,
                unselectedTextColor = Color.Gray
            )
        )
        NavigationBarItem(
            icon = { Icon(Icons.Default.Star, contentDescription = "Saved") },
            label = { Text("Saved") },
            selected = false,
            onClick = {},
            colors = NavigationBarItemDefaults.colors(
                selectedIconColor = Color.Gray,
                selectedTextColor = Color.Gray,
                indicatorColor = Color.Transparent,
                unselectedIconColor = Color.Gray,
                unselectedTextColor = Color.Gray
            )
        )
        NavigationBarItem(
            icon = { Icon(Icons.Default.Settings, contentDescription = "Settings") },
            label = { Text("Settings") },
            selected = false,
            onClick = {},
            colors = NavigationBarItemDefaults.colors(
                selectedIconColor = Color.Gray,
                selectedTextColor = Color.Gray,
                indicatorColor = Color.Transparent,
                unselectedIconColor = Color.Gray,
                unselectedTextColor = Color.Gray
            )
        )
    }
}
