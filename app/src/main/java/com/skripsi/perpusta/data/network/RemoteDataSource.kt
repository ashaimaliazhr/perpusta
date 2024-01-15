package com.skripsi.perpusta.data.network

import com.skripsi.perpusta.BuildConfig
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RemoteDataSource {

    companion object {
//        private const val BASE_URL = "http://192.168.124.209:4000/"
            private const val BASE_URL = "http://192.168.9.209:4000/"

    }

    private val retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .client(provideOkHttpClient())
        .build()

    private fun provideOkHttpClient(): OkHttpClient {
        val builder = OkHttpClient.Builder()

        // Add logging interceptor only for debug builds
        if (BuildConfig.DEBUG) {
            val loggingInterceptor = HttpLoggingInterceptor().apply {
                level = HttpLoggingInterceptor.Level.BODY
            }
            builder.addInterceptor(loggingInterceptor)
        }

        return builder.build()
    }

    fun createApiService(): ApiService {
        return retrofit.create(ApiService::class.java)
    }
}