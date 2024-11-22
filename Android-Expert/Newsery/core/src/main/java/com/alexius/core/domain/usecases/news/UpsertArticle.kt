package com.alexius.core.domain.usecases.news

import com.alexius.core.domain.model.Article
import com.alexius.core.domain.repository.NewsRepository

class UpsertArticle(
    private val newsRepository: NewsRepository
) {
    suspend operator fun invoke(article: Article){
        newsRepository.upsertArticle(article)
    }
}