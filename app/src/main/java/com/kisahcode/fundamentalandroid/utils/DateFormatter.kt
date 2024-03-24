package com.kisahcode.fundamentalandroid.utils

import java.text.DateFormat
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

/**
 * Utility object for date formatting.
 */
object DateFormatter {


    /**
     * Formats the given date string from the current format to the target format.
     * @param currentDate The date string to be formatted
     * @return The formatted date string, or null if parsing fails
     */
    fun formatDate(currentDate: String): String? {

        // Define current and target date formats
        val currentFormat = "yyyy-MM-dd'T'hh:mm:ss'Z'"
        val targetFormat = "dd MMM yyyy | HH:mm"
        val timezone = "GMT"

        // Create date formatter instances
        val currentDf: DateFormat = SimpleDateFormat(currentFormat, Locale.getDefault())
        currentDf.timeZone = TimeZone.getTimeZone(timezone)
        val targetDf: DateFormat = SimpleDateFormat(targetFormat, Locale.getDefault())

        var targetDate: String? = null
        try {
            // Parse the current date string
            val date = currentDf.parse(currentDate)
            if (date != null) {
                // Format the parsed date to the target format
                targetDate = targetDf.format(date)
            }
        } catch (ex: ParseException) {
            // Handle parsing exceptions
            ex.printStackTrace()
        }

        return targetDate
    }
}