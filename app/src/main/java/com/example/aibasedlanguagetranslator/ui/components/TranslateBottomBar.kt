package com.example.aibasedlanguagetranslator.ui.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Chat
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState

@Composable
fun TranslateBottomBar(navController: NavController) {
    val currentRoute = navController.currentBackStackEntryAsState().value?.destination?.route

    NavigationBar(containerColor = Color.White) {
        // Home (route = "translate")
        NavigationBarItem(
            icon = { Icon(Icons.Default.Home, contentDescription = "Home") },
            label = { Text("Home") },
            selected = currentRoute == "translate",
            onClick = {
                if (currentRoute != "translate") {
                    navController.navigate("translate") {
                        popUpTo("translate") { inclusive = true }
                    }
                }
            },
            colors = NavigationBarItemDefaults.colors(
                selectedIconColor = Color.White,
                selectedTextColor = Color(0xFF4285F4),
                indicatorColor = Color(0xFF4285F4),
                unselectedIconColor = Color.Gray,
                unselectedTextColor = Color.Gray
            )
        )

        NavigationBarItem(
            icon = { Icon(Icons.Default.Chat, contentDescription = "Chat") },
            label = { Text("Chat") },
            selected = currentRoute == "chat",
            onClick = {
                if (currentRoute != "chat") {
                    navController.navigate("chat")
                }
            },
            colors = NavigationBarItemDefaults.colors(
                selectedIconColor = Color.White,
                selectedTextColor = Color(0xFF4285F4),
                indicatorColor = Color(0xFF4285F4),
                unselectedIconColor = Color.Gray,
                unselectedTextColor = Color.Gray
            )
        )

        // Settings (route = "Settings" với chữ S hoa như trong NavGraph)
        NavigationBarItem(
            icon = { Icon(Icons.Default.Settings, contentDescription = "Settings") },
            label = { Text("settings") },
            selected = currentRoute == "settings", // So sánh currentRoute với route "Settings"
            onClick = {
                if (currentRoute != "settings") {
                    navController.navigate("settings") {
                        // Điều hướng đến Settings và pop các màn hình trên stack
                        popUpTo("translate") { inclusive = true }
                    }
                }
            },
            colors = NavigationBarItemDefaults.colors(
                selectedIconColor = Color.White,
                selectedTextColor = Color(0xFF4285F4),
                indicatorColor = Color(0xFF4285F4),
                unselectedIconColor = Color.Gray,
                unselectedTextColor = Color.Gray
            )
        )
    }
}

