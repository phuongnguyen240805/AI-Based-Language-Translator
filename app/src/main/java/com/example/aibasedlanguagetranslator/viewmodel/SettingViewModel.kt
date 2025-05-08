package com.example.aibasedlanguagetranslator.viewmodel

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class SettingsViewModel : ViewModel() {

    // Trạng thái chế độ tối
    private val _isDarkMode = MutableStateFlow(false)
    val isDarkMode: StateFlow<Boolean> = _isDarkMode

    // Ngôn ngữ mặc định
    private val _defaultLanguage = MutableStateFlow("English")
    val defaultLanguage: StateFlow<String> = _defaultLanguage

    // Toggle dark mode
    fun toggleDarkMode(enabled: Boolean) {
        _isDarkMode.value = enabled
    }

    // Đổi ngôn ngữ
    fun setDefaultLanguage(language: String) {
        _defaultLanguage.value = language
    }
}