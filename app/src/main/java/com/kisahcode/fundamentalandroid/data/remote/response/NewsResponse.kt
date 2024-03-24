package com.kisahcode.fundamentalandroid.data.remote.response

import com.google.gson.annotations.SerializedName

/**
 * Data class representing the response from the news API.
 *
 * @property totalResults The total number of results returned.
 * @property articles The list of news articles.
 * @property status The status of the API response.
 */
data class NewsResponse(

    @field:SerializedName("totalResults")
    val totalResults: Int,

    @field:SerializedName("articles")
    val articles: List<ArticlesItem>,

    @field:SerializedName("status")
    val status: String
)

/**
 * Data class representing the source of a news article.
 *
 * @property name The name of the news source.
 * @property id The ID of the news source.
 */
data class Source(

    @field:SerializedName("name")
    val name: String,

    @field:SerializedName("id")
    val id: Any
)

/**
 * Data class representing a single news article.
 *
 * @property publishedAt The publish date of the news article.
 * @property author The author of the news article.
 * @property urlToImage The URL to the image associated with the news article.
 * @property description The description of the news article.
 * @property source The source of the news article.
 * @property title The title of the news article.
 * @property url The URL to the full news article.
 * @property content The content of the news article.
 */
data class ArticlesItem(

    @field:SerializedName("publishedAt")
    val publishedAt: String,

    @field:SerializedName("author")
    val author: String,

    @field:SerializedName("urlToImage")
    val urlToImage: String,

    @field:SerializedName("description")
    val description: String,

    @field:SerializedName("source")
    val source: Source,

    @field:SerializedName("title")
    val title: String,

    @field:SerializedName("url")
    val url: String,

    @field:SerializedName("content")
    val content: Any
)
