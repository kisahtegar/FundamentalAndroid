package com.kisahcode.fundamentalandroid.data.local.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.kisahcode.fundamentalandroid.data.local.entity.NewsEntity

/**
 * Room database class for managing news entities.
 */
@Database(entities = [NewsEntity::class], version = 1, exportSchema = false)
abstract class NewsDatabase : RoomDatabase() {

    /**
     * Retrieves the DAO interface for accessing news entities.
     *
     * @return The DAO interface for news entities.
     */
    abstract fun newsDao(): NewsDao

    companion object {
        @Volatile
        private var instance: NewsDatabase? = null

        /**
         * Retrieves the singleton instance of the NewsDatabase.
         *
         * @param context The application context.
         * @return The singleton instance of NewsDatabase.
         */
        fun getInstance(context: Context): NewsDatabase =
            instance ?: synchronized(this) {
                instance ?: Room.databaseBuilder(
                    context.applicationContext,
                    NewsDatabase::class.java, "News.db"
                ).build()
            }
    }
}