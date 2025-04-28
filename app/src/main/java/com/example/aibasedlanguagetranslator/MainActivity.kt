package com.example.aibasedlanguagetranslator

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.navigation.compose.rememberNavController
import com.example.aibasedlanguagetranslator.ui.AppNavGraph
import com.example.aibasedlanguagetranslator.ui.LanguageappTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            LanguageappTheme {
                val navController = rememberNavController()
                AppNavGraph(navController = navController)
            }
        }
    }
}