package com.kisahcode.fundamentalandroid

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import com.kisahcode.fundamentalandroid.databinding.FragmentCategoryBinding

/**
 * A fragment representing a category screen of the application.
 * This fragment displays a list of categories and allows users to navigate to detailed category information.
 */
class CategoryFragment : Fragment() {

    // View binding instance for the fragment
    private var _binding: FragmentCategoryBinding? = null
    // This property is only valid between onCreateView and onDestroyView.
    private val binding get() = _binding!!

    /**
     * Companion object containing constants for passing data between fragments.
     */
    companion object {
        const val EXTRA_NAME = "extra_name"
        const val EXTRA_STOCK = "extra_stock"
    }

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
        // Inflate the layout for this fragment using view binding
        _binding = FragmentCategoryBinding.inflate(inflater, container, false)
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

        // Set click listener for the lifestyle category button to navigate to the detail category fragment
        binding.btnCategoryLifestyle.setOnClickListener {
            val bundle = Bundle().apply {
                // Pass data to the detail category fragment
                putString(EXTRA_NAME, "Lifestyle")
                putLong(EXTRA_STOCK, 7)
            }

            // Navigate to the detail category fragment with the provided bundle
            it.findNavController().navigate(R.id.action_categoryFragment_to_detailCategoryFragment, bundle)
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