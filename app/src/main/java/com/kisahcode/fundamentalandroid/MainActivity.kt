package com.kisahcode.fundamentalandroid

import android.Manifest
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts.RequestPermission
import androidx.appcompat.app.AppCompatActivity
import com.kisahcode.fundamentalandroid.databinding.ActivityMainBinding

/**
 * The main activity of the application responsible for managing user interactions.
 * It handles permission requests for receiving SMS messages and simulates a download process.
 */
class MainActivity : AppCompatActivity(), View.OnClickListener {

    companion object {
        /** The action string used to notify download completion. */
        const val ACTION_DOWNLOAD_STATUS = "download_status"
    }

    /** The BroadcastReceiver instance for receiving download completion notifications. */
    private lateinit var downloadReceiver: BroadcastReceiver

    /** View binding instance for accessing views in the layout. */
    private var binding: ActivityMainBinding? = null

    /**
     * Called when the activity is created.
     * Sets up the layout, initializes UI components, and registers the download receiver.
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding?.root)

        // Set click listeners for buttons
        binding?.btnPermission?.setOnClickListener(this)
        binding?.btnDownload?.setOnClickListener(this)

        // Initialize the download receiver and register it to listen for download completion
        downloadReceiver = object : BroadcastReceiver() {
            override fun onReceive(context: Context, intent: Intent) {
                // Display a toast message indicating download completion
                Toast.makeText(context, "Download Selesai", Toast.LENGTH_SHORT).show()
            }
        }
        val downloadIntentFilter = IntentFilter(ACTION_DOWNLOAD_STATUS)
        registerReceiver(downloadReceiver, downloadIntentFilter)
    }

    /** Permission launcher for requesting SMS receiver permission. */
    private var requestPermissionLauncher = registerForActivityResult(
        RequestPermission()
    ) { isGranted: Boolean ->
        if (isGranted) {
            Toast.makeText(this, "Sms receiver permission diterima", Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(this, "Sms receiver permission ditolak", Toast.LENGTH_SHORT).show()
        }
    }

    /**
     * Handles click events on views.
     * Launches the permission request or initiates the download process accordingly.
     */
    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.btn_permission -> requestPermissionLauncher.launch(Manifest.permission.RECEIVE_SMS)
            R.id.btn_download -> {
                // Simulate the download process by posting a delayed broadcast
                Handler(Looper.getMainLooper()).postDelayed(
                    {
                        // Create and send a broadcast intent to notify download completion
                        val notifyFinishIntent = Intent().setAction(ACTION_DOWNLOAD_STATUS)
                        sendBroadcast(notifyFinishIntent)
                    },
                    3000 // Delay for 3 seconds
                )
            }
        }
    }

    /**
     * Called when the activity is destroyed.
     * Unregisters the download receiver and releases the view binding instance.
     */
    override fun onDestroy() {
        super.onDestroy()
        unregisterReceiver(downloadReceiver)
        binding = null
    }
}