package com.example.aibasedlanguagetranslator.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.aibasedlanguagetranslator.repository.TranslationRepository

class TranslateViewModelFactory(private val repository: TranslationRepository) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(TranslateViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return TranslateViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
