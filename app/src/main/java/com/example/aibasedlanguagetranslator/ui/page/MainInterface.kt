package com.example.aibasedlanguagetranslator.ui.page

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.speech.RecognizerIntent
import android.util.Log
import android.widget.Toast
import androidx.activity.compose.BackHandler
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
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
import androidx.core.content.ContextCompat
import androidx.navigation.NavController
import com.example.aibasedlanguagetranslator.ui.components.LanguageSelector
import androidx.compose.material.icons.filled.SwapHoriz
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.platform.LocalClipboardManager
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.AnnotatedString
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.aibasedlanguagetranslator.apiService.RetrofitInstance
import com.example.aibasedlanguagetranslator.repository.TranslationRepository
import com.example.aibasedlanguagetranslator.speech.SpeechRecognizerHelper
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
import kotlinx.coroutines.launch

// Data class for translations
data class TranslationItem(
    val id: String = "",
    val original: String = "",
    val translated: String = "",
    val timestamp: Long = 0L
)

// Hàm kiểm tra sự sẵn sàng của Speech Recognition trên thiết bị
fun isSpeechRecognitionAvailable(context: Context): Boolean {
    val packageManager = context.packageManager
    val recognizerIntent = Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH)
    val activities = packageManager.queryIntentActivities(recognizerIntent, 0)
    Log.d("SpeechRecognition", "Found ${activities.size} activities that can handle speech recognition")
    return activities.isNotEmpty()
}

