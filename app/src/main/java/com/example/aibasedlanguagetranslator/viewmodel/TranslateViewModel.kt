package com.example.aibasedlanguagetranslator.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*
import org.json.JSONObject
import java.net.URLEncoder
import java.net.HttpURLConnection
import java.net.URL

class TranslateViewModel : ViewModel() {
    private val _translatedText = MutableStateFlow("")
    val translatedText: StateFlow<String> = _translatedText

    private val _errorMessage = MutableStateFlow<String?>(null)
    val errorMessage: StateFlow<String?> = _errorMessage

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    private var debounceJob: Job? = null

    fun translate(text: String, sourceLang: String, targetLang: String) {
        debounceJob?.cancel()
        debounceJob = viewModelScope.launch {
            delay(500)
            if (sourceLang == targetLang) {
                _translatedText.value = text
                return@launch
            }
            _isLoading.value = true
            val result = performGoogleTranslation(text, sourceLang, targetLang)
            _isLoading.value = false

            if (result == "Translation error") {
                _errorMessage.value = "Không thể kết nối với dịch vụ"
            } else {
                _errorMessage.value = null
            }
            _translatedText.value = result
        }
    }

    fun setTranslatedText(text: String) {
        _translatedText.value = text
    }

    private suspend fun performGoogleTranslation(
        text: String,
        sourceLang: String,
        targetLang: String
    ): String = withContext(Dispatchers.IO) {
        try {
            val apiKey = "YOUR_API_KEY"  // Thay thế bằng API Key của bạn
            val url = URL("https://translation.googleapis.com/language/translate/v2?key=$apiKey")
            val params = "q=${URLEncoder.encode(text, "UTF-8")}" +
                    "&source=$sourceLang&target=$targetLang"
            val conn = url.openConnection() as HttpURLConnection
            conn.requestMethod = "POST"
            conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded")
            conn.doOutput = true
            conn.outputStream.use { it.write(params.toByteArray()) }

            val response = conn.inputStream.bufferedReader().use { it.readText() }
            Log.d("GoogleTranslationResponse", response)

            // Parse response JSON
            val jsonResponse = JSONObject(response)
            jsonResponse.getJSONArray("data")
                .getJSONObject(0)
                .getJSONArray("translations")
                .getJSONObject(0)
                .getString("translatedText")
        } catch (e: Exception) {
            Log.e("TranslationError", "Error during translation", e)
            return@withContext "Translation error"
        }
    }
}
