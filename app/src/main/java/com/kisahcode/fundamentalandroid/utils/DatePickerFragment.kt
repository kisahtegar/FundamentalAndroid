package com.kisahcode.fundamentalandroid.utils

import android.app.DatePickerDialog
import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.widget.DatePicker
import androidx.fragment.app.DialogFragment
import java.util.*

/**
 * A DialogFragment for selecting a date using a DatePickerDialog.
 * This class provides a dialog for choosing a date and notifies the attached listener when a date is selected.
 */
class DatePickerFragment : DialogFragment(), DatePickerDialog.OnDateSetListener {

    // Listener interface for date selection events
    private var mListener: DialogDateListener? = null

    /**
     * Called when the fragment is attached to a context.
     *
     * Assigns the context as the listener for date selection events.
     * @param context The context to which the fragment is attached.
     */
    override fun onAttach(context: Context) {
        super.onAttach(context)
        mListener = context as DialogDateListener?
    }


    /**
     * Creates a new instance of DatePickerDialog and initializes it with the current date.
     * @param savedInstanceState The saved instance state.
     * @return A new instance of DatePickerDialog.
     */
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val calendar = Calendar.getInstance()
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val date = calendar.get(Calendar.DATE)
        return DatePickerDialog(activity as Context, this, year, month, date)
    }

    /**
     * Called when a date is selected in the DatePickerDialog.
     *
     * Notifies the attached listener with the selected date.
     * @param view The DatePicker view.
     * @param year The selected year.
     * @param month The selected month (0-indexed).
     * @param dayOfMonth The selected day of the month.
     */
    override fun onDateSet(view: DatePicker, year: Int, month: Int, dayOfMonth: Int) {
        mListener?.onDialogDateSet(tag, year, month, dayOfMonth)
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
     * Interface definition for a callback to be invoked when a date is set in the DatePickerDialog.
     */
    interface DialogDateListener {

        /**
         * Called when a date is set in the DatePickerDialog.
         * @param tag The tag of the DatePickerFragment.
         * @param year The selected year.
         * @param month The selected month (0-indexed).
         * @param dayOfMonth The selected day of the month.
         */
        fun onDialogDateSet(tag: String?, year: Int, month: Int, dayOfMonth: Int)
    }

}