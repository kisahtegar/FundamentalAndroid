package com.kisahcode.fundamentalandroid

import android.content.SharedPreferences
import android.os.Bundle

import androidx.preference.CheckBoxPreference
import androidx.preference.EditTextPreference
import androidx.preference.PreferenceFragmentCompat

/**
 * A fragment that displays a set of application preferences.
 */
class MyPreferenceFragment : PreferenceFragmentCompat(), SharedPreferences.OnSharedPreferenceChangeListener {

    // Preference keys
    private lateinit var NAME: String
    private lateinit var EMAIL: String
    private lateinit var AGE: String
    private lateinit var PHONE: String
    private lateinit var LOVE: String

    // Preference UI components
    private lateinit var namePreference: EditTextPreference
    private lateinit var emailPreference: EditTextPreference
    private lateinit var agePreference: EditTextPreference
    private lateinit var phonePreference: EditTextPreference
    private lateinit var isLoveMuPreference: CheckBoxPreference

    companion object {
        // Default value for preference summaries
        private const val DEFAULT_VALUE = "Tidak Ada"
    }

    /**
     * Called to do initial creation of the fragment.
     */
    override fun onCreatePreferences(bundle: Bundle?, s: String?) {
        addPreferencesFromResource(R.xml.preferences)
        initPreferences()
        setSummaries()
    }

    /**
     * Initializes preferences and related components.
     */
    private fun initPreferences() {

        NAME = resources.getString(R.string.key_name)
        EMAIL = resources.getString(R.string.key_email)
        AGE = resources.getString(R.string.key_age)
        PHONE = resources.getString(R.string.key_phone)
        LOVE = resources.getString(R.string.key_love)

        namePreference = findPreference<EditTextPreference>(NAME) as EditTextPreference
        emailPreference = findPreference<EditTextPreference>(EMAIL) as EditTextPreference
        agePreference = findPreference<EditTextPreference>(AGE) as EditTextPreference
        phonePreference = findPreference<EditTextPreference>(PHONE) as EditTextPreference
        isLoveMuPreference = findPreference<CheckBoxPreference>(LOVE) as CheckBoxPreference
    }

    /**
     * Called when the fragment is visible to the user.
     */
    override fun onResume() {
        super.onResume()
        preferenceScreen.sharedPreferences?.registerOnSharedPreferenceChangeListener(this)
    }

    /**
     * Called when the fragment is no longer started.
     */
    override fun onPause() {
        super.onPause()
        preferenceScreen.sharedPreferences?.unregisterOnSharedPreferenceChangeListener(this)
    }

    /**
     * Called when a shared preference is changed, added, or removed.
     *
     * @param sharedPreferences The SharedPreferences that received the change.
     * @param key The key of the preference that was changed, added, or removed.
     */
    override fun onSharedPreferenceChanged(sharedPreferences: SharedPreferences, key: String?) {
        when (key) {
            NAME -> namePreference.summary = sharedPreferences.getString(NAME, DEFAULT_VALUE)
            EMAIL -> emailPreference.summary = sharedPreferences.getString(EMAIL, DEFAULT_VALUE)
            AGE -> agePreference.summary = sharedPreferences.getString(AGE, DEFAULT_VALUE)
            PHONE -> phonePreference.summary = sharedPreferences.getString(PHONE, DEFAULT_VALUE)
            LOVE -> isLoveMuPreference.isChecked = sharedPreferences.getBoolean(LOVE, false)
        }
    }

    /**
     * Sets the initial summaries for the preferences.
     */
    private fun setSummaries() {
        val sh = preferenceManager.sharedPreferences

        sh?.let {
            namePreference.summary = sh.getString(NAME, DEFAULT_VALUE)
            emailPreference.summary = sh.getString(EMAIL, DEFAULT_VALUE)
            agePreference.summary = sh.getString(AGE, DEFAULT_VALUE)
            phonePreference.summary = sh.getString(PHONE, DEFAULT_VALUE)
            isLoveMuPreference.isChecked = sh.getBoolean(LOVE, false)
        }
    }
}