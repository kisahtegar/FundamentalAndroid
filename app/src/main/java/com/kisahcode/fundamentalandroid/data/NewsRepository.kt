package com.kisahcode.fundamentalandroid.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import com.kisahcode.fundamentalandroid.BuildConfig
import com.kisahcode.fundamentalandroid.data.local.entity.NewsEntity
import com.kisahcode.fundamentalandroid.data.local.room.NewsDao
import com.kisahcode.fundamentalandroid.data.remote.response.NewsResponse
import com.kisahcode.fundamentalandroid.data.remote.retrofit.ApiService
import com.kisahcode.fundamentalandroid.utils.AppExecutors
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*

/**
 * Repository responsible for handling news data from both remote and local sources.
 * Acts as a mediator between the API service and local database.
 *
 * @property apiService The Retrofit API service for fetching news from the remote server.
 * @property newsDao The Room DAO for accessing news data from the local database.
 * @property appExecutors The executor for handling asynchronous tasks.
 */
class NewsRepository private constructor(
    private val apiService: ApiService,
    private val newsDao: NewsDao,
    private val appExecutors: AppExecutors
) {
    private val result = MediatorLiveData<Result<List<NewsEntity>>>()

    /**
     * Fetches headline news from the remote server and updates the local database.
     * Returns a LiveData object representing the result of the operation.
     *
     * @return LiveData<Result<List<NewsEntity>>> A LiveData object containing the result of fetching news.
     */
    fun getHeadlineNews(): LiveData<Result<List<NewsEntity>>> {
        // Emit loading state
        result.value = Result.Loading

        // Fetch data from the remote server
        val client = apiService.getNews(BuildConfig.API_KEY)
        client.enqueue(object : Callback<NewsResponse> {
            override fun onResponse(call: Call<NewsResponse>, response: Response<NewsResponse>) {
                if (response.isSuccessful) {
                    val articles = response.body()?.articles
                    val newsList = ArrayList<NewsEntity>()

                    // Process articles from the response
                    appExecutors.diskIO.execute {
                        articles?.forEach { article ->
                            // Check if the news is bookmarked
                            val isBookmarked = newsDao.isNewsBookmarked(article.title)
                            val news = NewsEntity(
                                article.title,
                                article.publishedAt,
                                article.urlToImage,
                                article.url,
                                isBookmarked
                            )
                            newsList.add(news)
                        }
                        // Clear existing news and insert new news into the local database
                        newsDao.deleteAll()
                        newsDao.insertNews(newsList)
                    }
                }
            }

            override fun onFailure(call: Call<NewsResponse>, t: Throwable) {
                // Emit error state if the request fails
                result.value = Result.Error(t.message.toString())
            }
        })

        // Observe changes in the local database and emit the latest news data
        val localData = newsDao.getNews()
        result.addSource(localData) { newData: List<NewsEntity> ->
            result.value = Result.Success(newData)
        }
        return result
    }

    /**
     * Retrieves all bookmarked news from the local database.
     *
     * @return LiveData<List<NewsEntity>> A LiveData object containing a list of bookmarked news.
     */
    fun getBookmarkedNews(): LiveData<List<NewsEntity>> {
        return newsDao.getBookmarkedNews()
    }

    /**
     * Sets the bookmark state of a news entity in the local database.
     *
     * @param news The news entity to update.
     * @param bookmarkState The new bookmark state.
     */
    fun setBookmarkedNews(news: NewsEntity, bookmarkState: Boolean) {
        appExecutors.diskIO.execute {
            news.isBookmarked = bookmarkState
            newsDao.updateNews(news)
        }
    }

    /**
     * Companion object to provide a singleton instance of the NewsRepository.
     */
    companion object {
        @Volatile
        private var instance: NewsRepository? = null

        /**
         * Gets the singleton instance of NewsRepository.
         *
         * @param apiService The Retrofit API service.
         * @param newsDao The Room DAO for news entities.
         * @param appExecutors The executor for handling asynchronous tasks.
         * @return NewsRepository The singleton instance of NewsRepository.
         */
        fun getInstance(
            apiService: ApiService,
            newsDao: NewsDao,
            appExecutors: AppExecutors
        ): NewsRepository =
            instance ?: synchronized(this) {
                instance ?: NewsRepository(apiService, newsDao, appExecutors)
            }.also { instance = it }
    }
}