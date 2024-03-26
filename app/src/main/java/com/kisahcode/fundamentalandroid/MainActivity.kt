package com.kisahcode.fundamentalandroid

import android.Manifest
import android.os.Build
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.kisahcode.fundamentalandroid.databinding.ActivityMainBinding
import com.kisahcode.fundamentalandroid.utils.DatePickerFragment
import com.kisahcode.fundamentalandroid.utils.TimePickerFragment
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

/**
 * The main activity of the application.
 *
 * This activity allows users to set one-time alarms and handles date and time selection dialogs.
 */
class MainActivity : AppCompatActivity(), View.OnClickListener, DatePickerFragment.DialogDateListener, TimePickerFragment.DialogTimeListener {

    // View binding instance for the activity layout
    private var binding: ActivityMainBinding? = null
    // Instance of AlarmReceiver for managing alarms
    private lateinit var alarmReceiver: AlarmReceiver

    // Activity result launcher for requesting notifications permission
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
     *
     * Performs initialization tasks including setting up views, requesting notifications permission,
     * and setting up listeners for buttons.
     * @param savedInstanceState The saved instance state.
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding?.root)

        // Request notifications permission for Android versions >= 33 (Android 12)
        if (Build.VERSION.SDK_INT >= 33) {
            requestPermissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
        }

        // Set up click listeners for one-time-alarm buttons
        binding?.btnOnceDate?.setOnClickListener(this)
        binding?.btnOnceTime?.setOnClickListener(this)
        binding?.btnSetOnceAlarm?.setOnClickListener(this)

        // Set up click listeners for repeating-alarm buttons
        binding?.btnRepeatingTime?.setOnClickListener(this)
        binding?.btnSetRepeatingAlarm?.setOnClickListener(this)

        // Initialize AlarmReceiver instance
        alarmReceiver = AlarmReceiver()
    }

    /**
     * Handles clicks on various views.
     * @param v The view that was clicked.
     */
    override fun onClick(v: View?) {
        when (v?.id) {
            // Show date picker dialog when 'Pick Date' button is clicked
            R.id.btn_once_date -> {
                val datePickerFragment = DatePickerFragment()
                datePickerFragment.show(supportFragmentManager, DATE_PICKER_TAG)
            }
            // Show time picker dialog when 'Pick Time' button is clicked
            R.id.btn_once_time -> {
                val timePickerFragmentOne = TimePickerFragment()
                timePickerFragmentOne.show(supportFragmentManager, TIME_PICKER_ONCE_TAG)
            }
            // Set one-time alarm when 'Set Once Alarm' button is clicked
            R.id.btn_set_once_alarm -> {
                val onceDate = binding?.tvOnceDate?.text.toString()
                val onceTime = binding?.tvOnceTime?.text.toString()
                val onceMessage = binding?.edtOnceMessage?.text.toString()

                alarmReceiver.setOneTimeAlarm(this, AlarmReceiver.TYPE_ONE_TIME,
                    onceDate,
                    onceTime,
                    onceMessage)
            }
            // Show time picker dialog when 'Pick Time' button is clicked for repeating alarm
            R.id.btn_repeating_time -> {
                val timePickerFragmentRepeat = TimePickerFragment()
                timePickerFragmentRepeat.show(supportFragmentManager, TIME_PICKER_REPEAT_TAG)
            }
            // Set repeating alarm when 'Set Repeating Alarm' button is clicked
            R.id.btn_set_repeating_alarm -> {
                val repeatTime = binding?.tvRepeatingTime?.text.toString()
                val repeatMessage = binding?.edtRepeatingMessage?.text.toString()
                alarmReceiver.setRepeatingAlarm(this, AlarmReceiver.TYPE_REPEATING,
                    repeatTime, repeatMessage)
            }
        }
    }

    /**
     * Called when a date is set in the DatePickerDialog.
     *
     * Updates the date TextView with the selected date.
     * @param tag The tag of the DatePickerFragment.
     * @param year The selected year.
     * @param month The selected month (0-indexed).
     * @param dayOfMonth The selected day of the month.
     */
    override fun onDialogDateSet(tag: String?, year: Int, month: Int, dayOfMonth: Int) {
        // Create a Calendar instance and set it to the selected date
        val calendar = Calendar.getInstance()
        calendar.set(year, month, dayOfMonth)

        // Create a date formatter to format the selected date
        val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())

        // Update the TextView displaying the selected date with the formatted date
        binding?.tvOnceDate?.text = dateFormat.format(calendar.time)
    }

    /**
     * Called when a time is set in the TimePickerDialog.
     *
     * Updates the time TextView with the selected time.
     * @param tag The tag of the TimePickerFragment.
     * @param hourOfDay The selected hour of the day (in 24-hour format).
     * @param minute The selected minute.
     */
    override fun onDialogTimeSet(tag: String?, hourOfDay: Int, minute: Int) {
        // Create a Calendar instance and set it to the selected time
        val calendar = Calendar.getInstance()
        calendar.set(Calendar.HOUR_OF_DAY, hourOfDay)
        calendar.set(Calendar.MINUTE, minute)

        // Create a time formatter to format the selected time
        val dateFormat = SimpleDateFormat("HH:mm", Locale.getDefault())

        // Update the TextView displaying the selected time based on the tag
        when (tag) {
            // If the tag corresponds to the one-time time picker, update the corresponding TextView
            TIME_PICKER_ONCE_TAG -> binding?.tvOnceTime?.text = dateFormat.format(calendar.time)
            // If the tag corresponds to the repeat-time time picker, update the corresponding TextView
            TIME_PICKER_REPEAT_TAG -> binding?.tvRepeatingTime?.text = dateFormat.format(calendar.time)
            // For other cases (e.g., repeating time picker), no action is taken
            // You can add specific actions for other cases if needed
            else -> {}
        }
    }

    /**
     * Called when the activity is destroyed.
     * Releases the binding to prevent memory leaks.
     */
    override fun onDestroy() {
        super.onDestroy()
        binding = null
    }

    companion object {
        // Constants for tag names of date and time picker fragments
        private const val DATE_PICKER_TAG = "DatePicker"
        private const val TIME_PICKER_ONCE_TAG = "TimePickerOnce"
        private const val TIME_PICKER_REPEAT_TAG = "TimePickerRepeat"
    }
}