package com.kisahcode.fundamentalandroid

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity

/**
 * The main activity of the application. This activity serves as the entry point of the app and hosts various fragments.
 * It handles the initialization of the UI components and manages fragment transactions.
 */
class MainActivity : AppCompatActivity() {

    /**
     * Called when the activity is starting. This is where most initialization should go.
     *
     * @param savedInstanceState If the activity is being re-initialized after previously being shut down then this Bundle contains the data it most recently supplied in onSaveInstanceState(Bundle).
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Initialize FragmentManager to manage fragments within the activity
        val fragmentManager = supportFragmentManager
        val homeFragment = HomeFragment() // Instance the HomeFragment
        // Check if the HomeFragment is already added to the activity
        val fragment = fragmentManager.findFragmentByTag(HomeFragment::class.java.simpleName)

        // If the HomeFragment is not already added, add it to the activity
        if (fragment !is HomeFragment) {
            // Log the name of the fragment being added for debugging purposes
            Log.d("MyFlexibleFragment", "Fragment Name:" + HomeFragment::class.java.simpleName)

            // Begin a transaction to add the HomeFragment to the activity
            fragmentManager
                .beginTransaction()
                .add(R.id.frame_container, homeFragment, HomeFragment::class.java.simpleName)
                .commit()
        }
    }
}