package com.kisahcode.fundamentalandroid.helper

import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

/**
 * Helper class for working with date-related operations.
 */
object DateHelper {

    /**
     * Retrieves the current date in the format "yyyy/MM/dd HH:mm:ss".
     *
     * @return A string representing the current date.
     */
    fun getCurrentDate(): String {
        val dateFormat = SimpleDateFormat("yyyy/MM/dd HH:mm:ss", Locale.getDefault())
        val date = Date()
        return dateFormat.format(date)
    }
}