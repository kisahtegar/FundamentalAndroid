package com.kisahcode.fundamentalandroid.data

/**
 * A sealed class representing different states of a result.
 * @param R The type of data wrapped by the result.
 */
sealed class Result<out R> private constructor() {

    /**
     * Represents a successful result with associated data of type [T].
     * @property data The data associated with the success result.
     */
    data class Success<out T>(val data: T) : Result<T>()

    /**
     * Represents an error result with an error message.
     * @property error The error message associated with the error result.
     */
    data class Error(val error: String) : Result<Nothing>()

    /**
     * Represents a loading state.
     */
    data object Loading : Result<Nothing>()
}