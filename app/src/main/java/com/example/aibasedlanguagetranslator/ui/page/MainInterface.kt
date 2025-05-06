package com.example.aibasedlanguagetranslator.ui.page

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.Alignment
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.navigation.NavController
import com.example.aibasedlanguagetranslator.ui.components.LanguageSelector
import androidx.compose.material.icons.filled.SwapHoriz
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.platform.LocalClipboardManager
import androidx.compose.ui.text.AnnotatedString
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.aibasedlanguagetranslator.apiService.RetrofitInstance
import com.example.aibasedlanguagetranslator.repository.TranslationRepository
import com.example.aibasedlanguagetranslator.ui.components.ConfirmationDialog
import com.example.aibasedlanguagetranslator.ui.components.HistoryItem
import com.example.aibasedlanguagetranslator.ui.components.TranslateBottomBar
import com.example.aibasedlanguagetranslator.ui.components.TranslateHeader
import com.example.aibasedlanguagetranslator.ui.components.TranslateInput
import com.example.aibasedlanguagetranslator.ui.components.TranslateOutput
import com.example.aibasedlanguagetranslator.viewmodel.TranslateViewModel
import com.example.aibasedlanguagetranslator.viewmodel.TranslateViewModelFactory
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.delay
import kotlin.system.exitProcess
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query


// Data class for translations
data class TranslationItem(
    val id: String = "",
    val original: String = "",
    val translated: String = "",
    val timestamp: Long = 0L
)

