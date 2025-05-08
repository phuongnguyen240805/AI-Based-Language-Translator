package com.example.aibasedlanguagetranslator.ui.page

import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.example.aibasedlanguagetranslator.chatBoxAI.ui.AIChatBox
import org.koin.androidx.compose.koinViewModel
import com.example.aibasedlanguagetranslator.chatBoxAI.viewmodel.GeminiViewModel

@Composable
fun ChatAIScreen(navController: NavController) {
    Box(
        modifier = Modifier
            .fillMaxSize()
    ) {
        AIChatBox(
            viewModel = koinViewModel<GeminiViewModel>(),
            navController = navController
        )
    }
}

