package com.kisahcode.fundamentalandroid.data.retrofit

import com.kisahcode.fundamentalandroid.data.response.PostReviewResponse
import com.kisahcode.fundamentalandroid.data.response.RestaurantResponse
import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.Path

/**
 * Retrofit API service interface for fetching restaurant data.
 */
interface ApiService {

    /**
     * Fetches restaurant details based on the provided ID.
     *
     * @param id The ID of the restaurant to fetch.
     * @return A Retrofit [Call] object representing the request.
     */
    @GET("detail/{id}")
    fun getRestaurant(
        @Path("id") id: String
    ): Call<RestaurantResponse>

    /**
     * Posts a review for a specific restaurant.
     *
     * @param id The ID of the restaurant to post the review for.
     * @param name The name of the reviewer.
     * @param review The review content.
     * @return A Retrofit [Call] object representing the request.
     */
    @FormUrlEncoded
    @Headers("Authorization: token 12345")
    @POST("review")
    fun postReview(
        @Field("id") id: String,
        @Field("name") name: String,
        @Field("review") review: String
    ): Call<PostReviewResponse>
}