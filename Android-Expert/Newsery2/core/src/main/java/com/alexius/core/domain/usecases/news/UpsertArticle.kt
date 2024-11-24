package com.alexius.core.domain.usecases.news

import com.alexius.core.data.local.entity.ArticleEntity
import com.alexius.core.data.remote.response.Article
import com.alexius.core.domain.model.ArticleModel
import com.alexius.core.domain.repository.NewsRepository

class UpsertArticle(
    private val newsRepository: NewsRepository
) {
    suspend operator fun invoke(article: ArticleModel){
        newsRepository.upsertArticle(article)
    }
}