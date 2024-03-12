package com.kisahcode.fundamentalandroid.data.retrofit

import com.kisahcode.fundamentalandroid.data.response.RestaurantResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

/**
 * Retrofit API service interface for fetching restaurant data.
 */
interface ApiService {

    /**
     * Fetches restaurant details based on the provided ID.
     * @param id The ID of the restaurant to fetch.
     * @return A Retrofit [Call] object representing the request.
     */
    @GET("detail/{id}")
    fun getRestaurant(
        @Path("id") id: String
    ): Call<RestaurantResponse>
}