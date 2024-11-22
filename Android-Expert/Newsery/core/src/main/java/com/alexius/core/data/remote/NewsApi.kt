package com.alexius.core.data.remote

import com.alexius.core.data.remote.response.NewsResponse
import com.alexius.core.util.Constants.NEWS_API_KEY
import retrofit2.http.GET
import retrofit2.http.Query

interface NewsApi {

    @GET("everything")
    suspend fun getNews(
        @Query("page") page: Int,
        @Query("sources") sources: String,
        @Query("apiKey") apiKey: String = NEWS_API_KEY
    ): NewsResponse

    @GET("everything")
    suspend fun searchNews(
        @Query("q") query: String,
        @Query("page") page: Int,
        @Query("sources") sources: String,
        @Query("apiKey") apiKey: String = NEWS_API_KEY
    ): NewsResponse
}