@Composable
fun TranslateScreen(navController: NavController) {
    val sourceText = remember { mutableStateOf("") }
    val coroutineScope = rememberCoroutineScope()

    val auth = FirebaseAuth.getInstance()
    val currentUser = auth.currentUser
    val db = FirebaseFirestore.getInstance()
    val isLoggedIn = currentUser != null

    var userTranslations by remember { mutableStateOf<List<TranslationItem>>(emptyList()) }

    // Context cho các permission và xử lý
    val context = LocalContext.current

    // Xử lý permission cho RECORD_AUDIO
    var hasRecordAudioPermission by remember {
        mutableStateOf(
            ContextCompat.checkSelfPermission(
                context,
                Manifest.permission.RECORD_AUDIO
            ) == PackageManager.PERMISSION_GRANTED
        )
    }

    // Launcher để yêu cầu quyền ghi âm
    val permissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission()
    ) { isGranted ->
        hasRecordAudioPermission = isGranted
        if (!isGranted) {
            Toast.makeText(
                context,
                "Cần cấp quyền ghi âm để sử dụng tính năng nhận diện giọng nói",
                Toast.LENGTH_LONG
            ).show()
        }
    }

    // Kiểm tra sự hỗ trợ Speech Recognition
    val isSpeechRecognitionSupported = remember {
        val supported = isSpeechRecognitionAvailable(context)
        Log.d("TranslateScreen", "Speech Recognition supported: $supported")
        supported
    }

    // Khởi tạo Speech Recognition Helper
    val speechRecognizerHelper = remember { SpeechRecognizerHelper() }

    // Đăng ký ActivityResultLauncher cho speech recognition
    val speechLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.StartActivityForResult()
    ) { result ->
        try {
            val resultCode = result.resultCode
            val data = result.data

            when {
                resultCode == Activity.RESULT_OK && data != null -> {
                    val results = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS)
                    if (!results.isNullOrEmpty()) {
                        val recognizedText = results[0]
                        Log.d("SpeechRecognition", "Speech recognized: $recognizedText")
                        speechRecognizerHelper.processRecognizedText(recognizedText)
                    } else {
                        Log.w("SpeechRecognition", "No speech recognized (empty results)")
                        Toast.makeText(context, "Không nhận diện được giọng nói", Toast.LENGTH_SHORT).show()
                    }
                }
                resultCode == Activity.RESULT_CANCELED -> {
                    Log.i("SpeechRecognition", "Speech recognition canceled by user")
                    Toast.makeText(context, "Đã hủy nhận diện giọng nói", Toast.LENGTH_SHORT).show()
                }
                else -> {
                    Log.w("SpeechRecognition", "Speech recognition failed with result code: $resultCode")
                    Toast.makeText(context, "Không nhận được kết quả giọng nói", Toast.LENGTH_SHORT).show()
                }
            }
        } catch (e: Exception) {
            Log.e("SpeechRecognition", "Error processing speech recognition result: ${e.message}", e)
            Toast.makeText(context, "Lỗi xử lý kết quả: ${e.message}", Toast.LENGTH_SHORT).show()
        }
    }

    // Thiết lập callback khi component được tạo
    var inputText by rememberSaveable { mutableStateOf("") }

    LaunchedEffect(speechRecognizerHelper) {
        try {
            speechRecognizerHelper.setResultCallback { recognizedText ->
                if (recognizedText.isNotBlank()) {
                    inputText = recognizedText
                    sourceText.value = recognizedText
                }
            }
            Log.d("TranslateScreen", "Đã thiết lập callback thành công")
        } catch (e: Exception) {
            Log.e("TranslateScreen", "Lỗi khi thiết lập callback: ${e.message}", e)
        }
    }

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
                    Log.e("TranslateScreen", "Lỗi khi lấy lịch sử dịch: ${exception.message}")
                    Toast.makeText(context, "Lỗi khi lấy lịch sử dịch: ${exception.message}", Toast.LENGTH_SHORT).show()
                }
        }
    }

    // Middleware save
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

    // Hàm để khởi động speech recognition
    fun startSpeechRecognition(language: String) {
        if (!isSpeechRecognitionSupported) {
            Toast.makeText(context, "Thiết bị của bạn không hỗ trợ nhận diện giọng nói", Toast.LENGTH_LONG).show()
            return
        }

        if (!speechRecognizerHelper.ensureInitialized()) {
            Toast.makeText(context, "Đang khởi tạo nhận diện giọng nói, vui lòng thử lại sau", Toast.LENGTH_SHORT).show()
            return
        }

        try {
            // Tạo và cấu hình intent
            val intent = Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH).apply {
                putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM)
                putExtra(RecognizerIntent.EXTRA_LANGUAGE, language)
                putExtra(RecognizerIntent.EXTRA_PROMPT, "Hãy nói gì đó...")
                putExtra(RecognizerIntent.EXTRA_MAX_RESULTS, 1)
            }

            // Khởi động nhận diện giọng nói
            speechLauncher.launch(intent)
            Log.d("TranslateScreen", "Đã bắt đầu nhận diện giọng nói với ngôn ngữ: $language")
            Toast.makeText(context, "Đang lắng nghe...", Toast.LENGTH_SHORT).show()
        } catch (e: Exception) {
            Log.e("TranslateScreen", "Lỗi khi bắt đầu nhận diện giọng nói: ${e.message}", e)
            Toast.makeText(context, "Lỗi khi bắt đầu nhận diện giọng nói: ${e.message}", Toast.LENGTH_SHORT).show()
        }
    }

    Scaffold(
        bottomBar = { TranslateBottomBar() }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0xFFEAF0FB))
                .padding(paddingValues)
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
                onMicClick = {
                    // Kiểm tra và yêu cầu quyền trước khi bắt đầu ghi âm
                    if (hasRecordAudioPermission) {
                        // Sử dụng language code từ helper
                        val recognitionLanguage = speechRecognizerHelper.getRecognitionLanguage(sourceLanguage)
                        startSpeechRecognition(recognitionLanguage)
                    } else {
                        // Yêu cầu quyền ghi âm
                        Log.d("TranslateScreen", "Yêu cầu quyền ghi âm")
                        permissionLauncher.launch(Manifest.permission.RECORD_AUDIO)
                    }
                },
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

                                // Thông báo đã lưu thành công
                                Toast.makeText(context, "Đã lưu bản dịch", Toast.LENGTH_SHORT).show()
                            }
                            .addOnFailureListener { e ->
                                isSaving = false
                                saveError = e.message
                                Toast.makeText(context, "Lỗi: ${e.message}", Toast.LENGTH_SHORT).show()
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
                    color = Color.Red,
                    fontSize = 14.sp,
                    modifier = Modifier.padding(8.dp)
                )
            }

            // Error message
            errorMessage?.let {
                Spacer(modifier = Modifier.height(8.dp))
                Text(text = it, color = Color.Red, fontSize = 14.sp)
            }

            Spacer(modifier = Modifier.height(12.dp))

            // History Title
            Text(
                text = "History",
                fontWeight = FontWeight.Bold,
                fontSize = 18.sp,
                modifier = Modifier.padding(start = 8.dp, bottom = 4.dp)
            )

            // History List inside a Card with scroll
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .heightIn(min = 100.dp, max = 240.dp), // Giới hạn chiều cao khung
                shape = RoundedCornerShape(8.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White)
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
                                color = Color.Gray,
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
                                        Toast.makeText(context, "Đã sao chép vào clipboard", Toast.LENGTH_SHORT).show()
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
                                                    userTranslations = userTranslations.filter { it.id != item.id }
                                                    Toast.makeText(context, "Đã xóa bản dịch", Toast.LENGTH_SHORT).show()
                                                }
                                                .addOnFailureListener { e ->
                                                    saveError = e.message
                                                    Toast.makeText(context, "Lỗi khi xóa: ${e.message}", Toast.LENGTH_SHORT).show()
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

    // Middleware exit
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