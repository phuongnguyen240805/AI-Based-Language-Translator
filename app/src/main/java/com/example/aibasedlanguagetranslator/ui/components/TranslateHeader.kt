package com.example.aibasedlanguagetranslator.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Login
import androidx.compose.material.icons.filled.Translate
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.padding

@Composable
fun TranslateHeader(
    isLoggedIn: Boolean,
    userName: String,
    onLoginClick: () -> Unit
) {
    // Sử dụng Card để tạo Header với elevation và border radius
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(WindowInsets.statusBars.asPaddingValues()) // Để tránh bị che bởi status bar
            .height(56.dp)
            .padding(horizontal = 12.dp),
        shape = RoundedCornerShape(12.dp), // Áp dụng border radius cho Card
        colors = CardDefaults.cardColors(containerColor = Color(0xFF4285F4)) // Màu nền của Header
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 12.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            // Logo
            Icon(
                imageVector = Icons.Default.Translate,
                contentDescription = "Logo",
                tint = Color.White,
                modifier = Modifier.size(32.dp)
            )

            // Kiểm tra xem người dùng đã đăng nhập chưa
            if (isLoggedIn) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = Icons.Default.AccountCircle,
                        contentDescription = "Avatar",
                        tint = Color.White,
                        modifier = Modifier.size(36.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = userName,
                        color = Color.White,
                        fontWeight = FontWeight.Bold,
                        fontSize = 16.sp
                    )
                }
            } else {
                // Nếu chưa đăng nhập, hiển thị nút đăng nhập
                Button(
                    onClick = onLoginClick,
                    colors = ButtonDefaults.buttonColors(containerColor = Color.White),
                    shape = RoundedCornerShape(8.dp) // Border radius cho button đăng nhập
                ) {
                    Icon(Icons.Default.Login, contentDescription = null, tint = Color(0xFF4285F4))
                    Spacer(modifier = Modifier.width(4.dp))
                    Text("Đăng nhập", color = Color(0xFF4285F4))
                }
            }
        }
    }
}




