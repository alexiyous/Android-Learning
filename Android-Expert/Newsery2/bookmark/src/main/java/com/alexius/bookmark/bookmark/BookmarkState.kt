package com.alexius.bookmark.bookmark

import com.alexius.core.domain.model.Article

data class BookmarkState(
    val articles: List<Article> = emptyList()
)
