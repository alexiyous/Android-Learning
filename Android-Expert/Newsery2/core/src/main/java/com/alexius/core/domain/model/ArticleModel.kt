package com.alexius.core.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class ArticleModel(
    val author: String?,
    val content: String,
    val description: String,
    val publishedAt: String,
    // Since Source is an object and room database could
    // not store object, we need to convert it to string (See NewsTypeConverter.kt)
    val source: SourceModel,
    val title: String,
    val url: String,
    val urlToImage: String,
    var isBookmarked: Boolean = false
): Parcelable