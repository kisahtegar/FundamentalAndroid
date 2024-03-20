package com.kisahcode.fundamentalandroid

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

/**
 * ViewModel class responsible for managing UI-related data in the MainFragment.
 *
 * @param pref The SettingPreferences instance to access theme settings.
 */
class MainViewModel(private val pref: SettingPreferences) : ViewModel() {

    /**
     * Retrieves the theme settings as a LiveData.
     *
     * @return A LiveData emitting the current theme setting.
     */
    fun getThemeSettings(): LiveData<Boolean> {
        return pref.getThemeSetting().asLiveData()
    }

    /**
     * Saves the theme setting.
     *
     * @param isDarkModeActive Boolean indicating whether dark mode is active.
     */
    fun saveThemeSetting(isDarkModeActive: Boolean) {
        viewModelScope.launch {
            pref.saveThemeSetting(isDarkModeActive)
        }
    }
}