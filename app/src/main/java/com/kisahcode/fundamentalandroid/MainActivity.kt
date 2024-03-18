package com.kisahcode.fundamentalandroid

import android.os.Bundle
import android.text.TextUtils
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.kisahcode.fundamentalandroid.databinding.ActivityMainBinding

/**
 * The MainActivity class serves as the main entry point for the application.
 * It provides user interface elements for interacting with the CuboidModel through the MainViewModel.
 */
class MainActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var activityMainBinding: ActivityMainBinding
    private lateinit var mainViewModel: MainViewModel

    /**
     * Initializes the activity and sets up the UI components.
     * Also initializes the MainViewModel with a CuboidModel instance.
     * @param savedInstanceState The saved instance state.
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activityMainBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(activityMainBinding.root)

        // Initialize the MainViewModel with a CuboidModel instance
        mainViewModel = MainViewModel(CuboidModel())

        // Setup click listeners for buttons
        setupClickListeners()

    }

    /**
     * Sets up click listeners for UI buttons.
     */
    private fun setupClickListeners() {
        with(activityMainBinding) {
            btnSave.setOnClickListener(this@MainActivity)
            btnCalculateSurfaceArea.setOnClickListener(this@MainActivity)
            btnCalculateCircumference.setOnClickListener(this@MainActivity)
            btnCalculateVolume.setOnClickListener(this@MainActivity)
        }
    }

    /**
     * Handles click events for UI buttons.
     * @param v The view that was clicked.
     */
    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.btn_save -> {
                saveCuboid()
            }
            R.id.btn_calculate_circumference -> {
                calculateAndDisplay(mainViewModel.getCircumference())
            }
            R.id.btn_calculate_surface_area -> {
                calculateAndDisplay(mainViewModel.getSurfaceArea())
            }
            R.id.btn_calculate_volume -> {
                calculateAndDisplay(mainViewModel.getVolume())
            }
        }
    }

    /**
     * Saves the dimensions of the cuboid after validating user input.
     */
    private fun saveCuboid() {
        with(activityMainBinding) {
            val length = edtLength.text.toString().trim()
            val width = edtWidth.text.toString().trim()
            val height = edtHeight.text.toString().trim()

            when {
                TextUtils.isEmpty(length) -> edtLength.error = "Field ini tidak boleh kosong"
                TextUtils.isEmpty(width) -> edtWidth.error = "Field ini tidak boleh kosong"
                TextUtils.isEmpty(height) -> edtHeight.error = "Field ini tidak boleh kosong"
                else -> {
                    val valueLength = length.toDouble()
                    val valueWidth = width.toDouble()
                    val valueHeight = height.toDouble()
                    mainViewModel.save(valueLength, valueWidth, valueHeight)
                    toggleVisibility(View.VISIBLE, View.GONE)
                }
            }
        }
    }

    /**
     * Calculates and displays the result of a cuboid calculation.
     * @param result The result of the calculation to be displayed.
     */
    private fun calculateAndDisplay(result: Double) {
        activityMainBinding.tvResult.text = result.toString()
        toggleVisibility(View.GONE, View.VISIBLE)
    }

    /**
     * Toggles the visibility of UI buttons based on their current state.
     * @param visible The visibility state for visible buttons.
     * @param gone The visibility state for hidden buttons.
     */
    private fun toggleVisibility(visible: Int, gone: Int) {
        with(activityMainBinding) {
            btnCalculateVolume.visibility = visible
            btnCalculateCircumference.visibility = visible
            btnCalculateSurfaceArea.visibility = visible
            btnSave.visibility = gone
        }
    }
}