package com.kisahcode.fundamentalandroid.ui

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.kisahcode.fundamentalandroid.data.response.CustomerReviewsItem
import com.kisahcode.fundamentalandroid.data.response.PostReviewResponse
import com.kisahcode.fundamentalandroid.data.response.Restaurant
import com.kisahcode.fundamentalandroid.data.response.RestaurantResponse
import com.kisahcode.fundamentalandroid.data.retrofit.ApiConfig
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

/**
 * ViewModel class responsible for managing data related to the main activity.
 * This includes fetching restaurant details and posting reviews.
 */
class MainViewModel : ViewModel() {
    // LiveData for the restaurant details
    private val _restaurant = MutableLiveData<Restaurant>()
    val restaurant: LiveData<Restaurant> = _restaurant

    // LiveData for the list of customer reviews
    private val _listReview = MutableLiveData<List<CustomerReviewsItem>>()
    val listReview: LiveData<List<CustomerReviewsItem>> = _listReview

    // LiveData to indicate loading state
    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    companion object{
        private const val TAG = "MainViewModel"
        private const val RESTAURANT_ID = "uewq1zg2zlskfw1e867"
    }

    // Initialize ViewModel by fetching restaurant details
    init {
        findRestaurant()
    }

    /**
     * Fetches restaurant details from the API.
     */
    private fun findRestaurant() {
        // Show loading indicator to indicate network request is in progress
        _isLoading.value = true

        // Create a Retrofit client instance to make HTTP GET request
        val client = ApiConfig.getApiService().getRestaurant(RESTAURANT_ID)

        // Asynchronously handle the API response using callbacks
        client.enqueue(object : Callback<RestaurantResponse> {

            override fun onResponse(
                call: Call<RestaurantResponse>,
                response: Response<RestaurantResponse>
            ) {
                // Hide loading indicator as the response is received
                _isLoading.value = false

                // Check if the response is successful (HTTP status code 2xx)
                if (response.isSuccessful) {
                    _restaurant.value = response.body()?.restaurant
                    _listReview.value = response.body()?.restaurant?.customerReviews
                } else {
                    // Log error message if response is unsuccessful
                    Log.e(TAG, "onFailure: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<RestaurantResponse>, t: Throwable) {
                // Hide loading indicator and log error message on API call failure
                _isLoading.value = false
                Log.e(TAG, "onFailure: ${t.message.toString()}")
            }
        })
    }

    /**
     * Posts a review for the restaurant.
     *
     * @param review The review text to be posted.
     */
    fun postReview(review: String) {
        _isLoading.value = true

        // Create a Retrofit Call object to post the review
        val client = ApiConfig.getApiService().postReview(RESTAURANT_ID, "Kisah", review)

        // Enqueue the asynchronous request
        client.enqueue(object : Callback<PostReviewResponse> {
            override fun onResponse(
                call: Call<PostReviewResponse>,
                response: Response<PostReviewResponse>
            ) {
                _isLoading.value = false

                // Check if the response is successful (HTTP status code 2xx)
                if (response.isSuccessful) {
                    _listReview.value = response.body()?.customerReviews
                } else {
                    // Log an error message if the response is not successful
                    Log.e(TAG, "onFailure: ${response.message()}")
                }
            }
            override fun onFailure(call: Call<PostReviewResponse>, t: Throwable) {
                // Hide the loading indicator and log the error message
                _isLoading.value = false
                Log.e(TAG, "onFailure: ${t.message.toString()}")
            }
        })
    }
}