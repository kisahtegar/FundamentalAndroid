package com.kisahcode.fundamentalandroid.data.remote.retrofit

import com.kisahcode.fundamentalandroid.data.remote.response.NewsResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * Retrofit service interface for defining API endpoints.
 */
interface ApiService {

    /**
     * Fetches the top headlines news articles.
     *
     * @param apiKey The API key for accessing the news API.
     * @return A [Call] object representing the API call.
     */
    @GET("top-headlines?country=id&category=science")
    fun getNews(@Query("apiKey") apiKey: String): Call<NewsResponse>
}