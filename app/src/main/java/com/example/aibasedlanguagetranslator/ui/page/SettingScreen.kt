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

    SettingsScreen(
        navController = navController,
        isDarkMode = isDarkMode,
        onDarkModeToggle = { viewModel.toggleDarkMode(it) },
        onLogoutClick = onLogoutClick
    )
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsScreen(
    navController: NavController,
    isDarkMode: Boolean,
    onDarkModeToggle: (Boolean) -> Unit,
    onLogoutClick: () -> Unit
) {
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

            Spacer(modifier = Modifier.height(40.dp))

            // Logout button
            var showLogoutDialog by remember { mutableStateOf(false) }

            if (showLogoutDialog) {
                AlertDialog(
                    onDismissRequest = { showLogoutDialog = false },
                    title = { Text("Xác nhận đăng xuất") },
                    text = { Text("Bạn có chắc chắn muốn đăng xuất không?") },
                    confirmButton = {
                        TextButton(onClick = {
                            showLogoutDialog = false
                            onLogoutClick() // Gọi callback đăng xuất thật sự
                        }) {
                            Text("Đăng xuất")
                        }
                    },
                    dismissButton = {
                        TextButton(onClick = { showLogoutDialog = false }) {
                            Text("Hủy")
                        }
                    }
                )
            }

            Button(
                onClick = { showLogoutDialog = true },
                colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.error)
            ) {
                Text("Đăng xuất", color = MaterialTheme.colorScheme.onError)
            }
        }
    }
}