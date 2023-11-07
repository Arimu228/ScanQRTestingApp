package com.example.testingapp.data.retrofit

import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class RetrofitClient {
    val gson = GsonBuilder().setLenient().create()
    val retrofit: Retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
    var api = retrofit.create(QrCodeApiService::class.java)

    companion object {
        private const val BASE_URL = "http://worktime.kyrgyzpost.kg/api/V1/"
    }
}