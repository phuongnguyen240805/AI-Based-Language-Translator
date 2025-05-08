package com.example.aibasedlanguagetranslator.chatBoxAI.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.aibasedlanguagetranslator.chatBoxAI.repository.GeminiRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class GeminiViewModel(private val repository: GeminiRepository): ViewModel() {

    private val _response = MutableStateFlow("")
    val response: StateFlow<String> get() = _response

    private val _isProcessing = MutableStateFlow(false)
    val isProcessing: StateFlow<Boolean> get() = _isProcessing

    fun sendPrompt(prompt: String) {
        viewModelScope.launch {
            _isProcessing.value = true  // Đang xử lý
            _response.value = ""

            val result = repository.sendPrompt(prompt)
            if (result.isNullOrBlank()) {
                _response.value = "Đã có lỗi xảy ra khi gọi Gemini."
            } else {
                _response.value = result
            }

            _isProcessing.value = false  // Xử lý xong
        }
    }

//    fun clearResponse() {
//        _response.value = ""
//        _isProcessing.value = false
//    }
}
