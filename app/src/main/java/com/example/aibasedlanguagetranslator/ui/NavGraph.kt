package com.example.aibasedlanguagetranslator.ui

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable

@Composable
fun AppNavGraph(navController: NavHostController) {
    NavHost(navController, startDestination = "translate") {
        composable("translate") { TranslateScreen(navController) }
        composable("login") { LoginScreen(navController) }
        composable("register") { RegisterScreen(navController) }
    }
}
