package com.kisahcode.fundamentalandroid

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.Button
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.concurrent.Executors

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Initialize views
        val btnStart = findViewById<Button>(R.id.btn_start)
        val tvStatus = findViewById<TextView>(R.id.tv_status)

        // --------- Using Executor for background thread ---------
        // Initialize executor for background task
        //val executor = Executors.newSingleThreadExecutor()
        // Initialize handler for updating UI from background thread
        //val handler = Handler(Looper.getMainLooper())



        // Set click listener for the start button
        btnStart.setOnClickListener{
            // =====================================================================================

            // Example using executor
//            executor.execute {
//                try {
//                    // Simulate process compressing
//                    for (i in 0..10) {
//                        Thread.sleep(500) // Simulate a delay
//                        val percentage = i * 10
//
//                        // Update UI on the main thread
//                        handler.post {
//                            // Update the status TextView based on the progress
//                            if (percentage == 100) {
//                                tvStatus.setText(R.string.task_completed)
//                            } else {
//                                tvStatus.text = String.format(getString(R.string.compressing), percentage)
//                            }
//                        }
//                    }
//                } catch (e: InterruptedException) {
//                    e.printStackTrace()
//                }
//            }

            // =====================================================================================

            // Example using Coroutine. Launch a coroutine in the lifecycle scope of the activity
            lifecycleScope.launch(Dispatchers.Default) {
                //simulate process in background thread
                for (i in 0..10) {
                    delay(500) // Simulate a delay
                    val percentage = i * 10

                    // Update UI in the main thread
                    withContext(Dispatchers.Main) {
                        // Update the status TextView based on the progress
                        if (percentage == 100) {
                            tvStatus.setText(R.string.task_completed)
                        } else {
                            tvStatus.text = String.format(getString(R.string.compressing), percentage)
                        }
                    }
                }
            }
        }
    }
}