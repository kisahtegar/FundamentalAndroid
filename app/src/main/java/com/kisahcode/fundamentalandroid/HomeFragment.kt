package com.kisahcode.fundamentalandroid

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView

/**
 * A simple [Fragment] subclass.
 * Use the [HomeFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class HomeFragment : Fragment() {

    /**
     * Override onCreateView to create and return the fragment view hierarchy.
     * @param inflater The LayoutInflater object that can be used to inflate any views in the fragment.
     * @param container The parent view that the fragment's UI should be attached to.
     * @param savedInstanceState A Bundle object containing the fragment's previously saved state, if any.
     * @return Return the View for the fragment's UI.
     */
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    /**
     * Override onViewCreated to perform operations after the fragment view has been created.
     * @param view The View returned by onCreateView.
     * @param savedInstanceState A Bundle object containing the fragment's previously saved state, if any.
     */
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val tvLabel: TextView = view.findViewById(R.id.section_label)

        // Retrieve the index argument passed to the fragment
        val index = arguments?.getInt(ARG_SECTION_NUMBER, 0)

        // Set the text of the TextView using a string resource with formatted text
        tvLabel.text = getString(R.string.content_tab_text, index)
    }

    companion object {
        // Argument key for the section number
        const val ARG_SECTION_NUMBER = "section_number"
    }
}