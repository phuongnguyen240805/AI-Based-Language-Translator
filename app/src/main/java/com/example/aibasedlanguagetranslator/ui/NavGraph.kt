// AppNavGraph.kt
package com.example.aibasedlanguagetranslator.ui

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.aibasedlanguagetranslator.ui.page.LoginScreen
import com.example.aibasedlanguagetranslator.ui.page.RegisterScreen
import com.example.aibasedlanguagetranslator.ui.page.TranslateScreen
import com.example.aibasedlanguagetranslator.ui.screens.SettingsScreenWrapper
import com.example.aibasedlanguagetranslator.ui.viewmodel.SettingsViewModel

@Composable
fun AppNavGraph(
    navController: NavHostController,
    settingsViewModel: SettingsViewModel = SettingsViewModel() // Thêm viewModel ở đây
) {
    NavHost(navController = navController, startDestination = "translate") {
        composable("translate") { TranslateScreen(navController) }
        composable("login") { LoginScreen(navController) }
        composable("register") { RegisterScreen(navController) }

        // ✅ Route đến màn hình Settings
        composable("Settings") {
            SettingsScreenWrapper(
                navController = navController,
                viewModel = settingsViewModel,
                onLogoutClick = {
                    navController.navigate("login") {
                        popUpTo("translate") { inclusive = true }
                    }
                }
            )
        }


    }
}
