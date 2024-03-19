package com.kisahcode.fundamentalandroid

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.kisahcode.fundamentalandroid.databinding.ActivityFormUserPreferenceBinding

/**
 * Activity for adding or editing user preferences.
 */
class FormUserPreferenceActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var binding: ActivityFormUserPreferenceBinding

    companion object {
        const val EXTRA_TYPE_FORM = "extra_type_form"
        const val EXTRA_RESULT = "extra_result"
        const val RESULT_CODE = 101

        const val TYPE_ADD = 1
        const val TYPE_EDIT = 2

        private const val FIELD_REQUIRED = "Field tidak boleh kosong"
        private const val FIELD_DIGIT_ONLY = "Hanya boleh terisi numerik"
        private const val FIELD_IS_NOT_VALID = "Email tidak valid"
    }

    private lateinit var userModel: UserModel

    /**
     * Initializes the activity.
     *
     * @param savedInstanceState The saved instance state.
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFormUserPreferenceBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Sets click listener for the save button.
        binding.btnSave.setOnClickListener(this)

        // Retrieves user model and form type from intent extras.
        userModel = intent.getParcelableExtra<UserModel>("USER") as UserModel
        val formType = intent.getIntExtra(EXTRA_TYPE_FORM, 0)

        // Sets the action bar title and button title based on form type.
        val actionBarTitle = if (formType == TYPE_ADD) "Tambah Baru" else "Ubah"
        val btnTitle = if (formType == TYPE_ADD) "Simpan" else "Update"
        supportActionBar?.title = actionBarTitle
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        binding.btnSave.text = btnTitle

        // Shows user preferences in form if form type is edit.
        if (formType == TYPE_EDIT) showPreferenceInForm()
    }

    /**
     * Displays existing user preferences in the form for editing.
     */
    private fun showPreferenceInForm() {
        with(binding) {
            edtName.setText(userModel.name)
            edtEmail.setText(userModel.email)
            edtAge.setText(userModel.age.toString())
            edtPhone.setText(userModel.phoneNumber)
            rbYes.isChecked = userModel.isLove
            rbNo.isChecked = !userModel.isLove
        }
    }

    /**
     * Handles click events on views.
     *
     * @param view The clicked view.
     */
    override fun onClick(view: View) {
        // Checks if the clicked view is the save button.
        if (view.id == R.id.btn_save) {
            // Retrieves input values from EditText fields.
            val name = binding.edtName.text.toString().trim()
            val email = binding.edtEmail.text.toString().trim()
            val age = binding.edtAge.text.toString().trim()
            val phoneNo = binding.edtPhone.text.toString().trim()
            val isLoveMU = binding.rgLoveMu.checkedRadioButtonId == R.id.rb_yes

            // Validates user input. Returns if validation fails.
            if (!validateInput(name, email, age, phoneNo)) return

            // Saves user data and prepares result intent.
            saveUser(name, email, age, phoneNo, isLoveMU)
            val resultIntent = Intent()
            resultIntent.putExtra(EXTRA_RESULT, userModel)
            setResult(RESULT_CODE, resultIntent)

            // Finishes the activity.
            finish()
        }
    }

    /**
     * Validates user input.
     *
     * @return true if all inputs are valid, false otherwise.
     */
    private fun validateInput(name: String, email: String, age: String, phoneNo: String): Boolean {
        val errorField = when {
            name.isEmpty() -> binding.edtName.also { it.error = FIELD_REQUIRED }
            email.isEmpty() -> binding.edtEmail.also { it.error = FIELD_REQUIRED }
            !isValidEmail(email) -> binding.edtEmail.also { it.error = FIELD_IS_NOT_VALID }
            age.isEmpty() -> binding.edtAge.also { it.error = FIELD_REQUIRED }
            phoneNo.isEmpty() -> binding.edtPhone.also { it.error = FIELD_REQUIRED }
            !TextUtils.isDigitsOnly(phoneNo) -> binding.edtPhone.also { it.error = FIELD_DIGIT_ONLY }
            else -> null
        }
        return errorField == null
    }

    /**
     * Saves user data to shared preferences.
     *
     * @param name The user's name.
     * @param email The user's email address.
     * @param age The user's age.
     * @param phoneNo The user's phone number.
     * @param isLoveMU Indicates whether the user loves MU (Manchester United).
     */
    private fun saveUser(name: String, email: String, age: String, phoneNo: String, isLoveMU: Boolean) {
        // Initializes UserPreference instance.
        val userPreference = UserPreference(this)

        // Updates userModel fields with provided data.
        userModel.name = name
        userModel.email = email
        userModel.age = Integer.parseInt(age)
        userModel.phoneNumber = phoneNo
        userModel.isLove = isLoveMU

        // Saves updated userModel to shared preferences.
        userPreference.setUser(userModel)

        // Shows toast message confirming data save.
        Toast.makeText(this, "Data tersimpan", Toast.LENGTH_SHORT).show()
    }

    /**
     * Checks if the provided email address is valid.
     *
     * @param email The email address to be validated.
     * @return True if the email address is valid, false otherwise.
     */
    private fun isValidEmail(email: CharSequence): Boolean {
        // Uses Android's built-in email address pattern matcher.
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }

    /**
     * Handles menu item selection events.
     *
     * @param item The selected menu item.
     * @return True if the event was handled successfully, false otherwise.
     */
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Checks if the selected menu item is the home button.
        if (item.itemId == android.R.id.home) {
            // Finishes the activity if the home button is clicked.
            finish()
        }

        // Returns the result of the onOptionsItemSelected method from the superclass.
        return super.onOptionsItemSelected(item)
    }
}