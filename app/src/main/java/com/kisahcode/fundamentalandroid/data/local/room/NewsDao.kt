package com.kisahcode.fundamentalandroid.data.local.room

import androidx.lifecycle.LiveData
import androidx.room.*
import com.kisahcode.fundamentalandroid.data.local.entity.NewsEntity

/**
 * Data Access Object (DAO) for accessing news entities in the local database.
 */
@Dao
interface NewsDao {

    /**
     * Retrieves all news items sorted by their publication date in descending order.
     *
     * @return LiveData list of news entities.
     */
    @Query("SELECT * FROM news ORDER BY publishedAt DESC")
    fun getNews(): LiveData<List<NewsEntity>>

    /**
     * Retrieves all bookmarked news items.
     *
     * @return LiveData list of bookmarked news entities.
     */
    @Query("SELECT * FROM news where bookmarked = 1")
    fun getBookmarkedNews(): LiveData<List<NewsEntity>>

    /**
     * Inserts a list of news entities into the database.
     *
     * @param news The list of news entities to be inserted.
     */
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertNews(news: List<NewsEntity>)

    /**
     * Updates an existing news entity in the database.
     *
     * @param news The news entity to be updated.
     */
    @Update
    fun updateNews(news: NewsEntity)

    /**
     * Deletes all non-bookmarked news items from the database.
     */
    @Query("DELETE FROM news WHERE bookmarked = 0")
    fun deleteAll()

    /**
     * Checks if a news item is bookmarked.
     *
     * @param title The title of the news item to check.
     * @return True if the news item is bookmarked, false otherwise.
     */
    @Query("SELECT EXISTS(SELECT * FROM news WHERE title = :title AND bookmarked = 1)")
    fun isNewsBookmarked(title: String): Boolean
}