package com.alexius.core.domain.usecases.news

import com.alexius.core.domain.model.ArticleModel
import com.alexius.core.domain.repository.NewsRepository
import kotlinx.coroutines.flow.Flow

class SelectArticles(
    private val newsRepository: NewsRepository
) {
    operator fun invoke(): Flow<List<ArticleModel>> {
        return newsRepository.selectArticles()
    }
}