@Composable
fun TranslateScreen(navController: NavController) {
    val sourceText = remember { mutableStateOf("") }
    val coroutineScope = rememberCoroutineScope()

    val auth = FirebaseAuth.getInstance()
    val currentUser = auth.currentUser
    val db = FirebaseFirestore.getInstance()
    val isLoggedIn = currentUser != null

    var userTranslations by remember { mutableStateOf<List<TranslationItem>>(emptyList()) }

    // Lấy lịch sử bản dịch từ Firestore khi người dùng đã đăng nhập
    LaunchedEffect(isLoggedIn) {
        if (isLoggedIn) {
            db.collection("users")
                .document(currentUser!!.uid)
                .collection("translations")
                .orderBy("timestamp", Query.Direction.DESCENDING)
                .limit(20)  // Giới hạn 20 bản dịch gần nhất
                .get()
                .addOnSuccessListener { result ->
                    val translations = result.documents.mapNotNull { doc ->
                        val data = doc.data ?: return@mapNotNull null
                        TranslationItem(
                            id = doc.id,
                            original = data["original"] as? String ?: "",
                            translated = data["translated"] as? String ?: "",
                            timestamp = data["timestamp"] as? Long ?: 0L
                        )
                    }
                    userTranslations = translations
                }
                .addOnFailureListener { exception ->
                    // Xử lý lỗi khi không thể lấy dữ liệu
                    println("Lỗi khi lấy lịch sử dịch: ${exception.message}")
                }
        }
    }

    //    middleware save
    var showLoginDialog by remember { mutableStateOf(false) }
    if (showLoginDialog) {
        ConfirmationDialog(
            title = "Yêu cầu đăng nhập",
            message = "Bạn cần đăng nhập để lưu bản dịch. Chuyển đến màn hình đăng nhập?",
            confirmText = "Đăng nhập",
            dismissText = "Hủy",
            onConfirm = {
                showLoginDialog = false
                navController.navigate("login")
            },
            onDismiss = {
                showLoginDialog = false
            }
        )
    }

    val userName = currentUser?.displayName
        ?: currentUser?.email
        ?: "Khách"

    // Languages list
    val languageList = listOf(
        "Arabic",
        "Chinese",
        "English",
        "French",
        "German",
        "Hindi",
        "Italian",
        "Japanese",
        "Korean",
        "Portuguese",
        "Russian",
        "Spanish",
        "Turkish",
        "Vietnamese"
    )

    var sourceLanguage by remember { mutableStateOf("Vietnamese") }
    var targetLanguage by remember { mutableStateOf("English") }

    val viewModel: TranslateViewModel = viewModel(
        factory = TranslateViewModelFactory(
            TranslationRepository(api = RetrofitInstance.api)
        )
    )
    val translatedText by viewModel.translatedText.collectAsState()
    val errorMessage by viewModel.errorMessage.collectAsState()

    var inputText by rememberSaveable { mutableStateOf("") }

    // loading effect
    val isLoading by viewModel.isLoading.collectAsState()

    // copy text
    val clipboardManager = LocalClipboardManager.current

    // Debounce + API call mỗi khi input hoặc ngôn ngữ đổi
    LaunchedEffect(inputText, sourceLanguage, targetLanguage) {
        if (inputText.isNotBlank()) {
            delay(500) // debounce 0.5s sau khi gõ
            viewModel.translate(
                text = inputText,
                sourceLang = getLanguageCode(sourceLanguage),
                targetLang = getLanguageCode(targetLanguage)
            )
        } else {
            viewModel.setTranslatedText("")
        }
    }

    Scaffold(
        containerColor = MaterialTheme.colorScheme.background,
        bottomBar = { TranslateBottomBar(navController) }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background)
                .padding(paddingValues)      // <-- quan trọng: tránh đè footer
                .padding(12.dp)
                .verticalScroll(rememberScrollState())
        ) {
            // Header
            TranslateHeader(
                isLoggedIn = isLoggedIn,
                userName = userName,
                onLoginClick = { navController.navigate("login") }
            )

            Spacer(modifier = Modifier.height(8.dp))

            // Language Switcher
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(modifier = Modifier.weight(1f), contentAlignment = Alignment.Center) {
                    LanguageSelector(
                        selectedLanguage = sourceLanguage,
                        onLanguageSelected = {
                            sourceLanguage = it
                            viewModel.setTranslatedText("")
                        },
                        languageList = languageList
                    )
                }

                // Swap button
                Box(modifier = Modifier.width(48.dp), contentAlignment = Alignment.Center) {
                    IconButton(onClick = {
                        val temp = sourceLanguage
                        sourceLanguage = targetLanguage
                        targetLanguage = temp
                        viewModel.setTranslatedText("")
                    }) {
                        Icon(Icons.Default.SwapHoriz, contentDescription = "Swap")
                    }
                }

                // Right language selector
                Box(modifier = Modifier.weight(1f), contentAlignment = Alignment.Center) {
                    LanguageSelector(
                        selectedLanguage = targetLanguage,
                        onLanguageSelected = {
                            targetLanguage = it
                            viewModel.setTranslatedText("")
                        },
                        languageList = languageList
                    )
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            // Input Box
            TranslateInput(
                inputText = inputText,
                sourceLanguage = sourceLanguage,
                onInputChange = {
                    inputText = it
                    sourceText.value = it
                },
                onCopyClick = { clipboardManager.setText(AnnotatedString(inputText)) },
                onClearClick = { inputText = "" },
                onMicClick = { /* TODO */ },
                onVolumeClick = { /* TODO */ }
            )

            Spacer(modifier = Modifier.height(8.dp))

            // Translated Box
            var isSaving by remember { mutableStateOf(false) }
            var saveError by remember { mutableStateOf<String?>(null) }
            TranslateOutput(
                targetLanguage = targetLanguage,
                translatedText = translatedText,
                isLoading = isLoading,
                clipboardManager = clipboardManager,
                onVolumeClick = {
                    // TODO: read text
                },
                onCopyClick = {
                    clipboardManager.setText(AnnotatedString(translatedText))
                },
                onSaveClick = {
                    if (!isLoggedIn) {
                        showLoginDialog = true
                    } else if (inputText.isNotBlank() && translatedText.isNotBlank()) {
                        isSaving = true
                        saveError = null

                        // Lưu bản dịch vào Firestore
                        val translation = hashMapOf(
                            "original" to inputText,
                            "translated" to translatedText,
                            "timestamp" to System.currentTimeMillis()
                        )

                        db.collection("users")
                            .document(currentUser!!.uid)
                            .collection("translations")
                            .add(translation)
                            .addOnSuccessListener { documentRef ->
                                isSaving = false

                                // Thêm bản dịch mới vào danh sách hiện tại
                                val newTranslation = TranslationItem(
                                    id = documentRef.id,
                                    original = inputText,
                                    translated = translatedText,
                                    timestamp = System.currentTimeMillis()
                                )

                                // Cập nhật danh sách hiển thị ngay lập tức
                                userTranslations = listOf(newTranslation) + userTranslations
                            }
                            .addOnFailureListener { e ->
                                isSaving = false
                                saveError = e.message
                            }
                    }
                }
            )

            // Hiển thị loading và error bên ngoài callback, trong context Composable
            if (isSaving) {
                Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
                    CircularProgressIndicator(modifier = Modifier.size(24.dp))
                }
            }

            saveError?.let {
                Text(
                    text = it,
                    color = MaterialTheme.colorScheme.error,
                    fontSize = 14.sp,
                    modifier = Modifier.padding(8.dp)
                )
            }

            // Error message
            errorMessage?.let {
                Spacer(modifier = Modifier.height(8.dp))
                Text(text = it, color = MaterialTheme.colorScheme.error, fontSize = 14.sp)
            }

            Spacer(modifier = Modifier.height(12.dp))

            // History Title
            Text(
                text = "History",
                fontWeight = FontWeight.Bold,
                fontSize = 18.sp,
                color = MaterialTheme.colorScheme.onSurface,
                modifier = Modifier.padding(start = 8.dp, bottom = 4.dp)
            )

            // History List inside a Card with scroll
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .heightIn(min = 100.dp, max = 240.dp), // Giới hạn chiều cao khung
                shape = RoundedCornerShape(8.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(8.dp)
                ) {
                    if (userTranslations.isEmpty()) {
                        Box(
                            modifier = Modifier.fillMaxSize(),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = if (isLoggedIn) "No translation history yet" else "Login to view your history",
                                color = MaterialTheme.colorScheme.onSurfaceVariant,
                                fontSize = 16.sp
                            )
                        }
                    } else {
                        Column(
                            modifier = Modifier
                                .fillMaxSize()
                                .verticalScroll(rememberScrollState())
                        ) {
                            userTranslations.forEach { item ->
                                HistoryItem(
                                    original = item.original,
                                    translated = item.translated,
                                    onCopyClick = {
                                        clipboardManager.setText(AnnotatedString(item.translated))
                                    },
                                    onSaveClick = { /* Already saved */ },
                                    onDeleteClick = {
                                        if (isLoggedIn) {
                                            // Xóa bản dịch từ Firestore
                                            db.collection("users")
                                                .document(currentUser!!.uid)
                                                .collection("translations")
                                                .document(item.id)
                                                .delete()
                                                .addOnSuccessListener {
                                                    // Cập nhật UI sau khi xóa thành công
                                                    userTranslations =
                                                        userTranslations.filter { it.id != item.id }
                                                }
                                                .addOnFailureListener { e ->
                                                    saveError = e.message
                                                }
                                        }
                                    }
                                )
                            }
                        }
                    }
                }
            }
        }
    }

    //    middleware exit
    var showExitDialog by remember { mutableStateOf(false) }
    BackHandler {
        showExitDialog = true
    }

    if (showExitDialog) {
        ConfirmationDialog(
            title = "Thoát khỏi trình dịch",
            message = "Bạn có chắc chắn muốn rời khỏi màn hình này?",
            confirmText = "Thoát",
            dismissText = "Hủy",
            onConfirm = {
                showExitDialog = false
                exitProcess(0)
            },
            onDismiss = {
                showExitDialog = false
            }
        )
    }
}


