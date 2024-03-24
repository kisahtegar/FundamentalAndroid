package com.kisahcode.fundamentalandroid.di

import android.content.Context
import com.kisahcode.fundamentalandroid.data.NewsRepository
import com.kisahcode.fundamentalandroid.data.local.room.NewsDatabase
import com.kisahcode.fundamentalandroid.data.remote.retrofit.ApiConfig
import com.kisahcode.fundamentalandroid.utils.AppExecutors

/**
 * Dependency injection class responsible for providing instances of various components.
 */
object Injection {

    /**
     * Provides an instance of the NewsRepository with the required dependencies.
     *
     * @param context The application context.
     * @return NewsRepository An instance of the NewsRepository.
     */
    fun provideRepository(context: Context): NewsRepository {
        // Initialize required components
        val apiService = ApiConfig.getApiService()
        val database = NewsDatabase.getInstance(context)
        val dao = database.newsDao()
        val appExecutors = AppExecutors()

        // Return an instance of NewsRepository with the provided dependencies
        return NewsRepository.getInstance(apiService, dao, appExecutors)
    }
}