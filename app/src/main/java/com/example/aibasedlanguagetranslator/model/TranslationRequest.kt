package com.example.aibasedlanguagetranslator.model

data class TranslationRequest(
    val q: String,
    val source: String,
    val target: String,
    val format: String = "text",
    val api_key: String = ""
)