package com.kisahcode.fundamentalandroid

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

/**
 * The main activity of the application.
 * This activity serves as the entry point of the app.
 */
class MainActivity : AppCompatActivity() {


    /**
     * Called when the activity is starting.
     * This is where most initialization should go.
     *
     * @param savedInstanceState If the activity is being re-initialized after previously being shut
     * down then this Bundle contains the data it most recently supplied in onSaveInstanceState(Bundle).
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
}