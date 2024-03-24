package com.kisahcode.fundamentalandroid

import android.Manifest
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.NotificationCompat
import com.kisahcode.fundamentalandroid.databinding.ActivityMainBinding

/**
 * MainActivity class is responsible for handling the main UI and logic of the application.
 * It extends AppCompatActivity for compatibility with older versions of Android.
 */
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    /**
     * Activity Result Launcher to handle permission requests for notifications.
     * It uses ActivityResultContracts.RequestPermission to handle the request.
     */
    private val requestPermissionLauncher =
        registerForActivityResult(
            ActivityResultContracts.RequestPermission()
        ) { isGranted: Boolean ->
            if (isGranted) {
                // If permission is granted, show a toast message indicating so.
                Toast.makeText(this, "Notifications permission granted", Toast.LENGTH_SHORT).show()
            } else {
                // If permission is rejected, show a toast message indicating so.
                Toast.makeText(this, "Notifications permission rejected", Toast.LENGTH_SHORT).show()
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Request notification permission if the Android version is greater than or equal to Android 33.
        if (Build.VERSION.SDK_INT >= 33) {
            requestPermissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
        }

        // Prepare notification title and message.
        val title = getString(R.string.notification_title)
        val message = getString(R.string.notification_message)

        // Set OnClickListener for the button to send notification.
        binding.btnSendNotification.setOnClickListener {
            sendNotification(title, message)
        }
    }

    /**
     * Sends a notification with the given title and message.
     * @param title Title of the notification.
     * @param message Message of the notification.
     */
    private fun sendNotification(title: String, message: String) {
        // Create an intent to open a webpage when notification is clicked.
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse("http://dicoding.com"))

        // Create a PendingIntent to handle the intent when notification is clicked.
        val pendingIntent = PendingIntent.getActivity(
            this,
            0,
            intent,
            // Use PendingIntent.FLAG_IMMUTABLE if supported by the device's SDK version.
            // This flag makes the PendingIntent immutable to prevent modifications.
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) PendingIntent.FLAG_IMMUTABLE else 0
        )

        // Get notification manager service.
        val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        // Build notification using NotificationCompat.Builder.
        val builder = NotificationCompat.Builder(this, CHANNEL_ID)
            .setContentTitle(title)
            .setSmallIcon(R.drawable.baseline_notifications_active_24)
            .setContentText(message)
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .setSubText(getString(R.string.notification_subtext))
            .setContentIntent(pendingIntent) // Set the PendingIntent for when the notification is clicked.
            .setAutoCancel(true) // Automatically dismiss the notification when clicked.

        // Create notification channel if the Android version is Oreo or above.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                CHANNEL_ID,
                CHANNEL_NAME,
                NotificationManager.IMPORTANCE_DEFAULT
            )
            builder.setChannelId(CHANNEL_ID)
            notificationManager.createNotificationChannel(channel)
        }

        // Build and display the notification.
        val notification = builder.build()
        notificationManager.notify(NOTIFICATION_ID, notification)
    }

    companion object {
        private const val NOTIFICATION_ID = 1
        private const val CHANNEL_ID = "channel_01"
        private const val CHANNEL_NAME = "dicoding channel"
    }
}