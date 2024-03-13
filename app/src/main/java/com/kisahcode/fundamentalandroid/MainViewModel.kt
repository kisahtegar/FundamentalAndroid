package com.kisahcode.fundamentalandroid

import androidx.lifecycle.ViewModel

/**
 * ViewModel class responsible for handling the logic related to the MainActivity.
 */
class MainViewModel : ViewModel() {

    // Variable to store the calculation result.
    var result = 0

    /**
     * Calculates the result based on the provided width, height, and length.
     *
     * @param width The width input provided by the user.
     * @param height The height input provided by the user.
     * @param length The length input provided by the user.
     */
    fun calculate(width: String, height: String, length: String) {
        result = width.toInt() * height.toInt() * length.toInt()
    }
}