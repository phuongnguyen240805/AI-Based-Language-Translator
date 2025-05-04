package com.example.aibasedlanguagetranslator.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ContentCopy
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Mic
import androidx.compose.material.icons.filled.VolumeUp
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.aibasedlanguagetranslator.ui.page.getLanguageLabel
import com.example.aibasedlanguagetranslator.ui.page.getPlaceholder

@Composable
fun TranslateInput(
    inputText: String,
    sourceLanguage: String,
    onInputChange: (String) -> Unit,
    onCopyClick: () -> Unit = {},
    onClearClick: () -> Unit = {},
    onMicClick: () -> Unit = {},
    onVolumeClick: () -> Unit = {},
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(8.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(getLanguageLabel(sourceLanguage), color = Color.Gray, fontSize = 12.sp)

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .heightIn(min = 100.dp, max = 100.dp)
                    .verticalScroll(rememberScrollState())
            ) {
                TextField(
                    value = inputText,
                    onValueChange = onInputChange,
                    placeholder = { Text(getPlaceholder(sourceLanguage)) },
                    modifier = Modifier.fillMaxSize(),
                    colors = TextFieldDefaults.colors(
                        focusedContainerColor = Color.Transparent,
                        unfocusedContainerColor = Color.Transparent,
                        disabledContainerColor = Color.Transparent,
                        focusedIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent
                    )
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 4.dp)
                    .height(36.dp),
                horizontalArrangement = Arrangement.SpaceEvenly,
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(onClick = onCopyClick, modifier = Modifier.size(24.dp)) {
                    Icon(
                        Icons.Default.ContentCopy,
                        contentDescription = "Copy",
                        modifier = Modifier.size(24.dp)
                    )
                }
                IconButton(onClick = onClearClick, modifier = Modifier.size(24.dp)) {
                    Icon(
                        Icons.Default.Delete,
                        contentDescription = "Clear",
                        modifier = Modifier.size(24.dp)
                    )
                }
                IconButton(onClick = onMicClick, modifier = Modifier.size(24.dp)) {
                    Icon(
                        Icons.Default.Mic,
                        contentDescription = "Microphone",
                        modifier = Modifier.size(24.dp)
                    )
                }
                IconButton(onClick = onVolumeClick, modifier = Modifier.size(24.dp)) {
                    Icon(
                        Icons.Default.VolumeUp,
                        contentDescription = "Voice",
                        modifier = Modifier.size(24.dp)
                    )
                }
            }
        }
    }
}
