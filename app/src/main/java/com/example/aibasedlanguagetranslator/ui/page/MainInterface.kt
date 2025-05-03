package com.example.aibasedlanguagetranslator.ui.page

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
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
import com.example.aibasedlanguagetranslator.ui.components.TranslateHeader
import com.example.aibasedlanguagetranslator.viewmodel.TranslateViewModel
import com.example.aibasedlanguagetranslator.viewmodel.TranslateViewModelFactory
import kotlinx.coroutines.delay

val previousTranslations = listOf(
    "hello I am Hrittika" to "helo main ritika",
    "How are you?" to "tum kaise ho?",
    "Good morning" to "suprabhat",
)

@Composable
fun TranslateScreen(navController: NavController) {
    val isLoggedIn = false
    val userName = "Hrittika"

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

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFEAF0FB))
            .padding(12.dp)
            .verticalScroll(rememberScrollState()) // scroll được toàn bộ
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
        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(8.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White),
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text(getLanguageLabel(sourceLanguage), color = Color.Gray, fontSize = 12.sp)

                TextField(
                    value = inputText,
                    onValueChange = { inputText = it },
                    placeholder = { Text(getPlaceholder(sourceLanguage)) },
                    modifier = Modifier.fillMaxWidth(),
                    colors = TextFieldDefaults.colors(
                        focusedContainerColor = Color.Transparent,
                        unfocusedContainerColor = Color.Transparent,
                        disabledContainerColor = Color.Transparent,
                        focusedIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent
                    )
                )

                Spacer(modifier = Modifier.height(8.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    IconButton(onClick = {}) {
                        Icon(Icons.Default.CameraAlt, contentDescription = "Camera")
                    }
                    IconButton(onClick = {}) {
                        Icon(Icons.Default.Edit, contentDescription = "Handwriting")
                    }
                    IconButton(onClick = {}) {
                        Icon(Icons.Default.Mic, contentDescription = "Microphone")
                    }
                    IconButton(onClick = {}) {
                        Icon(Icons.Default.VolumeUp, contentDescription = "Voice")
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(8.dp))

        // Translated Box
        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(8.dp),
            colors = CardDefaults.cardColors(containerColor = Color(0xFF4285F4))
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text(getLanguageLabel(targetLanguage), color = Color.White, fontSize = 12.sp)
                if (isLoading) {
                    CircularProgressIndicator(color = Color.White, modifier = Modifier.size(24.dp))
                } else {
                    Text(
                        text = translatedText,
                        color = Color.White,
                        fontSize = 18.sp,
                        modifier = Modifier.padding(top = 8.dp)
                    )
                }

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    IconButton(onClick = {}) {
                        Icon(Icons.Default.Share, contentDescription = null, tint = Color.White)
                    }
                    IconButton(onClick = {
                        clipboardManager.setText(AnnotatedString(translatedText))
                    }) {
                        Icon(
                            Icons.Default.ContentCopy,
                            contentDescription = null,
                            tint = Color.White
                        )
                    }
                    IconButton(onClick = {}) {
                        Icon(
                            Icons.Default.StarBorder,
                            contentDescription = "Save",
                            tint = Color.White
                        )
                    }
                }
            }
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

        // History List
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
                .background(Color.White)
                .padding(8.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())
            ) {
                previousTranslations.forEach { (original, translated) ->
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 4.dp),
                        shape = RoundedCornerShape(8.dp),
                        colors = CardDefaults.cardColors(containerColor = Color(0xFFF5F5F5))
                    ) {
                        Column(modifier = Modifier.padding(12.dp)) {
                            Text(original, fontSize = 14.sp, color = Color.Gray)
                            Spacer(modifier = Modifier.height(4.dp))
                            Text(translated, fontSize = 16.sp, fontWeight = FontWeight.SemiBold)

                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.End
                            ) {
                                IconButton(onClick = {
                                    clipboardManager.setText(AnnotatedString(translatedText))
                                }) {
                                    Icon(
                                        Icons.Default.ContentCopy,
                                        contentDescription = "Copy",
                                        tint = Color.Black
                                    )
                                }
                                IconButton(onClick = { /* TODO: Save */ }) {
                                    Icon(
                                        Icons.Default.StarBorder,
                                        contentDescription = "Save",
                                        tint = Color.Black
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }

        // Bottom Navigation
        NavigationBar(containerColor = Color.White) {
            NavigationBarItem(
                icon = { Icon(Icons.Default.Home, contentDescription = "Home") },
                label = { Text("Home") },
                selected = true,
                onClick = {},
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = Color.White,
                    selectedTextColor = Color(0xFF4285F4),
                    indicatorColor = Color(0xFF4285F4),
                    unselectedIconColor = Color.Gray,
                    unselectedTextColor = Color.Gray
                )
            )
            NavigationBarItem(
                icon = { Icon(Icons.Default.Star, contentDescription = "Saved") },
                label = { Text("Saved") },
                selected = false,
                onClick = {},
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = Color.Gray,
                    selectedTextColor = Color.Gray,
                    indicatorColor = Color.Transparent,
                    unselectedIconColor = Color.Gray,
                    unselectedTextColor = Color.Gray
                )
            )
            NavigationBarItem(
                icon = { Icon(Icons.Default.Settings, contentDescription = "Settings") },
                label = { Text("Settings") },
                selected = false,
                onClick = {},
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = Color.Gray,
                    selectedTextColor = Color.Gray,
                    indicatorColor = Color.Transparent,
                    unselectedIconColor = Color.Gray,
                    unselectedTextColor = Color.Gray
                )
            )
        }
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
