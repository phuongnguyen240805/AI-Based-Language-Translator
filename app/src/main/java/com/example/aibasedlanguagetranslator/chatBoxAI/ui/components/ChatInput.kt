package com.example.aibasedlanguagetranslator.chatBoxAI.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Send
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun ChatInput(
    prompt: String,
    onPromptChange: (String) -> Unit,
    onSendClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    OutlinedTextField(
        value = prompt,
        onValueChange = onPromptChange,
        placeholder = { Text("Hỏi Gemini về từ vựng...") },
        modifier = modifier
            .fillMaxWidth(),
        trailingIcon = {
            IconButton(onClick = onSendClick) {
                Icon(imageVector = Icons.Default.Send, contentDescription = "Gửi")
            }
        },
        singleLine = true
    )
}

