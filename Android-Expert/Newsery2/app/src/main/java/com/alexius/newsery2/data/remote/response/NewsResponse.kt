package com.alexius.newsery2.data.remote.response

import com.alexius.newsery2.domain.model.Article

data class NewsResponse(
    val articles: List<Article>,
    val status: String,
    val totalResults: Int
)