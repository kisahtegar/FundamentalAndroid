package com.kisahcode.fundamentalandroid

import android.annotation.SuppressLint
import android.app.AlarmManager
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.media.RingtoneManager
import android.os.Build
import android.util.Log
import android.widget.Toast
import androidx.core.app.NotificationCompat
import androidx.core.content.ContextCompat
import java.util.*
import java.text.ParseException
import java.text.SimpleDateFormat

/**
 * BroadcastReceiver to handle alarms set using AlarmManager.
 * This class is responsible for receiving broadcast intents and triggering actions accordingly.
 */
class AlarmReceiver : BroadcastReceiver() {

    /**
     * Receives broadcast intents and triggers actions based on the intent type.
     *
     * This method is called when the BroadcastReceiver is receiving an Intent broadcast.
     * If the intent type indicates a one-time alarm, it shows a notification with the provided message.
     * @param context The context in which the receiver is running.
     * @param intent The received intent.
     */
    override fun onReceive(context: Context, intent: Intent) {
        // Extract the type and message from the received intent
        val type = intent.getStringExtra(EXTRA_TYPE)
        val message = intent.getStringExtra(EXTRA_MESSAGE)

        // Determine the title and notification ID based on the alarm type
        val title = if (type.equals(TYPE_ONE_TIME, ignoreCase = true)) TYPE_ONE_TIME else TYPE_REPEATING
        val notifId = if (type.equals(TYPE_ONE_TIME, ignoreCase = true)) ID_ONETIME else ID_REPEATING

        // If a message is provided, show the alarm notification
        if (message != null) {
            showAlarmNotification(context, title, message, notifId)
        }
    }

    /**
     * Shows a notification triggered by the alarm.
     *
     * This method creates and shows a notification with the specified title and message.
     * @param context The context in which the notification should be shown.
     * @param title The title of the notification.
     * @param message The message content of the notification.
     * @param notifId The unique ID for the notification.
     */
    @SuppressLint("NotificationPermission")
    private fun showAlarmNotification(context: Context, title: String, message: String, notifId: Int) {
        // Define notification channel properties
        val channelId = "Channel_1"
        val channelName = "AlarmManager channel"

        // Get the NotificationManager service
        val notificationManagerCompat = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        // Set the default notification sound
        val alarmSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)

        // Build the notification
        val builder = NotificationCompat.Builder(context, channelId)
            .setSmallIcon(R.drawable.baseline_access_time_24)
            .setContentTitle(title)
            .setContentText(message)
            .setColor(ContextCompat.getColor(context, android.R.color.transparent))
            .setVibrate(longArrayOf(1000, 1000, 1000, 1000, 1000))
            .setSound(alarmSound)

