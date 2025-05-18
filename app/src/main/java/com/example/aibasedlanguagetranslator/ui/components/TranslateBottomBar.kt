package com.example.aibasedlanguagetranslator.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Chat
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState

@Composable
fun TranslateBottomBar(
    navController: NavController,
    isDarkMode: Boolean
) {
    val currentRoute = navController.currentBackStackEntryAsState().value?.destination?.route

    val backgroundColor = if (isDarkMode) Color(0xFF121212) else Color.White
    val selectedColor = Color(0xFF4285F4)
    val unselectedColor = if (isDarkMode) Color.LightGray else Color.Gray
    val borderColor = if (isDarkMode) Color(0xFF4285F4).copy(alpha = 0.3f) else Color.Transparent

    Column {
        // Viền trên dày 2.dp, rộng full, màu xanh
        Divider(
            color = borderColor,
            thickness = 1.dp,
            modifier = Modifier.fillMaxWidth()
        )

        NavigationBar(containerColor = backgroundColor) {
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
                    selectedTextColor = selectedColor,
                    indicatorColor = selectedColor,
                    unselectedIconColor = unselectedColor,
                    unselectedTextColor = unselectedColor
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
                    selectedTextColor = selectedColor,
                    indicatorColor = selectedColor,
                    unselectedIconColor = unselectedColor,
                    unselectedTextColor = unselectedColor
                )
            )

            NavigationBarItem(
                icon = { Icon(Icons.Default.Settings, contentDescription = "Settings") },
                label = { Text("Settings") },
                selected = currentRoute == "settings",
                onClick = {
                    if (currentRoute != "settings") {
                        navController.navigate("settings") {
                            popUpTo("translate") { inclusive = true }
                        }
                    }
                },
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = Color.White,
                    selectedTextColor = selectedColor,
                    indicatorColor = selectedColor,
                    unselectedIconColor = unselectedColor,
                    unselectedTextColor = unselectedColor
                )
            )
        }
    }
}
