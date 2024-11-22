package com.alexius.bookmark.bookmark

import com.alexius.newsery2.domain.model.Article

data class BookmarkState(
    val articles: List<Article> = emptyList()
)
