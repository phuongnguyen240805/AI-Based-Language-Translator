package com.example.aibasedlanguagetranslator.ui.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.Star
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

        // Saved (tạm thời chưa có màn hình, điều hướng lại về "translate" để tránh crash)
        NavigationBarItem(
            icon = { Icon(Icons.Default.Star, contentDescription = "Saved") },
            label = { Text("Saved") },
            selected = currentRoute == "saved",
            onClick = {
                if (currentRoute != "saved") {
                    navController.navigate("translate") // thay "saved" thành "translate" để tránh crash
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
            label = { Text("Settings") },
            selected = currentRoute == "Settings",
            onClick = {
                if (currentRoute != "Settings") {
                    navController.navigate("Settings") {
                        popUpTo("translate")
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
