package com.alexius.bookmark.bookmark

data class BookmarkState(
    val articles: List<com.alexius.core.domain.model.Article> = emptyList()
)
