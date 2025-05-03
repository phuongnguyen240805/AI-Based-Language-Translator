package com.example.aibasedlanguagetranslator.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.aibasedlanguagetranslator.repository.TranslationRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class TranslateViewModel(private val repository: TranslationRepository) : ViewModel() {

    private val _translatedText = MutableStateFlow("")
    val translatedText: StateFlow<String> = _translatedText

    private val _errorMessage = MutableStateFlow<String?>(null)
    val errorMessage: StateFlow<String?> = _errorMessage

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    fun translate(text: String, sourceLang: String, targetLang: String) {
        _isLoading.value = true
        viewModelScope.launch {
            try {
                val response = repository.translate(text, sourceLang, targetLang)
                _translatedText.value = response.translatedText
                _errorMessage.value = null
            } catch (e: Exception) {
                _errorMessage.value = "Translation failed: ${e.message}"
                _translatedText.value = ""
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun setTranslatedText(text: String) {
        _translatedText.value = text
    }
}

