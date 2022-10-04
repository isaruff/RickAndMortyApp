package com.example.rickandmortyapp.network


import com.squareup.moshi.Moshi
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.concurrent.TimeUnit

val moshi: Moshi = Moshi.Builder().build()


object ApiInitHelper {
    private val okHttpClientBuilder = OkHttpClient.Builder()
    private var retrofit: Retrofit? = null


    private fun okHttpClient(): OkHttpClient {
        if (com.example.rickandmortyapp.BuildConfig.DEBUG) {
            val logging = HttpLoggingInterceptor()
            logging.level = HttpLoggingInterceptor.Level.BODY
            okHttpClientBuilder.addInterceptor(logging)
        }
        okHttpClientBuilder.callTimeout(60, TimeUnit.SECONDS)
            .readTimeout(60, TimeUnit.SECONDS)
            .writeTimeout(60, TimeUnit.SECONDS)
            .connectTimeout(60, TimeUnit.SECONDS)
        return okHttpClientBuilder.build()
    }

    private fun getClient(): Retrofit {
        retrofit = Retrofit.Builder()
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .client(okHttpClient())
            .baseUrl("https://rickandmortyapi.com/api/")
            .build()
        return retrofit as Retrofit
    }


    val api: ApiService by lazy {
        getClient().create(ApiService::class.java)
    }


}