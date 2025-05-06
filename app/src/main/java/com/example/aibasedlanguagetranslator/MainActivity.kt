package com.example.aibasedlanguagetranslator

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.runtime.collectAsState
import androidx.navigation.compose.rememberNavController
import com.example.aibasedlanguagetranslator.ui.AppNavGraph
import com.example.aibasedlanguagetranslator.ui.LanguageappTheme
import com.example.aibasedlanguagetranslator.ui.viewmodel.SettingsViewModel
import androidx.compose.runtime.getValue

class MainActivity : ComponentActivity() {
    private val settingsViewModel: SettingsViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            val isDarkMode by settingsViewModel.isDarkMode.collectAsState()

            LanguageappTheme(darkTheme = isDarkMode) {
                AppNavGraph(
                    navController = rememberNavController(),
                    settingsViewModel = settingsViewModel
                )
            }
        }
    }
}