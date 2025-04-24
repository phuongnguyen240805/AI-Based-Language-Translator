package com.example.aibasedlanguagetranslator

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.material3.TopAppBarDefaults
import com.example.aibasedlanguagetranslator.ui.LanguageappTheme

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main) // Đảm bảo bạn có layout activity_main.xml
        setContent {
            LanguageappTheme {
                TranslateScreen()
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
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
        // Top Bar
        TopAppBar(
            title = {
                Text(
                    "Google Translate",
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )
            },
            colors = TopAppBarDefaults.largeTopAppBarColors(
                containerColor = Color(0xFF4285F4)
            )
        )
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
                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceEvenly) {
                    IconButton(onClick = {}) {
                        Icon(Icons.Default.AccountCircle, contentDescription = null, tint = Color.White)

                    }
                    IconButton(onClick = {}) {
                        Icon(Icons.Default.Share, contentDescription = null, tint = Color.White)
                    }
                    IconButton(onClick = {}) {
                        Icon(Icons.Default.ContentCopy, contentDescription = null, tint = Color.White)
                    }
                }
            }
        }

        Spacer(modifier = Modifier.weight(1f))

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
    LanguageappTheme {
        TranslateScreen()
    }
}