fun getLanguageLabel(language: String): String {
    return when (language.lowercase()) {
        "english" -> "ENGLISH"
        "arabic" -> "TIẾNG Ả RẬP"
        "chinese" -> "TIẾNG TRUNG"
        "french" -> "TIẾNG PHÁP"
        "german" -> "TIẾNG ĐỨC"
        "hindi" -> "TIẾNG HINDI"
        "italian" -> "TIẾNG Ý"
        "japanese" -> "TIẾNG NHẬT"
        "korean" -> "TIẾNG HÀN"
        "portuguese" -> "TIẾNG BỒ ĐÀO NHA"
        "russian" -> "TIẾNG NGA"
        "spanish" -> "TIẾNG TÂY BAN NHA"
        "turkish" -> "TIẾNG THỔ NHĨ KỲ"
        "vietnamese" -> "TIẾNG VIỆT"
        else -> language.uppercase()
    }
}

fun getPlaceholder(language: String): String {
    return when (language.lowercase()) {
        "english" -> "Enter text"
        "arabic" -> "أدخل النص"
        "chinese" -> "输入文本"
        "french" -> "Entrez le texte"
        "german" -> "Text eingeben"
        "hindi" -> "पाठ दर्ज करें"
        "italian" -> "Inserisci il testo"
        "japanese" -> "テキストを入力"
        "korean" -> "텍스트 입력"
        "portuguese" -> "Insira o texto"
        "russian" -> "Введите текст"
        "spanish" -> "Ingrese texto"
        "turkish" -> "Metni girin"
        "vietnamese" -> "Nhập văn bản"
        else -> "Enter text"
    }
}

fun getLanguageCode(language: String): String {
    return when (language.lowercase()) {
        "english" -> "en"
        "arabic" -> "ar"
        "chinese" -> "zh"
        "french" -> "fr"
        "german" -> "de"
        "hindi" -> "hi"
        "italian" -> "it"
        "japanese" -> "ja"
        "korean" -> "ko"
        "portuguese" -> "pt"
        "russian" -> "ru"
        "spanish" -> "es"
        "turkish" -> "tr"
        "vietnamese" -> "vi"
        else -> "en" // fallback
    }
}