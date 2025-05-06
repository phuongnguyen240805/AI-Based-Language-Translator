package com.example.aibasedlanguagetranslator.ui

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.aibasedlanguagetranslator.ui.page.LoginScreen
import com.example.aibasedlanguagetranslator.ui.page.RegisterScreen
import com.example.aibasedlanguagetranslator.ui.page.SettingsScreenWrapper
import com.example.aibasedlanguagetranslator.ui.page.TranslateScreen
import com.example.aibasedlanguagetranslator.viewmodel.SettingsViewModel

@Composable
fun AppNavGraph(
    navController: NavHostController,
    settingsViewModel: SettingsViewModel
) {
    NavHost(navController = navController, startDestination = "translate") {
        composable("translate") { TranslateScreen(navController) }
        composable("login") { LoginScreen(navController) }
        composable("register") { RegisterScreen(navController) }

        composable("settings") {
            SettingsScreenWrapper(
                navController = navController,
                viewModel = settingsViewModel,
                onLogoutClick = {
                    navController.navigate("translate") {
                        popUpTo("translate") { inclusive = true }
                        launchSingleTop = true
                    }
                }
            )
        }
    }
}
