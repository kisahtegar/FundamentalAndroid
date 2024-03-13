package com.kisahcode.fundamentalandroid

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.ViewModelProvider
import com.kisahcode.fundamentalandroid.databinding.ActivityMainBinding


/**
 * Main activity responsible for user interaction and displaying the result.
 */
class MainActivity : AppCompatActivity() {

    private lateinit var activityMainBinding: ActivityMainBinding
    private lateinit var viewModel: MainViewModel

    // jika ingin disingkat dan tanpa instialize dengan provider
    //private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activityMainBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(activityMainBinding.root)

        // Initialize ViewModel
        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)

        // Display the result
        displayResult()

        // Set click listener for the calculate button
        activityMainBinding.btnCalculate.setOnClickListener {
            val width = activityMainBinding.edtWidth.text.toString()
            val height = activityMainBinding.edtHeight.text.toString()
            val length = activityMainBinding.edtLength.text.toString()

            // Perform validation and calculate the result
            when {
                width.isEmpty() -> {
                    activityMainBinding.edtWidth.error = "Masih kosong"
                }
                height.isEmpty() -> {
                    activityMainBinding.edtHeight.error = "Masih kosong"
                }
                length.isEmpty() -> {
                    activityMainBinding.edtLength.error = "Masih kosong"
                }
                else -> {
                    viewModel.calculate(width, height, length)
                    displayResult()
                }
            }
        }
    }

    /**
     * Displays the calculated result on the TextView.
     */
    private fun displayResult() {
        activityMainBinding.tvResult.text = viewModel.result.toString()
    }
}