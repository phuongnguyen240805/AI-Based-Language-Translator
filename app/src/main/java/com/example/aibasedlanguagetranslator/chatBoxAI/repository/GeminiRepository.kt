package com.example.aibasedlanguagetranslator.chatBoxAI.repository

import com.example.aibasedlanguagetranslator.chatBoxAI.apiService.GeminiContent
import com.example.aibasedlanguagetranslator.chatBoxAI.apiService.GeminiPart
import com.example.aibasedlanguagetranslator.chatBoxAI.apiService.GeminiRequest
import com.example.aibasedlanguagetranslator.chatBoxAI.apiService.GeminiResponse
import com.example.aibasedlanguagetranslator.chatBoxAI.apiService.GeminiService
import retrofit2.Response

class GeminiRepository(private val service: GeminiService, private val apiKey: String) {
    suspend fun sendPrompt(prompt: String): String? {
        val request = GeminiRequest(
            contents = listOf(GeminiContent(parts = listOf(GeminiPart(text = prompt))))
        )
        val response: Response<GeminiResponse> = service.generateContent(apiKey, request)
        return if (response.isSuccessful) {
            response.body()?.candidates?.firstOrNull()?.content?.parts?.firstOrNull()?.text
        } else null
    }
}
