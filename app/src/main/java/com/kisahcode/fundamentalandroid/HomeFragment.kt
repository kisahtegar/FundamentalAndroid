package com.kisahcode.fundamentalandroid

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button

/**
 * A simple [Fragment] subclass representing the home screen of the application.
 * This fragment displays the main interface with options for users to navigate through the app.
 */
class HomeFragment : Fragment(), View.OnClickListener {

    /**
     * Called to have the fragment instantiate its user interface view.
     *
     * In HomeFragment there is an onCreateView() method where the interface layout is defined and
     * transformed from a layout in the form of an xml file into a view object using the inflate()
     * method.
     *
     * @param inflater The LayoutInflater object that can be used to inflate any views in the fragment,
     * @param container If non-null, this is the parent view that the fragment's UI should be attached to.
     * @param savedInstanceState If non-null, this fragment is being re-constructed from a previous saved state as given here.
     * @return Return the View for the fragment's UI, or null.
     */
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {

        /**
         * Inflater.inflate() is an object from LayoutInflater which functions to change the XML
         * layout into the form of a ViewGroup object or widget by calling the inflate() method.
         * Just like setContentView in Activity, the inflate function here is to display the layout
         * of the Fragment, where the layout displayed here is fragment_home.
         */
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    /**
     * Called immediately after onCreateView() has returned, providing a view to this fragment's layout.
     *
     * @param view The View returned by onCreateView().
     * @param savedInstanceState If non-null, this fragment is being re-constructed from a previous saved state as given here.
     */
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Initialize and set up the button for navigating to categories
        val btnCategory:Button = view.findViewById(R.id.btn_category)
        btnCategory.setOnClickListener(this)
    }

    /**
     * Called when a view has been clicked.
     *
     * @param v The view that was clicked.
     */
    override fun onClick(v: View?) {
        // Check if the clicked view is the category button
        if (v?.id == R.id.btn_category) {
            // Instance of CategoryFragment
            val categoryFragment = CategoryFragment()
            // Get the FragmentManager of the parent activity
            val fragmentManager = parentFragmentManager

            // Begin a fragment transaction to replace the current fragment with the CategoryFragment
            fragmentManager.beginTransaction().apply {
                replace(R.id.frame_container, categoryFragment, CategoryFragment::class.java.simpleName)
                // Add the transaction to the back stack to allow navigation back to the previous fragment
                addToBackStack(null)
                // Commit the transaction
                commit()
            }
        }
    }
}