package com.example.aibasedlanguagetranslator.ui

import android.content.Intent
import androidx.activity.ComponentActivity
import androidx.compose.animation.core.*
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.aibasedlanguagetranslator.MainActivity
import com.example.aibasedlanguagetranslator.R
import kotlinx.coroutines.delay

@Composable
fun SplashScreen() {
    val context = LocalContext.current
    val alphaAnim = remember { Animatable(0f) }

    LaunchedEffect(Unit) {
        alphaAnim.animateTo(
            targetValue = 1f,
            animationSpec = tween(durationMillis = 1500)
        )
        delay(500) // Đợi thêm xíu sau animation
        context.startActivity(Intent(context, MainActivity::class.java))
        if (context is ComponentActivity) {
            (context as ComponentActivity).finish()
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Image(
            painter = painterResource(id = R.drawable.baseline_g_translate_24), // ông đổi id nếu cần
            contentDescription = "Logo",
            modifier = Modifier
                .size(150.dp)
                .alpha(alphaAnim.value)
        )
    }
}
