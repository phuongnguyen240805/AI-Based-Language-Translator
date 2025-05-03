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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.aibasedlanguagetranslator.R // Import for logo

@Composable
fun RegisterScreen(navController: NavController) {
    var fullName by remember { mutableStateOf("") }
    var username by remember { mutableStateOf("") }
    var gender by remember { mutableStateOf("Nam") }
    var password by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Quay lại
        IconButton(
            onClick = { navController.popBackStack() },
            modifier = Modifier.align(Alignment.Start)
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

        // Full Name
        TextField(
            value = fullName,
            onValueChange = { fullName = it },
            label = { Text("Họ tên") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp),
            colors = TextFieldDefaults.colors(
//                textColor = Color.Black,
//                containerColor = Color.White,
//                focusedIndicatorColor = Color(0xFF4285F4),
//                unfocusedIndicatorColor = Color.Gray,
//                focusedLabelColor = Color(0xFF4285F4),
//                unfocusedLabelColor = Color.Gray
            )
        )

        // Username
        TextField(
            value = username,
            onValueChange = { username = it },
            label = { Text("Tên tài khoản") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp),
            colors = TextFieldDefaults.colors(
//                textColor = Color.Black,
//                containerColor = Color.White,
//                focusedIndicatorColor = Color(0xFF4285F4),
//                unfocusedIndicatorColor = Color.Gray,
//                focusedLabelColor = Color(0xFF4285F4),
//                unfocusedLabelColor = Color.Gray
            )
        )

        // Password
        TextField(
            value = password,
            onValueChange = { password = it },
            label = { Text("Mật khẩu") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp, bottom = 16.dp),
            visualTransformation = PasswordVisualTransformation(),
            colors = TextFieldDefaults.colors(
//                textColor = Color.Black,
//                containerColor = Color.White,
//                focusedIndicatorColor = Color(0xFF4285F4),
//                unfocusedIndicatorColor = Color.Gray,
//                focusedLabelColor = Color(0xFF4285F4),
//                unfocusedLabelColor = Color.Gray
            )
        )

        // Gender Selection
        Text(
            "Giới tính",
            fontWeight = FontWeight.SemiBold,
            modifier = Modifier.padding(bottom = 8.dp)
        )
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            RadioButton(
                selected = gender == "Nam",
                onClick = { gender = "Nam" }
            )
            Text("Nam", modifier = Modifier.padding(end = 16.dp))

            RadioButton(
                selected = gender == "Nữ",
                onClick = { gender = "Nữ" }
            )
            Text("Nữ")
        }

        // Register Button
        Button(
            onClick = { navController.navigate("translate") },  // Điều hướng tới màn hình dịch
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp),
            shape = RoundedCornerShape(8.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF4285F4))
        ) {
            Text("Đăng ký", color = Color.White)
        }

        // Redirect to Login
        TextButton(
            onClick = { navController.navigate("login") },  // Điều hướng tới màn hình đăng nhập
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Đã có tài khoản? Đăng nhập", color = Color(0xFF4285F4))
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Footer Text with Terms & Privacy
        Text(
            text = "By signing up, you agree to our Terms of Service and Privacy Policy",
            fontSize = 12.sp,
            color = Color.Gray,
            modifier = Modifier.padding(top = 16.dp),
            textAlign = TextAlign.Center
        )
    }
}