package com.example.aibasedlanguagetranslator.chatBoxAI.apiService

import com.example.aibasedlanguagetranslator.chatBoxAI.repository.GeminiRepository
import com.example.aibasedlanguagetranslator.chatBoxAI.viewmodel.GeminiViewModel
import okhttp3.OkHttpClient
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

val AppModule = module {

    single {
        Retrofit.Builder()
            .baseUrl("https://generativelanguage.googleapis.com/")
            .client(OkHttpClient.Builder().build())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(GeminiService::class.java)
    }

    single { GeminiRepository(get(), "AIzaSyAlvg4HZCqarsu_ePikG5CLGQElLOcZqRM") }

    viewModel { GeminiViewModel(get()) }
}
