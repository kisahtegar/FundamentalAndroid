package com.kisahcode.fundamentalandroid

import android.Manifest
import android.os.Build
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.work.Constraints
import androidx.work.WorkManager
import com.kisahcode.fundamentalandroid.databinding.ActivityMainBinding
import androidx.work.Data
import androidx.work.NetworkType
import androidx.work.OneTimeWorkRequest
import androidx.work.PeriodicWorkRequest
import androidx.work.WorkInfo
import java.util.concurrent.TimeUnit

/**
 * The main activity of the application, responsible for handling user interactions
 * and initiating one-time background tasks using WorkManager.
 */
class MainActivity : AppCompatActivity(), View.OnClickListener {

    // WorkManager instance for managing background tasks
    private lateinit var workManager: WorkManager

    // PeriodicWorkRequest instance for defining a periodic background task.
    private lateinit var periodicWorkRequest: PeriodicWorkRequest

    // View binding instance for accessing layout elements
    private lateinit var binding: ActivityMainBinding

    // Activity result launcher for handling runtime permission requests
    private val requestPermissionLauncher =
        registerForActivityResult(
            ActivityResultContracts.RequestPermission()
        ) { isGranted: Boolean ->
            if (isGranted) {
                Toast.makeText(this, "Notifications permission granted", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, "Notifications permission rejected", Toast.LENGTH_SHORT).show()
            }
        }

    /**
     * Called when the activity is first created.
     * @param savedInstanceState The saved instance state.
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Request notifications permission if the API level is 33 or higher
        if (Build.VERSION.SDK_INT >= 33) {
            requestPermissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
        }

        // Initialize WorkManager instance
        workManager = WorkManager.getInstance(this)

        // Set click listener for the button to start one-time task
        binding.btnOneTimeTask.setOnClickListener(this)

        // Set click listener for the button to start periodic task
        binding.btnPeriodicTask.setOnClickListener(this)
        binding.btnCancelTask.setOnClickListener(this)
    }

    /**
     * Handles click events on views.
     * @param view The view that was clicked.
     */
    override fun onClick(view: View) {
        when (view.id) {
            R.id.btnOneTimeTask -> startOneTimeTask()
            R.id.btnPeriodicTask -> startPeriodicTask()
            R.id.btnCancelTask -> cancelPeriodicTask()
        }
    }

    /**
     * Starts a one-time background task using WorkManager.
     */
    private fun startOneTimeTask() {
        // Display initial status
        binding.textStatus.text = getString(R.string.status)

        // Prepare data for the worker
        val data = Data.Builder()
            .putString(MyWorker.EXTRA_CITY, binding.editCity.text.toString())
            .build()

        // Set constraints for the worker (requires network connection)
        val constraints = Constraints.Builder()
            .setRequiredNetworkType(NetworkType.CONNECTED)
            .build()

        // Create a one-time work request for the worker
        val oneTimeWorkRequest = OneTimeWorkRequest.Builder(MyWorker::class.java)
            .setInputData(data)
            .setConstraints(constraints)
            .build()

        // Enqueue the work request to WorkManager
        workManager.enqueue(oneTimeWorkRequest)

        // Observe the work status and update UI accordingly
        workManager.getWorkInfoByIdLiveData(oneTimeWorkRequest.id)
            .observe(this@MainActivity) { workInfo ->
                val status = workInfo.state.name
                binding.textStatus.append("\n" + status)
            }
    }

    /**
     * Starts a periodic background task using WorkManager.
     *
     * Periodic task will run every 15 minutes and requires network connection.
     * Updates UI to display task status and enables cancel button if task is enqueued.
     */
    private fun startPeriodicTask() {
        // Display initial status
        binding.textStatus.text = getString(R.string.status)

        // Prepare data for the worker
        val data = Data.Builder()
            .putString(MyWorker.EXTRA_CITY, binding.editCity.text.toString())
            .build()

        // Set constraints for the worker (requires network connection)
        val constraints = Constraints.Builder()
            .setRequiredNetworkType(NetworkType.CONNECTED)
            .build()

        // Create a periodic work request for the worker
        periodicWorkRequest = PeriodicWorkRequest.Builder(MyWorker::class.java, 15, TimeUnit.MINUTES)
            .setInputData(data)
            .setConstraints(constraints)
            .build()

        // Enqueue the periodic work request to WorkManager
        workManager.enqueue(periodicWorkRequest)

        // Observe the work status and update UI accordingly
        workManager
            .getWorkInfoByIdLiveData(periodicWorkRequest.id)
            .observe(this@MainActivity) { workInfo ->
                val status = workInfo.state.name
                binding.textStatus.append("\n" + status)
                binding.btnCancelTask.isEnabled = false
                if (workInfo.state == WorkInfo.State.ENQUEUED) {
                    binding.btnCancelTask.isEnabled = true
                }
            }
    }

    /**
     * Cancels the currently running periodic background task.
     */
    private fun cancelPeriodicTask() {
        workManager.cancelWorkById(periodicWorkRequest.id)
    }
}