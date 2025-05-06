package com.example.aibasedlanguagetranslator.ui.page

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.aibasedlanguagetranslator.ui.components.TranslateBottomBar
import com.example.aibasedlanguagetranslator.viewmodel.SettingsViewModel

@Composable
fun SettingsScreenWrapper(
    navController: NavController,
    viewModel: SettingsViewModel,
    onLogoutClick: () -> Unit
) {
    val isDarkMode by viewModel.isDarkMode.collectAsState()
    val defaultLanguage by viewModel.defaultLanguage.collectAsState()

    SettingsScreen(
        navController = navController,
        isDarkMode = isDarkMode,
        onDarkModeToggle = { viewModel.toggleDarkMode(it) },
        defaultLanguage = defaultLanguage,
        onLanguageChange = { viewModel.setDefaultLanguage(it) },
        onLogoutClick = onLogoutClick
    )
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsScreen(
    navController: NavController,
    isDarkMode: Boolean,
    onDarkModeToggle: (Boolean) -> Unit,
    defaultLanguage: String,
    onLanguageChange: (String) -> Unit,
    onLogoutClick: () -> Unit
) {
    val languageOptions = listOf("English", "Vietnamese", "Japanese", "Korean")
    var languageExpanded by remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Cài đặt", fontSize = 20.sp) }
            )
        },
        bottomBar = {
            TranslateBottomBar(navController)
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp)
        ) {
            // Dark mode toggle
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp)
            ) {
                Text("Chế độ tối", modifier = Modifier.weight(1f))
                Switch(checked = isDarkMode, onCheckedChange = onDarkModeToggle)
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Default language selector
            Text("Ngôn ngữ mặc định", fontSize = 16.sp)
            Spacer(modifier = Modifier.height(8.dp))

            Box {
                OutlinedButton(onClick = { languageExpanded = true }) {
                    Text(defaultLanguage)
                }

                DropdownMenu(
                    expanded = languageExpanded,
                    onDismissRequest = { languageExpanded = false }
                ) {
                    languageOptions.forEach { option ->
                        DropdownMenuItem(
                            text = { Text(option) },
                            onClick = {
                                onLanguageChange(option)
                                languageExpanded = false
                            }
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(40.dp))

            // Logout button
            Button(
                onClick = onLogoutClick,
                colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.error)
            ) {
                Text("Đăng xuất", color = MaterialTheme.colorScheme.onError)
            }
        }
    }
}