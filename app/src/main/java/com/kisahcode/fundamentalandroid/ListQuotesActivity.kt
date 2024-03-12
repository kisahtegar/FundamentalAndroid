package com.kisahcode.fundamentalandroid

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.kisahcode.fundamentalandroid.databinding.ActivityListQuotesBinding
import com.loopj.android.http.AsyncHttpClient
import com.loopj.android.http.AsyncHttpResponseHandler
import cz.msebera.android.httpclient.Header
import org.json.JSONArray

/**
 * Activity class for displaying a list of quotes.
 */
class ListQuotesActivity : AppCompatActivity() {

    companion object {
        private val TAG = ListQuotesActivity::class.java.simpleName
    }

    private lateinit var binding: ActivityListQuotesBinding

    /**
     * Called when the activity is starting.
     * Initializes the activity layout and components.
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityListQuotesBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Set up RecyclerView
        val layoutManager = LinearLayoutManager(this)
        binding.listQuotes.setLayoutManager(layoutManager)
        val itemDecoration = DividerItemDecoration(this, layoutManager.orientation)
        binding.listQuotes.addItemDecoration(itemDecoration)

        // Retrieve list of quotes from API
        getListQuotes()
    }

    /**
     * Retrieves the list of quotes from the API.
     * Initiates an asynchronous HTTP GET request to fetch quotes from the server.
     * Handles both successful responses and failures.
     */
    private fun getListQuotes() {
        // Display progress bar to indicate loading
        binding.progressBar.visibility = View.VISIBLE

        // Create an instance of AsyncHttpClient
        val client = AsyncHttpClient()
        val url = "https://quote-api.dicoding.dev/list"  // URL of the quote API

        // Send an asynchronous GET request to the API
        client.get(url, object : AsyncHttpResponseHandler() {
            // Callback function invoked when the HTTP request is successful
            override fun onSuccess(
                statusCode: Int,
                headers: Array<out Header>?,
                responseBody: ByteArray
            ) {
                // Hide progress bar when the request is completed
                binding.progressBar.visibility = View.INVISIBLE

                val listQuote = ArrayList<String>()
                val result = String(responseBody)

                Log.d(TAG, result)

                try {
                    // Parse JSON response
                    val jsonArray = JSONArray(result)

                    // Loop json Object
                    for (i in 0 until jsonArray.length()) {
                        val jsonObject = jsonArray.getJSONObject(i)
                        val quote = jsonObject.getString("en")
                        val author = jsonObject.getString("author")
                        listQuote.add("\n$quote\n — $author\n")
                    }

                    // Set up RecyclerView adapter
                    val adapter = QuoteAdapter(listQuote)
                    binding.listQuotes.adapter = adapter
                } catch (e: Exception) {
                    // Handle parsing errors
                    Toast.makeText(this@ListQuotesActivity, e.message, Toast.LENGTH_SHORT).show()
                    e.printStackTrace()
                }
            }

            // Callback function invoked when the HTTP request fails
            override fun onFailure(
                statusCode: Int,
                headers: Array<out Header>?,
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

                // Show toast
                Toast.makeText(this@ListQuotesActivity, errorMessage, Toast.LENGTH_SHORT).show()
            }
        })
    }
}