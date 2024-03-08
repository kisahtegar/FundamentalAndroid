package com.kisahcode.fundamentalandroid

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import com.kisahcode.fundamentalandroid.databinding.FragmentHomeBinding

/**
 * A fragment representing the home screen of the application.
 * This fragment displays options for navigating to different sections of the app.
 */
class HomeFragment : Fragment() {

    // Declare a private mutable property to hold the view binding instance for the fragment.
    private var _binding: FragmentHomeBinding? = null

    // This property is only valid between onCreateView and onDestroyView.
    private val binding get() = _binding!!

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
    ): View {
        // Inflate the layout using view binding and assign the inflated view to the _binding variable.
        // The inflated view is attached to the parent (container) but not immediately added to the view hierarchy.
        _binding = FragmentHomeBinding.inflate(inflater, container, false)

        // Return the root view of the inflated layout.
        // This root view represents the top-level view of the inflated layout.
        // It will be added to the view hierarchy by the system when the fragment is attached to the activity.
        return binding.root
    }

    /**
     * Called immediately after onCreateView() has returned, providing a view to this fragment's layout.
     *
     * @param view The View returned by onCreateView().
     * @param savedInstanceState If non-null, this fragment is being re-constructed from a previous saved state as given here.
     */
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Set click listener for navigating to the category fragment
        binding.btnCateegory.setOnClickListener(
            Navigation.createNavigateOnClickListener(R.id.action_homeFragment_to_categoryFragment)
        )

        // Set click listener for navigating to the profile activity
        binding.btnProfile.setOnClickListener{
            it.findNavController().navigate(R.id.action_homeFragment_to_profileActivity)
        }
    }

    /**
     * Called when the fragment is no longer in use.
     * This is called after onDestroy().
     */
    override fun onDestroy() {
        super.onDestroy()
        // Nullify the binding reference to avoid memory leaks
        _binding = null
    }
}