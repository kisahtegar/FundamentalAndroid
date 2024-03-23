package com.kisahcode.fundamentalandroid.helper

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.kisahcode.fundamentalandroid.ui.insert.NoteAddUpdateViewModel
import com.kisahcode.fundamentalandroid.ui.main.MainViewModel

/**
 * ViewModelFactory is responsible for creating instances of ViewModels with parameters.
 *
 * @property mApplication The application context required for creating ViewModels.
 */
class ViewModelFactory private constructor(private val mApplication: Application) : ViewModelProvider.NewInstanceFactory() {

    /**
     * Creates a new instance of the specified ViewModel.
     *
     * @param modelClass The class of the ViewModel to be created.
     * @return The newly created ViewModel instance.
     * @throws IllegalArgumentException If the ViewModel class is unknown.
     */
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MainViewModel::class.java)) {
            return MainViewModel(mApplication) as T
        } else if (modelClass.isAssignableFrom(NoteAddUpdateViewModel::class.java)) {
            return NoteAddUpdateViewModel(mApplication) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class: ${modelClass.name}")
    }

    /**
     * Companion object to provide a singleton instance of ViewModelFactory.
     */
    companion object {
        @Volatile
        private var INSTANCE: ViewModelFactory? = null

        /**
         * Retrieves or creates an instance of ViewModelFactory.
         *
         * @param application The application context.
         * @return The singleton instance of ViewModelFactory.
         */
        @JvmStatic
        fun getInstance(application: Application): ViewModelFactory {
            if (INSTANCE == null) {
                synchronized(ViewModelFactory::class.java) {
                    INSTANCE = ViewModelFactory(application)
                }
            }
            return INSTANCE as ViewModelFactory
        }
    }
}