package com.alexius.newsery2.presentation.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.alexius.core.domain.usecases.news.NewsUseCases

class HomeViewModel(
    private val newsUseCases: NewsUseCases
): ViewModel() {

    val news = newsUseCases.getNews(
        sources = listOf("techcrunch", "wired", "techinasia", "the-verge", "the-next-web")
    ).cachedIn(viewModelScope)
}