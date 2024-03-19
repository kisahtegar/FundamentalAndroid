package com.kisahcode.fundamentalandroid

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

/**
 * Data class representing a user model.
 * This class implements the Parcelable interface to allow easy data serialization for passing
 * between components of an Android application.
 *
 * @property name The name of the user.
 * @property email The email address of the user.
 * @property age The age of the user.
 * @property phoneNumber The phone number of the user.
 * @property isLove A flag indicating whether the user loves something.
 */
@Parcelize
data class UserModel(
    var name: String? = null,
    var email: String? = null,
    var age: Int = 0,
    var phoneNumber: String? = null,
    var isLove: Boolean = false
) : Parcelable
