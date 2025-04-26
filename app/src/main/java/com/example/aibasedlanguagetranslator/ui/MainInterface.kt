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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.Alignment
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll

@Composable
fun TranslateScreen() {
    var inputText by remember { mutableStateOf("hello I am Hrittika") }
    val translatedText by remember { mutableStateOf("helo main ritika") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFEAF0FB))
            .padding(12.dp)
    ) {
        // Custom Header
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(WindowInsets.statusBars.asPaddingValues()) // Né status bar tự động
                .height(56.dp)
                .background(Color(0xFF4285F4))
                .padding(horizontal = 12.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Left side: Logo
            Icon(
                imageVector = Icons.Default.Translate,
                contentDescription = "Logo",
                tint = Color.White,
                modifier = Modifier.size(32.dp)
            )

            // Right side: Avatar + Account Name
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    imageVector = Icons.Default.AccountCircle,
                    contentDescription = "Avatar",
                    tint = Color.White,
                    modifier = Modifier.size(36.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = "Hrittika",
                    color = Color.White,
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp
                )
            }
        }

        Spacer(modifier = Modifier.height(8.dp))

        // Language Switcher
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            horizontalArrangement = Arrangement.SpaceBetween
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
                TextField(
                    value = inputText,
                    onValueChange = { inputText = it },
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
                Row(horizontalArrangement = Arrangement.SpaceAround) {
                    IconButton(onClick = {}) {
                        Icon(Icons.Default.CameraAlt, contentDescription = "Camera")
                    }
                    IconButton(onClick = {}) {
                        Icon(Icons.Default.Edit, contentDescription = "Handwriting")
                    }
                    IconButton(onClick = {}) {
                        Icon(Icons.Default.Mic, contentDescription = "Conversation")
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
                        Icon(
                            Icons.Default.AccountCircle,
                            contentDescription = null,
                            tint = Color.White
                        )

                    }
                    IconButton(onClick = {}) {
                        Icon(Icons.Default.Share, contentDescription = null, tint = Color.White)
                    }
                    IconButton(onClick = {}) {
                        Icon(
                            Icons.Default.ContentCopy,
                            contentDescription = null,
                            tint = Color.White
                        )
                    }
                }
            }
        }

        // Dummy previous translations
        val previousTranslations = listOf(
            "hello I am Hrittika" to "helo main ritika",
            "How are you?" to "tum kaise ho?",
            "Good morning" to "suprabhat",
        )

        Spacer(modifier = Modifier.height(12.dp))

        Text(
            text = "History",
            fontWeight = FontWeight.Bold,
            fontSize = 18.sp,
            modifier = Modifier.padding(start = 8.dp, bottom = 4.dp)
        )

        // History Box (scroll riêng và chiếm hết không gian còn lại)
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f) // Chiếm phần còn lại của Column
                .background(Color.White)
                .padding(8.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState()) // Scroll riêng History
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
                                IconButton(onClick = { /* TODO: Copy action */ }) {
                                    Icon(
                                        Icons.Default.ContentCopy,
                                        contentDescription = "Copy",
                                        tint = Color.Black
                                    )
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
                onClick = {}
            )
            NavigationBarItem(
                icon = { Icon(Icons.Default.Star, contentDescription = "Saved") },
                label = { Text("Saved") },
                selected = false,
                onClick = {}
            )
            NavigationBarItem(
                icon = { Icon(Icons.Default.Settings, contentDescription = "Settings") },
                label = { Text("Settings") },
                selected = false,
                onClick = {}
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    TranslateScreen()
}
