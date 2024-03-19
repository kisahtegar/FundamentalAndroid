package com.kisahcode.fundamentalandroid

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

/**
 * The main activity of the application responsible for displaying the settings fragment.
 */
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Load the settings fragment into the activity
        supportFragmentManager
            .beginTransaction()
            .add(R.id.setting_holder, MyPreferenceFragment())
            .commit()
    }
}