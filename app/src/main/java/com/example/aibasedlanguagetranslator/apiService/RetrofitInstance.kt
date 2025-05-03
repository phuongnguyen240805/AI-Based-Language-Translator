package com.example.aibasedlanguagetranslator.apiService

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitInstance {
    val api: TranslationApi by lazy {
        Retrofit.Builder()
            .baseUrl("https://translate.atomjump.com/") // <-- nhớ có /
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(TranslationApi::class.java)
    }
}
