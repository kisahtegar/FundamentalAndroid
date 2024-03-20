package com.kisahcode.fundamentalandroid

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

/**
 * ViewModelFactory responsible for creating ViewModel instances with constructor parameters.
 *
 * @property pref The SettingPreferences instance to be injected into ViewModel instances.
 */
class ViewModelFactory(private val pref: SettingPreferences) : ViewModelProvider.NewInstanceFactory() {

    /**
     * Creates a new instance of the specified ViewModel class with the provided constructor arguments.
     *
     * @param modelClass The class of the ViewModel to create.
     * @return A newly created ViewModel instance.
     * @throws IllegalArgumentException if the ViewModel class is unknown.
     */
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        // Check if the requested ViewModel class is assignable from MainViewModel
        if (modelClass.isAssignableFrom(MainViewModel::class.java)) {
            // If so, create a new MainViewModel instance with the provided SettingPreferences
            return MainViewModel(pref) as T
        }

        // If the ViewModel class is unknown, throw an IllegalArgumentException
        throw IllegalArgumentException("Unknown ViewModel class: " + modelClass.name)
    }
}