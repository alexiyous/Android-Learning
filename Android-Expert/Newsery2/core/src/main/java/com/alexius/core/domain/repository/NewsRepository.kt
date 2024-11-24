package com.alexius.core.domain.repository

import androidx.paging.PagingData
import com.alexius.core.data.local.entity.ArticleEntity
import com.alexius.core.data.remote.response.Article
import com.alexius.core.domain.model.ArticleModel
import kotlinx.coroutines.flow.Flow

interface NewsRepository {

    fun getNews(sources: List<String>): Flow<PagingData<ArticleModel>>

    fun searchNews(searchQuery: String, sources: List<String>): Flow<PagingData<ArticleModel>>

    suspend fun upsertArticle(article: ArticleModel)

    suspend fun deleteArticle(article: ArticleModel)

    fun selectArticles(): Flow<List<ArticleModel>>

    suspend fun selectArticle(url: String): ArticleModel?
}