package com.alexius.newsery2.domain.usecases.news

import androidx.paging.PagingData
import com.alexius.newsery2.domain.model.Article
import com.alexius.newsery2.domain.repository.NewsRepository
import kotlinx.coroutines.flow.Flow

class SearchNews(
    private val newsRepository: NewsRepository
) {
    operator fun invoke(searchQuery: String, sources: List<String>): Flow<PagingData<Article>>{
        return newsRepository.searchNews(searchQuery, sources)
    }
}