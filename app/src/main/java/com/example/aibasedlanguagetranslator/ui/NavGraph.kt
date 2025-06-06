package com.example.aibasedlanguagetranslator.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.aibasedlanguagetranslator.ui.page.LoginScreen
import com.example.aibasedlanguagetranslator.ui.page.RegisterScreen
import com.example.aibasedlanguagetranslator.ui.page.SettingsScreenWrapper
import com.example.aibasedlanguagetranslator.ui.page.TranslateScreen
import com.example.aibasedlanguagetranslator.ui.page.ChatAIScreen
import com.example.aibasedlanguagetranslator.ui.page.EditProfileScreen
import com.example.aibasedlanguagetranslator.viewmodel.SettingsViewModel
import com.google.firebase.auth.FirebaseAuth

@Composable
fun AppNavGraph(
    navController: NavHostController,
    settingsViewModel: SettingsViewModel
) {

    val isDarkMode by settingsViewModel.isDarkMode.collectAsState()

    NavHost(navController = navController, startDestination = "translate") {
        composable("translate") { TranslateScreen(navController, isDarkMode) }
        composable("login") { LoginScreen(navController) }
        composable("register") { RegisterScreen(navController) }

        composable("chat") { ChatAIScreen(navController, isDarkMode) }

        composable("edit_profile") {
            EditProfileScreen(navController)
        }

        composable("settings") {
            SettingsScreenWrapper(
                navController = navController,
                viewModel = settingsViewModel,
                onLogoutClick = {
                    FirebaseAuth.getInstance().signOut() // đăng xuất khỏi Firebase
                    navController.navigate("translate") {   // điều hướng về màn hình đăng nhập
                        popUpTo("translate") {
                            inclusive = true
                        } // xóa stack để không quay lại được
                        launchSingleTop = true
                    }
                }
            )
        }
    }
}
