package com.example.aibasedlanguagetranslator.chatBoxAI.ui

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.aibasedlanguagetranslator.chatBoxAI.ui.components.ChatContent
import com.example.aibasedlanguagetranslator.chatBoxAI.ui.components.ChatHeader
import com.example.aibasedlanguagetranslator.chatBoxAI.ui.components.ChatInput
import com.example.aibasedlanguagetranslator.chatBoxAI.viewmodel.GeminiViewModel
import com.example.aibasedlanguagetranslator.ui.components.TranslateBottomBar

@Composable
fun AIChatBox(
    viewModel: GeminiViewModel,
    navController: NavController,
    isDarkMode: Boolean
) {
    var prompt by remember { mutableStateOf("") }
    val response by viewModel.response.collectAsState()
    val isProcessing by viewModel.isProcessing.collectAsState()
    val messages = remember { mutableStateListOf<Pair<String, String>>() }
    val scrollState = rememberScrollState()

    LaunchedEffect(response) {
        if (response.isNotBlank()) {
            messages.add("Gemini" to response)
        }
    }

    Scaffold(
        bottomBar = {
            TranslateBottomBar(navController = navController, isDarkMode = isDarkMode)
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            ChatHeader()

            // Nội dung chat
            Box(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth()
            ) {
                ChatContent(
                    messages = messages,
                    scrollState = scrollState,
                    isProcessing = isProcessing,
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(horizontal = 12.dp, vertical = 8.dp)
                )
            }

            // Thanh nhập liệu ở dưới (trên BottomBar)
            ChatInput(
                prompt = prompt,
                onPromptChange = { prompt = it },
                onSendClick = {
                    if (prompt.isNotBlank()) {
                        messages.add("User" to prompt)
                        viewModel.sendPrompt(prompt)
                        prompt = ""
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 12.dp, vertical = 4.dp)
            )
        }
    }
}
