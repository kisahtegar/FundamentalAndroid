package com.kisahcode.fundamentalandroid

import android.content.Intent
import android.os.Bundle

import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.kisahcode.fundamentalandroid.databinding.ActivityMainBinding

import com.loopj.android.http.AsyncHttpClient
import com.loopj.android.http.AsyncHttpResponseHandler

import org.json.JSONObject

import cz.msebera.android.httpclient.Header

/**
 * The main activity of the application.
 * Displays a random quote fetched from the quote API and provides a button to navigate to a list of all quotes.
 */
class MainActivity : AppCompatActivity() {

    companion object {
        private val TAG = MainActivity::class.java.simpleName
    }

    private lateinit var binding: ActivityMainBinding

    /**
     * Initializes the activity's layout and components when it is first created.
     * Fetches a random quote from the API and sets up a click listener for the button to navigate to the list of all quotes.
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Fetch a random quote from the API
        getRandomQuote()

        // Set up click listener for the button to navigate to the list of all quotes
        binding.btnAllQuotes.setOnClickListener {
            startActivity(Intent(this@MainActivity, ListQuotesActivity::class.java))
        }
    }

    /**
     * Fetches a random quote from the API and updates the UI with the retrieved quote.
     */
    private fun getRandomQuote() {
        // Display progress bar to indicate loading
        binding.progressBar.visibility = View.VISIBLE

        // Create an instance of AsyncHttpClient
        val client = AsyncHttpClient()
        val url = "http://quote-api.dicoding.dev/random"

        // Send an asynchronous GET request to fetch a random quote from the API
        client.get(url, object : AsyncHttpResponseHandler() {
            override fun onSuccess(
                statusCode: Int,
                headers: Array<Header>,
                responseBody: ByteArray
            ) {
                // Hide progress bar when the request is completed
                binding.progressBar.visibility = View.INVISIBLE

                // Convert the response body to a String
                val result = String(responseBody)

                Log.d(TAG, result)

                try {
                    // Parse the JSON response
                    val responseObject = JSONObject(result)
                    val quote = responseObject.getString("en") // Extract the quote text
                    val author = responseObject.getString("author") // Extract the author text

                    // Update the UI with the retrieved quote and author
                    binding.tvQuote.text = quote
                    binding.tvAuthor.text = author

                } catch (e: Exception) {
                    // Handle parsing errors
                    Toast.makeText(this@MainActivity, e.message, Toast.LENGTH_SHORT).show()
                    e.printStackTrace()
                }
            }

            // Callback function invoked when the HTTP request fails
            override fun onFailure(
                statusCode: Int,
                headers: Array<Header>?,
                responseBody: ByteArray?,
                error: Throwable
            ) {
                // Hide progress bar when the request is completed
                binding.progressBar.visibility = View.INVISIBLE

                // Show an error message based on the HTTP status code
                val errorMessage = when (statusCode) {
                    401 -> "$statusCode : Bad Request"
                    403 -> "$statusCode : Forbidden"
                    404 -> "$statusCode : Not Found"
                    else -> "$statusCode : ${error.message}"
                }

                // Showing toast
                Toast.makeText(this@MainActivity, errorMessage, Toast.LENGTH_SHORT).show()
            }

        })
    }
}