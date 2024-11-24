package com.alexius.bookmark.bookmark

import com.alexius.core.data.remote.response.Article
import com.alexius.core.domain.model.ArticleModel

data class BookmarkState(
    val articles: List<ArticleModel> = emptyList()
)
