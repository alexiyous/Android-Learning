package com.alexius.core.data.remote

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.alexius.core.data.remote.response.Article

class SearchNewsPagingSource(
    private val newsApi: NewsApi,
    private val searchQuery: String,
    private val sources: String
): PagingSource<Int, Article>() {

    private var totalNewsCount = 0

    override fun getRefreshKey(state: PagingState<Int, Article>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Article> {
        val page = params.key ?: 1
        return try {
            val response = newsApi.searchNews(page = page, sources = sources, query = searchQuery)
            totalNewsCount += response.articles.size
            val articles = response.articles.distinctBy { it.title } // Remove duplicate articles
            LoadResult.Page(
                data = articles,
                prevKey = null,
                nextKey = if (totalNewsCount >= response.totalResults) null else page + 1
            )
        } catch (e: Exception) {
            e.printStackTrace()
            LoadResult.Error(e)
        }
    }


}