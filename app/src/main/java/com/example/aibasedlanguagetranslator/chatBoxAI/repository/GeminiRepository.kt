package com.example.aibasedlanguagetranslator.chatBoxAI.repository

import com.example.aibasedlanguagetranslator.chatBoxAI.apiService.GeminiContent
import com.example.aibasedlanguagetranslator.chatBoxAI.apiService.GeminiPart
import com.example.aibasedlanguagetranslator.chatBoxAI.apiService.GeminiRequest
import com.example.aibasedlanguagetranslator.chatBoxAI.apiService.GeminiResponse
import com.example.aibasedlanguagetranslator.chatBoxAI.apiService.GeminiService
import retrofit2.Response

class GeminiRepository(private val service: GeminiService, private val apiKey: String) {
    suspend fun sendPrompt(userInput: String): String? {
        val prompt = """
        Bạn là một trợ lý AI trong ứng dụng học và dịch ngôn ngữ. 
        Nhiệm vụ của bạn là giúp người dùng hiểu từ vựng và ngữ pháp tiếng Anh, bằng cách giải thích đơn giản và đưa ví dụ câu.

        Chỉ trả lời các câu hỏi liên quan đến:
        - nghĩa của từ hoặc cụm từ tiếng Anh
        - ngữ pháp tiếng Anh
        - cách sử dụng tiếng Anh trong câu

        Nếu người dùng hỏi những chủ đề khác (như chính trị, lịch sử, thời tiết, lập trình...), hãy lịch sự từ chối và trả lời bằng tiếng Việt:
        "Mình chỉ hỗ trợ học từ vựng và ngữ pháp tiếng Anh. Bạn hãy hỏi một nội dung liên quan đến tiếng Anh nhé!"

        Luôn phản hồi bằng tiếng Việt nếu người dùng nhập tiếng Việt.

        Giờ hãy trả lời câu hỏi sau: "$userInput"
    """.trimIndent()

        val request = GeminiRequest(
            contents = listOf(GeminiContent(parts = listOf(GeminiPart(text = prompt))))
        )

        val response: Response<GeminiResponse> = service.generateContent(apiKey, request)
        return if (response.isSuccessful) {
            response.body()?.candidates?.firstOrNull()?.content?.parts?.firstOrNull()?.text
        } else null
    }
}
