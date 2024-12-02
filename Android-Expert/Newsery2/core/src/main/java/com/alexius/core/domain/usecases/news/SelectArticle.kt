package com.alexius.core.domain.usecases.news

import com.alexius.core.domain.model.ArticleModel
import com.alexius.core.domain.repository.NewsRepository

class SelectArticle (
    private val newsRepository: NewsRepository
) {
    suspend operator fun invoke(uri: String): ArticleModel?{
        return newsRepository.selectArticle(uri)
    }
}