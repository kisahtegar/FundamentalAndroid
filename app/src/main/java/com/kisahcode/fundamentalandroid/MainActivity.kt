package com.kisahcode.fundamentalandroid

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.kisahcode.fundamentalandroid.databinding.ActivityMainBinding

/**
 * Activity class responsible for displaying the main UI and updating the elapsed time.
 *
 * This activity observes the elapsed time LiveData from the MainViewModel and updates
 * the UI accordingly.
 */
class MainActivity : AppCompatActivity() {

    private lateinit var liveDataTimerViewModel: MainViewModel
    private lateinit var activityMainBinding: ActivityMainBinding

    /**
     * Called when the activity is starting. This is where most initialization should go.
     *
     * @param savedInstanceState If the activity is being re-initialized after previously
     *  being shut down then this Bundle contains the data it most recently supplied
     *  in onSaveInstanceState(Bundle).
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Inflate the layout and set it as the content view
        activityMainBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(activityMainBinding.root)

        // Initialize the ViewModel using ViewModelProvider
        liveDataTimerViewModel = ViewModelProvider(this)[MainViewModel::class.java]

        // Subscribe to observe the elapsed time LiveData
        subscribe()
    }

    /**
     * Subscribes to the elapsed time LiveData and updates the UI accordingly.
     */
    private fun subscribe() {
        // Create an Observer to listen for changes in the elapsed time LiveData
        val elapsedTimeObserver = Observer<Long?> {
            // Format the elapsed time value as a string
            val newText = this@MainActivity.resources.getString(R.string.seconds, it)

            // Update the TextView in the UI with the new elapsed time
            activityMainBinding.timerTextview.text = newText
        }

        // Observe the elapsed time LiveData and register the Observer
        liveDataTimerViewModel.getElapsedTime().observe(this, elapsedTimeObserver)
    }
}