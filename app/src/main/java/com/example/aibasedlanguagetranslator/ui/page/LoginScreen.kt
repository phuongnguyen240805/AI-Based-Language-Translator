package com.example.aibasedlanguagetranslator.ui.page

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.aibasedlanguagetranslator.R // Import for logo

@Composable
fun LoginScreen(navController: NavController) {
    var username by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Đưa biểu tượng "Back" vào trong TopAppBar
        IconButton(
            onClick = { navController.popBackStack() },
            modifier = Modifier
                .align(Alignment.Start)
                .padding(top = 16.dp)  // Thêm khoảng cách trên để đưa lên phía trên
        ) {
            Icon(
                imageVector = Icons.Default.ArrowBack,
                contentDescription = "Back",
                tint = Color(0xFF4285F4)
            )
        }

        // Logo
        Image(
            painter = painterResource(id = R.drawable.baseline_g_translate_24), // Thay đổi logo của bạn
            contentDescription = "App Logo",
            modifier = Modifier
                .size(120.dp)
                .padding(bottom = 32.dp)
        )

        // Tên tài khoản
        TextField(
            value = username,
            onValueChange = { username = it },
            label = { Text("Tên tài khoản") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp),
            colors = TextFieldDefaults.colors(
//               text color
            )
        )

        // Mật khẩu
        TextField(
            value = password,
            onValueChange = { password = it },
            label = { Text("Mật khẩu") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 24.dp),
            visualTransformation = PasswordVisualTransformation(),
            colors = TextFieldDefaults.colors(
//                textColor = Color.Black,
//                containerColor = Color.White,
//                focusedIndicatorColor = Color(0xFF4285F4),
//                unfocusedIndicatorColor = Color.Gray
            )
        )

        // Đăng nhập button
        Button(
            onClick = { navController.navigate("translate") },  // Điều hướng tới màn hình dịch
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp),
            shape = RoundedCornerShape(8.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF4285F4))
        ) {
            Text("Đăng nhập", color = Color.White)
        }

        // Chưa có tài khoản? Đăng ký
        TextButton(
            onClick = { navController.navigate("register") },  // Điều hướng tới màn hình đăng ký
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Chưa có tài khoản? Đăng ký", color = Color(0xFF4285F4))
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Footer Text with Terms & Privacy
        Text(
            text = "By signing in, you agree to our Terms of Service and Privacy Policy",
            fontSize = 12.sp,
            color = Color.Gray,
            modifier = Modifier.padding(top = 16.dp),
            textAlign = TextAlign.Center
        )
    }
}