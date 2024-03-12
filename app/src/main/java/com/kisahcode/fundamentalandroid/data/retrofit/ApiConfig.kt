package com.kisahcode.fundamentalandroid.data.retrofit

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

/**
 * Configuration class for setting up Retrofit and providing the API service.
 */
class ApiConfig {

    companion object {

        /**
         * Gets an instance of the Retrofit API service.
         * @return An instance of the Retrofit API service.
         */
        fun getApiService(): ApiService {

            // Create a logging interceptor for logging HTTP request and response data
            val loggingInterceptor =
                HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)

            // Create an OkHttpClient with the logging interceptor
            val client = OkHttpClient.Builder()
                .addInterceptor(loggingInterceptor)
                .build()

            // Create a Retrofit instance with the base URL and Gson converter factory
            val retrofit = Retrofit.Builder()
                .baseUrl("https://restaurant-api.dicoding.dev/")
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build()

            // Create and return an instance of the ApiService interface using Retrofit
            return retrofit.create(ApiService::class.java)
        }
    }
}