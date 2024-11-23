package com.alexius.core.domain.usecases.news

import com.alexius.core.domain.model.Article
import com.alexius.core.domain.repository.NewsRepository
import kotlinx.coroutines.flow.Flow

class SelectArticles(
    private val newsRepository: NewsRepository
) {
    operator fun invoke(): Flow<List<Article>> {
        return newsRepository.selectArticles()
    }
}