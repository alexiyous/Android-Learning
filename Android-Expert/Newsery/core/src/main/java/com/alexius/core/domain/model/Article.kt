package com.alexius.core.domain.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity
data class Article(
    val author: String?,
    val content: String,
    val description: String,
    val publishedAt: String,
    // Since Source is an object and room database could
    // not store object, we need to convert it to string (See NewsTypeConverter.kt)
    val source: Source,
    val title: String,
    @PrimaryKey val url: String,
    val urlToImage: String,
    var isBookmarked: Boolean = false
) : Parcelable