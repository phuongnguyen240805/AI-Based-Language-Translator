package com.example.aibasedlanguagetranslator.repository

import com.example.aibasedlanguagetranslator.apiService.TranslationApi
import com.example.aibasedlanguagetranslator.model.TranslationRequest
import com.example.aibasedlanguagetranslator.model.TranslationResponse

class TranslationRepository(private val api: TranslationApi) {
    suspend fun translate(text: String, sourceLang: String, targetLang: String): TranslationResponse {
        val request = TranslationRequest(
            q = text,
            source = sourceLang,
            target = targetLang
        )
        return api.translateText(request)
    }
}
