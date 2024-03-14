package com.kisahcode.fundamentalandroid.ui

import android.content.Context
import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputMethodManager

import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager

import com.bumptech.glide.Glide
import com.kisahcode.fundamentalandroid.data.response.CustomerReviewsItem
import com.kisahcode.fundamentalandroid.data.response.Restaurant
import com.kisahcode.fundamentalandroid.databinding.ActivityMainBinding

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

        // Initialize ViewModel
        val mainViewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory()).get(MainViewModel::class.java)

        // Observe changes in restaurant data
        mainViewModel.restaurant.observe(this) {
            setRestaurantData(it)
        }

        // Set up the RecyclerView layout manager and item decoration
        val layoutManager = LinearLayoutManager(this)
        binding.rvReview.layoutManager = layoutManager
        val itemDecoration = DividerItemDecoration(this, layoutManager.orientation)
        binding.rvReview.addItemDecoration(itemDecoration)

        // Observe changes in review data
        mainViewModel.listReview.observe(this) {
            setReviewData(it)
        }

        // Observe changes in loading state
        mainViewModel.isLoading.observe(this) {
            showLoading(it)
        }

        // Set click listener for sending reviews and hiding keyboard
        binding.btnSend.setOnClickListener { view ->
            // Call the postReview function of the MainViewModel with the review text entered in the EditText
            mainViewModel.postReview(binding.edReview.text.toString())

            // Get the InputMethodManager service to manage the keyboard
            val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager

            // Hide the soft keyboard using the window token of the clicked view
            imm.hideSoftInputFromWindow(view.windowToken, 0)
        }
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