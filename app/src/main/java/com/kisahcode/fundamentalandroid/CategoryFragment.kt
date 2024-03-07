package com.kisahcode.fundamentalandroid

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button

/**
 * A [Fragment] subclass representing the category screen of the application.
 * This fragment displays a list of categories and allows users to navigate to detailed category information.
 */
class CategoryFragment : Fragment(), View.OnClickListener {

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
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_category, container, false)
    }

    /**
     * Called immediately after onCreateView() has returned, providing a view to this fragment's layout.
     *
     * @param view The View returned by onCreateView().
     * @param savedInstanceState If non-null, this fragment is being re-constructed from a previous saved state as given here.
     */
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val btnDetailCategory: Button = view.findViewById(R.id.btn_detail_category)
        btnDetailCategory.setOnClickListener(this)
    }

    /**
     * Called when a view has been clicked.
     *
     * @param v The view that was clicked.
     */
    override fun onClick(v: View?) {
        // Check if the clicked view is the button for detailed category information
        if (v?.id == R.id.btn_detail_category) {
            val detailCategoryFragment = DetailCategoryFragment()

            // Create a Bundle to pass data to the DetailCategoryFragment
            val bundle = Bundle()
            bundle.putString(DetailCategoryFragment.EXTRA_NAME, "Lifestyle")

            // Additional description to be passed to the DetailCategoryFragment
            val description = "Kategori ini akan berisi produk-produk lifestyle"

            // Set the arguments of the DetailCategoryFragment with the Bundle
            detailCategoryFragment.arguments = bundle

            // Set the description of the DetailCategoryFragment
            detailCategoryFragment.description = description

            // Begin a fragment transaction to replace the current fragment with the DetailCategoryFragment
            val fragmentManager = parentFragmentManager
            fragmentManager.beginTransaction().apply {
                replace(R.id.frame_container, detailCategoryFragment, DetailCategoryFragment::class.java.simpleName)
                // Add the transaction to the back stack to allow navigation back to the previous fragment
                addToBackStack(null)
                // Commit the transaction
                commit()
            }
        }
    }
}