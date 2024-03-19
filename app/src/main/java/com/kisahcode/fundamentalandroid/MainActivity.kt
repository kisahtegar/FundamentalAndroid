package com.kisahcode.fundamentalandroid

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.kisahcode.fundamentalandroid.databinding.ActivityMainBinding

/**
 * This activity serves as the main entry point of the application,
 * where the user's preferences are displayed and can be modified.
 */
class MainActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var mUserPreference: UserPreference
    private lateinit var binding: ActivityMainBinding

    private var isPreferenceEmpty = false // Flag indicating whether the user preference is empty or not
    private lateinit var userModel: UserModel

    // Activity result launcher for handling form user preference activity results
    private val resultLauncher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result: ActivityResult ->
        if (result.data != null && result.resultCode == FormUserPreferenceActivity.RESULT_CODE) {
            userModel = result.data?.getParcelableExtra<UserModel>(FormUserPreferenceActivity.EXTRA_RESULT) as UserModel
            populateView(userModel)
            checkForm(userModel)
        }
    }

    /**
     * Called when the activity is created.
     *
     * @param savedInstanceState The saved instance state.
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Set the title of the action bar
        supportActionBar?.title = "My User Preference"

        // Initialize the user preference instance
        mUserPreference = UserPreference(this)

        // Show existing user preference data
        showExistingPreference()

        // Set click listener for the save button
        binding.btnSave.setOnClickListener(this)

    }

    /**
     * Displays existing user preference data.
     */
    private fun showExistingPreference() {
        // Retrieve user preference data from the UserPreference instance
        userModel = mUserPreference.getUser()
        // Populate the UI with the retrieved user preference data
        populateView(userModel)
        // Check the form status based on the retrieved user preference data
        checkForm(userModel)
    }

    /**
     * Populates the view with user preference data.
     *
     * @param userModel The user model containing the user's data.
     */
    private fun populateView(userModel: UserModel) {
        with(binding) {
            tvName.text = if (userModel.name.toString().isEmpty()) "Tidak Ada" else userModel.name
            tvAge.text = if (userModel.age.toString().isEmpty()) "Tidak Ada" else userModel.age.toString()
            tvIsLoveMu.text = if (userModel.isLove) "Ya" else "Tidak"
            tvEmail.text = if (userModel.email.toString().isEmpty()) "Tidak Ada" else userModel.email
            tvPhone.text = if (userModel.phoneNumber.toString().isEmpty()) "Tidak Ada" else userModel.phoneNumber
        }
    }

    /**
     * Checks the form based on the provided user model.
     *
     * @param userModel The user model containing the user's data.
     */
    private fun checkForm(userModel: UserModel) {
        when {
            userModel.name.toString().isNotEmpty() -> {
                binding.btnSave.text = getString(R.string.change)
                isPreferenceEmpty = false
            }
            else -> {
                binding.btnSave.text = getString(R.string.save)
                isPreferenceEmpty = true
            }
        }
    }

    /**
     * Handles click events for the activity views.
     *
     * @param view The view that was clicked.
     */
    override fun onClick(view: View) {
        // Check if the clicked view is the save button
        if (view.id == R.id.btn_save) {
            // Create an intent to navigate to the FormUserPreferenceActivity
            val intent = Intent(this@MainActivity, FormUserPreferenceActivity::class.java)
            when {
                isPreferenceEmpty -> {
                    // If the preference is empty, set the type of form to add
                    intent.putExtra(
                        FormUserPreferenceActivity.EXTRA_TYPE_FORM,
                        FormUserPreferenceActivity.TYPE_ADD
                    )
                    // Pass the user model data to the FormUserPreferenceActivity
                    intent.putExtra("USER", userModel)
                }
                else -> {
                    // If the preference exists, set the type of form to edit
                    intent.putExtra(
                        FormUserPreferenceActivity.EXTRA_TYPE_FORM,
                        FormUserPreferenceActivity.TYPE_EDIT
                    )
                    // Pass the user model data to the FormUserPreferenceActivity
                    intent.putExtra("USER", userModel)
                }
            }

            // Launch the FormUserPreferenceActivity and handle the result using resultLauncher
            resultLauncher.launch(intent)
        }
    }
}