package com.alexius.core.domain.usecases.news

import androidx.paging.PagingData
import com.alexius.core.domain.model.ArticleModel
import com.alexius.core.domain.repository.NewsRepository
import kotlinx.coroutines.flow.Flow

class GetNews(
    private val newsRepository: NewsRepository
) {
    operator fun invoke(sources: List<String>): Flow<PagingData<ArticleModel>>{
        return newsRepository.getNews(sources)
    }
}