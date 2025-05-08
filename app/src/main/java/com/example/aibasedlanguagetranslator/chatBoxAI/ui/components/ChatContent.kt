package com.example.aibasedlanguagetranslator.chatBoxAI.ui.components

import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun ChatContent(
    messages: SnapshotStateList<Pair<String, String>>,
    scrollState: ScrollState,
    isProcessing: Boolean,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
//            .padding(WindowInsets.statusBars.asPaddingValues())
            .padding(top = 8.dp)
            .verticalScroll(scrollState)
            .padding(bottom = 80.dp) // để không bị che bởi ô input
    ) {
        messages.forEach { (sender, message) ->
            Row(
                horizontalArrangement = if (sender == "Gemini") Arrangement.Start else Arrangement.End,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = message,
                    style = MaterialTheme.typography.bodyLarge,
                    modifier = Modifier
                        .padding(8.dp)
                        .background(
                            color = if (sender == "Gemini") MaterialTheme.colorScheme.secondary else MaterialTheme.colorScheme.primary,
                            shape = MaterialTheme.shapes.medium
                        )
                        .padding(12.dp),
                    color = if (sender == "Gemini") MaterialTheme.colorScheme.onSecondary else MaterialTheme.colorScheme.onPrimary
                )
            }
        }

        if (isProcessing) {
            Text(
                text = "Đang xử lý...",
                style = MaterialTheme.typography.bodyLarge,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                color = MaterialTheme.colorScheme.primary
            )
        }
    }
}
