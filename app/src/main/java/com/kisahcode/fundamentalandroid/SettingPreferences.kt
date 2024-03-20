package com.kisahcode.fundamentalandroid

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

/**
 * Extension property to provide access to DataStore instance for storing preferences.
 */
val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")

/**
 * Singleton class to manage application settings stored in DataStore.
 *
 * @property dataStore The DataStore instance to store preferences.
 */
class SettingPreferences private constructor(private val dataStore: DataStore<Preferences>) {

    // Key for storing theme setting preference
    private val THEME_KEY = booleanPreferencesKey("theme_setting")

    /**
     * Retrieves the theme setting preference as a Flow.
     *
     * @return A Flow emitting the current theme setting.
     */
    fun getThemeSetting(): Flow<Boolean> {
        return dataStore.data.map { preferences ->
            preferences[THEME_KEY] ?: false // Return false if theme setting is not found
        }
    }

    /**
     * Saves the theme setting preference.
     *
     * @param isDarkModeActive Boolean indicating whether dark mode is active.
     */
    suspend fun saveThemeSetting(isDarkModeActive: Boolean) {
        dataStore.edit { preferences ->
            preferences[THEME_KEY] = isDarkModeActive // Update the theme setting preference
        }
    }

    companion object {
        /**
         * The INSTANCE variable inside the companion object acts as a holder for the singleton
         * instance. This variable is marked @Volatile to ensure that changes made by one thread
         * to INSTANCE are immediately visible to all other threads, a consideration important
         * for multi-threaded applications.
         */
        @Volatile
        private var INSTANCE: SettingPreferences? = null

        /**
         * Retrieves an instance of SettingPreferences.
         *
         * @param dataStore The DataStore instance to store preferences.
         * @return An instance of SettingPreferences.
         */
        fun getInstance(dataStore: DataStore<Preferences>): SettingPreferences {
            return INSTANCE ?: synchronized(this) {
                val instance = SettingPreferences(dataStore)
                INSTANCE = instance
                instance
            }
        }
    }
}