package com.alexius.newsery2.domain.usecases.news

import com.alexius.newsery2.domain.model.Article
import com.alexius.newsery2.domain.repository.NewsRepository

class DeleteArticle(
    private val newsRepository: NewsRepository
) {
    suspend operator fun invoke(article: Article){
        newsRepository.deleteArticle(article)
    }
}