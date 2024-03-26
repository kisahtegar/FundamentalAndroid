package com.kisahcode.fundamentalandroid

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import android.os.Looper
import android.util.Log
import androidx.core.app.NotificationCompat
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.loopj.android.http.AsyncHttpResponseHandler
import com.loopj.android.http.SyncHttpClient
import cz.msebera.android.httpclient.Header
import org.json.JSONObject
import java.text.DecimalFormat


/**
 * A Worker class to perform background tasks using WorkManager.
 *
 * This class retrieves current weather information for a specified city
 * from the OpenWeatherMap API and displays a notification with the weather details.
 * @param context The application context.
 * @param workerParams The parameters to configure the Worker.
 */
class MyWorker(context: Context, workerParams: WorkerParameters) : Worker(context, workerParams) {

    // Result object to track the success/failure of the worker
    private var resultStatus: Result? = null

    /**
     * Performs the background task.
     *
     * This method fetches current weather data for the specified city
     * and displays a notification with the weather details.
     * @return The result of the task execution.
     */
    override fun doWork(): Result {
        val dataCity = inputData.getString(EXTRA_CITY)
        return getCurrentWeather(dataCity)
    }

    /**
     * Fetches the current weather data from the OpenWeatherMap API.
     * @param city The name of the city for which weather information is requested.
     * @return The result of the task execution.
     */
    private fun getCurrentWeather(city: String?): Result {
        // Logging task initiation
        Log.d(TAG, "getCurrentWeather: Mulai.....")

        // Prepare the HTTP client for synchronous request
        Looper.prepare()
        val client = SyncHttpClient()
        val url = "https://api.openweathermap.org/data/2.5/weather?q=$city&appid=$APP_ID"
        Log.d(TAG, "getCurrentWeather: $url")

        // Make a POST request to the OpenWeatherMap API
        client.post(url, object : AsyncHttpResponseHandler() {
            // Callback method for successful API response
            override fun onSuccess(statusCode: Int, headers: Array<Header?>?, responseBody: ByteArray) {
                val result = String(responseBody)
                Log.d(TAG, result)
                try {
                    // Parse JSON response
                    val responseObject = JSONObject(result)
                    val currentWeather: String = responseObject.getJSONArray("weather").getJSONObject(0).getString("main")
                    val description: String = responseObject.getJSONArray("weather").getJSONObject(0).getString("description")
                    val tempInKelvin = responseObject.getJSONObject("main").getDouble("temp")
                    val tempInCelsius = tempInKelvin - 273
                    val temperature: String = DecimalFormat("##.##").format(tempInCelsius)
                    val title = "Current Weather in $city"
                    val message = "$currentWeather, $description with $temperature celsius"

                    // Display notification with weather details
                    showNotification(title, message)
                    Log.d(TAG, "onSuccess: Selesai.....")
                    resultStatus = Result.success()
                } catch (e: Exception) {
                    // Handle JSON parsing error
                    showNotification("Get Current Weather Not Success", e.message)
                    Log.d(TAG, "onSuccess: Gagal.....")
                    resultStatus = Result.failure()
                }
            }

            // Callback method for API request failure
            override fun onFailure(statusCode: Int, headers: Array<Header?>?, responseBody: ByteArray?, error: Throwable) {
                Log.d(TAG, "onFailure: Gagal.....")
                // When the process fails, set jobFinished with the parameter true. This means the job needs to be rescheduled.
                showNotification("Get Current Weather Failed", error.message)
                resultStatus = Result.failure()
            }
        })
        return resultStatus as Result
    }

    /**
     * Displays a notification with the specified title and description.
     * @param title The title of the notification.
     * @param description The description/content of the notification.
     */
    private fun showNotification(title: String, description: String?) {
        // Retrieve the system service for managing notifications
        val notificationManager = applicationContext.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        // Build the notification using NotificationCompat.Builder
        val notification: NotificationCompat.Builder = NotificationCompat.Builder(applicationContext, CHANNEL_ID)
            .setSmallIcon(R.drawable.baseline_notifications_24)
            .setContentTitle(title)
            .setContentText(description)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setDefaults(NotificationCompat.DEFAULT_ALL)

        // Create notification channel for Android Oreo and higher
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(CHANNEL_ID, CHANNEL_NAME, NotificationManager.IMPORTANCE_HIGH)
            notification.setChannelId(CHANNEL_ID)
            notificationManager.createNotificationChannel(channel)
        }

        // Display the notification
        notificationManager.notify(NOTIFICATION_ID, notification.build())
    }

    companion object {
        // Tag for logging purposes
        private val TAG = MyWorker::class.java.simpleName

        // OpenWeatherMap API key obtained from BuildConfig
        const val APP_ID = BuildConfig.APP_ID

        // Key for passing city data to the worker
        const val EXTRA_CITY = "city"

        // Notification ID for identifying the notification
        const val NOTIFICATION_ID = 1

        // Notification channel ID and name for Android Oreo (API level 26) and higher
        const val CHANNEL_ID = "channel_01"
        const val CHANNEL_NAME = "dicoding channel"
    }
}