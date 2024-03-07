package com.kisahcode.fundamentalandroid

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.RadioButton
import android.widget.RadioGroup
import androidx.fragment.app.DialogFragment

/**
 * A dialog fragment providing options for the user to choose from.
 * This fragment displays a set of radio buttons allowing the user to select an option.
 */
class OptionDialogFragment : DialogFragment() {

    private lateinit var btnChoose: Button
    private lateinit var btnClose: Button
    private lateinit var rgOptions: RadioGroup
    private lateinit var rbSaf: RadioButton
    private lateinit var rbMou: RadioButton
    private lateinit var rbLvg: RadioButton
    private lateinit var rbMoyes: RadioButton
    private var optionDialogListener: OnOptionDialogListener? = null

    /**
     * Called to have the fragment instantiate its user interface view.
     *
     * @param inflater The LayoutInflater object that can be used to inflate any views in the fragment.
     * @param container If non-null, this is the parent view that the fragment's UI should be attached to.
     * @param savedInstanceState If non-null, this fragment is being re-constructed from a previous saved state as given here.
     * @return Return the View for the fragment's UI, or null.
     */
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_option_dialog, container, false)
    }

    /**
     * Called immediately after onCreateView() has returned, providing a view to this fragment's layout.
     *
     * @param view The View returned by onCreateView().
     * @param savedInstanceState If non-null, this fragment is being re-constructed from a previous saved state as given here.
     */
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Initialize views
        btnChoose = view.findViewById(R.id.btn_choose)
        btnClose = view.findViewById(R.id.btn_close)
        rgOptions = view.findViewById(R.id.rg_options)
        rbSaf = view.findViewById(R.id.rb_saf)
        rbLvg = view.findViewById(R.id.rb_lvg)
        rbMou = view.findViewById(R.id.rb_mou)
        rbMoyes = view.findViewById(R.id.rb_moyes)

        // Set click listener for the choose button to handle option selection
        btnChoose.setOnClickListener {
            val checkedRadioButtonId = rgOptions.checkedRadioButtonId

            if (checkedRadioButtonId != -1) {
                // Determine the selected coach based on the checked radio button
                val coach: String? = when (checkedRadioButtonId) {
                    R.id.rb_saf -> rbSaf.text.toString().trim()
                    R.id.rb_mou -> rbMou.text.toString().trim()
                    R.id.rb_lvg -> rbLvg.text.toString().trim()
                    R.id.rb_moyes -> rbMoyes.text.toString().trim()
                    else -> null
                }

                // Notify the listener with the selected coach and dismiss the dialog
                optionDialogListener?.onOptionChosen(coach)
                dialog?.dismiss()
            }
        }

        // Set click listener for the close button to dismiss the dialog
        btnClose.setOnClickListener {
            dialog?.cancel()
        }
    }

    /**
     * Called when the fragment is associated with an activity.
     * This method is called when the fragment is first attached to its context.
     *
     * @param context The context to which the fragment is attached.
     */
    override fun onAttach(context: Context) {
        super.onAttach(context)

        // Check if the parent fragment is an instance of DetailCategoryFragment
        val fragment = parentFragment
        if (fragment is DetailCategoryFragment) {
            // Set the option dialog listener from the parent fragment
            this.optionDialogListener = fragment.optionDialogListener
        }
    }

    /**
     * Called when the fragment is no longer attached to its activity.
     * This is called after onDestroy().
     */
    override fun onDetach() {
        super.onDetach()
        // Reset the option dialog listener
        this.optionDialogListener = null
    }

    /**
     * Interface definition for a callback to be invoked when an option is chosen.
     */
    interface OnOptionDialogListener {
        fun onOptionChosen(text: String?)
    }
}