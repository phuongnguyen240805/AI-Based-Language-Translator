package com.example.aibasedlanguagetranslator.chatBoxAI.ui.components

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp
import androidx.compose.ui.graphics.Color
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import com.example.aibasedlanguagetranslator.R

@Composable
fun ChatHeader() {
    val isDark = isSystemInDarkTheme()
    val headerColor = Color(0xFF4285F4)
    val contentColor = Color.White

    Surface(
        color = headerColor,
        modifier = Modifier
            .fillMaxWidth()
            .height(50.dp),
        tonalElevation = 4.dp,
        shadowElevation = 4.dp
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 12.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            Icon(
                painter = painterResource(id = R.drawable.baseline_g_translate_24),
                contentDescription = "App Logo",
                tint = contentColor,
                modifier = Modifier.size(20.dp)
            )

            Spacer(modifier = Modifier.width(8.dp))

            Text(
                text = "AI Chat - English Helper",
                style = MaterialTheme.typography.titleSmall,
                color = contentColor
            )
        }
    }
}
