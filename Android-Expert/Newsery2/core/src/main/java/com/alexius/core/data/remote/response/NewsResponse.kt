package com.alexius.core.data.remote.response

import com.alexius.core.domain.model.Article

data class NewsResponse(
    val articles: List<Article>,
    val status: String,
    val totalResults: Int
)