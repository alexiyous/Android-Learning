package com.alexius.newsery2.domain.usecases.news

import com.alexius.newsery2.domain.model.Article
import com.alexius.newsery2.domain.repository.NewsRepository
import kotlinx.coroutines.flow.Flow

class SelectArticles(
    private val newsRepository: NewsRepository
) {
    operator fun invoke(): Flow<List<Article>> {
        return newsRepository.selectArticles()
    }
}