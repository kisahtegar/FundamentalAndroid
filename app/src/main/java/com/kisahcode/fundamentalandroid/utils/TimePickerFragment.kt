package com.kisahcode.fundamentalandroid.utils

import android.app.Dialog
import android.app.TimePickerDialog
import android.content.Context
import android.os.Bundle
import android.widget.TimePicker
import androidx.fragment.app.DialogFragment
import java.util.*

/**
 * A DialogFragment for selecting a time using a TimePickerDialog.
 * This class provides a dialog for choosing a time and notifies the attached listener when a time is selected.
 */
class TimePickerFragment : DialogFragment(), TimePickerDialog.OnTimeSetListener {

    // Listener interface for time selection events
    private var mListener: DialogTimeListener? = null

    /**
     * Called when the fragment is attached to a context.
     * Assigns the context as the listener for time selection events.
     * @param context The context to which the fragment is attached.
     */
    override fun onAttach(context: Context) {
        super.onAttach(context)
        mListener = context as DialogTimeListener?
    }

    /**
     * Creates a new instance of TimePickerDialog and initializes it with the current time.
     * @param savedInstanceState The saved instance state.
     * @return A new instance of TimePickerDialog.
     */
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val calendar = Calendar.getInstance()
        val hour = calendar.get(Calendar.HOUR_OF_DAY)
        val minute = calendar.get(Calendar.MINUTE)
        val formatHour24 = true
        return TimePickerDialog(activity, this, hour, minute, formatHour24)
    }

    /**
     * Called when a time is selected in the TimePickerDialog.
     * Notifies the attached listener with the selected time.
     * @param view The TimePicker view.
     * @param hourOfDay The selected hour of the day (in 24-hour format).
     * @param minute The selected minute.
     */
    override fun onTimeSet(view: TimePicker, hourOfDay: Int, minute: Int) {
        mListener?.onDialogTimeSet(tag, hourOfDay, minute)
    }

    /**
     * Called when the fragment is detached from its context.
     * Removes the listener to prevent memory leaks.
     */
    override fun onDetach() {
        super.onDetach()
        if (mListener != null) {
            mListener = null
        }
    }

    /**
     * Interface definition for a callback to be invoked when a time is set in the TimePickerDialog.
     */
    interface DialogTimeListener {

        /**
         * Called when a time is set in the TimePickerDialog.
         * @param tag The tag of the TimePickerFragment.
         * @param hourOfDay The selected hour of the day (in 24-hour format).
         * @param minute The selected minute.
         */
        fun onDialogTimeSet(tag: String?, hourOfDay: Int, minute: Int)
    }
}