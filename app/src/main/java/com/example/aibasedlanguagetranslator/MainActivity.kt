package com.example.aibasedlanguagetranslator

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.example.aibasedlanguagetranslator.ui.LanguageappTheme
import com.example.aibasedlanguagetranslator.ui.TranslateScreen

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            LanguageappTheme {
                TranslateScreen()
            }
        }
    }
}