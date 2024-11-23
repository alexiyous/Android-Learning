package com.alexius.newsery2.presentation.search

import androidx.paging.PagingData
import com.alexius.core.domain.model.Article
import kotlinx.coroutines.flow.Flow

data class SearchState(
    val searchQuery: String = "",
    val articles: Flow<PagingData<Article>>? = null
) {
}