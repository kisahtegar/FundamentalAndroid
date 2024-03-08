package com.kisahcode.fundamentalandroid

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

/**
 * An activity representing the user profile screen.
 * This activity displays the user's profile information.
 */
class ProfileActivity : AppCompatActivity() {
    /**
     * Called when the activity is starting.
     *
     * @param savedInstanceState If the activity is being re-initialized after previously being shut down, then this Bundle contains the data it most recently supplied in onSaveInstanceState(Bundle).
     * @return This method returns nothing.
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)
    }
}