        // Create and set notification channel (for Android Oreo and above)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(channelId,
                channelName,
                NotificationManager.IMPORTANCE_DEFAULT)
            channel.enableVibration(true)
            channel.vibrationPattern = longArrayOf(1000, 1000, 1000, 1000, 1000)
            builder.setChannelId(channelId)
            notificationManagerCompat.createNotificationChannel(channel)
        }

        // Display the notification
        val notification = builder.build()
        notificationManagerCompat.notify(notifId, notification)
    }

    /**
     * Sets a one-time alarm using AlarmManager.
     *
     * This method schedules a one-time alarm to trigger at the specified date and time,
     * displaying the provided message when the alarm fires.
     * @param context The context in which the alarm should be set.
     * @param type The type of alarm.
     * @param date The date on which the alarm should trigger.
     * @param time The time at which the alarm should trigger.
     * @param message The message to be shown when the alarm triggers.
     */
    fun setOneTimeAlarm(
        context: Context,
        type: String,
        date: String,
        time: String,
        message: String
    ) {
        // Check if the provided date or time format is invalid
        if (isDateInvalid(date, DATE_FORMAT) || isDateInvalid(time, TIME_FORMAT)) return

        // Get the AlarmManager system service
        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager

        // Create an intent to be broadcasted when the alarm triggers
        val intent = Intent(context, AlarmReceiver::class.java)
        intent.putExtra(EXTRA_MESSAGE, message)
        intent.putExtra(EXTRA_TYPE, type)

        Log.e("ONE TIME", "$date $time")

        // Extract date and time components from the input strings
        val dateArray = date.split("-").toTypedArray()
        val timeArray = time.split(":").toTypedArray()

        // Set up the calendar object with the specified date and time
        val calendar = Calendar.getInstance()
        calendar.set(Calendar.YEAR, Integer.parseInt(dateArray[0]))
        calendar.set(Calendar.MONTH, Integer.parseInt(dateArray[1]) - 1)
        calendar.set(Calendar.DAY_OF_MONTH, Integer.parseInt(dateArray[2]))
        calendar.set(Calendar.HOUR_OF_DAY, Integer.parseInt(timeArray[0]))
        calendar.set(Calendar.MINUTE, Integer.parseInt(timeArray[1]))
        calendar.set(Calendar.SECOND, 0)

        // Create a PendingIntent for the one-time alarm
        val pendingIntent = PendingIntent.getBroadcast(context, ID_ONETIME, intent, PendingIntent.FLAG_IMMUTABLE)

        // Set the alarm using AlarmManager
        alarmManager.set(AlarmManager.RTC_WAKEUP, calendar.timeInMillis, pendingIntent)

        // Show a toast message indicating that the one-time alarm is set up
        Toast.makeText(context, "One time alarm set up", Toast.LENGTH_SHORT).show()
    }

    /**
     * Sets a repeating alarm using AlarmManager.
     *
     * This method schedules a repeating alarm to trigger at the specified time every day,
     * displaying the provided message when the alarm fires.
     * @param context The context in which the alarm should be set.
     * @param type The type of alarm ("OneTimeAlarm" or "RepeatingAlarm").
     * @param time The time at which the alarm should trigger (in "HH:mm" format).
     * @param message The message to be displayed when the alarm triggers.
     */
    fun setRepeatingAlarm(context: Context, type: String, time: String, message: String) {
        // Check if the provided time format is invalid
        if (isDateInvalid(time, TIME_FORMAT)) return

        // Get the AlarmManager system service
        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager

        // Create an intent to be broadcasted when the alarm triggers
        val intent = Intent(context, AlarmReceiver::class.java)
        intent.putExtra(EXTRA_MESSAGE, message)
        intent.putExtra(EXTRA_TYPE, type)

        // Extract hour and minute components from the input time string
        val timeArray = time.split(":".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()

        // Set up the calendar object with the specified time
        val calendar = Calendar.getInstance()
        calendar.set(Calendar.HOUR_OF_DAY, Integer.parseInt(timeArray[0]))
        calendar.set(Calendar.MINUTE, Integer.parseInt(timeArray[1]))
        calendar.set(Calendar.SECOND, 0)

        // Create a PendingIntent for the repeating alarm
        val pendingIntent = PendingIntent.getBroadcast(
            context,
            ID_REPEATING,
            intent,
            PendingIntent.FLAG_IMMUTABLE
        )

        // Set the repeating alarm using AlarmManager
        alarmManager.setInexactRepeating(
            AlarmManager.RTC_WAKEUP,
            calendar.timeInMillis,
            AlarmManager.INTERVAL_DAY,
            pendingIntent
        )

        // Show a toast message indicating that the repeating alarm is set up
        Toast.makeText(context, "Repeating alarm set up", Toast.LENGTH_SHORT).show()
    }

    /**
     * Cancels a previously set alarm.
     *
     * This method cancels either a one-time or repeating alarm based on the provided type.
     * @param context The context in which the alarm was set.
     * @param type The type of alarm to be canceled ("OneTimeAlarm" or "RepeatingAlarm").
     */
    fun cancelAlarm(context: Context, type: String) {
        // Get the AlarmManager system service
        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager

        // Create an intent for the AlarmReceiver
        val intent = Intent(context, AlarmReceiver::class.java)

        // Determine the request code based on the type of alarm
        val requestCode = if (type.equals(TYPE_ONE_TIME, ignoreCase = true)) ID_ONETIME else ID_REPEATING

        // Create a PendingIntent for the alarm
        val pendingIntent = PendingIntent.getBroadcast(context, requestCode, intent, PendingIntent.FLAG_IMMUTABLE)

        // Check if the PendingIntent exists
        if (pendingIntent != null) {
            // Cancel the PendingIntent and the alarm
            pendingIntent.cancel()
            alarmManager.cancel(pendingIntent)

            // Show a toast message indicating that the alarm is canceled
            Toast.makeText(context, "Repeating alarm dibatalkan", Toast.LENGTH_SHORT).show()
        }
    }

    /**
     * Checks if the provided date is valid according to the specified format.
     *
     * This method validates the format of the date string using the provided format pattern.
     * @param date The date string to be validated.
     * @param format The format of the date string.
     * @return True if the date is invalid, false otherwise.
     */
    private fun isDateInvalid(date: String, format: String): Boolean {
        return try {
            // Create a SimpleDateFormat object with the specified format pattern
            val df = SimpleDateFormat(format, Locale.getDefault())
            // Set lenient mode to false to enforce strict parsing
            df.isLenient = false
            // Attempt to parse the date string using the specified format
            df.parse(date)
            // If parsing succeeds without throwing a ParseException, the date is valid
            false
        } catch (e: ParseException) {
            // If parsing fails and a ParseException is caught, the date is invalid
            true
        }
    }

    companion object {
        // Constants for intent extras and IDs
        const val TYPE_ONE_TIME = "OneTimeAlarm"
        const val TYPE_REPEATING = "RepeatingAlarm"
        const val EXTRA_MESSAGE = "message"
        const val EXTRA_TYPE = "type"

        // 2 id for 2 type alarm, onetime dan repeating
        private const val ID_ONETIME = 100
        private const val ID_REPEATING = 101

        // Date and time formats
        private const val DATE_FORMAT = "yyyy-MM-dd"
        private const val TIME_FORMAT = "HH:mm"
    }
}