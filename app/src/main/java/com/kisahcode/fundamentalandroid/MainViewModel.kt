package com.kisahcode.fundamentalandroid

import android.os.SystemClock
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import java.util.Timer
import java.util.TimerTask

/**
 * ViewModel class responsible for managing the elapsed time.
 *
 * This ViewModel updates the elapsed time value every second and provides it as a LiveData
 * object to be observed by the UI components.
 */
class MainViewModel : ViewModel() {
    companion object {
        private const val ONE_SECOND = 1000 // Constant representing one second in milliseconds
    }

    // Initial time when the ViewModel is created
    private val mInitialTime = SystemClock.elapsedRealtime()

    // MutableLiveData to hold the elapsed time value
    private val mElapsedTime = MutableLiveData<Long?>()

    // Initialization block to start updating the elapsed time
    init {
        // Create a Timer object to schedule the task for updating elapsed time
        val timer = Timer()

        // Schedule a task to run every second and update the elapsed time
        timer.scheduleAtFixedRate(object : TimerTask() {
            override fun run() {
                // Calculate the elapsed time in seconds
                val newValue = (SystemClock.elapsedRealtime() - mInitialTime) / 1000

                // Post the new value to the MutableLiveData
                mElapsedTime.postValue(newValue)
            }
        }, ONE_SECOND.toLong(), ONE_SECOND.toLong())
    }

    /**
     * Returns a LiveData object that holds the elapsed time value.
     *
     * Observers can subscribe to this LiveData to receive updates on the elapsed time.
     * @return LiveData object representing the elapsed time.
     */
    fun getElapsedTime(): LiveData<Long?> {
        return mElapsedTime
    }
}