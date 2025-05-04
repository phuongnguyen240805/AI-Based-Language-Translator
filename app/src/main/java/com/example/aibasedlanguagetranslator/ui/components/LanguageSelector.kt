package com.example.aibasedlanguagetranslator.ui.components

import android.annotation.SuppressLint
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@SuppressLint("ConfigurationScreenWidthHeight")
@Composable
fun LanguageSelector(
    selectedLanguage: String,
    onLanguageSelected: (String) -> Unit,
    languageList: List<String>,
) {
    var expanded by remember { mutableStateOf(false) }

    val configuration = LocalConfiguration.current
    val screenHeight = configuration.screenHeightDp.dp
    val maxDropdownHeight = screenHeight / 2

    Box {
        Text(
            text = selectedLanguage,
            fontSize = 18.sp,
            modifier = Modifier
                .clickable { expanded = true }
                .padding(8.dp)
        )

        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false },
            modifier = Modifier.heightIn(max = maxDropdownHeight)
        ) {
            languageList.forEach { language ->
                DropdownMenuItem(
                    text = { Text(language) },
                    onClick = {
                        onLanguageSelected(language)
                        expanded = false
                    }
                )
            }
        }
    }
}