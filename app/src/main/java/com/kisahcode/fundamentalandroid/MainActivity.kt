package com.kisahcode.fundamentalandroid

import android.os.Bundle
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.kisahcode.fundamentalandroid.databinding.ActivityMainBinding

/**
 * The main activity of the application.
 * This activity hosts the bottom navigation view and manages navigation between different fragments.
 */
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Inflating layout and setting content view
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Finding the bottom navigation view
        val navView: BottomNavigationView = binding.navView

        // Finding the navController associated with the NavHostFragment
        val navController = findNavController(R.id.nav_host_fragment_activity_main)

        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.navigation_home, R.id.navigation_dashboard, R.id.navigation_notifications, R.id.navigation_profile
            )
        )

        // Setting up action bar with the navController and app bar configuration
        setupActionBarWithNavController(navController, appBarConfiguration)

        // Setting up bottom navigation view with the navController
        navView.setupWithNavController(navController)
    }
}