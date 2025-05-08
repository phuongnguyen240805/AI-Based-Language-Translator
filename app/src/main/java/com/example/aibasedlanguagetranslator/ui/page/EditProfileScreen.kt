package com.example.aibasedlanguagetranslator.ui.page

import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditProfileScreen(navController: NavController) {
    val context = LocalContext.current
    val auth = FirebaseAuth.getInstance()
    val db = FirebaseFirestore.getInstance()
    val uid = auth.currentUser?.uid

    var fullname by remember { mutableStateOf("") }
    var gender by remember { mutableStateOf("Nam") }
    var isLoading by remember { mutableStateOf(true) }

    LaunchedEffect(uid) {
        uid?.let {
            db.collection("userinfo").document(it).get()
                .addOnSuccessListener { doc ->
                    fullname = doc.getString("fullname") ?: ""
                    gender = doc.getString("gender") ?: "Nam"
                    isLoading = false
                }
                .addOnFailureListener {
                    Toast.makeText(context, "Không tải được dữ liệu", Toast.LENGTH_SHORT).show()
                    isLoading = false
                }
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Chỉnh sửa thông tin") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        }
    ) { padding ->
        if (isLoading) {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator()
            }
        } else {
            Column(
                modifier = Modifier
                    .padding(padding)
                    .padding(16.dp)
            ) {
                OutlinedTextField(
                    value = fullname,
                    onValueChange = { fullname = it },
                    label = { Text("Họ tên") },
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(16.dp))

                Text("Giới tính", style = MaterialTheme.typography.bodyLarge)
                Row(verticalAlignment = Alignment.CenterVertically) {
                    RadioButton(selected = gender == "Nam", onClick = { gender = "Nam" })
                    Text("Nam", modifier = Modifier.padding(end = 16.dp))

                    RadioButton(selected = gender == "Nữ", onClick = { gender = "Nữ" })
                    Text("Nữ")
                }

                Spacer(modifier = Modifier.height(24.dp))

                Button(
                    onClick = {
                        uid?.let {
                            val update = mapOf(
                                "fullname" to fullname,
                                "gender" to gender
                            )
                            db.collection("userinfo").document(it)
                                .update(update)
                                .addOnSuccessListener {
                                    Toast.makeText(context, "Cập nhật thành công", Toast.LENGTH_SHORT).show()
                                    navController.popBackStack()
                                }
                                .addOnFailureListener { e ->
                                    Toast.makeText(context, "Lỗi cập nhật: ${e.message}", Toast.LENGTH_LONG).show()
                                }
                        }
                    },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Lưu")
                }
            }
        }
    }
}
