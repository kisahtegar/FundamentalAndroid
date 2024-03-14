package com.kisahcode.fundamentalandroid.utils

/**
 * A wrapper class for data exposed via a LiveData, representing an event that should be handled only once.
 * @param T The type of data encapsulated by this event.
 * @property content The content of the event.
 */

open class Event<out T>(private val content: T) {

    /**
     * A flag indicating whether the event has already been handled.
     */
    @Suppress("MemberVisibilityCanBePrivate")
    var hasBeenHandled = false
        private set // Allow external read access but not write

    /**
     * Retrieves the content of the event if it has not been handled yet, marking it as handled if retrieved.
     * @return The content of the event if not already handled, or null otherwise.
     */
    fun getContentIfNotHandled(): T? {
        return if (hasBeenHandled) {
            null
        } else {
            hasBeenHandled = true
            content
        }
    }

    /**
     * Retrieves the content of the event, even if it has been handled already.
     * @return The content of the event.
     */
    fun peekContent(): T = content
}