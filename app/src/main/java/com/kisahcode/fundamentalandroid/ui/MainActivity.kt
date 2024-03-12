package com.kisahcode.fundamentalandroid.ui

import android.os.Bundle
import android.util.Log
import android.view.View

import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager

import com.bumptech.glide.Glide
import com.kisahcode.fundamentalandroid.data.response.CustomerReviewsItem
import com.kisahcode.fundamentalandroid.data.response.Restaurant
import com.kisahcode.fundamentalandroid.data.response.RestaurantResponse
import com.kisahcode.fundamentalandroid.data.retrofit.ApiConfig
import com.kisahcode.fundamentalandroid.databinding.ActivityMainBinding

import retrofit2.Call
import retrofit2.Callback;
import retrofit2.Response

/**
 * MainActivity class for displaying restaurant details and customer reviews.
 */
class MainActivity : AppCompatActivity() {

    companion object {
        private const val TAG = "MainActivity"
        private const val RESTAURANT_ID = "uewq1zg2zlskfw1e867"
    }

    private lateinit var binding: ActivityMainBinding

    /**
     * Called when the activity is starting. This is where most initialization should go.
     *
     * In this function, the layout is inflated, the action bar is hidden, and the RecyclerView
     * layout manager and item decoration are set up. It also calls the [findRestaurant] function
     * to fetch restaurant data from the API.
     *
     * @param savedInstanceState A Bundle object containing the activity's previously saved state, if any.
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Inflate the layout using view binding
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Hide the action bar
        supportActionBar?.hide()

        // Set up the RecyclerView layout manager and item decoration
        val layoutManager = LinearLayoutManager(this)
        binding.rvReview.layoutManager = layoutManager
        val itemDecoration = DividerItemDecoration(this, layoutManager.orientation)
        binding.rvReview.addItemDecoration(itemDecoration)

        // Fetch restaurant data from the API
        findRestaurant()
    }

    /**
     * Fetches restaurant data from the API.
     *
     * This function sends a network request to retrieve restaurant details from the API endpoint.
     * It updates the UI with the received data upon successful response or displays an error message
     * on failure.
     */
    private fun findRestaurant() {
        // Show loading indicator to indicate network request is in progress
        showLoading(true)

        // Create a Retrofit client instance to make HTTP GET request
        val client = ApiConfig.getApiService().getRestaurant(RESTAURANT_ID)

        // Asynchronously handle the API response using callbacks
        client.enqueue(object : Callback<RestaurantResponse>{

            override fun onResponse(
                call: Call<RestaurantResponse>,
                response: Response<RestaurantResponse>
            ) {
                // Hide loading indicator as the response is received
                showLoading(false)

                // Check if the response is successful (HTTP status code 2xx)
                if (response.isSuccessful) {
                    // Extract the response body containing restaurant details
                    val responseBody = response.body()
                    if (responseBody != null) {
                        // Set restaurant data and customer reviews in the UI
                        setRestaurantData(responseBody.restaurant)
                        setReviewData(responseBody.restaurant.customerReviews)
                    }
                } else {
                    // Log error message if response is unsuccessful
                    Log.e(TAG, "onFailure: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<RestaurantResponse>, t: Throwable) {
                // Hide loading indicator and log error message on API call failure
                showLoading(false)
                Log.e(TAG, "onFailure: ${t.message}")
            }
        })
    }

    /**
     * Sets restaurant data in the UI.
     *
     * This function populates the UI elements with the retrieved restaurant details.
     * It displays the restaurant name, description, and an image.
     *
     * @param restaurant The Restaurant object containing restaurant details.
     */
    private fun setRestaurantData(restaurant: Restaurant) {
        // Set restaurant name in the title text view
        binding.tvTitle.text = restaurant.name

        // Set restaurant description in the description text view
        binding.tvDescription.text = restaurant.description

        // Load and display restaurant image using Glide library
        Glide.with(this@MainActivity)
            .load("https://restaurant-api.dicoding.dev/images/large/${restaurant.pictureId}")
            .into(binding.ivPicture)
    }

    /**
     * Sets review data in the UI.
     *
     * This function populates the RecyclerView with customer reviews.
     *
     * @param customerReviews The list of CustomerReviewsItem objects containing customer reviews.
     */
    private fun setReviewData(consumerReviews: List<CustomerReviewsItem>) {
        // Create a ReviewAdapter instance
        val adapter = ReviewAdapter()

        // Submit the list of customer reviews to the adapter
        adapter.submitList(consumerReviews)

        // Set the adapter to the RecyclerView
        binding.rvReview.adapter = adapter

        // Clear the review input field
        binding.edReview.setText("")
    }

    /**
     * Shows or hides the loading indicator.
     *
     * This function controls the visibility of the loading indicator based on the isLoading parameter.
     *
     * @param isLoading Boolean flag indicating whether to show or hide the loading indicator.
     */
    private fun showLoading(isLoading: Boolean) {
        if (isLoading) {
            // If isLoading is true, show the loading indicator
            binding.progressBar.visibility = View.VISIBLE
        } else {
            // If isLoading is false, hide the loading indicator
            binding.progressBar.visibility = View.GONE
        }
    }
}