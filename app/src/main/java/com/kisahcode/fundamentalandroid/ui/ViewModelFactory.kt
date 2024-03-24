package com.kisahcode.fundamentalandroid.ui

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.kisahcode.fundamentalandroid.data.NewsRepository
import com.kisahcode.fundamentalandroid.di.Injection

/**
 * Factory for creating view models.
 *
 * @property newsRepository The repository for accessing news data.
 */
class ViewModelFactory private constructor(private val newsRepository: NewsRepository) :
    ViewModelProvider.NewInstanceFactory() {

    /**
     * Creates a new instance of the specified ViewModel class.
     *
     * @param modelClass The class of the ViewModel to create.
     * @return The created ViewModel instance.
     * @throws IllegalArgumentException if the ViewModel class is unknown.
     */
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(NewsViewModel::class.java)) {
            return NewsViewModel(newsRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class: " + modelClass.name)
    }

    companion object {
        @Volatile
        private var instance: ViewModelFactory? = null

        /**
         * Gets a singleton instance of the ViewModelFactory.
         *
         * @param context The application context.
         * @return The ViewModelFactory instance.
         */
        fun getInstance(context: Context): ViewModelFactory =
            instance ?: synchronized(this) {
                instance ?: ViewModelFactory(Injection.provideRepository(context))
            }.also { instance = it }
    }
}