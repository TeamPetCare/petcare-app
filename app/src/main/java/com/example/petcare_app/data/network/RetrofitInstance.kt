package com.example.petcare_app.data.network

import com.example.petcare_app.data.services.LoginService
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import okhttp3.OkHttpClient

object RetrofitInstance {
<<<<<<< HEAD
    private const val BASE_URL = "http://api.pet-care.software/"
=======
    private const val BASE_URL = "http://44.217.106.6/"
>>>>>>> dea19d2e9e37c5016d7bd519afc6a75f3ecdb48f

    val loggingInterceptor = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY
    }

    // Configuração do cliente OkHttp com o interceptor
    val client = OkHttpClient.Builder()
        .addInterceptor(loggingInterceptor)
        .build()

    val retrofit: Retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL + "api/")
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    val customRetrofitAuthToken = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

}
