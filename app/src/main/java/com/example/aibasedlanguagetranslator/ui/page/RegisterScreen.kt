package com.example.aibasedlanguagetranslator.ui.page

import android.widget.Toast
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.aibasedlanguagetranslator.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

@Composable
fun RegisterScreen(navController: NavController) {
    val context = LocalContext.current
    var fullName by remember { mutableStateOf("") }
    var username by remember { mutableStateOf("") }
    var gender by remember { mutableStateOf("Nam") }
    var password by remember { mutableStateOf("") }

    val auth = FirebaseAuth.getInstance()
    val db = FirebaseFirestore.getInstance()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
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

        Image(
            painter = painterResource(id = R.drawable.baseline_g_translate_24),
            contentDescription = "App Logo",
            modifier = Modifier
                .size(120.dp)
                .padding(bottom = 32.dp)
        )

        TextField(
            value = fullName,
            onValueChange = { fullName = it },
            label = { Text("Họ tên") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp)
        )

        TextField(
            value = username,
            onValueChange = { username = it },
            label = { Text("Email") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp)
        )

        TextField(
            value = password,
            onValueChange = { password = it },
            label = { Text("Mật khẩu") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp),
            visualTransformation = PasswordVisualTransformation()
        )

        Text("Giới tính", fontWeight = FontWeight.SemiBold, modifier = Modifier.padding(bottom = 8.dp))
        Row(verticalAlignment = Alignment.CenterVertically) {
            RadioButton(selected = gender == "Nam", onClick = { gender = "Nam" })
            Text("Nam", modifier = Modifier.padding(end = 16.dp))

            RadioButton(selected = gender == "Nữ", onClick = { gender = "Nữ" })
            Text("Nữ")
        }

        Button(
            onClick = {
                if (username.isNotBlank() && password.isNotBlank() && fullName.isNotBlank()) {
                    auth.createUserWithEmailAndPassword(username.trim(), password.trim())
                        .addOnCompleteListener { task ->
                            if (task.isSuccessful) {
                                val userId = auth.currentUser?.uid.orEmpty()
                                val user = hashMapOf(
                                    "uid" to userId,
                                    "fullname" to fullName.trim(),
                                    "email" to username.trim(),
                                    "gender" to gender
                                )
                                db.collection("userinfo").document(userId)
                                    .set(user)
                                    .addOnSuccessListener {
                                        Toast.makeText(context, "Đăng ký thành công", Toast.LENGTH_SHORT).show()
                                        navController.navigate("translate")
                                    }
                                    .addOnFailureListener { e ->
                                        Toast.makeText(context, "Lỗi lưu thông tin: ${e.message}", Toast.LENGTH_LONG).show()
                                    }
                            } else {
                                Toast.makeText(context, "Đăng ký thất bại: ${task.exception?.message}", Toast.LENGTH_LONG).show()
                            }
                        }
                } else {
                    Toast.makeText(context, "Vui lòng nhập đầy đủ thông tin", Toast.LENGTH_SHORT).show()
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 16.dp),
            shape = RoundedCornerShape(8.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF4285F4))
        ) {
            Text("Đăng ký", color = Color.White)
        }

        TextButton(
            onClick = { navController.navigate("login") },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Đã có tài khoản? Đăng nhập", color = Color(0xFF4285F4))
        }

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "Bằng cách đăng ký, bạn đồng ý với Điều khoản dịch vụ và Chính sách bảo mật",
            fontSize = 12.sp,
            color = Color.Gray,
            modifier = Modifier.padding(top = 16.dp),
            textAlign = TextAlign.Center
        )
    }
}
