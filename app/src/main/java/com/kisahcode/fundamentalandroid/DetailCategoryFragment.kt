package com.kisahcode.fundamentalandroid

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.kisahcode.fundamentalandroid.databinding.FragmentDetailCategoryBinding


/**
 * A fragment representing detailed category information.
 * This fragment displays detailed information about a specific category.
 */
class DetailCategoryFragment : Fragment() {

    // View binding instance for the fragment
    private var _binding: FragmentDetailCategoryBinding? = null
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
        // Inflate the layout for this fragment using view binding
        _binding = FragmentDetailCategoryBinding.inflate(inflater, container, false)
        return  binding.root
    }

    /**
     * Called immediately after onCreateView() has returned, providing a view to this fragment's layout.
     *
     * @param view The View returned by onCreateView().
     * @param savedInstanceState If non-null, this fragment is being re-constructed from a previous saved state as given here.
     */
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Retrieve data passed from the category fragment arguments
        val dataName = arguments?.getString(CategoryFragment.EXTRA_NAME)
        val dataDescription = arguments?.getLong(CategoryFragment.EXTRA_STOCK)

        // Set the category name and description text views
        binding.tvCategoryName.text = dataName
        "Stock: $dataDescription".also {
            binding.tvCategoryDescription.text = it
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