package com.kisahcode.fundamentalandroid

import android.os.Bundle
import android.widget.CompoundButton
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.switchmaterial.SwitchMaterial

/**
 * The main activity responsible for displaying the user interface and managing theme settings.
 */
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Initialize the SwitchMaterial view for toggling the theme
        val switchTheme = findViewById<SwitchMaterial>(R.id.switch_theme)

        // Initialize SettingPreferences instance using DataStore
        val pref = SettingPreferences.getInstance(application.dataStore)

        // Initialize MainViewModel instance with SettingPreferences
        val mainViewModel = ViewModelProvider(this, ViewModelFactory(pref)).get(
            MainViewModel::class.java
        )

        // Observe theme settings changes and update UI accordingly
        mainViewModel.getThemeSettings().observe(this) { isDarkModeActive: Boolean ->
            if (isDarkModeActive) {
                // Set night mode to MODE_NIGHT_YES if dark mode is active
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                switchTheme.isChecked = true
            } else {
                // Set night mode to MODE_NIGHT_NO if dark mode is not active
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                switchTheme.isChecked = false
            }
        }

        // Listen for changes in the SwitchMaterial state and update theme settings accordingly
        switchTheme.setOnCheckedChangeListener { _: CompoundButton?, isChecked: Boolean ->
            mainViewModel.saveThemeSetting(isChecked)
        }
    }
}