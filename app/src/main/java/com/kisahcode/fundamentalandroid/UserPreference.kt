package com.kisahcode.fundamentalandroid

import android.content.Context

/**
 * Helper class for managing user preferences.
 * This class provides methods for storing and retrieving user data in SharedPreferences.
 *
 * @property context The context used to access SharedPreferences.
 */
class UserPreference(context: Context) {
    companion object {
        private const val PREFS_NAME = "user_pref"
        private const val NAME = "name"
        private const val EMAIL = "email"
        private const val AGE = "age"
        private const val PHONE_NUMBER = "phone"
        private const val LOVE_MU = "islove"
    }

    private val preferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)

    /**
     * Sets the user data in SharedPreferences.
     *
     * @param value The UserModel object representing the user data to be stored.
     */
    fun setUser(value: UserModel) {
        val editor = preferences.edit()
        editor.putString(NAME, value.name)
        editor.putString(EMAIL, value.email)
        editor.putInt(AGE, value.age)
        editor.putString(PHONE_NUMBER, value.phoneNumber)
        editor.putBoolean(LOVE_MU, value.isLove)
        editor.apply()
    }

    /**
     * Retrieves the user data from SharedPreferences.
     *
     * @return A UserModel object containing the user data retrieved from SharedPreferences.
     */
    fun getUser(): UserModel {
        val model = UserModel()
        model.name = preferences.getString(NAME, "")
        model.email = preferences.getString(EMAIL, "")
        model.age = preferences.getInt(AGE, 0)
        model.phoneNumber = preferences.getString(PHONE_NUMBER, "")
        model.isLove = preferences.getBoolean(LOVE_MU, false)

        return model
    }
}