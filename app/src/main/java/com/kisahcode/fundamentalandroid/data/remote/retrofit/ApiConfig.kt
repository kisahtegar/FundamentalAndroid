package com.kisahcode.fundamentalandroid.data.remote.retrofit

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

/**
 * Singleton object responsible for creating and configuring the Retrofit API service.
 */
object ApiConfig {

    /**
     * Retrieves the configured ApiService instance.
     *
     * @return An instance of [ApiService].
     */
    fun getApiService(): ApiService {
        // Create a logging interceptor for logging network requests and responses
        val loggingInterceptor = HttpLoggingInterceptor()
            .setLevel(HttpLoggingInterceptor.Level.BODY)

        // Create an OkHttpClient instance with the logging interceptor
        val client: OkHttpClient = OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .build()

        // Create a Retrofit instance with the base URL, Gson converter factory, and OkHttpClient
        val retrofit = Retrofit.Builder()
            .baseUrl("https://newsapi.org/v2/")
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()

        // Return an instance of the ApiService created by Retrofit
        return retrofit.create(ApiService::class.java)
    }
}