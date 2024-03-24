package com.kisahcode.fundamentalandroid.ui

import androidx.lifecycle.ViewModel
import com.kisahcode.fundamentalandroid.data.NewsRepository
import com.kisahcode.fundamentalandroid.data.local.entity.NewsEntity

/**
 * ViewModel for managing news-related data.
 *
 * @property newsRepository The repository for accessing news data.
 */
class NewsViewModel(private val newsRepository: NewsRepository) : ViewModel() {

    /**
     * Retrieves the latest headline news.
     *
     * @return LiveData<Result<List<NewsEntity>>> LiveData containing the result of the operation.
     */
    fun getHeadlineNews() = newsRepository.getHeadlineNews()

    /**
     * Retrieves bookmarked news.
     *
     * @return LiveData<List<NewsEntity>> LiveData containing the bookmarked news.
     */
    fun getBookmarkedNews() = newsRepository.getBookmarkedNews()

    /**
     * Saves a news article as bookmarked.
     *
     * @param news The news entity to be saved.
     */
    fun saveNews(news: NewsEntity) {
        newsRepository.setBookmarkedNews(news, true)
    }

    /**
     * Deletes a news article from bookmarks.
     *
     * @param news The news entity to be deleted.
     */
    fun deleteNews(news: NewsEntity) {
        newsRepository.setBookmarkedNews(news, false)
    }
}