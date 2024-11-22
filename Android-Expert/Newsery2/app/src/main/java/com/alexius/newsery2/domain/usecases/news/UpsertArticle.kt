package com.alexius.newsery2.domain.usecases.news

import com.alexius.newsery2.domain.model.Article
import com.alexius.newsery2.domain.repository.NewsRepository

class UpsertArticle(
    private val newsRepository: NewsRepository
) {
    suspend operator fun invoke(article: Article){
        newsRepository.upsertArticle(article)
    }
}