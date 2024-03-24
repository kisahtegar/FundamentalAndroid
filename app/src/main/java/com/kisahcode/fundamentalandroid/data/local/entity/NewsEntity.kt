package com.kisahcode.fundamentalandroid.data.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Represents a single news entity stored in the local database.
 *
 * @property title The title of the news.
 * @property publishedAt The publication date of the news.
 * @property urlToImage The URL to the image associated with the news.
 * @property url The URL of the news article.
 * @property isBookmarked Indicates whether the news is bookmarked by the user.
 */
@Entity(tableName = "news")
class NewsEntity(
    @field:ColumnInfo(name = "title")
    @field:PrimaryKey
    val title: String,

    @field:ColumnInfo(name = "publishedAt")
    val publishedAt: String,

    @field:ColumnInfo(name = "urlToImage")
    val urlToImage: String? = null,

    @field:ColumnInfo(name = "url")
    val url: String? = null,

    @field:ColumnInfo(name = "bookmarked")
    var isBookmarked: Boolean
)