package com.example.aibasedlanguagetranslator.chatBoxAI.ui.components

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Send
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color

@Composable
fun ChatInput(
    prompt: String,
    onPromptChange: (String) -> Unit,
    onSendClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val isDark = isSystemInDarkTheme()
    val blueColor = Color(0xFF1A73E8)
    val textFieldColors = OutlinedTextFieldDefaults.colors(
        focusedBorderColor = blueColor,
        unfocusedBorderColor = blueColor.copy(alpha = 0.6f),
        cursorColor = blueColor,
        focusedLabelColor = blueColor,
        unfocusedLabelColor = blueColor.copy(alpha = 0.8f),
        focusedTrailingIconColor = blueColor,
        unfocusedTrailingIconColor = blueColor.copy(alpha = 0.8f),
        disabledTrailingIconColor = Color.Gray
    )

    OutlinedTextField(
        value = prompt,
        onValueChange = onPromptChange,
        placeholder = { Text("Hỏi Gemini về từ vựng...") },
        modifier = modifier.fillMaxWidth(),
        trailingIcon = {
            IconButton(onClick = onSendClick) {
                Icon(
                    imageVector = Icons.Default.Send,
                    contentDescription = "Gửi",
                    tint = blueColor
                )
            }
        },
        singleLine = true,
        colors = textFieldColors
    )
}
