package com.example.aibasedlanguagetranslator.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.Alignment
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.navigation.NavController

@Composable
fun TranslateScreen(navController: NavController) {
    val isLoggedIn by remember { mutableStateOf(false) }
    val userName by remember { mutableStateOf("Hrittika") }

    var inputText by remember { mutableStateOf("") }
    val translatedText by remember { mutableStateOf("helo main ritika") }

    val previousTranslations = listOf(
        "hello I am Hrittika" to "helo main ritika",
        "How are you?" to "tum kaise ho?",
        "Good morning" to "suprabhat",
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFEAF0FB))
            .padding(12.dp)
    ) {
        // Header
        TranslateHeader(
            isLoggedIn = isLoggedIn,
            userName = userName,
            onLoginClick = { navController.navigate("login") }
        )

        Spacer(modifier = Modifier.height(8.dp))

        // Language Switcher
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text("English", fontSize = 18.sp)
            Icon(Icons.Default.SwapHoriz, contentDescription = null)
            Text("Hindi", fontSize = 18.sp)
        }

        Spacer(modifier = Modifier.height(8.dp))

        // Input Box
        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(8.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White),
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text("ENGLISH", color = Color.Gray, fontSize = 12.sp)

                // TextField with Placeholder
                TextField(
                    value = inputText,
                    onValueChange = { inputText = it },
                    placeholder = { Text("Nhập văn bản") },
                    modifier = Modifier.fillMaxWidth(),
                    colors = TextFieldDefaults.colors(
                        focusedContainerColor = Color.Transparent,
                        unfocusedContainerColor = Color.Transparent,
                        disabledContainerColor = Color.Transparent,
                        focusedIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent
                    )
                )

                Spacer(modifier = Modifier.height(8.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    IconButton(onClick = {}) {
                        Icon(Icons.Default.CameraAlt, contentDescription = "Camera")
                    }
                    IconButton(onClick = {}) {
                        Icon(Icons.Default.Edit, contentDescription = "Handwriting")
                    }
                    IconButton(onClick = {}) {
                        Icon(Icons.Default.Mic, contentDescription = "Microphone")
                    }
                    IconButton(onClick = {}) {
                        Icon(Icons.Default.VolumeUp, contentDescription = "Voice")
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(8.dp))

        // Translated Box
        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(8.dp),
            colors = CardDefaults.cardColors(containerColor = Color(0xFF4285F4))
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text("HINDI", color = Color.White, fontSize = 12.sp)
                Text(
                    translatedText,
                    color = Color.White,
                    fontSize = 18.sp,
                    modifier = Modifier.padding(top = 8.dp)
                )
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    IconButton(onClick = {}) {
                        Icon(Icons.Default.Share, contentDescription = null, tint = Color.White)
                    }
                    IconButton(onClick = {}) {
                        Icon(Icons.Default.ContentCopy, contentDescription = null, tint = Color.White)
                    }
                    IconButton(onClick = {}) {
                        Icon(Icons.Default.StarBorder, contentDescription = "Save", tint = Color.White)
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(12.dp))

        // History Title
        Text(
            text = "History",
            fontWeight = FontWeight.Bold,
            fontSize = 18.sp,
            modifier = Modifier.padding(start = 8.dp, bottom = 4.dp)
        )

        // History List
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
                .background(Color.White)
                .padding(8.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())
            ) {
                previousTranslations.forEach { (original, translated) ->
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 4.dp),
                        shape = RoundedCornerShape(8.dp),
                        colors = CardDefaults.cardColors(containerColor = Color(0xFFF5F5F5))
                    ) {
                        Column(modifier = Modifier.padding(12.dp)) {
                            Text(original, fontSize = 14.sp, color = Color.Gray)
                            Spacer(modifier = Modifier.height(4.dp))
                            Text(translated, fontSize = 16.sp, fontWeight = FontWeight.SemiBold)

                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.End
                            ) {
                                IconButton(onClick = { /* TODO: Copy */ }) {
                                    Icon(Icons.Default.ContentCopy, contentDescription = "Copy", tint = Color.Black)
                                }
                                IconButton(onClick = { /* TODO: Save */ }) {
                                    Icon(Icons.Default.StarBorder, contentDescription = "Save", tint = Color.Black)
                                }
                            }
                        }
                    }
                }
            }
        }

        // Bottom Navigation
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
}


