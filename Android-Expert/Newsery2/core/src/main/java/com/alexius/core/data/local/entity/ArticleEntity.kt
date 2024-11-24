package com.alexius.core.data.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.alexius.core.data.remote.response.Source

@Entity(tableName = "Article")
data class ArticleEntity(
    @ColumnInfo(name = "author")
    val author: String?,
    @ColumnInfo(name = "content")
    val content: String,
    @ColumnInfo(name = "description")
    val description: String,
    @ColumnInfo(name = "publishedAt")
    val publishedAt: String,
    @ColumnInfo(name = "source")
    val source: SourceEntity,
    @ColumnInfo(name = "title")
    val title: String,
    @PrimaryKey val url: String,
    @ColumnInfo(name = "urlToImage")
    val urlToImage: String,
    @ColumnInfo(name = "isBookmarked")
    var isBookmarked: Boolean = false
)
