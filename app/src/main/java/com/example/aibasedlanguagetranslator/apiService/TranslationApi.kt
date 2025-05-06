package com.example.aibasedlanguagetranslator.apiService

import com.example.aibasedlanguagetranslator.model.TranslationRequest
import com.example.aibasedlanguagetranslator.model.TranslationResponse
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST

interface TranslationApi {
    @Headers("Content-Type: application/json")
    @POST("translate")
    suspend fun translateText(
        @Body request: TranslationRequest
    ): TranslationResponse
}
