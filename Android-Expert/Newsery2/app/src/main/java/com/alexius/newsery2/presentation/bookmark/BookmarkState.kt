package com.alexius.newsery2.presentation.bookmark

import com.alexius.newsery2.domain.model.Article

data class BookmarkState(
    val articles: List<Article> = emptyList()
)
