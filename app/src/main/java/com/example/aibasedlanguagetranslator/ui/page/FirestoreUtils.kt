package com.example.aibasedlanguagetranslator.ui.page

import android.util.Log
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.auth.FirebaseAuth

//fun saveTranslationToFirestore(
//    original: String,
//    translated: String,
//    onSuccess: () -> Unit,
//    onError: (Exception) -> Unit
//) {
//    val user = FirebaseAuth.getInstance().currentUser
//    if (user != null) {
//        val translation = hashMapOf(
//            "original" to original,
//            "translated" to translated,
//            "timestamp" to System.currentTimeMillis()
//        )
//
//        FirebaseFirestore.getInstance()
//            .collection("users")
//            .document(user.uid)
//            .collection("translations")
//            .add(translation)
//            .addOnSuccessListener { onSuccess() }
//            .addOnFailureListener { exception -> onError(exception) }
//    } else {
//        onError(Exception("Người dùng chưa đăng nhập"))
//    }
//}
// Lưu bản dịch vào Firestore
fun saveTranslationToFirestore(
    original: String,
    translated: String,
    onSuccess: () -> Unit,
    onError: (Exception) -> Unit
) {
    val user = FirebaseAuth.getInstance().currentUser
    if (user != null) {
        val translation = hashMapOf(
            "original" to original,
            "translated" to translated,
            "timestamp" to System.currentTimeMillis()
        )

        FirebaseFirestore.getInstance()
            .collection("users")
            .document(user.uid)
            .collection("translations")
            .add(translation)
            .addOnSuccessListener { onSuccess() }
            .addOnFailureListener { exception -> onError(exception) }
    } else {
        onError(Exception("Người dùng chưa đăng nhập"))
    }
}