package com.example.aibasedlanguagetranslator.ui

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.aibasedlanguagetranslator.ui.page.LoginScreen
import com.example.aibasedlanguagetranslator.ui.page.RegisterScreen
import com.example.aibasedlanguagetranslator.ui.page.TranslateScreen

@Composable
fun AppNavGraph(navController: NavHostController) {
    NavHost(navController, startDestination = "translate") {
        composable("translate") { TranslateScreen(navController) }
        composable("login") { LoginScreen(navController) }
        composable("register") { RegisterScreen(navController) }
    }
}
