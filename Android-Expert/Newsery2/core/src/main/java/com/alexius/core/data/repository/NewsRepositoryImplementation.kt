package com.alexius.core.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.map
import com.alexius.core.data.local.NewsDao
import com.alexius.core.data.remote.NewsApi
import com.alexius.core.data.remote.NewsPagingSource
import com.alexius.core.data.remote.SearchNewsPagingSource
import com.alexius.core.domain.model.ArticleModel
import com.alexius.core.domain.model.SourceModel
import com.alexius.core.domain.repository.NewsRepository
import com.alexius.core.util.DataMapper
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach

class NewsRepositoryImplementation(
    private val newsApi: NewsApi,
    private val newsDao: NewsDao
): NewsRepository {
    override fun getNews(sources: List<String>): Flow<PagingData<ArticleModel>> {
        return Pager(
            config = PagingConfig(pageSize = 10),
            pagingSourceFactory = {
                NewsPagingSource(
                    newsApi = newsApi,
                    sources = sources.joinToString(separator = ",")
                )
            }
        ).flow.map { pagingData ->
            pagingData.map {
                val source = SourceModel(
                    id = it.source.id,
                    name = it.source.name
                )
                ArticleModel(
                    author = it.author,
                    content = it.content,
                    description = it.description,
                    publishedAt = it.publishedAt,
                    source = source,
                    title = it.title,
                    url = it.url,
                    urlToImage = it.urlToImage,
                    isBookmarked = false
                )
            }
        }
    }

    override fun searchNews(
        searchQuery: String,
        sources: List<String>
    ): Flow<PagingData<ArticleModel>> {
        return Pager(
            config = PagingConfig(pageSize = 10),
            pagingSourceFactory = {
                SearchNewsPagingSource(
                    searchQuery = searchQuery,
                    newsApi = newsApi,
                    sources = sources.joinToString(separator = ",")
                )
            }
        ).flow.map {
            it.map { article ->
                val source = SourceModel(
                    id = article.source.id,
                    name = article.source.name
                )
                ArticleModel(
                    author = article.author,
                    content = article.content,
                    description = article.description,
                    publishedAt = article.publishedAt,
                    source = source,
                    title = article.title,
                    url = article.url,
                    urlToImage = article.urlToImage,
                    isBookmarked = false
                )
            }
        }
    }

    override suspend fun upsertArticle(article: ArticleModel) {
        val articleEntity = DataMapper.mapDomainToEntity(article)
        newsDao.upsert(articleEntity)
    }

    override suspend fun deleteArticle(article: ArticleModel) {
        val article = DataMapper.mapDomainToEntity(article)
        newsDao.delete(article)
    }

    override fun selectArticles(): Flow<List<ArticleModel>> {
        val articles =  newsDao.getArticles().onEach { it.reversed()}
        return articles.map {
            it.map { article ->
                DataMapper.mapEntityToDomain(article)
            }
        }
    }

    override suspend fun selectArticle(url: String): ArticleModel? {
        return newsDao.getArticle(url)?.let {
            DataMapper.mapEntityToDomain(it)
        }
    }
}