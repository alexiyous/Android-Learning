package com.alexius.core.domain.usecases.news

import com.alexius.core.domain.model.Article
import com.alexius.core.domain.repository.NewsRepository

class SelectArticle (
    private val newsRepository: NewsRepository
) {
    suspend operator fun invoke(uri: String): Article?{
        return newsRepository.selectArticle(uri)
    }
}