package com.example.aibasedlanguagetranslator

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.example.aibasedlanguagetranslator.ui.AiBasedLanguageTranslatorTheme
import com.example.aibasedlanguagetranslator.ui.SplashScreen

@SuppressLint("CustomSplashScreen")
class SplashScreenActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AiBasedLanguageTranslatorTheme {
                SplashScreen()
            }
        }
    }
}
