package com.example.aibasedlanguagetranslator.speech

import android.util.Log
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

/**
 * Helper class for speech recognition functionality without needing Activity
 */
class SpeechRecognizerHelper {
    companion object {
        private const val TAG = "SpeechRecognizerHelper"
    }

    // StateFlow to track if the recognizer is ready to use
    private val _isReady = MutableStateFlow(false)
    val isReady: StateFlow<Boolean> = _isReady.asStateFlow()

    // Callback for speech recognition results
    private var onResultCallback: ((String) -> Unit)? = null

    /**
     * Set the callback for speech recognition results
     * Call this method before using startSpeechRecognition
     */
    fun setResultCallback(callback: (String) -> Unit) {
        this.onResultCallback = callback
        _isReady.value = true
        Log.d(TAG, "Callback set, helper is ready")
    }

    /**
     * Process the recognized text
     */
    fun processRecognizedText(text: String) {
        Log.d(TAG, "Processing recognized text: $text")
        if (text.isNotBlank()) {
            onResultCallback?.invoke(text)
        }
    }

    /**
     * Check if speech recognizer is ready
     */
    fun ensureInitialized(): Boolean {
        return _isReady.value
    }

    /**
     * Get the recognition language code based on the source language name
     */
    fun getRecognitionLanguage(sourceLanguage: String): String {
        return when (sourceLanguage.lowercase()) {
            "vietnamese" -> "vi-VN"
            "english" -> "en-US"
            "french" -> "fr-FR"
            "german" -> "de-DE"
            "chinese" -> "zh-CN"
            "japanese" -> "ja-JP"
            "korean" -> "ko-KR"
            "russian" -> "ru-RU"
            "spanish" -> "es-ES"
            "portuguese" -> "pt-PT"
            "arabic" -> "ar-SA"
            "hindi" -> "hi-IN"
            "italian" -> "it-IT"
            "turkish" -> "tr-TR"
            else -> "en-US" // Default
        }
    }
}